package org.introse.core.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import org.introse.Constants;
import org.introse.Constants.DiagnosisTable;
import org.introse.Constants.RecordTable;
import org.introse.core.Diagnosis;
import org.introse.core.Record;

public class MysqlDiagnosisDao extends MysqlDao implements DiagnosisDao {

	
	@Override
	public List<Diagnosis> getDiagnosis(Record record) 
	{
		List<Diagnosis> matches = new Vector<Diagnosis>();
		Statement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String sql = "Select * from diagnosis WHERE " + Constants.DiagnosisTable.RECORD_TYPE+ " = \"" 
		+ record.getAttribute(RecordTable.RECORD_TYPE) + "\"" + " AND " + DiagnosisTable.RECORD_YEAR + " = " + 
				record.getAttribute(RecordTable.RECORD_YEAR) + " AND " + DiagnosisTable.RECORD_NUMBER + " = " + 
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
				char recordType = ((String)record.getAttribute(RecordTable.RECORD_TYPE)).charAt(0);
				int category = result.getInt(DiagnosisTable.CATEGORY_ID);
				String value = result.getString(DiagnosisTable.VALUE);
				Diagnosis diagnosis = new Diagnosis(category, value, recordType,
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
				if(conn != null)
					conn.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
		return matches;
	}
	
	@Override
	public void add(Diagnosis diagnosis)
	{
		String recordType = "\""+diagnosis.getRecordType()+"\"";
		int recordYear = diagnosis.getRecordYear();
		int recordNumber = diagnosis.getRecordNumber();
		String value = "\""+(String)diagnosis.getValue()+"\"";
		int category = (int)diagnosis.getCategory();
		
		String sql = "Insert into Diagnosis(" + DiagnosisTable.CATEGORY_ID+ ", " + DiagnosisTable.RECORD_TYPE+
				", " + DiagnosisTable.RECORD_YEAR + ", " + DiagnosisTable.RECORD_NUMBER + ", " +
				DiagnosisTable.VALUE + ") value (" + category + ", " + recordType + ", " + recordYear + ", " + recordNumber +" , " + value + ")";
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
	
	public void delete(Record record)
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "DELETE FROM diagnosis WHERE " + Constants.DiagnosisTable.RECORD_TYPE+ " = \"" 
		+ record.getAttribute(RecordTable.RECORD_TYPE) + "\"" + " AND " + DiagnosisTable.RECORD_YEAR + " = " + 
				record.getAttribute(RecordTable.RECORD_YEAR) + " AND " + DiagnosisTable.RECORD_NUMBER + " = " + 
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
				if(conn != null)
					conn.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
	}

	@Override
	public List<Diagnosis> getAll() 
	{
		List<Diagnosis> diagnosis = new Vector<Diagnosis>();
		Statement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String sql = "Select * from diagnosis";

		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				int recordNumber = result.getInt(DiagnosisTable.RECORD_NUMBER);
				int recordYear = result.getInt(DiagnosisTable.RECORD_YEAR);
				char recordType = result.getString(DiagnosisTable.RECORD_TYPE).charAt(0);
				int category = result.getInt(DiagnosisTable.CATEGORY_ID);
				String value = result.getString(DiagnosisTable.VALUE);
				Diagnosis curdiagnosis = new Diagnosis(category, value, recordType,
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
				if(conn != null)
					conn.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
		return diagnosis;
	}

	@Override
	public Diagnosis get(Diagnosis diagnosis) 
	{
		Diagnosis match = null;
		int category = diagnosis.getCategory();
		String recordType = "\"" + diagnosis.getRecordType() + "\"";
		int recordYear = diagnosis.getRecordYear();
		int recordNumber = diagnosis.getRecordNumber();
		Statement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String sql = "Select * from diagnosis WHERE " + DiagnosisTable.CATEGORY_ID + " = " +
		category + " AND " + DiagnosisTable.RECORD_TYPE + " = " + recordType + " AND " + 
		DiagnosisTable.RECORD_YEAR + " = " + recordYear + " AND " + 
		DiagnosisTable.RECORD_NUMBER + " = " + recordNumber;

		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			if(result.next())
			{
				recordNumber = result.getInt(DiagnosisTable.RECORD_NUMBER);
				recordYear = result.getInt(DiagnosisTable.RECORD_YEAR);
				char rType = result.getString(DiagnosisTable.RECORD_TYPE).charAt(0);
				category = result.getInt(DiagnosisTable.CATEGORY_ID);
				String value = result.getString(DiagnosisTable.VALUE);
				match = new Diagnosis(category, value, rType,
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
				if(conn != null)
					conn.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
		return match;
	}
	
	public void update(Diagnosis diagnosis)
	{
		int category = diagnosis.getCategory();
		String recordType = "\"" + diagnosis.getRecordType() + "\"";
		int recordYear = diagnosis.getRecordYear();
		int recordNumber = diagnosis.getRecordNumber();
		String value = "\""+ diagnosis.getValue() +"\"";
		
		String sql = "Update diagnosis set "  + 
				DiagnosisTable.VALUE + " = " + value  + 
				" WHERE " + DiagnosisTable.CATEGORY_ID + " = " +
				category + " AND " + DiagnosisTable.RECORD_TYPE + " = " + recordType + " AND " + 
				DiagnosisTable.RECORD_YEAR + " = " + recordYear + " AND " + 
				DiagnosisTable.RECORD_NUMBER + " = " + recordNumber;
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
}
