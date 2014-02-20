package org.introse.core;

/*
	Class that stores a database information

*/

public class Database {

	private String serverIP;
	private int serverPort;
	private String serverUsername;
	private String databaseName;
	private String serverPassword;
	
	public Database(String ip, String username, String password, String dbName, int port)
	{
		serverIP = ip;
		serverUsername = username;
		serverPassword = password;
		databaseName = dbName;
		serverPort = port;
	}
	
	public String getUsername()
	{
		return serverUsername;
	}
	
	public String getPassword()
	{
		return serverPassword;
	}
	
	public String getURL()
	{
		return "jdbc:mysql:/" + serverIP + ":" + serverPort + "/";
	}
	
	public String getDatabaseName()
	{
		return databaseName;
	}
}
