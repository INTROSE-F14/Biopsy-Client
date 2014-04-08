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
	
	private JLabel lblBackup, lblRestore, lblExport;
	private JTextField backupDirectory, restoreDirectory, exportDirectory;
	private JButton btnBackup, btnRestore, btnExport, btnBackupDirectory, btnRestoreDirectory, btnExportDirectory;
	
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
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 3;
		c.gridx = 0;
		y += 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		add(lblExport, c);
		c.gridwidth = 2;
		c.gridy = y;
		c.insets = new Insets(0,0,10,5);
		add(exportDirectory, c);
		c.gridwidth = 1;
		c.gridx= 2;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		add(btnExportDirectory, c);
		c.anchor = GridBagConstraints.LINE_END;
		c.gridy = y;
		c.gridx = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,20,0);
		add(btnExport, c);
	}
	
	private void initializeComponents()
	{
		lblBackup = new JLabel("Backup");
		lblRestore = new JLabel("Restore");
		lblExport = new JLabel("Export");
		
		lblBackup.setFont(MainMenu.PRIMARY_FONT.deriveFont(StyleConstants.HEADER));
		lblRestore.setFont(lblBackup.getFont());
		lblExport.setFont(lblRestore.getFont());
		
		backupDirectory = new JTextField(30);
		restoreDirectory = new JTextField(30);
		exportDirectory = new JTextField(30);
		backupDirectory.setFont(getFont().deriveFont(StyleConstants.MENU));
		restoreDirectory.setFont(backupDirectory.getFont());
		exportDirectory.setFont(restoreDirectory.getFont());
		backupDirectory.setEditable(false);
		restoreDirectory.setEditable(false);
		exportDirectory.setEditable(false);
		
		btnBackup = new JButton("create backup");
		btnRestore = new JButton("restore backup");
		btnExport = new JButton("export backup");
		btnBackupDirectory = new JButton("...");
		btnRestoreDirectory = new JButton("...");
		btnExportDirectory = new JButton("...");
		btnBackup.setFont(getFont().deriveFont(StyleConstants.MENU));
		btnRestore.setFont(btnBackup.getFont());
		btnExport.setFont(btnRestore.getFont());
		btnBackupDirectory.setFont(btnBackup.getFont());
		btnRestoreDirectory.setFont(btnBackupDirectory.getFont());
		btnExportDirectory.setFont(btnRestoreDirectory.getFont());
		btnBackup.setEnabled(false);
		btnRestore.setEnabled(false);
		btnExport.setEnabled(false);
	}
	
	public void addListener(ActionListener listener)
	{
		btnBackup.addActionListener(listener);
		btnRestore.addActionListener(listener);
		btnExport.addActionListener(listener);
		btnBackup.setActionCommand(ActionConstants.BACKUP);
		btnRestore.setActionCommand(ActionConstants.RESTORE);
		// btnExport.setActionCommand(ActionConstants.EXPORT);
		
		btnBackupDirectory.addActionListener(listener);
		btnRestoreDirectory.addActionListener(listener);
		btnExportDirectory.addActionListener(listener);
		btnBackupDirectory.setActionCommand(ActionConstants.SELECT_BACKUP);
		btnRestoreDirectory.setActionCommand(ActionConstants.SELECT_RESTORE);
		// btnExportDirectory.setActionCommand(ActionConstants.SELECT_EXPORT);
	}
	
	public void setBackupPath(String path)
	{
		backupDirectory.setText(path);
	}
	
	public void setRestorePath(String path)
	{
		restoreDirectory.setText(path);
	}
	
	public void setExportPath(String path)
	{
		exportDirectory.setText(path);
	}
	
	public String getBackupPath()
	{
		return backupDirectory.getText();
	}
	
	public String getRestorePath()
	{
		return restoreDirectory.getText();
	}
	
	public String getExportPath()
	{
		return exportDirectory.getText();
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
