package org.introse.gui.panel;

import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

public abstract class ListItem extends JPanel
{
	protected List<String> labels;
	
	public ListItem()
	{
		super(new GridBagLayout());
		labels = new Vector<String>();
	}
	
	public abstract void initializePanel();
	
	public int getLabelCount()
	{
		return labels.size();
	}
	
	public String getLabel(int index)
	{
		return labels.get(index);
	}
	
	public void addListener(MouseListener listener)
	{
		addMouseListener(listener);
	}
}
