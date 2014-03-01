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

import org.introse.core.CytologyRecord;
import org.introse.core.GynecologyRecord;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.core.RecordTable;
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
				int recordType = result.getInt(RecordTable.RECORD_TYPE.toString());
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
				record.putAttribute(RecordTable.PATIENT_ID.toString(), 
						result.getInt(RecordTable.PATIENT_ID.toString()));
				record.putAttribute(RecordTable.REF_NUM.toString(), 
						result.getString(RecordTable.REF_NUM.toString()));
				record.putAttribute(RecordTable.RECORD_TYPE.toString(),
						result.getInt(RecordTable.RECORD_TYPE.toString()));
				record.putAttribute(RecordTable.PATHOLOGIST.toString(), 
						result.getString(RecordTable.PATHOLOGIST.toString()));
				record.putAttribute(RecordTable.PHYSICIAN.toString(),
						result.getString(RecordTable.PHYSICIAN.toString()));
				record.putAttribute(RecordTable.SPECIMEN.toString(), 
						result.getString(RecordTable.SPECIMEN.toString()));
				record.putAttribute(RecordTable.DIAGNOSIS.toString(), 
						result.getString(RecordTable.DIAGNOSIS.toString()));
				record.putAttribute(RecordTable.REMARKS.toString(),
						result.getString(RecordTable.REMARKS.toString()));
				Calendar dateReceived = Calendar.getInstance();
				Calendar dateCompleted = Calendar.getInstance();
				dateCompleted.clear();
				dateCompleted.setTime(result.getDate(RecordTable.DATE_COMPLETED.toString()));
				dateReceived.clear();
				dateReceived.setTime(result.getDate(RecordTable.DATE_RECEIVED.toString()));
				record.putAttribute(RecordTable.DATE_COMPLETED.toString(), dateCompleted);
				record.putAttribute(RecordTable.DATE_RECEIVED.toString(), dateReceived);
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
		int patientId = (int)r.getAttribute(RecordTable.PATIENT_ID.toString());
		String refNum = "'"+(String)r.getAttribute(RecordTable.REF_NUM.toString())+"'";
		String specimen = "'"+(String)r.getAttribute(RecordTable.SPECIMEN.toString())+"'";
		String pathologist = "'"+(String)r.getAttribute(RecordTable.PATHOLOGIST.toString())+"'";
		String physician = "'" + (String)r.getAttribute(RecordTable.PHYSICIAN.toString()) +"'";
		String diagnosis = "'"+(String)r.getAttribute(RecordTable.DIAGNOSIS.toString())+"'";
		String remarks = "'"+(String)r.getAttribute(RecordTable.REMARKS.toString())+"'";
		int recordType = (int)r.getAttribute(RecordTable.RECORD_TYPE.toString());
		Calendar received = (Calendar)r.getAttribute(RecordTable.DATE_RECEIVED.toString());
		Calendar completed = (Calendar)r.getAttribute(RecordTable.DATE_COMPLETED.toString());
		
		String dateReceived = "'" + received.get(Calendar.YEAR) + 
				"-" + (received.get(Calendar.MONTH) + 1) + "-" + received.get(Calendar.DATE) + "'";
		String dateCompleted = "'" + completed.get(Calendar.YEAR) + 
				"-" + (completed.get(Calendar.MONTH) + 1) + "-" + completed.get(Calendar.DATE) + "'";
		

		String sql = null;
		if(get(record) == null)
			sql = "Insert into Records(" + RecordTable.PATIENT_ID.toString() + ", "+ RecordTable.REF_NUM.toString()  +
					", " + RecordTable.SPECIMEN.toString()   + ", " + RecordTable.PATHOLOGIST.toString()   + ", " +
					RecordTable.PHYSICIAN.toString() + ", " + RecordTable.DIAGNOSIS.toString() + ", " + 
					RecordTable.REMARKS.toString()   + ", " + RecordTable.DATE_RECEIVED.toString() +", " + 
					RecordTable.DATE_COMPLETED.toString() + ", " + RecordTable.RECORD_TYPE.toString() + ") value (" 
					+ patientId + ", " + refNum + ", " + specimen + ", " + pathologist + ", " + physician + ", " + 
					diagnosis + ", " + remarks + ", " + dateReceived + ", "+ dateCompleted + "," + recordType + ")";
		else sql = "Update records set "  + 
					RecordTable.SPECIMEN.toString() + " = " + specimen +", " + 
					RecordTable.PATHOLOGIST.toString() + " = " + pathologist + ", " + 
					RecordTable.PHYSICIAN.toString() + " = " + physician + ", "+
					RecordTable.DIAGNOSIS.toString() + " =  " + diagnosis + ", " +
					RecordTable.REMARKS.toString()   + " = " + remarks + ", " + 
					RecordTable.DATE_RECEIVED.toString() +" = " + dateReceived + ", "+
					RecordTable.DATE_COMPLETED.toString() + " = " + dateCompleted + 
					" WHERE " + RecordTable.REF_NUM.toString() + " = " + refNum;
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