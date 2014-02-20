import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainMenu 
{

	private JFrame frame;
	private JPanel mainPanel;
	
	public MainMenu()
	{
		frame = new JFrame("Main Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		frame.setContentPane(mainPanel);
		frame.pack();
	}
	
	public void showGUI() 
	{
		frame.setVisible(true);
	}
	
	public JFrame getFrame()
	{
		return frame;
	}

}
