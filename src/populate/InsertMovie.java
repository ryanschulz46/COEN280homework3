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
		 System.out.println("Finished Movie Data\n");
	}
 
	
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String movieId, title, year, avg_critic_rating, num_critic_rating;
		String top_critic_rating, num_top_critic, buf;
		String audience_rating, audience_num;

		String doubleapost = "''";
		String doublequotes = "\"";
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		
		movieId = sc.next(); //some missing ID's?
		title = sc.next();
		sc.next(); //skip imdbId
		sc.next();  //skip spanish title
		sc.next(); //skip picURL
		year = sc.next();
		sc.next();  //skip rtID
		avg_critic_rating = sc.next();
		num_critic_rating =  sc.next();
		sc.next(); //all has fresh
		sc.next(); //all has rotten
		sc.next(); // all critic score
		top_critic_rating =  sc.next();
		num_top_critic = sc.next();
		sc.next(); //top has fresh
		sc.next(); //top has rotten
		sc.next(); //top  all critic score
		audience_rating = sc.next();
		audience_num = sc.next();
		

		//escape ' for sql
		title = title.replaceAll(doubleapost, doublequotes); //remove any special values from sql
		title = title.replaceAll("'", doubleapost);
		
		//check if ratings are numbers, if not set to 0.
		if(!NumberUtils.isCreatable(avg_critic_rating)){ //ensures rating is a number
			avg_critic_rating = "0";
		}
		if(!NumberUtils.isCreatable(num_critic_rating)){ //ensures rating is a number
			num_critic_rating = "0";
		}
		if(!NumberUtils.isCreatable(top_critic_rating)){ //ensures rating is a number
			top_critic_rating = "0";
		}
		if(!NumberUtils.isCreatable(num_top_critic)){ //ensures rating is a number
			num_top_critic = "0";
		}
		if(!NumberUtils.isCreatable(audience_rating)){ //ensures rating is a number
			audience_rating = "0";
		}
		if(!NumberUtils.isCreatable(audience_num)){ //ensures rating is a number
			audience_num = "0";
		}
		
		
		
		int x = (int)((( Double.parseDouble(avg_critic_rating) + Double.parseDouble(top_critic_rating) + Double.parseDouble(audience_rating))/3)*100000);
		double avgA = ((double)x)/100000;
		
		int y = (int)((( Double.parseDouble(num_critic_rating) + Double.parseDouble(num_top_critic) + Double.parseDouble(audience_num))/3)*100000);
		double numA = ((double)y)/100000;
		
		
		x = (int)((((Double.parseDouble(avg_critic_rating)*Double.parseDouble(num_critic_rating)) + (Double.parseDouble(num_top_critic) * Double.parseDouble(top_critic_rating)) + (Double.parseDouble(audience_num) * Double.parseDouble(audience_rating)))/( Double.parseDouble(num_critic_rating) + Double.parseDouble(num_top_critic) + Double.parseDouble(audience_num)))*100000);
		double avgB = ((double)x)/100000;
		
		
		
		
		
		
		
		buf = "insert into MOVIE values (" + movieId + ", '" + title + "',  " + year + ", " + avg_critic_rating + ", " + num_critic_rating + ", " 
		+ avgA + ", " + numA + ", " + avgB + ")";
		//System.out.println(buf); //for testing
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}

