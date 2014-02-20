

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientProtocol implements Runnable{
	
	private BufferedReader in;
	private PrintWriter out;
	private Socket hostSocket;
	
	public ClientProtocol(Socket clientSocket) 
	{
		this.hostSocket = clientSocket;
		try
		{
			in = new BufferedReader(new InputStreamReader(hostSocket.getInputStream()));
			out = new PrintWriter(hostSocket.getOutputStream(), true);
		}catch(IOException e){e.printStackTrace();}
	}
	
	@Override
	public void run() 
	{	
		try
		{
			while(!hostSocket.isClosed())
			{
				String fromHost = in.readLine();
				if(fromHost != null)
				{
					
					if(fromHost.equals("DONE"))
					{
						in.close();
						out.close();
						hostSocket.close();
					}
					else
					{
						if(authorizeLogin(fromHost))
						{
							out.println(Server.DB_USERNAME + ":" + Server.DB_PASSWORD + ":" + 
									Server.DB_NAME + ":" + Server.DB_PORT);
						}else out.println(false);
					}
				}
			}
		}catch(IOException ioe){ioe.printStackTrace();}
	}
	
	public boolean authorizeLogin(String password)
	{
		if(Server.SERVER_PASSWORD.equals(password))
			return true;
		return false;
	}
}
