import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;


public class ClientProtocol implements Runnable{
	
	private BufferedReader in;
	private PrintWriter out;
	private Socket hostSocket;
	private Server server;
	
	public ClientProtocol(Socket clientSocket, Server server) 
	{
		this.server = server;
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
					else if(fromHost.equals("NEW_UPDATE"))
					{
						fromHost = in.readLine();
						Date date = new Date(Long.parseLong(fromHost));
						server.setLastUpdate(date);
					}
					else if(fromHost.equals("CHECK_UPDATE"))
					{
						out.println(server.getLastUpdate().getTime());
					}
					else if(fromHost.equals("LOGIN"))
					{
						fromHost = in.readLine();
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
