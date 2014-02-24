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
import org.introse.core.PatientTable;
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
		String sql = "Select p." + PatientTable.LAST_NAME + ", p." + PatientTable.FIRST_NAME +
				", p." + PatientTable.MIDDLE_NAME + ", r.* from records r, patients p where "
						+ " r." + RecordTable.PATIENT_ID + " = p." + PatientTable.PATIENT_ID;
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
						result.getString(RecordTable.PATIENT_ID.toString()));
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
	
}