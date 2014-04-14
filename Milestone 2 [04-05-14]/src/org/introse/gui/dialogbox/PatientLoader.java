package org.introse.gui.dialogbox;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.introse.Constants.StyleConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.Patient;
import org.introse.core.Preferences;
import org.introse.core.dao.PatientDao;
import org.introse.core.workers.PatientListGenerator;
import org.introse.core.workers.PatientRetrieveWorker;
import org.introse.gui.event.ListListener;
import org.introse.gui.panel.ListItem;
import org.introse.gui.window.LoginWindow;

public class PatientLoader extends JDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ListItem> patients; 
	private JPanel pane, emptyPanel, refreshPanel, cardPanel;
	private PatientDao patientDao;
	private JButton aC, dF, gI, jL, mO, pR, sV, wZ;
	private JPanel listPanel;
	private JScrollPane listScroller;
	private ListListener listListener;
	private char start, end;
	
	public PatientLoader(PatientDao patientDao)
	{
		super(null, TitleConstants.LOAD_PATIENT, ModalityType.APPLICATION_MODAL);
		this.patientDao = patientDao;
		start = 'A';
		end = 'C';
		initUI();
		layoutComponents();
		updatePList();
		updateButtons();
	}
	
	private void layoutComponents()
	{
		int y = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,10,10,5);
		c.gridx = 0;
		c.weightx = 1.0;
		pane.add(aC, c);
		c.insets = new Insets(10,0,10,5);
		c.gridx = 1;
		pane.add(dF, c);
		c.gridx = 2;
		pane.add(gI, c);
		c.gridx = 3;
		pane.add(jL, c);
		c.gridx = 4;
		pane.add(mO, c);
		c.gridx = 5;
		pane.add(pR, c);
		c.gridx = 6;
		pane.add(sV, c);
		c.gridx = 7;
		c.insets = new Insets(10,0,10,10);
		pane.add(wZ, c);
		c.insets = new Insets(0,10,10,10);
		c.weighty = 1.0;
		c.gridwidth = 8;
		c.gridx = 0;
		c.gridy = ++y;
		pane.add(cardPanel, c);
		setContentPane(pane);
		setPreferredSize(new Dimension(520, 
				(int)(Preferences.getScreenHeight() * 0.8)));
	}
	
	private void initUI()
	{
		pane = new JPanel(new GridBagLayout());
		pane.setBackground(Color.white);
		
		aC = new JButton("A-C");
		dF = new JButton("D-F");
		gI = new JButton("G-I");
		jL = new JButton("J-L");
		mO = new JButton("M-O");
		pR = new JButton("P-R");
		sV = new JButton("S-V");
		wZ = new JButton("W-Z");
		
		aC.addActionListener(this);
		dF.addActionListener(this);
		gI.addActionListener(this);
		jL.addActionListener(this);
		mO.addActionListener(this);
		pR.addActionListener(this);
		sV.addActionListener(this);
		wZ.addActionListener(this);
		
		cardPanel = new JPanel(new CardLayout());
		listPanel = new JPanel(new GridBagLayout());
		emptyPanel = new JPanel(new GridBagLayout());
		refreshPanel = new JPanel(new GridBagLayout());
		
		listScroller = new JScrollPane(listPanel,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setBorder(null);

		ImageIcon icon = new ImageIcon(getClass().getResource("/res/icons/empty.png"));
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(null, image.getGraphics(), 0, 0);
		
		double scaleWidth = 1.0 * Preferences.getScreenWidth() / Preferences.BASE_WIDTH;;
		double scaleHeight = 1.0 * Preferences.getScreenHeight() / Preferences.BASE_HEIGHT;
		if(scaleWidth > 1)
			scaleWidth = 1;
		if(scaleHeight > 1)
			scaleHeight = 1;
		Image scaledImage = image.getScaledInstance((int)(image.getWidth() * scaleWidth), 
				(int)(image.getHeight() * scaleHeight), 
				Image.SCALE_DEFAULT);
		
		JLabel emptyLabel = new JLabel(TitleConstants.EMPTY_PANEL);
		emptyLabel.setIcon(new ImageIcon(scaledImage));
		emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emptyLabel.setHorizontalTextPosition(JLabel.CENTER);
		emptyLabel.setVerticalTextPosition(JLabel.BOTTOM);
		emptyLabel.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.HEADER - 20));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		emptyPanel.add(emptyLabel, c);
		
		JLabel refreshLabel = new JLabel("refreshing list");
		refreshLabel.setIcon(new ImageIcon(getClass().getResource("/res/icons/refreshing.gif")));
		refreshLabel.setHorizontalAlignment(SwingConstants.CENTER);
		refreshLabel.setHorizontalTextPosition(JLabel.CENTER);
		refreshLabel.setVerticalTextPosition(JLabel.BOTTOM);
		refreshLabel.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.HEADER - 20));
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		refreshPanel.add(refreshLabel, c);
		
		listPanel.setBackground(Color.white);
		emptyPanel.setBackground(Color.white);
		refreshPanel.setBackground(Color.white);
		cardPanel.add(listScroller, TitleConstants.LIST_PANEL);
		cardPanel.add(emptyPanel, TitleConstants.EMPTY_PANEL);
		cardPanel.add(refreshPanel, TitleConstants.REFRESH_PANEL);
	}
	
	public void addListListener(ListListener listener)
	{
		listListener = listener;
	}
	
	public void showGUI()
	{
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void updatePList()
	{
		final CardLayout cl = (CardLayout)cardPanel.getLayout();
		cl.show(cardPanel, TitleConstants.REFRESH_PANEL);

		final PatientRetrieveWorker worker = new PatientRetrieveWorker(patientDao, start, end);
		worker.addPropertyChangeListener(new PropertyChangeListener() 
		{	
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("DONE"))
				{
					List<Patient> patientList;
					try 
					{
						patientList = (List<Patient>)worker.get();
						final PatientListGenerator listWorker = new PatientListGenerator(patientList, false);
						listWorker.addPropertyChangeListener(new PropertyChangeListener()
						{
							@Override
							public void propertyChange(PropertyChangeEvent evt) 
							{
								if(evt.getPropertyName().equals("DONE"))
								{
									try 
									{
										patients = listWorker.get();
										updateViewable(patients);
										if(patients.size() < 1)
											cl.show(cardPanel, TitleConstants.EMPTY_PANEL);
										else cl.show(cardPanel, TitleConstants.LIST_PANEL);
									} catch (InterruptedException | ExecutionException e) 
									{e.printStackTrace();}
								}
							}
						});
						listWorker.execute();
					} catch (InterruptedException | ExecutionException e1) 
					{e1.printStackTrace();}
				}
				
			}
		});
		worker.execute();
	}
	
	private void updateButtons()
	{
		aC.setEnabled(true);
		dF.setEnabled(true);
		gI.setEnabled(true);
		jL.setEnabled(true);
		mO.setEnabled(true);
		pR.setEnabled(true);
		sV.setEnabled(true);
		wZ.setEnabled(true);
		if(start == 'A')
			aC.setEnabled(false);
		else if(start == 'D')
			dF.setEnabled(false);
		else if(start == 'G')
			gI.setEnabled(false);
		else if(start == 'J')
			jL.setEnabled(false);
		else if(start =='M')
			mO.setEnabled(false);
		else if(start =='P')
			pR.setEnabled(false);
		else if(start== 'S')
			sV.setEnabled(false);
		else if(start == 'W')
			wZ.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		if(source.equals(aC))
		{
			start = 'A';
			end = 'C';
		}
		else if(source.equals(dF))
		{
			start = 'D';
			end = 'F';
		}
		else if(source.equals(gI))
		{
			start = 'G';
			end = 'I';
		}
		else if(source.equals(jL))
		{
			start = 'J';
			end = 'L';
		}
		else if(source.equals(mO))
		{
			start = 'M';
			end = 'O';
		}
		else if(source.equals(pR))
		{
			start = 'P';
			end = 'R';
		}
		else if(source.equals(sV))
		{
			start = 'S';
			end = 'V';
		}
		else if(source.equals(wZ))
		{
			start = 'W';
			end = 'Z';
		}
		updatePList();
		updateButtons();
	}
	
	public void updateViewable(List<ListItem> list)
	{
		listPanel.removeAll();
		Iterator<ListItem> i = list.iterator();
		final GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weighty = 0.0;
		c.weightx = 1.0;
		int y = 0;
		while(i.hasNext())
		{
			final ListItem listItem = i.next();
			listItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			listItem.addListener(listListener);
			c.gridy = y++;
			c.insets = new Insets(0,5,5,5);
			if(!i.hasNext())
				c.weighty = 1.0;
			listPanel.add(listItem, c);
		}
		listPanel.revalidate();
		listScroller.revalidate();
		cardPanel.revalidate();
		revalidate();
	}
}
