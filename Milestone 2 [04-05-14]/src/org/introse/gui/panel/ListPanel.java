package org.introse.gui.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import org.introse.Constants.StyleConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.Preferences;
import org.introse.gui.window.LoginWindow;

public class ListPanel extends JPanel
{
	private JPanel listPanel;
	private JPanel emptyPanel;
	private JPanel refreshPanel;
	private JScrollPane listScroller;
	private MouseListener listener;
	private List<ListItem> list;
	private int orientation;
	
	public ListPanel(int orientation)
	{
		super(new CardLayout());
		setBackground(Color.white);
		initUI();
		this.orientation = orientation;
		list  = new Vector<ListItem>();
		add(TitleConstants.LIST_PANEL, listScroller);
		add(TitleConstants.EMPTY_PANEL, emptyPanel);
		add(TitleConstants.REFRESH_PANEL, refreshPanel);
	}
	
	private void initUI()
	{
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
		
		JLabel emptyLabel = new JLabel("No records found");
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
		listPanel.setBorder(new EmptyBorder(5,0,5,5));
	}
	
	public void addListener(MouseListener listener)
	{
		this.listener = listener;
	}
	
	public void updateList(List<ListItem> list)
	{
		this.list = list;
		final List<ListItem> _list = list;
		listPanel.removeAll();
		
		if(orientation == SwingConstants.VERTICAL)
		{
			SwingWorker generatorWorker = new SwingWorker()
			{

				@Override
				protected Object doInBackground() throws Exception {
					generateVerticalList(_list);
					return null;
				}
				@Override
				protected void done()
				{
					firePropertyChange("DONE", null, null);
				}
			};
			generatorWorker.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals("DONE"))
					{
						listPanel.revalidate();
						listScroller.revalidate();
						revalidate();
					}
					
				}
			});
			generatorWorker.execute();
		}
		else if(orientation == SwingConstants.HORIZONTAL)
		{
			SwingWorker generatorWorker = new SwingWorker()
			{

				@Override
				protected Object doInBackground() throws Exception {
					generateHorizontalList(_list);
					return null;
				}
				@Override
				protected void done()
				{
					firePropertyChange("DONE", null, null);
				}
			};
			generatorWorker.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals("DONE"))
					{
						listPanel.revalidate();
						listScroller.revalidate();
						revalidate();
					}
					
				}
			});
			generatorWorker.execute();
		}
		
	}
	
	private void generateVerticalList(List<ListItem> items)
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
	
	private void generateHorizontalList(List<ListItem> items)
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
			if(y == 3 || (list.size() < 4 && !i.hasNext()))
				c.weighty = 1.0;
			else c.weighty = 0.0;
			
			if(y + 1 > 3)
			{
				y = 0;
				x++;
			}
			else y++;
			listPanel.add(listItem, c);
		}
	}
	
	public List<ListItem> getList()
	{
		return list;
	}
	
	public JScrollPane getScroller()
	{
		return listScroller;
	}
	
	public void showPanel(String view)
	{
		CardLayout cl = (CardLayout)getLayout();
		cl.show(this, view);
	}
}
