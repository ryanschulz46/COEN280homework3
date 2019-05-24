package hw3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListManager {
	
	private ArrayList<String> genreList;
	private ArrayList<String> originList;
	private ArrayList<String> filmList;
	private GUI gui;
	
	
	public ListManager(GUI gui){
		genreList = new ArrayList<String>();
		originList = new ArrayList<String>();
		filmList = new ArrayList<String>();
		this.gui = gui;
	}
	

	// select genres from DB into a list
	public void genreDbToList(Connection con, String query) throws SQLException {
		Statement stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT DISTINCT G.GENRE FROM GENRES G");
		String tag;
		
		while(rs.next()) {
			tag = rs.getString("GENRE");
			genreList.add(tag);
		}
		return;	
	}
	
	//display the genre options
	public void pushGenreList(DefaultListModel genreLM) {
		genreList.forEach((n) -> genreLM.addElement(n));
	}
	
	
	//get genres selected by user
	public void getGenreSelection(JList genre_list, Connection con) throws SQLException{
		List<String> genreSelected = genre_list.getSelectedValuesList();
		
		String selected = "";
		String bufOrigin;
		for (int i=0; i < genreSelected.size(); i++)
		{
			if(i==0) {
				selected = "'" + genreSelected.get(i) + "'";
			}
			else {
				selected += ", '" + genreSelected.get(i) + "'";
			}
		}
		if(gui.getAndSet()) {
			bufOrigin = "SELECT DISTINCT O.COUNTRY AS COUNTRY FROM (SELECT G.MOVIE_ID AS ID FROM GENRES G WHERE G.GENRE in (" + selected + ") GROUP BY G.MOVIE_ID HAVING COUNT(G.MOVIE_ID) = " + genreSelected.size() + ") gen, ORIGIN_COUNTRY O WHERE O.MOVIE_ID = gen.ID";
		}
		else {
			//or set
			bufOrigin = "SELECT DISTINCT O.COUNTRY AS COUNTRY FROM (SELECT G.MOVIE_ID AS ID FROM GENRES G WHERE G.GENRE IN(" + selected + " ) gen, ORIGIN_COUNTRY O WHERE O.MOVIE_ID = gen.ID";
		}
		
		originDbToList(con, bufOrigin);
		
	}
	
	public void originDbToList(Connection con, String query) throws SQLException {
		Statement stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery(query);
		String tag;
		
		while(rs.next()) {
			tag = rs.getString("COUNTRY");
			originList.add(tag);
		}
		return;	
	}
	
	public void pushOriginList(DefaultListModel originLM) {
		originList.forEach((n) -> originLM.addElement(n));
	}
	
	
	private void filmDbToList(Connection con, String query) throws SQLException {
		Statement stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT DISTINCT G.GENRE FROM GENRES G");
		String tag;
		
		while(rs.next()) {
			tag = rs.getString("GENRE");
			filmList.add(tag);
		}
		return;	
	}
	
	
	private void queryMaker() {
		
	}
	
	public void reset() {
		
	}
}
