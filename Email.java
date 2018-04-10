package sharedData;

public class Email extends SocketMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -187407964198623735L;
	
	private String messageContents;
	private int courseID;
	
	public Email(int messageID, boolean isQuerry, int courseID, String messageContents) 
	{
		super(isQuerry, messageID);
		setMessageType();

		this.courseID = courseID;
		this.messageContents = messageContents;
	}
	
	public String getMessageContents() {
		return messageContents;
	}

	public int getCourseID() {
		return courseID;
	}

	@Override
	public void setMessageType() 
	{
		messageType = emailMessage;
	}
}