package sharedData;

class Assignment extends Message
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1884683341633762559L;
	
	@Override
	public void setMessageType() 
	{	
		messageType = assignmentMessage;
	}
	
	public Assignment(int messageID, boolean isQuerry) 
	{
		super(messageID, isQuerry);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
	
}