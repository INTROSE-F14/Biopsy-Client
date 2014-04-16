package org.introse.gui.dialogbox;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.introse.Constants.StyleConstants;
import org.introse.gui.window.LoginWindow;


public class LoadingDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame parent;
	private JPanel coverupPanel, pane;
	private JLabel messageLabel;
	private String message;
	
	public LoadingDialog(JFrame parent, String title, String message)
	{
		super(parent, title, ModalityType.TOOLKIT_MODAL);
		this.parent = parent;
		this.message = message;
		setUndecorated(true);
		setBackground(new Color(0f, 0f, 0f, 0.1f));
		initUI();
		layoutComponents();
		setContentPane(coverupPanel);
	}
	
	private void initUI()
	{
		coverupPanel = new JPanel(new GridBagLayout());
		coverupPanel.setOpaque(true);
		coverupPanel.setBackground(new Color(0f, 0f, 0f, 0.0f));
		pane = new JPanel(new GridBagLayout());
		pane.setBackground(Color.white);
		pane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		messageLabel = new JLabel(message);
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		messageLabel.setOpaque(true);
		messageLabel.setBackground(Color.white);
		messageLabel.setIconTextGap(20);
		messageLabel.setIcon(new ImageIcon(getClass().getResource("/res/icons/refreshing.gif")));
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridy = 0;
		c.insets = new Insets(0,20,0,20);
		c.gridy = 1;
		c.insets = new Insets(20,20,20,20);
		pane.add(messageLabel, c);
	
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		coverupPanel.add(pane, c);
	}
	
	
	public void showGui()
	{
		setSize(parent.getSize());
		int x = parent.getLocation().x + (parent.getWidth() / 2);
		int y = parent.getLocation().y + (parent.getHeight() / 2);
		x -= getWidth() / 2;
		y -= getHeight() / 2;
		setLocation(x, y);
		setVisible(true);
	}
}
