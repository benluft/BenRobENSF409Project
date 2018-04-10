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

	public DecodeMessage(ObjectOutputStream writer, ObjectInputStream reader) 
	{
		this.writer = writer;
		this.reader = reader;
	}
	
	@Override
	public void run() {
		
		toSend = new Vector<SocketMessage>();
		
		checkLogin();
		
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
		System.out.println("Got in write worker");
		if(message.getMessageType().equals(emailMessage))
		{
			Email mail = (Email) message;
			
			writeEmail(mail);
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
	
	private void pickReadWorker(SocketMessage message)
	{
		System.out.println("Message has got to pickReadWorker");
		if(message.getMessageType().equals(courseMessage))
		{
			System.out.println("Message received is a Course");
			
			Course course = (Course) message; 
			
			readCourseTable(course);
		}
		
		if(message.getMessageType().equals(userMessage))
		{
			System.out.println("Message received is a user");
			
			User user = (User) message;
			
			readUserTable(user);
		}
		
		if(message.getMessageType().equals(enrolMessage))
		{
			System.out.println("Message received is an enrol");
			
			Enrolment enrol = (Enrolment) message;
			
			readEnrolTable(enrol);
		}
		
		if(message.getMessageType().equals(assignmentMessage))
		{
			System.out.println("Message received is an assignment");
			
			Assignment assign = (Assignment) message;
			
			readAssignmentTable(assign);
		}
	}
	
	private void readAssignmentTable(Assignment assign)
	{
		DBReader reader = new DBReader(assignmentMessage, "course_id", assign.getCourseID());
		
		ResultSet rs = reader.getReadResults();
		
		try {
			while(rs.next())
			{
				toSend.add(new Assignment(false, rs.getInt(1), rs.getInt(2),
						rs.getString(3),rs.getBoolean(5),rs.getString(6)));
				System.out.println(rs.getString(6));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readEnrolTable(Enrolment enrol) {

		DBReader dbReader = new DBReader(enrolMessage.toLowerCase(), "course_id", enrol.getCourseID());
		
		ResultSet rs = dbReader.getReadResults();
		
		try 
		{
			while(rs.next())
			{
				toSend.add(new Enrolment(false, rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void readCourseTable(Course course)
	{
		DBReader dbReader = new DBReader(courseMessage.toLowerCase(), "prof_id", course.getProfID());
		
		ResultSet rs = dbReader.getReadResults();
		
		try 
		{
			while(rs.next())
			{
				toSend.add(new Course(false, rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getBoolean(4)));
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void readUserTable(User user)
	{
		
		DBReader dbReader = new DBReader(userMessage.toLowerCase(), "type", user.getType());
		
		ResultSet rs = dbReader.getReadResults();
		
		try 
		{
			while(rs.next())
			{
				toSend.add(new User(false, rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6)));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void writeEmail(Email email)
	{
		
	}
	
	
	private void checkLogin()
	{
		boolean loggedIn = false;
		
		while(!loggedIn)
		{
			SocketMessage message;
			try {
				message = (SocketMessage) reader.readObject();
				loggedIn = attemptLogin((User) message);
				writer.writeObject(toSend);
			} 
			catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	private boolean attemptLogin(User user)
	{
		DBReader dbReader = new DBReader(userMessage.toLowerCase(), "id", user.getID(),"password", user.getPassword());
		
		ResultSet rs = dbReader.getReadResults();
		
		try 
		{
			if(rs.next())
			{
				toSend.add(new User(true, rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6)));
				return true;
			}
			else
			{
				toSend.add(new User(false, 0, null, null, null, null, null));
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}