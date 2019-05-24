package hw3;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI {
	
	private ListManager listM;
	private Connection con;
	private boolean isAndSet;
	private boolean andOrLock;
	private DefaultListModel genreListModel;
	private DefaultListModel originListModel;
	private DefaultListModel filmListModel;
	private JList genre_list;
	private JFrame contain;
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
	
	public GUI(Connection con) throws SQLException{
		this.con = con;
		isAndSet = true;
		andOrLock = false;
		listM = new ListManager(this);
		guiCreate();
		listM.genreDbToList(con, "");
		listM.pushGenreList(genreListModel);
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
		panel.setPreferredSize(new Dimension(1440, 800));
		panel.setSize(new Dimension(1440, 800));
		contain.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
	
		
		//genre list set up
		genre_scrollPane = new JScrollPane();
		genre_scrollPane.setBounds(54, 40, 226, 557);
		panel.add(genre_scrollPane);
		
		genreListModel = new DefaultListModel();
		genre_list = new JList(genreListModel);
		genre_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		genre_scrollPane.setViewportView(genre_list);
		
		lblGenre = new JLabel("Genre");
		genre_scrollPane.setColumnHeaderView(lblGenre);
		
		
		
		//origin list set up
		origin_scrollPane = new JScrollPane();
		origin_scrollPane.setBounds(335, 40, 248, 557);
		panel.add(origin_scrollPane);
		
		originListModel = new DefaultListModel();
		origin_list = new JList(originListModel);
		origin_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		origin_scrollPane.setViewportView(origin_list);
		
		lblOriginCountry = new JLabel("Origin Country");
		origin_scrollPane.setColumnHeaderView(lblOriginCountry);
		
		
		
		
		//film list set up
		film_scrollPane = new JScrollPane();
		film_scrollPane.setBounds(631, 40, 238, 557);
		panel.add(film_scrollPane);
		
		filmListModel = new DefaultListModel();
		film_list = new JList(filmListModel);
		film_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		film_scrollPane.setViewportView(film_list);
		
		lblFilmCountry = new JLabel("Film Country");
		film_scrollPane.setColumnHeaderView(lblFilmCountry);

		
		//btn next steo
		btnAddCountries = new JButton("Load countries from genres");
		btnAddCountries.addActionListener(new ActionListener() {
			public void btnAddCountries(ActionEvent arg0) {
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				andOrLock = true;
				try {
					listM.getGenreSelection(genre_list, con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Error btnAddCountries start");
					e.printStackTrace();
					System.out.println("Error btnAddCountries end");
				}
				listM.pushOriginList(originListModel);
				contain.pack();
				contain.repaint();
				
			}
		});
		btnAddCountries.setBounds(436, 625, 340, 35);
		panel.add(btnAddCountries);
		

		
		
				
		//radio buttons		
		
				
		rdbtnAnd = new JRadioButton("AND");
		rdbtnAnd.setBounds(79, 625, 79, 35);
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
		rdbtnOr.setBounds(171, 625, 79, 35);
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
		
		contain.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		contain.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        DBConnectionManager.closeConnection(con); 
		        System.out.println("closing db");
		        try
		        {
		            Thread.sleep(5000);
		        }
		        catch(InterruptedException ex)
		        {
		            Thread.currentThread().interrupt();
		        }
		        System.out.println("exit");
		    	System.exit(0);
		    }
		});
		
		
		contain.pack();
		contain.setVisible(true);
		
		// Jframe packing


	}

	public Connection getCon() {
		return con;
	}
	
	public boolean getAndSet() {
		return isAndSet;
	}
	
	
	
}
