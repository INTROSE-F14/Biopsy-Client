package org.introse.gui.event;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.TitleConstants;
import org.introse.ProjectDriver;
import org.introse.gui.dialogbox.PopupDialog;


public class CustomListener implements ActionListener, MouseListener
{
	private ProjectDriver projectDriver;
	public CustomListener(ProjectDriver driver) 
	{
		this.projectDriver = driver;
	}

	@Override
	public void actionPerformed(final ActionEvent e) 
	{
		final String actionCommand = e.getActionCommand();
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
			if(projectDriver.getDetailPanelStatus() == ActionConstants.EDIT || 
			projectDriver.getDetailPanelStatus() == ActionConstants.NEW)
			{
				PopupDialog popup = new PopupDialog(projectDriver.getMainMenu(), "Cancel form", 
						TitleConstants.DISCARD_CHANGES_MESSAGE, "Yes", "No");
				popup.addPropertyChangeListener(new PropertyChangeListener()
				{

					@Override
					public void propertyChange(PropertyChangeEvent evt) 
					{
						if(evt.getPropertyName().equals("POSITIVE"))
						{
							projectDriver.cancelCurrentForm();
							projectDriver.changeView(actionCommand);
							projectDriver.setSelectedButton(e.getSource());
						}
					}
				});
				popup.showGui();
			}
			else
			{
				projectDriver.removeDetailsPanel();
				projectDriver.changeView(actionCommand);
				projectDriver.setSelectedButton(e.getSource());
			}
			break;
		case Constants.ActionConstants.REFRESH: projectDriver.refresh(projectDriver.getCurrentView(), false);
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
		case Constants.ActionConstants.CANCEL: 
			
			PopupDialog popup = new PopupDialog(projectDriver.getMainMenu(), "Cancel form?", 
					TitleConstants.DISCARD_CHANGES_MESSAGE, "Yes", "No");
			popup.addPropertyChangeListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(PropertyChangeEvent evt) 
				{
					if(evt.getPropertyName().equals("POSITIVE"))
						projectDriver.cancelCurrentForm();
				}
			});
			popup.showGui();
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
       case ActionConstants.BACK: 
    	   if(projectDriver.getDetailPanelStatus() == ActionConstants.EDIT || 
   			projectDriver.getDetailPanelStatus() == ActionConstants.NEW)
   			{
   				PopupDialog popup1 = new PopupDialog(projectDriver.getMainMenu(), "Cancel form", 
   						TitleConstants.DISCARD_CHANGES_MESSAGE, "Yes", "No");
   				popup1.addPropertyChangeListener(new PropertyChangeListener()
   				{

   					@Override
   					public void propertyChange(PropertyChangeEvent evt) 
   					{
   						if(evt.getPropertyName().equals("POSITIVE"))
   						{
   							projectDriver.cancelCurrentForm();
   							projectDriver.changeView(projectDriver.getPreviousView());
   							projectDriver.setSelectedButton(projectDriver.getPreviousView());
   						}
   					}
   				});
   				popup1.showGui();
   			}
   			else
   			{
   				projectDriver.removeDetailsPanel();
   				projectDriver.changeView(projectDriver.getPreviousView());
   				projectDriver.setSelectedButton(projectDriver.getPreviousView());
   			}
        						   break;
       case ActionConstants.SELECT_RESTORE: projectDriver.selectRestorePath();
    	   break;
       case ActionConstants.SELECT_BACKUP: projectDriver.selectBackupPath();
       break;
       case ActionConstants.SELECT_EXPORT: projectDriver.selectExportPath();
       break;
       case ActionConstants.BACKUP: projectDriver.backup();
       break;
       case ActionConstants.RESTORE: projectDriver.restore();
       break;
       case ActionConstants.EXPORT: projectDriver.export();
       break;
       case ActionConstants.VIEW_BACKUP: projectDriver.changeToolsView(actionCommand);
    	   break;
       case ActionConstants.VIEW_EXPORT: projectDriver.changeToolsView(actionCommand);
    	   break;
       case ActionConstants.VIEW_RESTORE: projectDriver.changeToolsView(actionCommand);
			break;
       case ActionConstants.VIEW_TOOLSOVERVIEW: projectDriver.changeToolsView(null);
       break;
       case ActionConstants.NEXT:projectDriver.loadNext();
    	   break;
       case ActionConstants.PREVIOUS: projectDriver.loadPrevious();
       break;
       case ActionConstants.ADD_WORD: projectDriver.addCurrentWord();
       break;
       case ActionConstants.PRINT: projectDriver.printCurrentForm();
       break;
       case ActionConstants.DELETE: projectDriver.delete(((JButton)e.getSource()).getParent());
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
	public void mouseReleased(MouseEvent e) {}
}
