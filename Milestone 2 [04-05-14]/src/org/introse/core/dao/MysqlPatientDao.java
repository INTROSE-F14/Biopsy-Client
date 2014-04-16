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
import org.introse.core.CustomCalendar;
import org.introse.core.Patient;

public class MysqlPatientDao extends MysqlDao implements PatientDao
{
	
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
			} catch (SQLException e) {e.printStackTrace();}
		}
		return p;
	}

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
			} catch (SQLException e) {e.printStackTrace();}
		}
	}

	@Override
	public List<Patient> search(Patient patient, int start, int range) 
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
			lastName = lastName.replace("%", "\\%");
			sql = sql.concat(PatientTable.LAST_NAME + " LIKE \"%" + lastName + "%\"");
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
			firstName = firstName.replace("%", "\\%");
			sql = sql.concat(PatientTable.FIRST_NAME + " LIKE \"%" + firstName +"%\"");
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
			middleName.replace("%", "\\%");
			sql = sql.concat(PatientTable.MIDDLE_NAME + " LIKE \"%" + middleName + "%\"");
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
			sql = sql.concat(PatientTable.GENDER + " = \"" + gender + "\"");
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
			String bdayString = PatientTable.BIRTHDAY + " LIKE \"";
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
					bdayString = bdayString.concat("" + birthday.getDay() + "\"");
				else bdayString = bdayString.concat("0" + birthday.getDay() + "\"");
			}
			else bdayString = bdayString.concat("%\"");
			if(whereCount > 0)
			{
				sql = sql.concat(" AND ");
				whereCount--;
			}
			sql = sql.concat(bdayString);
			whereCount++;
		}
		sql = sql.concat(" LIMIT " + start + ", " + range);
		
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
			} catch (SQLException e) {e.printStackTrace();}
		}
		return matches;
	}
	
	public int add(Patient patient)
	{
		int patientId = -1;
		Object pID = patient.getAttribute(PatientTable.PATIENT_ID);
		if(pID != null)
			patientId = (int)pID;
		String lastName = ((String)patient.getAttribute(PatientTable.LAST_NAME)).replace("\"", "\\\"");
		lastName = "\""+lastName+"\"";
		String firstName = ((String)patient.getAttribute(PatientTable.FIRST_NAME)).replace("\"", "\\\"");
		firstName = "\""+firstName+"\"";
		String middleName = ((String)patient.getAttribute(PatientTable.MIDDLE_NAME)).replace("\"", "\\\"");
		middleName = "\""+middleName+"\"";
		
		String gender = "\""+(String)patient.getAttribute(PatientTable.GENDER)+"\"";
		CustomCalendar birthday = (CustomCalendar)patient.getAttribute(PatientTable.BIRTHDAY);
		String bDay = "\"" + birthday.getYear() + "-" + (birthday.getMonth() + 1) + "-" + birthday.getDay() + "\"";
		
		String sql = "Insert into Patients(" + PatientTable.LAST_NAME  +
				", " + PatientTable.FIRST_NAME + ", " + PatientTable.MIDDLE_NAME + ", " + PatientTable.GENDER + 
				", " + PatientTable.BIRTHDAY;
		if(pID != null)
			sql = sql.concat(", " + PatientTable.PATIENT_ID);
		sql = sql.concat(") value(" + lastName + ", " + firstName + ", " + middleName + ", " + gender + ", " + bDay);
		if(pID != null)
			sql = sql.concat(", " + patientId);
		sql = sql.concat(")");
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		ResultSet key = null;
		try 
		{
			
			conn = createConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			key = stmt.getGeneratedKeys();
			key.first();
			patientId = key.getInt(1);
			System.out.println(patientId);
		} catch (ClassNotFoundException | SQLException e) {e.printStackTrace();}  
		finally
		{
			try
			{
				if(result != null)
					result.close();
				if(stmt != null)
					stmt.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
		return patientId;
	}

	@Override
	public List<Patient> getAll(int start, int range) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from patients ORDER BY " + PatientTable.LAST_NAME + " LIMIT " + start +", " + range;
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
			} catch (SQLException e) {e.printStackTrace();}
		}
		return null;
	}

	
	@Override
	public void update(Patient patient) 
	{
		
		int patientId = (int)patient.getAttribute(PatientTable.PATIENT_ID);
		String lastName = ((String)patient.getAttribute(PatientTable.LAST_NAME)).replace("\"", "\\\"");
		lastName = "\""+lastName+"\"";
		String firstName = ((String)patient.getAttribute(PatientTable.FIRST_NAME)).replace("\"", "\\\"");
		firstName = "\""+firstName+"\"";
		String middleName = ((String)patient.getAttribute(PatientTable.MIDDLE_NAME)).replace("\"", "\\\"");
		middleName = "\""+middleName+"\"";
		
		String gender = "\""+(String)patient.getAttribute(PatientTable.GENDER)+"\"";
		CustomCalendar birthday = (CustomCalendar)patient.getAttribute(PatientTable.BIRTHDAY);
		String bDay = "\"" + birthday.getYear() + "-" + (birthday.getMonth() + 1) + "-" + birthday.getDay() + "\"";
		
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
			} catch (SQLException e) {e.printStackTrace();}
		}
		
	}
	
	public int getCount()
	{
		int count = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
			
		String sql = "Select count(*) from patients ";
		
		try 
		{
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			if(result.next())
				count = result.getInt("count(*)");
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
			} catch (SQLException e) {e.printStackTrace();}
		}
		return count;
	}


	@Override
	public int getCount(Patient patient) 
	{
		int count = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		int whereCount = 0;
			
		String sql = "Select count(*) from patients WHERE ";
		
		String lastName;
		if((lastName = (String)patient.getAttribute(Constants.PatientTable.LAST_NAME)) != null)
		{
			lastName = lastName.replace("%", "\\%");
			sql = sql.concat(PatientTable.LAST_NAME + " LIKE \"%" + lastName + "%\"");
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
			firstName = firstName.replace("%", "\\%");
			sql = sql.concat(PatientTable.FIRST_NAME + " LIKE \"%" + firstName +"%\"");
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
			middleName.replace("%", "\\%");
			sql = sql.concat(PatientTable.MIDDLE_NAME + " LIKE \"%" + middleName + "%\"");
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
			sql = sql.concat(PatientTable.GENDER + " = \"" + gender + "\"");
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
			String bdayString = PatientTable.BIRTHDAY + " LIKE \"";
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
					bdayString = bdayString.concat("" + birthday.getDay() + "\"");
				else bdayString = bdayString.concat("0" + birthday.getDay() + "\"");
			}
			else bdayString = bdayString.concat("%\"");
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
			if(result.next())
				count = result.getInt("count(*)");
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
			} catch (SQLException e) {e.printStackTrace();}
		}
		return count;
	}


	@Override
	public List<Patient> get(char start, char end) 
	{
		List<Patient> patients = new Vector<Patient>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from patients WHERE ";
		boolean hasPrevious = false;
		
		while(start <= end)
		{
			String lastName = "\"" + start + "%\"";
			if(hasPrevious)
				sql = sql.concat(" OR ");
			sql = sql.concat(PatientTable.LAST_NAME + " LIKE " + lastName);
			start++;
			hasPrevious = true;
		}
		System.out.println(sql);
		
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
			} catch (SQLException e) {e.printStackTrace();}
		}
		return patients;
	}
	
	@Override
	public List<Patient> get(char start, char end, char gender) 
	{
		List<Patient> patients = new Vector<Patient>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from patients WHERE (";
		boolean hasPrevious = false;
		
		while(start <= end)
		{
			String lastName = "\"" + start + "%\"";
			if(hasPrevious)
				sql = sql.concat(" OR ");
			sql = sql.concat(PatientTable.LAST_NAME + " LIKE " + lastName);
			start++;
			hasPrevious = true;
		}
		sql = sql.concat(") AND " + PatientTable.GENDER + " = '" + gender + "'");
		System.out.println(sql);
		
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
			} catch (SQLException e) {e.printStackTrace();}
		}
		return patients;
	}
}