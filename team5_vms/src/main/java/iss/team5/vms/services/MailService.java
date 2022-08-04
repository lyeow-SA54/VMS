package iss.team5.vms.services;

import javax.mail.MessagingException;
import java.util.List;

public interface MailService {
    void sendSimpleMail(String receiveEmail, String subject, String content);
}
