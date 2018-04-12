package backEnd;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sharedData.FileMessage;
import sharedData.SocketMessage;

/**
 * Used to read a pdf from the server, and send it to the client
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
class PDFReader
{
	
	/**
	 * Contains the file data
	 */
	private FileMessage fileToSend;
	
	/**
	 * Gets the file that should be sent to the client
	 * 
	 * @return File message to send
	 */
	public FileMessage getFileToSend() {
		return fileToSend;
	}

	/**
	 * Reads the PDF and sends it to the client
	 * 
	 * @param path that the file can be found at
	 */
	public PDFReader(String path)
	{
		File selectedFile = new File(path);
		
		System.out.println(path);
		
		long length = selectedFile.length();
		byte[] content = new byte[(int) length]; // Converting Long to Int
		try 
		{
			FileInputStream fis = new FileInputStream(selectedFile);
			BufferedInputStream bos = new BufferedInputStream(fis);
			bos.read(content, 0, (int)length);
			
			fileToSend = new FileMessage(false, 0, content);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}