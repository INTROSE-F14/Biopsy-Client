package org.introse.core.workers;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.SwingWorker;

import org.introse.Constants.ResultCategoriesConstants;
import org.introse.core.Record;
import org.introse.core.Result;
import org.introse.core.ResultCriteria;
import org.introse.core.dao.ResultDao;

public class ResultComparator extends SwingWorker<List<Record>, Void> {

	private List<Record> toBeChecked;
	private ResultCriteria criteria;
	private ResultDao resultDao;
	
	public ResultComparator(ResultDao resultDao, ResultCriteria criteria, List<Record> toBeChecked)
	{
		this.toBeChecked = toBeChecked;
		this.criteria = criteria;
		this.resultDao = resultDao;
	}
	
	@Override
	protected List<Record> doInBackground() throws Exception 
	{
		if(criteria.isEmpty())
			return toBeChecked;
		List<Record> matches = new Vector<Record>();
		Iterator<Record> i = toBeChecked.iterator();
		while(i.hasNext())
		{
			Record curRecord = i.next();
			int matchRate = evaluateMatchRate(curRecord);
			if(matchRate > 0)
			{
				curRecord.putAttribute("MATCH SIZE", matchRate);
				matches.add(curRecord);
			}
		}
		return quickSort(matches, 0, matches.size());
	}
	
	private List<Record> quickSort(List<Record> records, int start, int size)
	{
		if(size - start <= 1)
			return records;
		
		int left = start;
		int right = size - 1;
		int pivot = (int)records.get(new Random().nextInt(size)).getAttribute("MATCH SIZE");
		while(left < right)
		{
			while((int)records.get(left).getAttribute("MATCH SIZE") > pivot)
				left++;
			while((int)records.get(right).getAttribute("MATCH SIZE") < pivot)
				right--;
			Record a = records.get(left);
			Record b = records.get(right);
			records.set(left, b);
			records.set(right, a);
			left++;
		}
		quickSort(records, 0, left);
		quickSort(records, left + 1, size);
		return records;
	}
	
	private int evaluateMatchRate(Record record)
	{
		int diagnosisMatch = 0;
		int remarksMatch = 0;
		int grossDescMatch = 0;
		int microNotesMatch = 0;
		
		String diagnosis = criteria.getDiagnosis();
		String remarks = criteria.getRemarks();
		String microNotes = criteria.getMicroNotes();
		String grossDesc = criteria.getGrossDesc();
		
		String[] splitDiagnosis = null; 
		String[] splitRemarks = null;
		String[] splitMicroNotes = null;
		String[] splitGrossDesc = null;
		
		if(diagnosis != null)
			splitDiagnosis = diagnosis.split("\\s"); 
		if(remarks != null)
			splitRemarks = remarks.split("\\s");
		if(microNotes != null)
			splitMicroNotes = microNotes.split("\\s");
		if(grossDesc != null)
			splitGrossDesc = grossDesc.split("\\s");
		
		List<Result> results = resultDao.getDiagnosis(record);
		Iterator<Result> j = results.iterator();
		while(j.hasNext())
		{
			Result curResult = j.next();
			int category = curResult.getCategory();
			String value = curResult.getValue().toLowerCase();
			
			switch(category)
			{
			case ResultCategoriesConstants.DIAGNOSIS:
				if(diagnosis != null)
				{
					if(value.contains(diagnosis))
						diagnosisMatch++;
					int diagnosisSize = splitDiagnosis.length;
					for(int x = 0; x < diagnosisSize; x++)
					{
						if(value.contains(splitDiagnosis[x]))
							diagnosisMatch++;
					}
				}
				break;
			case ResultCategoriesConstants.REMARKS:
				if(remarks != null)
				{
					if(value.contains(remarks))
						remarksMatch++;
					int remarksSize = splitRemarks.length;
					for(int x = 0; x < remarksSize; x++)
					{
						if(value.contains(splitRemarks[x]))
							remarksMatch++;
					}
				}
				break;
			case ResultCategoriesConstants.GROSS_DESCRIPTION:
				if(grossDesc != null)
				{
					if(value.contains(grossDesc))
						grossDescMatch++;
					int grossDescSize = splitGrossDesc.length;
					for(int x = 0; x < grossDescSize; x++)
					{
						if(value.contains(splitGrossDesc[x]))
							grossDescMatch++;
					}
				}
				break;
			case ResultCategoriesConstants.MICROSCOPIC_NOTES:
				if(microNotes != null)
				{
					if(value.contains(microNotes))
						microNotesMatch++;
					int microNotesSize = splitMicroNotes.length;
					for(int x = 0; x < microNotesSize; x++)
					{
						if(value.contains(splitMicroNotes[x]))
							microNotesMatch++;
					}
				}
			}
		}
		return  microNotesMatch + 
				grossDescMatch + remarksMatch + diagnosisMatch;
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}

}
