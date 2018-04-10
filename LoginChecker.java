package backEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import sharedData.*;

class LoginChecker
{
	
	ObjectInputStream reader;
	ObjectOutputStream writer;
	
	Vector<User> toSend = null;
	
	public LoginChecker(ObjectInputStream reader, ObjectOutputStream writer)
	{
		this.reader = reader;
		this.writer = writer;
		
		checkLogin();
	}
	
	private void checkLogin()
	{
		boolean loggedIn = false;
		
		while(!loggedIn)
		{
			toSend = new Vector<User>();
			SocketMessage message;
			try {
				message = (SocketMessage) reader.readObject();
				loggedIn = attemptLogin((User) message);
				writer.writeObject(toSend);
			} 
			catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	private boolean attemptLogin(User user)
	{
		DBReader dbReader = new DBReader(MessageNameConstants.userMessage.toLowerCase(), 
				"id", user.getID(),"password", user.getPassword());
		
		ResultSet rs = dbReader.getReadResults();
		
		try 
		{
			if(rs.next())
			{
				toSend.add(new User(true, rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6)));
				return true;
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		toSend.add(new User(false, 0, null, null, null, null, null));
		return false;
	}
}