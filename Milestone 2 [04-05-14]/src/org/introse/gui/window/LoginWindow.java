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
	private JLabel loadingStatus;
	public static Font PRIMARY_FONT;
	public static Font SECONDARY_FONT;
	public int isListening;
	
	public LoginWindow() 
	{
		super(TitleConstants.LOGIN_WINDOW);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.4), 
				(int)(Preferences.getScreenHeight() * 0.5)));
		initUI();
		try
		{
			PRIMARY_FONT = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/res/fonts/calibri.ttf"));
			SECONDARY_FONT = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/res/fonts/calibri_light.ttf"));
			
		}catch (FontFormatException | IOException e) {e.printStackTrace();}
		isListening = 0;
	}
	
	public void initUI()
	{ 
		contentPane = new JPanel(new GridBagLayout());
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(20,20,20,20));
		setContentPane(contentPane);
		JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(LoginWindow.class.getResource("/res/icons/title.png")));
		
		ImageIcon loadingIcon = new ImageIcon(getClass().getResource("/res/icons/loading.gif"));
		loadingStatus = new JLabel();
		loadingStatus.setIcon(loadingIcon);
		loadingStatus.setVerticalTextPosition(JLabel.TOP);
		loadingStatus.setHorizontalAlignment(JLabel.CENTER);
		loadingStatus.setVisible(false);
		passwordField = new JPasswordField(20);
		passwordField.setForeground(Color.decode("#3498db"));
		passwordField.setBorder(null);
		passwordField.setFont(new Font("/res/fonts/calibri.ttf", Font.PLAIN, 17));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBackground(Color.decode("#ecf0f1"));
		ImageIcon loginIconRollover = new ImageIcon(getClass().getResource("/res/icons/btnLoginHover.png"));
		ImageIcon loginIconNormal = new ImageIcon(getClass().getResource("/res/icons/btnLogin.png"));
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
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.insets = new Insets(0,20,0,20);
		contentPane.add(loadingStatus, c);
	}
	
	public void showGUI()
	{
		pack();
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
	
	public void setLoadingVisible(boolean isVisible)
	{
		loadingStatus.setVisible(isVisible);
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
			if(e.getKeyCode() == KeyEvent.VK_ENTER && isListening == 0)
				actionButton.doClick();
			else actionButton.setEnabled(true);
			if(isListening == 1)
				isListening = 0;
		}
		else actionButton.setEnabled(false);
	} 
	
	public void setListening(int isListening)
	{
		this.isListening = isListening;
	}
}
