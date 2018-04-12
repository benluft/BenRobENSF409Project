package sharedData;

/**
 * Assignment to be sent over the socket
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
public class Assignment extends SocketMessage
{


	private static final long serialVersionUID = -1884683341633762559L;
	
	/**
	 * ID of assign
	 */
	private int courseID;
	/**
	 * Title of assign
	 */
	private String title;
	/**
	 * Whether the assign is active
	 */
	private boolean active;
	/**
	 * Due dat of the assign
	 */
	private String dueDate;
	
	/* (non-Javadoc)
	 * @see sharedData.SocketMessage#setMessageType()
	 */
	@Override
	public void setMessageType() 
	{	
		messageType = assignmentMessage;
	}
	
	/**
	 * Initializes all data members
	 * 
	 * @param isQuerry
	 * @param ID
	 * @param courseID
	 * @param title
	 * @param active
	 * @param dueDate
	 */
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