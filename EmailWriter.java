package backEnd;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sharedData.Email;

class EmailWriter
{
	private Properties properties;
	
	private Session session;
	
	public EmailWriter()
	{
		setProperties();
		setSession("ensf409prof1@gmail.com", "ENSF409RobBen");
		sendEmail();
	}
	
	private void setProperties()
	{
		properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "true"); // Using TLS
		properties.put("mail.smtp.auth", "true"); // Authenticate
		properties.put("mail.smtp.host", "smtp.gmail.com"); // Using Gmail Account
		properties.put("mail.smtp.port", "587"); // TLS uses port 587
	}
	
	private void setSession(String emailAddress, String password)
	{
		session = Session.getInstance(properties,
				new javax.mail.Authenticator(){
				 protected PasswordAuthentication getPasswordAuthentication() {
				 return new PasswordAuthentication(emailAddress, password);
				 }
				});
	}
	
	private void sendEmail()
	{
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ensf409prof1@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("benluft8@gmail.com"));
			message.setSubject("Your Message Subject");
			message.setText("Look it works");
			Transport.send(message); // Send the Email Message
			} catch (MessagingException e) {
			e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		EmailWriter email = new EmailWriter();
	}
}