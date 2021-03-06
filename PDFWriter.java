package backEnd;

import sharedData.SocketMessage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import sharedData.FileMessage;
import sharedData.MessageNameConstants;

/**
 * Writes a PDF (Or other file) to a place on the server
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
class PDFWriter implements MessageNameConstants
{
	/**
	 * Is the path to put the file at
	 */
	private String pdfPath;
	
	/**
	 * Writes the PDF or other file to the server
	 * 
	 * @param message contains the data of the file
	 */
	PDFWriter(SocketMessage message, String messageType)
	{
		FileMessage fileBytes = (FileMessage) message;
		
		DBReader reader;
		
		if(messageType.equals("P"))
		{
			reader = new DBReader(assignmentMessage, "path", fileBytes.getID());
		}
		else
		{
			reader = new DBReader(submissionMessage, "path", fileBytes.getID());
		}

		
		ResultSet rs = reader.getReadResults();
		
		try 
		{
			while(rs.next())
			{
				pdfPath = rs.getString(4);
			}
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		byte[] content = fileBytes.getFileData();
		System.out.println(pdfPath + " is the pdf path");
		downloadFile(content);
		System.out.println("Got to end of PDF printing");
	}
	
	/**
	 * Saves the file
	 * 
	 * @param contents is the data of the file
	 */
	private void downloadFile(byte[] contents)
	{
		File newFile = new File(pdfPath);
		
		try{
			if(!newFile.exists())
			{
				newFile.createNewFile();
			}
			
			FileOutputStream writer = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(writer);
			
			bos.write(contents);
			bos.close();
		} 
		catch(IOException e){
			e.printStackTrace();
		}

	}
}