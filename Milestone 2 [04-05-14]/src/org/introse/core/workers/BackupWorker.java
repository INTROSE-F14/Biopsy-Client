package org.introse.core.workers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordTable;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomCalendar;
import org.introse.core.Diagnosis;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.core.dao.DiagnosisDao;
import org.introse.core.dao.MysqlDiagnosisDao;
import org.introse.core.dao.MysqlPatientDao;
import org.introse.core.dao.MysqlRecordDao;
import org.introse.core.dao.PatientDao;
import org.introse.core.dao.RecordDao;
import org.introse.gui.panel.BackupPanel;

public class BackupWorker extends SwingWorker<Void, String> 
{
	private static final String SEPARATOR = ":::";
	private DiagnosisDao diagnosisDao;
	private RecordDao recordDao;
	private PatientDao patientDao;
	private File backupFile;
	private BackupPanel backupPanel;
	private int patientCount, recordCount;
	
	public BackupWorker(File backupFile, BackupPanel backupPanel)
	{
		this.backupFile = backupFile;
		this.diagnosisDao = new MysqlDiagnosisDao();
		this.patientDao = new MysqlPatientDao();
		this.recordDao = new MysqlRecordDao();
		this.backupPanel = backupPanel;
	}
	
	@Override
	protected Void doInBackground() throws Exception 
	{
		List<Patient> patients = patientDao.getAll(0, patientDao.getCount());
		List<Record> records = recordDao.getAll();
		List<Diagnosis> diagnosis = diagnosisDao.getAll();

		PrintWriter writer = null;
		try 
		{
			patientCount = 0;
			int patientSize = patients.size();
			setProgress(0);
			publish("Step 1/3:Backing up patients");
			
			writer = new PrintWriter(new FileWriter(backupFile, true));
			writer.println("#" + TitleConstants.PATIENTS + "#");
			Iterator<Patient> patientIterator = patients.iterator();
			while(patientIterator.hasNext())
			{
				Patient patient = patientIterator.next();
				int patientId = (int)patient.getAttribute(PatientTable.PATIENT_ID);
				String firstName = (String)patient.getAttribute(PatientTable.FIRST_NAME);
				String middleName = (String)patient.getAttribute(PatientTable.MIDDLE_NAME);
				String lastName = (String)patient.getAttribute(PatientTable.LAST_NAME);
				CustomCalendar bDay = (CustomCalendar)patient.getAttribute(PatientTable.BIRTHDAY);
				String birthday = (bDay.getMonth()+1) +"-"+bDay.getDay()+"-"+bDay.getYear();
				char gender = ((String)patient.getAttribute(PatientTable.GENDER)).charAt(0);
				
				writer.println(patientId + SEPARATOR + firstName + SEPARATOR + middleName + SEPARATOR + lastName
						+SEPARATOR+birthday+SEPARATOR+gender + "$");
				patientCount++;
				setProgress(patientCount * 100 / patientSize);
				publish("Step 1/3:Backing up patients");
			}
			
			recordCount = 0;
			int recordSize = records.size();
			setProgress(0);
			publish("Step 2/3:Backing up records");
			writer.println();
			writer.println("#" + TitleConstants.RECORDS + "#");
			Iterator<Record> recordIterator = records.iterator();
			while(recordIterator.hasNext())
			{
				Record record = recordIterator.next();
				int patientId = (int)record.getAttribute(RecordTable.PATIENT_ID);
				char recordType = ((String)record.getAttribute(RecordTable.RECORD_TYPE)).charAt(0);
				int recordYear = (int)record.getAttribute(RecordTable.RECORD_YEAR);
				int recordNumber = (int)record.getAttribute(RecordTable.RECORD_NUMBER);
				String physician = (String)record.getAttribute(RecordTable.PHYSICIAN);
				String pathologist = (String)record.getAttribute(RecordTable.PATHOLOGIST);
				CustomCalendar dR= (CustomCalendar)record.getAttribute(RecordTable.DATE_RECEIVED);
				CustomCalendar dC = (CustomCalendar)record.getAttribute(RecordTable.DATE_COMPLETED);
				String dateReceived = dR.getMonth() +"-"+dR.getDay() + "-"+dR.getYear();
				String dateCompleted = dC.getMonth()+"-"+dC.getDay() + "-"+dC.getYear();
				String specType = (String)record.getAttribute(RecordTable.SPEC_TYPE);
				String specimen = (String)record.getAttribute(RecordTable.SPECIMEN);
				String room = (String)record.getAttribute(RecordTable.ROOM);
				String remarks = (String)record.getAttribute(RecordTable.REMARKS);
				String grossDesc = (String)record.getAttribute(RecordTable.GROSS_DESC);
				String microNote = (String)record.getAttribute(RecordTable.MICRO_NOTE);
				writer.println(patientId + SEPARATOR + recordType + SEPARATOR + recordYear + SEPARATOR + recordNumber + 
						SEPARATOR+physician + SEPARATOR + pathologist + SEPARATOR+ dateReceived + SEPARATOR + dateCompleted + 
						SEPARATOR + specType + SEPARATOR+ specimen + SEPARATOR + room + SEPARATOR + remarks + SEPARATOR+grossDesc + 
						SEPARATOR + microNote + "$");
				recordCount++;
				setProgress(recordCount * 100 / recordSize);
				publish("Step 2/3:Backing up records");
			}
			
			int completedDiagnosis = 0;
			int diagnosisSize = diagnosis.size();
			setProgress(0);
			publish("Step 3/3: Backing up diagnosis'");
			writer.println();
			writer.println("#" + TitleConstants.DIAGNOSIS + "#");
			Iterator<Diagnosis> diagnosisIterator = diagnosis.iterator();
			while(diagnosisIterator.hasNext())
			{
				Diagnosis curDiagnosis = diagnosisIterator.next();
				int category = curDiagnosis.getCategory();
				int number = curDiagnosis.getRecordNumber();
				char type = curDiagnosis.getRecordType();
				int year = curDiagnosis.getRecordYear();
				String value = curDiagnosis.getValue();
				writer.println(category + SEPARATOR+type + SEPARATOR + year + SEPARATOR+number +SEPARATOR+ value + "$");
				completedDiagnosis++;
				setProgress(completedDiagnosis * 100 / diagnosisSize);
				publish("Step 3/3:Backing up diagnosis");
			}
		} catch (IOException e) {
			throw new IOException();
		}
		finally
		{
			if(writer != null)
				writer.close();
		}
		return null;
	}

	@Override
	protected void process(List<String> chunks)
	{
		backupPanel.getProgressBar().setIndeterminate(false);
		backupPanel.getProgressBar().setValue(getProgress());
		
		String[] messages = chunks.get(chunks.size() - 1).split(":");
		String main = messages[0];
		String sub = messages[1];
		
		backupPanel.setMainMessage(main);
		backupPanel.setSubMessage(sub);
		backupPanel.setRecordCount(recordCount);
		backupPanel.setPatientCount(patientCount);
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
		backupPanel.setRecordCount(recordCount);
		backupPanel.setPatientCount(patientCount);
	}
}
