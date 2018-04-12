package sharedData;

/**
 * Email message to be sent over the socket
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
public class Email extends SocketMessage
{

	private static final long serialVersionUID = -187407964198623735L;
	
	/**
	 * This contains the body of the email
	 */
	private String messageContents;
	/**
	 * This is the subject of the email
	 */
	private String messageSubject;
	/**
	 * ID of the course that the prof has to send the email to 
	 */
	private int courseID;
	
	/**
	 * Construct email with all data members filled
	 * 
	 * @param messageID
	 * @param isQuerry
	 * @param courseID
	 * @param messageContents
	 * @param messageSubject
	 */
	public Email(int messageID, boolean isQuerry, int courseID, String messageContents, String messageSubject) 
	{
		super(isQuerry, messageID);
		setMessageType();

		this.courseID = courseID;
		this.messageSubject = messageSubject;
		this.messageContents = messageContents;
	}
	

	public String getMessageContents() 
	{
		return messageContents;
	}


	public int getCourseID() 
	{
		return courseID;
	}
	

	public String getMessageSubject()
	{
		return messageSubject;
	}

	/* (non-Javadoc)
	 * @see sharedData.SocketMessage#setMessageType()
	 */
	@Override
	public void setMessageType() 
	{
		messageType = emailMessage;
	}
}