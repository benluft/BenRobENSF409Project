package backEnd;

import java.sql.ResultSet;

/**
 * 
 * This class is used to read from the database.  It can either search by one key or two keys
 * Has a method to return this result set from the database
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
class DBReader extends ReaderWorker
{
	
	/**
	 * The result set from the search of the database
	 */
	private ResultSet readResults;

	/**
	 * Gets a read result from a search with one criteria
	 * 
	 * @param tableName name of the table to search
	 * @param queryKey is the column that will be searched
	 * @param searchFor is the object to search for
	 */
	DBReader(String tableName, String queryKey, Object searchFor) 
	{
		
		super(tableName, queryKey);
		
		readResults = super.queryDatabase(searchFor);
		
	}
	
	/**
	 * Gets a read result from a search with two criteria
	 * 
	 * @param tableName name of the table to search
	 * @param queryKey is the column that will be searched
	 * @param searchFor is the object to search for
	 * @param queryKeySecond is the second column that will be searched
	 * @param searchForSecond is the second object to search for
	 */
	DBReader(String tableName, String queryKeyFirst, Object searchForFirst, String queryKeySecond, Object searchForSecond) 
	{
		
		super(tableName, queryKeyFirst, queryKeySecond);
		
		readResults = super.queryDatabase(searchForFirst, searchForSecond);
		
	}
	
    /**
     * Gets the results of the search
     * 
     * @return resultset with the results
     */
    public ResultSet getReadResults()
    {
    	return readResults;
    }
	
	
	
}