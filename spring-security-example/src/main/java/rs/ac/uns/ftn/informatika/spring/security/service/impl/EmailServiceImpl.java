package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import javax.mail.Transport;


import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.service.EmailService;

import org.springframework.mail.MailException;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService   {
	@Override
	public void sendEmailForRecoveryOfAccount(String email) throws MailException, MessagingException {
		
		Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", "smtp.gmail.com");
	      props.put("mail.smtp.port", "587");
	      Session session = Session.getInstance(props,
	    	         new javax.mail.Authenticator() {
	    	            protected PasswordAuthentication getPasswordAuthentication() {
	    	               return new PasswordAuthentication("notificationsnotifications22@gmail.com", "Admin123#");
	    	            }
	    		});
	//	Session session = Session.getDefaultInstance(System.getProperties());  
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("notificationsnotifications22@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,
	              InternetAddress.parse(email));

		   // Set Subject: header field
		   message.setSubject("Appointment");

		   // Send the actual HTML message, as big as you like
		   message.setContent(
		              "You have successfully scheduled an appointment",
		             " text/html; charset=UTF-8");
		   
		   // Send message
		   Transport.send(message);
	}
	@Override
	public void sendEmail(String email,String subject,String content) throws MailException, MessagingException {
		
		Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", "smtp.gmail.com");
	      props.put("mail.smtp.port", "587");
	      Session session = Session.getInstance(props,
	    	         new javax.mail.Authenticator() {
	    	            protected PasswordAuthentication getPasswordAuthentication() {
	    	               return new PasswordAuthentication("notificationsnotifications22@gmail.com", "Admin123#");
	    	            }
	    		});
	//	Session session = Session.getDefaultInstance(System.getProperties());  
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("notificationsnotifications22@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,
	              InternetAddress.parse(email));

		   // Set Subject: header field
		   message.setSubject(subject);

		   // Send the actual HTML message, as big as you like
		   
		   message.setContent(
		              content,
		             " text/html; charset=UTF-8");
		   
		   // Send message
		   Transport.send(message);
	}
}
