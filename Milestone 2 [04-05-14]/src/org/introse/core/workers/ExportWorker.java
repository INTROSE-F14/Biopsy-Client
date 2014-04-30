package org.introse.core.workers;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordTable;
import org.introse.Constants.ResultCategoriesConstants;
import org.introse.core.CustomCalendar;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.core.Result;
import org.introse.core.dao.PatientDao;
import org.introse.core.dao.RecordDao;
import org.introse.core.dao.ResultDao;
import org.introse.gui.panel.ExportPanel;

public class ExportWorker extends SwingWorker<Void, String> 
{
	private File exportFile;
	private ExportPanel exportPanel;
	private int recordCount;
	private RecordDao recordDao;
	private PatientDao patientDao;
	private ResultDao resultDao;
	
	public ExportWorker(File exportFile, ExportPanel exportPanel, RecordDao recordDao, PatientDao patientDao, ResultDao resultDao)
	{
		this.exportFile = exportFile;
		this.exportPanel = exportPanel;
		this.recordDao = recordDao;
		this.patientDao = patientDao;
		this.resultDao = resultDao;
	}
	
	@Override
	protected Void doInBackground() throws Exception 
	{
		setProgress(0);
		publish("Exporting data:Please wait");
		export();
		setProgress(100);
		return null;
	}
	
	private void export() throws Exception
	{
		List<Record> records = recordDao.getAll();
		PrintWriter writer = null;
		int total = records.size();
		try
		{
			writer = new PrintWriter(new FileWriter(exportFile, false));
			writer.println("Last Name, First Name, Middle Name, Birthday, " +
					"Internal Reference Number,Physician,Pathologist,Specimen Type, Specimen," +
					"Patient's Room,Patient's Age,Date Received,Date Completed," +
					"Specimen Adequacy,Hormonal Evaluation,Remarks,Gross Description,Microscopic Notes,Diagnosis");
			Iterator<Record> i = records.iterator();
			while(i.hasNext())
			{
				Record curRecord = i.next();
				Patient p = new Patient();
				p.putAttribute(PatientTable.PATIENT_ID, curRecord.getAttribute(RecordTable.PATIENT_ID));
				p = patientDao.get(p);
				String lastName = (String)p.getAttribute(PatientTable.LAST_NAME);
				String firstName = (String)p.getAttribute(PatientTable.FIRST_NAME);
				String middleName = (String)p.getAttribute(PatientTable.MIDDLE_NAME);
				String birthday = "n/a";
				CustomCalendar bDay = (CustomCalendar)p.getAttribute(PatientTable.BIRTHDAY);
				if(bDay != null)
					birthday = bDay.toNumericFormat();
				String irn = ""+curRecord.getAttribute(RecordTable.RECORD_TYPE)+ 
						curRecord.getAttribute(RecordTable.RECORD_YEAR) + "-" + 
						curRecord.getAttribute(RecordTable.RECORD_NUMBER);
				String physician = (String)curRecord.getAttribute(RecordTable.PHYSICIAN);
				String pathologist = (String)curRecord.getAttribute(RecordTable.PATHOLOGIST);
				String specimenType = (String)curRecord.getAttribute(RecordTable.SPEC_TYPE);
				String specimen = (String)curRecord.getAttribute(RecordTable.SPECIMEN);
				String room = "n/a";
				String rm = (String)curRecord.getAttribute(RecordTable.ROOM);
				if(rm != null)
					room = rm;
				CustomCalendar dR = (CustomCalendar)curRecord.getAttribute(RecordTable.DATE_RECEIVED);
				CustomCalendar dC = (CustomCalendar)curRecord.getAttribute(RecordTable.DATE_COMPLETED);
				String dateReceived = dR.toNumericFormat();
				String dateCompleted = dC.toNumericFormat();
				String age = "n/a";
				if(bDay != null)
					age = ""+bDay.getYearDifference(dR);
				
				String remarks = "";
				String microscopicNotes = "";
				String grossDesc = "";
				String diagnosis = "";
				String specimenAdequacy = "n/a";
				String superficials = "";
				String intermediates = "";
				String parabasals = "";
				String hormonalEvaluation = "";
				List<Result> results = resultDao.getDiagnosis(curRecord);
				if(results != null)
				{
					Iterator<Result> j = results.iterator();
					while(j.hasNext())
					{
						Result curResult = j.next();
						int category = curResult.getCategory();
						String value = curResult.getValue();
						System.out.println(category + "-" + value);
						switch(category)
						{
						case ResultCategoriesConstants.REMARKS: remarks = value;
						break;
						case ResultCategoriesConstants.MICROSCOPIC_NOTES: microscopicNotes = value;
						break;
						case ResultCategoriesConstants.GROSS_DESCRIPTION: grossDesc = value;
						break;
						case ResultCategoriesConstants.SPECIMEN_ADEQUACY: specimenAdequacy = value;
						break;
						case ResultCategoriesConstants.SUPERFICIALS: superficials = "Superficials " + value;
						break;
						case ResultCategoriesConstants.INTERMEDIATES: intermediates = "Intermediates " + value;
						break;
						case ResultCategoriesConstants.PARABASALS: parabasals = "Parabasals "+ value;
						break;
						default: diagnosis = value;
						}
					}
					hormonalEvaluation = superficials + " " + intermediates + " " + parabasals;
					if(hormonalEvaluation.replaceAll("\\s", "").length() < 1)
						hormonalEvaluation = "n/a";
				}
				String row = lastName + ","+firstName+","+middleName+","+birthday+","+
				irn+","+physician+","+pathologist+","+specimenType+","+specimen+","+room+","+age+","+
						dateReceived+","+dateCompleted+","+specimenAdequacy+","+hormonalEvaluation+","+
				remarks+","+grossDesc+","+microscopicNotes+","+diagnosis;
				writer.println(row);
				recordCount++;
				setProgress(recordCount * 100 / total);
				publish("Exporting data:Please wait");
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception();
		}
		finally
		{
			if(writer != null)
				writer.close();
		}
	}
	
	private String parseStr(String newStr)
	{
		String parseStr = "";
		if(newStr != null)
		{
			String tempStr = newStr.replaceAll("\"", "''");		
			parseStr = "\"" + tempStr + "\"";
		}
		System.out.println(parseStr);
		return parseStr;
	}
	
	@Override
	protected void process(List<String> chunks)
	{
		exportPanel.getProgressBar().setIndeterminate(false);
		exportPanel.getProgressBar().setValue(getProgress());
		
		String[] messages = chunks.get(chunks.size() - 1).split(":");
		String main = messages[0];
		String sub = messages[1];
		
		exportPanel.setMainMessage(main);
		exportPanel.setSubMessage(sub);
		exportPanel.setRecordCount(recordCount);
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
		exportPanel.setRecordCount(recordCount);
	}
}
