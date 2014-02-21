package org.introse.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.introse.gui.event.CustomListener;


public class MainMenu 
{

	private JFrame frame;
	private JPanel mainPanel;
	private JButton sendUpdate;
	private JButton getUpdate;
	public MainMenu()
	{
		frame = new JFrame("Main Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sendUpdate = new JButton("Send Update");
		getUpdate = new JButton("Get Update");
		mainPanel = new JPanel();
		mainPanel.add(sendUpdate);
		mainPanel.add(getUpdate);
		frame.setContentPane(mainPanel);
		frame.pack();
	}
	
	public void showGUI() 
	{
		frame.setVisible(true);
	}
	
	public void setListener(CustomListener listener)
	{
		sendUpdate.addActionListener(listener);
		getUpdate.addActionListener(listener);
		sendUpdate.setActionCommand("SEND_UPDATE");
		getUpdate.setActionCommand("GET_UPDATE");
	}
}
