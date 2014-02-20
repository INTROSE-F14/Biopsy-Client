package org.introse.core.network;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;


public class Client 
{

	private final int PORT = 4444;
	private final int TIMEOUT = 5000;
	private String serverInfo;
	public static final int AUTHENTICATION_FAILED = 2;
	public static final int AUTHENTICATION_SUCCESSFUL = 1;
	public static final int SERVER_ERROR = 0;
	
	public String getServerInfo()
	{
		return serverInfo;
	}
	public int connectToServer(String password) 
	{
		int statusFlag = 0;
		//flags {0 - error connecting to server, 
		//1 - authorization successful, 2 - authorization failed}
		
		try
		{
			InetAddress serverAddress = findServer();
			if(serverAddress != null)
			{
				if(authorizeLogin(serverAddress, password))
					statusFlag = 1;
				else statusFlag = 2;
			}
		}catch(IOException e){statusFlag = 0;}
		return statusFlag;
	}
	private InetAddress findServer() throws SocketException, IOException
	{
		InetAddress serverAddress = null;
		System.out.println("Finding Server...");
		byte[] receivedData = new byte[256];
		try(DatagramSocket socket = new DatagramSocket(PORT);)
		{
			socket.setSoTimeout(TIMEOUT);
			DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);
			socket.receive(receivePacket);
			
			//Use received packet to get Server Info
			serverAddress = receivePacket.getAddress();
			System.out.println("Server:" + serverAddress);
		}
		return serverAddress;
	}
	
	private boolean authorizeLogin(InetAddress serverAddress, String password) throws IOException
	{
		Socket clientSocket = new Socket(serverAddress, PORT);
		PrintWriter out = null;
		BufferedReader in = null;
		
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out.println(password);
		String fromServer = null;
		while((fromServer = in.readLine()) != null)
		{
			System.out.println(fromServer);
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
