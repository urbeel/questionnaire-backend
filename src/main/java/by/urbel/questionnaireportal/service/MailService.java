package by.urbel.questionnaireportal.service;

public interface MailService {
    void sendMessage(String toEmail, String subject, String text);
}
