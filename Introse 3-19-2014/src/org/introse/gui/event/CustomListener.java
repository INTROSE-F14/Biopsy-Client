package org.introse.gui.event;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.TitleConstants;
import org.introse.ProjectDriver;
import org.introse.gui.panel.ListItem;


public class CustomListener implements ActionListener, MouseListener, KeyListener
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
		case Constants.TitleConstants.HISTOPATHOLOGY:
		case Constants.TitleConstants.GYNECOLOGY:
		case Constants.TitleConstants.CYTOLOGY:
		case Constants.TitleConstants.PATHOLOGISTS:
		case Constants.TitleConstants.PATIENTS:
		case Constants.TitleConstants.PHYSICIANS:
		case Constants.TitleConstants.SPECIMENS:
		case Constants.TitleConstants.PREFERENCES:
			projectDriver.changeView(actionCommand);
			projectDriver.setSelectedButton(e.getSource());
			projectDriver.applyFilter(actionCommand);
			break;
		case Constants.ActionConstants.REFRESH: projectDriver.refresh(projectDriver.getCurrentView());
												projectDriver.removeDetailsPanel();
			break;
		case Constants.ActionConstants.LOG_OUT: projectDriver.logout();
			break;
		case Constants.ActionConstants.LOG_IN: projectDriver.login();
			break;
		case Constants.ActionConstants.EDIT_RECORD: projectDriver.editCurrentForm();
			break;
		case Constants.ActionConstants.SAVE: projectDriver.saveCurrentForm();
			break;
		case Constants.ActionConstants.CANCEL: projectDriver.cancelCurrentForm();
			break;
		case Constants.ActionConstants.NEW_HISTOPATHOLOGY:	projectDriver.createNew(Constants.RecordConstants.HISTOPATHOLOGY_RECORD);
			break;
		case Constants.ActionConstants.NEW_GYNENECOLOGY:	projectDriver.createNew(Constants.RecordConstants.GYNECOLOGY_RECORD);
			break;
		case Constants.ActionConstants.NEW_CYTOTOLOGY:	projectDriver.createNew(Constants.RecordConstants.CYTOLOGY_RECORD);
			break;
		case Constants.ActionConstants.NEW_PATIENT:	projectDriver.createNew(Constants.RecordConstants.PATIENT);
			break;
		case Constants.ActionConstants.LOAD_PATIENT: projectDriver.openPatientLoad();
									  break;
		case Constants.ActionConstants.SEARCH_PATIENT: projectDriver.openPatientSearch();
			break;
		case ActionConstants.SEARCH_RECORD: projectDriver.openRecordSearch();
			break;
		case ActionConstants.SEARCH: projectDriver.displaySearchResult();
			break;
       case ActionConstants.BACK: projectDriver.changeView(projectDriver.getPreviousView());
        						   projectDriver.removeDetailsPanel();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		e.getComponent().setBackground(Color.decode(Constants.StyleConstants.HOVER));
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		e.getComponent().setBackground(Color.decode(Constants.StyleConstants.NORMAL));
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		e.getComponent().setBackground(Color.decode(Constants.StyleConstants.PRESSED));
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getComponent().contains(e.getPoint()))
		{
			e.getComponent().setBackground(Color.decode(Constants.StyleConstants.HOVER));
			if(e.getComponent() instanceof ListItem)
			{
				ListItem listItem = (ListItem)e.getComponent();
				projectDriver.viewItem(listItem);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		projectDriver.applyFilter(projectDriver.getCurrentView());
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
			
	}
}
