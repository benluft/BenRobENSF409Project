package backEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import sharedData.*;

class DecodeMessage implements Runnable, MessageNameConstants
{
	ObjectInputStream reader;
	
	ObjectOutputStream writer;
	
	Vector<SocketMessage> toSend;
	
	User currentUser;

	public DecodeMessage(ObjectOutputStream writer, ObjectInputStream reader) 
	{
		this.writer = writer;
		this.reader = reader;
	}
	
	@Override
	public void run() {
		
		toSend = new Vector<SocketMessage>();
		
		LoginChecker login = new LoginChecker(reader, writer);
		
		currentUser = login.getUser().get(0);
		
		while(true)
		{
			System.out.println("Waiting for new message");
			toSend = new Vector<SocketMessage>();
			try 
			{
				
				SocketMessage message = (SocketMessage) reader.readObject();
				if(message.getIsQuerry())
				{
					System.out.println("This message is a query");
					pickReadWorker(message);
					writer.writeObject(toSend);
					
				}
				else
				{
					pickWriteWorker(message);
				}
				
			} 
			catch (ClassNotFoundException | IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void pickWriteWorker(SocketMessage message)
	{
		
		if(currentUser.getType().equals("P"))
		{
			ProfWriteMessage profWrite = new ProfWriteMessage(message, currentUser, reader);
		}
		else
		{
			StudentWriteMessage studentWrite = new StudentWriteMessage();
		}
	}
	
	private void pickReadWorker(SocketMessage message)
	{
		if(currentUser.getType().equals("P"))
		{
			ProfReadMessage profRead = new ProfReadMessage(message);
			toSend = profRead.getToSend();
		}
		else
		{
			StudentReadMessage studentRead = new StudentReadMessage(message);
			toSend = studentRead.getToSend();
		}
	}

}