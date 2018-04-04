package backEnd;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class Worker implements Runnable
{
	
	private ObjectInputStream reader;
	
	private ObjectOutputStream writer;

	Worker(ObjectOutputStream writer, ObjectInputStream reader)
	{
		this.reader = reader;
		this.writer = writer;
	}
	
	@Override
	public void run() 
	{
			
	}
	
}