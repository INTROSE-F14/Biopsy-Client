package org.introse.gui.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.introse.Constants.ActionConstants;
import org.introse.Constants.TitleConstants;
import org.introse.gui.event.CustomListener;
import org.introse.gui.event.ListListener;

public class DictionaryPanel extends JPanel implements FocusListener
{
	
	private JTextField textField;
	private JButton addButton;
	private ListPanel wordPanel;
	
	public DictionaryPanel() 
	{
		super(new GridBagLayout());
		setBackground(Color.white);
		initUI();
		layoutComponents();
	}
	
	private void initUI()
	{
		textField = new JTextField(TitleConstants.DICTIONARY_HINT, 50);
		textField.setForeground(Color.GRAY);
		textField.addFocusListener(this);
		addButton = new JButton("add");
		wordPanel = new ListPanel(SwingConstants.VERTICAL, 100, 0);
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c= new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		int y = 0;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,0);
		add(textField, c);
		c.gridx = 1;
		c.gridy = y++;
		c.weightx = 1.0;
		c.insets = new Insets(0,5,10,0);
		add(addButton, c);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = y;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(0,0,0,0);
		add(wordPanel, c);
	}
	
	public void addListListener(ListListener listener)
	{
		wordPanel.addMouseListener(listener);
	}
	
	public void addButtonListener(CustomListener listener)
	{
		addButton.addActionListener(listener);
		addButton.setActionCommand(ActionConstants.ADD_WORD);
	}

	public ListPanel getWordPanel()
	{
		return wordPanel;
	}
	
	public String getWord()
	{
		return textField.getText();
	}

	@Override
	public void focusGained(FocusEvent e) 
	{
		if(textField.getText().equals(TitleConstants.DICTIONARY_HINT))
		{
			textField.setText("");
			textField.setForeground(Color.black);
		}
	}

	@Override
	public void focusLost(FocusEvent e) 
	{
		if(textField.getText().length() == 0)
		{
			textField.setText(TitleConstants.DICTIONARY_HINT);
			textField.setForeground(Color.GRAY);
		}
	}
	
	public void reset()
	{
		textField.setText(TitleConstants.DICTIONARY_HINT);
		textField.setForeground(Color.gray);
	}
}
