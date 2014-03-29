package org.introse.core.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordTable;
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
	
	public DatabaseHelper(PatientDao patientDao, 
			RecordDao recordDao, DiagnosisDao diagnosisDao)
	{
		this.patientDao = patientDao;
		this.recordDao = recordDao;
		this.diagnosisDao = diagnosisDao;
	}
	public void backup(File backupFile)
	{
		List<Patient> patients = patientDao.getAll();
		List<Record> records = recordDao.getAll();
		List<Diagnosis> diagnosis = diagnosisDao.getAll();
		
		PrintWriter writer = null;
		try 
		{
			writer = new PrintWriter(new FileWriter(backupFile, true));
			writer.println("#Patients#");
			Iterator<Patient> patientIterator = patients.iterator();
			while(patientIterator.hasNext())
			{
				Patient patient = patientIterator.next();
				int patientId = (int)patient.getAttribute(PatientTable.PATIENT_ID);
				String firstName = (String)patient.getAttribute(PatientTable.FIRST_NAME);
				String middleName = (String)patient.getAttribute(PatientTable.MIDDLE_NAME);
				String lastName = (String)patient.getAttribute(PatientTable.LAST_NAME);
				CustomCalendar bDay = (CustomCalendar)patient.getAttribute(PatientTable.BIRTHDAY);
				String birthday = bDay.toString();
				char gender = ((String)patient.getAttribute(PatientTable.GENDER)).charAt(0);
				
				writer.println(patientId + ":" + firstName + ":" + middleName + ":" + lastName
						+":"+birthday+":"+gender + "$");
			}
			writer.println();
			writer.println("#Records#");
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
				String dateReceived = dR.toString();
				String dateCompleted = dC.toString();
				String specType = (String)record.getAttribute(RecordTable.SPEC_TYPE);
				String specimen = (String)record.getAttribute(RecordTable.SPECIMEN);
				String room = (String)record.getAttribute(RecordTable.ROOM);
				String remarks = (String)record.getAttribute(RecordTable.REMARKS);
				String grossDesc = (String)record.getAttribute(RecordTable.GROSS_DESC);
				String microNote = (String)record.getAttribute(RecordTable.MICRO_NOTE);
				writer.println(patientId + ":" + recordType + ":" + recordYear + ":" + recordNumber + 
						":"+physician + ":" + pathologist + ":"+ dateReceived + ":" + dateCompleted + 
						":" + specType + ":"+ specimen + ":" + room + ":" + remarks + ":"+grossDesc + 
						":" + microNote + "$");
			}
			writer.println();
			writer.println("#Diagnosis#");
			Iterator<Diagnosis> diagnosisIterator = diagnosis.iterator();
			while(diagnosisIterator.hasNext())
			{
				Diagnosis curDiagnosis = diagnosisIterator.next();
				int category = curDiagnosis.getCategory();
				int number = curDiagnosis.getRecordNumber();
				char type = curDiagnosis.getRecordType();
				int year = curDiagnosis.getRecordYear();
				String value = curDiagnosis.getValue();
				writer.println(category + ":"+type + ":" + year + ":"+number +":"+ value + "$");
			}
		} catch (IOException e) {e.printStackTrace();}
		finally
		{
			if(writer != null)
				writer.close();
		}
	}

}
