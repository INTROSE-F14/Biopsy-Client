package org.introse.gui.panel;



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.introse.Constants.ActionConstants;
import org.introse.Constants.StyleConstants;
import org.introse.gui.window.MainMenu;

public class BackupAndRestorePanel extends JPanel {
	
	private JLabel lblBackup, lblRestore;
	private JTextField backupDirectory, restoreDirectory;
	private JButton btnBackup, btnRestore, btnBackupDirectory, btnRestoreDirectory;
	
	public BackupAndRestorePanel() 
	{
		super(new GridBagLayout());
		initializeComponents();
		layoutComponents();
	}
	
	private void layoutComponents()
	{
		int y = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = y++;
		add(lblBackup, c);
		c.gridwidth = 2;
		c.gridy = y;
		c.insets = new Insets(0,0,10,5);
		add(backupDirectory, c);
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		add(btnBackupDirectory, c);
		c.anchor = GridBagConstraints.LINE_END;
		c.gridy = y++;
		c.gridx = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,20,0);
		add(btnBackup, c);
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		add(lblRestore, c);
		c.gridwidth = 2;
		c.gridy = y;
		c.insets = new Insets(0,0,10,5);
		add(restoreDirectory, c);
		c.gridwidth = 1;
		c.gridx= 2;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		add(btnRestoreDirectory, c);
		c.anchor = GridBagConstraints.LINE_END;
		c.gridy = y;
		c.gridx = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,20,0);
		add(btnRestore, c);
	}
	
	private void initializeComponents()
	{
		lblBackup = new JLabel("Backup");
		lblRestore = new JLabel("Restore");
		
		lblBackup.setFont(MainMenu.PRIMARY_FONT.deriveFont(StyleConstants.HEADER));
		lblRestore.setFont(lblBackup.getFont());
		
		backupDirectory = new JTextField(30);
		restoreDirectory = new JTextField(30);
		backupDirectory.setFont(getFont().deriveFont(StyleConstants.MENU));
		restoreDirectory.setFont(backupDirectory.getFont());
		backupDirectory.setEditable(false);
		restoreDirectory.setEditable(false);
		
		btnBackup = new JButton("create backup");
		btnRestore = new JButton("restore backup");
		btnBackupDirectory = new JButton("...");
		btnRestoreDirectory = new JButton("...");
		btnBackup.setFont(getFont().deriveFont(StyleConstants.MENU));
		btnRestore.setFont(btnBackup.getFont());
		btnBackupDirectory.setFont(btnBackup.getFont());
		btnRestoreDirectory.setFont(btnBackupDirectory.getFont());
		btnBackup.setEnabled(false);
		btnRestore.setEnabled(false);
	}
	
	public void addListener(ActionListener listener)
	{
		btnBackup.addActionListener(listener);
		btnRestore.addActionListener(listener);
		btnBackup.setActionCommand(ActionConstants.BACKUP);
		btnRestore.setActionCommand(ActionConstants.RESTORE);
		
		btnBackupDirectory.addActionListener(listener);
		btnRestoreDirectory.addActionListener(listener);
		btnBackupDirectory.setActionCommand(ActionConstants.SELECT_BACKUP);
		btnRestoreDirectory.setActionCommand(ActionConstants.SELECT_RESTORE);
	}
	
	public void setBackupPath(String path)
	{
		backupDirectory.setText(path);
	}
	
	public void setRestorePath(String path)
	{
		restoreDirectory.setText(path);
	}
	
	public String getBackupPath()
	{
		return backupDirectory.getText();
	}
	
	public String getRestorePath()
	{
		return restoreDirectory.getText();
	}

	public void setBackupEnabled(boolean isEnabled)
	{
		btnBackup.setEnabled(isEnabled);
	}
	
	public void setRestoreEnabled(boolean isEnabled)
	{
		btnRestore.setEnabled(isEnabled);
	}
}
