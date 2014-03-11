package org.introse.gui.window;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import org.introse.Constants;


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
		panel.setBackground(Color.white);
		panel.setBorder(new EmptyBorder(20,20,20,20));
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
		
		gc.gridx = 0;
		gc.gridy = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(actionButton, gc);
		
		mainFrame.setContentPane(panel);
		mainFrame.pack();
		
	}
	
	
	public void showGUI()
	{
		mainFrame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
	}
	
	public void hideGUI()
	{
		mainFrame.setVisible(false);
	}
	
	public void exit()
	{
		mainFrame.dispose();
	}
	
	public void addListener(ActionListener listener)
	{
		actionButton.addActionListener(listener);
		actionButton.setActionCommand(Constants.ActionConstants.LOG_IN);
	}
	
	public String getPassword()
	{
		return new String(passwordField.getPassword());
	}
}
