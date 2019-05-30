package hw3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class ListManager {
	
	private ArrayList<String> genreList;
	private ArrayList<String> originList;
	private ArrayList<String> filmList;
	private GUI gui;
	private String bufOrigin;
	private String bufFilm;
	private String genreSelected;
	private String genreQuery;
	private int genreSelectSize;
	private String genreSelectText;
	private int originSelectSize;
	private String originSelected;
	private String filmQuery;
	private String originSelectText;
	
	
	public ListManager(GUI gui){
		genreList = new ArrayList<String>();
		originList = new ArrayList<String>();
		filmList = new ArrayList<String>();
		this.gui = gui;
	}
	

	
	
/////GENRES *************	
	// select genres from DB into a list
	public void genreDbToList(Connection con) throws SQLException {
		Statement stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT DISTINCT G.GENRE FROM GENRES G ORDER BY G.GENRE ASC");
		String tag;
		genreList.clear();
		while(rs.next()) {
			tag = rs.getString("GENRE");
			genreList.add(tag); //genreList is ArrayList<String>
		}
		stmt.close();
		return;	
	}
	
	
	
	
	//display the genre options
	public void pushGenreList(DefaultListModel genreLM) {
		genreList.forEach((n) -> genreLM.addElement(n));
	}
	
	
	
	
	
	
	
	
	public boolean generateGenreQuery(JList genre_list) {
		List<String> genreSelected = genre_list.getSelectedValuesList();
		if(genreSelected.size() == 0) {
			JOptionPane.showMessageDialog(gui.contain, "Must selecte a genre");
			return false;
		}
		
		
		genreSelectText = "";
		genreQuery = "";

		for (int i=0; i < genreSelected.size(); i++)
		{
			if(i==0) {
				genreSelectText = "'" + genreSelected.get(i) + "'";
			}
			else {
				genreSelectText += ", '" + genreSelected.get(i) + "'";
			}
		}
		
		genreSelectSize = genreSelected.size();
		if(!gui.getAndSet()) { //or set
			genreQuery = " (SELECT G.MOVIE_ID AS ID FROM GENRES G WHERE G.GENRE IN(" + genreSelectText + " )) gen ";
		}
		else { //and set
			genreQuery = " (SELECT G.MOVIE_ID AS ID FROM GENRES G WHERE G.GENRE IN (" + genreSelectText + ") GROUP BY G.MOVIE_ID HAVING COUNT(G.MOVIE_ID) = " + genreSelectSize + ") gen ";
		}
		return true;
	}
	
	
	public boolean getOriginSelection(JList origin_list, Connection con) throws SQLException {
		List<String> originSelected = origin_list.getSelectedValuesList();
		if(originSelected.size() == 0) {
			JOptionPane.showMessageDialog(gui.contain, "Must selecte a origin");
			return false;
		}
		
		originSelectText = "";
		filmQuery = "";
		
		for (int i=0; i < originSelected.size(); i++)
		{
			if(i==0) {
				originSelectText = "'" + originSelected.get(i) + "'";
			}
			else {
				originSelectText += ", '" + originSelected.get(i) + "'";
			}
		}
		
		
		
		originSelectSize = originSelected.size();
		if(!gui.getAndSet()) { //or set
			filmQuery = " SELECT DISTINCT FILM.COUNTRY AS COUNTRY FROM (SELECT gen.ID AS ID FROM (SELECT G.MOVIE_ID AS ID FROM GENRES G WHERE G.GENRE IN(" + genreSelectText + " )) gen, "
					+ "(SELECT O.MOVIE_ID AS ID FROM ORIGIN_COUNTRY O WHERE O.COUNTRY IN("+ originSelectText + ")) origin"
					+ " WHERE gen.ID = origin.ID) orig,"
					+ "(SELECT F.MOVIE_ID, F.COUNTRY FROM FILM_COUNTRY F GROUP BY F.MOVIE_ID, F.COUNTRY)"
					+ " FILM WHERE FILM.MOVIE_ID = orig.ID ORDER BY FILM.COUNTRY ASC";
		}
		else { //and set
			filmQuery = " SELECT DISTINCT FILM.COUNTRY AS COUNTRY FROM (SELECT gen.ID AS ID FROM (SELECT G.MOVIE_ID AS ID FROM GENRES G WHERE G.GENRE IN(" + genreSelectText + " ) GROUP BY G.MOVIE_ID HAVING COUNT(G.MOVIE_ID) = " + genreSelectSize + ") gen, "
					+ " (SELECT O.MOVIE_ID AS ID FROM ORIGIN_COUNTRY O WHERE O.COUNTRY IN("+ originSelectText + ") GROUP BY O.MOVIE_ID HAVING COUNT(O.MOVIE_ID) = " + originSelectSize +  ") origin"
					+ " WHERE gen.ID = origin.ID) orig,"
					+ "(SELECT F.MOVIE_ID, F.COUNTRY FROM FILM_COUNTRY F GROUP BY F.MOVIE_ID, F.COUNTRY" + 
					") FILM WHERE FILM.MOVIE_ID = orig.ID ORDER BY FILM.COUNTRY ASC";
		}
		
		System.out.println(filmQuery);
		
		filmDbToList(con,filmQuery);
		
		return true;
	}
		
		
	
	
	
	//get genres selected by user
	public boolean getGenreSelection(JList genre_list, Connection con) throws SQLException{
		
		boolean success = generateGenreQuery(genre_list);
		bufOrigin = "";
		
		if(gui.getAndSet()) {
			bufOrigin = "SELECT DISTINCT O.COUNTRY AS COUNTRY FROM (SELECT G.MOVIE_ID AS ID FROM GENRES G WHERE G.GENRE IN (" + genreSelectText + ") GROUP BY G.MOVIE_ID HAVING COUNT(G.MOVIE_ID) = " + genreSelectSize + ") gen, ORIGIN_COUNTRY O WHERE O.MOVIE_ID = gen.ID ORDER BY O.COUNTRY ASC";
			bufFilm = "SELECT DISTINCT film.COUNTRY AS COUNTRY FROM (SELECT G.MOVIE_ID AS ID FROM GENRES G WHERE G.GENRE IN (" + genreSelectText + ") GROUP BY G.MOVIE_ID HAVING COUNT(G.MOVIE_ID) = " + genreSelectSize + ") gen,(SELECT F.MOVIE_ID, F.COUNTRY FROM FILM_COUNTRY F GROUP BY F.MOVIE_ID, F.COUNTRY ) film WHERE film.MOVIE_ID = gen.ID ORDER BY film.COUNTRY ASC";
		}
		else {
			//or set
			bufOrigin = "SELECT DISTINCT O.COUNTRY AS COUNTRY FROM (SELECT G.MOVIE_ID AS ID FROM GENRES G WHERE G.GENRE IN(" + genreSelectText + " )) gen, ORIGIN_COUNTRY O WHERE O.MOVIE_ID = gen.ID ORDER BY O.COUNTRY ASC";
			bufFilm = "SELECT DISTINCT film.COUNTRY AS COUNTRY FROM (SELECT G.MOVIE_ID AS ID FROM GENRES G WHERE G.GENRE IN(" + genreSelectText + " )) gen,(SELECT F.MOVIE_ID, F.COUNTRY FROM FILM_COUNTRY F GROUP BY F.MOVIE_ID, F.COUNTRY ) film WHERE film.MOVIE_ID = gen.ID ORDER BY film.COUNTRY ASC";
		}
		
		originDbToList(con, bufOrigin);
		filmDbToList(con, bufFilm);
		return success;
	}

	public String getGenreQuery() {
		return genreQuery;
	}

	
	
	
	//query origin table from genre
	public void originDbToList(Connection con, String query) throws SQLException {
		Statement stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery(query);
		String tag;
		originList.clear();
		while(rs.next()) {
			tag = rs.getString("COUNTRY");
			originList.add(tag);
		}
		stmt.close();
		return;	
	}
	
	//push origin query results to GUI filmlist
	public void pushOriginList(DefaultListModel originLM) {
		originList.forEach((n) -> originLM.addElement(n));
	}
	
	
	
	
	//query film table from genre
	private void filmDbToList(Connection con, String query) throws SQLException {
		Statement stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery(query);
		String tag;
		filmList.clear();
		while(rs.next()) {
			tag = rs.getString("COUNTRY");
			filmList.add(tag);
		}
		stmt.close();
		return;	
	}
	
	//push film query results to GUI filmlist
	public void pushFilmList(DefaultListModel filmLM) {
		filmList.forEach((n) -> filmLM.addElement(n));
	}

	
	public void resetNoUnselect()
	{
		
	}

	public void reset() {
		bufOrigin = "";
		bufFilm = "";
		originList.clear();
		filmList.clear();
		genreSelected = "";
		genreQuery = "";
		genreSelectSize =0;
		genreSelectText = "";
		originSelectSize = 0;
		originSelectText = "";
		originSelected = "";
	}
	
}
