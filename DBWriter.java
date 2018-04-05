package backEnd;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.Message;

import com.mysql.jdbc.PreparedStatement;

import sharedData.Assignment;
import sharedData.MessageNameConstants;
import sharedData.SocketMessage;

class DBWriter extends WriterWorker implements MessageNameConstants, ServerFilePaths
{
	
	private int tableColsPerMessage;

	private Object[] colsToWrite;
	
	DBWriter(String tableName, SocketMessage message) {
		super(tableName);
		
		if(tableName.equals(assignmentMessage.toLowerCase()))
		{
			writeAssignment(message);
		}
		else if(tableName.equals(courseMessage.toLowerCase()))
		{
			writeCourse(message);
		}
		else if(tableName.equals(enrolMessage.toLowerCase()))
		{
			writeEnrol(message);
		}
		else if(tableName.equals(submissionMessage.toLowerCase()))
		{
			writeSubmission(message);
		}
		else if(tableName.equals(gradesMessage.toLowerCase()))
		{
			writeGrades(message);
		}
		else
		{
			System.err.println("TRYING TO WRITE SOMETHING WEIRD");
		}
	}
	
	private PreparedStatement createSQLCommand(int sizeCommand)
	{
		for(int i = 1; i < tableColsPerMessage; i++)
		{
			super.appendSqlCommand("?,");
		}
		super.appendSqlCommand("?)");
		
		try 
		{
			return (PreparedStatement) super.getJdbc_connection().prepareStatement(super.getSqlCommand());
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	private void writeAssignment(SocketMessage message)
	{
		Assignment assign = (Assignment) message;
		
		PreparedStatement statement = createSQLCommand(6);
		
		try 
		{
			statement.setInt(2, assign.getCourseID());
			statement.setString(3, assign.getTitle());
			statement.setString(4, assignmentPath + assign.getTitle());
			statement.setBoolean(5, assign.isActive());
			statement.setString(6, assign.getDueDate());
			
			statement.execute();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void writeGrades(SocketMessage message)
	{
		
	}
	
	private void writeCourse(SocketMessage message)
	{
		
	}
	
	private void writeEnrol(SocketMessage message)
	{
		
	}
	
	private void writeSubmission(SocketMessage message)
	{
		
	}
	
}