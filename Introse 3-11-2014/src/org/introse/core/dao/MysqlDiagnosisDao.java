package org.introse.core.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import org.introse.Constants.DiagnosisTable;
import org.introse.Constants.RecordTable;
import org.introse.core.Diagnosis;
import org.introse.core.Record;

public class MysqlDiagnosisDao extends MysqlDao implements DiagnosisDao {

	@Override
	public List<Diagnosis> getDiagnosis(Record record) 
	{
		List<Diagnosis> matches = new Vector<Diagnosis>();
		String referenceNumber = "'" + (String)record.getAttribute(RecordTable.REF_NUM) + "'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from diagnosis WHERE " + DiagnosisTable.REF_NUM + " = " + referenceNumber;
		
		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				int category = result.getInt(DiagnosisTable.CATEGORY_ID);
				String value = result.getString(DiagnosisTable.VALUE);
				Diagnosis diagnosis = new Diagnosis(category, value, referenceNumber);
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
		String referenceNumber = "'" + (String)diagnosis.getReferenceNumber() + "'";
		String value = "'"+(String)diagnosis.getValue()+"'";
		int category = (int)diagnosis.getCategory();
		
		String sql = "Insert into Diagnosis(" + DiagnosisTable.CATEGORY_ID+ ", " + DiagnosisTable.REF_NUM +
				", " + DiagnosisTable.VALUE + ") value (" + category + ", " + referenceNumber+ ", " + value + ")";
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
	
	public void delete(Record record)
	{
		String referenceNumber = "'" + (String)record.getAttribute(RecordTable.REF_NUM) + "'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "DELETE FROM diagnosis WHERE " + DiagnosisTable.REF_NUM + " = " + referenceNumber;
		
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

}
