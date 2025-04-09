package beforerefactoring;

public class TopQuery {
  public final static int DB_ACCESS = 1;
  public final static int DB_ORACLE = 2;
  public final static int DB_SQL_SERVER = 3;
  public final static int DB_SYBASE = 4;
  public final static int DB_OTHER = 5;
 
  private int dbType = -1;
 
  public static final int NO_MAXIMUM_LIMIT = -1;
  private int maxResults = NO_MAXIMUM_LIMIT;
 
  private boolean isValidDBType(int db) {
    switch(db) {
      case DB_ACCESS     : return true;
      case DB_ORACLE     : return true;
      case DB_SQL_SERVER : return true;
      case DB_SYBASE     : return true;
      case DB_OTHER      : return true;
                 default : return false;
    }
  }
 
  public void setMaxResults(int maxResults) {
    this.maxResults = (maxResults < 0) ? NO_MAXIMUM_LIMIT : maxResults;
  }
 
  public int getMaxResults() {
    return maxResults;
  }
 
  public int getDbType() {
    return dbType;
  }
 
  public void setDbType(int dbType) {
    if (isValidDBType(dbType))
      this.dbType = dbType;
  }
 
  public String query(String query) {
    switch(dbType) {
      case DB_ACCESS: return (maxResults == NO_MAXIMUM_LIMIT) ? query
                        : "SELECT TOP " + maxResults +
                          " * FROM (" + query + ")";
 
      case DB_SYBASE:
      case DB_SQL_SERVER: if (maxResults == NO_MAXIMUM_LIMIT)
                            return query;
                          else {
                            query = query.toUpperCase();
                            if (query.indexOf("SELECT ") != -1)
                              return query.replaceFirst("SELECT ",
                                       "SELECT TOP " + maxResults + " ");
                            else
                              return query;
                           }
 
      case DB_ORACLE: return (maxResults == NO_MAXIMUM_LIMIT) ? query
                        : "SELECT * FROM (" + query +
                          ") WHERE rownum <= " + maxResults;
 
      case DB_OTHER:
      default : return query;
    }
  }
}