package hw3;

import java.sql.Connection;
import java.sql.SQLException;

import populate.DBConnectionManager;
import populate.InsertFilmCountry;

public class Main {
	public static void main(String[] args) {
        Connection con = null; 
        try {
        	System.out.println("Start");
            con = DBConnectionManager.openConnection();
            GUI gui = new GUI(con);
        } 
        catch (SQLException e) { 
            System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
        } 
        catch (ClassNotFoundException e) { 
            System.err.println("Cannot find the database driver"); 
        } 		
	}
}
