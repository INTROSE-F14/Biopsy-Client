package org.introse.gui.event;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.introse.Constants;
import org.introse.ProjectDriver;
import org.introse.gui.button.TabButton;

public class TabListener implements MouseListener 
{
	private ProjectDriver driver;
	public TabListener(ProjectDriver driver)
	{
		this.driver = driver;
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
		TabButton source = (TabButton)e.getSource();
		if(!source.getState())
			source.setBackground(Color.decode(Constants.StyleConstants.TERTIARY_COLOR));
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		final TabButton source = (TabButton)e.getComponent();
		if(source.contains(e.getPoint()))
		{
			final String name = source.getName();
			driver.changeView(name, false);
		}
	}

}
