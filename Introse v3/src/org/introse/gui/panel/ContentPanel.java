package org.introse.gui.panel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.MainMenu;


public class ContentPanel extends JPanel 
{	
	private String currentView;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel listPanel;
	private JPanel settingsPanel;
	private JPanel detailsPanel;
	private JPanel histopathologyPanel;
	private JPanel cytologyPanel;
	private JPanel gynecologyPanel;
	private JPanel patientPanel;
	private JPanel searchPanel;
	private JButton newButton;
	private JButton searchButton;
	private JButton refreshButton;
	private JTextField filterField;
	private JLabel headerLabel;
	
	private JPopupMenu createPopup;
	private JMenuItem histoMenu;
	private JMenuItem cytoMenu;
	private JMenuItem gyneMenu;
	private JMenuItem patientMenu;
	private JPopupMenu searchPopup;
	private JMenuItem searchPatientMenu;
	private JMenuItem searchRecordMenu;
	
	private ListProvider patientList;
	private ListProvider histopathologyList;
	private ListProvider gynecologyList;
	private ListProvider cytologyList;
	private ListProvider searchList;
	
	public ContentPanel(MainMenu menu)
	{
		super(new CardLayout());
		histopathologyList = new ListProvider();
		gynecologyList = new ListProvider();
		cytologyList = new ListProvider();
		patientList = new ListProvider();
		searchList = new ListProvider();
		createListPanel();
		createSettingsPanel();
		add(Constants.TitleConstants.RECORDS, listPanel);
		add(Constants.TitleConstants.PREFERENCES, settingsPanel);
		headerLabel.setText(Constants.TitleConstants.HISTOPATHOLOGY);
		currentView = Constants.TitleConstants.HISTOPATHOLOGY;
	}
	
	public void createListPanel()
	{
		listPanel = new JPanel(new GridBagLayout());
		listPanel.setBackground(Color.white);
		
		topPanel = new JPanel(new GridBagLayout());
		topPanel.setBackground(Color.white);
		
		newButton = new JButton("create");
		refreshButton = new JButton("refresh");
		searchButton = new JButton("search");
		newButton.setOpaque(true);
		refreshButton.setOpaque(true);
		searchButton.setOpaque(true);
		newButton.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.MENU));
		refreshButton.setFont(newButton.getFont());
		searchButton.setFont(newButton.getFont());
		newButton.setContentAreaFilled(false);
		refreshButton.setContentAreaFilled(false);
		searchButton.setContentAreaFilled(false);
		newButton.setBorderPainted(false);
		refreshButton.setBorderPainted(false);
		searchButton.setBorderPainted(false);
		newButton.setFocusPainted(false);
		refreshButton.setFocusPainted(false);
		searchButton.setFocusPainted(false);
		filterField = new JTextField(25);
		headerLabel = new JLabel();
		headerLabel.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		newButton.setOpaque(true);
		newButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		refreshButton.setOpaque(true);
		refreshButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		searchButton.setOpaque(true);
		searchButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		
		newButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/create.png")));
		searchButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/search.png")));
		refreshButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/refresh.png")));
		newButton.setIconTextGap(7);
		searchButton.setIconTextGap(7);
		refreshButton.setIconTextGap(7);
		
		createPopup = new JPopupMenu(){
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.WHITE);
			    g.fillRect(0,0,getWidth(), getHeight());
			}
		};
		histoMenu = new JMenuItem("histopathology record");
		cytoMenu = new JMenuItem("cytology record");
		gyneMenu = new JMenuItem("gynecology record");
		patientMenu = new JMenuItem("patient");
		createPopup.add(histoMenu);
		createPopup.add(cytoMenu);
		createPopup.add(gyneMenu);
		createPopup.add(patientMenu);
		createPopup.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		
		searchPopup = new JPopupMenu(){
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.WHITE);
			    g.fillRect(0,0,getWidth(), getHeight());
			}
		};
		searchPatientMenu = new JMenuItem("patient");
		searchRecordMenu = new JMenuItem("record");
		searchPopup.add(searchRecordMenu);
		searchPopup.add(searchPatientMenu);
		searchPopup.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 1.0;
		topPanel.add(newButton, c);
		c.gridx = 1;
		topPanel.add(searchButton, c);
		c.gridx = 2;
		topPanel.add(refreshButton, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		topPanel.add(filterField, c);
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(10,0,0,0);
		topPanel.add(headerLabel, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.insets = new Insets(0,0,10,0);
		c.fill = GridBagConstraints.BOTH;
		listPanel.add(topPanel, c);
		
		bottomPanel = new JPanel(new CardLayout());
		bottomPanel.setBackground(Color.white);
		
		histopathologyPanel = new JPanel();
		histopathologyPanel.setBackground(Color.white);
		histopathologyPanel.add(histopathologyList.getPanel());
		gynecologyPanel = new JPanel();
		gynecologyPanel.setBackground(Color.white);
		gynecologyPanel.add(gynecologyList.getPanel());
		cytologyPanel = new JPanel();
		cytologyPanel.setBackground(Color.white);
		cytologyPanel.add(cytologyList.getPanel());
		patientPanel = new JPanel();
		patientPanel.setBackground(Color.white);
		patientPanel.add(patientList.getPanel());
		searchPanel = new JPanel();
		searchPanel.setBackground(Color.white);
		searchPanel.add(searchList.getPanel());
		JPanel pathPanel = new JPanel(new GridLayout(0, 1)); //list for pathologists
		JPanel physPanel = new JPanel(new GridLayout(0, 1)); //list for physicians
		JPanel specPanel = new JPanel(new GridLayout(0, 1)); //list for specimens
		bottomPanel.add(Constants.TitleConstants.HISTOPATHOLOGY, histopathologyPanel);
		bottomPanel.add(Constants.TitleConstants.GYNECOLOGY, gynecologyPanel);
		bottomPanel.add(Constants.TitleConstants.CYTOLOGY, cytologyPanel);
		bottomPanel.add(Constants.TitleConstants.PATIENTS,patientPanel);
		bottomPanel.add(Constants.TitleConstants.SEARCH_RESULT, searchPanel);
		bottomPanel.add(Constants.TitleConstants.PATHOLOGISTS, pathPanel);
		bottomPanel.add(Constants.TitleConstants.PHYSICIANS, physPanel);
		bottomPanel.add(Constants.TitleConstants.SPECIMENS, specPanel);
		
		
		c.gridy = 1;
		c.gridheight = 2;
		listPanel.add(bottomPanel, c);
		
		detailsPanel = new JPanel();
		detailsPanel.setBackground(Color.white);
		detailsPanel.setPreferredSize(new Dimension(700,600));
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		listPanel.add(detailsPanel, c);
		
		
	}
	
	public void createSettingsPanel()
	{
		settingsPanel = new JPanel(new GridBagLayout());
		settingsPanel.setBackground(Color.white);
		JLabel header = new JLabel("Preferences");
		header.setFont(MainMenu.PRIMARY_FONT.deriveFont(Constants.StyleConstants.HEADER));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		settingsPanel.add(header, c);
	}
	
	public void addListener(CustomListener listener)
	{
		histopathologyList.addListener(listener);
		cytologyList.addListener(listener);
		gynecologyList.addListener(listener);
		patientList.addListener(listener);
		searchList.addListener(listener);
		newButton.addActionListener(new ActionListener()
		{		
			public void actionPerformed(ActionEvent e) 
			{
				JButton button = (JButton)e.getSource();
				createPopup.show(listPanel, button.getX() + button.getHeight()/2,
						button.getY() + button.getHeight());
			}
			
		});
		searchButton.addActionListener(new ActionListener()
		{		
			public void actionPerformed(ActionEvent e) 
			{
				JButton button = (JButton)e.getSource();
				searchPopup.show(listPanel, button.getX() + button.getHeight()/2,
						button.getY() + button.getHeight());
			}
			
		});
		refreshButton.addActionListener(listener);
		histoMenu.addActionListener(listener);
		cytoMenu.addActionListener(listener);
		gyneMenu.addActionListener(listener);
		patientMenu.addActionListener(listener);
		histoMenu.setActionCommand(Constants.ActionConstants.NEW_HISTOPATHOLOGY);
		cytoMenu.setActionCommand(Constants.ActionConstants.NEW_CYTOTOLOGY);
		gyneMenu.setActionCommand(Constants.ActionConstants.NEW_GYNENECOLOGY);
		patientMenu.setActionCommand(Constants.ActionConstants.NEW_PATIENT);
		searchPatientMenu.setActionCommand(ActionConstants.SEARCH_PATIENT);
		searchRecordMenu.setActionCommand(ActionConstants.SEARCH_RECORD);
		refreshButton.setActionCommand(Constants.ActionConstants.REFRESH);
		searchPatientMenu.addActionListener(listener);
		searchRecordMenu.addActionListener(listener);
		filterField.addKeyListener(listener);
		newButton.addMouseListener(listener);
		searchButton.addMouseListener(listener);
		refreshButton.addMouseListener(listener);
		
	}
	
	public void changeView(String view)
	{
		if(!currentView.equals(view))
		{
			CardLayout mainLayout = (CardLayout)getLayout();
			CardLayout subLayout = (CardLayout)bottomPanel.getLayout();
			switch(view)
			{
				case Constants.TitleConstants.HISTOPATHOLOGY: 
								  mainLayout.show(this, Constants.TitleConstants.RECORDS);
								  subLayout.show(bottomPanel, view);
								  headerLabel.setText(view);
								  break;
				case Constants.TitleConstants.GYNECOLOGY:  
								  mainLayout.show(this, Constants.TitleConstants.RECORDS);
								  subLayout.show(bottomPanel, view);
								  headerLabel.setText(view);
								  break;
				case Constants.TitleConstants.CYTOLOGY:  
								  mainLayout.show(this, Constants.TitleConstants.RECORDS);
								  subLayout.show(bottomPanel,view);
								  headerLabel.setText(view);
								  break;
				case Constants.TitleConstants.PATHOLOGISTS:  
								  mainLayout.show(this, Constants.TitleConstants.RECORDS);
								  subLayout.show(bottomPanel, view);
								  headerLabel.setText(view);
								  break;
				case Constants.TitleConstants.PATIENTS: 
									mainLayout.show(this, Constants.TitleConstants.RECORDS);
									subLayout.show(bottomPanel, view);
									headerLabel.setText(view);
									break;
				case Constants.TitleConstants.PHYSICIANS:  
								  mainLayout.show(this, Constants.TitleConstants.RECORDS);
								  subLayout.show(bottomPanel, view);
								  headerLabel.setText(view);
								  break;
				case Constants.TitleConstants.SPECIMENS:  
								  mainLayout.show(this, Constants.TitleConstants.RECORDS);
								  subLayout.show(bottomPanel, view);
								  headerLabel.setText(view);
								  break;
				case Constants.TitleConstants.PREFERENCES: 
								  mainLayout.show(this, view);
								  break;
				case Constants.TitleConstants.SEARCH_RESULT:
								  mainLayout.show(this, Constants.TitleConstants.RECORDS);
								  subLayout.show(bottomPanel, view);
								  headerLabel.setText(view);
			}
			filterField.setText("");
			currentView = view;
		}
	}

	public String getCurrentView()
	{
		return currentView;
	}
	
	public String getFilter()
	{
		return filterField.getText();
	}
	
	public void updateHistopathologyList(List<ListItem> list)
	{
		histopathologyList.updateList(list);
		histopathologyPanel.revalidate();
	}
	
	public void updateGynecologyList(List<ListItem> list)
	{
		gynecologyList.updateList(list);
		gynecologyPanel.revalidate();
	}
	
	public void updateCytologyList(List<ListItem> list)
	{
		cytologyList.updateList(list);
		cytologyPanel.revalidate();
	}
	
	public void updatePatientList(List<ListItem> list)
	{
		patientList.updateList(list);
		patientPanel.revalidate();
	}
	
	public void updateSearchList(List<ListItem> list)
	{
		searchList.updateList(list);
		searchPanel.revalidate();
	}
	
	public void setDetailsPanel(JPanel panel)
	{
		detailsPanel.removeAll();
		if(panel != null)
		{
			detailsPanel.add(((DetailPanel)panel).getScroller());
			detailsPanel.revalidate();
		}
		detailsPanel.repaint();
	}
}
