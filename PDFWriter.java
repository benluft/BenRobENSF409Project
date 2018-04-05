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

class PDFWriter implements MessageNameConstants
{
	private String pdfPath;
	
	PDFWriter(SocketMessage message)
	{
		FileMessage fileBytes = (FileMessage) message;
		
		DBReader reader = new DBReader(assignmentMessage.toLowerCase(), "path", fileBytes.getID());
		
		ResultSet rs = reader.getReadResults();
		
		try 
		{
			if(rs.next())
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
	
	private void downloadFile(byte[] contents)
	{
		File newFile = new File(pdfPath + fileBytes.getFileExtension());
		
		try{
			if(! newFile.exists())
			{
				newFile.createNewFile();
			}
			
			FileOutputStream writer = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(writer);
			
			bos.write(content);
			bos.close();
		} 
		catch(IOException e){
			e.printStackTrace();
		}

	}
}