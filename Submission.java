package sharedData;

class Submission extends SocketMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5925131203286336978L;
	
	public Submission(boolean isQuerry, int ID) 
	{
		super(isQuerry, ID);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setMessageType() 
	{
		messageType = submissionMessage;
	}
}