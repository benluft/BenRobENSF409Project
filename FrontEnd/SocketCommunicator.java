package frontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Used to communicate with the socket
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
class SocketCommunicator
{
	/**
	 * Reads the socket
	 */
	private ObjectInputStream reader;
	
	/**
	 * Writes to the socket
	 */
	private ObjectOutputStream writer;
	
	/**
	 * Constructs the data members
	 * 
	 * @param reader
	 * @param writer
	 */
	public SocketCommunicator(ObjectInputStream reader, ObjectOutputStream writer) 
	{
		this.reader = reader;
		this.writer = writer;
	}
	
	/**
	 * Writes an object to the socket, with a flush of the stream
	 * 
	 * @param obj what to write to the socket
	 */ 
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
	
	/**
	 * Reads from the socket
	 * 
	 * @return the Object read from the socket
	 */
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