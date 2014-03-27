import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JButton;


public class ServerUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI frame = new ServerUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerUI() {
		setTitle("Server Module");
		setResizable(false);
		setBounds(new Rectangle(0, 0, 400, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBounds(new Rectangle(0, 0, 400, 300));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setIcon(new ImageIcon(ServerUI.class.getResource("/res/icons/titleSmall.png")));
		lblTitle.setBounds(10, 42, 424, 27);
		contentPane.add(lblTitle);
		
		JPanel pnlStatus = new JPanel();
		pnlStatus.setBounds(120, 80, 200, 100);
		contentPane.add(pnlStatus);
		pnlStatus.setLayout(null);
		
		JLabel lblServer = new JLabel("Server IP:");
		lblServer.setBounds(10, 11, 180, 14);
		pnlStatus.add(lblServer);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(10, 36, 180, 14);
		pnlStatus.add(lblStatus);
		
		JButton btnActive = new JButton("");
		btnActive.setBorderPainted(false);
		btnActive.setIcon(new ImageIcon(ServerUI.class.getResource("/res/icons/btnActiveDisabled.png")));
		btnActive.setBounds(120, 191, 100, 40);
		contentPane.add(btnActive);
		
		JButton btnInactive = new JButton("");
		btnInactive.setIcon(new ImageIcon(ServerUI.class.getResource("/res/icons/btnInactiveEnabled.png")));
		btnInactive.setBorderPainted(false);
		btnInactive.setBounds(220, 191, 100, 40);
		contentPane.add(btnInactive);
	}
}
