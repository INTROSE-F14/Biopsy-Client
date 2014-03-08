package org.introse.core.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.introse.Constants;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.core.CustomCalendar;
import org.introse.core.CytologyRecord;
import org.introse.core.GynecologyRecord;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Record;

public class MysqlRecordDao extends MysqlDao implements RecordDao
{
	@Override
	public List<Record> getAll() 
	{
		List<Record> records = new Vector<Record>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from records";
		try 
		{
			conn = createConnection();
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
				record.putAttribute(Constants.RecordTable.REMARKS,
						result.getString(Constants.RecordTable.REMARKS));
				record.putAttribute(Constants.RecordTable.ROOM, 
						result.getString(RecordTable.ROOM));
				Calendar dateReceived = Calendar.getInstance();
				Calendar dateCompleted = Calendar.getInstance();
				dateCompleted.clear();
				dateCompleted.setTime(result.getDate(Constants.RecordTable.DATE_COMPLETED));
				dateReceived.clear();
				dateReceived.setTime(result.getDate(Constants.RecordTable.DATE_RECEIVED));
				
				CustomCalendar dR = new CustomCalendar();
				CustomCalendar dC = new CustomCalendar();
				dR.set(dateReceived.get(Calendar.MONTH), dateReceived.get(Calendar.DATE), dateReceived.get(Calendar.YEAR));
				dC.set(dateCompleted.get(Calendar.MONTH), dateCompleted.get(Calendar.DATE), dateCompleted.get(Calendar.YEAR));
				record.putAttribute(Constants.RecordTable.DATE_COMPLETED, dC);
				record.putAttribute(Constants.RecordTable.DATE_RECEIVED, dR);
				records.add(record);
			}
			return records;
			
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
		return null;
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
	public Record get(Record record)
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from records WHERE " + Constants.RecordTable.REF_NUM + " = '" 
		+ record.getAttribute(RecordTable.REF_NUM) + "'";

		try 
		{
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			if(result.next())
			{
				record = null;
				int recordType = result.getInt(Constants.RecordTable.RECORD_TYPE);
				switch(recordType)
				{
					case Constants.RecordConstants.HISTOPATHOLOGY_RECORD:
						record = new HistopathologyRecord();
						break;
					case RecordConstants.GYNECOLOGY_RECORD:
						record = new GynecologyRecord();
						break;
					case RecordConstants.CYTOLOGY_RECORD: 
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
				record.putAttribute(Constants.RecordTable.REMARKS,
						result.getString(Constants.RecordTable.REMARKS));
				record.putAttribute(Constants.RecordTable.ROOM, 
						result.getString(Constants.RecordTable.ROOM));
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
				record.putAttribute(Constants.RecordTable.DATE_COMPLETED, dC);
				record.putAttribute(Constants.RecordTable.DATE_RECEIVED, dR);
				return record;
			}
		} catch (ClassNotFoundException | SQLException e) 
		{
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
					
			} catch (SQLException e) 
			{e.printStackTrace();}
		}
		return null;
	}

	
	//the whole list of data will be first checked if there 
	//is an update and retrieved using getAll()
	//why: record to be deleted might not be in the list anymore
	//(deleted already by another computer prior to your deletion)
	@Override
	public void delete(Record record) 
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "DELETE * FROM RECORDS WHERE " + Constants.RecordTable.REF_NUM + " = '" 
		+ record.getAttribute(RecordTable.REF_NUM) + "'";

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
					
			} catch (SQLException e) 
			{e.printStackTrace();}
		}
	}

	@Override
	public List<Record> search(Record record) 
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from records WHERE ";
		int whereCount = 0;
		
		Object patientId;
		if((patientId = record.getAttribute(Constants.RecordTable.PATIENT_ID)) != null)
		{
			sql = sql.concat(RecordTable.PATIENT_ID + " = " + (int)patientId);
			whereCount++;
		}
		
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		String refNum;
		if((refNum = (String)record.getAttribute(Constants.RecordTable.REF_NUM)) != null)
		{
			sql = sql.concat(RecordTable.REF_NUM + " LIKE '%" + refNum + "%'");
			whereCount++;
		}
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		String specimen;
		if((specimen = (String)record.getAttribute(Constants.RecordTable.SPECIMEN)) != null)
		{
			sql = sql.concat(RecordTable.SPECIMEN + " LIKE '%" + specimen +"%'");
			whereCount++;
		}
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		String pathologist;
		if((pathologist = (String)record.getAttribute(Constants.RecordTable.PATHOLOGIST)) != null)
		{
			sql = sql.concat(RecordTable.PATHOLOGIST + " LIKE '%" + pathologist + "%'");
			whereCount++;
		}
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		String physician;
		if((physician = (String)record.getAttribute(Constants.RecordTable.PHYSICIAN)) != null)
		{
			sql = sql.concat(RecordTable.PHYSICIAN + " LIKE '%" + physician + "%'");
			whereCount++;
		}
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		String room;
		if((room = (String)record.getAttribute(Constants.RecordTable.ROOM)) != null)
		{
			sql = sql.concat(RecordTable.ROOM + " LIKE '%" + room + "%'");
			whereCount++;
		}
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		Object recordType;
		if((recordType = record.getAttribute(Constants.RecordTable.RECORD_TYPE)) != null)
		{
			sql = sql.concat(RecordTable.RECORD_TYPE + " = " + (int)recordType);
			whereCount++;
		}
		
		CustomCalendar received = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_RECEIVED);
		CustomCalendar completed = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_COMPLETED);
		
		if(received != null)
		{
			String receivedString = RecordTable.DATE_RECEIVED + " LIKE '";
			if(received.getYear() != -1)
				receivedString = receivedString.concat("" + received.getYear() + "-");
			else receivedString = receivedString.concat("%-");
			if(received.getMonth() != -1)
			{
				if(received.getMonth() > 9)
				receivedString = receivedString.concat("" + received.getMonth() + "-");
				else receivedString = receivedString.concat("0" + received.getMonth() + "-");
			}
			else receivedString = receivedString.concat("%-");
			if(received.getDay() != -1)
			{
				if(received.getDay() > 9)
				receivedString = receivedString.concat("" + received.getDay() + "'");
				else receivedString = receivedString.concat("0" + received.getDay() + "'");
			}
			else receivedString = receivedString.concat("%'");
			if(whereCount > 0)
			{
				sql = sql.concat(" AND ");
				whereCount--;
			}
			sql = sql.concat(receivedString);
			whereCount++;
		}
		
		if(completed != null)
		{
			String completedString = RecordTable.DATE_COMPLETED + " LIKE '";
			if(completed.getYear() != -1)
				completedString = completedString.concat("" + completed.getYear() + "-");
			else completedString = completedString.concat("%-");
			if(completed.getMonth() != -1)
			{
				if(completed.getMonth() > 9)
				completedString = completedString.concat("" + completed.getMonth() + "-");
				else completedString = completedString.concat("0" + completed.getMonth() + "-");
			}
			else completedString = completedString.concat("%-");
			if(completed.getDay() != -1)
			{
				if(completed.getDay() > 9)
				completedString = completedString.concat("" + completed.getDay() + "'");
				else completedString = completedString.concat("0" + completed.getDay() + "'");
			}
			else completedString = completedString.concat("%'");
			if(whereCount > 0)
			{
				sql = sql.concat(" AND ");
				whereCount--;
			}
			sql = sql.concat(completedString);
			whereCount++;
		}
		List<Record> records = new Vector<Record>();
		
		try 
		{
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			System.out.println(sql);
			while(result.next())
			{
				int type = result.getInt(Constants.RecordTable.RECORD_TYPE);
				switch(type)
				{
					case Constants.RecordConstants.HISTOPATHOLOGY_RECORD:
						record = new HistopathologyRecord();
						break;
					case RecordConstants.GYNECOLOGY_RECORD:
						record = new GynecologyRecord();
						break;
					case RecordConstants.CYTOLOGY_RECORD: 
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
				record.putAttribute(Constants.RecordTable.REMARKS,
						result.getString(Constants.RecordTable.REMARKS));
				Calendar dateReceived = Calendar.getInstance();
				Calendar dateCompleted = Calendar.getInstance();
				dateCompleted.clear();
				dateCompleted.setTime(result.getDate(Constants.RecordTable.DATE_COMPLETED));
				dateReceived.clear();
				dateReceived.setTime(result.getDate(Constants.RecordTable.DATE_RECEIVED));
				
				CustomCalendar dR = new CustomCalendar();
				CustomCalendar dC = new CustomCalendar();
				dR.set(dateReceived.get(Calendar.MONTH), dateReceived.get(Calendar.DATE), dateReceived.get(Calendar.YEAR));
				dC.set(dateCompleted.get(Calendar.MONTH), dateCompleted.get(Calendar.DATE), dateCompleted.get(Calendar.YEAR));
				record.putAttribute(Constants.RecordTable.DATE_COMPLETED, dC);
				record.putAttribute(Constants.RecordTable.DATE_RECEIVED, dR);
				records.add(record);
			}
			return records;
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
					
			} catch (SQLException e) 
			{e.printStackTrace();}
		}
		return null;
	}
	
	@Override
	public void add(Record record)
	{
		int patientId = (int)record.getAttribute(Constants.RecordTable.PATIENT_ID);
		String refNum = "'"+(String)record.getAttribute(Constants.RecordTable.REF_NUM)+"'";
		String specimen = "'"+(String)record.getAttribute(Constants.RecordTable.SPECIMEN)+"'";
		String pathologist = "'"+(String)record.getAttribute(Constants.RecordTable.PATHOLOGIST)+"'";
		String physician = "'" + (String)record.getAttribute(Constants.RecordTable.PHYSICIAN) +"'";
		String remarks = "'"+(String)record.getAttribute(Constants.RecordTable.REMARKS)+"'";
		int recordType = (int)record.getAttribute(Constants.RecordTable.RECORD_TYPE);
		CustomCalendar received = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_RECEIVED);
		CustomCalendar completed = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_COMPLETED);
		String room = (String)record.getAttribute(RecordTable.ROOM);
		if (room != null)
			room = "'" + room + "'";
		String dateReceived = "'" + received.getYear() + 
				"-" + (received.getMonth() + 1) + "-" + received.getDay() + "'";
		String dateCompleted = "'" + completed.getYear() + 
				"-" + (completed.getMonth() + 1) + "-" + completed.getDay() + "'";
		

		String sql = "Insert into Records(" + Constants.RecordTable.PATIENT_ID + ", "+ Constants.RecordTable.REF_NUM  +
					", " + Constants.RecordTable.SPECIMEN + ", " + Constants.RecordTable.PATHOLOGIST + ", " +
					Constants.RecordTable.PHYSICIAN + ", " + Constants.RecordTable.REMARKS + ", " + 
					Constants.RecordTable.DATE_RECEIVED +", " + Constants.RecordTable.DATE_COMPLETED + ", " + 
					Constants.RecordTable.RECORD_TYPE + ", " + RecordTable.ROOM + ") value (" + patientId + ", " + refNum + ", " + 
					specimen + ", " + pathologist + ", " + physician + ", " + remarks + ", " + 
					dateReceived + ", "+ dateCompleted + "," + recordType + ", " + room + ")";
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
	public void update(Record record) 
	{
		String refNum = "'"+(String)record.getAttribute(Constants.RecordTable.REF_NUM)+"'";
		String specimen = "'"+(String)record.getAttribute(Constants.RecordTable.SPECIMEN)+"'";
		String pathologist = "'"+(String)record.getAttribute(Constants.RecordTable.PATHOLOGIST)+"'";
		String physician = "'" + (String)record.getAttribute(Constants.RecordTable.PHYSICIAN) +"'";
		String remarks = "'"+(String)record.getAttribute(Constants.RecordTable.REMARKS)+"'";
		String room = (String)record.getAttribute(RecordTable.ROOM);
		if(room != null)
			room = "'" + room + "'";
		CustomCalendar received = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_RECEIVED);
		CustomCalendar completed = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_COMPLETED);
		
		String dateReceived = "'" + received.getYear() + 
				"-" + (received.getMonth() + 1) + "-" + received.getDay() + "'";
		String dateCompleted = "'" + completed.getYear() + 
				"-" + (completed.getMonth() + 1) + "-" + completed.getDay() + "'";

		String sql = "Update records set "  + 
				Constants.RecordTable.SPECIMEN + " = " + specimen +", " + 
				Constants.RecordTable.PATHOLOGIST + " = " + pathologist + ", " + 
				Constants.RecordTable.PHYSICIAN + " = " + physician + ", "+
				Constants.RecordTable.REMARKS   + " = " + remarks + ", " + 
				Constants.RecordTable.DATE_RECEIVED +" = " + dateReceived + ", "+
				Constants.RecordTable.DATE_COMPLETED + " = " + dateCompleted + ", " + 
				RecordTable.ROOM + " = " + room +
					" WHERE " + Constants.RecordTable.REF_NUM + " = " + refNum;
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
