package org.introse.core;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import org.introse.Constants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.StyleConstants;
import org.introse.gui.panel.ListItem;
import org.introse.gui.window.LoginWindow;

public class Patient extends ListItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> attributes;
	
	public Patient()
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
	
	@Override
	public void initializePanel() 
	{
		setPreferredSize(new Dimension((450), 100));
		String label1 = getAttribute(PatientTable.LAST_NAME) + ", " + getAttribute(PatientTable.FIRST_NAME) + 
				" " + getAttribute(PatientTable.MIDDLE_NAME);
		String label2 = "";
		CustomCalendar birthday =  (CustomCalendar)getAttribute(PatientTable.BIRTHDAY);
		String label3 = "N/A";
		if(birthday != null)
			label3 = birthday.toString();
		setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		setBorder(new EmptyBorder(20,20,20,20));
		
		if(((String)getAttribute(PatientTable.GENDER)).charAt(0) == 'M')
			label2 = "Male";
		else label2 = "Female";
		
		this.labels.add(label1);
		this.labels.add(label2);
		this.labels.add(label3);
		
		JLabel nameLabel = new JLabel("Name");
		JLabel genderLabel = new JLabel("Gender");
		JLabel birthdayLabel = new JLabel("Birthday");
		JLabel nameValue = new JLabel(label1);
		JLabel genderValue = new JLabel(label2);
		JLabel birthdayValue = new JLabel(label3);
		nameValue.setFont(LoginWindow.PRIMARY_FONT.deriveFont(Font.BOLD, Constants.StyleConstants.SUBHEADER));
		
		nameLabel.setFont(LoginWindow.PRIMARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		genderLabel.setFont(nameLabel.getFont());
		genderValue.setFont(nameLabel.getFont());
		birthdayLabel.setFont(nameLabel.getFont());
		birthdayValue.setFont(nameLabel.getFont());
		
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
		c.insets = new Insets(0,0,0,50);
		c.gridy = y++;
		c.gridx = 1;
		add(nameValue, c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0,0,0,0);
		add(deleteButton, c);
		
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
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,20,20,20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = y;
		add(birthdayLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,20,0);
		c.gridx = 1;
		c.gridy = y++;
		add(birthdayValue, c);
		
	}
}