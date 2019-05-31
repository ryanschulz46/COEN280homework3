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

public class InsertMovieActors {

	public static void ReadInsert(Connection con) throws SQLException { 
		 
		Statement stmt = con.createStatement(); 
		

		
		 System.out.println("Inserting Movie Actor Data."); 
		
		//dat file
		File file = new File("assets/datafiles/movie_actors.dat");
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
		 System.out.println("Finished Movie Actor Data\n");
	}
 
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String movieId, actorId, actorName, ranking, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		
		// Check if there is another line of input
		movieId = sc.next();
		actorId = sc.next();
		actorName = sc.next();
		ranking = sc.next();
		if (actorName.equals("") || actorId.equals("")) {
			System.out.println("Error on actorId '" + actorId + "' in movieId '" + movieId + "'. Null name encountered. Avoiding");
			sc.close();
			return;
		}
		
		//escape ' for sql
		String doubleapost = "''";
		String doublequotes = "\"";
		actorName = actorName.replaceAll(doubleapost, doublequotes); //remove any special values from sql
		actorName = actorName.replaceAll("'", doubleapost);
		
		buf = "insert into MOVIE_ACTORS values (" + movieId + ", '" + actorId + "', '" + actorName + "', " 
		+ ranking + ")";
		
		//System.out.println(buf);
		try {
			stmt.executeUpdate(buf);
			sc.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.out.println("\n" + buf);
			sc.close();
		}
	}
	
}