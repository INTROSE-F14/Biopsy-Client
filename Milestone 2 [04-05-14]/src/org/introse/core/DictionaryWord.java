package org.introse.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.introse.Constants;
import org.introse.Constants.StyleConstants;
import org.introse.gui.panel.ListItem;
import org.introse.gui.window.LoginWindow;

public class DictionaryWord extends ListItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private String word;
	
	public DictionaryWord(int type, String word)
	{
		super();
		this.type = type;
		this.word = word;
		initializePanel();
	}
	
	@Override
	public void initializePanel() 
	{
		setPreferredSize(new Dimension((450), 30));
		setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		JLabel wordLabel = new JLabel(word);
		wordLabel.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.MENU));
		wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,20,0,0);
		add(wordLabel, c);
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.NONE;
		add(deleteButton, c);
	}
	
	public String getWord()
	{
		return word;
	}
	
	public int getType()
	{
		return type;
	}

}
