package populate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertTagMap {

	public static void ReadInsert(Connection con) throws SQLException { 
		 
		Statement stmt = con.createStatement(); 
		 System.out.println("Inserting Tag Map Data ..."); 
		
		//dat file
		File file = new File("assets/datafiles/tags.dat");
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
		String id, value, buf;
		Scanner sc = new Scanner(str);
		sc.useDelimiter("\t");
		// Check if there is another line of input
		id = sc.next();
		value = sc.next();
		buf = "insert into TAG_MAP values (" + id + ", '" + value + "')";
		System.out.println(buf);
		stmt.executeUpdate(buf);
		sc.close();
	}
	
}