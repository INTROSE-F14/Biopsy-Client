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
import org.introse.gui.form.RecordForm;

public class RecordPanel extends DetailPanel
{
	private JPanel topPanel;
	private JPanel recordForm;
	private JButton editOrSaveButton;
	private JButton printOrCancelButton;
	private JButton backButton;
	
	public RecordPanel(JPanel recordForm, int mode)
	{
		super(new GridBagLayout(), mode);
		setBackground(Color.white);
		this.recordForm = recordForm;
		initializeComponents();
		layoutComponents();
		setMode(mode);
	}
	
	private void initializeComponents()
	{
		topPanel = new JPanel(new GridBagLayout());
		topPanel.setBackground(Color.white);
		backButton = new JButton();
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setOpaque(true);
		backButton.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		backButton.setIconTextGap(7);
		backButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/back.png")));
		backButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/back_rollover.png")));
		
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
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.0;
		c.weighty = 1.0;
		topPanel.add(backButton, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.insets = new Insets(0,0,0,2);
		c.weightx = 1.0;
		topPanel.add(editOrSaveButton, c);
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.0;
		c.insets = new Insets(0,0,0,0);
		topPanel.add(printOrCancelButton, c);
		
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
		editOrSaveButton.addMouseListener(listener);
		printOrCancelButton.addMouseListener(listener);
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
			 		   editOrSaveButton.setText("Edit");
			 		   printOrCancelButton.setText("Print");
			 		   ((RecordForm)recordForm).setRecordEditable(false);
			 		   ((RecordForm)recordForm).setPatientEditable(false);
			 		   ((RecordForm)recordForm).setLoadPatientEnabled(false);
			 		   editOrSaveButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/edit.png")));
			 		   printOrCancelButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/print.png")));
			 		   break;
			case Constants.ActionConstants.EDIT: 
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
					   printOrCancelButton.setActionCommand(Constants.ActionConstants.CANCEL);
					   editOrSaveButton.setText("Save");
					   printOrCancelButton.setText("Cancel");
					   ((RecordForm)recordForm).setRecordEditable(true);
					   ((RecordForm)recordForm).setPatientEditable(false);
					   ((RecordForm)recordForm).setLoadPatientEnabled(false);
					   editOrSaveButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/save.png")));
						printOrCancelButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/cancel.png")));
					   break;
			case Constants.ActionConstants.NEW:  
					   editOrSaveButton.setActionCommand(Constants.ActionConstants.SAVE);
			   		   printOrCancelButton.setActionCommand(Constants.ActionConstants.CANCEL);
			   		   editOrSaveButton.setText("Save");
			   		   printOrCancelButton.setText("Cancel");
			   		   ((RecordForm)recordForm).setRecordEditable(true);
			   		   ((RecordForm)recordForm).setPatientEditable(true);
			   		   ((RecordForm)recordForm).setLoadPatientEnabled(true);
			   		   editOrSaveButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/save.png")));
			   		   printOrCancelButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/cancel.png")));
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
