/*
 * Ryan Schulz
 * COEN 280
 * Project 3
 * Due 5/31/2019
 */

package hw3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnectionManager {
	
	
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException err) {
			err.printStackTrace();
		}
	}
	
	public static Connection openConnection() throws SQLException, ClassNotFoundException { 
		// Load the Oracle database driver 
           DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 
    

           String host = "localhost"; 
           String port = "1521"; 
           String dbName = "orcl"; 
           String userName = "scott"; 
           String password = "tiger"; 
    
           // Construct the JDBC URL 
           String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + "/" + dbName; 
           return DriverManager.getConnection(dbURL, userName, password); 
	}
	
}
