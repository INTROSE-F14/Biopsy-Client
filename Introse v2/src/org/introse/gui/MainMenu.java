package org.introse.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.introse.core.Preferences;
import org.introse.core.dao.PatientDao;
import org.introse.core.dao.RecordDao;
import org.introse.gui.customcomponent.ListItem;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.ContentPanel;
import org.introse.gui.panel.NavigationPanel;


public class MainMenu {

	private JFrame frame;
	private JPanel mainPanel;
	private NavigationPanel navigationPanel;
	private ContentPanel contentPanel;
	
	public static Font PRIMARY_FONT;
	public static Font SECONDARY_FONT;
	public static final float HEADER = 25;
	public static final float SUBHEADER = 18;
	public static final float MENU = 15;
	public static final String HOVER = "#E6E8FA";
	public static final String PRESSED  = "#CCCCCC";
	public static final String SELECTED = "#33b5e5";
	public static final String NORMAL = "#FFFFFF";
	
	private RecordDao recordDao;
	private PatientDao patientDao;
	
	public MainMenu(RecordDao recordDao, PatientDao patientDao)
	{
		frame = new JFrame(Preferences.PROGRAM_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.recordDao = recordDao;
		this.patientDao = patientDao;
		try
		{
			PRIMARY_FONT = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/res/fonts/calibri.ttf"));
			SECONDARY_FONT = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/res/fonts/calibri.ttf"));
			
		}catch (FontFormatException | IOException e) {e.printStackTrace();}
		
		mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(Color.white);
		createNavigationPanel();
		createContentPanel();
		frame.setContentPane(mainPanel);
	}
	
	
	public void addListener(CustomListener listener)
	{
		navigationPanel.addListener(listener);
		contentPanel.addListener(listener);
	}
	
	public void showGUI()
	{
		frame.pack();
		frame.setMinimumSize(new Dimension(frame.getWidth(), frame.getHeight()));
		frame.setVisible(true);
	}
	
	private void createNavigationPanel()
	{
		navigationPanel = new NavigationPanel();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(20,20,20,20);
		mainPanel.add(navigationPanel, c);
	}
	
	private void createContentPanel()
	{
		contentPanel = new ContentPanel(this);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 3;
		c.insets = new Insets(20,20,20,20);
		mainPanel.add(contentPanel);
	}
	
	public ContentPanel getContentPanel()
	{
		return contentPanel;
	}
	
	public NavigationPanel getNavigationPanel()
	{
		return navigationPanel;
	}
	
	public void exit()
	{
		frame.dispose();
	}
	
	public RecordDao getRecordDao()
	{
		return recordDao;
	}
	
	public PatientDao getPatientDao()
	{
		return patientDao;
	}
	
	public void viewItem(ListItem item)
	{
		contentPanel.viewItem(item);
	}
}
