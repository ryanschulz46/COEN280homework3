package populate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertMovie {

	public static void ReadInsert(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		 System.out.println("Inserting Movie Data ..."); 
		
		//dat file
		File file = new File("assets/datafiles/movies.dat");
		Scanner sc = null;
		 try {
			 sc = new Scanner(file);
	   // Check if there is another line of input
			 String str = sc.nextLine(); //skip header
			 int i=0;
			 while(i < 5){
				 str = sc.nextLine();
				 parseLine(str, stmt);
				 i++;
			 }
	   
		 } catch (IOException  exp) {
			 // TODO Auto-generated catch block
			 exp.printStackTrace();
		 }
	  
		 sc.close();
		 stmt.close();
		 System.out.println("Finished adding movies");
	}
 
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String movieId, title, year, avg_critic_rating, num_critic_rating, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		// Check if there is another line of input
		movieId = sc.next(); //some missing ID's?
		title = sc.next();
		sc.next(); //skip imdbID
		sc.next(); //skip spanish title
		sc.next(); //skip imdbPictureURL
		year = sc.next();
		sc.next(); //skip rtID
		avg_critic_rating = sc.next();
		num_critic_rating = sc.next();
		
		buf = "insert into MOVIES values (" + movieId + ", '" + title + "', " + year + ", " + avg_critic_rating + ", " + num_critic_rating + ")";
		System.out.println(buf);
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}

