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
import org.introse.gui.window.MainMenu;

public class ExportPanel extends JPanel {

	private JLabel lblExport, lblDiagnosisDirectory, lblRecordDirectory, lblPatientDirectory, 
	lblStatus, exportDescription1, exportDiagnosisDirectory, exportRecordDirectory, 
	exportPatientDirectory, exportStatus;
	private JButton btnExport, btnBack;
	
	private JPanel actionPanel, progressPanel;
	private JProgressBar progressBar;
	private JLabel mainMessage, subMessage, lblRecords, recordCount, lblPatient, patientCount;
	private ImageIcon successIcon, failIcon;
	
	public ExportPanel()
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
		
		btnExport = new JButton();
		lblExport = new JLabel(ActionConstants.EXPORT);
		exportDescription1 = new JLabel("Export existing records and patients to a CSV file");
		
		lblExport.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.HEADER));
		exportDescription1.setFont(lblExport.getFont().deriveFont(StyleConstants.SUBHEADER));
		
		actionPanel = new JPanel(new CardLayout());
		actionPanel.setBackground(Color.white);
		
		progressPanel = new JPanel(new GridBagLayout());
		progressPanel.setBackground(Color.white);
		lblDiagnosisDirectory = new JLabel("Diagnosis Export file");
		lblRecordDirectory = new JLabel("Records export file");
		lblPatientDirectory = new JLabel("Patient export file");
		lblStatus = new JLabel("Status");
		exportDiagnosisDirectory = new JLabel();
		exportPatientDirectory = new JLabel();
		exportRecordDirectory = new JLabel();
		exportStatus = new JLabel();
		successIcon = new ImageIcon(getClass().getResource("/res/icons/success.png"));
		failIcon = new ImageIcon(getClass().getResource("/res/icons/fail.png"));
		lblDiagnosisDirectory.setFont(LoginWindow.PRIMARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		lblPatientDirectory.setFont(LoginWindow.PRIMARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		lblRecordDirectory.setFont(LoginWindow.PRIMARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		lblStatus.setFont(lblDiagnosisDirectory.getFont());
		exportDiagnosisDirectory.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.MENU));
		exportPatientDirectory.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.MENU));
		exportRecordDirectory.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.MENU));
		exportStatus.setFont(exportDiagnosisDirectory.getFont());
		
		exportDiagnosisDirectory.setOpaque(true);
		exportPatientDirectory.setOpaque(true);
		exportRecordDirectory.setOpaque(true);
		exportDiagnosisDirectory.setBackground(Color.white);
		exportRecordDirectory.setBackground(Color.white);
		exportPatientDirectory.setBackground(Color.white);
		exportStatus.setOpaque(true);
		exportStatus.setBackground(Color.white);
		lblDiagnosisDirectory.setOpaque(true);
		lblRecordDirectory.setOpaque(true);
		lblPatientDirectory.setOpaque(true);
		lblDiagnosisDirectory.setBackground(Color.white);
		lblRecordDirectory.setBackground(Color.white);
		lblPatientDirectory.setBackground(Color.white);
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
		add(lblExport, c);
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 2;
		c.insets = new Insets(0,50,20,0);
		add(exportDescription1, c);
		c.gridwidth = 2;
		c.gridy = y++;
		add(btnExport, c);
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
		progressPanel.add(lblPatientDirectory, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.insets = new Insets(0,0,5,0);
		progressPanel.add(exportPatientDirectory, c);
		c.gridwidth = 1;
		c.gridy = y++;
		c.gridx = 0;
		c.insets = new Insets(0,0,5,10);
		progressPanel.add(lblRecordDirectory, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.insets = new Insets(0,0,5,0);
		progressPanel.add(exportRecordDirectory, c);
		c.gridwidth = 1;
		c.gridy = y++;
		c.gridx = 0;
		c.insets = new Insets(0,0,5,10);
		progressPanel.add(lblDiagnosisDirectory, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.insets = new Insets(0,0,5,0);
		progressPanel.add(exportDiagnosisDirectory, c);
		c.weightx = 0.0;
		c.gridy = y++;
		c.gridx = 0;
		c.insets = new Insets(0,0,20,10);
		progressPanel.add(lblStatus, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.insets = new Insets(0,0,20,0);
		progressPanel.add(exportStatus, c);
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
		btnExport.addActionListener(listener);
		btnExport.setActionCommand(ActionConstants.SELECT_EXPORT);
		btnBack.addActionListener(listener);
		btnBack.setActionCommand(ActionConstants.VIEW_TOOLSOVERVIEW);
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				setStatus(StatusConstants.DEFAULT);
			}
		});
	}
	
	public void setExportDiagnosisPath(String path)
	{
		exportDiagnosisDirectory.setText(path);
	}
	
	public void setExportRecordPath(String path)
	{
		exportRecordDirectory.setText(path);
	}
	
	public void setExportPatientPath(String path)
	{
		exportPatientDirectory.setText(path);
	}
	
	public String getExportDiagnosisPath()
	{
		return exportDiagnosisDirectory.getText();
	}
	
	public String getExportRecordPath()
	{
		return exportRecordDirectory.getText();
	}
	
	public String getExportPatientPath()
	{
		return exportPatientDirectory.getText();
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
		case StatusConstants.SUCCESS: exportStatus.setText("Complete");
									  mainMessage.setIcon(successIcon);
									  mainMessage.setText("Export successful!");
									  subMessage.setText("");
									  progressBar.setVisible(false);
									  btnExport.setEnabled(true);
									  btnExport.setText("export data");
									  cl.last(actionPanel);
									  btnBack.setEnabled(true);
			break;
		case StatusConstants.FAILED: exportStatus.setText("Incomplete");
									 mainMessage.setIcon(failIcon);
									 mainMessage.setText("Export failed!");
									 subMessage.setText("");
									 progressBar.setVisible(false);
									 btnExport.setEnabled(true);
									 btnExport.setText("export data");
									 cl.last(actionPanel);
									 btnBack.setEnabled(true);
			break;
		case StatusConstants.PREPARING:	exportStatus.setText("Preparing");
										mainMessage.setText("Preparing to export data");
										mainMessage.setIcon(null);
										subMessage.setText("Please wait");
										progressBar.setVisible(true);
										progressBar.setIndeterminate(true);
										btnExport.setEnabled(false);
										btnExport.setText("please wait");
										cl.last(actionPanel);
										btnBack.setEnabled(false);
			break;
		case StatusConstants.DEFAULT: exportStatus.setText("");
									  exportDiagnosisDirectory.setText("");
									  exportRecordDirectory.setText("");
									  exportPatientDirectory.setText("");
									  mainMessage.setText("");
									  mainMessage.setIcon(null);
									  subMessage.setText("");
									  progressBar.setVisible(false);
									  cl.first(actionPanel);
									  btnExport.setEnabled(true);
									  btnExport.setText("export data");
									  btnBack.setEnabled(true);
			break;
		case StatusConstants.ONGOING: exportStatus.setText("In progress");
									  mainMessage.setText("Exporting data");
									  mainMessage.setIcon(null);
									  subMessage.setText("This might take a while");
									  progressBar.setVisible(true);
									  progressBar.setIndeterminate(false);
									  cl.last(actionPanel);
									  btnExport.setEnabled(false);
									  btnExport.setText("please wait");
									  btnBack.setEnabled(false);
		}
	}
}
