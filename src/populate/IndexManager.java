package populate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class IndexManager {
	
	public static void removeIndex(Connection con) throws SQLException {
		
		Statement stmt = con.createStatement(); 
		
		System.out.println("Deleting all indexes");
		
		try {
			stmt.executeQuery("DROP INDEX MOVIE_INDEX");
		}catch(Exception e) {
			System.out.println("Delete error: Movie_Index does not exist.");
		}
		
		try {
			stmt.executeQuery("DROP INDEX GENRES_INDEX");
		}catch(Exception e) {
			System.out.println("Delete error: Genres_Index does not exist.");
		}
		
		
		
		try {
			stmt.executeQuery("DROP INDEX TAG_MOVIE_PAIR_INDEX");
		}catch(Exception e) {
			System.out.println("Delete error: Tag_Movie_Pair_Index does not exist.");
		}
		
		try {
			stmt.executeQuery("DROP INDEX ORIGIN_INDEX");
		}catch(Exception e) {
			System.out.println("Delete error: Origin_Index does not exist.");
		}
		
		try {
			stmt.executeQuery("DROP INDEX FILM_INDEX");
		}catch(Exception e) {
			System.out.println("Delete error: Film_Index does not exist.");
		}
		
		
		
		stmt.close();
		System.out.println("All indexes deleted\n");
	}
	
	
	public static void createIndex(Connection con) throws SQLException {
		
		Statement stmt = con.createStatement(); 
		
		System.out.println("Creating all indexes");
		
		stmt.executeQuery("CREATE INDEX FILM_INDEX ON FILM_COUNTRY(COUNTRY)");
		stmt.executeQuery("CREATE INDEX ORIGIN_INDEX ON ORIGIN_COUNTRY(COUNTRY, MOVIE_ID)");
		stmt.executeQuery("CREATE INDEX TAG_MOVIE_PAIR_INDEX ON TAG_MOVIE_PAIR(TAG_ID, WEIGHT)");
		stmt.executeQuery("CREATE INDEX GENRES_INDEX ON GENRES(GENRE, MOVIE_ID)");
		stmt.executeQuery("CREATE INDEX MOVIE_INDEX ON MOVIE(MOVIE_ID, YEAR)");

		stmt.close();
		System.out.println("All indexes created\n");
	}
	
}
