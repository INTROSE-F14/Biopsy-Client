

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;




public class Server
{

	private static final int SOCKET_TIMEOUT = 1000;
	private static final int PORT = 4444;
	private static final int INACTIVE = 0;
	private static final int ACTIVE = 1;
	public static final String SERVER_PASSWORD = "1234";
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "password";
	public static final String DB_NAME = "database";
	public static final int DB_PORT = 3306;
	private int status;
	
	public Server()
	{
		status = INACTIVE;
	}
	
	private InetAddress findBroadcastAddress() throws SocketException 
	{
		Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
		for(NetworkInterface curNI : Collections.list(nis))
		{
			List<InterfaceAddress> addresses = curNI.getInterfaceAddresses();
			for(InterfaceAddress curIA : addresses)
			{
				InetAddress curAddress = curIA.getBroadcast();
				if(curAddress != null && !curAddress.isLoopbackAddress() && !curAddress.toString().equals("/0.0.0.0"))
					return curAddress;
			}
		}
		return null;
	}
	
	private void startBroadcast(InetAddress broadcastAddress) throws SocketException, UnknownHostException, IOException, InterruptedException
	{
		byte[] b = new byte[256];
		DatagramPacket broadcastPacket = new DatagramPacket(b, b.length, broadcastAddress, PORT);
		DatagramSocket broadcastSocket = new DatagramSocket(PORT);
		broadcastSocket.setBroadcast(true);
		while(status == ACTIVE)
		{
			broadcastSocket.send(broadcastPacket);
			Thread.sleep(1000);
		}	
		broadcastSocket.close();
	}	
	
	private void acceptConnections() throws IOException
	{
		ServerSocket serverSocket = new ServerSocket(PORT);
		serverSocket.setSoTimeout(SOCKET_TIMEOUT);
		while(status == ACTIVE)
		{
			try
			{
				Socket hostSocket = serverSocket.accept();
				ClientProtocol tempCP = new ClientProtocol(hostSocket);
				Thread temp = new Thread(tempCP);
				temp.start();
			}
			catch(SocketTimeoutException ste){}
		}
		serverSocket.close();
	}
	
	private synchronized void setStatus(int status)
	{
		this.status = status;
	}
	
	public void closeServer()
	{
		setStatus(Server.INACTIVE);
	}
	
	public boolean openServer()
	{
		setStatus(Server.ACTIVE);
		try 
		{
			final InetAddress address = findBroadcastAddress();
			if(address != null)
			{
				new Thread(new Runnable()
							{
								public void run()
								{
									try {startBroadcast(address);}
									catch (IOException | InterruptedException e) {e.printStackTrace();}
							}}).start();
				new Thread(new Runnable()
							{
								public void run()
								{
									try {acceptConnections();}
									catch (IOException e) {e.printStackTrace();}
								}}).start();	
				return true;
			}
			else
			{
				setStatus(Server.INACTIVE);
				return false;
			}
		} catch (SocketException e1) 
		{
			e1.printStackTrace();
			setStatus(Server.INACTIVE);
			return false;
		}
	}
}