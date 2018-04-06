package sharedData;

public class FileMessage extends SocketMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1479413585259609557L;
	
	private byte[] fileData;

	
	public FileMessage(boolean isQuerry,int ID, byte[] fileData) 
	{
		super(isQuerry, ID);
		setMessageType();
		this.fileData = fileData;
	}

	@Override
	public void setMessageType() 
	{
		messageType = pdfMessage;
	}

	public byte[] getFileData() {
		return fileData;
	}


}