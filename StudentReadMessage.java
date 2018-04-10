package backEnd;

import java.util.Vector;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readUserTable(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readCourseTable(Course course) {
		// TODO Auto-generated method stub
		
	}
}