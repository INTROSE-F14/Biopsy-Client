package org.introse.gui.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import org.introse.Constants;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JButton actionButton;

	public LoginWindow() {
		initUI();
	}
	
	public void initUI()
	{ 
		setTitle("Login - Biopsy Client");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel label = new JLabel("");
		label.setBounds(5, 128, 784, 63);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(LoginWindow.class.getResource("/res/icons/title.png")));
		contentPane.add(label);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.decode("#3498db"));
		passwordField.setBorder(null);
		passwordField.setFont(new Font("/res/fonts/calibri.ttf", Font.PLAIN, 17));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBackground(Color.decode("#ecf0f1"));
		
		passwordField.setBounds(new Rectangle(0, 0, 273, 47));
		passwordField.setBounds(213, 243, 273, 47);
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		actionButton = new JButton("");
		actionButton.setBorder(null);
		actionButton.setRequestFocusEnabled(false);
		actionButton.setRolloverIcon(new ImageIcon(LoginWindow.class.getResource("/res/icons/btnLoginHover.png")));
		actionButton.setIcon(new ImageIcon(LoginWindow.class.getResource("/res/icons/btnLogin.png")));
		actionButton.setBounds(485, 243, 97, 47);
		contentPane.add(actionButton);
	}
	
	public void showGUI()
	{
		setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
	}
	
	public void hideGUI()
	{
		setVisible(false);
	}
	
	public void exit()
	{
		dispose();
	}
	
	public void addListener(ActionListener listener)
	{
		actionButton.addActionListener(listener);
		actionButton.setActionCommand(Constants.ActionConstants.LOG_IN);
	}
	
	public String getPassword()
	{
		return new String(passwordField.getPassword());
	}
}
