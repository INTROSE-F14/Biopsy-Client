package org.introse.gui.event;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.introse.ProjectDriver;
import org.introse.gui.MainMenu;
import org.introse.gui.customcomponent.ListItem;
import org.introse.gui.panel.ContentPanel;


public class CustomListener implements ActionListener, MouseListener
{
	private ProjectDriver projectDriver;
	public CustomListener(ProjectDriver driver) 
	{
		this.projectDriver = driver;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String actionCommand = e.getActionCommand();
		switch(actionCommand)
		{
		case ContentPanel.HISTO_PANEL:
		case ContentPanel.GYNE_PANEL:
		case ContentPanel.CYTO_PANEL:
		case ContentPanel.PATH_PANEL:
		case ContentPanel.PATIENT_PANEL:
		case ContentPanel.PHYS_PANEL:
		case ContentPanel.SPEC_PANEL:
			if(projectDriver.getMainMenuDriver().getCurrentView().equals(ContentPanel.PREFERENCES_VIEW))
				projectDriver.getMainMenuDriver().changeMainView(ContentPanel.RECORDS_VIEW);
			projectDriver.getMainMenuDriver().setSelectedButton(e.getSource());
			projectDriver.getMainMenuDriver().changeSubView(actionCommand);
			break;
		case "VIEW_PREFERENCES":
			if(projectDriver.getMainMenuDriver().getCurrentView().equals(ContentPanel.RECORDS_VIEW))
				projectDriver.getMainMenuDriver().changeMainView(ContentPanel.PREFERENCES_VIEW);
			projectDriver.getMainMenuDriver().setSelectedButton(e.getSource());
			break;
		case "LOG_OUT": projectDriver.getMainMenuDriver().logout();
			break;
		case "LOG_IN" : projectDriver.login();
		}
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.getComponent().setBackground(Color.decode(MainMenu.HOVER));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.getComponent().setBackground(Color.decode(MainMenu.NORMAL));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		e.getComponent().setBackground(Color.decode(MainMenu.PRESSED));
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getComponent().contains(e.getPoint()))
		{
			ListItem item = (ListItem)e.getComponent();
			item.setBackground(Color.decode(MainMenu.HOVER));
			projectDriver.getMainMenuDriver().viewItem(item);
			
		}
	}
}
