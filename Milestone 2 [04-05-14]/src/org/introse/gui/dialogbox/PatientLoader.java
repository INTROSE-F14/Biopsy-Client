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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.introse.Constants.TitleConstants;
import org.introse.core.Preferences;
import org.introse.core.workers.FilterWorker;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.ListItem;
import org.introse.gui.panel.ListPanel;

public class PatientLoader extends JDialog implements KeyListener
{
	private ListPanel patientList;
	private List<ListItem> patients; 
	private JTextField filterField;
	private JPanel container;
	private JPanel listPanel;
	
	public PatientLoader(List<ListItem> patients, CustomListener listener)
	{
		super(null, TitleConstants.LOAD_PATIENT, ModalityType.APPLICATION_MODAL);
		this.patients = patients;
		patientList = new ListPanel(SwingConstants.VERTICAL);
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
		listPanel.add(patientList, c);
		
		container.add(listPanel, BorderLayout.CENTER);
		setContentPane(container);
	}
	
	public void showGUI()
	{
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	public void filterList(String filter, List<ListItem> currentList)
	{
		patientList.showPanel(TitleConstants.REFRESH_PANEL);
		final FilterWorker filterWorker = new FilterWorker(filter, 
				currentList);
		filterWorker.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("DONE"))
				{
					try 
					{
						List<ListItem> list = filterWorker.get();
						patientList.updateList(list);
						if(list.size() > 0)
							patientList.showPanel(TitleConstants.LIST_PANEL);
						else patientList.showPanel(TitleConstants.EMPTY_PANEL);
						
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		});
		filterWorker.execute();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			filterList(filterField.getText(), patients);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
