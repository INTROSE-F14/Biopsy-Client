package org.introse.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.introse.Constants;
import org.introse.Constants.PatientTable;
import org.introse.core.CustomCalendar;
import org.introse.core.Patient;
import org.introse.core.Preferences;

public class MysqlPatientDao extends MysqlDao implements PatientDao
{
	
	//patient should only contain the primary key
	//sample:
	//Patient r = new Patient(1);
	//Patient completePatientInfo = InstanceOfPatientDao.get(r);
	//The whole list of data will be first checked if there 
	//is an update and retrieved using getAll()
	//why 1: patient to be retrieved might not be in the list anymore
	//(deleted already by another computer prior to your selection)
	//why 2: patient may have newer data
	//(edited/modified by another computer prior to your selection)
	@Override
	public Patient get(Patient patient) 
	{
		Patient p = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from patients WHERE " + PatientTable.PATIENT_ID + " = " + 
		patient.getAttribute(PatientTable.PATIENT_ID);
		try 
		{
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			if(result.next())
			{
				p = new Patient();
				p.putAttribute(PatientTable.PATIENT_ID.toString(),
						result.getInt(PatientTable.PATIENT_ID.toString()));
				p.putAttribute(PatientTable.LAST_NAME.toString(), 
						result.getString(PatientTable.LAST_NAME.toString()));
				p.putAttribute(PatientTable.FIRST_NAME.toString(), 
						result.getString(PatientTable.FIRST_NAME.toString()));
				p.putAttribute(PatientTable.MIDDLE_NAME.toString(), 
						result.getString(PatientTable.MIDDLE_NAME.toString()));
				Calendar bday = Calendar.getInstance();
				bday.setTime(result.getDate(PatientTable.BIRTHDAY.toString()));
				CustomCalendar calendar = new CustomCalendar();
				calendar.set(bday.get(Calendar.MONTH), bday.get(Calendar.DATE), bday.get(Calendar.YEAR));
				p.putAttribute(PatientTable.BIRTHDAY.toString(), calendar);
				p.putAttribute(PatientTable.GENDER.toString(), 
						result.getString(PatientTable.GENDER.toString()));
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
		return p;
	}

	
	//the whole list of data will be first checked if there 
	//is an update and retrieved using getAll()
	//why: patient to be deleted might not be in the list anymore
	//(deleted already by another computer prior to your deletion)
	@Override
	public void delete(Patient patient) 
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "DELETE FROM PATIENTS WHERE " + PatientTable.PATIENT_ID + " = " + 
		patient.getAttribute(PatientTable.PATIENT_ID);
		try 
		{
			conn = createConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
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
	}

	@Override
	public List<Patient> search(Patient patient) 
	{
		List<Patient> matches = new Vector<Patient>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		int whereCount = 0;
		
		String sql = "Select * from patients WHERE ";
		
		String lastName;
		if((lastName = (String)patient.getAttribute(Constants.PatientTable.LAST_NAME)) != null)
		{
			sql = sql.concat(PatientTable.LAST_NAME + " LIKE '%" + lastName + "%'");
			whereCount++;
		}
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		
		String firstName;
		if((firstName = (String)patient.getAttribute(Constants.PatientTable.FIRST_NAME)) != null)
		{
			sql = sql.concat(PatientTable.FIRST_NAME + " LIKE '%" + firstName +"%'");
			whereCount++;
		}
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		String middleName;
		if((middleName = (String)patient.getAttribute(Constants.PatientTable.MIDDLE_NAME)) != null)
		{
			sql = sql.concat(PatientTable.MIDDLE_NAME + " LIKE '%" + middleName + "%'");
			whereCount++;
		}
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		String gender;
		if((gender= (String)patient.getAttribute(Constants.PatientTable.GENDER)) != null)
		{
			sql = sql.concat(PatientTable.GENDER + " = '" + gender + "'");
			whereCount++;
		}	
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		
		CustomCalendar birthday = (CustomCalendar)patient.getAttribute(Constants.PatientTable.BIRTHDAY);
		if(birthday!= null)
		{
			String bdayString = PatientTable.BIRTHDAY + " LIKE '";
			if(birthday.getYear() != -1)
				bdayString = bdayString.concat("" + birthday.getYear() + "-");
			else bdayString = bdayString.concat("%-");
			if(birthday.getMonth() != -1)
			{
				if(birthday.getMonth() > 9)
				bdayString = bdayString.concat("" + birthday.getMonth() + "-");
				else bdayString = bdayString.concat("0" + birthday.getMonth() + "-");
			}
			else bdayString = bdayString.concat("%-");
			if(birthday.getDay() != -1)
			{
				if(birthday.getDay() > 9)
					bdayString = bdayString.concat("" + birthday.getDay() + "'");
				else bdayString = bdayString.concat("0" + birthday.getDay() + "'");
			}
			else bdayString = bdayString.concat("%'");
			if(whereCount > 0)
			{
				sql = sql.concat(" AND ");
				whereCount--;
			}
			sql = sql.concat(bdayString);
			whereCount++;
		}
		
		try 
		{
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			while(result.next())
			{
				Patient p = new Patient();
				p.putAttribute(PatientTable.PATIENT_ID.toString(),
						result.getInt(PatientTable.PATIENT_ID.toString()));
				p.putAttribute(PatientTable.LAST_NAME.toString(), 
						result.getString(PatientTable.LAST_NAME.toString()));
				p.putAttribute(PatientTable.FIRST_NAME.toString(), 
						result.getString(PatientTable.FIRST_NAME.toString()));
				p.putAttribute(PatientTable.MIDDLE_NAME.toString(), 
						result.getString(PatientTable.MIDDLE_NAME.toString()));
				Calendar bday = Calendar.getInstance();
				CustomCalendar calendar = new CustomCalendar();
				bday.setTime(result.getDate(PatientTable.BIRTHDAY.toString()));
				calendar.set(bday.get(Calendar.MONTH), bday.get(Calendar.DATE), bday.get(Calendar.YEAR));
				p.putAttribute(PatientTable.BIRTHDAY.toString(), calendar);
				p.putAttribute(PatientTable.GENDER.toString(), 
						result.getString(PatientTable.GENDER.toString()));
				matches.add(p);
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
		return matches;
	}
	
	public void add(Patient patient)
	{
		int patientId = (int)patient.getAttribute(PatientTable.PATIENT_ID);
		String lastName = "'"+(String)patient.getAttribute(PatientTable.LAST_NAME)+"'";
		String firstName = "'"+(String)patient.getAttribute(PatientTable.FIRST_NAME)+"'";
		String middleName = "'"+(String)patient.getAttribute(PatientTable.MIDDLE_NAME)+"'";
		
		String gender = "'"+(String)patient.getAttribute(PatientTable.GENDER)+"'";
		CustomCalendar birthday = (CustomCalendar)patient.getAttribute(PatientTable.BIRTHDAY);
		String bDay = "'" + birthday.getYear() + "-" + (birthday.getMonth() + 1) + "-" + birthday.getDay() + "'";
		
		String sql = "Insert into Patients(" + PatientTable.PATIENT_ID + ", " + PatientTable.LAST_NAME  +
				", " + PatientTable.FIRST_NAME + ", " + PatientTable.MIDDLE_NAME + ", " + PatientTable.GENDER + 
				", " + PatientTable.BIRTHDAY + ") value (" + patientId + ", " +
				lastName + ", " + firstName + ", " + middleName + ", " + gender + ", " + bDay + ")";
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		try 
		{
			conn = createConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (ClassNotFoundException | SQLException e) {e.printStackTrace();}  
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
	}

	@Override
	public List<Patient> getAll() {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from patients ORDER BY " + PatientTable.LAST_NAME;
		List<Patient> patients = new Vector<Patient>();
		try 
		{
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				Patient p = new Patient();
				p.putAttribute(PatientTable.PATIENT_ID.toString(),
						result.getInt(PatientTable.PATIENT_ID.toString()));
				p.putAttribute(PatientTable.LAST_NAME.toString(), 
						result.getString(PatientTable.LAST_NAME.toString()));
				p.putAttribute(PatientTable.FIRST_NAME.toString(), 
						result.getString(PatientTable.FIRST_NAME.toString()));
				p.putAttribute(PatientTable.MIDDLE_NAME.toString(), 
						result.getString(PatientTable.MIDDLE_NAME.toString()));
				Calendar bday = Calendar.getInstance();
				bday.setTime(result.getDate(PatientTable.BIRTHDAY.toString()));
				CustomCalendar calendar = new CustomCalendar();
				calendar.set(bday.get(Calendar.MONTH), bday.get(Calendar.DATE), bday.get(Calendar.YEAR));
				p.putAttribute(PatientTable.BIRTHDAY.toString(), calendar);
				p.putAttribute(PatientTable.GENDER.toString(), 
						result.getString(PatientTable.GENDER.toString()));
				patients.add(p);
			}
			return patients;
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
		return null;
	}

	
	@Override
	public void update(Patient patient) 
	{
		
		int patientId = (int)patient.getAttribute(PatientTable.PATIENT_ID);
		String lastName = "'"+(String)patient.getAttribute(PatientTable.LAST_NAME)+"'";
		String firstName = "'"+(String)patient.getAttribute(PatientTable.FIRST_NAME)+"'";
		String middleName = "'"+(String)patient.getAttribute(PatientTable.MIDDLE_NAME)+"'";
		String gender = "'"+(String)patient.getAttribute(PatientTable.GENDER)+"'";
		CustomCalendar birthday = (CustomCalendar)patient.getAttribute(PatientTable.BIRTHDAY);
		String bDay = "'" + birthday.getYear() + "-" + (birthday.getMonth() + 1) + "-" + birthday.getDay() + "'";
		
		String sql = "Update patients set " + 
				PatientTable.LAST_NAME + " = " + lastName + ", " + 
				PatientTable.FIRST_NAME + " = " + firstName +", " + 
				PatientTable.LAST_NAME + " = " + lastName + ", " + 
				PatientTable.MIDDLE_NAME + " = " + middleName+ ", "+
				PatientTable.GENDER + " =  " + gender + ", " + 
				PatientTable.BIRTHDAY +" = " + bDay + 
				" WHERE " + PatientTable.PATIENT_ID + " = " + patientId;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		try 
		{
			conn = createConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (ClassNotFoundException | SQLException e) {e.printStackTrace();}  
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
		
	}
}