package org.introse.gui.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import org.introse.Constants.StyleConstants;
import org.introse.gui.window.LoginWindow;

public class ProgressPanel extends JPanel {
	
	private JProgressBar progressBar;
	private JLabel mainMessage, subMessage, recordCount, patientCount;
	
	public ProgressPanel()
	{
		super(new GridBagLayout());
		setBackground(Color.white);
		setBorder(new EmptyBorder(20,50,20,50));
		initUI();
		layoutComponents();
	}
	
	private void initUI()
	{
		progressBar = new JProgressBar();
		mainMessage = new JLabel();
		subMessage = new JLabel();
		recordCount = new JLabel();
		patientCount= new JLabel();
		progressBar.setIndeterminate(false);
		
		mainMessage.setFont(LoginWindow.PRIMARY_FONT.deriveFont(StyleConstants.HEADER - 10));
		subMessage.setFont(mainMessage.getFont().deriveFont(StyleConstants.MENU));
		patientCount.setFont(subMessage.getFont());
		recordCount.setFont(subMessage.getFont());
	}
	
	private void layoutComponents()
	{
		int y = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = y++;
		add(mainMessage, c);
		c.gridwidth = 1;
		c.gridy = y++;
		add(subMessage, c);
		c.gridwidth = 3;
		c.gridy = y++;
		add(progressBar, c);
		c.gridy = y++;
		add(recordCount, c);
		c.gridy = y++;
		add(patientCount, c);
	}
	
	public void setMainMessage(String message)
	{
		mainMessage.setText(message);
	}
	
	public void setSubMessage(String message)
	{
		subMessage.setText(message);
	}
	
	public void setProgress(int progress)
	{
		progressBar.setValue(progress);
	}
	
	public JProgressBar getProgressBar()
	{
		return progressBar;
	}
	
	public void setRecordCount(String message)
	{
		recordCount.setText(message);
	}
	
	public void setPatientCount(String message)
	{
		patientCount.setText(message);
	}
	
	public void reset()
	{
		progressBar.setVisible(true);
		progressBar.setIndeterminate(false);
		patientCount.setText("");
		recordCount.setText("");
		mainMessage.setText("");
		subMessage.setText("");
	}

}
