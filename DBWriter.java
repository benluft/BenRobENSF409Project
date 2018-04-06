package backEnd;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.Message;

import com.mysql.jdbc.PreparedStatement;

import sharedData.Assignment;
import sharedData.Course;
import sharedData.Enrolment;
import sharedData.MessageNameConstants;
import sharedData.SocketMessage;

class DBWriter extends WriterWorker implements MessageNameConstants, ServerFilePaths
{
	
	private int tableColsPerMessage;
	
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
		super.resetSqlCommand();
	}
	
	private PreparedStatement createSQLCommand(int sizeCommand)
	{
		for(int i = 1; i < sizeCommand; i++)
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
	
	private void duplicateProtection()
	{
		super.appendSqlCommand(" ON DUPLICATE KEY UPDATE active=?");
	}
	
	private void writeAssignment(SocketMessage message)
	{
		Assignment assign = (Assignment) message;
		
		PreparedStatement statement = createSQLCommand(6);
		
		super.appendSqlCommand(" ON DUPLICATE KEY UPDATE active=?");
		
		try 
		{
			statement.setInt(1, 0);
			statement.setInt(2, assign.getCourseID());
			statement.setString(3, assign.getTitle());
			statement.setString(4, assignmentPath + assign.getTitle());
			statement.setBoolean(5, assign.isActive());
			statement.setString(6, assign.getDueDate());
			statement.setBoolean(7, assign.isActive());
			
			statement.execute();
			super.resetSqlCommand();
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
		Course course = (Course) message;
		
		PreparedStatement statement = createSQLCommand(4);
		
		super.appendSqlCommand(" ON DUPLICATE KEY UPDATE active=?");
		
		try 
		{
			statement.setInt(1, 0);
			statement.setInt(2, course.getProfID());
			statement.setString(3, course.getName());
			statement.setBoolean(4, course.isActive());
			statement.setBoolean(5, course.isActive());
			
			statement.execute();
			super.resetSqlCommand();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeEnrol(SocketMessage message)
	{
		System.out.println("Got to Enrol");
		Enrolment enrol = (Enrolment) message;
		
		if(enrol.getID() == 0)
		{
			PreparedStatement statement = createSQLCommand(3);
			
			try {
				statement.setInt(1, enrol.getID());
				statement.setInt(2, enrol.getStudentID());
				statement.setInt(3, enrol.getCourseID());
				
				statement.execute();
				super.resetSqlCommand();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			String deleteSQL = "DELETE FROM " + enrolMessage + " WHERE course_id=" + enrol.getCourseID() +
					" AND student_id=" + enrol.getStudentID();
			
			try {
				PreparedStatement statement = (PreparedStatement) super.getJdbc_connection().prepareStatement(deleteSQL);
				
				statement.execute();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void writeSubmission(SocketMessage message)
	{
		
	}
	
}