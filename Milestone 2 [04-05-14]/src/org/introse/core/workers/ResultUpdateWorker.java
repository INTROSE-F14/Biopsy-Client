package org.introse.core.workers;

import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import org.introse.core.Result;
import org.introse.core.Record;
import org.introse.core.dao.ResultDao;

public class ResultUpdateWorker extends SwingWorker<Void, Void> {

	public static final int ADD = 0;
	public static final int DELETE = 1;
	private ResultDao diagnosisDao;
	private Record record;
	private int operation;
	private List<Result> diagnosisList;
	
	
	public ResultUpdateWorker(ResultDao diagnosisDao, List<Result> diagnosis)
	{
		this.diagnosisDao = diagnosisDao;
		this.diagnosisList = diagnosis;
		operation = ADD;
	}
	
	public ResultUpdateWorker(ResultDao diagnosisDao, Record record)
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
		case ADD: Iterator<Result> i = diagnosisList.iterator();
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
