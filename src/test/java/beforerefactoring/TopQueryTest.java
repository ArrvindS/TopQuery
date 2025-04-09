package beforerefactoring;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TopQueryTest {
    private final String query = "select * from employee";
    private TopQuery topQuery;
    private int maxResults = 50;

    @Before
    public void setUp() throws Exception {
        topQuery = new TopQuery();
        topQuery.setMaxResults(maxResults);
    }

    @Test
    public void createsAccessTopQuery() {
        topQuery.setDbType(TopQuery.DB_ACCESS);
        String expectedQuery = "SELECT TOP " + maxResults + " * FROM (" + query + ")";
        String actualQuery = topQuery.query(query);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void createsSybaseTopQuery() {
        topQuery.setDbType(TopQuery.DB_SQL_SERVER);
        String expectedQuery = "SELECT TOP " + maxResults + " * FROM EMPLOYEE";
        String actualQuery = topQuery.query(query);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void createsSQLServerTopQuery() {
        topQuery.setDbType(TopQuery.DB_SYBASE);
        String expectedQuery = "SELECT TOP " + maxResults + " * FROM EMPLOYEE";
        String actualQuery = topQuery.query(query);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void createsOracleTopQuery() {
        topQuery.setDbType(TopQuery.DB_ORACLE);
        String expectedQuery = "SELECT * FROM (" + query + ") WHERE rownum <= " + maxResults;
        String actualQuery = topQuery.query(query);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void createsOtherTopQuery() {
        topQuery.setDbType(TopQuery.DB_OTHER);
        String actualQuery = topQuery.query(query);
        assertEquals(query, actualQuery);
    }
}