package org.introse.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.introse.Constants.ActionConstants;
import org.introse.Constants.DictionaryConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomDocument;
import org.introse.core.Preferences;
import org.introse.gui.event.CustomListener;
import org.introse.gui.event.ListListener;

public class DictionaryPanel extends JPanel implements FocusListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		textField = new JTextField(50);
		textField.setForeground(Color.GRAY);
		textField.addFocusListener(this);
		addButton = new JButton("add");
		wordPanel = new ListPanel(SwingConstants.HORIZONTAL, 28, 14);
		textField.setDocument(new CustomDocument(DictionaryConstants.WORD_LENGTH));
		textField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(textField.getText().replace(TitleConstants.DICTIONARY_HINT, "").length() > 0)
					{
						addButton.doClick();
						addButton.requestFocusInWindow();
						reset();
					}
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		textField.setText(TitleConstants.DICTIONARY_HINT);
	}
	
	private void layoutComponents()
	{
		int y = 0;
		GridBagConstraints c= new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,20,5);
		add(textField, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,20,0);
		c.weightx = 1.0;
		add(addButton, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = y;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.gridwidth = 2;
		c.gridheight = 3;
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
		wordPanel.addButtonListener(listener);
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
			reset();
	}
	
	public void reset()
	{
		textField.setText(TitleConstants.DICTIONARY_HINT);
		textField.setForeground(Color.gray);
	}
}
