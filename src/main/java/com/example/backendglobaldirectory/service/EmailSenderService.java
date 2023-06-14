package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.ThePasswordsDoNotMatchException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private UserRepository userRepository;


    public String createEmail(String email)
            throws UserNotFoundException {
        Optional<User> userOptional = this.userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("No user found with the given email. Can't perform the password change.");
        }
        String toEmail = email;
        String subject = "Reset password";
        String body = "Dear user ,\n"
                + "You have requested to reset your password. Please click the link below to proceed with the password reset process:\n\n"
                + "resetLink" + "\n"
                + "If you did not request a password reset, please ignore this email.\n"
                + "Thank you.\n";
        return sendEmail(toEmail, subject, body);
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
