package populate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;

public class InsertFilmCountry {
	
	private LinkedList<String> repeatCheck;
	private Connection con;
	private Statement stmt;
	
	public InsertFilmCountry(Connection con) throws SQLException {
		this.con = con;
		repeatCheck = new LinkedList<String>();
		ReadInsert();
	}
	
	public void ReadInsert() throws SQLException { 
		 System.out.println("Inserting Film Country Data. Assuming data is sorted by movie_Id"); 
		 Statement stmt = con.createStatement(); 
		//dat file
		File file = new File("assets/datafiles/movie_locations.dat");
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
		 System.out.println("Finished Film Country Data");
	}
 
	private void parseLine(String str, Statement stmt) throws SQLException{
		String movieId, country, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		// Check if there is another line of input
		movieId = sc.next();
		
		if(!repeatCheck.contains(movieId)) {
			repeatCheck.clear(); //clear hashmap as its a new movie
			repeatCheck.add(movieId);
		}
		
		try {
			country = sc.next();
			if(country.equals("")) {
				sc.close();
				return;
			}
			buf = "insert into FILM_COUNTRY values (" + movieId + ", '" + country + "')";
		} catch(Exception e) {
			sc.close();
			return;
		}
		
		if(repeatCheck.contains(country)) {
			sc.close();
			return;
		}
		else {
			repeatCheck.add(country); //ADD TO LIST
		}
		
		
		
		//System.out.println(buf);
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}