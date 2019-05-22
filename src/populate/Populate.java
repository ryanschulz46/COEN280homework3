package populate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Populate {
	 
	public static void main(String[] args) {
		
        Connection con = null; 
        try { 
            con = DBConnectionManager.openConnection();
            //InsertTagMap tagMap = new InsertTagMap(con); 
            InsertMovieTags.ReadInsert(con);
        } 
        catch (SQLException e) { 
            System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
        } 
        catch (ClassNotFoundException e) { 
            System.err.println("Cannot find the database driver"); 
        } 
        finally { 
            // Never forget to close database connection 
        	DBConnectionManager.closeConnection(con); 
        } 
		
		
	 }
	
	
	
}
