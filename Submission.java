package sharedData;

/**
 * Creates a submission message that can be sent over a socket
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
public class Submission extends SocketMessage
{

	private static final long serialVersionUID = 5925131203286336978L;
	
	/**
	 * The Id of the assignment the submission is for
	 */
	private int assignID;
	/**
	 * The Id of the student who submitted
	 */
	private int studentID;
	/**
	 * The title of the assignment
	 */
	private String title;
	/**
	 * The grade received on the assignment
	 */
	private int grade;
	/**
	 * Any comments on the assignment
	 */
	private String comments;
	
	/**
	 * Initializes all the data members
	 * 
	 * @param isQuerry
	 * @param ID
	 * @param assignID
	 * @param studentID
	 * @param title
	 * @param grade
	 * @param comments
	 */
	public Submission(boolean isQuerry, int ID, int assignID, int studentID, String title,
			int grade, String comments) 
	{
		super(isQuerry, ID);
		setMessageType();

		this.assignID = assignID;
		this.studentID = studentID;
		this.title = title;
		this.grade = grade;
		this.comments = comments;
	}
	
	
	public int getAssignID() {
		return assignID;
	}


	public int getStudentID() {
		return studentID;
	}


	public String getTitle() {
		return title;
	}


	public int getGrade() {
		return grade;
	}


	public String getComments() {
		return comments;
	}

	/* (non-Javadoc)
	 * @see sharedData.SocketMessage#setMessageType()
	 */
	@Override
	public void setMessageType() 
	{
		messageType = submissionMessage;
	}
}