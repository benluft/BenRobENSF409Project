package sharedData;

public class Email extends SocketMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -187407964198623735L;
	
	public Email(int messageID, boolean isQuerry) 
	{
		super(isQuerry, messageID);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setMessageType() 
	{
		messageType = emailMessage;
	}
}