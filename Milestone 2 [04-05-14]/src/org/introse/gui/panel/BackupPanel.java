package org.introse.gui.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.introse.Constants.ActionConstants;
import org.introse.Constants.StatusConstants;
import org.introse.Constants.StyleConstants;
import org.introse.core.Preferences;
import org.introse.gui.window.LoginWindow;

public class BackupPanel extends JPanel {

	private JLabel lblBackup, lblDirectory, lblStatus,
	backupDescription1, backupDescription2, backupDirectory,
	backupStatus;
	private JButton btnBackup, btnBack;
	
	private JPanel actionPanel, progressPanel;
	private JProgressBar progressBar;
	private JLabel mainMessage, subMessage, lblRecords, recordCount, lblPatient, patientCount;
	private ImageIcon successIcon, failIcon;
	
	public BackupPanel()
	{
		super(new GridBagLayout());
		initUI();
		layoutUI();
	}
	
	private void initUI()
	{
		btnBack = new JButton();
		btnBack.setIcon(new ImageIcon(getClass().getResource("/res/icons/back.png")));
		btnBack.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/back_rollover.png")));
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		
		btnBackup = new JButton();
		lblBackup = new JLabel(ActionConstants.BACKUP);
		backupDescription1 = new JLabel("Set up a backup for existing records and patients in the database");
		
		lblBackup.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.HEADER));
		backupDescription1.setFont(lblBackup.getFont().deriveFont(StyleConstants.SUBHEADER));
		
		actionPanel = new JPanel(new CardLayout());
		actionPanel.setBackground(Color.white);
		
		progressPanel = new JPanel(new GridBagLayout());
		progressPanel.setBackground(Color.white);
		lblDirectory = new JLabel("Backup file");
		lblStatus = new JLabel("Status");
		backupDirectory = new JLabel();
		backupStatus = new JLabel();
		successIcon = new ImageIcon(getClass().getResource("/res/icons/success.png"));
		failIcon = new ImageIcon(getClass().getResource("/res/icons/fail.png"));
		lblDirectory.setFont(LoginWindow.PRIMARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		lblStatus.setFont(lblDirectory.getFont());
		backupDirectory.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.MENU));
		backupStatus.setFont(backupDirectory.getFont());
		
		backupDirectory.setOpaque(true);
		backupDirectory.setBackground(Color.white);
		backupStatus.setOpaque(true);
		backupStatus.setBackground(Color.white);
		lblDirectory.setOpaque(true);
		lblDirectory.setBackground(Color.white);
		lblStatus.setOpaque(true);
		lblStatus.setBackground(Color.white);
		
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.3),
				20));
		mainMessage = new JLabel();
		subMessage = new JLabel();
		recordCount = new JLabel();
		patientCount= new JLabel();
		lblPatient = new JLabel("Patient(s)");
		lblRecords = new JLabel("Record(s)");
		progressBar.setIndeterminate(true);
		mainMessage.setHorizontalTextPosition(JLabel.LEADING);
		mainMessage.setIconTextGap(20);
		
		mainMessage.setFont(LoginWindow.PRIMARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		subMessage.setFont(mainMessage.getFont().deriveFont(StyleConstants.MENU));
		patientCount.setFont(mainMessage.getFont());
		recordCount.setFont(mainMessage.getFont());
		lblPatient.setFont(mainMessage.getFont());
		lblRecords.setFont(mainMessage.getFont());
		
		JPanel emptyPanel = new JPanel();
		emptyPanel.setBackground(Color.white);
		actionPanel.add("EMPTY", emptyPanel);
		actionPanel.add("PROGRESS", progressPanel);
		setStatus(StatusConstants.DEFAULT);
	}
	
	private void layoutUI()
	{
		int y = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		add(btnBack, c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,20,0);
		add(lblBackup, c);
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 2;
		c.insets = new Insets(0,50,20,0);
		add(backupDescription1, c);
		c.gridwidth = 2;
		c.gridy = y++;
		add(btnBackup, c);
		c.gridy = y++;
		c.gridx = 0;
		c.weighty = 1.0;
		add(actionPanel, c);
		
		y = 0;
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridy = y++;
		c.gridx = 0;
		c.insets = new Insets(0,0,5,10);
		progressPanel.add(lblDirectory, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.insets = new Insets(0,0,5,0);
		progressPanel.add(backupDirectory, c);
		c.weightx = 0.0;
		c.gridy = y++;
		c.gridx = 0;
		c.insets = new Insets(0,0,20,10);
		progressPanel.add(lblStatus, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.insets = new Insets(0,0,20,0);
		progressPanel.add(backupStatus, c);
		c.weightx = 0.0;
		c.gridy = y++;
		c.gridx = 0;
		c.gridwidth = 3;
		c.insets = new Insets(0,0,5,20);
		progressPanel.add(mainMessage, c);
		c.gridy = y++;
		progressPanel.add(subMessage, c);
		c.gridy = y++;
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 4;
		progressPanel.add(progressBar, c);
		c.gridy = y;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,5,10);
		c.fill = GridBagConstraints.HORIZONTAL;
		progressPanel.add(lblRecords, c);
		c.gridy = y++;
		c.weightx = 1.0;
		c.gridx = 1;
		c.insets = new Insets(0,0,5,0);
		progressPanel.add(recordCount, c);
		c.gridx = 0;
		c.weightx = 0.0;
		c.gridy = y;
		c.insets = new Insets(0,0,5,10);
		c.fill = GridBagConstraints.HORIZONTAL;
		progressPanel.add(lblPatient, c);
		c.gridy = y++;
		c.weightx = 1.0;
		c.gridx = 1;
		c.insets = new Insets(0,0,5,0);
		progressPanel.add(patientCount, c);
	}
	
	public void addListener(ActionListener listener)
	{
		btnBackup.addActionListener(listener);
		btnBackup.setActionCommand(ActionConstants.SELECT_BACKUP);
		btnBack.addActionListener(listener);
		btnBack.setActionCommand(ActionConstants.VIEW_TOOLSOVERVIEW);
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				setStatus(StatusConstants.DEFAULT);
			}
		});
	}
	
	public void setBackupPath(String path)
	{
		backupDirectory.setText(path);
	}
	
	public String getBackupPath()
	{
		return backupDirectory.getText();
	}
	
	public JProgressBar getProgressBar()
	{
		return progressBar;
	}
	
	public void setMainMessage(String message)
	{
		mainMessage.setText(message);
	}
	
	public void setSubMessage(String message)
	{
		subMessage.setText(message);
	}
	
	public void setRecordCount(int count)
	{
		recordCount.setText(""+count);
	}
	
	public void setPatientCount(int count)
	{
		patientCount.setText(""+count);
	}
	
	public void setStatus(int status)
	{
		CardLayout cl = (CardLayout)actionPanel.getLayout();
		switch(status)
		{
		case StatusConstants.SUCCESS: backupStatus.setText("Complete");
									  mainMessage.setIcon(successIcon);
									  mainMessage.setText("Backup successful!");
									  subMessage.setText("");
									  progressBar.setVisible(false);
									  btnBackup.setEnabled(true);
									  btnBackup.setText("create backup");
									  cl.last(actionPanel);
									  btnBack.setEnabled(true);
			break;
		case StatusConstants.FAILED: backupStatus.setText("Incomplete");
									 mainMessage.setIcon(failIcon);
									 mainMessage.setText("Backup failed!");
									 subMessage.setText("");
									 progressBar.setVisible(false);
									 btnBackup.setEnabled(true);
									 btnBackup.setText("create backup");
									 cl.last(actionPanel);
									 btnBack.setEnabled(true);
			break;
		case StatusConstants.PREPARING:	backupStatus.setText("Preparing");
										mainMessage.setText("Preparing to backup data");
										mainMessage.setIcon(null);
										subMessage.setText("Please wait");
										progressBar.setVisible(true);
										progressBar.setIndeterminate(true);
										btnBackup.setEnabled(false);
										btnBackup.setText("please wait");
										cl.last(actionPanel);
										btnBack.setEnabled(false);
			break;
		case StatusConstants.DEFAULT: backupStatus.setText("");
									  backupDirectory.setText("");
									  mainMessage.setText("");
									  mainMessage.setIcon(null);
									  subMessage.setText("");
									  progressBar.setVisible(false);
									  cl.first(actionPanel);
									  btnBackup.setEnabled(true);
									  btnBackup.setText("create backup");
									  btnBack.setEnabled(true);
			break;
		case StatusConstants.ONGOING: backupStatus.setText("In progress");
									  mainMessage.setText("Backing up data");
									  mainMessage.setIcon(null);
									  subMessage.setText("This might take a while");
									  progressBar.setVisible(true);
									  progressBar.setIndeterminate(false);
									  cl.last(actionPanel);
									  btnBackup.setEnabled(false);
									  btnBackup.setText("please wait");
									  btnBack.setEnabled(false);
		}
	}
}
