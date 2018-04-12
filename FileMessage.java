package sharedData;

/**
 * Contains the data to send a file over a socket
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
public class FileMessage extends SocketMessage
{

	private static final long serialVersionUID = -1479413585259609557L;
	
	/**
	 * The data in a file
	 */
	private byte[] fileData;

	
	/**
	 * Creates a file message with all data members filled
	 * 
	 * @param isQuerry
	 * @param ID
	 * @param fileData
	 */
	public FileMessage(boolean isQuerry,int ID, byte[] fileData) 
	{
		super(isQuerry, ID);
		setMessageType();
		this.fileData = fileData;
	}

	/* (non-Javadoc)
	 * @see sharedData.SocketMessage#setMessageType()
	 */
	@Override
	public void setMessageType() 
	{
		messageType = pdfMessage;
	}

	public byte[] getFileData() {
		return fileData;
	}


}