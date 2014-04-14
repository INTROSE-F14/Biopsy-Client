package org.introse.gui.button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;


public class NavButton extends JButton implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isSelected;
	private String normalColor;
	private String hoverColor;
	private String pressedColor;
	private String selectedColor;
	private String text;
	public NavButton(String normalColor, String hoverColor, 
			String pressedColor, String selectedColor, String text, boolean isSelected)
	{
		super(text);
		setOpaque(true);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		this.normalColor = normalColor;
		this.hoverColor = hoverColor;
		this.pressedColor = pressedColor;
		this.selectedColor = selectedColor;
		this.text = text;
		setState(isSelected);
		addMouseListener(this);
	}
	
	public void setState(boolean isSelected)
	{
		this.isSelected = isSelected;
		if(isSelected)
		{
			setBackground(Color.decode(selectedColor));
			setForeground(Color.white);
		}
		else
		{
			setBackground(Color.decode(normalColor));
			setForeground(Color.black);
		}
	}
	
	public boolean getState()
	{
		return isSelected;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if(!((NavButton)e.getComponent()).getState())
		{
			e.getComponent().setBackground(Color.decode(hoverColor));
			e.getComponent().setForeground(Color.black);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		setState(getState());	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!((NavButton)e.getComponent()).getState())
		{
			e.getComponent().setBackground(Color.decode(pressedColor));
			e.getComponent().setForeground(Color.black);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		Insets insets = getInsets();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(text, 0 + insets.left, 0 + getFontMetrics(getFont()).getHeight());
	}
}
