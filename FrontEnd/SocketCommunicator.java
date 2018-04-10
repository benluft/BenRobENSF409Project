package frontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class SocketCommunicator
{
	private ObjectInputStream reader;
	
	private ObjectOutputStream writer;
	
	public SocketCommunicator(ObjectInputStream reader, ObjectOutputStream writer) 
	{
		this.reader = reader;
		this.writer = writer;
	}
	
	public void write(Object obj)
	{
		try 
		{
			writer.writeObject(obj);
			writer.flush();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Object read()
	{
		try 
		{
			return reader.readObject();
		} 
		catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}