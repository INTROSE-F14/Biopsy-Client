package org.introse.gui.button;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.introse.Constants;
import org.introse.gui.window.LoginWindow;


public class NavButton extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isSelected;
	private String selectedColor;
	private JLabel indicator;
	private JLabel title;
	
	public NavButton(String normalColor, String selectedColor, String text, boolean isSelected)
	{
		super(new GridBagLayout());
		setOpaque(true);
		setBackground(Color.decode(normalColor));
		this.isSelected = isSelected;
		this.selectedColor = selectedColor;
		indicator = new JLabel("  ");
		indicator.setOpaque(true);
		indicator.setBackground(getBackground());
		title = new JLabel(text);
		title.setOpaque(true);
		title.setBackground(getBackground());
		title.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(10,10,10,5);
		add(title, c);
		c.gridx = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0,0,0,0);
		add(indicator, c);
		setState(isSelected);
	}
	
	public void setState(boolean isSelected)
	{
		this.isSelected = isSelected;
		if(isSelected)
		{
			indicator.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_indicator_selected.png")));
			title.setForeground(Color.decode(selectedColor));
		}
		else
		{
			indicator.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_indicator_unselected.png")));
			title.setForeground(Color.GRAY);
		}
	}
	
	public boolean getState()
	{
		return isSelected;
	}
	
	@Override
	public void setBackground(Color color)
	{
		super.setBackground(color);
		if(title!= null)
			title.setBackground(color);
		if(indicator!= null)
			indicator.setBackground(getBackground());
	}
}
