	package org.introse.gui.panel;
	import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.TitleConstants;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.MainMenu;
	
	
	public class ContentPanel extends JPanel 
	{	
		private String currentView;
		private String previousView;
		private JPanel topPanel;
		private JPanel itemPanel;
		private JPanel listPanel;
		private JPanel settingsPanel;
		private JPanel detailsPanel;
		private JButton newButton;
		private JButton searchButton;
		private JButton refreshButton;
		private JTextField filterField;
		private JLabel headerLabel;
		private JLabel countLabel;
		
		private ListProvider patientList;
		private ListProvider histopathologyList;
		private ListProvider gynecologyList;
		private ListProvider cytologyList;
		private ListProvider searchList;
		
		public ContentPanel(MainMenu menu)
		{
			super(new CardLayout());
			histopathologyList = new ListProvider(SwingConstants.HORIZONTAL);
			gynecologyList = new ListProvider(SwingConstants.HORIZONTAL);
			cytologyList = new ListProvider(SwingConstants.HORIZONTAL);
			patientList = new ListProvider(SwingConstants.HORIZONTAL);
			searchList = new ListProvider(SwingConstants.HORIZONTAL);
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
			filterField = new JTextField("Quick filter", 25);
			filterField.setForeground(Color.GRAY);
			headerLabel = new JLabel();
			headerLabel.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.HEADER));
			countLabel = new JLabel("0");
			countLabel.setFont(headerLabel.getFont());
			countLabel.setForeground(Color.LIGHT_GRAY);
			newButton.setOpaque(true);
			newButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
			refreshButton.setOpaque(true);
			refreshButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
			searchButton.setOpaque(true);
			searchButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
			
			newButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/add.png")));
			newButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/add_rollover.png")));
			searchButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/find.png")));
			searchButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/find_rollover.png")));
			refreshButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/refresh.png")));
			refreshButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/refresh_rollover.png")));
			newButton.setIconTextGap(7);
			searchButton.setIconTextGap(7);
			refreshButton.setIconTextGap(7);
			
			itemPanel = new JPanel(new CardLayout());
			itemPanel.setBackground(Color.white);
			
			JPanel pathPanel = new JPanel(new GridLayout(0, 1)); //list for pathologists
			JPanel physPanel = new JPanel(new GridLayout(0, 1)); //list for physicians
			JPanel specPanel = new JPanel(new GridLayout(0, 1)); //list for specimens
			itemPanel.add(Constants.TitleConstants.HISTOPATHOLOGY, histopathologyList.getPanel());
			itemPanel.add(Constants.TitleConstants.GYNECOLOGY, gynecologyList.getPanel());
			itemPanel.add(Constants.TitleConstants.CYTOLOGY, cytologyList.getPanel());
			itemPanel.add(Constants.TitleConstants.PATIENTS,patientList.getPanel());
			itemPanel.add(Constants.TitleConstants.SEARCH_RESULT, searchList.getPanel());
			itemPanel.add(Constants.TitleConstants.PATHOLOGISTS, pathPanel);
			itemPanel.add(Constants.TitleConstants.PHYSICIANS, physPanel);
			itemPanel.add(Constants.TitleConstants.SPECIMENS, specPanel);
			
			detailsPanel = new JPanel();
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
			listPanel.add(filterField, c);
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
			newButton.addActionListener(listener);
			searchButton.addActionListener(listener);
			refreshButton.addActionListener(listener);
			refreshButton.setActionCommand(Constants.ActionConstants.REFRESH);
			filterField.addKeyListener(listener);
			filterField.addFocusListener(new FocusListener()
			{

				@Override
				public void focusGained(FocusEvent e) 
				{
					if(((JTextField)e.getComponent()).getText().equals(TitleConstants.QUICK_FILTER))
						((JTextField)e.getComponent()).setText("");
					((JTextField)e.getComponent()).setForeground(Color.black);
				}

				@Override
				public void focusLost(FocusEvent e) 
				{
					if(((JTextField)e.getComponent()).getText().length() < 1)
					{
						((JTextField)e.getComponent()).setText("Quick filter");
						((JTextField)e.getComponent()).setForeground(Color.GRAY);
					}
				}
				
			});
			
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
									  countLabel.setText(histopathologyList.getList().size()+"");
									  newButton.setActionCommand(ActionConstants.NEW_HISTOPATHOLOGY);
									  searchButton.setActionCommand(ActionConstants.SEARCH_RECORD);
									  break;
					case Constants.TitleConstants.GYNECOLOGY:  
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  countLabel.setText(gynecologyList.getList().size()+"");
									  newButton.setActionCommand(ActionConstants.NEW_GYNENECOLOGY);
									  searchButton.setActionCommand(ActionConstants.SEARCH_RECORD);
									  break;
					case Constants.TitleConstants.CYTOLOGY:  
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel,view);
									  headerLabel.setText(view);
									  countLabel.setText(cytologyList.getList().size()+"");
									  newButton.setActionCommand(ActionConstants.NEW_CYTOTOLOGY);
									  searchButton.setActionCommand(ActionConstants.SEARCH_RECORD);
									  break;
					case Constants.TitleConstants.PATHOLOGISTS:  
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  newButton.setActionCommand(ActionConstants.NEW_PATHOLOGIST);
									  break;
					case Constants.TitleConstants.PATIENTS: 
										mainLayout.show(this, Constants.TitleConstants.RECORDS);
										subLayout.show(itemPanel, view);
										headerLabel.setText(view);
										countLabel.setText(patientList.getList().size()+"");
										newButton.setActionCommand(ActionConstants.NEW_PATIENT);
										searchButton.setActionCommand(ActionConstants.SEARCH_PATIENT);
										break;
					case Constants.TitleConstants.PHYSICIANS:  
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  newButton.setActionCommand(ActionConstants.NEW_PHYSICIAN);
									  break;
					case Constants.TitleConstants.SPECIMENS:  
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  newButton.setActionCommand(ActionConstants.NEW_SPECIMEN);
									  break;
					case Constants.TitleConstants.PREFERENCES: 
									  mainLayout.show(this, view);
									  break;
					case Constants.TitleConstants.SEARCH_RESULT:
									  mainLayout.show(this, Constants.TitleConstants.RECORDS);
									  subLayout.show(itemPanel, view);
									  headerLabel.setText(view);
									  countLabel.setText(searchList.getList().size()+"");
									  break;
					case Constants.TitleConstants.DETAIL_PANEL:
						  				mainLayout.show(this, Constants.TitleConstants.DETAIL_PANEL);
						
				}
				filterField.setText(TitleConstants.QUICK_FILTER);
				filterField.setForeground(Color.GRAY);
				previousView = currentView;
				currentView = view;
		}
	
		public String getCurrentView()
		{
			return currentView;
		}
		
		public String getPreviousView()
		{
			return previousView;
		}
		
		public String getFilter()
		{
			return filterField.getText();
		}
		
		public void updateList(List<ListItem> list, String view)
		{
			switch(view)
			{
			case Constants.TitleConstants.HISTOPATHOLOGY: 
				 histopathologyList.updateList(list);
				 countLabel.setText(list.size()+"");
				 break;
			case Constants.TitleConstants.GYNECOLOGY:  
				gynecologyList.updateList(list);
							  countLabel.setText(list.size()+"");
							  break;
			case Constants.TitleConstants.CYTOLOGY:  
				cytologyList.updateList(list);
							  countLabel.setText(list.size()+"");
							  break;
			case Constants.TitleConstants.PATIENTS: 
				patientList.updateList(list);
								countLabel.setText(list.size()+"");
								break;
			case Constants.TitleConstants.SEARCH_RESULT:
				searchList.updateList(list);
							  countLabel.setText(list.size()+"");
			}
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