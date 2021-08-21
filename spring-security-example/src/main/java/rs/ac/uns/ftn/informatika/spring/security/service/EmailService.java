package rs.ac.uns.ftn.informatika.spring.security.service;
import javax.mail.MessagingException;

import org.springframework.mail.MailException;
public interface EmailService {
	public void sendEmailForRecoveryOfAccount(String email) throws MailException, MessagingException;
	public  void sendEmail(String email,String subject,String content) throws MailException, MessagingException;
}
