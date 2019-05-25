package hw3;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

import populate.DBConnectionManager;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.RoundingMode;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;

public class GUI {
	private boolean active;
	private ListManager listM;
	private QueryMaker queryM;
	private Connection con;
	private boolean isAndSet;
	private boolean andOrLock;
	private DefaultListModel genreListModel;
	private DefaultListModel originListModel;
	private DefaultListModel filmListModel;
	private DefaultListModel nameListModel;
	private JList genre_list;
	public JFrame contain;
	private JPanel panel;
	private ButtonGroup andOrSelector;
	private JScrollPane genre_scrollPane;
	private JLabel lblGenre;
	private JScrollPane origin_scrollPane;
	private JList origin_list;
	private JLabel lblOriginCountry;
	private JScrollPane film_scrollPane;
	private JList film_list;
	private JLabel lblFilmCountry;
	private JButton btnAddCountries;
	private JRadioButton rdbtnAnd;
	private JRadioButton rdbtnOr;
	private JLabel lblMovieWasReleased;
	private JFormattedTextField floorYear;
	private JLabel LblMovieTo;
	private JFormattedTextField ceilYear;
	private JLabel lblTagWeightIs;
	private JComboBox tagCombo;
	private JFormattedTextField tagWeight;
	private JButton btnQuery;
	private JLabel lblNumMovie;

	
	public GUI(Connection con) throws SQLException{
		active = true;
		this.con = con;
		isAndSet = true;
		andOrLock = false;
		listM = new ListManager(this);
		guiCreate();
		listM.genreDbToList(con);
		listM.pushGenreList(genreListModel);
		queryM = new QueryMaker(this, listM);
		reset();
		System.out.println("All loaded");
	}
	
	private void guiCreate() throws SQLException {
		

		//JFrame creation
		contain = new JFrame();
		contain.setPreferredSize(new Dimension(1440, 800));
		contain.setSize(new Dimension(1440, 800));
		contain.setResizable(false);
		
		
		//Panel creation
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setPreferredSize(new Dimension(1440, 800));
		panel.setSize(new Dimension(1440, 800));
		contain.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(null);
		
	
		
		//genre list set up
		genre_scrollPane = new JScrollPane();
		genre_scrollPane.setBounds(21, 40, 226, 400);
		panel.add(genre_scrollPane);
		
		genreListModel = new DefaultListModel();
		genre_list = new JList(genreListModel);
		genre_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		genre_scrollPane.setViewportView(genre_list);
		
		lblGenre = new JLabel("Genre");
		lblGenre.setHorizontalAlignment(SwingConstants.CENTER);
		lblGenre.setBackground(Color.GRAY);
		lblGenre.setForeground(Color.RED);
		genre_scrollPane.setColumnHeaderView(lblGenre);
		
		
		
		//origin list set up
		origin_scrollPane = new JScrollPane();
		origin_scrollPane.setBounds(268, 40, 248, 400);
		panel.add(origin_scrollPane);
		
		originListModel = new DefaultListModel();
		origin_list = new JList(originListModel);
		origin_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		origin_scrollPane.setViewportView(origin_list);
		
		lblOriginCountry = new JLabel("Origin Country");
		lblOriginCountry.setHorizontalAlignment(SwingConstants.CENTER);
		lblOriginCountry.setForeground(Color.RED);
		origin_scrollPane.setColumnHeaderView(lblOriginCountry);
		
		
		
		
		//film list set up
		film_scrollPane = new JScrollPane();
		film_scrollPane.setBounds(537, 40, 300, 400);
		panel.add(film_scrollPane);
		
		filmListModel = new DefaultListModel();
		film_list = new JList(filmListModel);
		film_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		film_scrollPane.setViewportView(film_list);
		
		lblFilmCountry = new JLabel("Film Country");
		lblFilmCountry.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilmCountry.setForeground(Color.RED);
		film_scrollPane.setColumnHeaderView(lblFilmCountry);

		
		//btn next steo
		btnAddCountries = new JButton("Load countries options");
		btnAddCountries.addActionListener(new ActionListener() {
			public void btnAddCountries(ActionEvent arg0) {
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			
					
					try {
						boolean success = listM.getGenreSelection(genre_list, con);
						if(!success) {
							reset();
							return;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.println("Error btnAddCountries start");
						e.printStackTrace();
						System.out.println("Error btnAddCountries end");
					}
					if(!andOrLock) {
						listM.pushOriginList(originListModel);
						listM.pushFilmList(filmListModel);
						andOrLock = true;
					}
					
					contain.pack();
					contain.repaint();
				}
			
		});
		btnAddCountries.setBounds(238, 457, 319, 35);
		panel.add(btnAddCountries);
		
		
				
		//radio buttons			
		rdbtnAnd = new JRadioButton("AND");
		rdbtnAnd.setBounds(42, 457, 79, 35);
		rdbtnAnd.setSelected(true);
		rdbtnAnd.addActionListener(new ActionListener() {
			public void rdbtnAnd(ActionEvent arg0) {
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!andOrLock) {
					isAndSet = true;
					System.out.println("And");
				}
				else {
					System.out.println("AndOr Locked!");
					andOrLock = false;
					rdbtnOr.setSelected(true);
					andOrLock = true;
					JOptionPane.showMessageDialog(contain, "AndOr Switching is locked mid query");
				}
			}	
		});
		
		rdbtnOr = new JRadioButton("OR");
		rdbtnOr.setBounds(120, 457, 79, 35);
		rdbtnOr.setSelected(false);
		rdbtnOr.addActionListener(new ActionListener() {
			public void rdbtnOr(ActionEvent arg0) {
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!andOrLock) {
					isAndSet = false;
					System.out.println("Or");
					
				}
				else {
					System.out.println("AndOr Locked!");
					andOrLock = false;
					rdbtnAnd.setSelected(true);
					andOrLock = true;
					JOptionPane.showMessageDialog(contain, "AndOr Switching is locked mid query");
				}
			}	
		});
		andOrSelector = new ButtonGroup();
		andOrSelector.add(rdbtnAnd);
		andOrSelector.add(rdbtnOr);
		
		panel.add(rdbtnOr);
		panel.add(rdbtnAnd);
		
		
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(617, 457, 141, 35);
		btnReset.addActionListener(new ActionListener() {
			public void btnAddCountries(ActionEvent arg0) {
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
			
		});
		panel.add(btnReset);
		
		
		
		
		///REVIEW AVG AND NUM PANEL
		JPanel reviewPanel = new JPanel();
		reviewPanel.setBackground(Color.LIGHT_GRAY);
		reviewPanel.setBounds(858, 109, 555, 291);
		panel.add(reviewPanel);
		reviewPanel.setLayout(null);
		
		
		//textFormat
		NumberFormat doubleFormat = DecimalFormat.getInstance();
		doubleFormat.setMinimumFractionDigits(1);
		doubleFormat.setMaximumFractionDigits(1);
		doubleFormat.setRoundingMode(RoundingMode.HALF_UP);
		
		NumberFormat intFormat = NumberFormat.getIntegerInstance();
		intFormat.setGroupingUsed(false);
		
		/*NumberFormatter intFormat = new NumberFormatter(numFormat);
	    intFormat.setValueClass(Integer.class);
	    intFormat.setMinimum(0);
	    intFormat.setMaximum(2019);
	    intFormat.setAllowsInvalid(false);*/
		intFormat.setRoundingMode(RoundingMode.HALF_UP);
		
		
		//comboBoxFormat
		String[] comboOpt = {"=", ">", "<", "<="};
		
		
		//avg review
		JLabel lblAverageRatingIs = new JLabel("Average Rating is");
		lblAverageRatingIs.setBounds(37, 21, 164, 26);
		reviewPanel.add(lblAverageRatingIs);
		
		JComboBox avgCombo = new JComboBox(comboOpt);
		avgCombo.setSelectedIndex(0);
		avgCombo.setBounds(252, 18, 44, 32);
		reviewPanel.add(avgCombo);
		
		JFormattedTextField avgReview = new JFormattedTextField(doubleFormat);
		avgReview.setBounds(317, 18, 128, 32);
		reviewPanel.add(avgReview);
		
		
		//num review
		JLabel lblNumberOfReviews = new JLabel("Number of reviews is");
		lblNumberOfReviews.setBounds(21, 84, 229, 26);
		reviewPanel.add(lblNumberOfReviews);
		
		JComboBox numCombo = new JComboBox(comboOpt);
		numCombo.setSelectedIndex(0);
		numCombo.setBounds(252, 81, 44, 32);
		reviewPanel.add(numCombo);
		
		JFormattedTextField numReview = new JFormattedTextField(intFormat);
		numReview.setBounds(317, 81, 128, 32);
		reviewPanel.add(numReview);
		
		
		
		//Movie released year
		lblMovieWasReleased = new JLabel("Movie years from:");
		lblMovieWasReleased.setBounds(21, 151, 174, 26);
		reviewPanel.add(lblMovieWasReleased);
		LblMovieTo = new JLabel("to");
		LblMovieTo.setHorizontalAlignment(SwingConstants.CENTER);
		LblMovieTo.setBounds(317, 151, 50, 26);
		reviewPanel.add(LblMovieTo);
		
		//floor year
		floorYear = new JFormattedTextField(intFormat);
		floorYear.setBounds(197, 148, 109, 32);
		reviewPanel.add(floorYear);
		floorYear.setColumns(10);
		
		//ceil year
		ceilYear = new JFormattedTextField(intFormat);
		ceilYear.setBounds(369, 148, 109, 32);
		reviewPanel.add(ceilYear);
		ceilYear.setColumns(10);
		
		
		
		//Tag Weights
		lblTagWeightIs = new JLabel("Tag weight is");
		lblTagWeightIs.setBounds(37, 220, 138, 26);
		reviewPanel.add(lblTagWeightIs);
		
		tagCombo = new JComboBox(comboOpt);
		tagCombo.setBounds(252, 217, 44, 32);
		reviewPanel.add(tagCombo);
		
		tagWeight = new JFormattedTextField(intFormat);
		tagWeight.setBounds(317, 217, 128, 32);
		reviewPanel.add(tagWeight);
		tagWeight.setColumns(10);
		
		

		
		
		
		//results
		JScrollPane movieScroll = new JScrollPane();
		movieScroll.setBounds(21, 519, 495, 260);
		panel.add(movieScroll);
		
		nameListModel = new DefaultListModel();
		JList name_list = new JList(nameListModel);
		movieScroll.setViewportView(name_list);
		
		JLabel lblMovieNames = new JLabel("Movie Names");
		lblMovieNames.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovieNames.setForeground(Color.RED);
		movieScroll.setColumnHeaderView(lblMovieNames);
		
		JScrollPane tagScroll = new JScrollPane();
		tagScroll.setBounds(583, 519, 495, 260);
		panel.add(tagScroll);
		
		JLabel lblTagNames = new JLabel("Tag Names");
		lblTagNames.setForeground(Color.RED);
		lblTagNames.setHorizontalAlignment(SwingConstants.CENTER);
		tagScroll.setColumnHeaderView(lblTagNames);
		
		JList tag_list = new JList();
		tagScroll.setViewportView(tag_list);

		//query
		btnQuery = new JButton("QUERY");
		btnQuery.setBounds(893, 421, 448, 71);
		btnQuery.addActionListener(new ActionListener() {
			public void btnQuery(ActionEvent arg0) {
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
	
				if(!listM.generateGenreQuery(genre_list)) {
					return;
				}
				
				
				String inputAvgCombo = (String) avgCombo.getSelectedItem();
				String inputNumCombo = (String) numCombo.getSelectedItem();
				String inputTagCombo = (String) tagCombo.getSelectedItem();
				int inputTag;
				double inputAvg;
				int inputNum;
				int inputCeil;
				int inputFloor;
				
				
				try {
					inputTag = Integer.parseInt(tagWeight.getText());
				}catch(Exception e) { //no tag weight given
					inputTag = 0;
					inputTagCombo = ">=";
				}
				
				try {
					inputAvg = Double.parseDouble(avgReview.getText());
				}catch(Exception e) { //no input average input given
					inputAvg = 0;
					inputAvgCombo = ">=";
				}
				
				try {
					inputNum = Integer.parseInt(numReview.getText());
				} catch(Exception e) {
					inputNum = 0;
					inputNumCombo = ">=";
				}
				
				try {
					inputFloor = Integer.parseInt(floorYear.getText());
				}catch(Exception e) {
					inputFloor = 0;
				}
				
				try {
					inputCeil = Integer.parseInt(ceilYear.getText());
				} catch(Exception e) {
					inputCeil = 3000;
				}
				
				try {
					queryM.intakeData(origin_list, film_list, con, inputAvg, inputNum, inputFloor, inputCeil, inputTag, inputTagCombo, inputAvgCombo, inputNumCombo);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				queryM.displayNames(nameListModel);
				
				contain.pack();
				contain.repaint();
				
				
			}
			
		});
		panel.add(btnQuery);
		
		JPanel counterPanel = new JPanel();
		counterPanel.setBounds(1156, 542, 195, 169);
		panel.add(counterPanel);
		
		lblNumMovie = new JLabel("Numer of movies: 0");
		counterPanel.add(lblNumMovie);
		
		//contain.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//EXIT CRITERIA
		contain.addWindowListener(new WindowAdapter()
		{
		    @Override
		    public void windowClosing(WindowEvent e)
		    {
		    	System.out.println("close jframe");
		        DBConnectionManager.closeConnection(con);
		        System.exit(0);
		    }
		});
		
		
		contain.pack();
		contain.setVisible(true);
		
		// Jframe packing


	}

	public void reset() {
		film_list.clearSelection();
		origin_list.clearSelection();
		genre_list.clearSelection();
		filmListModel.removeAllElements();
		originListModel.removeAllElements();
		genreListModel.removeAllElements();
		listM.pushGenreList(genreListModel);
		listM.reset();
		andOrLock = false;
		rdbtnAnd.setSelected(true);
		isAndSet = true;
		contain.pack();
		contain.repaint();
	}
	
	public Connection getCon() {
		return con;
	}
	
	public boolean getAndSet() {
		return isAndSet;
	}
	
	public void setAndOrLock(boolean x) {
		andOrLock = x;
	}
	public boolean getAndOrLock() {
		return andOrLock;
	}
	
	public void setJlabel(int name) {
		String str = "Num of movies: " + name;
		lblNumMovie.setText(str);
		contain.repaint();
	}
	
	public boolean getActive() {
		return active;
	}
}
