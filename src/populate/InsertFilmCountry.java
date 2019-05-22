package populate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertFilmCountry {

	public static void ReadInsert(Connection con) throws SQLException { 
		 
		Statement stmt = con.createStatement(); 
		 System.out.println("Inserting Film Country Data ..."); 
		
		//dat file
		File file = new File("assets/datafiles/movie_locations.dat");
		Scanner sc = null;
		 try {
			 sc = new Scanner(file);
	   // Check if there is another line of input
			 String str = sc.nextLine();
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
		 System.out.println("Finished adding");
	}
 
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String movieId, country, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		// Check if there is another line of input
		while(sc.hasNext()){
			movieId = sc.next();
			country = sc.next();
			buf = "insert into FILM_COUNTRY values (" + movieId + ", '" + country + "')";
			System.out.println(buf);
			stmt.executeUpdate(buf);
		}
		sc.close();
	}
	
}