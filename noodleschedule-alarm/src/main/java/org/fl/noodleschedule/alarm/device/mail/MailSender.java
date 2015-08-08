package org.fl.noodleschedule.alarm.device.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {
	
	private final static Logger logger = LoggerFactory.getLogger(MailSender.class);
	
	private String sender = "Noodle Schedule";
	private String senderEmail = "noodle_schedule@163.com";
	private String user = "noodle_schedule";
	private String password = "schedule@noodle";
	private String smtpHost = "smtp.163.com";
	
	public boolean sendMail(String toMail, String title, String content) {
		
		Properties properties = System.getProperties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.stmp.timeout", "10000");
		
		Session session = Session.getDefaultInstance(properties, new MyAuthenticator());
		session.setDebug(false);
		
		try {
			
			Message message = new MimeMessage(session);
			message.setSubject(title);
			message.setContent(content, "text/html;charset=UTF-8;");
			message.setFrom(new InternetAddress(senderEmail, sender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
			message.setSentDate(new Date());
			
			logger.info("Send a alarm email -> toMail: {}, title: {}, content: {}", toMail, title, content);
			
			Transport.send(message);
			
			return true;
			
		} catch (Exception e) {
			logger.error("sendMail -> Transport.send -> toMail: {}, title: {}, content: {} -> Exception: {}", toMail, title, content, e);
		}
		
		return false;		
	}
	
	private class MyAuthenticator extends Authenticator {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password);
		}
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
}
