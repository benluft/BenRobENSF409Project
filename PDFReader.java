package backEnd;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sharedData.FileMessage;
import sharedData.SocketMessage;

class PDFReader
{
	
	private FileMessage fileToSend;
	
	public FileMessage getFileToSend() {
		return fileToSend;
	}

	public PDFReader(String path)
	{
		File selectedFile = new File(path);
		
		long length = selectedFile.length();
		byte[] content = new byte[(int) length]; // Converting Long to Int
		try 
		{
			FileInputStream fis = new FileInputStream(selectedFile);
			BufferedInputStream bos = new BufferedInputStream(fis);
			bos.read(content, 0, (int)length);
			
			FileMessage pdfToSend = new FileMessage(false, 0, content);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}