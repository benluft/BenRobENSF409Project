package backEnd;

import java.util.Vector;

import sharedData.Assignment;
import sharedData.Course;
import sharedData.Enrolment;
import sharedData.MessageNameConstants;
import sharedData.SocketMessage;
import sharedData.Submission;
import sharedData.User;

/**
 * Is the superclass for any reader of the database
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
abstract class StudentAndProfDBReader implements MessageNameConstants
{
/**
 * Is what the server will send back to the client
 */
private Vector<SocketMessage> toSend;
	
	/**
	 * Decides on how to read the message based off the message type
	 * 
	 * @param message the received message
	 */
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
		
		if(message.getMessageType().equals(submissionMessage))
		{
			System.out.println("Message received is a submission");
			
			Submission submission = (Submission) message;
			
			readSubmissionTable(submission);
		}
	}
	
	/**
	 * Reads the assign table
	 * @param assign the message
	 */
	abstract protected void readAssignmentTable(Assignment assign);
	/**
	 * Reads the enrol table
	 * @param enrol the message
	 */
	abstract protected void readEnrolTable(Enrolment enrol);
	/**
	 * Reads the user table
	 * @param user the message
	 */
	abstract protected void readUserTable(User user);
	/**
	 * Reads the course table
	 * @param course the message
	 */
	abstract protected void readCourseTable(Course course);
	/**
	 * Reads the submission table
	 * @param submission the message
	 */
	abstract protected void readSubmissionTable(Submission submission);
	/**
	 * Reads the course table using a vector of couse messages
	 * @param course the messages
	 */
	abstract protected void readCourseTable(Vector<Course> courses);


	public Vector<SocketMessage> getToSend() {
		return toSend;
	}


	public void setToSend(Vector<SocketMessage> toSend) {
		this.toSend = toSend;
	}

	
}