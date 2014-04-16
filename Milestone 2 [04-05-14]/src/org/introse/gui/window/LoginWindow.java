package org.introse.gui.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.introse.Constants;
import org.introse.Constants.TitleConstants;
import org.introse.core.Preferences;
import org.introse.gui.event.CustomListener;

public class LoginWindow extends JFrame implements KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JButton actionButton;
	public static Font PRIMARY_FONT;
	public static Font SECONDARY_FONT;
	public static BufferedImage PROGRAM_ICON;
	
	public LoginWindow() 
	{
		super(TitleConstants.LOGIN_WINDOW);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.4), 
				(int)(Preferences.getScreenHeight() * 0.5)));
		try
		{
			PRIMARY_FONT = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/res/fonts/calibri.ttf"));
			SECONDARY_FONT = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/res/fonts/calibri_light.ttf"));
			ImageIcon programIcon = new ImageIcon(getClass().getResource("/res/icons/ic_launcher.png"));
			PROGRAM_ICON = new BufferedImage(programIcon.getIconWidth(), programIcon.getIconHeight(), 
					BufferedImage.TYPE_INT_ARGB);
			programIcon.paintIcon(null, PROGRAM_ICON.getGraphics(), 0, 0);
		}catch (FontFormatException | IOException e) {e.printStackTrace();}
		setIconImage(PROGRAM_ICON);
		initUI();
	}
	
	public void initUI()
	{ 
		contentPane = new JPanel(new GridBagLayout());
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(20,20,20,20));
		setContentPane(contentPane);
		JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(LoginWindow.class.getResource("/res/icons/ic_banner.png")));
		
		passwordField = new JPasswordField(20);
		passwordField.setFont(PRIMARY_FONT.deriveFont(20f));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon loginIconRollover = new ImageIcon(getClass().getResource("/res/icons/ic_login_hover.png"));
		ImageIcon loginIconNormal = new ImageIcon(getClass().getResource("/res/icons/ic_login_normal.png"));
		actionButton = new JButton();
		actionButton.setBorderPainted(false);
		actionButton.setContentAreaFilled(false);
		actionButton.setRequestFocusEnabled(false);
		actionButton.setRolloverIcon(loginIconRollover);
		actionButton.setIcon(loginIconNormal);
		actionButton.setPreferredSize(new Dimension(loginIconNormal.getIconWidth(), loginIconNormal.getIconHeight()));
		actionButton.setEnabled(false);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(20,20,60,20);
		contentPane.add(label, c);
		c.anchor = GridBagConstraints.LINE_END;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1.0;
		c.gridwidth = 2;
		c.gridy = 1;
		c.insets = new Insets(0,20,20,0);
		contentPane.add(passwordField, c);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		c.gridx = 2;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,20,20);
		contentPane.add(actionButton, c);
	}
	
	public void showGUI()
	{
		pack();
		setVisible(true);
		setResizable(false);
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
	
	public void addListener(CustomListener listener)
	{
		actionButton.addActionListener(listener);
		actionButton.setActionCommand(Constants.ActionConstants.LOG_IN);
		passwordField.addKeyListener(this);
	}
	
	public String getPassword()
	{
		return new String(passwordField.getPassword());
	}
	
	public void setLoginButtonEnabled(boolean isEnabled)
	{
		actionButton.setEnabled(isEnabled);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(passwordField.getPassword().length > 0)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				actionButton.requestFocusInWindow();
				actionButton.doClick();
			}
			else actionButton.setEnabled(true);
		}
		else actionButton.setEnabled(false);
	} 
}
