package sharedData;

class User extends Message
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7649437873694239705L;
	
	public User(int messageID, boolean isQuerry) 
	{
		super(messageID, isQuerry);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setMessageType() 
	{
		messageType = userMessage;
	}
}