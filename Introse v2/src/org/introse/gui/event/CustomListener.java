package org.introse.gui.event;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.introse.ProjectDriver;
import org.introse.gui.MainMenu;
import org.introse.gui.customcomponent.ListItem;
import org.introse.gui.form.Form;
import org.introse.gui.panel.ContentPanel;
import org.introse.gui.panel.DetailPanel;


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
		case ContentPanel.HISTO_PANEL:
		case ContentPanel.GYNE_PANEL:
		case ContentPanel.CYTO_PANEL:
		case ContentPanel.PATH_PANEL:
		case ContentPanel.PATIENT_PANEL:
		case ContentPanel.PHYS_PANEL:
		case ContentPanel.SPEC_PANEL:
		case ContentPanel.PREFERENCES_VIEW:
			projectDriver.getMainMenuDriver().changeView(actionCommand, true);
			projectDriver.getMainMenuDriver().setSelectedButton(e.getSource());
			break;
		case "REFRESH": projectDriver.getMainMenuDriver().refresh();
						projectDriver.getMainMenuDriver().removeDetailsPanel();
			break;
		case "LOG_OUT": projectDriver.getMainMenuDriver().logout();
			break;
		case "LOG_IN" : projectDriver.login();
			break;
		case "EDIT_RECORD": projectDriver.getMainMenuDriver().editCurrentForm();
			break;
		case "SAVE_RECORD": projectDriver.getMainMenuDriver().saveCurrentForm();
			break;
		case "CANCEL_EDIT": projectDriver.getMainMenuDriver().cancelCurrentForm();
			break;
		case "NEW_HISTOPATHOLOGY_RECORD":	projectDriver.getMainMenuDriver().createNew(DetailPanel.HISTOPATHOLOGY);
			break;
		case "NEW_GYNECOLOGY_RECORD":	projectDriver.getMainMenuDriver().createNew(DetailPanel.GYNECOLOGY);
			break;
		case "NEW_CYTOLOGY_RECORD":	projectDriver.getMainMenuDriver().createNew(DetailPanel.CYTOLOGY);
			break;
		case "NEW_PATIENT":	projectDriver.getMainMenuDriver().createNew(DetailPanel.PATIENT);
			break;
		case "LOAD_EXISTING_PATIENT": projectDriver.getMainMenuDriver().changeView(ContentPanel.PATIENT_PANEL, false);
									  projectDriver.getMainMenuDriver().setSelectedButton(ContentPanel.PATIENT_PANEL);
/*		case "PRINT_RECORD": projectDriver.getMainMenuDriver().printCurrentForm();
			break;
		
		*/
		
		}
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		e.getComponent().setBackground(Color.decode(MainMenu.HOVER));
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		e.getComponent().setBackground(Color.decode(MainMenu.NORMAL));
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		e.getComponent().setBackground(Color.decode(MainMenu.PRESSED));
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getComponent().contains(e.getPoint()))
		{
			e.getComponent().setBackground(Color.decode(MainMenu.HOVER));
			if(e.getComponent() instanceof ListItem)
			{
				ListItem item = (ListItem)e.getComponent();
				projectDriver.getMainMenuDriver().viewItem(item);
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
		projectDriver.getMainMenuDriver().applyFilter();	
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
			
	}
}
