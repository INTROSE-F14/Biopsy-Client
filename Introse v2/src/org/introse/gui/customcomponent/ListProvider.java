package org.introse.gui.customcomponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.introse.gui.MainMenu;

public class ListProvider {

	private JPanel listPanel;
	private JPanel container;
	private JScrollPane listScroller;
	private List<ListItem> list;
	public static final int HISTOPATHOLOGY_RECORD = 0;
	public static final int GYNECOLOGY_RECORD = 1;
	public static final int CYTOLOGY_RECORD = 2;
	public static final int PATIENT = 1;
	public static final int OTHERS = 2;
	private MouseListener listener;
	
	public ListProvider()
	{
		container = new JPanel();
		container.setBackground(Color.white);
		listPanel = new JPanel(new GridLayout(0, 1, 0, 1)); 
		listPanel.setBorder(new EmptyBorder(1,1,1,1));
		listPanel.setBackground(Color.gray);
		listScroller = new JScrollPane(listPanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setBorder(null);
		container.add(listScroller, BorderLayout.CENTER);
	}
	
	public void addListener(MouseListener listener)
	{
		this.listener = listener;
	}
	
	public void updateList(List<ListItem> list)
	{
		this.list = list;
		listPanel.removeAll();
		if(list.size() < 1)
		{
			listPanel.setBackground(Color.decode(MainMenu.NORMAL));
			listPanel.add(new JLabel("No records found"));
			return;
		}
		
		Iterator<ListItem> i = list.iterator();
		while(i.hasNext())
		{
			ListItem listItem = i.next();
			listItem.addListener(listener);
			listPanel.add(listItem);
		}
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = g.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
		listScroller.setPreferredSize(new Dimension((int)(rect.width * 0.3), (int)(rect.height * 0.7)));
	}
	public JPanel getPanel()
	{
		return container;
	}
	
	public ListItem getItem(int index)
	{
		return list.get(index);
	}
}
