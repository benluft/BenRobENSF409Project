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
 * @author Ben
 *
 */
class PDFWriter implements MessageNameConstants
{
	/**
	 * 
	 */
	private String pdfPath;
	
	/**
	 * @param message
	 */
	PDFWriter(SocketMessage message)
	{
		FileMessage fileBytes = (FileMessage) message;
		
		DBReader reader = new DBReader(assignmentMessage.toLowerCase(), "path", fileBytes.getID());
		
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
		
		downloadFile(content);
	}
	
	/**
	 * @param contents
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