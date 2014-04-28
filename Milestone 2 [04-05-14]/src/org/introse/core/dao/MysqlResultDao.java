package org.introse.core.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import org.introse.Constants;
import org.introse.Constants.ResultsTable;
import org.introse.Constants.RecordTable;
import org.introse.core.Result;
import org.introse.core.Record;

public class MysqlResultDao extends MysqlDao implements ResultDao {

	
	@Override
	public List<Result> getDiagnosis(Record record) 
	{
		List<Result> matches = new Vector<Result>();
		Statement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String sql = "Select * from " + ResultsTable.TABLE_NAME + " WHERE " + Constants.ResultsTable.RECORD_TYPE+ " = \"" 
		+ record.getAttribute(RecordTable.RECORD_TYPE) + "\"" + " AND " + ResultsTable.RECORD_YEAR + " = " + 
				record.getAttribute(RecordTable.RECORD_YEAR) + " AND " + ResultsTable.RECORD_NUMBER + " = " + 
		record.getAttribute(RecordTable.RECORD_NUMBER);

		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				int recordNumber = (int)record.getAttribute(Constants.RecordTable.RECORD_NUMBER);
				int recordYear = (int)record.getAttribute(RecordTable.RECORD_YEAR);
				char recordType = ((char)record.getAttribute(RecordTable.RECORD_TYPE));
				int category = result.getInt(ResultsTable.CATEGORY_ID);
				String value = result.getString(ResultsTable.VALUE);
				Result diagnosis = new Result(category, value, recordType,
						recordYear, recordNumber);
				matches.add(diagnosis);
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
	
	@Override
	public void add(Result diagnosis)
	{
		String recordType = "\""+diagnosis.getRecordType()+"\"";
		int recordYear = diagnosis.getRecordYear();
		int recordNumber = diagnosis.getRecordNumber();
		String value =(String)diagnosis.getValue().replace("\"", "\\\"");
		value =  "\""+value+"\"";
		int category = (int)diagnosis.getCategory();
		
		String sql = "Insert into " + ResultsTable.TABLE_NAME + "(" + ResultsTable.CATEGORY_ID+ ", " + ResultsTable.RECORD_TYPE+
				", " + ResultsTable.RECORD_YEAR + ", " + ResultsTable.RECORD_NUMBER + ", " +
				ResultsTable.VALUE + ") value (" + category + ", " + recordType + ", " + recordYear + ", " + recordNumber +" , " + value + ")";
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
			} catch (SQLException e) {e.printStackTrace();}
		}
	}
	
	public void delete(Record record)
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "DELETE FROM " + ResultsTable.TABLE_NAME + " WHERE " + Constants.ResultsTable.RECORD_TYPE+ " = \"" 
		+ record.getAttribute(RecordTable.RECORD_TYPE) + "\"" + " AND " + ResultsTable.RECORD_YEAR + " = " + 
				record.getAttribute(RecordTable.RECORD_YEAR) + " AND " + ResultsTable.RECORD_NUMBER + " = " + 
		record.getAttribute(RecordTable.RECORD_NUMBER);
		try 
		{
			System.out.println(sql);
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
	public List<Result> getAll() 
	{
		List<Result> diagnosis = new Vector<Result>();
		Statement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String sql = "Select * from " + ResultsTable.TABLE_NAME;

		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				int recordNumber = result.getInt(ResultsTable.RECORD_NUMBER);
				int recordYear = result.getInt(ResultsTable.RECORD_YEAR);
				char recordType = result.getString(ResultsTable.RECORD_TYPE).charAt(0);
				int category = result.getInt(ResultsTable.CATEGORY_ID);
				String value = result.getString(ResultsTable.VALUE);
				Result curdiagnosis = new Result(category, value, recordType,
						recordYear, recordNumber);
				diagnosis.add(curdiagnosis);
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
		return diagnosis;
	}

	@Override
	public Result get(Result diagnosis) 
	{
		Result match = null;
		int category = diagnosis.getCategory();
		String recordType = "\"" + diagnosis.getRecordType() + "\"";
		int recordYear = diagnosis.getRecordYear();
		int recordNumber = diagnosis.getRecordNumber();
		Statement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String sql = "Select * from " + ResultsTable.TABLE_NAME + " WHERE " + ResultsTable.CATEGORY_ID + " = " +
		category + " AND " + ResultsTable.RECORD_TYPE + " = " + recordType + " AND " + 
		ResultsTable.RECORD_YEAR + " = " + recordYear + " AND " + 
		ResultsTable.RECORD_NUMBER + " = " + recordNumber;

		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			if(result.next())
			{
				recordNumber = result.getInt(ResultsTable.RECORD_NUMBER);
				recordYear = result.getInt(ResultsTable.RECORD_YEAR);
				char rType = result.getString(ResultsTable.RECORD_TYPE).charAt(0);
				category = result.getInt(ResultsTable.CATEGORY_ID);
				String value = result.getString(ResultsTable.VALUE);
				match = new Result(category, value, rType,
						recordYear, recordNumber);
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
		return match;
	}
	
	public void update(Result diagnosis)
	{
		int category = diagnosis.getCategory();
		String recordType = "\"" + diagnosis.getRecordType() + "\"";
		int recordYear = diagnosis.getRecordYear();
		int recordNumber = diagnosis.getRecordNumber();
		String value = "\""+ diagnosis.getValue() +"\"";
		
		String sql = "Update " + ResultsTable.TABLE_NAME + " set "  + 
				ResultsTable.VALUE + " = " + value  + 
				" WHERE " + ResultsTable.CATEGORY_ID + " = " +
				category + " AND " + ResultsTable.RECORD_TYPE + " = " + recordType + " AND " + 
				ResultsTable.RECORD_YEAR + " = " + recordYear + " AND " + 
				ResultsTable.RECORD_NUMBER + " = " + recordNumber;
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
			} catch (SQLException e) {e.printStackTrace();}
		}
	}
}
