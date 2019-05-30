package populate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertUserTagged {

	public static void ReadInsert(Connection con) throws SQLException { 
		 
		Statement stmt = con.createStatement(); 
		

		
		 System.out.println("Inserting User Tagged Movie Data."); 
		
		//dat file
		File file = new File("assets/datafiles/user_taggedmovies.dat");
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
		 System.out.println("Finished User Tagged Movie Data\n");
	}
 
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String userId, movieId, tagId, day, month, year, hour, minute, second, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		
		// Check if there is another line of input
		userId = sc.next();
		movieId = sc.next();
		tagId = sc.next();
		day = sc.next();
		month = sc.next();
		year = sc.next();
		hour = sc.next();
		minute = sc.next();
		second = sc.next();

		
		buf = "insert into USER_TAGGED_MOVIES values (" + userId + ", " + movieId + ", " + tagId + ", " 
				+ "TO_DATE('"+ year +"-" +month+ "-" + day+ " " + hour+":"+minute+":"+second+ "','YYYY-MM-DD HH24:MI:SS'))";
				
		//System.out.println(buf);
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}