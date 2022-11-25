package by.urbel.questionnaireportal.service.impl;

import by.urbel.questionnaireportal.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    @Value(value = "${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendNotificationOfSuccessfulRegistration(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Successful registration");
        message.setText("Thank you for registration! Now you can create your custom questionnaire)");
        mailSender.send(message);
    }
}
