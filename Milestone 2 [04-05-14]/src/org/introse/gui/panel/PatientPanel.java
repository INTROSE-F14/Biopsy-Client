package org.introse.gui.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.Form;
import org.introse.gui.form.PatientForm;

public class PatientPanel extends DetailPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel patientForm;
	private JButton editOrSaveButton, cancelButton, backButton;
	
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
		backButton = new JButton();
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setOpaque(true);
		backButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		backButton.setIconTextGap(7);
		backButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/back.png")));
		backButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/back_rollover.png")));
		editOrSaveButton = new JButton();
		cancelButton = new JButton("Cancel");
		editOrSaveButton.setBorderPainted(false);
		editOrSaveButton.setContentAreaFilled(false);
		editOrSaveButton.setOpaque(true);
		cancelButton.setBorderPainted(false);
		cancelButton.setContentAreaFilled(false);
		cancelButton.setOpaque(true);
		cancelButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/cancel.png")));
		cancelButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		editOrSaveButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		editOrSaveButton.setIconTextGap(7);
		cancelButton.setIconTextGap(7);
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		add(backButton, c);
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 3;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,0,2);
		add(editOrSaveButton, c);
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 0.0;
		c.insets = new Insets(0,0,0,0);
		add(cancelButton, c);
		c.insets = new Insets(20,0,0,0);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weighty = 1.0;
		add(patientForm, c);
	}
	
	public void addListener(CustomListener listener)
	{
		backButton.addActionListener(listener);
		backButton.setActionCommand(ActionConstants.BACK);
		editOrSaveButton.addActionListener(listener);
		cancelButton.addActionListener(listener);
		editOrSaveButton.addMouseListener(listener);
		cancelButton.setActionCommand(ActionConstants.CANCEL);
		cancelButton.addMouseListener(listener);
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
		switch(mode)
		{
			case Constants.ActionConstants.VIEW: editOrSaveButton.setActionCommand(Constants.ActionConstants.EDIT_RECORD);
			 		   editOrSaveButton.setText("Edit");
			 		   editOrSaveButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/edit.png")));
			 		   cancelButton.setVisible(false);
			 		   ((Form)patientForm).setEditable(false);
			 		   break;
			case Constants.ActionConstants.EDIT: 
			case Constants.ActionConstants.NEW:  
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
					   cancelButton.setVisible(true);
					   editOrSaveButton.setText("Save");
					   ((Form)patientForm).setEditable(true);
					   editOrSaveButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/save.png")));
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
