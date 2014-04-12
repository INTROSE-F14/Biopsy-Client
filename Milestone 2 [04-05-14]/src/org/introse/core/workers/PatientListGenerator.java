package org.introse.core.workers;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.SwingWorker;

import org.introse.core.Patient;
import org.introse.gui.panel.ListItem;

public class PatientListGenerator extends SwingWorker<List<ListItem>, ListItem> {

	private List<Patient> patients;
	private boolean deleteEnabled;
	
	public PatientListGenerator(List<Patient> patients, boolean deleteEnabled)
	{
		this.patients = patients;
		this.deleteEnabled = deleteEnabled;
	}
	@Override
	protected List<ListItem> doInBackground() throws Exception 
	{
		List<ListItem> patientList = new Vector<ListItem>();
		Iterator<Patient> i = patients.iterator();
		while(i.hasNext())
		{	
			ListItem listItem = i.next();
			listItem.initializePanel();
			listItem.setDeleteEnabled(deleteEnabled);
			patientList.add(listItem);
		}
		return patientList;
	}
	
	@Override
	protected void done()
	{	
		firePropertyChange("DONE", null, null);
	}

}
