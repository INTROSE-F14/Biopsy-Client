package org.introse.core.workers;

import java.util.List;
import java.util.Vector;

import javax.swing.SwingWorker;

import org.introse.core.Record;
import org.introse.core.dao.RecordDao;

public class RecordRetrieveWorker extends SwingWorker<Object, Void> 
{	
	public static final int SEARCH = 0;
	public static final int GET = 1;
	private int operation;
	private RecordDao recordDao;
	private int start, range;
	private Record model;
	
	public RecordRetrieveWorker(RecordDao recordDao, Record model, int start, int range)
	{
		this.recordDao = recordDao;
		this.model = model;
		this.start = start;
		this.range = range;
		if(start ==0 && range ==0)
			operation = GET;
		else operation = SEARCH;
	}
	
	@Override
	protected Object doInBackground() throws Exception 
	{
		switch(operation)
		{
			case SEARCH: return recordDao.search(model, start, range);
			case GET: return recordDao.get(model);
		}
		return null;
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}

}
