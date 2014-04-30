package org.introse.gui.event;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.TitleConstants;
import org.introse.ProjectDriver;
import org.introse.gui.button.NavButton;
import org.introse.gui.dialogbox.PopupDialog;

public class NavigationListener implements MouseListener {
	
	private ProjectDriver projectDriver;
	
	public NavigationListener(ProjectDriver driver)
	{
		this.projectDriver = driver;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		e.getComponent().setBackground(Color.decode(Constants.StyleConstants.QUARTERNARY_COLOR));
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		e.getComponent().setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		NavButton source = (NavButton)e.getSource();
		if(!source.getState())
			source.setBackground(Color.decode(Constants.StyleConstants.TERTIARY_COLOR));

	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		final NavButton source = (NavButton)e.getComponent();
		if(source.contains(e.getPoint()))
		{
			final String name = source.getName();
			if(projectDriver.getDetailPanelStatus() == ActionConstants.EDIT || 
			projectDriver.getDetailPanelStatus() == ActionConstants.NEW)
			{
				PopupDialog popup = new PopupDialog(projectDriver.getMainMenu(), "Cancel form", 
						TitleConstants.DISCARD_CHANGES_MESSAGE, "Yes", "No");
				if(!name.equals(ActionConstants.LOG_OUT))
				{
					popup.addPropertyChangeListener(new PropertyChangeListener()
					{
						@Override
						public void propertyChange(PropertyChangeEvent evt) 
						{
							if(evt.getPropertyName().equals("POSITIVE"))
							{
								projectDriver.cancelCurrentForm();
								projectDriver.changeView(name, false);
								projectDriver.setSelectedButton(source);
								source.setBackground(Color.decode(Constants.StyleConstants.QUARTERNARY_COLOR));
							}
						}
					});
				}
				else 
				{
					popup.addPropertyChangeListener(new PropertyChangeListener()
					{
						@Override
						public void propertyChange(PropertyChangeEvent evt) 
						{
							if(evt.getPropertyName().equals("POSITIVE"))
							{
								projectDriver.cancelCurrentForm();
								projectDriver.logout();
							}
						}
					});
				}
				popup.showGui();
			}
			else
			{
				if(name.equals(ActionConstants.LOG_OUT))
					projectDriver.logout();
				else
				{
					projectDriver.removeDetailsPanel();
					projectDriver.changeView(name, false);
					projectDriver.setSelectedButton(source);
					source.setBackground(Color.decode(Constants.StyleConstants.QUARTERNARY_COLOR));
				}
			}
		}
	}
}
