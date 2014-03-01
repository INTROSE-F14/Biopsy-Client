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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.introse.gui.MainMenu;
import org.introse.gui.customcomponent.ListItem;
import org.introse.gui.customcomponent.ListProvider;
import org.introse.gui.event.CustomListener;


public class ContentPanel extends JPanel {

	public static final String HISTO_PANEL = "Histopathology";
	public static final String GYNE_PANEL = "Gynecology";
	public static final String CYTO_PANEL = "Cytology";
	public static final String PATIENT_PANEL = "Patients";
	public static final String PATH_PANEL = "Pathologists";
	public static final String PHYS_PANEL = "Physicians";
	public static final String SPEC_PANEL = "Specimens";
	public static final String PREFERENCES_VIEW = "Preferences";
	public static final String RECORDS_VIEW = "Records";
	public static final String NEW_HISTO = "New Histopathology";
	public static final String NEW_GYNE = "New Gynecology";
	public static final String NEW_CYTO = "New Cytology";
	public static final String NEW_PATIENT = "New Patient";
	public static final String NEW_SPECIMEN = "New Specimen";
	public static final String NEW_PHYSICIAN = "New Physician";
	public static final String NEW_PATHOLOGIST = "New Pathologist";
	
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
	
	private ListProvider patientList;
	private ListProvider histopathologyList;
	private ListProvider gynecologyList;
	private ListProvider cytologyList;
	
	public ContentPanel(MainMenu menu)
	{
		super(new CardLayout());
		histopathologyList = new ListProvider();
		gynecologyList = new ListProvider();
		cytologyList = new ListProvider();
		patientList = new ListProvider();
		createListPanel();
		createSettingsPanel();
		add(RECORDS_VIEW, listPanel);
		add(PREFERENCES_VIEW, settingsPanel);
		headerLabel.setText(HISTO_PANEL);
		currentView = HISTO_PANEL;
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
		newButton.setFont(MainMenu.SECONDARY_FONT.deriveFont(MainMenu.MENU));
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
		headerLabel.setFont(MainMenu.SECONDARY_FONT.deriveFont(MainMenu.SUBHEADER));
		newButton.setOpaque(true);
		newButton.setBackground(Color.decode(MainMenu.NORMAL));
		refreshButton.setOpaque(true);
		refreshButton.setBackground(Color.decode(MainMenu.NORMAL));
		searchButton.setOpaque(true);
		searchButton.setBackground(Color.decode(MainMenu.NORMAL));
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
		JPanel pathPanel = new JPanel(new GridLayout(0, 1)); //list for pathologists
		JPanel physPanel = new JPanel(new GridLayout(0, 1)); //list for physicians
		JPanel specPanel = new JPanel(new GridLayout(0, 1)); //list for specimens
		bottomPanel.add(HISTO_PANEL, histopathologyPanel);
		bottomPanel.add(GYNE_PANEL, gynecologyPanel);
		bottomPanel.add(CYTO_PANEL, cytologyPanel);
		bottomPanel.add(PATIENT_PANEL,patientPanel);
		bottomPanel.add(PATH_PANEL, pathPanel);
		bottomPanel.add(PHYS_PANEL, physPanel);
		bottomPanel.add(SPEC_PANEL, specPanel);
		
		
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
		header.setFont(MainMenu.PRIMARY_FONT.deriveFont(MainMenu.HEADER));
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
		newButton.addActionListener(new ActionListener()
		{		
			public void actionPerformed(ActionEvent e) 
			{
				JButton button = (JButton)e.getSource();
				createPopup.show(listPanel, button.getX() + button.getHeight()/2,
						button.getY() + button.getHeight());
			}
			
		});
		searchButton.addActionListener(listener);
		refreshButton.addActionListener(listener);
		histoMenu.addActionListener(listener);
		cytoMenu.addActionListener(listener);
		gyneMenu.addActionListener(listener);
		patientMenu.addActionListener(listener);
		histoMenu.setActionCommand("NEW_HISTOPATHOLOGY_RECORD");
		cytoMenu.setActionCommand("NEW_CYTOLOGY_RECORD");
		gyneMenu.setActionCommand("NEW_GYNECOLOGY_RECORD");
		patientMenu.setActionCommand("NEW_PATIENT");
		searchButton.setActionCommand("SEARCH");
		refreshButton.setActionCommand("REFRESH");
		filterField.addKeyListener(listener);
		histoMenu.addMouseListener(listener);
		cytoMenu.addMouseListener(listener);
		gyneMenu.addMouseListener(listener);
		patientMenu.addMouseListener(listener);
		newButton.addMouseListener(listener);
		searchButton.addMouseListener(listener);
		refreshButton.addMouseListener(listener);
		
	}
	
	public void changeView(String view, boolean removeDetails)
	{
		if(!currentView.equals(view))
		{
			CardLayout mainLayout = (CardLayout)getLayout();
			CardLayout subLayout = (CardLayout)bottomPanel.getLayout();
			switch(view)
			{
				case HISTO_PANEL: mainLayout.show(this, ContentPanel.RECORDS_VIEW);
								  subLayout.show(bottomPanel, ContentPanel.HISTO_PANEL);
								  headerLabel.setText(view);
								  break;
				case GYNE_PANEL:  mainLayout.show(this, RECORDS_VIEW);
								  subLayout.show(bottomPanel, GYNE_PANEL);
								  headerLabel.setText(view);
								  break;
				case CYTO_PANEL:  mainLayout.show(this, RECORDS_VIEW);
								  subLayout.show(bottomPanel, CYTO_PANEL);
								  headerLabel.setText(view);
								  break;
				case PATH_PANEL:  mainLayout.show(this, RECORDS_VIEW);
								  subLayout.show(bottomPanel, PATH_PANEL);
								  headerLabel.setText(view);
								  break;
				case PATIENT_PANEL: mainLayout.show(this, RECORDS_VIEW);
									subLayout.show(bottomPanel, PATIENT_PANEL);
									headerLabel.setText(view);
									break;
				case PHYS_PANEL:  mainLayout.show(this, RECORDS_VIEW);
								  subLayout.show(bottomPanel, PHYS_PANEL);
								  headerLabel.setText(view);
								  break;
				case SPEC_PANEL:  mainLayout.show(this, RECORDS_VIEW);
								  subLayout.show(bottomPanel, SPEC_PANEL);
								  headerLabel.setText(view);
								  break;
				case PREFERENCES_VIEW: mainLayout.show(this, PREFERENCES_VIEW);
			}
			if(detailsPanel != null)
			{
				if(removeDetails)
				setDetailsPanel(null);
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
	
	public void setDetailsPanel(JPanel panel)
	{
		detailsPanel.removeAll();
		if(panel == null)
		{
			panel = new JPanel();
			panel.setBackground(Color.white);
		}
		detailsPanel.add(panel);
		detailsPanel.revalidate();
		detailsPanel.repaint();
	}
}
