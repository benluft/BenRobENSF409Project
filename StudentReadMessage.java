package backEnd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.interceptors.ResultSetScannerInterceptor;

import sharedData.Assignment;
import sharedData.Course;
import sharedData.Enrolment;
import sharedData.MessageNameConstants;
import sharedData.SocketMessage;
import sharedData.User;

class StudentReadMessage extends StudentAndProfDBReader implements MessageNameConstants
{
	Vector<SocketMessage> messageToSend; 
	
	public StudentReadMessage(SocketMessage message)
	{
		super(message);
		
		messageToSend = new Vector<SocketMessage>();

	}

	@Override
	protected void readAssignmentTable(Assignment assign) {
		// TODO Auto-generated method stub
		
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
			reader = new DBReader(courseMessage, "id", courses.get(i).getID(), "active", true);
			
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
	protected void readCourseTable(Course course) {
		// TODO Auto-generated method stub
		
	}
}