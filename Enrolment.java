package sharedData;

public class Enrolment extends SocketMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7098152461325695995L;
	
	private int studentID;
	private int courseID;
	
	
	public int getStudentID() {
		return studentID;
	}

	public int getCourseID() {
		return courseID;
	}

	public Enrolment(boolean isQuerry, int ID, int studentID, int courseID) 
	{
		super(isQuerry, ID);
		setMessageType();
		
		this.courseID = courseID;
		this.studentID = studentID;
	}
	
	@Override
	public void setMessageType() 
	{
		messageType = enrolMessage;
	}
	
}