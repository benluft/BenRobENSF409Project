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
	
	Vector<User> user = null;
	
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
			user = new Vector<User>();
			SocketMessage message;
			try {
				message = (SocketMessage) reader.readObject();
				loggedIn = attemptLogin((User) message);
				writer.writeObject(user);
			} 
			catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	private boolean attemptLogin(User userRead)
	{
		DBReader dbReader = new DBReader(MessageNameConstants.userMessage.toLowerCase(), 
				"id", userRead.getID(),"password", userRead.getPassword());
		
		ResultSet rs = dbReader.getReadResults();
		
		try 
		{
			if(rs.next())
			{
				user.add(new User(true, rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6)));
				return true;
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		user.add(new User(false, 0, null, null, null, null, null));
		return false;
	}
	
	public Vector<User> getUser()
	{
		return user;
	}
}