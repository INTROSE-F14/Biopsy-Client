package org.introse.core;

/*
 * Class Name: Preferences
 * Brief Description: 
 * 	Class used to load and store program settings
 * 
 * Attributes:
 * 	serverAddress - (String) address of the database server
 * 	databaseName - (String) name of the database
 * 	SA_PREFIX -(String) prefix used to indicate the server address in the properties file
 * 	DN_PREFIX - (String) prefix used to indicate the database name in the properties file
 * 
 * Methods: 
 * 	setPreferences (File)
 * 	setPreferences (String, String, String, String)
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public abstract class Preferences 
{
	public static String serverAddress;
	public static String databaseName;
	public static String username;
	public static String password;
	private static final String SA_PREFIX = "server_url";
	private static final String DN_PREFIX = "db_name";
	
	public static void setPreferences(File properties)
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(properties));
			String line = null;
			while((line = reader.readLine()) != null)
			{
				if(line.startsWith(SA_PREFIX))
					serverAddress = line.substring(line.indexOf(":") + 1);
				else if(line.startsWith(DN_PREFIX))
					databaseName = line.substring(line.indexOf(":") + 1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setDatabase(Database db)
	{
		serverAddress = db.getURL();
		databaseName = db.getDatabaseName();
		username = db.getUsername();
		password = db.getPassword();
	}
}
