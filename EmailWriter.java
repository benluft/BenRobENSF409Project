package backEnd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sharedData.Email;
import sharedData.User;
import sharedData.MessageNameConstants;

class EmailWriter
{
	private Properties properties;
	
	private Session session;
	
	public EmailWriter(User senderUser, Email email)
	{
		setProperties();
		
		setSession(senderUser.getEmail(), "ENSF409RobBen");
		
		ArrayList<String> recipientEmails = new ArrayList<String>();
		
		if(senderUser.getType().equals("P"))
		{
						
			DBReader readEnrolled = new DBReader(MessageNameConstants.enrolMessage, 
					"courseID", email.getCourseID());
			ResultSet rs = readEnrolled.getReadResults();
			
			try {
				while(rs.next())
				{
					recipientEmails.add(rs.getString(3));
				}
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		sendEmail(recipientEmails, senderUser.getEmail(), email);
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
	
	private void sendEmail(ArrayList<String> recipients, String senderEmail, Email email)
	{
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmail));
			if(recipients.size() > 0)
			{
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients.get(0)));
			}
			else
			{
				return;
			}
			
			for(int i = 1; i < recipients.size(); i++)
			{
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(recipients.get(i)));
			}
			
			message.setSubject("Your Message Subject");
			message.setText(email.getMessageContents());
			Transport.send(message); // Send the Email Message
			} catch (MessagingException e) {
			e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		//EmailWriter email = new EmailWriter();
	}
}