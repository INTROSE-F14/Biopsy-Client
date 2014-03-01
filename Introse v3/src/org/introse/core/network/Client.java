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

import org.introse.Constants;


public class Client 
{
	private String serverInfo;
	private InetAddress serverAddress;
	
	public String getServerInfo()
	{
		return serverInfo;
	}
	public int connectToServer(String password) 
	{
		int statusFlag = Constants.NetworkConstants.SERVER_ERROR;
		try
		{
			serverAddress = findServer();
			if(serverAddress != null)
			{
				if(authorizeLogin(serverAddress, password))
					statusFlag = Constants.NetworkConstants.AUTHENTICATION_SUCCESSFUL;
				else statusFlag = Constants.NetworkConstants.AUTHENTICATION_FAILED;
			}
		}catch(IOException e){statusFlag = Constants.NetworkConstants.SERVER_ERROR; e.printStackTrace();}
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
			s.bind(new InetSocketAddress(Constants.NetworkConstants.PORT));
			s.setSoTimeout(Constants.NetworkConstants.TIMEOUT);
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
		try(Socket clientSocket = new Socket(serverAddress, Constants.NetworkConstants.PORT);)
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
	
	public void sendLatestVersion(Date latestVersion) throws IOException
	{
		try(Socket clientSocket = new Socket(serverAddress, Constants.NetworkConstants.PORT);)
		{
			PrintWriter out = null;
			BufferedReader in = null;
			
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out.println("NEW_UPDATE");
			out.println(latestVersion.getTime());
			out.println("DONE");
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(clientSocket != null)
				clientSocket.close();
		}
	}
	
	public Date getLatestVersion()
	{
		Date lastUpdate = null;
		try(Socket clientSocket = new Socket(serverAddress, Constants.NetworkConstants.PORT);)
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
		return lastUpdate;
	}
	
	//returns true if there is an update;
	public boolean isUpdateAvailable(Date currentVersion) 
	{
		boolean isUpdateAvailable = false;
		
		try(Socket clientSocket = new Socket(serverAddress, Constants.NetworkConstants.PORT);)
		{
			PrintWriter out = null;
			BufferedReader in = null;
			
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			out.println("CHECK_UPDATE");
			String fromHost = in.readLine();
			if(currentVersion.getTime() < Long.parseLong(fromHost))
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
