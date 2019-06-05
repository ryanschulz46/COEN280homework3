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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertTagMap {

	public static void ReadInsert(Connection con) throws SQLException { 
		 
		Statement stmt = con.createStatement();
		 

		System.out.println("Inserting Tag Map Data"); 
		
		//dat file
		File file = new File("assets/datafiles/tags.dat");
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
		 System.out.println("Finished Tag Map Data\n");
	}
 
	private static void parseLine(String str, Statement stmt ) throws SQLException{
		String id, value, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		// Check if there is another line of input
		id = sc.next();
		value = sc.next();
		buf = "insert into TAG_MAP values (" + id + ", '" + value + "')";
		//System.out.println(buf);
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}