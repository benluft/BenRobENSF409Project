package sharedData;

public class Course extends SocketMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1467102052685004280L;
	
	private int profID;
	
	private String name;
	
	private boolean active;
	
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

	@Override
	public void setMessageType() 
	{
		messageType = courseMessage;
	}
	
}