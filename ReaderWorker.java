package backEnd;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

/**
 * Superclass for the reader, provides a simple way to write to the database
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
abstract class ReaderWorker extends Worker
{

	/**
	 * The name of the table to search
	 */
	String tableName;
	
	/**
	 * The column to search
	 */
	String queryKey;
	
	/**
	 * The second column to search
	 */
	String queryKey2;
	
	/**
	 * Constructs for a single column query
	 * 
	 * @param tableName
	 * @param queryKey
	 */
	ReaderWorker(String tableName, String queryKey) 
	{
		this.tableName = tableName;
		this.queryKey = queryKey;
	}
	
	/**
	 * Constructs for a double column query
	 * 
	 * @param tableName
	 * @param queryKey
	 * @param queryKey2
	 */
	ReaderWorker(String tableName, String queryKey, String queryKey2) 
	{
		this.tableName = tableName;
		this.queryKey = queryKey;
		this.queryKey2 = queryKey2;
	}
	

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}

	/**
	 * 
	 * Querys the database for a specific key
	 * 
	 * @param searchKey the key to search for
	 * @return the result of the querry
	 */
	protected ResultSet queryDatabase(Object searchKey)
	{
		String sqlCommand = "SELECT * FROM " + tableName + " WHERE " + 	queryKey + "=?";
		
		try 
		{
			PreparedStatement statement = (PreparedStatement) super.getJdbc_connection().prepareStatement(sqlCommand);
			statement.setObject(1, searchKey);
			ResultSet returnFromQuery = statement.executeQuery();
			return returnFromQuery;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * Querys the database for two specific keys
	 * 
	 * @param searchKey the key to search for 
	 * @param searchKey2 is the second key to search for 
	 * @return the result of the query
	 */
	protected ResultSet queryDatabase(Object searchKey, Object searchKey2)
	{
		String sqlCommand = "SELECT * FROM " + tableName + " WHERE " + 	queryKey + "=?" + " AND " + queryKey2 +"=?" ;
		
		try 
		{
			PreparedStatement statement = (PreparedStatement) super.getJdbc_connection().prepareStatement(sqlCommand);
			statement.setObject(1, searchKey);
			statement.setObject(2, searchKey2);
			ResultSet returnFromQuery = statement.executeQuery();
			return returnFromQuery;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}