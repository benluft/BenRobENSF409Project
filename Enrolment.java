package sharedData;

class Enrolment extends Message
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7098152461325695995L;
	
	public Enrolment(int messageID, boolean isQuerry) 
	{
		super(messageID, isQuerry);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setMessageType() 
	{
		messageType = enrolMessage;
	}
	
}