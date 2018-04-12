package backEnd;

import org.omg.CORBA.Current;

import com.mysql.jdbc.Connection;

/**
 * Used to write to the database, mostly sets up the SQL connection and 
 * sets the SQL command
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
class WriterWorker extends Worker
{
	/**
	 * The name of the table 
	 */
	String tableName;
	
	/**
	 * The command that will be used to write to the database
	 */
	String sqlCommand;

	/**
	 * Adds a new string to the SQL command
	 * 
	 * @param toAppend what to add to the command
	 */
	public void appendSqlCommand(String toAppend) {
		sqlCommand += toAppend;
	}

	/**
	 * Constructs a sqlCommand
	 * 
	 * @param tableName
	 */
	WriterWorker(String tableName)
	{
		this.tableName = tableName;
		resetSqlCommand();
	}
	
	/**
	 * Gets the current SQL command
	 * 
	 * @return SQL command
	 */
	public String getSqlCommand()
	{
		return sqlCommand;
	}
	
	/**
	 * Resets the SQL command to the default
	 */
	public void resetSqlCommand()
	{
		sqlCommand = "INSERT INTO " + tableName + " VALUES (";
	}

}