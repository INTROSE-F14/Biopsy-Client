package org.introse.core.workers;

import javax.swing.SwingWorker;

import org.introse.core.dao.DictionaryDao;

public class DictionaryRetrieveWorker extends SwingWorker<Object, Void> {

	private DictionaryDao dictionaryDao;
	private int start, range, type;
	
	public DictionaryRetrieveWorker(DictionaryDao dictionaryDao, int start, int range, int type)
	{
		this.dictionaryDao = dictionaryDao;
		this.start = start;
		this.range = range;
		this.type = type;
	}
	@Override
	protected Object doInBackground() throws Exception 
	{
		return dictionaryDao.getWords(type, start, range);
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}

}
