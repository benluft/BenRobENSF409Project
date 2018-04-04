package sharedData;

class Course extends Message
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1467102052685004280L;
	
	public Course(int messageID, boolean isQuerry) 
	{
		super(messageID, isQuerry);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setMessageType() 
	{
		messageType = courseMessage;
	}
	
}