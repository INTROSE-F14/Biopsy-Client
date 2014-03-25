package org.introse.gui.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.introse.Constants.StyleConstants;
import org.introse.gui.window.MainMenu;

public class PageViewer extends JPanel implements ActionListener
{

	private JPanel contentPanel;
	private JButton nextButton;
	private JButton previousButton;
	private List<JPanel> pages;
	private List<String> titles;
	private JLabel titleLabel;
	private int pageCount;
	private int currentPage;
	
	public PageViewer(List<JPanel> pages, List<String> titles, int defaultPage)
	{
		super(new BorderLayout());
		setBackground(Color.white);
		this.pages = pages;
		this.titles = titles;
		pageCount = pages.size();
		currentPage = defaultPage;
		contentPanel = new JPanel(new CardLayout());
		nextButton = new JButton();
		previousButton = new JButton();
		nextButton.addActionListener(this);
		previousButton.addActionListener(this);
		
		nextButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/right_normal.png")));
		nextButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/right_rollover.png")));
		nextButton.setBorderPainted(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setFocusable(false);
		previousButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/left_normal.png")));
		previousButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/left_rollover.png")));
		previousButton.setBorderPainted(false);
		previousButton.setContentAreaFilled(false);
		previousButton.setFocusable(false);
		
		
		titleLabel = new JLabel();
		titleLabel.setFont(MainMenu.PRIMARY_FONT.deriveFont(StyleConstants.SUBHEADER));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		setupPages(defaultPage);	
		layoutComponents();
		updateButtons();
	}
	
	private void setupPages(int defaultPage)
	{
		Iterator<JPanel> i = pages.iterator();
		Iterator<String> j = titles.iterator();
		while(i.hasNext() && j.hasNext())
		{
			contentPanel.add(j.next(), i.next());
		}
		
		CardLayout layout = (CardLayout)contentPanel.getLayout();
		layout.show(contentPanel, titles.get(defaultPage));
		titleLabel.setText(titles.get(defaultPage));
	}
	
	private void layoutComponents()
	{
		add(previousButton, BorderLayout.WEST);
		add(contentPanel, BorderLayout.CENTER);
		add(nextButton, BorderLayout.EAST);
		add(titleLabel, BorderLayout.NORTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		CardLayout layout = (CardLayout)contentPanel.getLayout();
		Object source = e.getSource();
		if(source.equals(nextButton))
		{
			layout.next(contentPanel);
			titleLabel.setText(titles.get(++currentPage));	
			updateButtons();
		}
		else
		{
			layout.previous(contentPanel);
			titleLabel.setText(titles.get(--currentPage));
			updateButtons();
		}
	}
	
	public void updateButtons()
	{
		if(currentPage == pageCount - 1)
			nextButton.setVisible(false);
		else nextButton.setVisible(true);
		
		if(currentPage == 0)
			previousButton.setVisible(false);
		else previousButton.setVisible(true);
	}

}
