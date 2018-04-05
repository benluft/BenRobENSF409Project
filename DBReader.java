package backEnd;

import java.sql.ResultSet;

class DBReader extends ReaderWorker
{
	
	private ResultSet readResults;

	DBReader(String tableName, String queryKey, Object searchFor) 
	{
		
		super(tableName, queryKey);
		
		readResults = super.queryDatabase(searchFor);
		
	}
	
	DBReader(String tableName, String queryKeyFirst, Object searchForFirst, String queryKeySecond, Object searchForSecond) 
	{
		
		super(tableName, queryKeyFirst, queryKeySecond);
		
		readResults = super.queryDatabase(searchForFirst, searchForSecond);
		
	}
	
    public ResultSet getReadResults()
    {
    	return readResults;
    }
	
	
	
}