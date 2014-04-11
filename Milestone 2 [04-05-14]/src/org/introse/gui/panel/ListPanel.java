package org.introse.gui.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.introse.Constants.ActionConstants;
import org.introse.Constants.StyleConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.Preferences;
import org.introse.gui.window.LoginWindow;

public class ListPanel extends JPanel
{
	protected JPanel cardPanel;
	protected JPanel listPanel;
	protected JPanel emptyPanel;
	protected JPanel refreshPanel;
	protected JPanel buttonPanel;
	protected JScrollPane listScroller;
	protected MouseListener listener;
	protected List<ListItem> list;
	protected int orientation;
	protected JButton next,previous;
	private int start, range, size, rows;
	private JLabel currentRange;
	
	public ListPanel(int orientation, int range, int rows)
	{
		super(new GridBagLayout());
		start = 0;
		this.rows = rows;
		this.range = range;
		setBackground(Color.white);
		initUI();
		this.orientation = orientation;
		size = 0;
		list  = new Vector<ListItem>();
		cardPanel.add(TitleConstants.LIST_PANEL, listScroller);
		cardPanel.add(TitleConstants.EMPTY_PANEL, emptyPanel);
		cardPanel.add(TitleConstants.REFRESH_PANEL, refreshPanel);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(cardPanel, c);
		c.gridy = 1;
		c.weighty = 0.0;
		add(buttonPanel, c);
		updateButton();
	}
	
	protected void initUI()
	{
		currentRange = new JLabel();
		currentRange.setOpaque(true);
		currentRange.setBackground(Color.white);
		currentRange.setHorizontalAlignment(SwingConstants.CENTER);
		currentRange.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.MENU));
		next = new JButton();
		previous = new JButton();
		next.setContentAreaFilled(false);
		next.setBorderPainted(false);
		previous.setContentAreaFilled(false);
		previous.setBorderPainted(false);
		next.setIcon(new ImageIcon(getClass().getResource("/res/icons/next.png")));
		next.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/next_rollover.png")));
		previous.setIcon(new ImageIcon(getClass().getResource("/res/icons/previous.png")));
		previous.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/previous_rollover.png")));
		cardPanel = new JPanel(new CardLayout());
		buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.setBackground(Color.white);
		buttonPanel.add(previous);
		buttonPanel.add(currentRange);
		buttonPanel.add(next);
		listPanel = new JPanel(new GridBagLayout());
		emptyPanel = new JPanel(new GridBagLayout());
		refreshPanel = new JPanel(new GridBagLayout());
		
		if(orientation == SwingConstants.VERTICAL)
			listScroller = new JScrollPane(listPanel,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		else listScroller = new JScrollPane(listPanel,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setBorder(null);

		ImageIcon icon = new ImageIcon(getClass().getResource("/res/icons/empty.png"));
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(null, image.getGraphics(), 0, 0);
		
		double scaleWidth = 1.0 * Preferences.getScreenWidth() / Preferences.BASE_WIDTH;;
		double scaleHeight = 1.0 * Preferences.getScreenHeight() / Preferences.BASE_HEIGHT;
		if(scaleWidth > 1)
			scaleWidth = 1;
		if(scaleHeight > 1)
			scaleHeight = 1;
		Image scaledImage = image.getScaledInstance((int)(image.getWidth() * scaleWidth), 
				(int)(image.getHeight() * scaleHeight), 
				Image.SCALE_DEFAULT);
		
		JLabel emptyLabel = new JLabel(TitleConstants.EMPTY_PANEL);
		emptyLabel.setIcon(new ImageIcon(scaledImage));
		emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emptyLabel.setHorizontalTextPosition(JLabel.CENTER);
		emptyLabel.setVerticalTextPosition(JLabel.BOTTOM);
		emptyLabel.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.HEADER - 20));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		emptyPanel.add(emptyLabel, c);
		
		JLabel refreshLabel = new JLabel("refreshing list");
		refreshLabel.setIcon(new ImageIcon(getClass().getResource("/res/icons/refreshing.gif")));
		refreshLabel.setHorizontalAlignment(SwingConstants.CENTER);
		refreshLabel.setHorizontalTextPosition(JLabel.CENTER);
		refreshLabel.setVerticalTextPosition(JLabel.BOTTOM);
		refreshLabel.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.HEADER - 20));
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		refreshPanel.add(refreshLabel, c);
		
		listPanel.setBackground(Color.white);
		emptyPanel.setBackground(Color.white);
		refreshPanel.setBackground(Color.white);
		listPanel.setBorder(new EmptyBorder(0,0,0,0));
	}
	
	public void addMouseListener(MouseListener listener)
	{
		this.listener = listener;
	}
	
	public void addButtonListener(ActionListener listener)
	{
		next.addActionListener(listener);
		next.setActionCommand(ActionConstants.NEXT);
		previous.addActionListener(listener);
		previous.setActionCommand(ActionConstants.PREVIOUS);
	}
	
	public void updateViewable(List<ListItem> list)
	{
		this.list = list;
		listPanel.removeAll();
		if(orientation == SwingConstants.VERTICAL)
			generateVerticalList(list);
		else if(orientation == SwingConstants.HORIZONTAL)
			generateHorizontalList(list);
	}
	
	protected void generateVerticalList(List<ListItem> items)
	{
		Iterator<ListItem> i = items.iterator();
		final GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weighty = 0.0;
		c.weightx = 1.0;
		int y = 0;
		while(i.hasNext())
		{
			final ListItem listItem = i.next();
			listItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			listItem.addListener(listener);
			c.gridy = y++;
			c.insets = new Insets(0,5,5,5);
			if(!i.hasNext())
				c.weighty = 1.0;
			listPanel.add(listItem, c);
		}
	}
	
	protected void generateHorizontalList(List<ListItem> items)
	{
		Iterator<ListItem> i = items.iterator();
		final GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 0.0;
		int y = 0;
		int x = 0;
		while(i.hasNext())
		{				
			final ListItem listItem = i.next();
			listItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			listItem.addListener(listener);
			c.gridy = y;
			c.gridx = x;
			c.insets = new Insets(5,5,0,0);
			if(!i.hasNext())
				c.weightx = 1.0;
			if(y == (rows-1) || (list.size() < rows && !i.hasNext()))
				c.weighty = 1.0;
			else c.weighty = 0.0;
			
			if(y + 1 > (rows-1))
			{
				y = 0;
				x++;
			}
			else y++;
			listPanel.add(listItem, c);
		}
	}
	
	public JScrollPane getScroller()
	{
		return listScroller;
	}
	
	public void showPanel(String view)
	{
		CardLayout cl = (CardLayout)cardPanel.getLayout();
		cl.show(cardPanel, view);
	}
	
	public int getListSize()
	{
		return size;
	}
	
	public void setListSize(int size)
	{
		this.size = size;
		if(size > range)
		{
			buttonPanel.setVisible(true);
			updateButton();
		}
		else buttonPanel.setVisible(false);
	}
	
	public void next()
	{
		if(start + range < size)
			start += range;
		updateButton();
	}
	
	public void updateButton()
	{
		if(start > 0)
			previous.setVisible(true);
		else previous.setVisible(false);
		
		if(start + range <  size)
			next.setVisible(true);
		else next.setVisible(false);
		int max = start + range;
		if(max > size)
			max = size;
		currentRange.setText((start+1) + "-" + max);
		
	}
	public void previous()
	{
		if(start - range >= 0)
			start -= range;
		updateButton();
	}
	
	public int getStart()
	{
		return start;
	}
	
	public int getRange()
	{
		return range;
	}
	
	public void setStart(int start)
	{
		this.start = start;
	}
}
