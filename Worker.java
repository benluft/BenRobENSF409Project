package backEnd;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class Worker
{
	/**
	 * Connection to the database
	 */
	private Connection jdbc_connection;
	/**
	 * From left to right the address of the database, and the login and password for this database
	 */
	public String connectionInfo = "jdbc:mysql://localhost:3306/finalprojectdb",  
				  login          = "root",
				  password       = "bunnies";
	
	Worker()
	{
		// If this fails make sure your connectionInfo and login/password are correct
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getJdbc_connection() {
		return jdbc_connection;
	}
	
}