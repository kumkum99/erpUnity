package com.springboot.web.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.springboot.web.model.Employee;
import com.springboot.web.repository.UserRepository;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;  // Inject the mail sender

    private Map<String, String> verificationCodeStorage = new HashMap<>();

    // Method to send a password reset code to the user
    public boolean sendPasswordResetCode(String email) {
        if (!userRepository.existsByEmail(email)) {
            System.out.println("Email not found: " + email);
            return false;
        }

        String code = generateVerificationCode();
        verificationCodeStorage.put(email, code);

        // Send the code via email
        sendEmail(email, code);

        System.out.println("Password reset code sent to: " + email);
        System.out.println("Code: " + code);

        return true;
    }

    // Method to reset the password using the code
    public boolean resetPassword(String email, String code, String newPassword) {
        String storedCode = verificationCodeStorage.get(email);

        if (storedCode == null || !storedCode.equals(code)) {
            System.out.println("Invalid or expired code for email: " + email);
            return false;
        }

        Employee user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
            verificationCodeStorage.remove(email);
            System.out.println("Password reset successfully for email: " + email);
            return true;
        }

        System.out.println("User not found for email: " + email);
        return false;
    }

    // Method to generate a 6-digit verification code
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generate a 6-digit code
        return String.valueOf(code);
    }

    // Method to send email with the password reset code
    private void sendEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Code");
        message.setText("Your password reset code is: " + code);
        mailSender.send(message);
    }
}
