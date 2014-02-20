package org.introse.gui.event;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.introse.ProjectDriver;


public class CustomListener implements ActionListener 
{
	private ProjectDriver driver;
	
	public CustomListener(ProjectDriver driver) 
	{
		this.driver = driver;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("LOGIN"))
			driver.login();
	}

}
