package sharedData;

class Grades extends Message
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9212023025432246936L;
	
	@Override
	public void setMessageType() 
	{
		messageType = gradesMessage;
	}
	
	public Grades(int messageID, boolean isQuerry) 
	{
		super(messageID, isQuerry);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
}