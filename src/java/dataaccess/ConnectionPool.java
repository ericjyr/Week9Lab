package dataaccess;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.*;

//class defines connection pool 
public class ConnectionPool {
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;

    //private constructors imply a singleton pattern 
    //this ensures only a single instance of this object
    private ConnectionPool() {
        try {
            //provides access to context.xml
            InitialContext ic = new InitialContext(); 
            //looks for resource within context.xml that has corresponding name
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/userdb");
        } catch (NamingException e) {
            System.out.println(e);
        }
    }
    
    
    public static synchronized ConnectionPool getInstance() {
        //if object is already initialized within program it is returned rather
        //than making another instance
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
