package org.introse.gui.window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.introse.Constants.StyleConstants;
import org.introse.core.Preferences;
import org.introse.gui.event.CustomListener;
import org.introse.gui.event.ListListener;
import org.introse.gui.event.NavigationListener;
import org.introse.gui.event.TabListener;
import org.introse.gui.panel.ContentPanel;
import org.introse.gui.panel.NavigationPanel;


public class MainMenu extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private NavigationPanel navigationPanel;
	private ContentPanel contentPanel;
	
	public MainMenu()
	{
		super(Preferences.PROGRAM_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setIconImage(LoginWindow.PROGRAM_ICON);
		mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		createNavigationPanel();
		createContentPanel();
		setContentPane(mainPanel);
	}
	
	public void addListListener(ListListener listener)
	{
		contentPanel.addListListener(listener);
	}
	
	public void addListener(CustomListener listener)
	{
		contentPanel.addListener(listener);
	}
	
	public void addNavigationListener(NavigationListener listener)
	{
		navigationPanel.addListener(listener);
	}
	
	public void addTabListener(TabListener listener)
	{
		contentPanel.addTabListener(listener);
	}
	
	public void showGUI()
	{
		pack();
		setMinimumSize(new Dimension(getWidth(), getHeight()));
		setVisible(true);
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
		dispose();
	}
}
