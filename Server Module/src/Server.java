

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
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
	private Date lastUpdate;
	
	public Server()
	{
		status = INACTIVE;
		lastUpdate = new Date();
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
		try(DatagramSocket broadcastSocket = new DatagramSocket(null);)
		{
			broadcastSocket.setReuseAddress(true);
			broadcastSocket.bind(new InetSocketAddress(PORT));
			broadcastSocket.setBroadcast(true);
			while(status == ACTIVE)
			{
				broadcastSocket.send(broadcastPacket);
				Thread.sleep(3000);
			}
			broadcastSocket.close();
		}
	}	
	
	private void acceptConnections() throws IOException
	{
		try(ServerSocket serverSocket = new ServerSocket(PORT);)
		{
			serverSocket.setSoTimeout(SOCKET_TIMEOUT);
			while(status == ACTIVE)
			{
				try
				{
					Socket hostSocket = serverSocket.accept();
					ClientProtocol tempCP = new ClientProtocol(hostSocket, this);
					Thread temp = new Thread(tempCP);
					temp.start();
				}
				catch(SocketTimeoutException ste){}
			}
			serverSocket.close();
		}
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
		try 
		{
			final InetAddress address = findBroadcastAddress();
			if(address != null)
			{
				setStatus(Server.ACTIVE);
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
				System.out.println("broadcast address not found");
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
	
	public void setLastUpdate(Date date)
	{
		this.lastUpdate = date;
	}
	
	public Date getLastUpdate()
	{
		return lastUpdate;
	}
}