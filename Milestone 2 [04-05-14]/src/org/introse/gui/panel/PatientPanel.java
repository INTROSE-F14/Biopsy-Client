package org.introse.gui.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	private JLabel divider;
	private JButton editOrSaveButton, cancelButton, backButton;
	private ImageIcon editIcon, saveIcon, cancelIcon, editIconRollover, saveIconRollover, cancelIconRollover;
	
	
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
		divider = new JLabel("l");
		divider.setFont(getFont().deriveFont(25f));
		divider.setForeground(Color.LIGHT_GRAY);
		editIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_edit.png"));
		editIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_edit_hover.png"));
		saveIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_accept.png"));
		saveIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_accept_hover.png"));
		cancelIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_cancel.png"));
		cancelIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_cancel_hover.png"));
		
		backButton = new JButton();
		backButton.setFocusable(false);
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setOpaque(true);
		backButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		backButton.setIconTextGap(7);
		backButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_back.png")));
		backButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_back_hover.png")));
		editOrSaveButton = new JButton();
		cancelButton = new JButton();
		cancelButton.setFocusable(false);
		editOrSaveButton.setFocusable(false);
		cancelButton.setIcon(cancelIcon);
		cancelButton.setRolloverIcon(cancelIconRollover);
		editOrSaveButton.setBorderPainted(false);
		editOrSaveButton.setContentAreaFilled(false);
		editOrSaveButton.setOpaque(true);
		cancelButton.setBorderPainted(false);
		cancelButton.setContentAreaFilled(false);
		cancelButton.setOpaque(true);
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
		add(divider, c);
		c.gridx = 5;
		c.gridy = 0;
		c.weightx = 0.0;
		c.insets = new Insets(0,0,0,0);
		add(cancelButton, c);
		c.insets = new Insets(20,0,0,0);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 6;
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
		cancelButton.setActionCommand(ActionConstants.CANCEL);
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
		switch(mode)
		{
			case Constants.ActionConstants.VIEW: editOrSaveButton.setActionCommand(Constants.ActionConstants.EDIT_RECORD);
			 		   editOrSaveButton.setIcon(editIcon);
			 		   editOrSaveButton.setRolloverIcon(editIconRollover);
			 		   cancelButton.setVisible(false);
			 		   divider.setVisible(false);
			 		   ((Form)patientForm).setEditable(false);
			 		   break;
			case Constants.ActionConstants.EDIT: 
			case Constants.ActionConstants.NEW:  
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
					   cancelButton.setVisible(true);
					   ((Form)patientForm).setEditable(true);
					   divider.setVisible(true);
					   editOrSaveButton.setIcon(saveIcon);
					   editOrSaveButton.setRolloverIcon(saveIconRollover);
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
