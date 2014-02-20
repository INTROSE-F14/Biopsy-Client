

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Driver 
{
	private static LoginWindow loginForm;
	private static final CustomListener listener =  new CustomListener(new Driver());
	private MainMenu mainWindow;
	
	public static void main(String[] args) 
	{
		//set java look and feel to native windows theme
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
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
			Preferences.setPreferences(db.getURL(), 
					db.getDatabaseName(), db.getUsername(), db.getPassword());
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
