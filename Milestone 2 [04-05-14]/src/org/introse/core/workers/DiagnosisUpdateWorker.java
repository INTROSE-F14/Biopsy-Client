package org.introse.core.workers;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.SwingWorker;

import org.introse.core.Diagnosis;
import org.introse.core.Record;
import org.introse.core.dao.DiagnosisDao;

public class DiagnosisUpdateWorker extends SwingWorker<Void, Void> {

	public static final int ADD = 0;
	public static final int DELETE = 1;
	private DiagnosisDao diagnosisDao;
	private Record record;
	private int operation;
	private List<Diagnosis> diagnosisList;
	
	
	public DiagnosisUpdateWorker(DiagnosisDao diagnosisDao, List<Diagnosis> diagnosis)
	{
		this.diagnosisDao = diagnosisDao;
		this.diagnosisList = diagnosis;
		operation = ADD;
	}
	
	public DiagnosisUpdateWorker(DiagnosisDao diagnosisDao, Record record)
	{
		this.diagnosisDao = diagnosisDao;
		this.record = record;
		operation = DELETE;
	}

	@Override
	protected Void doInBackground() throws Exception 
	{
		switch(operation)
		{
		case ADD: Iterator<Diagnosis> i = diagnosisList.iterator();
				  while(i.hasNext())
					  diagnosisDao.add(i.next());
		break;
		case DELETE: diagnosisDao.delete(record);
		}
		return null;
	}

	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}
}
