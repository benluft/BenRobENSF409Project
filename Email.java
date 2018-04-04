package sharedData;

class Email extends Message
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -187407964198623735L;
	
	public Email(int messageID, boolean isQuerry) 
	{
		super(messageID, isQuerry);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setMessageType() 
	{
		messageType = emailMessage;
	}
}