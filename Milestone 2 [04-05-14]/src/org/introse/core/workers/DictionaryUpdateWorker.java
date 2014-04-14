package org.introse.core.workers;

import javax.swing.SwingWorker;

import org.introse.core.dao.DictionaryDao;

public class DictionaryUpdateWorker extends SwingWorker<Object, Void> {

	public static final int ADD = 0;
	public static final int DELETE = 1;
	public static final int CHECK = 2;
	private DictionaryDao dictionaryDao;
	private String word;
	private int operation, type;
	
	public DictionaryUpdateWorker(DictionaryDao dictionaryDao, String word, int type,
			int operation)
	{
		this.dictionaryDao =  dictionaryDao;
		this.word = word;
		this.type = type;
		this.operation = operation;
	}
	
	@Override
	protected Object doInBackground() throws Exception 
	{
		switch(operation)
		{
		case ADD: dictionaryDao.add(word, type);
		break;
		case DELETE: dictionaryDao.delete(word, type);
		break;
		case CHECK: return dictionaryDao.isUnique(word, type);
		}
		return null;
	}

	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}
}
