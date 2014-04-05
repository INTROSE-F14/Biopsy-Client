package org.introse.gui.window;
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
	
	public MainMenu()
	{
		frame = new JFrame(Preferences.PROGRAM_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		try
		{
			PRIMARY_FONT = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/res/fonts/calibri.ttf"));
			SECONDARY_FONT = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/res/fonts/calibri_light.ttf"));
			
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
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(100,20,20,50);
		mainPanel.add(navigationPanel, c);
	}
	
	private void createContentPanel()
	{
		contentPanel = new ContentPanel(this);
		contentPanel.setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.75),
				(int)(Preferences.getScreenHeight() * 0.9)));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 1;
		c.gridy = 0;
		mainPanel.add(contentPanel, c);
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
}
