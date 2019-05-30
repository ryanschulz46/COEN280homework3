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
	private ArrayList<String> tagList;
	private String origin;
	private String film;
	private int averageMode;
	
	public QueryMaker(GUI gui, ListManager listM) {
		this.gui = gui;
		this.listM = listM;
		nameList = new ArrayList<String>();
		tagList = new ArrayList<String>();
		origin = "";
		film = "";
		averageMode = 0;
		
	}
	
	public void intakeData(JList origin_list, JList film_list, Connection con, double avg, int num, int fYear, int cYear, int tag, String tagCombo, String avgCombo, String numCombo) throws SQLException {
		if(cYear < fYear) {
			int temp = cYear;
			cYear = fYear;
			fYear = temp;
		}
		
		String originFilmWhere = "";
		origin = generateOriginQuery(origin_list);
		if(origin.equals("")) {
			originFilmWhere ="";
		}
		else {
			originFilmWhere = " AND review.ID = orig.ID";
		}
		
		film = generateFilmQuery(film_list);
		if(!film.equals("")) {
			originFilmWhere += " AND review.ID = film.ID";
		}
		
		
		String numType = "AVG_NUM_RATINGS";
		String avgType;
		
		if(averageMode == 0) {
			avgType = "AVG_TOP_CRITIC";
			numType = "NUM_TOP_CRITIC";
		}
		else if(averageMode == 1) {
			avgType = "AVG_RATING_A";
		}
		else {
			avgType = "AVG_RATING_B";
		}
		
		String nameQuery = "SELECT DISTINCT review.NAME as NAME FROM (SELECT M0.MOVIE_ID AS ID FROM MOVIE M0 WHERE M0.YEAR >= "
				+ fYear + " AND M0.YEAR <= " + cYear + " ) year, (SELECT M1.MOVIE_ID AS ID, M1.NAME AS NAME FROM MOVIE M1 WHERE M1."+  avgType + " " 
				+ avgCombo + avg + " AND M1." + numType + " " + numCombo + num + ") review, " + origin + film + listM.getGenreQuery() 
				+ "WHERE review.ID = year.ID AND review.ID = gen.ID" + originFilmWhere;
		
		String query = "SELECT review.ID as ID FROM (SELECT M0.MOVIE_ID AS ID FROM MOVIE M0 WHERE M0.YEAR >= "
				+ fYear + " AND M0.YEAR <= " + cYear + " ) year, (SELECT M1.MOVIE_ID AS ID, M1.NAME AS NAME FROM MOVIE M1 WHERE M1." + avgType + " " 
				+ avgCombo + avg + " AND M1." + numType  +" "+ numCombo + num + ") review, " + origin + film + listM.getGenreQuery() 
				+ "WHERE review.ID = year.ID AND review.ID = gen.ID" + originFilmWhere;
		
		String tagQuery = "SELECT DISTINCT TM.TAG_NAME AS TAG FROM (SELECT T.TAG_ID AS TID FROM( " + query + ") name, TAG_MOVIE_PAIR T"
				+ " WHERE T.MOVIE_ID = name.ID) tagID, Tag_Map TM WHERE TM.TAG_ID = tagID.TID";
				
				
		
		System.out.println("\n" + nameQuery + "\n");
		System.out.println(tagQuery + "\n");
		
		//name query
		Statement stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery(nameQuery);
		String resultName;
		nameList.clear();
		while(rs.next()) {
			resultName = rs.getString("NAME");
			nameList.add(resultName);
		}
		stmt.close();
		
		
		Statement stmtTag = con.createStatement(); 
		ResultSet rsTag = stmtTag.executeQuery(tagQuery);
		String resultTag;
		tagList.clear();
		while(rsTag.next()) {
			resultTag = rsTag.getString("TAG");
			tagList.add(resultTag);
		}
		stmtTag.close();
	
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
		
		origin = originQuery;
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
			filmQuery = "(SELECT inFilm.ID AS ID FROM (SELECT F.MOVIE_ID AS ID, F.COUNTRY AS COUNTRY "
					 + "FROM FILM_COUNTRY F GROUP BY F.MOVIE_ID, F.COUNTRY) inFilm WHERE inFilm.COUNTRY IN("
					+ filmSelectText +")"
					 + " GROUP BY inFilm.ID HAVING COUNT(inFilm.ID) = " + filmSelected.size() + ") film, "; 
		}
		
		System.out.println(filmQuery);
		return filmQuery;
	}
	
 
	public void setAvgMode(int x) {
		averageMode = x;
	}
	
	
	public void reset() {
		nameList.clear();
		tagList.clear();
	}
	

	public void displayNames(DefaultListModel nameLM) {
		int size = nameList.size();
		gui.setJlabelName(size);
		nameList.forEach((n) -> nameLM.addElement(n));
	}
	
	public void displayTags(DefaultListModel tagLM) {
		int size = tagList.size();
		gui.setJlabelTag(size);
		tagList.forEach((n) -> tagLM.addElement(n));
	}
	
	
}
