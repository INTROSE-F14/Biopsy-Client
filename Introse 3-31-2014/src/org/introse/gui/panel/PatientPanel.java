package org.introse.gui.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.introse.Constants;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.Form;
import org.introse.gui.form.PatientForm;

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
		((PatientForm)patientForm).setViewOnly(false);
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
		editOrSaveButton.setIconTextGap(7);
		printOrCancelButton.setIconTextGap(7);
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
		editOrSaveButton.addMouseListener(listener);
		printOrCancelButton.addMouseListener(listener);
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
		switch(mode)
		{
			case Constants.ActionConstants.VIEW: editOrSaveButton.setActionCommand(Constants.ActionConstants.EDIT_RECORD);
			 		   printOrCancelButton.setActionCommand(Constants.ActionConstants.PRINT);
			 		   editOrSaveButton.setText("Edit");
			 		   printOrCancelButton.setText("Print");
			 		   ((Form)patientForm).setEditable(false);
			 		   editOrSaveButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/edit.png")));
			 			printOrCancelButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/print.png")));
			 		   break;
			case Constants.ActionConstants.EDIT: 
			case Constants.ActionConstants.NEW:  
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
					   printOrCancelButton.setActionCommand(Constants.ActionConstants.CANCEL);
					   editOrSaveButton.setText("Save");
					   printOrCancelButton.setText("Cancel");
					   ((Form)patientForm).setEditable(true);
					   editOrSaveButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/save.png")));
						printOrCancelButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/cancel.png")));
			   		   break;
		}
	}

	@Override
	public boolean areFieldsValid() 
	{
		return ((Form)patientForm).areFieldsValid();
	}


	@Override
	public Object getObject() 
	{
		return ((Form)patientForm).getObject();
	}
	
	public Form getPatientForm()
	{
		return (Form)patientForm;
	}
}
