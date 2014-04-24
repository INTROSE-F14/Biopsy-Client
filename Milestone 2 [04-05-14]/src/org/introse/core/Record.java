package org.introse.core;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.JLabel;

import org.introse.Constants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordTable;
import org.introse.Constants.StyleConstants;
import org.introse.gui.panel.ListItem;
import org.introse.gui.window.LoginWindow;

public class Record  extends ListItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	public void initializePanel()
	{	
		setPreferredSize(new Dimension((450), 120));

		Patient patient = (Patient)getAttribute(RecordTable.PATIENT);
		String patientName = patient.getAttribute(PatientTable.LAST_NAME) + 
				", " + patient.getAttribute(PatientTable.FIRST_NAME) + " " +
				patient.getAttribute(PatientTable.MIDDLE_NAME);
		JLabel irnLabel = new JLabel("Internal Reference Number"), 
				pathologistLabel = new JLabel("Pathologist"), 
				specimenLabel = new JLabel("Specimen"), 
				physicianLabel = new JLabel("Physician"),
				patientLabel = new JLabel("Patient");
		String  label2 = (String)getAttribute(RecordTable.PATHOLOGIST), 
				label3 = (String)getAttribute(RecordTable.SPECIMEN), 
				label4 = (String)getAttribute(RecordTable.PHYSICIAN),
				label5 = patientName;
		
		String label1 = null;
		if(getAttribute(RecordTable.RECORD_NUMBER) != null)
		{
			int number = (int)getAttribute(RecordTable.RECORD_NUMBER);
			int i;
			if(number > 999)
				i = 4;
			else if(number > 99)
				i = 3;
			else if(number > 9)
				i = 2;
			else i = 1;
			
			label1 = "" + number;
			for(int j = i; j < 4; j++)
			{
				label1 = "0" + label1;
			}
			label1 = ""+(char)getAttribute(RecordTable.RECORD_TYPE) + 
				getAttribute(RecordTable.RECORD_YEAR) + "-" + label1;
		}
		
		JLabel irnValue = new JLabel(label1),
				pathologistValue = new JLabel(label2),
				physicianValue = new JLabel(label4),
				specimenValue = new JLabel(label3),
				patientValue = new JLabel(label5);
		
		irnLabel.setFont(LoginWindow.PRIMARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		pathologistLabel.setFont(irnLabel.getFont());
		specimenLabel.setFont(irnLabel.getFont());
		physicianLabel.setFont(irnLabel.getFont());
		patientLabel.setFont(irnLabel.getFont());
		
		irnValue.setFont(LoginWindow.PRIMARY_FONT.deriveFont(Font.BOLD, StyleConstants.SUBHEADER));
		pathologistValue.setFont(irnLabel.getFont());
		specimenValue.setFont(irnLabel.getFont());
		physicianValue.setFont(irnLabel.getFont());
		patientValue.setFont(irnLabel.getFont());
		this.labels.add(label1);
		this.labels.add(label2);
		this.labels.add(label3);
		this.labels.add(label4);
		this.labels.add(label5);
		setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		int y = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(20,20,0,20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = y;
		add(irnLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(20,0,0,0);
		c.gridy = y++;
		c.gridx = 1;
		add(irnValue, c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.EAST;
		add(deleteButton, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,20,0,20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = y;
		add(patientLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,0,20);
		c.gridx = 1;
		c.gridy = y++;
		add(patientValue, c);
		
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
		c.insets = new Insets(0,0,0,20);
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
		c.insets = new Insets(0,0,0,20);
		c.weightx = 1.0;
		c.gridy = y++;
		c.gridx = 1;
		add(pathologistValue, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,20,20,20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = y;
		add(physicianLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,20,20);
		c.gridx = 1;
		c.gridy = y++;
		add(physicianValue, c);
	}
}
