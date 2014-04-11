package org.introse.core.workers;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import org.introse.Constants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordTable;
import org.introse.core.HealthData;
import org.introse.core.dao.MysqlHealthDataDao;
import org.introse.gui.panel.ExportPanel;

public class ExportWorker extends SwingWorker<Void, String> 
{
	private MysqlHealthDataDao healthDataDao;
	private File exportFile;
	private ExportPanel exportPanel;
	private int recordCount;
	
	public ExportWorker(File exportFile, ExportPanel exportPanel)
	{
		//this.healthDataFile = healthDataFile;
		this.exportFile = exportFile;
		this.healthDataDao = new MysqlHealthDataDao();
		this.exportPanel = exportPanel;
	}
	
	@Override
	protected Void doInBackground() throws Exception 
	{
		setProgress(0);
		publish("Exporting data:Please wait");
		healthDataExport();
		setProgress(100);
		return null;
	}
	
	private void healthDataExport() throws Exception
	{
		List<HealthData> hdList = healthDataDao.getData();
		PrintWriter writer = null;
		int total = hdList.size();
		int completed = 0;
		try
		{
			writer = new PrintWriter(exportFile);
			writer.println("Patient ID,Last Name,First Name,Middle Name,Birthdate,Gender,Record ID,Pathologist,Physician,"
					+ "Specimen,Remarks,Room,Gross Description,Microscopic Notes,Date Received,Date Completed,Category ID,Diagnosis");
			Iterator<HealthData> i = hdList.iterator();
			while(i.hasNext())
			{
				String remarks = "";
				String grossDesc = "";
				String microNote = "";
				String room = "";
				HealthData tempHealthData = i.next();
				
				String expStr = tempHealthData.getAttribute(PatientTable.PATIENT_ID.toString()) + "," +
						tempHealthData.getAttribute(PatientTable.LAST_NAME.toString()) + "," +
						tempHealthData.getAttribute(PatientTable.FIRST_NAME.toString()) + "," +
						tempHealthData.getAttribute(PatientTable.MIDDLE_NAME.toString()) + "," + 
						parseStr(tempHealthData.getAttribute(PatientTable.BIRTHDAY).toString()) + "," +
						tempHealthData.getAttribute(PatientTable.GENDER.toString()) + "," +
						tempHealthData.getAttribute(Constants.RecordTable.RECORD_TYPE.toString()) +
						tempHealthData.getAttribute(Constants.RecordTable.RECORD_YEAR.toString()) + "-" +
						tempHealthData.getAttribute(RecordTable.RECORD_NUMBER.toString()) + "," +
						tempHealthData.getAttribute(Constants.RecordTable.PATHOLOGIST.toString())  + "," +
						tempHealthData.getAttribute(Constants.RecordTable.PHYSICIAN.toString())  + "," +
						tempHealthData.getAttribute(Constants.RecordTable.SPECIMEN.toString())  + ",";
				
				if((remarks = (String)tempHealthData.getAttribute(RecordTable.REMARKS)) != null)
					remarks = parseStr(remarks);
				if((room = (String)tempHealthData.getAttribute(RecordTable.ROOM)) != null)
					room = parseStr(room);
				if((grossDesc = (String)tempHealthData.getAttribute(RecordTable.GROSS_DESC)) != null)
					grossDesc = parseStr(grossDesc);
				if((microNote = (String)tempHealthData.getAttribute(RecordTable.MICRO_NOTE)) != null)
					microNote = parseStr(microNote);

				expStr = expStr + remarks + "," + room + "," + grossDesc + "," + microNote + "," +
						parseStr(tempHealthData.getAttribute(RecordTable.DATE_COMPLETED).toString())  + "," +
						parseStr(tempHealthData.getAttribute(RecordTable.DATE_RECEIVED).toString()) + "," +
						tempHealthData.getAttribute(Constants.DiagnosisTable.CATEGORY_ID.toString()) + "," +
						parseStr((String)tempHealthData.getAttribute(Constants.DiagnosisTable.VALUE));
				writer.println(expStr);
				System.out.println(expStr);
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
