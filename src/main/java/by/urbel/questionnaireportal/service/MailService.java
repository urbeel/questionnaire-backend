package by.urbel.questionnaireportal.service;

import java.util.Map;

public interface MailService {
    void sendMessage(String toEmail, String subject, String templateName, Map<String, Object> templateVariables);
}
