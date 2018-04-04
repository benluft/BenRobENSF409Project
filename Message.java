package sharedData;

import java.io.Serializable;

public class Message implements Serializable, MessageNameConstants
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5633187010151237388L;
	
	String messageType;
	
	boolean isQuerry;
	
	int messageId;
	
	public Message(int messageId, boolean isQuerry)
	{
		this.messageId = messageId;
		this.isQuerry = isQuerry;
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

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	
}