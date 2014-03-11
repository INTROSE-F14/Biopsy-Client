package org.introse.core;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import org.introse.Constants;
import org.introse.Constants.RecordTable;
import org.introse.gui.panel.ListItem;

public class Record  extends ListItem implements Printable
{
	protected HashMap<String, Object> attributes;
	
	public Record()
	{
		super();
		attributes = new HashMap<>();
	}

	public Object getAttribute(String key)
	{
		return attributes.get(key);
	}
	
	public void putAttribute(String key, Object newValue)
	{
		if(attributes.containsKey(key))
		attributes.remove(attributes.get(key));
		attributes.put(key, newValue);
	}
	
	/*
	public boolean matches(Record record)
	{
		if(record.getClass().equals(getClass()))
		{
			HashMap<String, Object> attributes = record.getHashMap();
			Set<String> keys = attributes.keySet();
			Iterator<String> i = keys.iterator();
			while(i.hasNext())
			{
				String currentKey = i.next();
				System.out.println(currentKey);
				Object foreignValue = attributes.get(currentKey);
				Object myValue = this.attributes.get(currentKey);
				
				if(myValue != null)
				{
					//checking for patient (two patients are equal if their IDs are equal)
					if(foreignValue instanceof Patient)
					{
						Patient p = (Patient)foreignValue;
						Patient p2 = (Patient)myValue;
						if(!p2.equals(p))
							return false;
					}
					//checking for calendar
					else if(foreignValue instanceof Calendar || foreignValue instanceof Date)
					{
						if(!myValue.toString().equals(foreignValue.toString()))
							return false;
					}
					else if(foreignValue instanceof String)
					{
						if(!(((String)myValue).equalsIgnoreCase((String)foreignValue)))
							return false;
					}
					//checking for primitive values
					else
					{
						if(!myValue.equals(foreignValue))
						return false;
					}
				}
				else return false;
			}
			return true;
		}
		return false;
	}
	/*
	@Override
	public boolean equals(Object b)
	{
		if(!(getClass().equals(b.getClass())))
		{
			System.out.println("CLASS 1: " + getClass() + " CLASS 2: " + b.getClass());
			System.out.println("Different classes");
			return false;
		}
		Record r = (Record)b;
		Object rId = r.getAttribute(Constants.RecordTable.REF_NUM);
		Object myId = getAttribute(Constants.RecordTable.REF_NUM);
		if(rId != null && myId != null)
			return rId.equals(myId);
		return super.equals(b);
	}*/
	
	public void initializePanel()
	{
		JLabel irnLabel = new JLabel("Internal Reference Number"), 
				pathologistLabel = new JLabel("Pathologist"), 
				specimenLabel = new JLabel("Specimen"), 
				physicianLabel = new JLabel("Physician");
		
		String label1 = (String)getAttribute(RecordTable.REF_NUM), 
				label2 = (String)getAttribute(RecordTable.PATHOLOGIST), 
				label3 = (String)getAttribute(RecordTable.SPECIMEN), 
				label4 = (String)getAttribute(RecordTable.PHYSICIAN);
		
		JLabel irnValue = new JLabel(label1),
				pathologistValue = new JLabel(label2),
				physicianValue = new JLabel(label4),
				specimenValue = new JLabel(label3);
		irnValue.setFont(irnValue.getFont().deriveFont(Font.BOLD));
		this.labels.add(label1);
		this.labels.add(label2);
		this.labels.add(label3);
		this.labels.add(label4);
		setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		setBorder(new EmptyBorder(20,20,20,20));
		
		int y = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,20,0,20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = y;
		add(irnLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,0,0);
		c.weightx = 1.0;
		c.gridy = y++;
		c.gridx = 1;
		add(irnValue, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,20,0,20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = y;
		add(specimenLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,0,0);
		c.gridx = 1;
		c.gridy = y++;
		add(specimenValue, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,20,0,20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = y;
		add(pathologistLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,0,0);
		c.weightx = 1.0;
		c.gridy = y++;
		c.gridx = 1;
		add(pathologistValue, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,20,0,20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = y;
		add(physicianLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,0,0);
		c.gridx = 1;
		c.gridy = y++;
		add(physicianValue, c);
	}
	
	@Override
	public int print(Graphics g, PageFormat pf, int page)
	{
		return 0;
	}
}
