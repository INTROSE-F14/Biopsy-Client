package org.introse.core.workers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingWorker;

import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomCalendar;
import org.introse.core.Diagnosis;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.core.dao.DiagnosisDao;
import org.introse.core.dao.PatientDao;
import org.introse.core.dao.RecordDao;
import org.introse.gui.panel.RestorePanel;

public class RestoreWorker extends SwingWorker<Void, String> {

	private RecordDao recordDao;
	private PatientDao patientDao;
	private DiagnosisDao diagnosisDao;
	private static final String SEPARATOR = ":::";
	private File backupFile;
	private RestorePanel restorePanel;
	private int patientCount, recordCount;
	
	public RestoreWorker(File backupFile, PatientDao patientDao, RecordDao recordDao,
			DiagnosisDao diagnosisDao, RestorePanel restorePanel)
	{
		this.backupFile = backupFile;
		this.patientDao = patientDao;
		this.recordDao = recordDao;
		this.diagnosisDao = diagnosisDao;
		this.restorePanel = restorePanel;
		patientCount = 0;
		recordCount = 0;
	}
	
	@Override
	protected Void doInBackground() throws Exception 
	{
		BufferedReader reader = null;
		int currentType = RecordConstants.OTHERS;
		int passes = 0;
		try
		{
			publish("Preparing to restore:Please wait");
			reader = new BufferedReader(new FileReader(backupFile));
			String line = null;
			String curLine = "";
			do
			{
				do
				{
					line = reader.readLine();
					if(line != null)
					{
						if(passes > 0)
							line = "\n" + line;
						curLine = curLine.concat(line);
						passes++;
					}
				}while(line != null && !line.endsWith("$") && !line.endsWith("#"));
				passes = 0;
				if(line!= null)
				{
					if(curLine.replaceAll("\\s", "").startsWith("#") && curLine.replaceAll("\\s", "").endsWith("#"))
					{
						curLine = curLine.replace("#", "");
						curLine = curLine.replaceAll("\\s", "");
						if(curLine.equals(TitleConstants.RECORDS))
						{
							currentType = RecordConstants.RECORD;
						}
						else if(curLine.equals(TitleConstants.PATIENTS))
						{
							currentType = RecordConstants.PATIENT;
						}
						else if(curLine.equals(TitleConstants.DIAGNOSIS))
							currentType = RecordConstants.DIAGNOSIS;
					}
					else if(curLine.endsWith("$"))
					{
						curLine = curLine.replace("$", "");
						switch(currentType)
						{
						case RecordConstants.PATIENT: restorePatient(curLine);
							break;
						case RecordConstants.RECORD:restoreRecord(curLine);
							break;
						case RecordConstants.DIAGNOSIS:restoreDiagnosis(curLine);
						}
					}
					curLine = "";
				}
			}while(line != null);
		}catch(Exception e){e.printStackTrace();}
		finally
		{
			if(reader != null)
				try {reader.close();} 
			catch (IOException e) 
			{e.printStackTrace();}
		}
		return null;
	}
	
	private void restorePatient(String patientInfo)
	{
		String[] curPatient = patientInfo.split(SEPARATOR);
		Patient patient = new Patient();
		int patientID = Integer.parseInt(curPatient[0]);
		String firstName = curPatient[1];
		String middleName = curPatient[2];
		String lastName = curPatient[3];
		String[] birthday = curPatient[4].split("-");
		CustomCalendar bDay = new CustomCalendar();
		bDay.set(Integer.parseInt(birthday[0]) - 1, 
				Integer.parseInt(birthday[1]), 
				Integer.parseInt(birthday[2]));
		String gender = curPatient[5];
		patient.putAttribute(PatientTable.PATIENT_ID, patientID);
		patient.putAttribute(PatientTable.FIRST_NAME, firstName);
		patient.putAttribute(PatientTable.MIDDLE_NAME, middleName);
		patient.putAttribute(PatientTable.LAST_NAME, lastName);
		patient.putAttribute(PatientTable.BIRTHDAY, bDay);
		patient.putAttribute(PatientTable.GENDER, gender);
	
		publish("Restoring patients:" + lastName + ", " + firstName + " " + middleName);
		if(patientDao.get(patient) == null)
			patientDao.add(patient);
		else patientDao.update(patient);
		patientCount++;
	}
	
	private void restoreRecord(String recordInfo)
	{
		String[] curRecord = recordInfo.split(SEPARATOR);
		int patientID = Integer.parseInt(curRecord[0]);
		char recordType = curRecord[1].charAt(0);
		int recordYear = Integer.parseInt(curRecord[2]);
		int recordNumber = Integer.parseInt(curRecord[3]);
		String physician = curRecord[4];
		String pathologist = curRecord[5];
		String[] dateReceived = curRecord[6].split("-");
		String[] dateCompleted = curRecord[7].split("-");
		CustomCalendar dR = new CustomCalendar();
		CustomCalendar dC = new CustomCalendar();
		dR.set(Integer.parseInt(dateReceived[0]), 
				Integer.parseInt(dateReceived[1]), 
						Integer.parseInt(dateReceived[2]));
		dC.set(Integer.parseInt(dateCompleted[0]), 
				Integer.parseInt(dateCompleted[1]), 
						Integer.parseInt(dateCompleted[2]));
		String specType = curRecord[8];
		String specimen = curRecord[9];
		String room = curRecord[10];
		String remarks = curRecord[11];
		String gross = curRecord[12];
		String micro = curRecord[13];
		
		Record record = new Record();
		record.putAttribute(RecordTable.RECORD_TYPE, recordType);
		record.putAttribute(RecordTable.RECORD_YEAR, recordYear);
		record.putAttribute(RecordTable.RECORD_NUMBER, recordNumber);
		record.putAttribute(RecordTable.PATIENT_ID, patientID);
		record.putAttribute(RecordTable.SPECIMEN, specimen);
		record.putAttribute(RecordTable.SPEC_TYPE, specType);
		record.putAttribute(RecordTable.PATHOLOGIST, pathologist);
		record.putAttribute(RecordTable.PHYSICIAN, physician);
		
		if(!remarks.equals("null"))
			record.putAttribute(RecordTable.REMARKS, remarks);
		if(!gross.equals("null"))
			record.putAttribute(RecordTable.GROSS_DESC, gross);
		if(!micro.equals("null"))
			record.putAttribute(RecordTable.MICRO_NOTE, micro);
		if(!room.equals("null"))
			record.putAttribute(RecordTable.ROOM, room);
		record.putAttribute(RecordTable.DATE_RECEIVED, dR);
		record.putAttribute(RecordTable.DATE_COMPLETED, dC);
		
		publish("Restoring records:" + recordType + recordYear + "-" + recordNumber);
		if(recordDao.get(record) == null)
			recordDao.add(record);
		else
		{
			recordDao.update(record);
			diagnosisDao.delete(record);
		}
		recordCount++;
	}
	
	private void restoreDiagnosis(String diagnosisInfo)
	{
		String[] curDiagnosis = diagnosisInfo.split(SEPARATOR);
		int category = Integer.parseInt(curDiagnosis[0]);
		char recordType = curDiagnosis[1].charAt(0);
		int recordYear = Integer.parseInt(curDiagnosis[2]);
		int recordNumber = Integer.parseInt(curDiagnosis[3]);
		String value = curDiagnosis[4];
		
		Diagnosis diagnosis = new Diagnosis(category, value, recordType, recordYear, recordNumber);
		diagnosisDao.add(diagnosis);
	}

	@Override
	protected void process(List<String> chunks)
	{
		String[] messages = chunks.get(chunks.size() - 1).split(":");
		String main = messages[0];
		String sub = messages[1];
		
		restorePanel.setMainMessage(main);
		restorePanel.setSubMessage(sub);
		restorePanel.setRecordCount(recordCount);
		restorePanel.setPatientCount(patientCount);
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
		restorePanel.setRecordCount(recordCount);
		restorePanel.setPatientCount(patientCount);
	}
}
