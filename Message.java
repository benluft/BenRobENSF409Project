package sharedData;

import java.io.Serializable;

public class SocketMessage implements Serializable, MessageNameConstants
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5633187010151237388L;
	
	String messageType;
	
	boolean isQuerry;
	
	int ID;
	
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