

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CustomListener implements ActionListener 
{
	private Driver driver;
	
	public CustomListener(Driver driver) 
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
