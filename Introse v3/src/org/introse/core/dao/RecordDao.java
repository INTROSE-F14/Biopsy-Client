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

import org.introse.Constants;
import org.introse.core.CytologyRecord;
import org.introse.core.GynecologyRecord;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.core.network.Client;

public class RecordDao extends Dao
{

	public RecordDao(Client client) 
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
		String sql = "Select * from records";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		
			conn = DriverManager.getConnection(Preferences.serverAddress +
					Preferences.databaseName, Preferences.username, Preferences.password);
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				Record record = null;
				int recordType = result.getInt(Constants.RecordTable.RECORD_TYPE);
				switch(recordType)
				{
				case 0: //histopathology
					record = new HistopathologyRecord();
					break;
				case 1: //gynecology
					record = new GynecologyRecord();
					break;
				case 2: //cytology
					record = new CytologyRecord();
					break;
				}
				record.putAttribute(Constants.RecordTable.PATIENT_ID, 
						result.getInt(Constants.RecordTable.PATIENT_ID));
				record.putAttribute(Constants.RecordTable.REF_NUM, 
						result.getString(Constants.RecordTable.REF_NUM));
				record.putAttribute(Constants.RecordTable.RECORD_TYPE,
						result.getInt(Constants.RecordTable.RECORD_TYPE));
				record.putAttribute(Constants.RecordTable.PATHOLOGIST, 
						result.getString(Constants.RecordTable.PATHOLOGIST));
				record.putAttribute(Constants.RecordTable.PHYSICIAN,
						result.getString(Constants.RecordTable.PHYSICIAN));
				record.putAttribute(Constants.RecordTable.SPECIMEN, 
						result.getString(Constants.RecordTable.SPECIMEN));
				record.putAttribute(Constants.RecordTable.DIAGNOSIS, 
						result.getString(Constants.RecordTable.DIAGNOSIS));
				record.putAttribute(Constants.RecordTable.REMARKS,
						result.getString(Constants.RecordTable.REMARKS));
				Calendar dateReceived = Calendar.getInstance();
				Calendar dateCompleted = Calendar.getInstance();
				dateCompleted.clear();
				dateCompleted.setTime(result.getDate(Constants.RecordTable.DATE_COMPLETED));
				dateReceived.clear();
				dateReceived.setTime(result.getDate(Constants.RecordTable.DATE_RECEIVED));
				record.putAttribute(Constants.RecordTable.DATE_COMPLETED, dateCompleted);
				record.putAttribute(Constants.RecordTable.DATE_RECEIVED, dateReceived);
				data.add(record);
			}
			
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
		Dao.dataVersion = client.getLatestVersion();
	}
	
	//record should only contain the primary key
	//sample:
	//Record r = new HistopathologyRecord("H2014-1234");
	//Record completeRecordInfo = InstanceOfRecordDao.get(r);
	//The whole list of data will be first checked if there 
	//is an update and retrieved using getAll()
	//why 1: record to be retrieved might not be in the list anymore
	//(deleted already by another computer prior to your selection)
	//why 2: record may have newer data
	//(edited/modified by another computer prior to your selection)
	@Override
	public Object get(Object record)
	{
		if(!(record instanceof Record))
			return null;
		
		data = getAll();
		Iterator<Object> i = data.iterator();
		while(i.hasNext())
		{
			Record curRecord = (Record)i.next();
			if(curRecord.equals(record))
					return curRecord;
		}
		return null;
	}

	
	//the whole list of data will be first checked if there 
	//is an update and retrieved using getAll()
	//why: record to be deleted might not be in the list anymore
	//(deleted already by another computer prior to your deletion)
	@Override
	public void delete(Object record) 
	{
		if(!(record instanceof Record))
			return;
		
		data = getAll();
		Iterator<Object> i = data.iterator();
		while(i.hasNext())
		{
			Record curRecord = (Record)i.next();
			if(curRecord.equals(record))
			{
				//insert sql delete statement here for curRecord
				System.out.println("delete using sql");
				data.remove(curRecord);
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
	//why: Record to be deleted might not be in the list anymore
	//(deleted already by another computer prior to your deletion)
	@Override
	public void delete(List<Object> toBeDeleted) 
	{
		data = getAll();
		Iterator<Object> j = toBeDeleted.iterator();
		
		while(j.hasNext())
		{
			Object curToBeDeleted = j.next();
			if(!(curToBeDeleted instanceof Record))
				continue;
			
			Iterator<Object> i = data.iterator();
			while(i.hasNext())
			{
				Record curRecord = (Record)i.next();
				if(curRecord.equals(curToBeDeleted))
				{
					//insert sql delete statement here
					System.out.println("delete using sql");
					data.remove(curRecord);
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
		if(!(criteria instanceof Record))
			return null;
		
		List<Object> matches = new Vector<Object>();
		data = getAll();
		Iterator<Object> i = data.iterator();
		while(i.hasNext())
		{
			Record record = (Record)i.next();
			if(record.matches((Record)criteria))
				matches.add(record);
		}
		return matches;
	}
	
	public void add(Object record)
	{
		Record r = (Record)record;
		int patientId = (int)r.getAttribute(Constants.RecordTable.PATIENT_ID);
		String refNum = "'"+(String)r.getAttribute(Constants.RecordTable.REF_NUM)+"'";
		String specimen = "'"+(String)r.getAttribute(Constants.RecordTable.SPECIMEN)+"'";
		String pathologist = "'"+(String)r.getAttribute(Constants.RecordTable.PATHOLOGIST)+"'";
		String physician = "'" + (String)r.getAttribute(Constants.RecordTable.PHYSICIAN) +"'";
		String diagnosis = "'"+(String)r.getAttribute(Constants.RecordTable.DIAGNOSIS)+"'";
		String remarks = "'"+(String)r.getAttribute(Constants.RecordTable.REMARKS)+"'";
		int recordType = (int)r.getAttribute(Constants.RecordTable.RECORD_TYPE);
		Calendar received = (Calendar)r.getAttribute(Constants.RecordTable.DATE_RECEIVED);
		Calendar completed = (Calendar)r.getAttribute(Constants.RecordTable.DATE_COMPLETED);
		
		String dateReceived = "'" + received.get(Calendar.YEAR) + 
				"-" + (received.get(Calendar.MONTH) + 1) + "-" + received.get(Calendar.DATE) + "'";
		String dateCompleted = "'" + completed.get(Calendar.YEAR) + 
				"-" + (completed.get(Calendar.MONTH) + 1) + "-" + completed.get(Calendar.DATE) + "'";
		

		String sql = null;
		if(get(record) == null)
			sql = "Insert into Records(" + Constants.RecordTable.PATIENT_ID + ", "+ Constants.RecordTable.REF_NUM  +
					", " + Constants.RecordTable.SPECIMEN + ", " + Constants.RecordTable.PATHOLOGIST + ", " +
					Constants.RecordTable.PHYSICIAN + ", " + Constants.RecordTable.DIAGNOSIS + ", " + 
					Constants.RecordTable.REMARKS + ", " + Constants.RecordTable.DATE_RECEIVED +", " + 
					Constants.RecordTable.DATE_COMPLETED + ", " + Constants.RecordTable.RECORD_TYPE + ") value (" 
					+ patientId + ", " + refNum + ", " + specimen + ", " + pathologist + ", " + physician + ", " + 
					diagnosis + ", " + remarks + ", " + dateReceived + ", "+ dateCompleted + "," + recordType + ")";
		else sql = "Update records set "  + 
				Constants.RecordTable.SPECIMEN + " = " + specimen +", " + 
				Constants.RecordTable.PATHOLOGIST + " = " + pathologist + ", " + 
				Constants.RecordTable.PHYSICIAN + " = " + physician + ", "+
				Constants.RecordTable.DIAGNOSIS + " =  " + diagnosis + ", " +
				Constants.RecordTable.REMARKS   + " = " + remarks + ", " + 
				Constants.RecordTable.DATE_RECEIVED +" = " + dateReceived + ", "+
				Constants.RecordTable.DATE_COMPLETED + " = " + dateCompleted + 
					" WHERE " + Constants.RecordTable.REF_NUM + " = " + refNum;
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Preferences.serverAddress +
					Preferences.databaseName, Preferences.username, Preferences.password);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println(sql);
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