/*
 * Ryan Schulz
 * COEN 280
 * Project 3
 * Due 5/31/2019
 */

package populate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

import javax.swing.Timer;

/* All insert statements were crafted with insight for this guide:
 * https://netjs.blogspot.com/2016/06/reading-delimited-file-in-java-using-scanner.html
 */
public class Populate{

	public static void main(String[] args) {
		//dont run unless necessary as it takes a while!
		System.out.print("Type 'Yes' to run on only necessary, 'All' to run on all tables, anything else to cancel\nWhat do you want to do: ");
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
		sc.close();
		
		int insertMode;
		if (input.equals("Yes")) {
			insertMode = 0;
		}
		else if (input.equals("All")) {
			insertMode = 1;
		}
		else {
			System.out.println("Exiting");
			return;
		}
		
		
		Connection con = null; 
		
		try { 

			long t = System.currentTimeMillis();

	
			con = DBConnectionManager.openConnection();
			System.out.println("Connection opened\n");

			
			
			
			IndexManager.removeIndex(con); //remove index
			//DELETE FIRST
			Statement stmt = con.createStatement();
			System.out.println("Deleting all previous User Tagged Timestamp Data");
			stmt.executeQuery("DELETE USER_TAGGED_TIMESTAMP");
			System.out.println("Deleting all previous User Tagged Movie Data");
			stmt.executeQuery("DELETE USER_TAGGED_MOVIES");
			System.out.println("Deleting all previous User Rated Timestamp Data");
			stmt.executeQuery("DELETE USER_RATED_TIMESTAMP");
			System.out.println("Deleting all previous User Rated Movie Data");
			stmt.executeQuery("DELETE USER_RATED_MOVIES");
			System.out.println("Deleting all previous Movie Director Data");
			stmt.executeQuery("DELETE MOVIE_DIRECTORS");
			System.out.println("Deleting all previous Movie Actor Data");
			stmt.executeQuery("DELETE MOVIE_ACTORS");
			System.out.println("Deleting all previous Film Country Data");
			stmt.executeQuery("DELETE FILM_COUNTRY");
			System.out.println("Deleting all previous Origin Country Data");
			stmt.executeQuery("DELETE ORIGIN_COUNTRY");
			System.out.println("Deleting all previous Movie Tag Data");
			stmt.executeQuery("DELETE TAG_MOVIE_PAIR");
			System.out.println("Deleting all previous Tag Map Data");
			stmt.executeQuery("DELETE TAG_MAP");
			System.out.println("Deleting all previous Genres Data");
			stmt.executeQuery("DELETE GENRES");
			System.out.println("Deleting all previous Movie Data\n");
			stmt.executeQuery("DELETE MOVIE");
			
			
			
			//required for GUI
			InsertMovie.ReadInsert(con);
			InsertGenres.ReadInsert(con);
			InsertTagMap.ReadInsert(con);
			InsertMovieTags.ReadInsert(con);
			InsertOriginCountry.ReadInsert(con);
			InsertFilmCountry.ReadInsert(con); //2 min 30 seconds up to here
			
			
			//these are not required for GUI
			
			if(insertMode == 1) {
			printTime(t);
			InsertMovieActors.ReadInsert(con);
			InsertMovieDirectors.ReadInsert(con);
			InsertUserRated.ReadInsert(con);
			InsertRatedTimestamp.ReadInsert(con);
			InsertUserTagged.ReadInsert(con);
			InsertTaggedTimestamp.ReadInsert(con);
			}
           
            
            System.out.println("All files were successfully added to the database.\n");
            IndexManager.createIndex(con); //create index
            printTime(t);
       
            
        } 
        catch (SQLException e) { 
            System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
        } 
        catch (ClassNotFoundException e) { 
            System.err.println("Cannot find the database driver"); 
        } 
        finally { 
            // Never forget to close database connection 
        	DBConnectionManager.closeConnection(con); 
        	System.out.println("\nConnection closed.");
        } 
		
		
	 }
	//prints time for performance evaluation
	public static void printTime(long t) {
		long now = System.currentTimeMillis();
		long sec = (now - t);
		int minutes = (int)Math.floor(((sec/1000)/ 60));
		int seconds = (int)((sec/1000)%60);

		System.out.println("Time since start: " + minutes + " minutes and " + seconds + " seconds\n\n");
	}
	
	
	
}
