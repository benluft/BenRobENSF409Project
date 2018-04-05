package sharedData;

public class FileMessage extends SocketMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1479413585259609557L;
	
	private byte[] fileData;
	private String fileExtension;
	
	public FileMessage(boolean isQuerry,int ID, byte[] fileData, String fileExtension) 
	{
		super(isQuerry, ID);
		setMessageType();
		this.fileData = fileData;
		this.fileExtension = fileExtension;
	}

	@Override
	public void setMessageType() 
	{
		messageType = pdfMessage;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public String getFileExtension() {
		return fileExtension;
	}

}