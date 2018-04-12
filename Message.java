package sharedData;

import java.io.Serializable;

/**
 * Superclass that can be sent over a socket.  What kind of message is 
 * sent over the socket is in the message type field
 * 
 * @author Ben
 *
 */
public class SocketMessage implements Serializable, MessageNameConstants
{

	
	private static final long serialVersionUID = -5633187010151237388L;
	
	/**
	 * The type of the subclass
	 */
	String messageType;
	
	/**
	 * Where this message is a query for the database or an insertion
	 */
	boolean isQuerry;
	
	/**
	 * ID of the data
	 */
	int ID;
	
	/**
	 * Initializes the data members
	 * 
	 * @param isQuerry
	 * @param ID
	 */
	public SocketMessage(boolean isQuerry, int ID)
	{
		this.isQuerry = isQuerry;
		this.ID = ID;
	}


	public String getMessageType() {
		return messageType;
	}


	public void setMessageType() {
		messageType = emptyMessage;
	}


	public boolean getIsQuerry() {
		return isQuerry;
	}


	public void setQuerry(boolean isQuerry) {
		this.isQuerry = isQuerry;
	}
	

	public int getID()
	{
		return ID;
	}
	
}