package backEnd;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.interceptors.ResultSetScannerInterceptor;

import sharedData.Assignment;
import sharedData.Course;
import sharedData.Enrolment;
import sharedData.MessageNameConstants;
import sharedData.SocketMessage;
import sharedData.Submission;
import sharedData.User;

class StudentReadMessage extends StudentAndProfDBReader implements MessageNameConstants
{
	Vector<SocketMessage> messageToSend; 
	ObjectOutputStream writer;
	
	public StudentReadMessage(SocketMessage message, ObjectOutputStream writer)
	{
		super(message);
		
		this.writer = writer;
		messageToSend = new Vector<SocketMessage>();

	}

	@Override
	protected void readAssignmentTable(Assignment assign) {

		DBReader dbreader;
		
		dbreader = new DBReader(assignmentMessage, "course_id", assign.getCourseID());
		
		ResultSet rs = dbreader.getReadResults();
		
		if(assign.getID() == -1)
		{
			try
			{
				dbreader = new DBReader(assignmentMessage, "title", assign.getTitle());
				ResultSet rsFile = dbreader.getReadResults();
				
				rsFile.next();
				PDFReader pdfReader = new PDFReader(rsFile.getString(4));
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
					getToSend().add(new Assignment(false,rs.getInt(1),rs.getInt(2), 
							rs.getString(3), rs.getBoolean(5), rs.getString(6)));
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void readEnrolTable(Enrolment enrol) {
		
		DBReader dbReader;
		
		dbReader = new DBReader(enrolMessage, "student_id", enrol.getStudentID());
			
		ResultSet rs = dbReader.getReadResults();
		
		try 
		{
			Vector<Course> coursesEnrolled = new Vector<Course>();
			while(rs.next())
			{
				System.out.println("Course id on server "+ rs.getInt(3));
				coursesEnrolled.add(new Course(true, rs.getInt(3), 0, null, true));
			}
			readCourseTable(coursesEnrolled);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void readUserTable(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readCourseTable(Vector<Course> courses) 
	{
		DBReader reader;
	
		System.out.println("Number of courses " + courses.size());
		for(int i = 0; i < courses.size(); i++)
		{
			reader = new DBReader(courseMessage, "id", courses.get(i).getID());
			
			ResultSet rs = reader.getReadResults();
			
			try {
				rs.next();
				
				getToSend().add(new Course(false, rs.getInt(1), rs.getInt(2), rs.getString(3), 
						rs.getBoolean(4)));
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	protected void readSubmissionTable(Submission submission) {
		
		DBReader reader = new DBReader(submissionMessage, "assign_id", submission.getAssignID());
		
		ResultSet rs = reader.getReadResults();
		
		try 
		{
			while(rs.next())
			{
				getToSend().add(new Submission(false, rs.getInt(1), rs.getInt(2), rs.getInt(3), 
						rs.getString(5), rs.getInt(6), rs.getString(7)));
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void readCourseTable(Course course) {
		// TODO Auto-generated method stub
		
	}


}