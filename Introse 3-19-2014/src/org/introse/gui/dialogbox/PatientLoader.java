package org.introse.gui.dialogbox;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.introse.Constants.TitleConstants;
import org.introse.core.Preferences;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.ListItem;
import org.introse.gui.panel.ListProvider;

public class PatientLoader extends JDialog 
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
		
		filterField = new JTextField(25);
		
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
}
