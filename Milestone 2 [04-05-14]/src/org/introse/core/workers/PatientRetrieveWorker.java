package org.introse.core.workers;

import javax.swing.SwingWorker;

import org.introse.core.Patient;
import org.introse.core.dao.PatientDao;

public class PatientRetrieveWorker extends SwingWorker<Object, Void> 
{
	private static final int GET_ALL = 0;
	private static final int SEARCH_BY_MODEL = 1;
	private static final int SEARCH_BY_LETTER = 2;
	private static final int GET_SPECIFIC = 3;
	
	private PatientDao patientDao;
	private int start, range;
	private char letterStart, letterEnd;
	private Patient model;
	private int searchType;
	
	public PatientRetrieveWorker(PatientDao patientDao, char start, char end)
	{
		this.patientDao = patientDao;
		this.letterStart = start;
		this.letterEnd = end;
		searchType = SEARCH_BY_LETTER;
	}
	
	public PatientRetrieveWorker(PatientDao patientDao, Patient model, int start, int range)
	{
		this.patientDao = patientDao;
		this.model = model;
		this.start = start;
		this.range = range;
		if(model != null)
			searchType = SEARCH_BY_MODEL;
		else searchType = GET_ALL;
		if(start == 0 && range == 0)
			searchType = GET_SPECIFIC;
	}
	
	@Override
	protected Object doInBackground() throws Exception 
	{
		switch(searchType)
		{
			case GET_ALL: return patientDao.getAll(start, range);
			case SEARCH_BY_MODEL: return patientDao.search(model, start, range);
			case SEARCH_BY_LETTER: return patientDao.get(letterStart, letterEnd);
			case GET_SPECIFIC: return patientDao.get(model);
		}
		return null;
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}

}
