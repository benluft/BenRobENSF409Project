package sharedData;

class PDF extends Message
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1479413585259609557L;
	
	public PDF(int messageID, boolean isQuerry) 
	{
		super(messageID, isQuerry);
		setMessageType();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setMessageType() 
	{
		messageType = pdfMessage;
	}
}