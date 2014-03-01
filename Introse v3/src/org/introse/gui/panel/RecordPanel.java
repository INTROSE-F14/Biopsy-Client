package org.introse.gui.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.introse.Constants;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.Form;
import org.introse.gui.form.PatientForm;

public class RecordPanel extends DetailPanel
{
	private JPanel recordForm;
	private JPanel patientForm;
	private JButton editOrSaveButton;
	private JButton printOrCancelButton;
	
	public RecordPanel(JPanel recordForm, JPanel patientForm, int mode)
	{
		super(new GridBagLayout(), mode);
		setBackground(Color.white);
		this.recordForm = recordForm;
		this.patientForm = patientForm;
		initializeComponents();
		layoutComponents();
		setMode(mode);
	}
	
	private void initializeComponents()
	{
		editOrSaveButton = new JButton();
		printOrCancelButton = new JButton();
		editOrSaveButton.setBorderPainted(false);
		editOrSaveButton.setContentAreaFilled(false);
		editOrSaveButton.setOpaque(true);
		printOrCancelButton.setBorderPainted(false);
		printOrCancelButton.setContentAreaFilled(false);
		printOrCancelButton.setOpaque(true);
		printOrCancelButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		editOrSaveButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
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
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 5;
		c.gridheight = 2;
		c.weighty = 1.0;
		add(recordForm, c);
	}
	
	public void addListener(CustomListener listener)
	{
		((PatientForm)patientForm).addListener(listener);
		editOrSaveButton.addActionListener(listener);
		printOrCancelButton.addActionListener(listener);
		editOrSaveButton.addMouseListener(listener);
		printOrCancelButton.addMouseListener(listener);
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
		switch(mode)
		{
			case Constants.ActionConstants.VIEW: 
				       editOrSaveButton.setActionCommand(Constants.ActionConstants.EDIT_RECORD);
			 		   printOrCancelButton.setActionCommand(Constants.ActionConstants.PRINT);
			 		   editOrSaveButton.setText("Edit");
			 		   printOrCancelButton.setText("Print");
			 		   ((Form)recordForm).setEditable(false);
			 		   ((Form)patientForm).setEditable(false);
			 		  editOrSaveButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/edit.png")));
			 			printOrCancelButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/print.png")));
			 		   break;
			case Constants.ActionConstants.EDIT: 
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
					   printOrCancelButton.setActionCommand(Constants.ActionConstants.CANCEL);
					   editOrSaveButton.setText("Save");
					   printOrCancelButton.setText("Cancel");
					   ((Form)recordForm).setEditable(true);
					   ((Form)patientForm).setEditable(false);
					   ((PatientForm)patientForm).setLoadExisting(false);
					   editOrSaveButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/save.png")));
						printOrCancelButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/cancel.png")));
					   break;
			case Constants.ActionConstants.NEW:  
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
			   		   printOrCancelButton.setActionCommand(Constants.ActionConstants.CANCEL);
			   		   editOrSaveButton.setText("Save");
			   		   printOrCancelButton.setText("Cancel");
			   		   ((Form)recordForm).setEditable(true);
			   		   ((Form)patientForm).setEditable(true);
			   		   ((PatientForm)patientForm).setLoadExisting(true);
			   		  editOrSaveButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/save.png")));
						printOrCancelButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/cancel.png")));
			   		   break;
		}
	}

	@Override
	public Record getRecord() 
	{
		return (Record)((Form)recordForm).getObject();
	}

	@Override
	public Patient getPatient() 
	{
		return (Patient)((Form)patientForm).getObject();
	}
	
	public boolean areFieldsValid()
	{
		if(!((Form)patientForm).areFieldsValid())
			return false;
		if(!((Form)recordForm).areFieldsValid())
			return false;
		return true;
	}
	
	public Form getRecordForm()
	{
		return (Form)recordForm;
	}
	
	public Form getPatientForm()
	{
		return (Form)patientForm;
	}
}
