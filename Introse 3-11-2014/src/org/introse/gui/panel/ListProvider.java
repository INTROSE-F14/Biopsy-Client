package org.introse.gui.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.introse.Constants.StyleConstants;
import org.introse.core.Preferences;
import org.introse.gui.window.MainMenu;

public class ListProvider {

	private JPanel listPanel;
	private JPanel container;
	private JPanel emptyPanel;
	private JScrollPane listScroller;
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
		listScroller.setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.3), 
				(int)(Preferences.getScreenHeight()* 0.8)));
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/res/icons/empty.png"));
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(null, image.getGraphics(), 0, 0);
		Image scaledImage = image.getScaledInstance(image.getWidth() * (Preferences.getScreenWidth() / Preferences.BASE_WIDTH), 
				image.getHeight() * (Preferences.getScreenHeight() / Preferences.BASE_HEIGHT), 
				Image.SCALE_FAST);
		emptyPanel = new JPanel(new BorderLayout());
		JLabel emptyLabel = new JLabel("No records found");
		emptyLabel.setIcon(new ImageIcon(scaledImage));
		emptyLabel.setFont(MainMenu.PRIMARY_FONT.deriveFont(StyleConstants.HEADER));
		emptyLabel.setHorizontalTextPosition(JLabel.CENTER);
		emptyLabel.setVerticalTextPosition(JLabel.BOTTOM);
		emptyPanel.setBorder(new EmptyBorder(0,60,0,0));
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
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weighty = 0.0;
		int y = 0;
		while(i.hasNext())
		{
			ListItem listItem = i.next();
			listItem.setPreferredSize(new Dimension(listScroller.getPreferredSize().width - 20,
					listItem.getPreferredSize().height));
			listItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			listItem.addListener(listener);
			if(!i.hasNext())
				c.weighty = 1.0;
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
