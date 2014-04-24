package org.introse.gui.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

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
	private JButton editOrSaveButton, cancelButton, backButton;
	private ImageIcon editIcon, saveIcon, cancelIcon, editIconRollover, saveIconRollover, cancelIconRollover;
	
	
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
		buttonPanel = new JPanel(new GridLayout(1,2,1,1));
		buttonPanel.setBackground(Color.LIGHT_GRAY);
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
		backButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
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
		cancelButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		editOrSaveButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		editOrSaveButton.setIconTextGap(7);
		cancelButton.setIconTextGap(7);
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		topPanel.add(backButton, c);
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 3;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,0,2);
		topPanel.add(buttonPanel, c);
		buttonPanel.add(editOrSaveButton);
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHEAST;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		add(topPanel, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 5;
		c.gridheight = 2;
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
			 		   patientForm.setEditable(false);
			 		   buttonPanel.remove(cancelButton);
			 		   buttonPanel.revalidate();
			 		   repaint();
			 		   break;
			case Constants.ActionConstants.EDIT: 
			case Constants.ActionConstants.NEW:  
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
					   cancelButton.setVisible(true);
					   patientForm.setEditable(true);
					   editOrSaveButton.setIcon(saveIcon);
					   editOrSaveButton.setRolloverIcon(saveIconRollover);
					   buttonPanel.add(cancelButton);
					   buttonPanel.revalidate();
					   repaint();
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
