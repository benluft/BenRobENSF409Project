package backEnd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import sharedData.Assignment;
import sharedData.Course;
import sharedData.Enrolment;
import sharedData.MessageNameConstants;
import sharedData.SocketMessage;
import sharedData.Submission;
import sharedData.User;

class ProfReadMessage extends StudentAndProfDBReader implements MessageNameConstants
{
	
	public ProfReadMessage(SocketMessage message)
	{
		super(message);
	}
	
	protected void readAssignmentTable(Assignment assign)
	{
		DBReader reader;
		
		reader = new DBReader(assignmentMessage, "course_id", assign.getCourseID());
		
		ResultSet rs = reader.getReadResults();
		
		try {
			while(rs.next())
			{
				getToSend().add(new Assignment(false, rs.getInt(1), rs.getInt(2),
						rs.getString(3),rs.getBoolean(5),rs.getString(6)));
				System.out.println(rs.getString(6));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void readEnrolTable(Enrolment enrol) {

		DBReader dbReader;
		
		dbReader = new DBReader(enrolMessage, "course_id", enrol.getCourseID());
			
		ResultSet rs = dbReader.getReadResults();
		
		try 
		{
			while(rs.next())
			{
				getToSend().add(new Enrolment(false, rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void readCourseTable(Course course)
	{

		DBReader dbReader = new DBReader(courseMessage.toLowerCase(), "prof_id", course.getProfID());
		
		ResultSet rs = dbReader.getReadResults();
		
		try 
		{
			while(rs.next())
			{
				getToSend().add(new Course(false, rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getBoolean(4)));
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	protected void readUserTable(User user)
	{
		
		DBReader dbReader = new DBReader(userMessage.toLowerCase(), "type", user.getType());
		
		ResultSet rs = dbReader.getReadResults();
		
		try 
		{
			while(rs.next())
			{
				getToSend().add(new User(false, rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6)));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public Vector<SocketMessage> getToSend()
	{
		return super.getToSend();
	}

	@Override
	protected void readCourseTable(Vector<Course> courses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readSubmissionTable(Submission submission) {

		DBReader dbreader;
		
		dbreader = new DBReader(submissionMessage, "assign_id", submission.getAssignID());
		
		ResultSet rs = dbreader.getReadResults();
		
		System.out.println("In readSubmission table submission assign id is" + submission.getAssignID());
		
		if(submission.getID() == -1)
		{
			try
			{
				System.out.println("In PDF place table");
				rs.next();
				PDFReader pdfReader = new PDFReader(rs.getString(4));
				getToSend().add(pdfReader.getFileToSend());
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				while(rs.next())
				{
					System.out.println("One Submission sending");
					getToSend().add(new Submission(false, rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(5), rs.getInt(6), null));
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
	}
}