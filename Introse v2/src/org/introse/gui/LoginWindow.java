package org.introse.gui;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;


public class LoginWindow
{

	private JFrame mainFrame;
	private JButton actionButton;
	private JPasswordField passwordField;
	
	public LoginWindow() 
	{
		initUI();
	}

	private void initUI()
	{
		mainFrame = new JFrame("Client Module");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(new EmptyBorder(10,10,10,10));
		actionButton = new JButton("Login");
		passwordField = new JPasswordField(20);
		
		GridBagConstraints gc = new GridBagConstraints();
	
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		panel.add(new JLabel("Password"), gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 3;
		panel.add(passwordField, gc);
		
		gc.gridx = 1;
		gc.gridy = 2;
		gc.gridwidth = 1;
		panel.add(actionButton, gc);
		
		mainFrame.setContentPane(panel);
		mainFrame.pack();
		
	}
	
	
	public void showGUI()
	{
		mainFrame.setVisible(true);
	}
	
	public void hideGUI()
	{
		mainFrame.setVisible(false);
	}
	
	public void exit()
	{
		mainFrame.dispose();
	}
	
	public void setActionListener(ActionListener listener)
	{
		actionButton.addActionListener(listener);
		actionButton.setActionCommand("LOG_IN");
	}
	
	public String getPassword()
	{
		return new String(passwordField.getPassword());
	}
}
