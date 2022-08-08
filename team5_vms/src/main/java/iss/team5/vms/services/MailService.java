package iss.team5.vms.services;

public interface MailService {
    void sendSimpleMail(String receiveEmail, String subject, String content);
}
