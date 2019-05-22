package populate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertOriginCountry {

	public static void ReadInsert(Connection con) throws SQLException { 
		 
		Statement stmt = con.createStatement(); 
		 System.out.println("Inserting Origin Country Data"); 
		
		//dat file
		File file = new File("assets/datafiles/movie_countries.dat");
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
		 System.out.println("Finished Origin Country Data");
	}
 
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String movieId, country, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		// Check if there is another line of input
		movieId = sc.next();
		try {
			country = sc.next();
			buf = "insert into ORIGIN_COUNTRY values (" + movieId + ", '" + country + "')";
		} catch(Exception e) {
			buf = "insert into ORIGIN_COUNTRY values (" + movieId + ", NULL)";
		}
		
		//System.out.println(buf);
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}