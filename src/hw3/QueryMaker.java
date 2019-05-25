package hw3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class QueryMaker {
	private GUI gui;
	private ListManager listM;
	private ArrayList<String> nameList;
	
	public QueryMaker(GUI gui, ListManager listM) {
		this.gui = gui;
		this.listM = listM;
		nameList = new ArrayList<String>();
	}
	
	public void intakeData(JList origin_list, JList film_list, Connection con, double avg, int num, int fYear, int cYear, int tag, String tagCombo, String avgCombo, String numCombo) throws SQLException {
		if(cYear < fYear) {
			int temp = cYear;
			cYear = fYear;
			fYear = temp;
		}
		
		String originFilmWhere = "";
		String origin = generateOriginQuery(origin_list);
		if(origin.equals("")) {
			originFilmWhere ="";
		}
		else {
			originFilmWhere = " AND review.ID = orig.ID";
		}
		
		String film = generateFilmQuery(film_list);
		if(!film.equals("")) {
			originFilmWhere += " AND review.ID = film.ID";
		}
		
		String query = "SELECT review.NAME AS NAME FROM (SELECT M0.MOVIE_ID AS ID FROM MOVIE M0 WHERE M0.YEAR >= "
				+ fYear + " AND M0.YEAR <= " + cYear + " ) year, (SELECT M1.MOVIE_ID AS ID, M1.NAME AS NAME FROM MOVIE M1 WHERE M1.CRITIC_RATING " 
				+ avgCombo + avg + "AND M1.NUM_RATINGS " + numCombo + num + ") review, " + origin + film + listM.getGenreQuery() 
				+ "WHERE review.ID = year.ID AND review.ID = gen.ID" + originFilmWhere;
		
		System.out.println("\n" + query + "\n");
		
		Statement stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery(query);
		String resultTag;
		nameList.clear();
		while(rs.next()) {
			resultTag = rs.getString("NAME");
			nameList.add(resultTag);
		}
		
		return;
			
	}
	
	public String generateOriginQuery(JList origin_list) {
		List<String> originSelected = origin_list.getSelectedValuesList();
		if(originSelected.size() == 0) {
			return "";
		}
		
		String originSelectText ="";

		for (int i=0; i < originSelected.size(); i++)
		{
			if(i==0) {
				originSelectText = "'" + originSelected.get(i) + "'";
			}
			else {
				originSelectText += ", '" + originSelected.get(i) + "'";
			}
		}
		
		String originQuery;
		
		if(!gui.getAndSet()) { //or set
			originQuery = "(SELECT O.MOVIE_ID AS ID FROM ORIGIN_COUNTRY O WHERE O.COUNTRY IN(" + originSelectText 
					+ " )) orig, ";
		}
		else { //and set
			originQuery = "(SELECT O.MOVIE_ID AS ID FROM ORIGIN_COUNTRY O WHERE O.COUNTRY IN(" + originSelectText 
					+ ") GROUP BY O.MOVIE_ID HAVING COUNT(O.MOVIE_ID) = " + originSelected.size() + ") orig, ";
		}
		
		return originQuery;
	}
	
	
	
	public String generateFilmQuery(JList film_list) {
		List<String> filmSelected = film_list.getSelectedValuesList();
		if(filmSelected.size() == 0) {
			return "";
		}
		
		String filmSelectText ="";

		for (int i=0; i < filmSelected.size(); i++)
		{
			if(i==0) {
				filmSelectText = "'" + filmSelected.get(i) + "'";
			}
			else {
				filmSelectText += ", '" + filmSelected.get(i) + "'";
			}
		}
		
		String filmQuery;
		
		if(!gui.getAndSet()) { //or set
			filmQuery = "(SELECT F.MOVIE_ID AS ID FROM FILM_COUNTRY F WHERE F.COUNTRY IN(" + filmSelectText + " )) film, ";
		}
		else { //and set
			filmQuery = "(SELECT F.MOVIE_ID AS ID FROM FILM_COUNTRY F WHERE F.COUNTRY IN(" + filmSelectText 
					+ ") GROUP BY F.MOVIE_ID HAVING COUNT(F.MOVIE_ID) = " + filmSelected.size() + ") film, ";
		}
		
		return filmQuery;
	}
	

	public void displayNames(DefaultListModel nameLM) {
		int size = nameList.size();
		gui.setJlabel(size);
		nameList.forEach((n) -> nameLM.addElement(n));
	}
	
	
}
