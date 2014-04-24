package org.introse.gui.event;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.introse.Constants;
import org.introse.ProjectDriver;
import org.introse.gui.panel.ListItem;

public class ListListener implements MouseListener {

	private ProjectDriver driver;
	
	public ListListener(ProjectDriver driver)
	{
		super();
		this.driver = driver;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

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
		e.getComponent().setBackground(Color.decode(Constants.StyleConstants.QUARTERNARY_COLOR));
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getComponent().contains(e.getPoint()))
		{
			e.getComponent().setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
			ListItem listItem = (ListItem)e.getComponent();
			driver.viewItem(listItem);
		}
	}

}
