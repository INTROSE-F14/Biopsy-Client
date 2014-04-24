package org.introse.gui.panel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.StyleConstants;
import org.introse.Constants.TitleConstants;
import org.introse.gui.event.CustomListener;
import org.introse.gui.event.ListListener;
import org.introse.gui.event.TabListener;
import org.introse.gui.window.LoginWindow;
import org.introse.gui.window.MainMenu;
	
	
public class ContentPanel extends JPanel 
{	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String currentView;
		private String previousView;
		private JPanel topPanel;
		private JPanel itemPanel;
		private JPanel listPanel;
		private JPanel settingsPanel;
		private JPanel detailsPanel;
		private DictionaryPanel dictionaryPanel;
		private ToolsPanel toolsPanel;
		private JButton newButton;
		private JButton searchButton;
		private JButton refreshButton;
		private JLabel headerLabel;
		private JLabel countLabel;
		
		private ListPanel patientList;
		private ListPanel histopathologyList;
		private ListPanel gynecologyList;
		private ListPanel cytologyList;
		private ListPanel searchList;
		
		public ContentPanel(MainMenu menu)
		{
			super(new CardLayout());
			histopathologyList = new ListPanel(SwingConstants.HORIZONTAL, 8, 4);
			gynecologyList = new ListPanel(SwingConstants.HORIZONTAL, 8, 4);
			cytologyList = new ListPanel(SwingConstants.HORIZONTAL, 8, 4);
			patientList = new ListPanel(SwingConstants.HORIZONTAL, 10, 5);
			searchList = new ListPanel(SwingConstants.HORIZONTAL, 8, 4);
			createListPanel();
			createSettingsPanel();
			add(Constants.TitleConstants.RECORDS, listPanel);
			add(Constants.TitleConstants.PREFERENCES, settingsPanel);
			add(Constants.TitleConstants.DETAIL_PANEL, detailsPanel);
			currentView = TitleConstants.HISTOPATHOLOGY;
			previousView = null;
		}
		
		public void createListPanel()
		{
			listPanel = new JPanel(new GridBagLayout()); 
			listPanel.setBackground(Color.white);
			topPanel = new JPanel(new GridBagLayout());
			topPanel.setBackground(Color.white);
			
			newButton = new JButton();
			refreshButton = new JButton();
			searchButton = new JButton();
			
			newButton.setToolTipText("new");
			refreshButton.setToolTipText("refresh");
			searchButton.setToolTipText("find");
			newButton.setOpaque(true);
			refreshButton.setOpaque(true);
			searchButton.setOpaque(true);
			newButton.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.MENU));
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
			headerLabel = new JLabel();
			headerLabel.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.HEADER));
			headerLabel.setForeground(Color.decode(StyleConstants.GRAY));
			countLabel = new JLabel("0");
			countLabel.setFont(headerLabel.getFont());
			countLabel.setForeground(Color.LIGHT_GRAY);
			newButton.setOpaque(true);
			newButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
			refreshButton.setOpaque(true);
			refreshButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
			searchButton.setOpaque(true);
			searchButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
			
			newButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_new.png")));
			newButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_new_hover.png")));
			searchButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_search.png")));
			searchButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_search_hover.png")));
			refreshButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_refresh.png")));
			refreshButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_refresh_hover.png")));
			newButton.setIconTextGap(7);
			searchButton.setIconTextGap(7);
			refreshButton.setIconTextGap(7);
			
			itemPanel = new JPanel(new CardLayout());
			itemPanel.setBackground(Color.white);
			dictionaryPanel = new DictionaryPanel();
			itemPanel.add(Constants.TitleConstants.HISTOPATHOLOGY, histopathologyList);
			itemPanel.add(Constants.TitleConstants.GYNECOLOGY, gynecologyList);
			itemPanel.add(Constants.TitleConstants.CYTOLOGY, cytologyList);
			itemPanel.add(Constants.TitleConstants.PATIENTS,patientList);
			itemPanel.add(Constants.TitleConstants.SEARCH_RESULT, searchList);
			itemPanel.add(Constants.TitleConstants.DICTIONARY, dictionaryPanel);
			detailsPanel = new JPanel(new GridBagLayout());
			detailsPanel.setBackground(Color.white);

			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.WEST;
			c.fill = GridBagConstraints.NONE;
			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(0,40,0,20);
			listPanel.add(headerLabel, c);
			c.gridx = 1;
			c.insets = new Insets(0,0,0,10);
			listPanel.add(countLabel, c);
			c.gridx = 2;
			c.insets = new Insets(0,0,0,5);
			listPanel.add(newButton, c);
			c.gridx = 3;
			listPanel.add(searchButton, c);
			c.gridx = 4;
			listPanel.add(refreshButton, c);
			c.gridy = 1;
			c.gridx = 0;
			c.gridwidth = 5;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(0,40,0,0);
		//	listPanel.add(filterField, c);
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 2;
			c.gridheight = 3;
			c.gridwidth = 7;
			c.weighty = 1.0;
			c.weightx = 1.0;
			c.insets = new Insets(20,40,20,40);
			listPanel.add(itemPanel, c);
		}
		
		public void createSettingsPanel()
		{
			settingsPanel = new JPanel(new GridBagLayout());
			settingsPanel.setBackground(Color.white);
			
			toolsPanel = new ToolsPanel();
			toolsPanel.setBackground(Color.white);
			
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.NORTHWEST;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1.0;
			c.weighty = 1.0;
			settingsPanel.add(toolsPanel,c);
		}
		
		public void addListListener(ListListener listener)
		{
			histopathologyList.addMouseListener(listener);
			gynecologyList.addMouseListener(listener);
			cytologyList.addMouseListener(listener);
			searchList.addMouseListener(listener);
			patientList.addMouseListener(listener);
			
		}
		
		public void addTabListener(TabListener listener)
		{
			dictionaryPanel.addTabListener(listener);
		}
		
		public void addListener(CustomListener listener)
		{
			dictionaryPanel.addButtonListener(listener);
			toolsPanel.addListener(listener);
			histopathologyList.addButtonListener(listener);
			cytologyList.addButtonListener(listener);
			gynecologyList.addButtonListener(listener);
			patientList.addButtonListener(listener);
			searchList.addButtonListener(listener);
			newButton.addActionListener(listener);
			searchButton.addActionListener(listener);
			refreshButton.addActionListener(listener);
			refreshButton.setActionCommand(Constants.ActionConstants.REFRESH);
		}
		
		public void changeView(String view)
		{
				CardLayout mainLayout = (CardLayout)getLayout();
				CardLayout subLayout = (CardLayout)itemPanel.getLayout();
				switch(view)
				{
					case Constants.TitleConstants.HISTOPATHOLOGY: 
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  newButton.setActionCommand(ActionConstants.NEW_HISTOPATHOLOGY);
									  searchButton.setActionCommand(ActionConstants.SEARCH_RECORD);
									  searchButton.setVisible(true);
									  newButton.setVisible(true);
									  countLabel.setVisible(true);
									  break;
					case Constants.TitleConstants.GYNECOLOGY:  
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  newButton.setActionCommand(ActionConstants.NEW_GYNENECOLOGY);
									  searchButton.setActionCommand(ActionConstants.SEARCH_RECORD);
									  searchButton.setVisible(true);
									  newButton.setVisible(true);
									  countLabel.setVisible(true);
									  break;
					case Constants.TitleConstants.CYTOLOGY:  
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel,view);
									  headerLabel.setText(view);
									  newButton.setActionCommand(ActionConstants.NEW_CYTOTOLOGY);
									  searchButton.setActionCommand(ActionConstants.SEARCH_RECORD);
									  searchButton.setVisible(true);
									  newButton.setVisible(true);
									  countLabel.setVisible(true);
									  break;
					case Constants.TitleConstants.DICTIONARY:  
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  searchButton.setVisible(false);
									  newButton.setVisible(false);
									  countLabel.setVisible(false);
									  break;
					case TitleConstants.PATHOLOGISTS: dictionaryPanel.setSelectedTab(view);
									  break;
					case TitleConstants.PHYSICIANS: dictionaryPanel.setSelectedTab(view);
									  break;
					case TitleConstants.SPECIMENS: dictionaryPanel.setSelectedTab(view);
									  break;
					case Constants.TitleConstants.PATIENTS: 
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  newButton.setActionCommand(ActionConstants.NEW_PATIENT);
									  searchButton.setActionCommand(ActionConstants.SEARCH_PATIENT);
									  searchButton.setVisible(true);
									  newButton.setVisible(true);
									  countLabel.setVisible(true);
									  break;
					case Constants.TitleConstants.PREFERENCES: 
									  mainLayout.show(this, view);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  break;
					case Constants.TitleConstants.SEARCH_RESULT:
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  searchButton.setVisible(true);
									  newButton.setVisible(false);
									  countLabel.setVisible(true);
									  break;
					case Constants.TitleConstants.DETAIL_PANEL:
						  				mainLayout.show(this, Constants.TitleConstants.DETAIL_PANEL);
						
				}
				if(!currentView.equals(view) && !currentView.equals(TitleConstants.DETAIL_PANEL))
					previousView = currentView;
				currentView = view;
		}
		
		public void setCountLabel(String view, int count)
		{
			if(view.equals(currentView))
				countLabel.setText("" + count);
		}
	
		public String getCurrentView()
		{
			return currentView;
		}
		
		public String getPreviousView()
		{
			return previousView;
		}	
		
		public void setDetailsPanel(JPanel panel)
		{
			detailsPanel.removeAll();
			if(panel != null)
			{
				GridBagConstraints c = new GridBagConstraints();
				c.anchor = GridBagConstraints.NORTHWEST;
				c.fill = GridBagConstraints.BOTH;
				c.weightx = 1.0;
				c.weighty = 1.0;
				detailsPanel.add(panel, c);
			}
			detailsPanel.revalidate();
			detailsPanel.repaint();
		}
		
		public ToolsPanel getToolsPanel()
		{
			return toolsPanel;
		}
		
		public ListPanel getPanel(String view)
		{
			switch(view)
			{
			case TitleConstants.HISTOPATHOLOGY: return histopathologyList;
			case TitleConstants.GYNECOLOGY: return gynecologyList;
			case TitleConstants.CYTOLOGY: return cytologyList;
			case TitleConstants.PATIENTS: return patientList;
			case TitleConstants.SEARCH_RESULT: return searchList;
			case TitleConstants.PHYSICIANS: return dictionaryPanel.getPanel(view);
			case TitleConstants.PATHOLOGISTS: return dictionaryPanel.getPanel(view);
			case TitleConstants.SPECIMENS: return dictionaryPanel.getPanel(view);
			}
			return null;
		}
		
		public DictionaryPanel getDickPanel()
		{
			return dictionaryPanel;
		}
	}
