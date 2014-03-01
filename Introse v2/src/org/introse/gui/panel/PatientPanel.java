package org.introse.gui.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.Form;

public class PatientPanel extends DetailPanel 
{
	private JPanel patientForm;
	private JButton editOrSaveButton;
	private JButton printOrCancelButton;
	
	public PatientPanel(JPanel patientForm, int mode)
	{
		super(new GridBagLayout(), mode);
		setBackground(Color.white);
		this.patientForm = patientForm;
		initializeComponents();
		layoutComponents();
		setMode(mode);
	}
	
	private void initializeComponents()
	{
		editOrSaveButton = new JButton();
		printOrCancelButton = new JButton();
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 3;
		c.gridy = 0;
		c.weighty = 0.0;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,0,2);
		add(editOrSaveButton, c);
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0,0,0,0);
		add(printOrCancelButton, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weighty = 0.0;
		add(patientForm, c);
	}
	
	public void addListener(CustomListener listener)
	{
		editOrSaveButton.addActionListener(listener);
		printOrCancelButton.addActionListener(listener);
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
		switch(mode)
		{
			case VIEW: editOrSaveButton.setActionCommand("EDIT_RECORD");
			 		   printOrCancelButton.setActionCommand("PRINT_RECORD");
			 		   editOrSaveButton.setText("Edit");
			 		   printOrCancelButton.setText("Print");
			 		   ((Form)patientForm).setEditable(false);
			 		   break;
			case EDIT: 
			case NEW:  editOrSaveButton.setActionCommand("SAVE_RECORD");
					   printOrCancelButton.setActionCommand("CANCEL_EDIT");
					   editOrSaveButton.setText("Save");
					   printOrCancelButton.setText("Cancel");
					   ((Form)patientForm).setEditable(true);
			   		   break;
		}
	}

	@Override
	public Record getRecord() {
		return null;
	}

	@Override
	public Patient getPatient() 
	{
		return (Patient)((Form)patientForm).getObject();
	}

	@Override
	public boolean areFieldsValid() 
	{
		return ((Form)patientForm).areFieldsValid();
	}

	public Form getPatientForm()
	{
		return (Form)patientForm;
	}
}
