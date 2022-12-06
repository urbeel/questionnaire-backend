package by.urbel.questionnaireportal.service.impl;

import by.urbel.questionnaireportal.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;
    @Value(value = "${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendMessage(String toEmail, String subject, String templateName, Map<String, Object> templateVariables) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateVariables);
            String htmlBody = thymeleafTemplateEngine.process(templateName, thymeleafContext);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(String.format("Error while sending message to %s", toEmail), e);
        }
    }
}
