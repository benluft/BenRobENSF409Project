package backEnd;

import java.util.Vector;

import sharedData.Assignment;
import sharedData.Course;
import sharedData.Enrolment;
import sharedData.MessageNameConstants;
import sharedData.SocketMessage;
import sharedData.Submission;
import sharedData.User;

abstract class StudentAndProfDBReader implements MessageNameConstants
{
private Vector<SocketMessage> toSend;
	
	public StudentAndProfDBReader(SocketMessage message)
	{
		setToSend(new Vector<SocketMessage>());
		
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
	
	abstract protected void readAssignmentTable(Assignment assign);
	abstract protected void readEnrolTable(Enrolment enrol);
	abstract protected void readUserTable(User user);
	abstract protected void readCourseTable(Course course);
	abstract protected void readSubmissionTable(Submission submission);
	abstract protected void readCourseTable(Vector<Course> courses);

	public Vector<SocketMessage> getToSend() {
		return toSend;
	}

	public void setToSend(Vector<SocketMessage> toSend) {
		this.toSend = toSend;
	}

	
}