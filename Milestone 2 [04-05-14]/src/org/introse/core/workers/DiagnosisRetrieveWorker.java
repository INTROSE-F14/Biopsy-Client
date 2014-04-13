package org.introse.core.workers;

import javax.swing.SwingWorker;

import org.introse.core.Record;
import org.introse.core.dao.DiagnosisDao;

public class DiagnosisRetrieveWorker extends SwingWorker<Object, Void> {

	private DiagnosisDao diagnosisDao;
	private Record record;
	
	public DiagnosisRetrieveWorker(DiagnosisDao diagnosisDao, Record record)
	{
		this.diagnosisDao = diagnosisDao;
		this.record = record;
	}
	@Override
	protected Object doInBackground() throws Exception 
	{
		return diagnosisDao.getDiagnosis(record);
	}

	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}
}
