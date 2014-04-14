package org.introse.core.workers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.introse.Constants;
import org.introse.ProjectDriver;
import org.introse.core.Preferences;
import org.introse.core.network.Client;
import org.introse.gui.dialogbox.PopupDialog;
import org.introse.gui.window.LoginWindow;

public class LoginWorker extends SwingWorker<Integer, Void> implements PropertyChangeListener {

	private Client client;
	private String password;
	private LoginWindow loginForm;
	private ProjectDriver driver;
	
	public LoginWorker(LoginWindow loginForm, ProjectDriver driver, Client client)
	{
		this.client = client;
		this.loginForm = loginForm;
		this.driver = driver;
		this.password = loginForm.getPassword();
		addPropertyChangeListener(this);
	}
	@Override
	protected Integer doInBackground() throws Exception 
	{
		int loginStatus = client.connectToServer(password);
		return loginStatus;
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) 
	{
		if(evt.getPropertyName().equals("DONE"))
		{
			int loginStatus;
			try 
			{
				loginStatus = (int)get();
				loginForm.setLoadingVisible(false);
				loginForm.setLoginButtonEnabled(true);
				loginForm.setListening(2);
				switch(loginStatus)
				{
					case Constants.NetworkConstants.AUTHENTICATION_SUCCESSFUL: 
						String[] serverInfo = client.getServerInfo().split(":");
						String dbUsername = serverInfo[0];
						String dbPassword = serverInfo[1];
						String dbName = serverInfo[2];
						int port = Integer.parseInt(serverInfo[3]);
						String ip = serverInfo[4];
						Preferences.setDatabaseName(dbName);
						Preferences.setDatabaseAddress(ip, port);
						Preferences.setDatabaseUsername(dbUsername);
						Preferences.setDatabasePassword(dbPassword);
						loginForm.exit();
						driver.startMainMenu();
						break;
					case Constants.NetworkConstants.AUTHENTICATION_FAILED:
						PopupDialog dialog = new PopupDialog(loginForm, "Login Failed", 
								"You entered an invalid password.", "OK");
						dialog.addPropertyChangeListener(new PropertyChangeListener(){

							@Override
							public void propertyChange(PropertyChangeEvent evt) 
							{loginForm.setListening(1);}
						});
						dialog.showGui();
						break;
					case Constants.NetworkConstants.SERVER_ERROR:
						PopupDialog dialog2 = new PopupDialog(loginForm, "Server Error", 
								"An error occured while trying to connect to the server.", "OK");
						dialog2.addPropertyChangeListener(new PropertyChangeListener(){

							@Override
							public void propertyChange(PropertyChangeEvent evt) 
							{loginForm.setListening(1);}
						});
						dialog2.showGui();
						break;
				}
			} catch (InterruptedException | ExecutionException e) 
			{
				e.printStackTrace();
			}
		}
		
	}

}
