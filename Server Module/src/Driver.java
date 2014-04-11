import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Driver
{
	private static Server server = new Server();
	private static ServerController sc;
	public static void main(String[] args)
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGui();
			}
		});
	
	}
	
	public static void createAndShowGui()
	{
		sc = new ServerController(server);
		sc.showGui();
	}
}