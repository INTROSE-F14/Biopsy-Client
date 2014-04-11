package org.introse.gui.dialogbox;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.introse.Constants.TitleConstants;
import org.introse.core.Preferences;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.ListItem;
import org.introse.gui.panel.ListProvider;

public class PatientLoader extends JDialog implements KeyListener
{
	private ListProvider patientList;
	private List<ListItem> patients; 
	private JTextField filterField;
	private JPanel container;
	private JPanel listPanel;
	
	public PatientLoader(List<ListItem> patients, CustomListener listener)
	{
		super(null, TitleConstants.LOAD_PATIENT, ModalityType.APPLICATION_MODAL);
		this.patients = patients;
		patientList = new ListProvider(SwingConstants.VERTICAL);
		patientList.addListener(listener);
		patientList.updateList(patients);
		
		container = new JPanel();
		container.setBackground(Color.white);
		
		listPanel = new JPanel(new GridBagLayout());
		listPanel.setBackground(Color.white);
		listPanel.requestFocusInWindow();
		filterField = new JTextField(TitleConstants.QUICK_FILTER, 25);
		filterField.setForeground(Color.gray);
		filterField.addKeyListener(this);
		filterField.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e) 
			{
				if(((JTextField)e.getComponent()).getText().equals(TitleConstants.QUICK_FILTER))
					((JTextField)e.getComponent()).setText("");
				((JTextField)e.getComponent()).setForeground(Color.black);
			}

			@Override
			public void focusLost(FocusEvent e) 
			{
				if(((JTextField)e.getComponent()).getText().length() < 1)
				{
					((JTextField)e.getComponent()).setText("Quick filter");
					((JTextField)e.getComponent()).setForeground(Color.GRAY);
				}
			}
			
		});
		patientList.getScroller().setPreferredSize(new Dimension(patientList.getScroller().getPreferredSize().width + 10,
				(int)(Preferences.getScreenHeight() * 0.7)));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.gridheight = 1;
		c.gridwidth = 2;
		c.insets = new Insets(10,5,5,5);
		listPanel.add(filterField, c);
		c.gridy = 1;
		c.gridheight = 3;
		c.insets = new Insets(0,5,5,5);
		listPanel.add(patientList.getPanel(), c);
		
		container.add(listPanel, BorderLayout.CENTER);
		setContentPane(container);
	}
	
	public void showGUI()
	{
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	public List<ListItem> filterList(String filter, List<ListItem> currentList)
	{
		if(filter.length() < 1 || filter.equals(TitleConstants.QUICK_FILTER))
			return currentList;
		List<ListItem> filteredList = new Vector<ListItem>();
		Iterator<ListItem> i = currentList.iterator();
		while(i.hasNext())
		{
			ListItem listItem = i.next();
			for(int j = 0; j < listItem.getLabelCount(); j++)
				if(listItem.getLabel(j).toLowerCase().contains(filter.toLowerCase()))
				{
					filteredList.add(listItem);
					j = listItem.getLabelCount();
				}
		}
		return filteredList;
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		patientList.updateList(filterList(filterField.getText(), patients));	
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
			
	}
}
