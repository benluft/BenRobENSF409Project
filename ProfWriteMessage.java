package backEnd;

import java.io.IOException;
import java.io.ObjectInputStream;

import sharedData.*;

/**
 * Writes a message to the database from a professor
 * 
 * @author Ben Luft
 *
 */
class ProfWriteMessage implements MessageNameConstants, ServerFilePaths
{
	
	/**
	 * Writes to the database from the professor
	 * 
	 * @param message is the message to write to the file
	 * @param currentUser is the current user of the program
	 * @param reader is used to write to the socket
	 */
	public ProfWriteMessage(SocketMessage message, User currentUser, ObjectInputStream reader)
	{
		System.out.println("ProfWritemessage sees message type "+ message.getMessageType());
		
		if(message.getMessageType().equals(emailMessage))
		{
			Email mail = (Email) message;
			
			EmailWriter emailwriter = new EmailWriter(currentUser, mail);
		}
		
		if(message.getMessageType().equals(assignmentMessage))
		{
			Assignment assign = (Assignment) message;
			
			DBWriter writer = new DBWriter(assignmentMessage.toLowerCase(), message);
			
			SocketMessage messageFile;
			
			if(assign.getID() == -1)
			{
				try 
				{
					messageFile = (SocketMessage) reader.readObject();
					PDFWriter pdfWriter = new PDFWriter(messageFile, currentUser.getType());
				} 
				catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
		
		if(message.getMessageType().equals(courseMessage))
		{
			System.out.println("Message to write is a course Message");
			DBWriter writer = new DBWriter(courseMessage.toLowerCase(), message);
		}
		
		if(message.getMessageType().equals(enrolMessage))
		{
			DBWriter writer = new DBWriter(enrolMessage.toLowerCase(), message);
		}
		
		if(message.getMessageType().equals(submissionMessage))
		{
			System.out.println("message to write is a submission message");
			DBWriter writer = new DBWriter(submissionMessage, message);
		}
		
	}
	
}