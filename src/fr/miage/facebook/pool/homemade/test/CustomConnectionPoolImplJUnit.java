package fr.miage.facebook.pool.homemade.test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
 

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.miage.facebook.pool.homemade.CustomConnectionPoolImpl;
import fr.miage.facebook.pool.homemade.ICustomConnectionPool;
 
/**
 * This class contains unit tests for connection pool.
 * @author Mohamed
 * 
 */
public class CustomConnectionPoolImplJUnit {
 
    private Logger log = Logger.getLogger(getClass());
 
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/facebook";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    private static final int MAX_CONNECTIONS = 5;
    private static final int INITIAL_CONNECTIONS = 2;
    private static final boolean WAIT_IF_BUSY = true;
 
    private ICustomConnectionPool pool = null;
 
    @Before
    public void setUp() throws SQLException {
        pool = new CustomConnectionPoolImpl(DRIVER, URL, USER_NAME, PASSWORD,
                INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
        log.debug("Connection pool created" + pool);
    }
 
    @After
    public void destroy() {
        pool = null;
    }
 
    @Test(expected = IllegalArgumentException.class)
    public void testNullDriver() throws SQLException {
        pool = new CustomConnectionPoolImpl(null, URL, USER_NAME, PASSWORD,
                INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }
 
    @Test(expected = SQLException.class)
    public void testDriverNotFound() throws SQLException {
        pool = new CustomConnectionPoolImpl("some.funky.driver", URL,
                USER_NAME, PASSWORD, INITIAL_CONNECTIONS, MAX_CONNECTIONS,
                WAIT_IF_BUSY);
    }
 
    @Test(expected = IllegalArgumentException.class)
    public void testNullUrl() throws SQLException {
        pool = new CustomConnectionPoolImpl(DRIVER, null, USER_NAME, PASSWORD,
                INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }
 
    @Test(expected = IllegalArgumentException.class)
    public void testNullUserName() throws SQLException {
        pool = new CustomConnectionPoolImpl(DRIVER, URL, null, PASSWORD,
                INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }
 
    @Test(expected = IllegalArgumentException.class)
    public void testNullPassword() throws SQLException {
        pool = new CustomConnectionPoolImpl(DRIVER, URL, USER_NAME, null,
                INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }
 
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMaxConnections() throws SQLException {
        pool = new CustomConnectionPoolImpl(DRIVER, URL, USER_NAME, PASSWORD,
                INITIAL_CONNECTIONS, -2, WAIT_IF_BUSY);
    }
 
    @Test
    public void testPoolInitialization() throws SQLException {
        assertTrue("unexpected size of pool",
                INITIAL_CONNECTIONS == pool.getNumberOfAvailableConnections());
    }
 
    @Test
    public void testGetConnection() throws SQLException {
        pool.getConnection();
        assertTrue(pool.getNumberOfAvailableConnections() == 1);
        assertTrue(pool.getNumberOfBusyConnections() == 1);
    }
 
    @Test
    public void testReleaseConnection() throws SQLException {
        Connection conn = pool.getConnection();
        assertTrue(pool.getNumberOfBusyConnections() == 1);
        pool.releaseConnection(conn);
        assertTrue(pool.getNumberOfBusyConnections() == 0);
    }
 
    @Test
    public void testCloseAllConnections() throws SQLException {
        pool.getConnection();
        assertTrue(pool.getNumberOfBusyConnections() == 1);
        assertTrue(pool.getNumberOfAvailableConnections() == 1);
        pool.closeAllConnections();
        assertTrue(pool.getNumberOfBusyConnections() == 0);
        assertTrue(pool.getNumberOfAvailableConnections() == 0);
    }
}