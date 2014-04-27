package org.introse.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.StyleConstants;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.PatientForm;

public class PatientPanel extends DetailPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PatientForm patientForm;
	private JPanel topPanel, buttonPanel;
	private JButton editOrSaveButton, deleteOrCancelButton, backButton;
	private ImageIcon editIcon, saveIcon, cancelIcon, editIconRollover, 
	saveIconRollover, cancelIconRollover, deleteIcon, deleteIconRollover;
	
	
	public PatientPanel(PatientForm patientForm, int mode)
	{
		super(new GridBagLayout(), mode);
		setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		this.patientForm = patientForm;
		initializeComponents();
		layoutComponents();
		setMode(mode);
		patientForm.setViewOnly(false);
	}
	
	private void initializeComponents()
	{
		topPanel = new JPanel(new GridBagLayout());
		topPanel.setBackground(new Color(0f,0f,0f,0f));
		buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		editIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_edit.png"));
		editIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_edit_hover.png"));
		saveIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_accept.png"));
		saveIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_accept_hover.png"));
		cancelIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_cancel.png"));
		cancelIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_cancel_hover.png"));
		deleteIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_delete2.png"));
		deleteIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_delete2_hover.png"));
		backButton = new JButton();
		backButton.setFocusable(false);
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setOpaque(true);
		backButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		backButton.setIconTextGap(7);
		backButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_back.png")));
		backButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_back_hover.png")));
		editOrSaveButton = new JButton();
		deleteOrCancelButton = new JButton();
		deleteOrCancelButton.setFocusable(false);
		editOrSaveButton.setFocusable(false);
		deleteOrCancelButton.setIcon(cancelIcon);
		deleteOrCancelButton.setRolloverIcon(cancelIconRollover);
		editOrSaveButton.setBorderPainted(false);
		editOrSaveButton.setContentAreaFilled(false);
		editOrSaveButton.setOpaque(true);
		deleteOrCancelButton.setBorderPainted(false);
		deleteOrCancelButton.setContentAreaFilled(false);
		deleteOrCancelButton.setOpaque(true);
		deleteOrCancelButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		editOrSaveButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		editOrSaveButton.setIconTextGap(7);
		deleteOrCancelButton.setIconTextGap(7);
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		topPanel.add(backButton, c);
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.gridy = 0;
		c.insets = new Insets(20,5,35,0);
		buttonPanel.add(editOrSaveButton, c);
		c.gridy = 1;
		c.weighty = 1.0;
		c.insets = new Insets(0,5,35,0);
		buttonPanel.add(deleteOrCancelButton, c);
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		add(topPanel, c);
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 2;
		c.weighty = 1.0;
		add(patientForm, c);
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.NORTH;
		JSeparator divider = new JSeparator(SwingConstants.VERTICAL);
		divider.setPreferredSize(new Dimension(1, 1));
		divider.setBackground(Color.LIGHT_GRAY);
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 0.0;
		c.gridx = 1;
		add(divider, c);
		c.gridx = 2;
		add(buttonPanel, c);
	}
	
	public void addListener(CustomListener listener)
	{
		backButton.addActionListener(listener);
		backButton.setActionCommand(ActionConstants.BACK);
		editOrSaveButton.addActionListener(listener);
		deleteOrCancelButton.addActionListener(listener);
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
		switch(mode)
		{
			case Constants.ActionConstants.VIEW: editOrSaveButton.setActionCommand(Constants.ActionConstants.EDIT_RECORD);
			 		   editOrSaveButton.setIcon(editIcon);
			 		   editOrSaveButton.setRolloverIcon(editIconRollover);
			 		   deleteOrCancelButton.setIcon(deleteIcon);
			 		   deleteOrCancelButton.setRolloverIcon(deleteIconRollover);
			 		   patientForm.setEditable(false);
			 		   deleteOrCancelButton.setActionCommand(ActionConstants.DELETE_CURRENT);
			 		   break;
			case Constants.ActionConstants.EDIT: 
			case Constants.ActionConstants.NEW:  
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
					   deleteOrCancelButton.setActionCommand(ActionConstants.CANCEL);
					   deleteOrCancelButton.setVisible(true);
					   patientForm.setEditable(true);
					   editOrSaveButton.setIcon(saveIcon);
					   editOrSaveButton.setRolloverIcon(saveIconRollover);
			   		   editOrSaveButton.setToolTipText("Save");
			 		   deleteOrCancelButton.setIcon(cancelIcon);
			 		   deleteOrCancelButton.setRolloverIcon(cancelIconRollover);
					   break;
		}
	}

	@Override
	public boolean areFieldsValid() 
	{
		return patientForm.areFieldsValid();
	}


	@Override
	public Object getObject() 
	{
		return patientForm.getObject();
	}
	
	public PatientForm getPatientForm()
	{
		return patientForm;
	}
}
