package org.introse.core.network;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

import org.introse.ProjectDriver;


public class Client 
{

	private final int PORT = 4444;
	private final int TIMEOUT = 5000;
	private String serverInfo;
	public static final int AUTHENTICATION_FAILED = 2;
	public static final int AUTHENTICATION_SUCCESSFUL = 1;
	public static final int SERVER_ERROR = 0;
	private InetAddress serverAddress;
	private Date lastUpdate;
	
	public Client()
	{
		lastUpdate = new Date();
	}
	
	public String getServerInfo()
	{
		return serverInfo;
	}
	public int connectToServer(String password) 
	{
		int statusFlag = SERVER_ERROR;
		try
		{
			serverAddress = findServer();
			if(serverAddress != null)
			{
				if(authorizeLogin(serverAddress, password))
					statusFlag = AUTHENTICATION_SUCCESSFUL;
				else statusFlag = AUTHENTICATION_FAILED;
			}
		}catch(IOException e){statusFlag = SERVER_ERROR; e.printStackTrace();}
		return statusFlag;
	}
	
	private InetAddress findServer() throws SocketException, IOException
	{
		InetAddress serverAddress = null;
		System.out.println("Finding Server...");
		byte[] receivedData = new byte[256];
		try(DatagramSocket s = new DatagramSocket(null);)
		{
			s.setReuseAddress(true);
			s.bind(new InetSocketAddress(PORT));
			s.setSoTimeout(TIMEOUT);
			DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);
			s.receive(receivePacket);
			
			//Use received packet to get Server Info
			serverAddress = receivePacket.getAddress();
			System.out.println("Server:" + serverAddress);
			s.close();
		}
		return serverAddress;
	}
	
	private boolean authorizeLogin(InetAddress serverAddress, String password) throws IOException
	{
		try(Socket clientSocket = new Socket(serverAddress, PORT);)
		{
			PrintWriter out = null;
			BufferedReader in = null;
			
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out.println("LOGIN");
			out.println(password);
			String fromServer = null;
			while((fromServer = in.readLine()) != null)
			{
				if(fromServer.equals("false"))
					out.println("DONE");
				else
				{
					serverInfo = fromServer; 
					serverInfo = serverInfo.concat(":" + serverAddress);
					out.println("DONE");
					if(in != null)
						in.close();
					if(out != null)
						out.close();
					if(clientSocket != null)
						clientSocket.close();
					return true;
				}
			}
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(clientSocket != null)
				clientSocket.close();
			return false;
		}
	}
	
	public void sendUpdate() throws IOException
	{
		try(Socket clientSocket = new Socket(serverAddress, PORT);)
		{
			PrintWriter out = null;
			BufferedReader in = null;
			
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out.println("NEW_UPDATE");
			lastUpdate = new Date();
			out.println(lastUpdate.getTime());
			out.println("DONE");
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(clientSocket != null)
				clientSocket.close();
		}
	}
	
	public void getUpdate()
	{
		try(Socket clientSocket = new Socket(serverAddress, PORT);)
		{
			PrintWriter out = null;
			BufferedReader in = null;
			
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			out.println("CHECK_UPDATE");
			String fromHost = in.readLine();
			lastUpdate = new Date(Long.parseLong(fromHost));
			out.println("DONE");
			
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(clientSocket != null)
				clientSocket.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//returns true if there is an update;
	public boolean isUpdateAvailable() 
	{
		boolean isUpdateAvailable = false;
		
		try(Socket clientSocket = new Socket(serverAddress, PORT);)
		{
			PrintWriter out = null;
			BufferedReader in = null;
			
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			out.println("CHECK_UPDATE");
			String fromHost = in.readLine();
			if(lastUpdate.getTime() < Long.parseLong(fromHost))
				isUpdateAvailable = true;
			out.println("DONE");
			
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(clientSocket != null)
				clientSocket.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return isUpdateAvailable;
	}
}
