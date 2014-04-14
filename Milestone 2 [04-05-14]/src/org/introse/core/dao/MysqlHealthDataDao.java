package org.introse.core.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.introse.Constants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordTable;
import org.introse.core.CustomCalendar;
import org.introse.core.HealthData;

public class MysqlHealthDataDao extends MysqlDao
{
	public List<HealthData> getData() 
	{
		List<HealthData> healthDataList = new Vector<HealthData>();
		Statement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String sql = "SELECT * FROM patients AS pat, records AS rec, diagnosis AS dia "
				+ "WHERE pat.patientID = rec.patientID AND " +
				"rec.recordYear = dia.recordYear AND " +
				"rec.recordType = dia.recordType AND " +
				"rec.recordNumber = dia.recordNumber";

		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				//Constants from Patient DAO
				HealthData hd = new HealthData();
				hd.putAttribute(PatientTable.PATIENT_ID.toString(),
						result.getInt(PatientTable.PATIENT_ID.toString()));
				hd.putAttribute(PatientTable.LAST_NAME.toString(), 
						result.getString(PatientTable.LAST_NAME.toString()));
				hd.putAttribute(PatientTable.FIRST_NAME.toString(), 
						result.getString(PatientTable.FIRST_NAME.toString()));
				hd.putAttribute(PatientTable.MIDDLE_NAME.toString(), 
						result.getString(PatientTable.MIDDLE_NAME.toString()));
				Calendar bday = Calendar.getInstance();
				bday.setTime(result.getDate(PatientTable.BIRTHDAY.toString()));
				CustomCalendar calendar = new CustomCalendar();
				calendar.set(bday.get(Calendar.MONTH), bday.get(Calendar.DATE), bday.get(Calendar.YEAR));
				hd.putAttribute(PatientTable.BIRTHDAY.toString(), calendar);
				hd.putAttribute(PatientTable.GENDER.toString(), 
						result.getString(PatientTable.GENDER.toString()));
				
				//Constants from Record DAO
				hd.putAttribute(Constants.RecordTable.RECORD_YEAR, 
						result.getInt(Constants.RecordTable.RECORD_YEAR));
				hd.putAttribute(RecordTable.RECORD_NUMBER, 
						result.getInt(RecordTable.RECORD_NUMBER));
				hd.putAttribute(Constants.RecordTable.RECORD_TYPE,
						result.getString(Constants.RecordTable.RECORD_TYPE));
				hd.putAttribute(Constants.RecordTable.PATHOLOGIST, 
						result.getString(Constants.RecordTable.PATHOLOGIST));
				hd.putAttribute(Constants.RecordTable.PHYSICIAN,
						result.getString(Constants.RecordTable.PHYSICIAN));
				hd.putAttribute(Constants.RecordTable.SPECIMEN, 
						result.getString(Constants.RecordTable.SPECIMEN));
				hd.putAttribute(Constants.RecordTable.REMARKS,
						result.getString(Constants.RecordTable.REMARKS));
				hd.putAttribute(Constants.RecordTable.ROOM, 
						result.getString(Constants.RecordTable.ROOM));
				hd.putAttribute(RecordTable.GROSS_DESC,
						result.getString(RecordTable.GROSS_DESC));
				hd.putAttribute(RecordTable.MICRO_NOTE, 
						result.getString(RecordTable.MICRO_NOTE));
				Calendar dateReceived = Calendar.getInstance();
				Calendar dateCompleted = Calendar.getInstance();
				dateCompleted.clear();
				dateCompleted.setTime(result.getDate(Constants.RecordTable.DATE_COMPLETED));
				dateReceived.clear();
				dateReceived.setTime(result.getDate(Constants.RecordTable.DATE_RECEIVED));
				
				CustomCalendar dC = new CustomCalendar();
				CustomCalendar dR = new CustomCalendar();
				dC.set(dateCompleted.get(Calendar.MONTH), dateCompleted.get(Calendar.DATE), dateCompleted.get(Calendar.YEAR));
				dR.set(dateReceived.get(Calendar.MONTH), dateReceived.get(Calendar.DATE), dateReceived.get(Calendar.YEAR));
				hd.putAttribute(Constants.RecordTable.DATE_COMPLETED, dC);
				hd.putAttribute(Constants.RecordTable.DATE_RECEIVED, dR);
				hd.putAttribute(RecordTable.SPEC_TYPE, result.getString(RecordTable.SPEC_TYPE));
				
				//Constants from Diagnosis DAO
				hd.putAttribute(Constants.RecordTable.RECORD_TYPE,
						result.getString(Constants.RecordTable.RECORD_TYPE));
				hd.putAttribute(Constants.DiagnosisTable.CATEGORY_ID,
						result.getString(Constants.DiagnosisTable.CATEGORY_ID));
				hd.putAttribute(Constants.DiagnosisTable.VALUE,
						result.getString(Constants.DiagnosisTable.VALUE));
				
				healthDataList.add(hd);
			}
		} catch (ClassNotFoundException | SQLException e) 
		{e.printStackTrace();}  
		finally
		{
			try
			{
				if(result != null)
					result.close();
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
		return healthDataList;
	}
}
