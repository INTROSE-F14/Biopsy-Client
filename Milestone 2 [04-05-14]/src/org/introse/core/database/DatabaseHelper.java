package org.introse.core.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

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

public class DatabaseHelper {
	
	private PatientDao patientDao;
	private RecordDao recordDao;
	private DiagnosisDao diagnosisDao;
	private static final String SEPARATOR = ":::";
	
	public DatabaseHelper(PatientDao patientDao, 
			RecordDao recordDao, DiagnosisDao diagnosisDao)
	{
		this.patientDao = patientDao;
		this.recordDao = recordDao;
		this.diagnosisDao = diagnosisDao;
	}
	
	public void backup(File backupFile)
	{
		List<Patient> patients = patientDao.getAll(0, patientDao.getCount());
		List<Record> records = recordDao.getAll();
		List<Diagnosis> diagnosis = diagnosisDao.getAll();
		
		PrintWriter writer = null;
		try 
		{
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
			}
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
			}
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
			}
		} catch (IOException e) {e.printStackTrace();}
		finally
		{
			if(writer != null)
				writer.close();
		}
	}
	
	public void restore(File backupFile)
	{
		BufferedReader reader = null;
		int currentType = RecordConstants.OTHERS;
		int passes = 0;
		try
		{
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
				System.out.println(curLine);
				if(line!= null)
				{
					if(curLine.replaceAll("\\s", "").startsWith("#") && curLine.replaceAll("\\s", "").endsWith("#"))
					{
						curLine = curLine.replace("#", "");
						curLine = curLine.replaceAll("\\s", "");
						System.out.println(curLine);
						if(curLine.equals(TitleConstants.RECORDS))
						{
							currentType = RecordConstants.RECORD;
							System.out.println("YES");
						}
						else if(curLine.equals(TitleConstants.PATIENTS))
							currentType = RecordConstants.PATIENT;
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
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
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
	
		if(patientDao.get(patient) == null)
			patientDao.add(patient);
		else patientDao.update(patient);
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
		if(recordDao.get(record) == null)
			recordDao.add(record);
		else recordDao.update(record);
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
	
		if(diagnosisDao.get(diagnosis) == null)
			diagnosisDao.add(diagnosis);
		else diagnosisDao.update(diagnosis);
	}

}
