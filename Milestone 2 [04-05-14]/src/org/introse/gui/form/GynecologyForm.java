package org.introse.gui.form;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.introse.Constants;
import org.introse.Constants.CategoriesConstants;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomDocument;
import org.introse.core.Diagnosis;
import org.introse.core.GynecologyRecord;
import org.introse.core.Patient;
import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.RecordOverview;
import org.introse.gui.window.LoginWindow;


public class GynecologyForm extends JPanel implements ActionListener, RecordForm
{
	private JPanel cardPanel;
	private JLabel pageLabel;
	private JPanel firstPanel, secondPanel, findingsPanel, bottomPanel;
	private JButton nextButton, previousButton;
	private JTextArea remarksValue, grossDescValue, microNoteValue;
	private JScrollPane remarksScroller, grossDescScroller, 
	microNoteScroller;
	private JLabel remarksLabel, grossDescLabel, microNoteLabel;
	private RecordOverview overviewPanel;
	private JScrollPane omnScroller;
	
	private JLabel specimenTypeLabel;
	private ButtonGroup specGroup;
	private JRadioButton conventionalSmear;
	private JRadioButton liquidBased;
	private JRadioButton others;
	
	private JLabel specimenAdequacyLabel;
	private ButtonGroup saGroup;
	private JRadioButton satisfactory;
	private JRadioButton unsatisfactory;
	private JTextField unsatisfactoryDueTo;
	
	private JCheckBox nilm, eca, omn;
	private JCheckBox organisms;
	private ButtonGroup organismsGroup;
	private JRadioButton org1, org2, org3, org4, org5;
	private JCheckBox onf;
	private ButtonGroup onfGroup;
	private JRadioButton onf1, onf2, onf3;
	private JComboBox<String> onf1Box;
	private JCheckBox other;
	private ButtonGroup otherGroup;
	private JRadioButton other1;
	private JCheckBox squamousCell;
	private ButtonGroup squamousGroup;
	private JRadioButton squamous1, squamous2, squamous3, squamous4;
	private JComboBox<String> squamous1Box, squamous3Box;
	private JCheckBox glandularCell;
	private ButtonGroup glandularGroup;
	private JRadioButton glandular1, glandular2, glandular3;
	private JComboBox<String> glandular1Box, glandular3Box;
	private JTextArea omnArea;
	
	public GynecologyForm()
	{
		super(new GridBagLayout());
		setBackground(Color.white);
		initializeComponents();
		layoutComponents();
		updateButtons();
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		int y = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(remarksLabel, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y++;
		c.gridx = 1;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(grossDescLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,30);
		findingsPanel.add(remarksScroller, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		findingsPanel.add(grossDescScroller, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(microNoteLabel, c);
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,0);
		findingsPanel.add(microNoteScroller, c);
		
		y= 0 ;
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y++;
		secondPanel.add(specimenTypeLabel, c);
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(conventionalSmear, c);
		c.gridy = y++;
		secondPanel.add(liquidBased, c);
		c.gridy = y++;
		secondPanel.add(others, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(specimenAdequacyLabel, c);
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(satisfactory, c);
		c.gridy = y++;
		secondPanel.add(unsatisfactory, c);
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(unsatisfactoryDueTo, c);
		///////////////////////////
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(nilm, c);
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(organisms, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(org1, c);
		c.gridy = y++;
		secondPanel.add(org2, c);
		c.gridy = y++;
		secondPanel.add(org3, c);
		c.gridy = y++;
		secondPanel.add(org4, c);
		c.gridy = y++;
		secondPanel.add(org5, c);
		c.insets = new Insets(0,20,0,0);
		c.gridy = y++;
		secondPanel.add(onf, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(onf1, c);
		c.gridy = y++;
		c.insets = new Insets(0,60,0,0);
		secondPanel.add(onf1Box, c);
		c.insets = new Insets(0,40,0,0);
		c.gridy = y++;
		secondPanel.add(onf2, c);
		c.gridy = y++;
		secondPanel.add(onf3, c);
		
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(other, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(other1, c);
		
		y = 0;
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(eca, c);
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(squamousCell, c);
		c.insets = new Insets(0,40,0,0);
		c.gridy = y++;
		secondPanel.add(squamous1, c);
		c.gridy = y++;
		c.insets = new Insets(0,60,0,0);
		secondPanel.add(squamous1Box, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(squamous2, c);
		c.gridy = y++;
		secondPanel.add(squamous3, c);
		c.gridy = y++;
		c.insets = new Insets(0,60,0,0);
		secondPanel.add(squamous3Box, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(squamous4, c);
		
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(glandularCell, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(glandular1, c);
		c.gridy = y++;
		c.insets = new Insets(0,60,0,0);
		secondPanel.add(glandular1Box, c);
		c.insets = new Insets(0,40,0,0);
		c.gridy = y++;
		secondPanel.add(glandular2, c);
		c.gridy = y++;
		secondPanel.add(glandular3, c);
		c.gridy = y++;
		c.insets = new Insets(0,60,0,0);
		secondPanel.add(glandular3Box, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(omn, c);
		c.gridy = y++;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 8;
		c.insets = new Insets(0,0,10,0);
		secondPanel.add(omnScroller, c);
		
		y = 0;
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = y++;
		firstPanel.add(overviewPanel, c);
		c.gridy = y++;
		firstPanel.add(findingsPanel, c);
		
		cardPanel.add("FIRST", firstPanel);
		cardPanel.add("SECOND", secondPanel);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(cardPanel, c);
		c.gridy = 1;
		c.weighty = 0.0;
		add(bottomPanel, c);
		
		bottomPanel.add(previousButton);
		bottomPanel.add(pageLabel);
		bottomPanel.add(nextButton);
	}
	
	private void initializeComponents()
	{	
		cardPanel = new JPanel(new CardLayout());
		bottomPanel = new JPanel(new GridLayout(1, 3, 1, 1));
		bottomPanel.setBackground(Color.white);
		nextButton = new JButton();
		nextButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/next.png")));
		nextButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/next_rollover.png")));
		nextButton.setContentAreaFilled(false);
		nextButton.setBorderPainted(false);
		nextButton.setOpaque(true);
		nextButton.setBackground(Color.white);
		previousButton = new JButton();
		previousButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/previous.png")));
		previousButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/previous_rollover.png")));
		previousButton.setContentAreaFilled(false);
		previousButton.setBorderPainted(false);
		previousButton.setOpaque(true);
		previousButton.setBackground(Color.white);
		previousButton.setVisible(false);
		pageLabel = new JLabel("1");
		pageLabel.setOpaque(true);
		pageLabel.setBackground(Color.white);
		pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		overviewPanel = new RecordOverview(RecordConstants.GYNECOLOGY_RECORD);
		overviewPanel.setBackground(Color.white);
		firstPanel = new JPanel(new GridBagLayout());
		firstPanel.setBackground(Color.white);
		findingsPanel = new JPanel(new GridBagLayout());
		findingsPanel.setBackground(Color.white);
		secondPanel = new JPanel(new GridBagLayout());
		secondPanel.setBackground(Color.white);
		secondPanel.setBorder(null);
		remarksLabel = new JLabel("Remarks");
		grossDescLabel = new JLabel("Gross Description");
		microNoteLabel = new JLabel("Microscopic Notes");
		grossDescValue = new JTextArea(3,25);
		microNoteValue = new JTextArea(3,50);
		remarksValue= new JTextArea(3,25);
		
		remarksScroller = new JScrollPane(remarksValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		grossDescScroller = new JScrollPane(grossDescValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		microNoteScroller = new JScrollPane(microNoteValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		remarksScroller.setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.32), 
				(int)(Preferences.getScreenHeight() * 0.10)));
		grossDescScroller.setPreferredSize(remarksScroller.getPreferredSize());
		microNoteScroller.setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.64), 
				(int)(Preferences.getScreenHeight() * 0.10)));
		remarksValue.setFont(LoginWindow.PRIMARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		grossDescValue.setFont(remarksValue.getFont());
		microNoteValue.setFont(remarksValue.getFont());
		nilm = new JCheckBox(TitleConstants.NILM);
		eca = new JCheckBox(TitleConstants.ECA);
		omn = new JCheckBox(TitleConstants.OMN);
		nilm.setBackground(Color.white);
		eca.setBackground(Color.white);
		omn.setBackground(Color.white);
		
		specimenTypeLabel = new JLabel(TitleConstants.SPEC_TYPE);
		specGroup = new ButtonGroup();
		conventionalSmear = new JRadioButton(TitleConstants.CONVENTIONAL);
		liquidBased = new JRadioButton(TitleConstants.LIQUID_BASED);
		others = new JRadioButton(TitleConstants.OTHERS);
		specGroup.add(conventionalSmear);
		specGroup.add(liquidBased);
		specGroup.add(others);
		conventionalSmear.setBackground(Color.white);
		liquidBased.setBackground(Color.white);
		others.setBackground(Color.white);
		
		specimenAdequacyLabel = new JLabel(TitleConstants.SPEC_ADEQ);
		saGroup = new ButtonGroup();
		satisfactory = new JRadioButton(TitleConstants.SATISFACTORY);
		unsatisfactory = new JRadioButton(TitleConstants.UNSATISFACTORY);
		satisfactory.setBackground(Color.white);
		unsatisfactory.setBackground(Color.white);
		unsatisfactoryDueTo = new JTextField(35);
		saGroup.add(satisfactory);
		saGroup.add(unsatisfactory);
		
		organisms = new JCheckBox(TitleConstants.ORGANISMS);
		organismsGroup = new ButtonGroup();
		org1 = new JRadioButton(TitleConstants.ORG1);
		org2 = new JRadioButton(TitleConstants.ORG2);
		org3 = new JRadioButton(TitleConstants.ORG3);
		org4 = new JRadioButton(TitleConstants.ORG4);
		org5 = new JRadioButton(TitleConstants.ORG5);
		organismsGroup.add(org1);
		organismsGroup.add(org2);
		organismsGroup.add(org3);
		organismsGroup.add(org4);
		organismsGroup.add(org5);
		organisms.setBackground(Color.white);
		org1.setBackground(Color.white);
		org2.setBackground(Color.white);
		org3.setBackground(Color.white);
		org4.setBackground(Color.white);
		org5.setBackground(Color.white);
		
		onf = new JCheckBox(TitleConstants.ONF);
		onfGroup = new ButtonGroup();
		onf1 = new JRadioButton(TitleConstants.ONF1);
		onf2 = new JRadioButton(TitleConstants.ONF2);
		onf3 = new JRadioButton(TitleConstants.ONF3);
		onfGroup.add(onf1);
		onfGroup.add(onf2);
		onfGroup.add(onf3);
		String[] onf1BoxString = {TitleConstants.ONF1BOX1, TitleConstants.ONF1BOX2,TitleConstants.ONF1BOX3};
		onf1Box = new JComboBox<String>(onf1BoxString);
		onf.setBackground(Color.white);
		onf1.setBackground(Color.white);
		onf2.setBackground(Color.white);
		onf3.setBackground(Color.white);
		
		other = new JCheckBox(TitleConstants.OTHER);
		otherGroup = new ButtonGroup();
		other1 = new JRadioButton(TitleConstants.OTHER1);
		otherGroup.add(other1);
		other.setBackground(Color.white);
		other1.setBackground(Color.white);
		
		squamousCell = new JCheckBox(TitleConstants.SQUAMOUS_CELL);
		squamousGroup = new ButtonGroup();
		squamous1 = new JRadioButton(TitleConstants.SQUAMOUS1);
		squamous2 = new JRadioButton(TitleConstants.SQUAMOUS2);
		squamous3 = new JRadioButton(TitleConstants.SQUAMOUS3);
		squamous4 = new JRadioButton(TitleConstants.SQUAMOUS4);
		squamousGroup.add(squamous1);
		squamousGroup.add(squamous2);
		squamousGroup.add(squamous3);
		squamousGroup.add(squamous4);
		squamousCell.setBackground(Color.white);
		squamous1.setBackground(Color.white);
		squamous2.setBackground(Color.white);
		squamous3.setBackground(Color.white);
		squamous4.setBackground(Color.white);
		String[] squamous1BoxString = {TitleConstants.SQUAMOUS1BOX1, TitleConstants.SQUAMOUS1BOX2};
		String[] squamous3BoxString = {TitleConstants.SQUAMOUS3BOX1, TitleConstants.SQUAMOUS3BOX2};
		squamous1Box =new JComboBox<String>(squamous1BoxString);
		squamous3Box = new JComboBox<String>(squamous3BoxString);
		squamous1Box.setBorder(null);
		squamous3Box.setBorder(null);
		
		glandularCell = new JCheckBox(TitleConstants.GLANDULAR_CELL);
		glandularGroup = new ButtonGroup();
		glandular1 = new JRadioButton(TitleConstants.GLANDULAR1);
		glandular2 = new JRadioButton(TitleConstants.GLANDULAR2);
		glandular3 = new JRadioButton(TitleConstants.GLANDULAR3);
		glandularGroup.add(glandular1);
		glandularGroup.add(glandular2);
		glandularGroup.add(glandular3);
		glandularCell.setBackground(Color.white);
		glandular1.setBackground(Color.white);
		glandular2.setBackground(Color.white);
		glandular3.setBackground(Color.white);
		
		String[] glandular1BoxString = {TitleConstants.GLANDULAR1BOX1, TitleConstants.GLANDULAR1BOX2,
				TitleConstants.GLANDULAR1BOX3, TitleConstants.GLANDULAR1BOX4, TitleConstants.GLANDULAR1BOX5};
		String[] glandular2BoxString = {TitleConstants.GLANDULAR3BOX1, TitleConstants.GLANDULAR3BOX2,
				TitleConstants.GLANDULAR3BOX3, TitleConstants.GLANDULAR3BOX4};
		glandular1Box = new JComboBox<String>(glandular1BoxString);
		glandular3Box = new JComboBox<String>(glandular2BoxString);
		glandular1Box.setBorder(null);
		glandular3Box.setBorder(null);
		omnArea = new JTextArea(3, 30);
		omnScroller = new JScrollPane(omnArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		omnScroller.setPreferredSize(new Dimension(500, 70));
		
		remarksValue.setDocument(new CustomDocument(RecordConstants.REMARKS_LENGTH));
		grossDescValue.setDocument(new CustomDocument(RecordConstants.GROSS_LENGTH));
		microNoteValue.setDocument(new CustomDocument(RecordConstants.MICRO_LENGTH));
		omnArea.setDocument(new CustomDocument(RecordConstants.DIAGNOSIS_LENGTH));
		unsatisfactoryDueTo.setDocument(new CustomDocument(RecordConstants.DIAGNOSIS_LENGTH));
		
	}
	
	public void setFields(Record record, Patient patient)
	{	
		overviewPanel.setRecordFields(record);
		overviewPanel.setPatientFields(patient);
		
		if(record.getAttribute(RecordTable.SPEC_TYPE) != null)
		switch((String)record.getAttribute(RecordTable.SPEC_TYPE))
		{
		case TitleConstants.CONVENTIONAL: conventionalSmear.setSelected(true);
												 break;
		case TitleConstants.LIQUID_BASED: liquidBased.setSelected(true);
											break;
		case TitleConstants.OTHERS: others.setSelected(true);
		}
		
		resetButtons();
		List<Diagnosis> diagnosis = (List)record.getAttribute(RecordTable.DIAGNOSIS);
		if(diagnosis!= null)
		{
			Iterator<Diagnosis> i = diagnosis.iterator();
			while(i.hasNext())
			{
				Diagnosis curDiagnosis = i.next();
				int category = curDiagnosis.getCategory();
				String value = curDiagnosis.getValue();
				
				switch(category)
				{
				case CategoriesConstants.ORGANISMS: 
				case CategoriesConstants.ONF:
				case CategoriesConstants.OTHER:	
					
					if(category == CategoriesConstants.ORGANISMS)
					{
						organisms.setSelected(true);
						if(value.contains(TitleConstants.ORG1))
							org1.setSelected(true);
						else if(value.contains(TitleConstants.ORG2))
							org2.setSelected(true);
						else if(value.contains(TitleConstants.ORG3))
							org3.setSelected(true);
						else if(value.contains(TitleConstants.ORG4))
							org4.setSelected(true);
						else if(value.contains(TitleConstants.ORG5))
							org5.setSelected(true);
					}
					else if(category == CategoriesConstants.ONF)
					{
						onf.setSelected(true);
						if(value.contains(TitleConstants.ONF1))
						{
							onf1.setSelected(true);
							if(value.contains(TitleConstants.ONF1BOX1))
								onf1Box.setSelectedItem(TitleConstants.ONF1BOX1);
							else if(value.contains(TitleConstants.ONF1BOX2))
								onf1Box.setSelectedItem(TitleConstants.ONF1BOX2);
							else onf1Box.setSelectedItem(TitleConstants.ONF1BOX3);
						}
						else if(value.contains(TitleConstants.ONF2))
							onf2.setSelected(true);
						else if(value.contains(TitleConstants.ONF3)) 
							onf3.setSelected(true);
					}
					else
					{
						other.setSelected(true);
						other1.setSelected(true);
					}
					nilm.setSelected(true);
					break;
				case CategoriesConstants.SC:
				case CategoriesConstants.GC: 
							
					if(category == CategoriesConstants.SC)
					{
						squamousCell.setSelected(true);
						if(value.contains(TitleConstants.SQUAMOUS1))
						{
							squamous1.setSelected(true);
							if(value.contains(TitleConstants.SQUAMOUS1BOX1))
								squamous1Box.setSelectedItem(TitleConstants.SQUAMOUS1BOX1);
							else if(value.contains(TitleConstants.SQUAMOUS1BOX2))
								squamous1Box.setSelectedItem(TitleConstants.SQUAMOUS1BOX2);
						}
						else if(value.contains(TitleConstants.SQUAMOUS2))
							squamous2.setSelected(true);
						else if(value.contains(TitleConstants.SQUAMOUS3))
						{
							squamous3.setSelected(true);
							if(value.contains(TitleConstants.SQUAMOUS3BOX1))
								squamous3Box.setSelectedItem(TitleConstants.SQUAMOUS3BOX1);
							else if(value.contains(TitleConstants.SQUAMOUS3BOX2))
								squamous3Box.setSelectedItem(TitleConstants.SQUAMOUS3BOX2);
						}
						else if(value.contains(TitleConstants.SQUAMOUS4))
							squamous4.setSelected(true);
					}
					else
					{
						glandularCell.setSelected(true);
						if(value.contains(TitleConstants.GLANDULAR1))
						{
							glandular1.setSelected(true);
							if(value.contains(TitleConstants.GLANDULAR1BOX1))
								glandular1Box.setSelectedItem(TitleConstants.GLANDULAR1BOX1);
							else if(value.contains(TitleConstants.GLANDULAR1BOX2))
								glandular1Box.setSelectedItem(TitleConstants.GLANDULAR1BOX2);
							else if(value.contains(TitleConstants.GLANDULAR1BOX3))
								glandular1Box.setSelectedItem(TitleConstants.GLANDULAR1BOX3);
							else if(value.contains(TitleConstants.GLANDULAR1BOX4))
								glandular1Box.setSelectedItem(TitleConstants.GLANDULAR1BOX4);
							else glandular1Box.setSelectedItem(TitleConstants.GLANDULAR1BOX5);
						}
						else if(value.contains(TitleConstants.GLANDULAR2))
							glandular2.setSelected(true);
						else if(value.contains(TitleConstants.GLANDULAR3))
						{
							glandular3.setSelected(true);
							if(value.contains(TitleConstants.GLANDULAR3BOX1))
								glandular3Box.setSelectedItem(TitleConstants.GLANDULAR3BOX1);
							else if(value.contains(TitleConstants.GLANDULAR3BOX2))
								glandular3Box.setSelectedItem(TitleConstants.GLANDULAR3BOX2);
							else if(value.contains(TitleConstants.GLANDULAR3BOX3))
								glandular3Box.setSelectedItem(TitleConstants.GLANDULAR3BOX3);
							else glandular3Box.setSelectedItem(TitleConstants.GLANDULAR3BOX4);
						}
					}
					eca.setSelected(true);
					break;
				case CategoriesConstants.OMN: omn.setSelected(true);
											  omnArea.setText(value);
											  break;
				case CategoriesConstants.SA: if(value.contains(TitleConstants.SATISFACTORY))
												satisfactory.setSelected(true);
											 else if(value.contains(TitleConstants.UNSATISFACTORY))
											 {
												 unsatisfactory.setSelected(true);
												 unsatisfactoryDueTo.setText(
														 value.substring(TitleConstants.UNSATISFACTORY.length() + 1));
											 }
				}
			}
		}
		String remarks = (String)record.getAttribute(RecordTable.REMARKS);
		if(remarks != null)
		remarksValue.setText(remarks);
		updateButtons();
		
		String microNote = (String)record.getAttribute(RecordTable.MICRO_NOTE);
		if(microNote != null)
			microNoteValue.setText(microNote);
		
		String grossdesc = (String)record.getAttribute(RecordTable.GROSS_DESC);
		if(grossdesc != null)
			grossDescValue.setText(grossdesc);
	}
	@Override
	public void setRecordEditable(boolean isEditable)
	{
		overviewPanel.setRecordEditable(isEditable);
		remarksValue.setEditable(isEditable);
		nilm.setEnabled(isEditable);
		eca.setEnabled(isEditable);
		omn.setEnabled(isEditable);
		satisfactory.setEnabled(isEditable);
		unsatisfactory.setEnabled(isEditable);
		conventionalSmear.setEnabled(isEditable);
		liquidBased.setEnabled(isEditable);
		others.setEnabled(isEditable);
		grossDescValue.setEditable(isEditable);
		microNoteValue.setEditable(isEditable);
		updateButtons();
	}
	@Override
	public void setPatientEditable(boolean isEditable)
	{
		overviewPanel.setPatientEditable(isEditable);
	}
	
	public void updateButtons()
	{
		organisms.setEnabled(nilm.isSelected() && nilm.isEnabled());
		organisms.setSelected(organisms.isSelected() && nilm.isSelected());
		onf.setEnabled(nilm.isSelected() && nilm.isEnabled());
		onf.setSelected(onf.isSelected() && nilm.isSelected());
		other.setEnabled(nilm.isSelected() && nilm.isEnabled());
		other.setSelected(other.isSelected() && nilm.isSelected());
		
		if(!organisms.isSelected())
			organismsGroup.clearSelection();
		
		org1.setEnabled(organisms.isSelected() && organisms.isEnabled());
		org2.setEnabled(organisms.isSelected() && organisms.isEnabled());
		org3.setEnabled(organisms.isSelected() && organisms.isEnabled());
		org4.setEnabled(organisms.isSelected() && organisms.isEnabled());
		org5.setEnabled(organisms.isSelected() && organisms.isEnabled());
		
		if(!onf.isSelected())
			onfGroup.clearSelection();
		onf1.setEnabled(onf.isSelected() && onf.isEnabled()); 
		onf2.setEnabled(onf.isSelected() && onf.isEnabled());
		onf3.setEnabled(onf.isSelected() && onf.isEnabled());
		onf1Box.setEnabled(onf1.isSelected() && onf1.isEnabled());
		
		if(!other.isSelected())
			otherGroup.clearSelection();
		other1.setEnabled(other.isSelected() && other.isEnabled());
		
		squamousCell.setEnabled(eca.isSelected() && eca.isEnabled());
		squamousCell.setSelected(squamousCell.isSelected() && eca.isSelected());
		
		if(!squamousCell.isSelected())
			squamousGroup.clearSelection();
		squamous1.setEnabled(squamousCell.isSelected() && squamousCell.isEnabled());
		squamous2.setEnabled(squamousCell.isSelected() && squamousCell.isEnabled());
		squamous3.setEnabled(squamousCell.isSelected() && squamousCell.isEnabled());
		squamous4.setEnabled(squamousCell.isSelected() && squamousCell.isEnabled());
		squamous1Box.setEnabled(squamous1.isSelected() && squamous1.isEnabled());
		squamous3Box.setEnabled(squamous3.isSelected() && squamous3.isEnabled());
		
		glandularCell.setEnabled(eca.isSelected() && eca.isEnabled());
		glandularCell.setSelected(glandularCell.isSelected() && eca.isSelected());
		
		if(!glandularCell.isSelected())
			glandularGroup.clearSelection();
		glandular1.setEnabled(glandularCell.isSelected() && glandularCell.isEnabled());
		glandular2.setEnabled(glandularCell.isSelected() && glandularCell.isEnabled());
		glandular3.setEnabled(glandularCell.isSelected() && glandularCell.isEnabled());
		glandular1Box.setEnabled(glandular1.isSelected() && glandular1.isEnabled());
		glandular3Box.setEnabled(glandular3.isSelected() && glandular3.isEnabled());
		
		omnArea.setEditable(omn.isSelected() && omn.isEnabled());
		if(!omn.isSelected())
			omnArea.setText("");
		
		unsatisfactoryDueTo.setEnabled(unsatisfactory.isSelected() && unsatisfactory.isEnabled());
	}
	
	public boolean areFieldsValid()
	{
		JScrollPane defaultScrollPane = new JScrollPane();
		boolean isValid = true;
		if(!overviewPanel.areFieldsValid())
			isValid = false;
		if(unsatisfactory.isSelected() && unsatisfactoryDueTo.getText().replaceAll("\\s", "").length() < 1)
			isValid = false;
		
		if(nilm.isSelected())
		{
			if(!organisms.isSelected() && !onf.isSelected() && !other.isSelected())
				isValid = false;
			
			if(organisms.isSelected())
			{
				if(!org1.isSelected() && !org2.isSelected() && 
						!org3.isSelected() && !org4.isSelected() && !org5.isSelected())
					isValid = false;
			}
			if(onf.isSelected())
			{
				if(!onf1.isSelected() && !onf2.isSelected() && !onf3.isSelected())
					isValid = false;
			}
			if(other.isSelected())
				if(!other1.isSelected())
					isValid = false;
		}
		if(eca.isSelected())
		{
			if(!squamousCell.isSelected() && !glandularCell.isSelected())
				isValid = false;
			if(squamousCell.isSelected())
			{
				if(!squamous1.isSelected() && !squamous2.isSelected() && !squamous3.isSelected()
						&& !squamous4.isSelected())
					isValid = false;
			}
			if(glandularCell.isSelected())
			{
				if(!glandular1.isSelected() && !glandular2.isSelected() && !glandular3.isSelected())
					isValid = false;
			}
		}
		if(omn.isSelected())
		{	if(omnArea.getText().replaceAll("\\s", "").length() < 1 || 
					omnArea.getText().length() > RecordConstants.DIAGNOSIS_LENGTH)
			{
				isValid = false;
				omnScroller.setBorder(BorderFactory.createLineBorder(Color.red));
			}
			else omnScroller.setBorder(defaultScrollPane.getBorder());
		}
		return isValid;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		updateButtons();
	}

	@Override
	public void addListener(CustomListener listener) 
	{
		nilm.addActionListener(this);
		eca.addActionListener(this);
		omn.addActionListener(this);
		organisms.addActionListener(this);
		onf.addActionListener(this);
		other.addActionListener(this);
		onf1.addActionListener(this);
		onf2.addActionListener(this);
		onf3.addActionListener(this);
		
		squamousCell.addActionListener(this);
		squamous1.addActionListener(this);
		squamous2.addActionListener(this);
		squamous3.addActionListener(this);
		squamous4.addActionListener(this);
		
		glandularCell.addActionListener(this);
		glandular1.addActionListener(this);
		glandular2.addActionListener(this);
		glandular3.addActionListener(this);
		unsatisfactory.addActionListener(this);
		satisfactory.addActionListener(this);	
		overviewPanel.addListener(listener);
		
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				CardLayout cl = (CardLayout)cardPanel.getLayout();
				cl.last(cardPanel);
				pageLabel.setText("2");
				nextButton.setVisible(false);
				previousButton.setVisible(true);
			}
		});
		
		previousButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				CardLayout cl = (CardLayout)cardPanel.getLayout();
				cl.first(cardPanel);
				nextButton.setVisible(true);
				previousButton.setVisible(false);
				pageLabel.setText("1");
			}
		});
	}
	
	public void resetButtons()
	{
		nilm.setSelected(false);
		eca.setSelected(false);
		omn.setSelected(false);
		organisms.setSelected(false);
		organismsGroup.clearSelection();
		onf.setSelected(false);
		onfGroup.clearSelection();
		other.setSelected(false);
		otherGroup.clearSelection();
		squamousCell.setSelected(false);
		squamousGroup.clearSelection();
		glandularCell.setSelected(false);
		glandularGroup.clearSelection();
		omnArea.setText("");
		saGroup.clearSelection();
	}

	@Override
	public Record getRecord() 
	{
		Record record = (GynecologyRecord)overviewPanel.getRecord();
		String specimenType = TitleConstants.OTHERS;
		if(conventionalSmear.isSelected())
			specimenType = TitleConstants.CONVENTIONAL;
		else if(liquidBased.isSelected())
			specimenType = TitleConstants.LIQUID_BASED;
		record.putAttribute(RecordTable.SPEC_TYPE, specimenType);
		
		List<Diagnosis> diagnosisList = new Vector<Diagnosis>();
		if(satisfactory.isSelected())
			diagnosisList.add(new Diagnosis(CategoriesConstants.SA, satisfactory.getText()));
		else if(unsatisfactory.isSelected())
			diagnosisList.add(new Diagnosis(CategoriesConstants.SA, 
					unsatisfactory.getText() + " " + unsatisfactoryDueTo.getText()));
		
		if(org1.isSelected() && org1.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ORGANISMS, org1.getText()));
		else if(org2.isSelected() && org2.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ORGANISMS, org2.getText()));
		else if(org3.isSelected() && org3.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ORGANISMS, org3.getText()));
		else if(org4.isSelected() && org4.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ORGANISMS, org4.getText()));
		else if(org5.isSelected() && org5.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ORGANISMS, org5.getText()));
		
		if(onf1.isSelected() && onf1.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ONF, 
					onf1.getText() + " " + onf1Box.getSelectedItem()));
		else if(onf2.isSelected() && onf2.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ONF, onf2.getText()));
		else if(onf3.isSelected() && onf3.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ONF, onf3.getText()));
		
		if(other1.isSelected() && other1.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.OTHER, other1.getText()));
		
		if(squamous1.isSelected() && squamous1.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.SC, 
					squamous1.getText() + " " + squamous1Box.getSelectedItem()));
		else if(squamous2.isSelected() && squamous2.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.SC, squamous2.getText()));
		else if(squamous3.isSelected() && squamous3.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.SC, 
				squamous3.getText() + " " + squamous3Box.getSelectedItem()));
		else if(squamous4.isSelected() && squamous4.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.SC, squamous4.getText()));
		
		if(glandular1.isSelected() && glandular1.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.GC, 
					glandular1.getText() + " " + glandular1Box.getSelectedItem()));
		else if(glandular2.isSelected() && glandular2.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.GC, glandular2.getText()));
		if(glandular3.isSelected() && glandular3.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.GC, 
					glandular3.getText() + " " + glandular3Box.getSelectedItem()));
		
		if(omn.isSelected() && omn.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.OMN, omnArea.getText()));
		record.putAttribute(RecordTable.DIAGNOSIS, diagnosisList);
		if(remarksValue.getText().length() > 0)
			record.putAttribute(RecordTable.REMARKS, remarksValue.getText());
		if(grossDescValue.getText().length() > 0)
			record.putAttribute(RecordTable.GROSS_DESC, grossDescValue.getText());
		if(microNoteValue.getText().length() > 0)
			record.putAttribute(RecordTable.MICRO_NOTE, microNoteValue.getText());
		return record;
	}

	@Override
	public Patient getPatient() 
	{
		return overviewPanel.getPatient();
	}

	@Override
	public void setPatient(Patient patient) 
	{
		overviewPanel.setPatientFields(patient);
	}
	

	@Override
	public void setLoadPatientEnabled(boolean isEnabled) 
	{
		overviewPanel.getPatientForm().setLoadExisting(isEnabled);
	} 
	
}