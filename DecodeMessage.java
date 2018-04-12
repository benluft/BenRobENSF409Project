package backEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import sharedData.*;

/**
 * Thread that decides what kind of message the server has received, and whether to read or write
 * to the database, or take another course of action
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
class DecodeMessage implements Runnable, MessageNameConstants
{
	/**
	 * Reads from the socket
	 */
	ObjectInputStream reader;
	
	/**
	 * Writes to the socket
	 */
	ObjectOutputStream writer;
	
	/**
	 * The Vector that will be sent over the socket
	 */
	Vector<SocketMessage> toSend;
	
	/**
	 * The user that logged into the system
	 */
	User currentUser;

	/**
	 * Sets the reader and writer
	 * 
	 * @param writer
	 * @param reader
	 */
	public DecodeMessage(ObjectOutputStream writer, ObjectInputStream reader) 
	{
		this.writer = writer;
		this.reader = reader;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
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
	
	/**
	 * Picks a writer class based on whether the user is a student or a prof
	 * 
	 * @param message is the message to write
	 */
	private void pickWriteWorker(SocketMessage message)
	{
		
		if(currentUser.getType().equals("P"))
		{
			ProfWriteMessage profWrite = new ProfWriteMessage(message, currentUser, reader);
		}
		else
		{
			StudentWriteMessage studentWrite = new StudentWriteMessage(message, currentUser, reader);
		}
	}
	
	/**
	 * Picks a reader class based on whether the user is a student or prof
	 * 
	 * @param message is the message to send
	 */
	private void pickReadWorker(SocketMessage message)
	{
		if(currentUser.getType().equals("P"))
		{
			ProfReadMessage profRead = new ProfReadMessage(message);
			toSend = profRead.getToSend();
		}
		else
		{
			StudentReadMessage studentRead = new StudentReadMessage(message, writer);
			toSend = studentRead.getToSend();
		}
	}

}