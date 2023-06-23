package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.entities.Token;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.repository.TokenRepository;
import com.example.backendglobaldirectory.repository.UserRepository;
import com.example.backendglobaldirectory.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    public String createEmail(String email)
            throws UserNotFoundException {
        Optional<User> userOptional = this.userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("No user found with the given email. Can't perform the password change.");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", List.of(userOptional.get().getRole().name()));

        String jwtToken = this.jwtService.generateToken(claims, userOptional.get());

        Token token = new Token(jwtToken,
                false,
                false,
                userOptional.get());

        this.tokenRepository.save(token);

        String link = "http://localhost:4200/reset-password?token=" + jwtToken;

        String subject = "Reset password";
        String body = "Dear user ,\n"
                + "You have requested to reset your password. Please click the link below to proceed with the password reset process:\n\n"
                + link + "\n"
                + "If you did not request a password reset, please ignore this email.\n"
                + "Thank you.\n";
        return sendEmail(email, subject, body);
    }

    public void sendAnniversaryEmailToUser(User user, int noOfYears)
            throws FileNotFoundException {
        String anniversaryMailFormat = Utils.readAnniversaryMailPattern();

        String emailBody = String.format(
                anniversaryMailFormat,
                user.getFirstName() + " " + user.getLastName(),
                noOfYears);

        sendEmail(user.getEmail(), "Anniversary email", emailBody);
    }

    public void sendPromotionEmailToUser(User user, String newJobTitle)
            throws FileNotFoundException {
        String promotionMailFormat = Utils.readPromotionMailPattern();

        String emailBody = String.format(
                promotionMailFormat,
                user.getFirstName() + " " + user.getLastName(),
                newJobTitle);

        sendEmail(user.getEmail(), "Anniversary email", emailBody);
    }

    public String sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        return "Mail sent successfully.";
    }
}
