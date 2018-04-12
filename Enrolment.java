package sharedData;

/**
 * A list of enrolled students and the courses they are enrolled in
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
public class Enrolment extends SocketMessage
{

	private static final long serialVersionUID = 7098152461325695995L;
	
	/**
	 * The Id of the enrolled student
	 */
	private int studentID;
	/**
	 * The id of the course they are enrolled in
	 */
	private int courseID;
	

	public int getStudentID() {
		return studentID;
	}


	public int getCourseID() {
		return courseID;
	}

	/**
	 * Makes an enrollment object with all data members initialized
	 * 
	 * @param isQuerry
	 * @param ID
	 * @param studentID
	 * @param courseID
	 */
	public Enrolment(boolean isQuerry, int ID, int studentID, int courseID) 
	{
		super(isQuerry, ID);
		setMessageType();
		
		this.courseID = courseID;
		this.studentID = studentID;
	}
	
	/* (non-Javadoc)
	 * @see sharedData.SocketMessage#setMessageType()
	 */
	@Override
	public void setMessageType() 
	{
		messageType = enrolMessage;
	}
	
}