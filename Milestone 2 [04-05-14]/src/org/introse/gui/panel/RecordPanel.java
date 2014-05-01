package org.introse.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.StyleConstants;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.CytologyForm;
import org.introse.gui.form.GynecologyForm;
import org.introse.gui.form.HistopathologyForm;
import org.introse.gui.form.RecordForm;

public class RecordPanel extends DetailPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RecordForm recordForm;
	private JPanel sidePanel, buttonPanel;
	private JButton editOrSaveButton, printOrCancelButton, backButton, deleteButton;
	private ImageIcon editIcon, printIcon, saveIcon, editIconRollover,
	printIconRollover, cancelIcon, saveIconRollover, cancelIconRollover;
	private JButton nextButton, previousButton;
	private int page, pageMax;
	private Action changePage;
	private JLabel pageLabel;
	
	public RecordPanel(RecordForm recordForm, int mode)
	{
		super(new GridBagLayout(), mode);
		setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		this.recordForm = recordForm;
		changePage = new ChangePage();
		if(recordForm instanceof HistopathologyForm)
			pageMax = RecordForm.HISTOPATHOLOGY_PAGES;
		else if(recordForm instanceof CytologyForm)
			pageMax = RecordForm.CYTOLOGY_PAGES;
		else if(recordForm instanceof GynecologyForm)
			pageMax = RecordForm.GYNECOLOGY_PAGES;
		page = 1;
		initializeComponents();
		layoutComponents();
		setMode(mode);
		
	}
	
	private void initializeComponents()
	{
		buttonPanel = new JPanel(new GridLayout(1,3,0,0));
		buttonPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		sidePanel = new JPanel(new GridBagLayout());
		sidePanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		editIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_edit.png"));
		editIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_edit_hover.png"));
		printIcon = new ImageIcon(getClass().getResource("/res/icons/ic_action_print.png"));
		printIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_action_print_hover.png"));
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
		
		pageLabel = new JLabel("1 of " + pageMax);
		pageLabel.setOpaque(true);
		pageLabel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		nextButton = new JButton();
		nextButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_next.png")));
		nextButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_next_hover.png")));
		nextButton.setContentAreaFilled(false);
		nextButton.setBorderPainted(false);
		nextButton.setOpaque(true);
		nextButton.setBackground(Color.white);
		nextButton.setVisible(true);
		previousButton = new JButton();
		previousButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_previous.png")));
		previousButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_previous_hover.png")));
		previousButton.setContentAreaFilled(false);
		previousButton.setBorderPainted(false);
		previousButton.setOpaque(true);
		previousButton.setBackground(Color.white);
		previousButton.setVisible(false);
		nextButton.setFocusable(false);
		previousButton.setFocusable(false);
		
		previousButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.VK_ALT, true), "previous page");
		previousButton.getActionMap().put("previous page", changePage);
		nextButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.VK_ALT, true), "next page");
		nextButton.getActionMap().put("next page", changePage);
		nextButton.addActionListener(changePage);
		previousButton.addActionListener(changePage);
		
		printOrCancelButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.VK_ALT, true), "print");
		printOrCancelButton.getActionMap().put("print", new Print());
		printOrCancelButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.VK_ALT, true), "cancel");
		printOrCancelButton.getActionMap().put("cancel", new Cancel());
		
		editOrSaveButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.VK_ALT, true), "save");
		editOrSaveButton.getActionMap().put("save", new Save());
		editOrSaveButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.VK_ALT, true), "edit");
		editOrSaveButton.getActionMap().put("edit", new Edit());
		
		deleteButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.VK_ALT, true), "delete");
		deleteButton.getActionMap().put("delete", new Delete());
		backButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, KeyEvent.VK_ALT, true), "back");
		backButton.getActionMap().put("back", new Back());
		
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.gridy = 0;
		c.insets = new Insets(20,5,35,0);
		sidePanel.add(backButton, c);
		c.insets = new Insets(0,5,35,0);
		c.gridy = 1;
		sidePanel.add(printOrCancelButton, c);
		c.gridy = 2;
		c.insets = new Insets(0,5,35,0);
		sidePanel.add(editOrSaveButton, c);
		c.gridy = 3;
		c.weighty = 1.0;
		sidePanel.add(deleteButton, c);
		
		buttonPanel.add(previousButton);
		buttonPanel.add(pageLabel);
		buttonPanel.add(nextButton);
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.insets = new Insets(30,0,0,20);
		add(recordForm, c);
		c.gridy = 1;
		c.weighty = 0.0;
		c.insets = new Insets(0,0,0,20);
		add(buttonPanel, c);
		c.gridheight = 2;
		c.insets = new Insets(0,0,0,0);
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.NORTH;
		JSeparator divider = new JSeparator(SwingConstants.VERTICAL);
		divider.setPreferredSize(new Dimension(1, 1));
		divider.setBackground(Color.LIGHT_GRAY);
		c.gridy = 0;
		c.weightx = 0.0;
		c.gridx = 1;
		add(divider, c);
		c.gridx = 2;
		add(sidePanel, c);
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
	
	private class ChangePage implements Action
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if(source.equals(nextButton))
			{
				if(page < pageMax)
				{
					page++;
					recordForm.nextPage();
				}
			}
			else
			{
				if(page > 1)
				{
					page--;
					recordForm.previousPage();
				}
			}
			
			if(page > 1)
				previousButton.setVisible(true);
			else previousButton.setVisible(false);
			
			if(page < pageMax)
				nextButton.setVisible(true);
			else nextButton.setVisible(false);
			
			pageLabel.setText(page + " of " + pageMax);
		}
		
		@Override
		public Object getValue(String key) {
			return null;
		}

		@Override
		public void putValue(String key, Object value) {}

		@Override
		public void setEnabled(boolean b) {}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public void addPropertyChangeListener(
				PropertyChangeListener listener) {}

		@Override
		public void removePropertyChangeListener(
				PropertyChangeListener listener) {}
	}

	private class Edit implements Action
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			editOrSaveButton.doClick();
		}
		
		@Override
		public Object getValue(String key) {
			return null;
		}

		@Override
		public void putValue(String key, Object value) {}

		@Override
		public void setEnabled(boolean b) {}

		@Override
		public boolean isEnabled() {
			if(editOrSaveButton.getActionCommand().equals(ActionConstants.EDIT_RECORD))
				return true;
			return false;
		}

		@Override
		public void addPropertyChangeListener(
				PropertyChangeListener listener) {}

		@Override
		public void removePropertyChangeListener(
				PropertyChangeListener listener) {}
	}
	
	private class Save implements Action
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			editOrSaveButton.doClick();
		}
		
		@Override
		public Object getValue(String key) {
			return null;
		}

		@Override
		public void putValue(String key, Object value) {}

		@Override
		public void setEnabled(boolean b) {}

		@Override
		public boolean isEnabled() {
			if(editOrSaveButton.getActionCommand().equals(ActionConstants.SAVE))
				return true;
			return false;
		}

		@Override
		public void addPropertyChangeListener(
				PropertyChangeListener listener) {}

		@Override
		public void removePropertyChangeListener(
				PropertyChangeListener listener) {}
	}
	
	private class Delete implements Action
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			deleteButton.doClick();
		}
		
		@Override
		public Object getValue(String key) {
			return null;
		}

		@Override
		public void putValue(String key, Object value) {}

		@Override
		public void setEnabled(boolean b) {}

		@Override
		public boolean isEnabled() {
			return deleteButton.isEnabled();
		}

		@Override
		public void addPropertyChangeListener(
				PropertyChangeListener listener) {}

		@Override
		public void removePropertyChangeListener(
				PropertyChangeListener listener) {}
	}
	
	private class Back implements Action
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			backButton.doClick();
		}
		
		@Override
		public Object getValue(String key) {
			return null;
		}

		@Override
		public void putValue(String key, Object value) {}

		@Override
		public void setEnabled(boolean b) {}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public void addPropertyChangeListener(
				PropertyChangeListener listener) {}

		@Override
		public void removePropertyChangeListener(
				PropertyChangeListener listener) {}
	}
	
	private class Print implements Action
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			printOrCancelButton.doClick();
		}
		
		@Override
		public Object getValue(String key) {
			return null;
		}

		@Override
		public void putValue(String key, Object value) {}

		@Override
		public void setEnabled(boolean b) {}

		@Override
		public boolean isEnabled() {
			if(printOrCancelButton.getActionCommand().equals(ActionConstants.PRINT))
				return true;
			return false;
		}

		@Override
		public void addPropertyChangeListener(
				PropertyChangeListener listener) {}

		@Override
		public void removePropertyChangeListener(
				PropertyChangeListener listener) {}
	}
	
	private class Cancel implements Action
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			printOrCancelButton.doClick();
		}
		
		@Override
		public Object getValue(String key) {
			return null;
		}

		@Override
		public void putValue(String key, Object value) {}

		@Override
		public void setEnabled(boolean b) {}

		@Override
		public boolean isEnabled() {
			if(printOrCancelButton.getActionCommand().equals(ActionConstants.CANCEL))
				return true;
			return false;
		}

		@Override
		public void addPropertyChangeListener(
				PropertyChangeListener listener) {}

		@Override
		public void removePropertyChangeListener(
				PropertyChangeListener listener) {}
	}
	
}
