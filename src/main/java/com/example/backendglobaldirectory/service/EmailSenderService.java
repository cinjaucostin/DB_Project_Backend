package com.example.backendglobaldirectory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;


    public String createEmail(String email){
        String toEmail = email;
        String subject = "Reset password";
        String body = "Dear user ,\n\n"
                + "You have requested to reset your password. Please click the link below to proceed with the password reset process:\n\n"
                + "resetLink" + "\n\n"
                + "If you did not request a password reset, please ignore this email.\n\n"
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
