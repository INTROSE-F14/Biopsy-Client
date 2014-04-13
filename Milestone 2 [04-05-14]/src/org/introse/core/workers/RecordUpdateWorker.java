package org.introse.core.workers;

import javax.swing.SwingWorker;

import org.introse.Constants.RecordTable;
import org.introse.core.Record;
import org.introse.core.dao.RecordDao;

public class RecordUpdateWorker extends SwingWorker<Integer, Void> 
{
	public static final int ADD = 0;
	public static final int DELETE = 1;
	public static final int UPDATE = 2;
	private RecordDao recordDao;
	private Record toBeUpdated;
	private int operation;
	
	public RecordUpdateWorker(RecordDao recordDao, Record toBeUpdated, int operation)
	{
		this.recordDao = recordDao;
		this.toBeUpdated = toBeUpdated;
		this.operation = operation;
	}
	@Override
	protected Integer doInBackground() throws Exception 
	{
		switch(operation)
		{
		case ADD: return recordDao.add(toBeUpdated);
		case DELETE: 
			if(toBeUpdated.getAttribute(RecordTable.RECORD_NUMBER) != null)
				recordDao.delete(toBeUpdated);
			else recordDao.delete((int)(toBeUpdated.getAttribute(RecordTable.PATIENT_ID)));
		break;
		case UPDATE: recordDao.update(toBeUpdated);
		}
		return null;
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}
}
