package org.introse.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.introse.core.Preferences;


public abstract class MysqlDao
{	
	public Connection createConnection() throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(Preferences.serverAddress +
				Preferences.databaseName, Preferences.username, Preferences.password);
		return conn;
	}
}