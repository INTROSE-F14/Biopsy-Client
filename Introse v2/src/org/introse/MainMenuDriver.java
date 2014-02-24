package org.introse;

import javax.swing.SwingUtilities;

import org.introse.core.dao.PatientDao;
import org.introse.core.dao.RecordDao;
import org.introse.core.network.Client;
import org.introse.gui.MainMenu;
import org.introse.gui.customcomponent.ListItem;
import org.introse.gui.event.CustomListener;

public class MainMenuDriver 
{
	private MainMenu mainMenu;
	private RecordDao recordDao;
	private PatientDao patientDao;
	private ProjectDriver driver;
	private CustomListener listener;
	private Client client;
	
	public MainMenuDriver(ProjectDriver driver)
	{
		this.driver = driver;
		this.client = driver.getClient();
		this.listener = driver.getListener();
		recordDao = new RecordDao(client);
		patientDao = new PatientDao(client);
	}
	
	public void main()
	{
		showMainMenu();
	}
	
	private void showMainMenu()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mainMenu = new MainMenu(recordDao, patientDao);
				mainMenu.addListener(listener);
				mainMenu.getContentPanel().updateCytologyList();
				mainMenu.getContentPanel().updateGynecologyList();
				mainMenu.getContentPanel().updateHistopathologyList();
				mainMenu.showGUI();
			}});
	}
	
	public String getCurrentView()
	{
		return mainMenu.getContentPanel().getCurrentView();
	}
	
	public void changeSubView(String view)
	{
		mainMenu.getContentPanel().changeSubView(view);
	}
	
	public void changeMainView(String view)
	{
		mainMenu.getContentPanel().changeMainView(view);
	}
	
	public void setSelectedButton(Object button)
	{
		mainMenu.getNavigationPanel().setSelectedButton(button);
	}
	
	public void logout()
	{
		mainMenu.exit();
		ProjectDriver.createAndShowGui();
	}
	
	public void viewItem(ListItem item)
	{
		mainMenu.viewItem(item);
	}
}
