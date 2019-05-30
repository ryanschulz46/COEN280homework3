package populate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertMovieDirectors {

	public static void ReadInsert(Connection con) throws SQLException { 
		 
		Statement stmt = con.createStatement(); 
		

		
		 System.out.println("Inserting Movie Director Data."); 
		
		//dat file
		File file = new File("assets/datafiles/movie_directors.dat");
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
		 System.out.println("Finished Movie Director Data\n");
	}
 
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String movieId, directId, directName, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		
		// Check if there is another line of input
		movieId = sc.next();
		directId = sc.next();
		directName = sc.next();
		if (directName.equals("") || directId.equals("")) {
			System.out.println("Error on directId '" + directId + "' in movieId '" + movieId + "'. Null name encountered. Avoiding");
			sc.close();
			return;
		}
		
		
		String doubleapost = "''";
		String doublequotes = "\"";
		directName = directName.replaceAll(doubleapost, doublequotes); //remove any special values from sql
		directName = directName.replaceAll("'", doubleapost);
		
		buf = "insert into MOVIE_DIRECTORS values (" + movieId + ", '" + directId + "', '" + directName + "')";
		
		//System.out.println(buf);
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}