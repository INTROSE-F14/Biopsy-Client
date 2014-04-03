package org.introse.core.dbtools;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.introse.Constants;
import org.introse.Constants.DiagnosisTable;
import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordTable;
import org.introse.core.CustomCalendar;
import org.introse.core.Diagnosis;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.core.dao.MysqlDiagnosisDao;
import org.introse.core.dao.MysqlPatientDao;
import org.introse.core.dao.MysqlRecordDao;

public class ExportCSV
{
	MysqlDiagnosisDao diagDao = new MysqlDiagnosisDao();
	MysqlPatientDao patDao = new MysqlPatientDao();
	MysqlRecordDao recDao = new MysqlRecordDao();
	
	List<Diagnosis> diagList = new Vector<Diagnosis>();
	List<Patient> patList = new Vector<Patient>();
	List<Record> recList = new Vector<Record>();
	
	public void initExport()
	{
		diagList = diagDao.getAll();
		diagExport();
		
		patList = patDao.getAll();
		patExport();
		
		recList = recDao.getAll();
		recExport();
	}
	
	private void diagExport()
	{
		try
		{
			PrintWriter writer = new PrintWriter("diagBackup.csv", "UTF-8");
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
			}
			writer.close();
		}
		
		catch(Exception e)
		{
			
		}
	}
	
	private void patExport()
	{
		try
		{
			PrintWriter writer = new PrintWriter("patBackup.csv", "UTF-8");
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
			}
			writer.close();
		}
		
		catch(Exception e)
		{
		}
	}
	
	private void recExport()
	{
		try
		{
			PrintWriter writer = new PrintWriter("recBackup.csv", "UTF-8");
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
				tempRec.getAttribute(Constants.RecordTable.SPECIMEN.toString())  + "," +
				parseStr(tempRec.getAttribute(Constants.RecordTable.REMARKS).toString())  + "," +
				tempRec.getAttribute(Constants.RecordTable.ROOM.toString()) + "," +
				parseStr(tempRec.getAttribute(RecordTable.GROSS_DESC).toString())  + "," +
				parseStr(tempRec.getAttribute(RecordTable.MICRO_NOTE).toString())  + "," +
				dateParse(tempRec.getAttribute(RecordTable.DATE_COMPLETED).toString())  + "," +
				dateParse(tempRec.getAttribute(RecordTable.DATE_RECEIVED).toString());
				writer.println(expStr);
			}
			writer.close();
		}
		
		catch(Exception e)
		{
		}
	}
	
	private String dateParse(String newDate)
	{
		String parseDate = newDate.replace(',', ' ');
		return parseDate;
	}
	
	private String parseStr(String newStr)
	{
		String tempStr = newStr.replaceAll("\"", "''");		
		String parseStr = "\"" + tempStr + "\"";
		System.out.println(parseStr);
		return parseStr;
	}
}
