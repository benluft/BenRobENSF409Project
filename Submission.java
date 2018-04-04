package sharedData;

class Submission extends Message
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5925131203286336978L;
	
	public Submission(int messageID, boolean isQuerry) 
	{
		super(messageID, isQuerry);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setMessageType() 
	{
		messageType = submissionMessage;
	}
}