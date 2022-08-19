package iss.team5.vms.email.service;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import iss.team5.vms.model.User;

@Service
public class EmailService {

	private final TemplateEngine templateEngine;

	private final JavaMailSender javaMailSender;

	public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
		this.templateEngine = templateEngine;
		this.javaMailSender = javaMailSender;
	}

	public String sendMail(User user) throws MessagingException {
		Context context = new Context();
		context.setVariable("user", user);

		String process = templateEngine.process("welcome", context);
		javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setSubject("Welcome " + user.getFirstName() + " " + user.getLastName());
		helper.setText(process, true);
		helper.setTo(user.getEmail());
		javaMailSender.send(mimeMessage);
		return "Sent";
	}
}