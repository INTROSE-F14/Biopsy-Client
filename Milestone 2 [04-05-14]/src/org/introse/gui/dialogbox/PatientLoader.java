package org.introse.gui.dialogbox;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.introse.Constants.ActionConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.Patient;
import org.introse.core.Preferences;
import org.introse.core.dao.PatientDao;
import org.introse.core.workers.PatientListGenerator;
import org.introse.gui.event.ListListener;
import org.introse.gui.panel.ListItem;
import org.introse.gui.panel.ListPanel;

public class PatientLoader extends JDialog implements ActionListener
{
	private ListPanel listPanel;
	private List<ListItem> patients; 
	private JPanel pane;
	private PatientDao patientDao;
	
	public PatientLoader(PatientDao patientDao, ListListener listener)
	{
		super(null, TitleConstants.LOAD_PATIENT, ModalityType.APPLICATION_MODAL);
		this.patientDao = patientDao;
		
		listPanel = new ListPanel(SwingConstants.VERTICAL, 10, 0);
		listPanel.addButtonListener(this);
		listPanel.addMouseListener(listener);
		updatePList();
		pane = new JPanel(new GridBagLayout());
		pane.setBackground(Color.white);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(10,10,10,10);
		pane.add(listPanel, c);
		setContentPane(pane);
		setPreferredSize(new Dimension(470, 
				(int)(Preferences.getScreenHeight() * 0.8)));
	}
	
	public void showGUI()
	{
		pack();
		setVisible(true);
	}
	
	public void updatePList()
	{
		listPanel.setListSize(patientDao.getCount());
		List<Patient> patientList = patientDao.getAll(listPanel.getStart(), listPanel.getRange());
		listPanel.showPanel(TitleConstants.REFRESH_PANEL);
		

		final PatientListGenerator listWorker = new PatientListGenerator(patientList);
		listWorker.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt) 
			{
				if(evt.getPropertyName().equals("DONE"))
				{
					try 
					{
						patients = listWorker.get();
						listPanel.updateViewable(patients);
						if(patients.size() < 1)
							listPanel.showPanel(TitleConstants.EMPTY_PANEL);
						else listPanel.showPanel(TitleConstants.LIST_PANEL);
					} catch (InterruptedException | ExecutionException e) 
					{e.printStackTrace();}
				}
			}
		});
		listWorker.execute();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals(ActionConstants.NEXT))
			listPanel.next();
		else listPanel.previous();
		updatePList();
	}
}
