/*
 * Ryan Schulz
 * COEN 280
 * Project 3
 * Due 5/31/2019
 */

package populate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertGenres {

	public static void ReadInsert(Connection con) throws SQLException { 
		 
		Statement stmt = con.createStatement(); 
		

		
		 System.out.println("Inserting Genres Data"); 
		
		//dat file
		File file = new File("assets/datafiles/movie_genres.dat");
		Scanner sc = null;
		 try {
			 sc = new Scanner(file);
	   // Check if there is another line of input
			 String str = sc.nextLine();
			 while(sc.hasNextLine()){
				 str = sc.nextLine();
				 parseLine(str, stmt);
			 }
	   
		 } catch (IOException  exp) {
			 // TODO Auto-generated catch block
			 exp.printStackTrace();
		 }
	  
		 sc.close();
		 stmt.close();
		 System.out.println("Finished Genres Data\n");
	}
 
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String movieId, country, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		
		// Check if there is another line of input
		movieId = sc.next();
		country = sc.next();
		buf = "insert into GENRES values (" + movieId + ", '" + country + "')";
		
		//System.out.println(buf);
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}