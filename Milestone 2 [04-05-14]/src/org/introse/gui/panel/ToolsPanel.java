package org.introse.gui.panel;



import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.introse.Constants.ActionConstants;
import org.introse.Constants.StyleConstants;
import org.introse.core.Preferences;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.LoginWindow;

public class ToolsPanel extends JPanel 
{	
	private JPanel overviewPanel;
	private JButton backupViewButton, restoreViewButton, exportViewButton;
	private BackupPanel backupPanel;
	private RestorePanel restorePanel;
	private ExportPanel exportPanel;
	
	public ToolsPanel() 
	{
		super(new CardLayout());
		initializeComponents();
		layoutComponents();
		add("Overview", overviewPanel);
		add(ActionConstants.VIEW_BACKUP, backupPanel);
		add(ActionConstants.VIEW_RESTORE, restorePanel);
		add(ActionConstants.VIEW_EXPORT, exportPanel);
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.insets = new Insets(0,0,0,50);
		overviewPanel.add(backupViewButton, c);
		c.gridx = 1;
		overviewPanel.add(restoreViewButton, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,0,0);
		overviewPanel.add(exportViewButton, c);
	}
	
	private void initializeComponents()
	{
		overviewPanel = new JPanel(new GridBagLayout());
		overviewPanel.setBackground(Color.white);
		backupViewButton = new JButton("Backup existing database");
		restoreViewButton = new JButton("Restore a backup file");
		exportViewButton = new JButton("Export to CSV");
		backupViewButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/backup.png")));
		restoreViewButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/restore.png")));
		exportViewButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/export.png")));
		backupViewButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		backupViewButton.setIconTextGap(10);
		restoreViewButton.setIconTextGap(10);
		exportViewButton.setIconTextGap(10);
		restoreViewButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		exportViewButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		backupViewButton.setHorizontalTextPosition(SwingConstants.CENTER);
		restoreViewButton.setHorizontalTextPosition(SwingConstants.CENTER);
		exportViewButton.setHorizontalTextPosition(SwingConstants.CENTER);
		backupViewButton.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		restoreViewButton.setFont(backupViewButton.getFont());
		exportViewButton.setFont(backupViewButton.getFont());
		backupViewButton.setOpaque(true);
		backupViewButton.setContentAreaFilled(false);
		backupViewButton.setBorderPainted(false);
		restoreViewButton.setOpaque(true);
		restoreViewButton.setContentAreaFilled(false);
		restoreViewButton.setBorderPainted(false);
		exportViewButton.setOpaque(true);
		exportViewButton.setContentAreaFilled(false);
		exportViewButton.setBorderPainted(false);
		backupPanel = new BackupPanel();
		restorePanel = new RestorePanel();
		exportPanel = new ExportPanel();
		
		backupPanel.setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.4), 
				(int)(Preferences.getScreenHeight() * 0.9)));
		restorePanel.setPreferredSize(backupPanel.getPreferredSize());
		exportPanel.setPreferredSize(backupPanel.getPreferredSize());
		restorePanel.setBackground(Color.white);
		backupPanel.setBackground(Color.white);
		exportPanel.setBackground(Color.white);

	}
	
	public void addListener(ActionListener listener)
	{
		backupPanel.addListener(listener);
		restorePanel.addListener(listener);
		exportPanel.addListener(listener);
		backupViewButton.addActionListener(listener);
		restoreViewButton.addActionListener(listener);
		exportViewButton.addActionListener(listener);
		backupViewButton.setActionCommand(ActionConstants.VIEW_BACKUP);
		restoreViewButton.setActionCommand(ActionConstants.VIEW_RESTORE);
		exportViewButton.setActionCommand(ActionConstants.VIEW_EXPORT);
		backupViewButton.addMouseListener((CustomListener)listener);
		restoreViewButton.addMouseListener((CustomListener)listener);
		exportViewButton.addMouseListener((CustomListener)listener);
		
	}

	public RestorePanel getRestorePanel()
	{
		return restorePanel;
	}
	public ExportPanel getExportPanel()
	{
		return exportPanel;
	}
	
	public void showPanel(String view)
	{
		CardLayout cl = (CardLayout)getLayout();
		if(view != null)
			cl.show(this, view);
		else cl.first(this);
	}
	
	public BackupPanel getBackupPanel()
	{
		return backupPanel;
	}
}
