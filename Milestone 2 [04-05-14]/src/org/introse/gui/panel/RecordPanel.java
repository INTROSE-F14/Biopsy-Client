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
import org.introse.gui.form.RecordForm;

public class RecordPanel extends DetailPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel topPanel, recordForm, buttonPanel;
	private JButton editOrSaveButton, printOrCancelButton, backButton, deleteButton;
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
		buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
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
		editOrSaveButton.setBorderPainted(false);
		editOrSaveButton.setContentAreaFilled(false);
		editOrSaveButton.setOpaque(true);
		editOrSaveButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		editOrSaveButton.setIconTextGap(7);
		
		printOrCancelButton = new JButton();
		printOrCancelButton.setFocusable(false);
		printOrCancelButton.setBorderPainted(false);
		printOrCancelButton.setContentAreaFilled(false);
		printOrCancelButton.setOpaque(true);
		printOrCancelButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		printOrCancelButton.setIconTextGap(7);
		
		deleteButton = new JButton();
		deleteButton.setFocusable(false);
		deleteButton.setBorderPainted(false);
		deleteButton.setContentAreaFilled(false);
		deleteButton.setOpaque(true);
		deleteButton.setBackground(Color.decode(Constants.StyleConstants.PRIMARY_COLOR));
		deleteButton.setIconTextGap(7);
		deleteButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_delete2.png")));
		deleteButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_delete2_hover.png")));
		deleteButton.setDisabledIcon(new ImageIcon(getClass().
				   getResource("/res/icons/ic_action_delete2_placeholder.png")));
		deleteButton.setToolTipText("Delete");
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
		buttonPanel.add(backButton, c);
		c.insets = new Insets(0,5,35,0);
		c.gridy = 1;
		buttonPanel.add(printOrCancelButton, c);
		c.gridy = 2;
		c.insets = new Insets(0,5,35,0);
		buttonPanel.add(editOrSaveButton, c);
		c.gridy = 3;
		c.weighty = 1.0;
		buttonPanel.add(deleteButton, c);
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		add(topPanel, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		c.gridheight = 2;
		c.weighty = 1.0;
		add(recordForm, c);
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
		editOrSaveButton.addActionListener(listener);
		printOrCancelButton.addActionListener(listener);
		backButton.addActionListener(listener);
		backButton.setActionCommand(ActionConstants.BACK);
		deleteButton.addActionListener(listener);
		deleteButton.setActionCommand(ActionConstants.DELETE_CURRENT);
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
			 		  editOrSaveButton.setToolTipText("Edit");
			 		   printOrCancelButton.setToolTipText("Print");
			 		  deleteButton.setEnabled(true);
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
						editOrSaveButton.setToolTipText("Save");
				 	    printOrCancelButton.setToolTipText("Cancel");
				 	   deleteButton.setEnabled(false);
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
			   		   editOrSaveButton.setToolTipText("Save");
			 		   printOrCancelButton.setToolTipText("Cancel");
			 		   deleteButton.setEnabled(false);
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
