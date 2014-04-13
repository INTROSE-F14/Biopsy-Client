package org.introse.core.workers;

import javax.swing.SwingWorker;

import org.introse.core.Patient;
import org.introse.core.dao.PatientDao;

public class PatientUpdateWorker extends SwingWorker<Integer, Void> 
{
	public static final int ADD = 0;
	public static final int DELETE = 1;
	public static final int UPDATE = 2;
	private PatientDao patientDao;
	private Patient toBeUpdated;
	private int operation;
	
	public PatientUpdateWorker(PatientDao patientDao, Patient toBeUpdated, int operation)
	{
		this.patientDao = patientDao;
		this.toBeUpdated = toBeUpdated;
		this.operation = operation;
	}
	@Override
	protected Integer doInBackground() throws Exception 
	{
		int returnInt = -1;
		switch(operation)
		{
		case ADD: returnInt = patientDao.add(toBeUpdated);
		break;
		case DELETE: patientDao.delete(toBeUpdated);
		break;
		case UPDATE: patientDao.update(toBeUpdated);
		}
		return returnInt;
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}
}
