package org.introse.core.workers;

import javax.swing.SwingWorker;

import org.introse.core.Record;
import org.introse.core.dao.ResultDao;

public class ResultRetrieveWorker extends SwingWorker<Object, Void> {

	private ResultDao diagnosisDao;
	private Record record;
	
	public ResultRetrieveWorker(ResultDao diagnosisDao, Record record)
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
