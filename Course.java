package sharedData;

/**
 * Course to be sent over the socket
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
public class Course extends SocketMessage
{
	
	/**
	 * ID of prof teaching the course
	 */
	private int profID;
	
	/**
	 * Name of the course
	 */
	private String name;
	
	/**
	 * Whether the course is active or not
	 */
	private boolean active;
	
	/**
	 * Initializes all the data members
	 * 
	 * @param isQuerry
	 * @param ID
	 * @param profID
	 * @param name
	 * @param active
	 */
	public Course(boolean isQuerry, int ID, int profID, String name, boolean active) 
	{
		super(isQuerry, ID);
		setMessageType();
		
		this.active = active;
		this.name = name;
		this.profID = profID;
	}
	
	
	public int getProfID() {
		return profID;
	}

	public String getName() {
		return name;
	}

	public boolean isActive() {
		return active;
	}
	

	public void setIsActive(boolean active)
	{
		this.active = active;
	}

	/* (non-Javadoc)
	 * @see sharedData.SocketMessage#setMessageType()
	 */
	@Override
	public void setMessageType() 
	{
		messageType = courseMessage;
	}
	
}