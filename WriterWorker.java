package backEnd;

import com.mysql.jdbc.Connection;

class WriterWorker extends Worker
{
	String tableName;
	
	String sqlCommand;

	public void appendSqlCommand(String toAppend) {
		sqlCommand += toAppend;
	}

	WriterWorker(String tableName)
	{
		this.tableName = tableName;
		insertIntoDatabase();
	}
	
	public void insertIntoDatabase()
	{
		sqlCommand = "INSERT INTO " + tableName + " VALUES (";
	}
	
	public String getSqlCommand()
	{
		return sqlCommand;
	}

}