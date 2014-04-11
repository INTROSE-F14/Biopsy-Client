package org.introse.core.workers;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.SwingWorker;

import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordTable;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.core.dao.PatientDao;
import org.introse.gui.panel.ListItem;

public class RecordListGenerator extends SwingWorker<List<ListItem>, ListItem> {

	private List<Record> records;
	private PatientDao patientDao;
	
	public RecordListGenerator(List<Record> records, PatientDao patientDao)
	{
		this.records = records;
		this.patientDao = patientDao;
	}
	
	@Override
	protected List<ListItem> doInBackground() throws Exception 
	{

		List<ListItem> recordList = new Vector<ListItem>();
		int size = records.size();
		int completed = 0;
		Iterator<Record> i = records.iterator();
		while(i.hasNext())
		{
			ListItem listItem = i.next();
			Record record = (Record)listItem;
			int patientID = (int)record.getAttribute(RecordTable.PATIENT_ID);
			Patient p = new Patient();
			p.putAttribute(PatientTable.PATIENT_ID, patientID);
			record.putAttribute(RecordTable.PATIENT, patientDao.get(p));
			listItem.initializePanel();
			recordList.add(listItem);
			completed++;
			publish(listItem);
			setProgress(completed * 100 / size);
		}
		return recordList;
	}
	
	@Override 
	protected void process(List<ListItem> items)
	{
	}
	
	@Override
	protected void done()
	{
		
		firePropertyChange("DONE", null, null);
	}
}
