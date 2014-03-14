import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class ServerController implements ItemListener
{

	private Server server;
	private JFrame window;
	private JComboBox<String> statusBox;
	
	public ServerController(Server server) 
	{
		this.server = server;
		try {
			initUI();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initUI() throws UnknownHostException
	{
		window = new JFrame("Server Module");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel logPanel = new JPanel(new GridBagLayout());
		logPanel.setBorder(new EmptyBorder(20,20,20,20));
		JLabel serverIPLabel = new JLabel("Server IP: ");
		JLabel serverIP = new JLabel(InetAddress.getLocalHost().getHostAddress());
		JLabel serverStatusLabel = new JLabel("Status: ");
		String[] serverStatuses = {"Inactive", "Active"};
		statusBox = new JComboBox<String>(serverStatuses);
		statusBox.addItemListener(this);
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.LINE_START;
		logPanel.add(serverIPLabel, gc);
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.LINE_START;
		logPanel.add(serverIP, gc);
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.LINE_START;
		logPanel.add(serverStatusLabel, gc);
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 2;
		gc.insets = new Insets(0,10,0,10);
		gc.anchor = GridBagConstraints.LINE_START;
		logPanel.add(statusBox, gc);
		window.setContentPane(logPanel);
		window.pack();
	}
	
	
	public void showGui()
	{
		window.setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent ie) 
	{
		
		int stateChange = ie.getStateChange();
		if(stateChange == ItemEvent.SELECTED)
		{
			if(ie.getItem().toString().equalsIgnoreCase("active"))
			{
				if(!server.openServer())
				{
					statusBox.setSelectedIndex(0);
					new ErrorDialog("Server Error", "Server failed to open").showGui();
				}
			}
			else server.closeServer();
		}
		
	}
}
