package backEnd;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

abstract class ReaderWorker extends Worker
{

	String tableName;
	
	String queryKey;
	
	String queryKey2;
	
	ReaderWorker(String tableName, String queryKey) 
	{
		this.tableName = tableName;
		this.queryKey = queryKey;
	}
	
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

	ResultSet queryDatabase(Object searchKey)
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
	
	ResultSet queryDatabase(Object searchKey, Object searchKey2)
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