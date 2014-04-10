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
		System.out.println("ENTERED");
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
			ListItem listItem = (ListItem)e.getComponent();
			driver.viewItem(listItem);
		}
	}

}
