package org.introse.gui.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.introse.Constants.ActionConstants;
import org.introse.Constants.DictionaryConstants;
import org.introse.Constants.StyleConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomDocument;
import org.introse.gui.button.TabButton;
import org.introse.gui.event.CustomListener;
import org.introse.gui.event.ListListener;
import org.introse.gui.event.TabListener;

public class DictionaryPanel extends JPanel implements FocusListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel topPanel, cardPanel, buttonPanel;
	private JTextField textField;
	private JButton addButton;
	private ListPanel pathologistPanel, physicianPanel, specimenPanel;
	private TabButton pathologistButton, physicianButton, specimenButton;
	private String currentView;
	
	public DictionaryPanel() 
	{
		super(new GridBagLayout());
		setBackground(new Color(0f,0f,0f,0f));
		initUI();
		layoutComponents();
		currentView = TitleConstants.PATHOLOGISTS;
	}
	
	private void initUI()
	{
		buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		pathologistButton = new TabButton(StyleConstants.PRIMARY_COLOR, StyleConstants.SECONDARY_COLOR, 
				TitleConstants.PATHOLOGISTS, true);
		physicianButton = new TabButton(StyleConstants.PRIMARY_COLOR, StyleConstants.SECONDARY_COLOR,
				TitleConstants.PHYSICIANS, false);
		specimenButton = new TabButton(StyleConstants.PRIMARY_COLOR, StyleConstants.SECONDARY_COLOR,
				TitleConstants.SPECIMENS, false);
		pathologistButton.setName(TitleConstants.PATHOLOGISTS);
		physicianButton.setName(TitleConstants.PHYSICIANS);
		specimenButton.setName(TitleConstants.SPECIMENS);
		cardPanel = new JPanel(new CardLayout());
		cardPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		topPanel = new JPanel(new GridBagLayout());
		topPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		textField = new JTextField(50);
		textField.setForeground(Color.GRAY);
		textField.addFocusListener(this);
		addButton = new JButton();
		addButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_new.png")));
		addButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_new_hover.png")));
		addButton.setContentAreaFilled(false);
		addButton.setBorderPainted(false);
		pathologistPanel = new ListPanel(SwingConstants.HORIZONTAL, 24, 12);
		physicianPanel = new ListPanel(SwingConstants.HORIZONTAL, 24, 12);
		specimenPanel = new ListPanel(SwingConstants.HORIZONTAL, 24, 12);
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
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.gridwidth = 3;
		c.insets = new Insets(0,0,10,5);
		topPanel.add(textField, c);
		c.gridx = 3;
		c.gridy = y++;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,10,0);
		topPanel.add(addButton, c);
		c.weightx = 1.0;	
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridy = y;
		c.insets = new Insets(0,0,0,0);
		topPanel.add(buttonPanel, c);
		
		cardPanel.add(TitleConstants.PATHOLOGISTS, pathologistPanel);
		cardPanel.add(TitleConstants.PHYSICIANS, physicianPanel);
		cardPanel.add(TitleConstants.SPECIMENS, specimenPanel);
		
		y = 0;
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.ipadx = 15;
		buttonPanel.add(pathologistButton, c);
		JSeparator divider1 = new JSeparator(SwingConstants.VERTICAL);
		divider1.setPreferredSize(new Dimension(1,20));
		c.gridx = 1;
		c.ipadx = 0;
		buttonPanel.add(divider1, c);
		c.gridx = 2;
		c.ipadx = 20;
		buttonPanel.add(physicianButton, c);
		JSeparator divider2 = new JSeparator(SwingConstants.VERTICAL);
		divider2.setPreferredSize(divider1.getPreferredSize());
		c.gridx = 3;
		c.ipadx = 0;
		buttonPanel.add(divider2, c);
		c.gridx = 4;
		c.ipadx = 15;
		c.insets = new Insets(0,0,0,0);
		buttonPanel.add(specimenButton, c);
		
		y = 0;
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 1.0;
		c.gridy = y++;
		add(topPanel, c);
		
		JSeparator divider = new JSeparator(SwingConstants.HORIZONTAL);
		divider.setPreferredSize(new Dimension(20, 1));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = y++;
		c.insets = new Insets(0,0,15,0);
		add(divider, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridy = y;
		c.weighty = 1.0;
		add(cardPanel, c);
	}
	
	public void addListListener(ListListener listener)
	{
		pathologistPanel.addMouseListener(listener);
		physicianPanel.addMouseListener(listener);
		specimenPanel.addMouseListener(listener);
	}
	
	public void addTabListener(TabListener listener)
	{
		pathologistButton.addMouseListener(listener);
		physicianButton.addMouseListener(listener);
		specimenButton.addMouseListener(listener);
	}
	
	public void addButtonListener(CustomListener listener)
	{
		addButton.addActionListener(listener);
		addButton.setActionCommand(ActionConstants.ADD_WORD);
		pathologistPanel.addButtonListener(listener);
		physicianPanel.addButtonListener(listener);
		specimenPanel.addButtonListener(listener);
	}
	
	public ListPanel getPanel(String view)
	{
		switch(view)
		{
		case TitleConstants.PATHOLOGISTS: return pathologistPanel;
		case TitleConstants.PHYSICIANS: return physicianPanel;
		case TitleConstants.SPECIMENS: return specimenPanel;
		}
		return null;
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
	
	public void setSelectedTab(String name)
	{
		currentView = name;
		CardLayout cl = (CardLayout)cardPanel.getLayout();
		cl.show(cardPanel, name);
		pathologistButton.setState(false);
		physicianButton.setState(false);
		specimenButton.setState(false);
		if(name.equals(pathologistButton.getName()))
			pathologistButton.setState(true);
		else if(name.equals(physicianButton.getName()))
			physicianButton.setState(true);
		else if(name.equals(specimenButton.getName()))
			specimenButton.setState(true);
	}
	
	public String getCurrentView()
	{
		return currentView;
	}
}
