package backEnd;

import java.io.IOException;
import java.io.ObjectInputStream;

import sharedData.*;

class ProfWriteMessage implements MessageNameConstants
{
	
	public ProfWriteMessage(SocketMessage message, User currentUser, ObjectInputStream reader)
	{
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
					PDFWriter pdfWriter = new PDFWriter(messageFile);
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
		
	}
	
}