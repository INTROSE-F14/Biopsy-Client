package org.introse.core.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.introse.core.Patient;
import org.introse.core.PatientTable;
import org.introse.core.Preferences;
import org.introse.core.network.Client;

public class PatientDao extends Dao
{

	public PatientDao(Client client) 
	{
		super(client);
	}

	@Override
	public void updateList() 
	{
		this.data = new Vector<Object>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from patients";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		
			conn = DriverManager.getConnection(Preferences.serverAddress +
					Preferences.databaseName, Preferences.username, Preferences.password);
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				Patient p = new Patient();
				p.putAttribute(PatientTable.PATIENT_ID.toString(),
						result.getString(PatientTable.PATIENT_ID.toString()));
				p.putAttribute(PatientTable.LAST_NAME.toString(), 
						result.getString(PatientTable.LAST_NAME.toString()));
				p.putAttribute(PatientTable.FIRST_NAME.toString(), 
						result.getString(PatientTable.FIRST_NAME.toString()));
				p.putAttribute(PatientTable.MIDDLE_NAME.toString(), 
						result.getString(PatientTable.MIDDLE_NAME.toString()));
				Calendar bday = Calendar.getInstance();
				bday.setTime(result.getDate(PatientTable.BIRTHDAY.toString()));
				p.putAttribute(PatientTable.BIRTHDAY.toString(), bday);
				p.putAttribute(PatientTable.GENDER.toString(), 
						result.getString(PatientTable.GENDER.toString()));
				p.putAttribute(PatientTable.ROOM.toString(), 
						result.getString(PatientTable.ROOM.toString()));
				data.add(p);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
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
		Dao.dataVersion = client.getLatestVersion();
		System.out.println(data.size());
	}
	
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
	public Object get(Object patient) 
	{
		if(!(patient instanceof Patient))
			return null;
		
		data = getAll();
		Iterator<Object> i = data.iterator();
		while(i.hasNext())
		{
			Patient curPatient = (Patient)i.next();
			if(curPatient.equals(patient))
					return curPatient;
		}
		return null;
	}

	
	//the whole list of data will be first checked if there 
	//is an update and retrieved using getAll()
	//why: patient to be deleted might not be in the list anymore
	//(deleted already by another computer prior to your deletion)
	@Override
	public void delete(Object patient) 
	{
		if(!(patient instanceof Patient))
			return;
		
		data = getAll();
		Iterator<Object> i = data.iterator();
		while(i.hasNext())
		{
			Patient curPatient = (Patient)i.next();
			if(curPatient.equals(patient))
			{
				//insert sql delete statement here for curPatient
				System.out.println("delete using sql");
				data.remove(curPatient);
				try {
					client.sendLatestVersion(dataVersion = new Date());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				return;
			}
		}
		
	}

	//the whole list of data will be first checked if there 
	//is an update and retrieved using getAll()
	//why: Patient to be deleted might not be in the list anymore
	//(deleted already by another computer prior to your deletion)
	@Override
	public void delete(List<Object> toBeDeleted) 
	{
		data = getAll();
		Iterator<Object> j = toBeDeleted.iterator();
		
		while(j.hasNext())
		{
			Object curToBeDeleted = j.next();
			if(!(curToBeDeleted instanceof Patient))
				continue;
			
			Iterator<Object> i = data.iterator();
			while(i.hasNext())
			{
				Patient curPatient = (Patient)i.next();
				if(curPatient.equals(curToBeDeleted))
					{
						//insert sql delete statement here
						System.out.println("delete using sql");
						data.remove(curPatient);
						try {
							client.sendLatestVersion(dataVersion = new Date());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}
			}
		}
		
	}

	@Override
	public List<Object> search(Object criteria) 
	{
		if(!(criteria instanceof Patient))
			return null;
		
		List<Object> match = new Vector<Object>();
		data = getAll();
		Iterator<Object> i = data.iterator();
		while(i.hasNext())
		{
			Patient patient = (Patient)i.next();
			if(patient.matches((Patient)criteria))
				match.add(patient);
		}
		return match;
	}
}