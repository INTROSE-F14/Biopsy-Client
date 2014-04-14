package org.introse.core.workers;

import javax.swing.SwingWorker;

import org.introse.core.dao.DictionaryDao;

public class DictionaryRetrieveWorker extends SwingWorker<Object, Void> {

	public static final int GET_WORDS = 0;
	public static final int GET_COUNT = 1;
	private DictionaryDao dictionaryDao;
	private int start, range, type, operation;
	
	public DictionaryRetrieveWorker(DictionaryDao dictionaryDao)
	{
		this.dictionaryDao = dictionaryDao;
		this.operation = GET_COUNT;
		type = -1;
	}
	
	public DictionaryRetrieveWorker(DictionaryDao dictionaryDao, int type)
	{
		this.dictionaryDao = dictionaryDao;
		this.type = type;
		this.operation = GET_COUNT;
	}
	
	public DictionaryRetrieveWorker(DictionaryDao dictionaryDao, int start, int range, int type)
	{
		this.dictionaryDao = dictionaryDao;
		this.start = start;
		this.range = range;
		this.type = type;
		this.operation = GET_WORDS;
	}
	@Override
	protected Object doInBackground() throws Exception 
	{
		switch(operation)
		{
		case GET_WORDS: return dictionaryDao.getWords(type, start, range);
		case GET_COUNT: if(type != -1)
							return dictionaryDao.getCount(type);
						else return dictionaryDao.getCount();
		}
		return null;
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}

}
