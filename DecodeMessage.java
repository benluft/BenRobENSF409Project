package backEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.util.ReadAheadInputStream;

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
		
		checkLogin();
		
		while(true)
		{
			try 
			{
				SocketMessage message = (SocketMessage) reader.readObject();
				if(message.getIsQuerry())
				{
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
		if(message.getMessageType() == emailMessage)
		{
			Email mail = (Email) message;
			
			writeEmail(mail);
		}
		
		if(message.getMessageType() == assignmentMessage)
		{
			Assignment assign = (Assignment) message;
			
			readAssignmentTable(assign);
			
			writeAssignmentTable(assign);
			
			SocketMessage messageFile;
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
	
	private void pickReadWorker(SocketMessage message)
	{
		
		if(message.getMessageType() == courseMessage)
		{
			Course course = (Course) message; 
			
			readCourseTable(course);
		}
		
		if(message.getMessageType() == userMessage)
		{
			User user = (User) message;
			
			readUserTable(user);
		}
		
		if(message.getMessageType() == enrolMessage)
		{
			Enrolment enrol = (Enrolment) message;
			
			readEnrolTable(enrol);
		}
		
		if(message.getMessageType() == assignmentMessage)
		{
			Assignment assign = (Assignment) message;
			
			readAssignmentTable(assign);
		}
	}
	
	private void readAssignmentTable(Assignment assign)
	{
		DBReader reader = new DBReader(assignmentMessage, "course_id", assign.getCourseID(), "title", assign.getTitle());
		
		ResultSet rs = reader.getReadResults();
		
		try {
			while(rs.next())
			{
				toSend.add(new Assignment(false, rs.getInt(1), rs.getInt(2),
						rs.getString(3),rs.getBoolean(4),rs.getString(5)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readEnrolTable(Enrolment enrol) {

		DBReader dbReader = new DBReader(courseMessage.toLowerCase(), "course_id", enrol.getCourseID());
		
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
	
	private void writeAssignmentTable(Assignment assign)
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
				toSend.add(new User(false, rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6)));
				return true;
			}
			else
			{
				toSend.add(new User(true, 0, null, null, null, null, null));
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}