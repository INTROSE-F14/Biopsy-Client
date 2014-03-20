package org.introse.core;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import org.introse.Constants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.StyleConstants;
import org.introse.gui.panel.ListItem;
import org.introse.gui.window.MainMenu;

public class Patient extends ListItem
{
	private HashMap<String, Object> attributes;
	private List<Record> records;
	
	public Patient()
	{
		super();
		attributes = new HashMap<>();
		records = new Vector<Record>();
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
	public boolean matches(Patient patient)
	{
		if(patient.getClass().equals(getClass()))
		{
			HashMap<String, Object> attributes = patient.getHashMap();
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
					
					//checking for calendar
					if(foreignValue instanceof Calendar || foreignValue instanceof Date)
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

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Patient))
			return false;
		Patient p = (Patient)o;
		Object pId = p.getAttribute(PatientTable.PATIENT_ID.toString());
		Object myId = getAttribute(PatientTable.PATIENT_ID.toString());
		if(pId != null && myId != null)
			return pId.equals(myId);
		return super.equals(o);
	}
	*/
	@Override
	public void initializePanel() 
	{
		String label1 = getAttribute(PatientTable.LAST_NAME) + ", " + getAttribute(PatientTable.FIRST_NAME) + 
				" " + getAttribute(PatientTable.MIDDLE_NAME);
		String label2 = (String)getAttribute(PatientTable.GENDER);
		setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		setBorder(new EmptyBorder(20,20,20,20));
		setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.3), 
				(int)(Preferences.getScreenHeight() * 0.1)));;
		
		this.labels.add(label1);
		this.labels.add(label2);
		
		JLabel nameLabel = new JLabel("Name");
		JLabel genderLabel = new JLabel("Gender");
		JLabel nameValue = new JLabel(label1);
		JLabel genderValue = new JLabel(label2);
		nameValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(Font.BOLD, Constants.StyleConstants.SUBHEADER));
		setPreferredSize(new Dimension(350,100));
		nameLabel.setFont(MainMenu.PRIMARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		genderLabel.setFont(nameLabel.getFont());
		genderValue.setFont(nameLabel.getFont());
		
		int y = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,20,0,20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = y;
		add(nameLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,0,0);
		c.weightx = 1.0;
		c.gridy = y++;
		c.gridx = 1;
		add(nameValue, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,20,0,20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = y;
		add(genderLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,0,0);
		c.gridx = 1;
		c.gridy = y++;
		add(genderValue, c);
	}
}