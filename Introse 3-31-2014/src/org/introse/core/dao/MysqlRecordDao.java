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
import org.introse.Constants.TitleConstants;
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
		String sql = "Select * from " + TitleConstants.RECORDS;
		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				Record record = null;
				char recordType = result.getString(Constants.RecordTable.RECORD_TYPE).charAt(0);
				switch(recordType)
				{
				case RecordConstants.HISTOPATHOLOGY_RECORD: //histopathology
					record = new HistopathologyRecord();
					break;
				case RecordConstants.GYNECOLOGY_RECORD: //gynecology
					record = new GynecologyRecord();
					break;
				case RecordConstants.CYTOLOGY_RECORD: //cytology
					record = new CytologyRecord();
					break;
				}
				record.putAttribute(Constants.RecordTable.PATIENT_ID, 
						result.getInt(Constants.RecordTable.PATIENT_ID));
				record.putAttribute(Constants.RecordTable.RECORD_YEAR, 
						result.getInt(Constants.RecordTable.RECORD_YEAR));
				record.putAttribute(RecordTable.RECORD_NUMBER, 
						result.getInt(RecordTable.RECORD_NUMBER));
				record.putAttribute(Constants.RecordTable.RECORD_TYPE,
						result.getString(Constants.RecordTable.RECORD_TYPE));
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
				record.putAttribute(RecordTable.GROSS_DESC,
						result.getString(RecordTable.GROSS_DESC));
				record.putAttribute(RecordTable.MICRO_NOTE, 
						result.getString(RecordTable.MICRO_NOTE));
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
				record.putAttribute(Constants.RecordTable.SPEC_TYPE, result.getString(RecordTable.SPEC_TYPE));
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
		String sql = "Select * from records WHERE " + Constants.RecordTable.RECORD_TYPE+ " = \"" 
		+ record.getAttribute(RecordTable.RECORD_TYPE) + "\"" + " AND " + RecordTable.RECORD_YEAR + " = " + 
				record.getAttribute(RecordTable.RECORD_YEAR) + " AND " + RecordTable.RECORD_NUMBER + " = " + 
		record.getAttribute(RecordTable.RECORD_NUMBER);

		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			if(result.next())
			{
				record = null;
				char recordType = result.getString(Constants.RecordTable.RECORD_TYPE).charAt(0);
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
				record.putAttribute(Constants.RecordTable.RECORD_YEAR, 
						result.getInt(Constants.RecordTable.RECORD_YEAR));
				record.putAttribute(RecordTable.RECORD_NUMBER, 
						result.getInt(RecordTable.RECORD_NUMBER));
				record.putAttribute(Constants.RecordTable.RECORD_TYPE,
						result.getString(Constants.RecordTable.RECORD_TYPE));
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
				record.putAttribute(RecordTable.GROSS_DESC,
						result.getString(RecordTable.GROSS_DESC));
				record.putAttribute(RecordTable.MICRO_NOTE, 
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
				record.putAttribute(Constants.RecordTable.DATE_COMPLETED, dC);
				record.putAttribute(Constants.RecordTable.DATE_RECEIVED, dR);
				record.putAttribute(RecordTable.SPEC_TYPE, result.getString(RecordTable.SPEC_TYPE));
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
		String sql = "DELETE * FROM RECORDS WHERE " + Constants.RecordTable.RECORD_TYPE+ " = \"" 
				+ record.getAttribute(RecordTable.RECORD_TYPE) + "\"" + " AND " + RecordTable.RECORD_YEAR + " = " + 
						record.getAttribute(RecordTable.RECORD_YEAR) + " AND " + RecordTable.RECORD_NUMBER + " = " + 
				record.getAttribute(RecordTable.RECORD_NUMBER);
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
		String sql = "Select * from " + TitleConstants.RECORDS + " WHERE ";
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
		Object recordYear;
		if((recordYear = record.getAttribute(Constants.RecordTable.RECORD_YEAR)) != null)
		{
			sql = sql.concat(RecordTable.RECORD_YEAR + " = " + recordYear);
			whereCount++;
		}
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		Object recordNumber;
		if((recordNumber = record.getAttribute(Constants.RecordTable.RECORD_NUMBER)) != null)
		{
			sql = sql.concat(RecordTable.RECORD_NUMBER + " = " + recordNumber);
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
			specimen = specimen.replace("%", "\\%");
			sql = sql.concat(RecordTable.SPECIMEN + " LIKE \"%" + specimen +"%\"");
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
			pathologist = pathologist.replace("%", "\\%");
			sql = sql.concat(RecordTable.PATHOLOGIST + " LIKE \"%" + pathologist + "%\"");
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
			physician = physician.replace("%", "\\%");
			sql = sql.concat(RecordTable.PHYSICIAN + " LIKE \"%" + physician + "%\"");
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
			room = room.replace("%", "\\%");
			sql = sql.concat(RecordTable.ROOM + " LIKE \"%" + room + "%\"");
			whereCount++;
		}
		if(whereCount > 0)
		{
			sql = sql.concat(" AND ");
			whereCount--;
		}
		char recordType;
		if((record.getAttribute(Constants.RecordTable.RECORD_TYPE) != null))
		{
			recordType = (char)record.getAttribute(Constants.RecordTable.RECORD_TYPE);
			sql = sql.concat(RecordTable.RECORD_TYPE+ " = \"" + recordType + "\"");
			whereCount++;
		}
		
		CustomCalendar received = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_RECEIVED);
		CustomCalendar completed = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_COMPLETED);
		
		if(received != null)
		{
			String receivedString = RecordTable.DATE_RECEIVED + " LIKE \"";
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
				receivedString = receivedString.concat("" + received.getDay() + "%\"");
				else receivedString = receivedString.concat("0" + received.getDay() + "%\"");
			}
			else receivedString = receivedString.concat("%\"");
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
			String completedString = RecordTable.DATE_COMPLETED + " LIKE \"";
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
				completedString = completedString.concat("" + completed.getDay() + "%\"");
				else completedString = completedString.concat("0" + completed.getDay() + "%\"");
			}
			else completedString = completedString.concat("%\"");
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
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			while(result.next())
			{
				char type = result.getString(Constants.RecordTable.RECORD_TYPE).charAt(0);
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
				record.putAttribute(Constants.RecordTable.RECORD_YEAR, 
						result.getInt(Constants.RecordTable.RECORD_YEAR));
				record.putAttribute(RecordTable.RECORD_NUMBER, 
						result.getInt(RecordTable.RECORD_NUMBER));
				record.putAttribute(Constants.RecordTable.RECORD_TYPE,
						result.getString(Constants.RecordTable.RECORD_TYPE));
				record.putAttribute(Constants.RecordTable.PATHOLOGIST, 
						result.getString(Constants.RecordTable.PATHOLOGIST));
				record.putAttribute(Constants.RecordTable.PHYSICIAN,
						result.getString(Constants.RecordTable.PHYSICIAN));
				record.putAttribute(Constants.RecordTable.SPECIMEN, 
						result.getString(Constants.RecordTable.SPECIMEN));
				record.putAttribute(Constants.RecordTable.REMARKS,
						result.getString(Constants.RecordTable.REMARKS));
				record.putAttribute(RecordTable.ROOM, 
						result.getString(RecordTable.ROOM));
				record.putAttribute(RecordTable.GROSS_DESC,
						result.getString(RecordTable.GROSS_DESC));
				record.putAttribute(RecordTable.MICRO_NOTE, 
						result.getString(RecordTable.MICRO_NOTE));
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
				record.putAttribute(Constants.RecordTable.SPEC_TYPE, result.getString(RecordTable.SPEC_TYPE));
				records.add(record);
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
					
			} catch (SQLException e) 
			{e.printStackTrace();}
		}
		return records;
	}
	
	@Override
	public void add(Record record)
	{
		int patientId = (int)record.getAttribute(Constants.RecordTable.PATIENT_ID);
		String recordType = "\""+(char)record.getAttribute(Constants.RecordTable.RECORD_TYPE)+"\"";
		int recordYear = (int)record.getAttribute(RecordTable.RECORD_YEAR);
		int recordNumber = (int)record.getAttribute(RecordTable.RECORD_NUMBER);
		String specimen = "\""+(String)record.getAttribute(Constants.RecordTable.SPECIMEN)+"\"";
		String pathologist = "\""+(String)record.getAttribute(Constants.RecordTable.PATHOLOGIST)+"\"";
		String physician = "\"" + (String)record.getAttribute(Constants.RecordTable.PHYSICIAN) +"\"";
		String specimenType = "\""+ (String)record.getAttribute(RecordTable.SPEC_TYPE) +"\"";
		CustomCalendar received = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_RECEIVED);
		CustomCalendar completed = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_COMPLETED);
		//nullables
		String grossdesc = null;
		String micronote = null;
		String remarks = null;
		String room = null;
		if(record.getAttribute(RecordTable.ROOM)!= null)
			room = "\"" + record.getAttribute(RecordTable.ROOM) + "\"";
		if(record.getAttribute(RecordTable.MICRO_NOTE) != null)
			micronote = "\"" + record.getAttribute(RecordTable.MICRO_NOTE) + "\"";
		if(record.getAttribute(RecordTable.GROSS_DESC) != null)
			grossdesc = "\"" + record.getAttribute(RecordTable.GROSS_DESC) + "\"";
		if(record.getAttribute(Constants.RecordTable.REMARKS) != null)
			remarks = "\"" + record.getAttribute(Constants.RecordTable.REMARKS) + "\"";
		String dateReceived = "\"" + received.getYear() + 
				"-" + (received.getMonth() + 1) + "-" + received.getDay() + "\"";
		String dateCompleted = "\"" + completed.getYear() + 
				"-" + (completed.getMonth() + 1) + "-" + completed.getDay() + "\"";
		
		
		String sql = "Insert into Records(" + Constants.RecordTable.PATIENT_ID + ", "+ Constants.RecordTable.RECORD_YEAR+", "+
					RecordTable.RECORD_NUMBER + ", " + Constants.RecordTable.SPECIMEN + ", " + Constants.RecordTable.PATHOLOGIST + ", " +
					Constants.RecordTable.PHYSICIAN + ", " + Constants.RecordTable.REMARKS + ", " + 
					Constants.RecordTable.DATE_RECEIVED +", " + Constants.RecordTable.DATE_COMPLETED + ", " + 
					Constants.RecordTable.RECORD_TYPE + ", " + RecordTable.ROOM + ", " + RecordTable.SPEC_TYPE + ", " + 
					RecordTable.GROSS_DESC + ", " + RecordTable.MICRO_NOTE + ") value (" + patientId + ", " + recordYear + ", " + recordNumber + ", " + specimen + ", " + pathologist + ", " + 
					physician + ", " + remarks + ", " + dateReceived + ", "+ dateCompleted + "," + recordType + 
					", " + room + ", " + specimenType +", " + grossdesc + ", " + micronote + ")";
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		try 
		{
			System.out.println(sql);
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
		int recordNumber = (int)record.getAttribute(Constants.RecordTable.RECORD_NUMBER);
		int recordYear = (int)record.getAttribute(RecordTable.RECORD_YEAR);
		String recordType = "\""+(char)record.getAttribute(RecordTable.RECORD_TYPE)+"\"";
		String specimen = "\""+(String)record.getAttribute(Constants.RecordTable.SPECIMEN)+"\"";
		String pathologist = "\""+(String)record.getAttribute(Constants.RecordTable.PATHOLOGIST)+"\"";
		String physician = "\"" + (String)record.getAttribute(Constants.RecordTable.PHYSICIAN) +"\"";
		String specimenType = "\"" + (String)record.getAttribute(RecordTable.SPEC_TYPE) +"\"";
		//nullables
		String grossdesc = null;
		String micronote = null;
		String remarks = null;
		String room = null;
		if(record.getAttribute(RecordTable.ROOM)!= null)
			room = "\"" + record.getAttribute(RecordTable.ROOM) + "\"";
		if(record.getAttribute(RecordTable.MICRO_NOTE) != null)
			micronote = "\"" + record.getAttribute(RecordTable.MICRO_NOTE) + "\"";
		if(record.getAttribute(RecordTable.GROSS_DESC) != null)
			grossdesc = "\"" + record.getAttribute(RecordTable.GROSS_DESC) + "\"";
		if(record.getAttribute(Constants.RecordTable.REMARKS) != null)
			remarks = "\"" + record.getAttribute(Constants.RecordTable.REMARKS) + "\"";
		
		CustomCalendar received = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_RECEIVED);
		CustomCalendar completed = (CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_COMPLETED);
		
		String dateReceived = "\"" + received.getYear() + 
				"-" + (received.getMonth() + 1) + "-" + received.getDay() + "\"";
		String dateCompleted = "\"" + completed.getYear() + 
				"-" + (completed.getMonth() + 1) + "-" + completed.getDay() + "\"";

		String sql = "Update records set "  + 
				Constants.RecordTable.SPECIMEN + " = " + specimen +", " + 
				Constants.RecordTable.PATHOLOGIST + " = " + pathologist + ", " + 
				Constants.RecordTable.PHYSICIAN + " = " + physician + ", "+
				Constants.RecordTable.REMARKS   + " = " + remarks + ", " + 
				Constants.RecordTable.DATE_RECEIVED +" = " + dateReceived + ", "+
				Constants.RecordTable.DATE_COMPLETED + " = " + dateCompleted + ", " + 
				RecordTable.ROOM + " = " + room + ", " +
				RecordTable.SPEC_TYPE + " = " + specimenType + ", " + 
				RecordTable.GROSS_DESC + " = " + grossdesc + ", " + 
				RecordTable.MICRO_NOTE + " = " + micronote +
					" WHERE " + Constants.RecordTable.RECORD_TYPE + " = " + recordType + 
					" AND " + RecordTable.RECORD_YEAR + " = " + recordYear + " AND " +  
					RecordTable.RECORD_NUMBER + " = " + recordNumber;
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		try 
		{
			System.out.println(sql);
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
	
	public void assignReferenceNumber(Record record)
	{
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet result = null;
		ResultSet result2 = null;
		int max = 0;
		int count = 0;
		String sql1 = "SELECT MAX(" + RecordTable.RECORD_NUMBER + ") FROM " + TitleConstants.RECORDS + " WHERE " + 
		RecordTable.RECORD_TYPE + " = \"" + record.getAttribute(RecordTable.RECORD_TYPE) + "\" " + 
		" AND " + RecordTable.RECORD_YEAR + " = " + record.getAttribute(RecordTable.RECORD_YEAR);
		String sql2 = "SELECT COUNT(*) FROM " + TitleConstants.RECORDS + " WHERE " + 
				RecordTable.RECORD_TYPE + " = \"" + record.getAttribute(RecordTable.RECORD_TYPE) + "\" " + 
				" AND " + RecordTable.RECORD_YEAR + " = " + record.getAttribute(RecordTable.RECORD_YEAR);
		
		try 
		{
			System.out.println(sql1);
			conn = createConnection();
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			result = stmt.executeQuery(sql1);
			result2 = stmt2.executeQuery(sql2);
			if(result.next() && result2.next())
			{
				max = result.getInt("MAX(" + RecordTable.RECORD_NUMBER + ")");
				count = result2.getInt("COUNT(*)");
				if(count > 0)
					max++;
				record.putAttribute(RecordTable.RECORD_NUMBER, max);
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
	}
}