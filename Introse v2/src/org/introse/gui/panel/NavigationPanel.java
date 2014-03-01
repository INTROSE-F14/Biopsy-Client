package org.introse.gui.panel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.introse.gui.MainMenu;
import org.introse.gui.customcomponent.NavButton;


public class NavigationPanel extends JPanel
{

	
	private NavButton histoButton;
	private NavButton gyneButton;
	private NavButton cytoButton;
	private NavButton patientButton;	
	private NavButton pathButton;
	private NavButton physButton;
	private NavButton specButton;
	private NavButton prefButton;
	private NavButton logoutButton;

	public NavigationPanel()
	{

		super(new GridBagLayout());
		setBackground(Color.white);
		JPanel recordsPanel = new JPanel(new GridLayout(0,1,0,0));
		recordsPanel.setBackground(Color.white);
		JLabel recordsPanelHeader = new JLabel("Records");
		recordsPanelHeader.setFont(MainMenu.PRIMARY_FONT.deriveFont(MainMenu.HEADER));
		histoButton = new NavButton(MainMenu.NORMAL, MainMenu.HOVER,
				MainMenu.PRESSED, MainMenu.SELECTED, "histopathology", true);
		gyneButton = new NavButton(MainMenu.NORMAL,MainMenu.HOVER,
				MainMenu.PRESSED, MainMenu.SELECTED, "gynecology", false);
		cytoButton = new NavButton(MainMenu.NORMAL, MainMenu.HOVER,
				MainMenu.PRESSED, MainMenu.SELECTED, "cytology", false);
		patientButton = new NavButton(MainMenu.NORMAL, MainMenu.HOVER,
				MainMenu.PRESSED, MainMenu.SELECTED, "patients", false);
		histoButton.setFont(MainMenu.SECONDARY_FONT.deriveFont(MainMenu.SUBHEADER));
		gyneButton.setFont(histoButton.getFont());
		cytoButton.setFont(histoButton.getFont());
		patientButton.setFont(histoButton.getFont());
		histoButton.setHorizontalAlignment(SwingConstants.LEFT);
		gyneButton.setHorizontalAlignment(SwingConstants.LEFT);
		cytoButton.setHorizontalAlignment(SwingConstants.LEFT);
		patientButton.setHorizontalAlignment(SwingConstants.LEFT);
		recordsPanel.add(recordsPanelHeader);
		recordsPanel.add(histoButton);
		recordsPanel.add(gyneButton);
		recordsPanel.add(cytoButton);
		recordsPanel.add(patientButton);
		JPanel maintenancePanel = new JPanel(new GridLayout(0,1,0,0));
		maintenancePanel.setBackground(Color.white);
		pathButton = new NavButton(MainMenu.NORMAL,MainMenu.HOVER,
				MainMenu.PRESSED,MainMenu.SELECTED, "pathologist", false);
		physButton = new NavButton(MainMenu.NORMAL,MainMenu.HOVER,
				MainMenu.PRESSED,MainMenu.SELECTED,"physician", false);
		specButton = new NavButton(MainMenu.NORMAL,MainMenu.HOVER,
				MainMenu.PRESSED,MainMenu.SELECTED,"specimen", false);
		JLabel maintenanceHeader = new JLabel("Maintenance");
		maintenanceHeader.setFont(MainMenu.PRIMARY_FONT.deriveFont(MainMenu.HEADER));
		maintenancePanel.add(maintenanceHeader);
		maintenancePanel.add(pathButton);
		maintenancePanel.add(physButton);
		maintenancePanel.add(specButton);
		pathButton.setFont(histoButton.getFont());
		physButton.setFont(histoButton.getFont());
		specButton.setFont(histoButton.getFont());
		pathButton.setHorizontalAlignment(SwingConstants.LEFT);
		physButton.setHorizontalAlignment(SwingConstants.LEFT);
		specButton.setHorizontalAlignment(SwingConstants.LEFT);
		
		JPanel othersPanel = new JPanel(new GridLayout(0,1,0,0));
		othersPanel.setBackground(Color.white);
		prefButton = new NavButton(MainMenu.NORMAL,MainMenu.HOVER,
				MainMenu.PRESSED,MainMenu.SELECTED,"preferences", false);
		logoutButton = new NavButton(MainMenu.NORMAL,MainMenu.HOVER,
				MainMenu.PRESSED,MainMenu.SELECTED,"log out", false);
		JLabel othersHeader = new JLabel("Others");
		othersHeader.setFont(MainMenu.PRIMARY_FONT.deriveFont(MainMenu.HEADER));
		othersPanel.add(othersHeader);
		othersPanel.add(prefButton);
		othersPanel.add(logoutButton);
		prefButton.setFont(histoButton.getFont());
		logoutButton.setFont(histoButton.getFont());
		prefButton.setHorizontalAlignment(SwingConstants.LEFT);
		logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
		

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0,0,20,0);
		c.gridx = 0;
		c.gridy = 0;
		add(recordsPanel, c);
		c.gridy = 1;
		add(maintenancePanel, c);
		c.gridy = 2;
		add(othersPanel, c);
	}
	
	public void addListener(ActionListener listener)
	{
		histoButton.addActionListener(listener);
		gyneButton.addActionListener(listener);
		cytoButton.addActionListener(listener);
		patientButton.addActionListener(listener);	
		pathButton.addActionListener(listener);
		physButton.addActionListener(listener);
		specButton.addActionListener(listener);
		prefButton.addActionListener(listener);
		logoutButton.addActionListener(listener);
		
		histoButton.setActionCommand(ContentPanel.HISTO_PANEL);
		gyneButton.setActionCommand(ContentPanel.GYNE_PANEL);
		cytoButton.setActionCommand(ContentPanel.CYTO_PANEL);
		patientButton.setActionCommand(ContentPanel.PATIENT_PANEL);	
		pathButton.setActionCommand(ContentPanel.PATH_PANEL);
		physButton.setActionCommand(ContentPanel.PHYS_PANEL);
		specButton.setActionCommand(ContentPanel.SPEC_PANEL);
		prefButton.setActionCommand(ContentPanel.PREFERENCES_VIEW);
		logoutButton.setActionCommand("LOG_OUT");
		histoButton.setName(ContentPanel.HISTO_PANEL);
		gyneButton.setName(ContentPanel.GYNE_PANEL);
		cytoButton.setName(ContentPanel.CYTO_PANEL);
		patientButton.setName(ContentPanel.PATIENT_PANEL);	
		pathButton.setName(ContentPanel.PATH_PANEL);
		physButton.setName(ContentPanel.PHYS_PANEL);
		specButton.setName(ContentPanel.SPEC_PANEL);
		prefButton.setName("VIEW_PREFERENCES");
		logoutButton.setName("LOG_OUT");
	}
	
	public void setSelectedButton(Object button)
	{
		if(histoButton.equals(button))
			((NavButton)histoButton).setState(true);
		else ((NavButton)histoButton).setState(false);
		
		if(gyneButton.equals(button))
			((NavButton)gyneButton).setState(true);
		else ((NavButton)gyneButton).setState(false);
		
		if(cytoButton.equals(button))
			((NavButton)cytoButton).setState(true);
		else ((NavButton)cytoButton).setState(false);
		
		if(patientButton.equals(button))
			((NavButton)patientButton).setState(true);
		else ((NavButton)patientButton).setState(false);
		
		if(pathButton.equals(button))
			((NavButton)pathButton).setState(true);
		else ((NavButton)pathButton).setState(false);
		
		if(physButton.equals(button))
			((NavButton)physButton).setState(true);
		else ((NavButton)physButton).setState(false);
		
		if(specButton.equals(button))
			((NavButton)specButton).setState(true);
		else ((NavButton)specButton).setState(false);
		
		if(prefButton.equals(button))
			((NavButton)prefButton).setState(true);
		else ((NavButton)prefButton).setState(false);
	}
	
	public void setSelectedButton(String button)
	{
		((NavButton)histoButton).setState(false);
		((NavButton)gyneButton).setState(false);
		((NavButton)cytoButton).setState(false);
		((NavButton)patientButton).setState(false);
		((NavButton)pathButton).setState(false);
		((NavButton)physButton).setState(false);
		((NavButton)specButton).setState(false);
		((NavButton)prefButton).setState(false);
		
		switch(button)
		{
		case ContentPanel.HISTO_PANEL:	((NavButton)histoButton).setState(true);
										break;
		case ContentPanel.GYNE_PANEL:	((NavButton)gyneButton).setState(true);
										break;
		case ContentPanel.CYTO_PANEL:	((NavButton)cytoButton).setState(true);
										break;
		case ContentPanel.PATH_PANEL:	((NavButton)pathButton).setState(true);
										break;
		case ContentPanel.PATIENT_PANEL:	((NavButton)patientButton).setState(true);
											break;
		case ContentPanel.PHYS_PANEL:	((NavButton)physButton).setState(true);
										break;
		case ContentPanel.SPEC_PANEL:	((NavButton)specButton).setState(true);
										break;
		case ContentPanel.PREFERENCES_VIEW:	((NavButton)prefButton).setState(true);
		}
	}
}
