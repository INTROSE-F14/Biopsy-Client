package org.introse.core.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import org.introse.Constants.DictionaryTable;
import org.introse.Constants.TitleConstants;
import org.introse.core.DictionaryWord;

import ca.odell.glazedlists.BasicEventList;

public class MysqlDictionaryDao extends MysqlDao implements DictionaryDao
{

	@Override
	public void add(String word, int type) 
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		word = word.replace("\"", "\\\\");
		word = "\"" + word + "\"";
		String sql = "INSERT INTO " + TitleConstants.DICTIONARY +" VALUE " +
				"(" + word + ", " + type + ")";
				
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

	@Override
	public void delete(String word, int type) 
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		word = word.replace("\"", "\\\\");
		word = "\"" + word + "\"";
		String sql = "DELETE FROM " + TitleConstants.DICTIONARY +" WHERE " +
				DictionaryTable.WORD + " = " + word + " AND " +
				DictionaryTable.TYPE + " = " + type;
				
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

	@Override
	public List<DictionaryWord> getAll(int type) 
	{
		List<DictionaryWord> words = new Vector<DictionaryWord>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from " + TitleConstants.DICTIONARY +" WHERE " + 
		DictionaryTable.TYPE + " = " + type;
		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				String word = result.getString(DictionaryTable.WORD);
				words.add(new DictionaryWord(type, word));
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
			} catch (SQLException e) {e.printStackTrace();}
		}
		return words;
	}
	
	
	@Override
	public List<String> getWords(int type) 
	{
		List<String> words = new BasicEventList<String>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select * from " + TitleConstants.DICTIONARY +" WHERE " + 
		DictionaryTable.TYPE + " = " + type;
		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				words.add(result.getString(DictionaryTable.WORD));
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
			} catch (SQLException e) {e.printStackTrace();}
		}
		return words;
	}

	@Override
	public int getCount(int type) 
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select count(*) from " + TitleConstants.DICTIONARY +" WHERE " + 
		DictionaryTable.TYPE + " = " + type;
		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			if(result.next())
				return result.getInt("count(*)");
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
		return 0;
		
	}

	@Override
	public int getCount() 
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "Select count(*) from " + TitleConstants.DICTIONARY;
		try 
		{
			System.out.println(sql);
			conn = createConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			if(result.next())
				return result.getInt("count(*)");
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
		return 0;
		
	}
}
