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
import org.introse.gui.form.RecordForm;

public class RecordPanel extends DetailPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel topPanel, recordForm, buttonPanel;
	private JButton editOrSaveButton, printOrCancelButton, backButton;
	private ImageIcon editIcon, printIcon, saveIcon, editIconRollover,
	printIconRollover, cancelIcon, saveIconRollover, cancelIconRollover;
	
	public RecordPanel(JPanel recordForm, int mode)
	{
		super(new GridBagLayout(), mode);
		setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		this.recordForm = recordForm;
		initializeComponents();
		layoutComponents();
		setMode(mode);
	}
	
	private void initializeComponents()
	{
		buttonPanel = new JPanel(new GridLayout(1,2,1,1));
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		editIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_edit.png"));
		editIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_edit_hover.png"));
		printIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_print.png"));
		printIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_print_hover.png"));
		saveIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_accept.png"));
		saveIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_accept_hover.png"));
		cancelIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_cancel.png"));
		cancelIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_cancel_hover.png"));
		topPanel = new JPanel(new GridBagLayout());
		topPanel.setBackground(new Color(0f,0f,0f,0f));
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
		editOrSaveButton.setFocusable(false);
		printOrCancelButton = new JButton();
		printOrCancelButton.setFocusable(false);
		editOrSaveButton.setBorderPainted(false);
		editOrSaveButton.setContentAreaFilled(false);
		editOrSaveButton.setOpaque(true);
		printOrCancelButton.setBorderPainted(false);
		printOrCancelButton.setContentAreaFilled(false);
		printOrCancelButton.setOpaque(true);
		printOrCancelButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		editOrSaveButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		editOrSaveButton.setIconTextGap(7);
		printOrCancelButton.setIconTextGap(7);
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.0;
		c.weighty = 1.0;
		topPanel.add(backButton, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.insets = new Insets(0,0,0,2);
		c.weightx = 1.0;
		topPanel.add(buttonPanel, c);
		
		buttonPanel.add(editOrSaveButton);
		buttonPanel.add(printOrCancelButton);
		
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
		add(recordForm, c);
	}
	
	public void addListener(CustomListener listener)
	{
		editOrSaveButton.addActionListener(listener);
		printOrCancelButton.addActionListener(listener);
		backButton.addActionListener(listener);
		backButton.setActionCommand(ActionConstants.BACK);
		((RecordForm)recordForm).addListener(listener);
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
		switch(mode)
		{
			case Constants.ActionConstants.VIEW: 
				       editOrSaveButton.setActionCommand(Constants.ActionConstants.EDIT_RECORD);
			 		   printOrCancelButton.setActionCommand(Constants.ActionConstants.PRINT);
			 		   ((RecordForm)recordForm).setRecordEditable(false);
			 		   ((RecordForm)recordForm).setPatientEditable(false);
			 		   ((RecordForm)recordForm).setLoadPatientEnabled(false);
			 		   editOrSaveButton.setIcon(editIcon);
			 		   printOrCancelButton.setIcon(printIcon);
			 		   editOrSaveButton.setRolloverIcon(editIconRollover);
			 		   printOrCancelButton.setRolloverIcon(printIconRollover);
			 		   break;
			case Constants.ActionConstants.EDIT: 
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
					   printOrCancelButton.setActionCommand(Constants.ActionConstants.CANCEL);
					   ((RecordForm)recordForm).setRecordEditable(true);
					   ((RecordForm)recordForm).setPatientEditable(false);
					   ((RecordForm)recordForm).setLoadPatientEnabled(false);
					   editOrSaveButton.setIcon(saveIcon);
					   editOrSaveButton.setRolloverIcon(saveIconRollover);
						printOrCancelButton.setIcon(cancelIcon);
						printOrCancelButton.setRolloverIcon(cancelIconRollover);
					   break;
			case Constants.ActionConstants.NEW:  
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
			   		   printOrCancelButton.setActionCommand(Constants.ActionConstants.CANCEL);
			   		   ((RecordForm)recordForm).setRecordEditable(true);
			   		   ((RecordForm)recordForm).setPatientEditable(true);
			   		   ((RecordForm)recordForm).setLoadPatientEnabled(true);
			   		   editOrSaveButton.setIcon(saveIcon);
			   		   printOrCancelButton.setIcon(cancelIcon);
			   		   editOrSaveButton.setRolloverIcon(saveIconRollover);
			   		   printOrCancelButton.setRolloverIcon(cancelIconRollover);
			   		   break;
		}
	}

	@Override
	public Object getObject() 
	{
		return ((RecordForm)recordForm).getRecord();
	}

	public boolean areFieldsValid()
	{
		if(!((RecordForm)recordForm).areFieldsValid())
			return false;
		return true;
	}
	
	public RecordForm getRecordForm()
	{
		return (RecordForm)recordForm;
	}
}
