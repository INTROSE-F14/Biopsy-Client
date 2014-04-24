package org.introse.gui.panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.StyleConstants;
import org.introse.Constants.TitleConstants;
import org.introse.gui.button.NavButton;
import org.introse.gui.event.NavigationListener;
import org.introse.gui.window.LoginWindow;


public class NavigationPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NavButton histoButton;
	private NavButton gyneButton;
	private NavButton cytoButton;
	private NavButton patientButton;	
	private NavButton dictionaryButton;
	private NavButton toolsButton;
	private NavButton logoutButton;
	private JPanel recordsPanel;
	private JPanel maintenancePanel;
	private JPanel othersPanel;

	public NavigationPanel()
	{

		super(new GridBagLayout());
		setBackground(new Color(0f, 0f, 0f, 0f));
		initUI();
		layoutComponents();
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,20,0);
		c.gridx = 0;
		c.gridy = 0;
		add(recordsPanel, c);
		c.gridy = 1;
		add(maintenancePanel, c);
		c.gridy = 2;
		add(othersPanel, c);
	}
	
	private void initUI()
	{
		recordsPanel = new JPanel(new GridLayout(0,1,1,1));
		recordsPanel.setOpaque(true);
		recordsPanel.setBackground(Color.LIGHT_GRAY);
		JLabel recordHeader = new JLabel(TitleConstants.RECORDS.toUpperCase());
		recordHeader.setBorder(new EmptyBorder(0,10,0,0));
		recordHeader.setOpaque(true);
		recordHeader.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		recordHeader.setForeground(Color.decode(StyleConstants.GRAY));
		recordHeader.setFont(LoginWindow.PRIMARY_FONT.deriveFont(Font.BOLD, Constants.StyleConstants.MENU));
		histoButton = new NavButton(StyleConstants.PRIMARY_COLOR, "#00000", "histopathology", true);
		gyneButton = new NavButton(StyleConstants.PRIMARY_COLOR, "#00000", "gynecology", false);
		cytoButton = new NavButton(StyleConstants.PRIMARY_COLOR, "#00000", "cytology", false);
		patientButton = new NavButton(StyleConstants.PRIMARY_COLOR, "#00000", "patients", false);
		histoButton.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		gyneButton.setFont(histoButton.getFont());
		cytoButton.setFont(histoButton.getFont());
		patientButton.setFont(histoButton.getFont());
		recordsPanel.add(recordHeader);
		recordsPanel.add(histoButton);
		recordsPanel.add(gyneButton);
		recordsPanel.add(cytoButton);
		recordsPanel.add(patientButton);
		
		maintenancePanel = new JPanel(new GridLayout(0,1,1,1));
		maintenancePanel.setBackground(Color.LIGHT_GRAY);
		dictionaryButton = new NavButton(StyleConstants.PRIMARY_COLOR, "#00000", 
				TitleConstants.DICTIONARY.toLowerCase(), false);
		toolsButton = new NavButton(StyleConstants.PRIMARY_COLOR, "#00000", "tools", false);		
		JLabel maintenanceHeader = new JLabel(TitleConstants.MAINTENANCE.toUpperCase());
		maintenanceHeader.setBorder(new EmptyBorder(0,10,0,0));
		maintenanceHeader.setOpaque(true);
		maintenanceHeader.setFont(recordHeader.getFont());
		maintenanceHeader.setBackground(recordHeader.getBackground());
		maintenanceHeader.setForeground(recordHeader.getForeground());
		maintenancePanel.add(maintenanceHeader);
		maintenancePanel.add(dictionaryButton);
		maintenancePanel.add(toolsButton);
		dictionaryButton.setFont(histoButton.getFont());
		toolsButton.setFont(histoButton.getFont());
		
		
		othersPanel = new JPanel(new GridLayout(0,1,1,1));
		othersPanel.setBackground(Color.LIGHT_GRAY);
		logoutButton = new NavButton(StyleConstants.PRIMARY_COLOR, "#00000", "log out", false);
		JLabel othersHeader = new JLabel(TitleConstants.OTHERS.toUpperCase());
		othersHeader.setBorder(new EmptyBorder(0,10,0,0));
		othersHeader.setOpaque(true);
		othersHeader.setFont(recordHeader.getFont());
		othersHeader.setBackground(recordHeader.getBackground());
		othersHeader.setForeground(recordHeader.getForeground());
		othersPanel.add(othersHeader);
		othersPanel.add(logoutButton);
		logoutButton.setFont(histoButton.getFont());
	}
	
	public void addListener(NavigationListener listener)
	{
		histoButton.addMouseListener(listener);
		gyneButton.addMouseListener(listener);
		cytoButton.addMouseListener(listener);
		patientButton.addMouseListener(listener);	
		dictionaryButton.addMouseListener(listener);
		toolsButton.addMouseListener(listener);
		logoutButton.addMouseListener(listener);
		
		histoButton.setName(Constants.TitleConstants.HISTOPATHOLOGY);
		gyneButton.setName(Constants.TitleConstants.GYNECOLOGY);
		cytoButton.setName(Constants.TitleConstants.CYTOLOGY);
		patientButton.setName(Constants.TitleConstants.PATIENTS);	
		dictionaryButton.setName(Constants.TitleConstants.PATHOLOGISTS);
		toolsButton.setName(Constants.TitleConstants.PREFERENCES);
		logoutButton.setName(ActionConstants.LOG_OUT);
		histoButton.setName(Constants.TitleConstants.HISTOPATHOLOGY);
		gyneButton.setName(Constants.TitleConstants.GYNECOLOGY);
		cytoButton.setName(Constants.TitleConstants.CYTOLOGY);
		patientButton.setName(Constants.TitleConstants.PATIENTS);	
		dictionaryButton.setName(Constants.TitleConstants.DICTIONARY);
		toolsButton.setName(Constants.TitleConstants.PREFERENCES);
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
		
		if(dictionaryButton.equals(button))
			((NavButton)dictionaryButton).setState(true);
		else ((NavButton)dictionaryButton).setState(false);

		if(toolsButton.equals(button))
			((NavButton)toolsButton).setState(true);
		else ((NavButton)toolsButton).setState(false);
		
	}
	
	public void setSelectedButton(String button)
	{
		
		((NavButton)histoButton).setState(false);
		((NavButton)gyneButton).setState(false);
		((NavButton)cytoButton).setState(false);
		((NavButton)patientButton).setState(false);
		((NavButton)dictionaryButton).setState(false);
		((NavButton)toolsButton).setState(false);
		
		switch(button)
		{
		case Constants.TitleConstants.HISTOPATHOLOGY:	
										((NavButton)histoButton).setState(true);
										break;
		case Constants.TitleConstants.GYNECOLOGY:	
										((NavButton)gyneButton).setState(true);
										break;
		case Constants.TitleConstants.CYTOLOGY:	
										((NavButton)cytoButton).setState(true);
										break;
		case Constants.TitleConstants.PATHOLOGISTS:	
										((NavButton)dictionaryButton).setState(true);
										break;
		case Constants.TitleConstants.PATIENTS:	
										((NavButton)patientButton).setState(true);
										break;
		case Constants.TitleConstants.PREFERENCES:	
										((NavButton)toolsButton).setState(true);
		}
	}
}
