package org.introse;



import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.introse.core.Database;
import org.introse.core.Preferences;
import org.introse.core.network.Client;
import org.introse.gui.ErrorDialog;
import org.introse.gui.LoginWindow;
import org.introse.gui.MainMenu;
import org.introse.gui.event.CustomListener;


public class ProjectDriver 
{
	private static LoginWindow loginForm;
	private static final CustomListener listener =  new CustomListener(new ProjectDriver());
	private MainMenu mainWindow;
	
	public static void main(String[] args) 
	{
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) 
		{e.printStackTrace();}
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGui();
			}
		});
	}
	
	public static void createAndShowGui()
	{
		loginForm = new LoginWindow();
		loginForm.setActionListener(listener);
		loginForm.showGUI();
	}
	
	
	public void login()
	{
		Client client = new Client();
		String password = new String(loginForm.getPassword());
		
		int loginStatus = client.connectToServer(password);
		switch(loginStatus)
		{
			case Client.AUTHENTICATION_SUCCESSFUL: 
				String[] serverInfo = client.getServerInfo().split(":");
				String dbUsername = serverInfo[0];
				String dbPassword = serverInfo[1];
				String dbName = serverInfo[2];
				int port = Integer.parseInt(serverInfo[3]);
				String ip = serverInfo[4].replace("/", "");
				Database db = new Database(ip, dbUsername, dbPassword, dbName,port);
				Preferences.setDatabase(db);
				loginForm.hideGUI();
				showMainMenu();
				break;
			case Client.AUTHENTICATION_FAILED:
				new ErrorDialog("Login Failed", "You entered an invalid password").showGui();
				break;
			case Client.SERVER_ERROR:
				new ErrorDialog("Server Error", "An error occured while trying to connect to the server").showGui();
				break;
		}
	}
	
	public void showMainMenu()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mainWindow = new MainMenu();
				mainWindow.showGUI();
			}});
	
	}
}
