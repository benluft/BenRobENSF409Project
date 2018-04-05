package sharedData;

public class Assignment extends SocketMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1884683341633762559L;
	
	private int courseID;
	private String title;
	private boolean active;
	private String dueDate;
	
	@Override
	public void setMessageType() 
	{	
		messageType = assignmentMessage;
	}
	
	public Assignment(boolean isQuerry, int ID, int courseID, String title, boolean active, String dueDate) 
	{
		super(isQuerry, ID);
		setMessageType();
		
		this.title = title;
		this.courseID =courseID;
		this.active = active;
		this.dueDate = dueDate;

		
	}

	public int getCourseID() {
		return courseID;
	}

	public String getTitle() {
		return title;
	}

	public boolean isActive() {
		return active;
	}

	public String getDueDate() {
		return dueDate;
	}
	
}