package org.introse.gui.customcomponent;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ListProvider {

	private JPanel listPanel;
	private JPanel container;
	private JPanel emptyPanel;
	private JScrollPane listScroller;
	public static final int HISTOPATHOLOGY_RECORD = 0;
	public static final int GYNECOLOGY_RECORD = 1;
	public static final int CYTOLOGY_RECORD = 2;
	public static final int PATIENT = 3;
	public static final int OTHERS = 4;
	private MouseListener listener;
	
	public ListProvider()
	{
		container = new JPanel(new CardLayout());
		container.setBackground(Color.white);
		listPanel = new JPanel(new GridBagLayout());
		listPanel.setBackground(Color.white);
		listPanel.setBorder(new EmptyBorder(1,1,1,1));
		listScroller = new JScrollPane(listPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setBorder(null);
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = g.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
		listScroller.setPreferredSize(new Dimension((int)(rect.width * 0.3), (int)(rect.height * 0.7)));
		emptyPanel = new JPanel();
		JLabel emptyLabel = new JLabel("No records found");
		emptyPanel.add(emptyLabel, BorderLayout.CENTER);
		emptyPanel.setBackground(Color.white);
		container.add("LIST", listScroller);
		container.add("EMPTY", emptyPanel);
	}
	
	public void addListener(MouseListener listener)
	{
		this.listener = listener;
	}
	
	public void updateList(List<ListItem> list)
	{
		listPanel.removeAll();

		CardLayout cl = (CardLayout)container.getLayout();
		if(list.size() < 1)
			cl.last(container);
		else cl.first(container);
		
		Iterator<ListItem> i = list.iterator();
		GridBagConstraints c = new GridBagConstraints();
		int y = 0;
		while(i.hasNext())
		{
			
			ListItem listItem = i.next();
			listItem.setPreferredSize(new Dimension(listScroller.getPreferredSize().width - 20,100));
			listItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			listItem.addListener(listener);
			c.gridy = y++;
			listPanel.add(listItem, c);
		}
		listPanel.revalidate();
		listScroller.revalidate();
		container.revalidate();
	}
	
	public JPanel getPanel()
	{
		return container;
	}
}
