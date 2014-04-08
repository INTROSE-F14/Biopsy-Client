package org.introse.core.workers;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.SwingWorker;

import org.introse.Constants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordTable;
import org.introse.core.Diagnosis;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.core.dao.DiagnosisDao;
import org.introse.core.dao.MysqlDiagnosisDao;
import org.introse.core.dao.MysqlPatientDao;
import org.introse.core.dao.MysqlRecordDao;
import org.introse.core.dao.PatientDao;
import org.introse.core.dao.RecordDao;
import org.introse.gui.panel.ExportPanel;

public class ExportWorker extends SwingWorker<Void, String> 
{
	private DiagnosisDao diagnosisDao;
	private RecordDao recordDao;
	private PatientDao patientDao;
	private File patientFile;
	private File recordFile;
	private File diagnosisFile;
	private ExportPanel exportPanel;
	private int patientCount, recordCount;
	
	public ExportWorker(File patientFile, File recordFile, File diagnosisFile, ExportPanel exportPanel)
	{
		this.patientFile = patientFile;
		this.recordFile = recordFile;
		this.diagnosisFile = diagnosisFile;
		this.diagnosisDao = new MysqlDiagnosisDao();
		this.patientDao = new MysqlPatientDao();
		this.recordDao = new MysqlRecordDao();
		this.exportPanel = exportPanel;
	}
	
	@Override
	protected Void doInBackground() throws Exception 
	{
		setProgress(0);
		publish("Step 1/3:Exporting patients");
		patExport();
		setProgress(0);
		publish("Step 2/3:Exporting records");
		recExport();
		setProgress(0);
		publish("Step 3/3:Exporting diagnosis");
		diagExport();
		setProgress(100);
		return null;
	}
	
	private void patExport() throws Exception
	{
		List<Patient> patList = patientDao.getAll();
		patientCount = 0;
		int patientSize = patList.size();
		setProgress(0);
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(patientFile);
			writer.println("Patient ID, Last Name, First Name, Middle Name, Birthdate, Gender");
			for(int i = 0; i < patList.size(); i++)
			{
				Patient tempPat = patList.get(i);
				String expStr = tempPat.getAttribute(PatientTable.PATIENT_ID.toString()) + "," +
						tempPat.getAttribute(PatientTable.LAST_NAME.toString()) + "," +
						tempPat.getAttribute(PatientTable.FIRST_NAME.toString()) + "," +
						tempPat.getAttribute(PatientTable.MIDDLE_NAME.toString()) + "," + 
						dateParse(tempPat.getAttribute(PatientTable.BIRTHDAY).toString()) + "," +
						tempPat.getAttribute(PatientTable.GENDER.toString());
				writer.println(expStr);
				patientCount++;
				setProgress(patientCount * 100 / patientSize);
				publish("Step 1/3:Exporting patients");
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
	
	private void diagExport() throws Exception
	{
		List<Diagnosis> diagList = diagnosisDao.getAll();
		PrintWriter writer = null;
		int completedDiagnosis = 0;
		int diagnosisSize = diagList.size();
		try
		{
			writer = new PrintWriter(diagnosisFile);
			writer.println("Rec. Num,Rec. Year,Rec. Type,Category ID,Value");
			for(int i = 0; i < diagList.size(); i++)
			{
				Diagnosis tempDiag = diagList.get(i);
				String expStr = Integer.toString(tempDiag.getRecordNumber()) + "," +
						Integer.toString(tempDiag.getRecordYear()) + "," +
						Character.toString(tempDiag.getRecordType()) + "," +
						Integer.toString(tempDiag.getCategory()) + "," +
						parseStr(tempDiag.getValue());
				System.out.println();
				writer.println(expStr);
				completedDiagnosis++;
				setProgress(completedDiagnosis * 100 / diagnosisSize);
				publish("Step 3/3:Exporting diagnosis");
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

	private void recExport() throws Exception
	{
		List<Record> recList = recordDao.getAll();
		recordCount = 0;
		int recordSize = recList.size();
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(recordFile);
			writer.println("Patient ID,Rec. Year,Rec. Num.,Rec. Type,Pathologist,Physician,Specimen,Remarks,Room,Gross Desc.,Micro. Note,Date Completed,Date Received");
			for(int i = 0; i < recList.size(); i++)
			{
				Record tempRec = recList.get(i);
				String expStr = tempRec.getAttribute(Constants.RecordTable.PATIENT_ID.toString()) + "," +
				tempRec.getAttribute(Constants.RecordTable.RECORD_YEAR.toString()) + "," +
				tempRec.getAttribute(RecordTable.RECORD_NUMBER.toString()) + "," +
				tempRec.getAttribute(Constants.RecordTable.RECORD_TYPE.toString()) + "," +
				tempRec.getAttribute(Constants.RecordTable.PATHOLOGIST.toString())  + "," +
				tempRec.getAttribute(Constants.RecordTable.PHYSICIAN.toString())  + "," +
				tempRec.getAttribute(Constants.RecordTable.SPECIMEN.toString())  + ",";
				
				//these are nullables
				String remarks = "";
				String grossDesc = "";
				String microNote = "";
				String room = "";
				
				if((remarks = (String)tempRec.getAttribute(RecordTable.REMARKS)) != null)
					remarks = parseStr(remarks);
				if((room = (String)tempRec.getAttribute(RecordTable.ROOM)) != null)
					room = parseStr(room);
				if((grossDesc = (String)tempRec.getAttribute(RecordTable.GROSS_DESC)) != null)
					grossDesc = parseStr(grossDesc);
				if((microNote = (String)tempRec.getAttribute(RecordTable.MICRO_NOTE)) != null)
					microNote = parseStr(microNote);
				
				expStr = expStr + remarks + "," + room + "," + grossDesc + "," + microNote + ","+
				dateParse(tempRec.getAttribute(RecordTable.DATE_COMPLETED).toString())  + "," +
				dateParse(tempRec.getAttribute(RecordTable.DATE_RECEIVED).toString());
				writer.println(expStr);
				recordCount++;
				setProgress(recordCount * 100 / recordSize);
				publish("Step 2/3:Exporting records");
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
	
	private String dateParse(String newDate)
	{
		String parseDate = newDate.replace(',', ' ');
		return parseDate;
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
		exportPanel.setPatientCount(patientCount);
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
		exportPanel.setRecordCount(recordCount);
		exportPanel.setPatientCount(patientCount);
	}
}
