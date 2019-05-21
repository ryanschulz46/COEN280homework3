package populate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;


public class DBConnectionManager {

	/*public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException err) {
			err.printStackTrace();
		}
	}
	
	private Connection openConnection() throws SQLException, ClassNotFoundException { 
		// Load the Oracle database driver 
           DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 
    
           /* 
           Here is the information needed when connecting to a database 
           server. These values are now hard-coded in the program. In 
           general, they should be stored in some configuration file and 
           read at run time. 
           //*//* 
           String host = "localhost"; 
           String port = "1521"; 
           String dbName = "coen280"; 
           String userName = "temp"; 
           String password = "temp585"; 
    
           // Construct the JDBC URL 
           String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName; 
           return DriverManager.getConnection(dbURL, userName, password); 
	}
	*/
}
