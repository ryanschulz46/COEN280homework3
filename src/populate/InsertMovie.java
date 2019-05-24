package populate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import org.apache.commons.lang3.math.NumberUtils;


public class InsertMovie {

	public static void ReadInsert(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		 System.out.println("Inserting Movie Data"); 
		
		//dat file
		File file = new File("assets/datafiles/movies.dat");
		Scanner sc = null;
		 try {
			 sc = new Scanner(file);
	   // Check if there is another line of input
			 String str = sc.nextLine(); //skip header
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
		 System.out.println("Finished Movie Data");
	}
 
	
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String movieId, title, year, avg_critic_rating, num_critic_rating, buf;
		String doubleapost = "''";
		String doublequotes = "\"";
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
		
		title = title.replaceAll(doubleapost, doublequotes); //remove any special values from sql
		title = title.replaceAll("'", doubleapost);
		
		if(!NumberUtils.isCreatable(avg_critic_rating)){ //ensures rating is a number
			avg_critic_rating = "0";
		}
		if(!NumberUtils.isCreatable(num_critic_rating)){ //ensures rating is a number
			num_critic_rating = "0";
		}
		
		buf = "insert into MOVIE values (" + movieId + ", '" + title + "', " + year + ", " + avg_critic_rating + ", " + num_critic_rating + ")";
		//System.out.println(buf); //for testing
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}

