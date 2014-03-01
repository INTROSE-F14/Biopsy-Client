package org.introse.gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.introse.Constants;


public class ListItem extends JPanel{
	
	private String ID;
	private String h1;
	private String h2;
	private String s1;
	private String s2;
	private int itemType;
	
	public ListItem(String ID, String h1, String h2, String s1, String s2, int itemType)
	{
		super(new GridBagLayout());
		this.ID = ID;
		this.h1 = h1;
		this.h2 = h2;
		this.s1 = s1;
		this.s2= s2;
		this.itemType = itemType;
		setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		setBorder(new EmptyBorder(20,20,20,20));
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		JLabel header1 = new JLabel(h1);
		header1.setFont(header1.getFont().deriveFont(Font.BOLD, Constants.StyleConstants.MENU));
		add(header1, c);
		c.gridy = 1;
		JLabel header2 = new JLabel(h2);
		add(header2, c);
		c.gridy = 2;
		JLabel subheader1 = new JLabel(s1);
		add(subheader1, c);
		c.gridy = 3;
		JLabel subheader2 = new JLabel(s2);
		add(subheader2, c);
	}
	
	public String getHeader1()
	{
		return h1;
	}
	
	public String getHeader2()
	{
		return h2;
	}
	
	public String getSubheader1()
	{
		return s1;
	}
	
	public String getSubheader2()
	{
		return s2;
	}
	
	public void addListener(MouseListener listener)
	{
		addMouseListener(listener);
	}
	
	public String getID()
	{
		return ID;
	}
	
	public int getType()
	{
		return itemType;
	}
}
