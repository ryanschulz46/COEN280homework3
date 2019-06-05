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

public class InsertUserRated {

	public static void ReadInsert(Connection con) throws SQLException { 
		 
		Statement stmt = con.createStatement(); 
		

		 System.out.println("Inserting User Rated Movie Data."); 
		
		//dat file
		File file = new File("assets/datafiles/user_ratedmovies.dat");
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
		 System.out.println("Finished User Rated Movie Data\n");
	}
 
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String userId, movieId, rating, day, month, year, hour, minute, second, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		
		// Check if there is another line of input
		userId = sc.next();
		movieId = sc.next();
		rating = sc.next();
		day = sc.next();
		month = sc.next();
		year = sc.next();
		hour = sc.next();
		minute = sc.next();
		second = sc.next();

		
		
		buf = "insert into USER_RATED_MOVIES values (" + userId + ", " + movieId + ", " + rating + ", " 
		+ "TO_DATE('"+ year +"-" +month+ "-" + day+ " " + hour+":"+minute+":"+second+ "','YYYY-MM-DD HH24:MI:SS'))";
		
		//System.out.println(buf);
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}