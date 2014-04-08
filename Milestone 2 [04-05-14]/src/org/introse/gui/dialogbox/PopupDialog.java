package org.introse.gui.dialogbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.introse.Constants;
import org.introse.Constants.StyleConstants;
import org.introse.gui.window.LoginWindow;


public class PopupDialog extends JDialog implements ActionListener, MouseListener
{
	private int popupType;
	public static final int SIMPLE_POPUP = 0;
	public static final int YES_NO_POPUP = 1;
	
	private JFrame parent;
	private JPanel coverupPanel, pane;
	private JLabel messageLabel, titleLabel;
	private JButton positiveButton, negativeButton;
	private String positiveOption, negativeOption;
	private String message, title;
	
	public PopupDialog(JFrame parent, String title, String message, String positiveOption, 
			String negativeOption)
	{
		super(parent, title, ModalityType.TOOLKIT_MODAL);
		popupType = YES_NO_POPUP;
		this.parent = parent;
		this.message = message;
		this.title = title;
		this.positiveOption = positiveOption;
		this.negativeOption = negativeOption;
		setUndecorated(true);
		setBackground(new Color(0f, 0f, 0f, 0.5f));
		initYesNoUI();
		layoutYesNoComponents();
		setContentPane(coverupPanel);
	}
	public PopupDialog(JFrame parent, String title, String message, String positiveOption)
	{
		super(parent, title, ModalityType.TOOLKIT_MODAL);
		popupType = SIMPLE_POPUP;
		this.parent = parent;
		this.message = message;
		this.title = title;
		this.positiveOption = positiveOption;
		setUndecorated(true);
		setBackground(new Color(0f, 0f, 0f, 0.5f));
		initSimpleUI();
		layoutSimpleComponents();
		setContentPane(coverupPanel);
	}
	
	private void initYesNoUI()
	{
		coverupPanel = new JPanel(new GridBagLayout());
		coverupPanel.setOpaque(true);
		coverupPanel.setBackground(new Color(0f, 0f, 0f, 0.5f));
		pane = new JPanel(new GridBagLayout());
		pane.setBackground(Color.white);
		pane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		messageLabel = new JLabel(message);
		titleLabel = new JLabel(title);
		titleLabel.setOpaque(true);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		messageLabel.setFont(titleLabel.getFont().deriveFont(StyleConstants.MENU));
		titleLabel.setOpaque(true);
		messageLabel.setOpaque(true);
		titleLabel.setBackground(Color.white);
		messageLabel.setBackground(Color.white);
		positiveButton = new JButton(positiveOption);
		positiveButton.setOpaque(true);
		positiveButton.setBackground(Color.white);
		positiveButton.addActionListener(this);
		positiveButton.addMouseListener(this);
		positiveButton.setContentAreaFilled(false);
		positiveButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		positiveButton.setBorderPainted(true);
		positiveButton.setFocusPainted(false);
		positiveButton.setFont(messageLabel.getFont());
		
		negativeButton = new JButton(negativeOption);
		negativeButton.setOpaque(true);
		negativeButton.setBackground(Color.white);
		negativeButton.addActionListener(this);
		negativeButton.addMouseListener(this);
		negativeButton.setContentAreaFilled(false);
		negativeButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		negativeButton.setBorderPainted(true);
		negativeButton.setFocusPainted(false);
		negativeButton.setFont(messageLabel.getFont());
	}
	
	private void initSimpleUI()
	{
		coverupPanel = new JPanel(new GridBagLayout());
		coverupPanel.setOpaque(true);
		coverupPanel.setBackground(new Color(0f, 0f, 0f, 0.5f));
		pane = new JPanel(new GridBagLayout());
		pane.setBackground(Color.white);
		pane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		messageLabel = new JLabel(message);
		titleLabel = new JLabel(title);
		titleLabel.setOpaque(true);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		messageLabel.setFont(titleLabel.getFont().deriveFont(StyleConstants.MENU));
		titleLabel.setOpaque(true);
		messageLabel.setOpaque(true);
		titleLabel.setBackground(Color.white);
		messageLabel.setBackground(Color.white);
		positiveButton = new JButton(positiveOption);
		positiveButton.setOpaque(true);
		positiveButton.setBackground(Color.white);
		positiveButton.addActionListener(this);
		positiveButton.addMouseListener(this);
		positiveButton.setContentAreaFilled(false);
		positiveButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		positiveButton.setBorderPainted(true);
		positiveButton.setFocusPainted(false);
		positiveButton.setFont(messageLabel.getFont());
	}
	
	private void layoutSimpleComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridy = 0;
		c.insets = new Insets(0,20,0,20);
//		pane.add(titleLabel, c);
		c.gridy = 1;
		c.insets = new Insets(20,20,20,20);
		pane.add(messageLabel, c);
		c.gridy = 2;
		c.insets = new Insets(0,0,0,0);
		pane.add(positiveButton, c);
	
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		coverupPanel.add(pane, c);
	}
	
	private void layoutYesNoComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(0,20,0,20);
//		pane.add(titleLabel, c);
		c.gridy = 1;
		c.insets = new Insets(20,20,20,20);
		pane.add(messageLabel, c);
		c.gridy = 2;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,0);
		pane.add(positiveButton, c);
		c.gridx = 1;
		pane.add(negativeButton, c);
	
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

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		if(source.equals(positiveButton))
			firePropertyChange("POSITIVE", null, null);
		else firePropertyChange("NEGATIVE", null, null);
		dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		JButton button = (JButton)e.getSource();
		button.setOpaque(true);
		button.setBackground(Color.decode(Constants.StyleConstants.HOVER));
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		JButton button = (JButton)e.getSource();
		button.setOpaque(true);
		button.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		JButton button = (JButton)e.getSource();
		button.setOpaque(true);
		button.setBackground(Color.decode(Constants.StyleConstants.PRESSED));
	}
}
