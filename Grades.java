package sharedData;

class Grades extends SocketMessage
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
	
	public Grades(boolean isQuerry, int ID) 
	{
		super(isQuerry, ID);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
}