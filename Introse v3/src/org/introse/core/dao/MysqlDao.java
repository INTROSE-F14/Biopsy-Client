package org.introse.core.dao;

import java.sql.Connection;
import java.sql.SQLException;


public interface MysqlDao
{	
	public Connection createConnection() throws ClassNotFoundException, SQLException;
	
	
}