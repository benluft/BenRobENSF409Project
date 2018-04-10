package sharedData;

public class Submission extends SocketMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5925131203286336978L;
	
	private int assignID;
	private int studentID;
	private String title;
	private int grade;
	private String comments;
	
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

	@Override
	public void setMessageType() 
	{
		messageType = submissionMessage;
	}
}