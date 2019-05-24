package populate;

import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;

/* All insert statements were crafted with insight for this guide:
 * https://netjs.blogspot.com/2016/06/reading-delimited-file-in-java-using-scanner.html
 */
public class Populate {
	 
	public static void main(String[] args) {
		
        Connection con = null; 
        try { 
            con = DBConnectionManager.openConnection();
           // InsertMovie.ReadInsert(con);
            //InsertGenres.ReadInsert(con);
           // InsertTagMap.ReadInsert(con);
           // InsertMovieTags.ReadInsert(con);
            InsertOriginCountry.ReadInsert(con);
            InsertFilmCountry filmCountry = new InsertFilmCountry(con);
            System.out.println("All files were successfully added to the database.");
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
