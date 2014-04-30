package org.introse.core.workers;

import javax.swing.SwingWorker;

import org.introse.core.Record;
import org.introse.core.dao.RecordDao;

public class RecordRetrieveWorker extends SwingWorker<Object, Void> 
{	
	public static final int SEARCH = 0;
	public static final int GET = 1;
	public static final int GET_COUNT = 2;
	public static final int GET_ALL = 3;
	private int operation;
	private RecordDao recordDao;
	private int start, range;
	private Record model;
	
	public RecordRetrieveWorker(RecordDao recordDao)
	{
		this.recordDao = recordDao;
		operation = GET_ALL;
	}
	
	public RecordRetrieveWorker(RecordDao recordDao, Record model, int operation)
	{
		this.recordDao = recordDao;
		this.model = model;
		this.operation = operation;
	}
	public RecordRetrieveWorker(RecordDao recordDao, Record model, int start, int range, int operation)
	{
		this.recordDao = recordDao;
		this.model = model;
		this.start = start;
		this.range = range;
		this.operation = operation;
	}
	
	@Override
	protected Object doInBackground() throws Exception 
	{
		switch(operation)
		{
			case SEARCH: return recordDao.search(model, start, range);
			case GET: return recordDao.get(model);
			case GET_COUNT: return recordDao.getCount(model);
			case GET_ALL: return recordDao.getAll();
		}
		return null;
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}

}
