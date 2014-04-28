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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.introse.Constants;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.ResultCategoriesConstants;
import org.introse.Constants.StyleConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomDocument;
import org.introse.core.Result;
import org.introse.core.Patient;
import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.RecordOverview;
import org.introse.gui.window.LoginWindow;


public class GynecologyForm extends JPanel implements ActionListener, RecordForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int page;
	private JPanel cardPanel;
	private JLabel pageLabel, findings1Label, findings2Label;
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
	
	private JLabel hormonalEvaluationLabel;
	private JLabel superficialsLabel, intermediatesLabel, parabasalsLabel;
	private JTextField superficialValue, intermediatesValue, parabasalsValue;
	
	private JLabel specimenAdequacyLabel;
	private ButtonGroup saGroup;
	private JRadioButton satisfactory;
	private JRadioButton unsatisfactory;
	private JTextField unsatisfactoryDueTo;
	
	private JRadioButton nilm, eca, omn;
	private ButtonGroup nilmGroup;
	private ButtonGroup ecaGroup;
	private JRadioButton organisms;
	private ButtonGroup organismsGroup;
	private JRadioButton org1, org2, org3, org4, org5;
	private JRadioButton onf;
	private ButtonGroup onfGroup;
	private JRadioButton onf1, onf2, onf3;
	private JComboBox<String> onf1Box;
	private JRadioButton other;
	private ButtonGroup otherGroup;
	private JRadioButton other1;
	private JRadioButton squamousCell;
	private ButtonGroup squamousGroup;
	private JRadioButton squamous1, squamous2, squamous3, squamous4;
	private JComboBox<String> squamous1Box, squamous3Box;
	private JRadioButton glandularCell;
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
		updateGUI();
		page = 1;
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		int y = 0;
		JSeparator divider1 = new JSeparator(SwingConstants.HORIZONTAL);
		JSeparator divider2 = new JSeparator(SwingConstants.HORIZONTAL);
		divider1.setPreferredSize(new Dimension(1,1));
		divider2.setPreferredSize(divider1.getPreferredSize());
		divider1.setBackground(Color.LIGHT_GRAY);
		divider2.setBackground(divider1.getBackground());
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridy = y++;
		findingsPanel.add(findings1Label, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,30,0);
		findingsPanel.add(divider1, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y++;
		c.weighty = 0.0;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(remarksLabel, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = y++;
		c.weighty = 1.0;
		c.insets = new Insets(0,0,20,0);
		findingsPanel.add(remarksScroller, c);
		c.fill = GridBagConstraints.NONE;
		c.weighty = 0.0;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(grossDescLabel, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = y++;
		c.weighty = 1.0;
		c.insets = new Insets(0,0,20,0);
		findingsPanel.add(grossDescScroller, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y++;
		c.weighty = 0.0;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(microNoteLabel, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = y++;
		c.weighty = 1.0;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(microNoteScroller, c);
		
		y= 0 ;
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 6;
		c.weightx = 1.0;
		c.gridy = y++;
		secondPanel.add(findings2Label, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,30,0);
		secondPanel.add(divider2, c);
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.insets = new Insets(0,0,0,0);
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
		
		y = 2;
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		c.gridwidth = 5;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(hormonalEvaluationLabel, c);
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.gridy = y;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(superficialsLabel, c);
		c.gridwidth = 4;
		c.weightx = 1.0;
		c.gridy = y++;
		c.gridx = 2;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(superficialValue, c);
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.gridy = y;
		c.gridx = 1;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(intermediatesLabel, c);
		c.gridwidth = 4;
		c.weightx = 1.0;
		c.gridy = y++;
		c.gridx = 2;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(intermediatesValue, c);
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.gridy = y;
		c.gridx = 1;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(parabasalsLabel, c);
		c.gridwidth = 4;
		c.weightx = 1.0;
		c.gridx = 2;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(parabasalsValue, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = y++;
		c.gridwidth = 5;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(eca, c);
		c.weightx = 1.0;
		c.gridwidth = 5;
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(squamousCell, c);
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridy = y;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(squamous1, c);
		c.weightx = 1.0;
		c.gridwidth = 2;
		c.gridy = y++;
		c.gridx = 4;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(squamous1Box, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = y++;
		c.gridwidth = 5;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(squamous2, c);
		c.weightx = 0.0;
		c.gridwidth = 4;
		c.gridy = y;
		secondPanel.add(squamous3, c);
		c.weightx = 1.0;
		c.gridx = 5;
		c.gridy = y++;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(squamous3Box, c);
		c.weightx = 1.0;
		c.gridwidth = 5;
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(squamous4, c);
		
		c.weightx = 1.0;
		c.gridwidth = 5;
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		secondPanel.add(glandularCell, c);
		c.weightx = 0.0;
		c.gridy = y;
		c.gridwidth = 1;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(glandular1, c);
		c.weightx = 1.0;
		c.gridwidth = 4;
		c.gridy = y++;
		c.gridx = 2;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(glandular1Box, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = y++;
		c.gridwidth = 5;
		c.insets = new Insets(0,40,0,0);
		secondPanel.add(glandular2, c);
		c.weightx = 0.0;
		c.gridy = y;
		c.gridwidth = 2;
		secondPanel.add(glandular3, c);
		c.weightx = 1.0;
		c.gridy = y++;
		c.gridx = 3;
		c.gridwidth = 3;
		c.insets = new Insets(0,0,0,0);
		secondPanel.add(glandular3Box, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 5;
		secondPanel.add(omn, c);
		c.gridy = y++;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 8;
		c.insets = new Insets(0,0,0,0);
		c.weighty = 1.0;
		secondPanel.add(omnScroller, c);
		
		cardPanel.add("FIRST", overviewPanel);
		cardPanel.add("SECOND", findingsPanel);
		cardPanel.add("THIRD", secondPanel);
		
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
		findings1Label = new JLabel("Interpretation and Results");
		findings2Label = new JLabel("Interpretation and Results");
		findings1Label.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		findings2Label.setBackground(findings1Label.getBackground());
		findings1Label.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.CATEGORY));
		findings2Label.setFont(findings1Label.getFont());
		findings1Label.setForeground(Color.gray);
		findings2Label.setForeground(findings1Label.getForeground());
		cardPanel = new JPanel(new CardLayout());
		bottomPanel = new JPanel(new GridLayout(1, 3, 1, 1));
		bottomPanel.setBackground(Color.white);
		nextButton = new JButton();
		nextButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_next.png")));
		nextButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_next_hover.png")));
		nextButton.setContentAreaFilled(false);
		nextButton.setBorderPainted(false);
		nextButton.setOpaque(true);
		nextButton.setBackground(Color.white);
		previousButton = new JButton();
		previousButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_previous.png")));
		previousButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_previous_hover.png")));
		previousButton.setContentAreaFilled(false);
		previousButton.setBorderPainted(false);
		previousButton.setOpaque(true);
		previousButton.setBackground(Color.white);
		previousButton.setVisible(false);
		pageLabel = new JLabel("1 of 3");
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
		
		grossDescValue.setLineWrap(true);
		grossDescValue.setWrapStyleWord(true);
		microNoteValue.setLineWrap(true);
		microNoteValue.setWrapStyleWord(true);
		remarksValue.setLineWrap(true);
		remarksValue.setWrapStyleWord(true);
		
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
		
		nilm = new JRadioButton(TitleConstants.NILM);
		eca = new JRadioButton(TitleConstants.ECA);
		omn = new JRadioButton(TitleConstants.OMN);
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
		
		hormonalEvaluationLabel = new JLabel(TitleConstants.HORM_EVAL);
		superficialsLabel = new JLabel(TitleConstants.SUPERFICIALS);
		intermediatesLabel = new JLabel(TitleConstants.INTERMEDIATES);
		parabasalsLabel = new JLabel(TitleConstants.PARABASALS);
		superficialValue = new JTextField(4);
		intermediatesValue = new JTextField(4);
		parabasalsValue = new JTextField(4);
		parabasalsValue.setDocument(new CustomDocument(4));
		superficialValue.setDocument(new CustomDocument(4));
		intermediatesValue.setDocument(new CustomDocument(4));
		
		nilmGroup = new ButtonGroup();
		organisms = new JRadioButton(TitleConstants.ORGANISMS);
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
		
		onf = new JRadioButton(TitleConstants.ONF);
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
		
		other = new JRadioButton(TitleConstants.OTHER);
		otherGroup = new ButtonGroup();
		other1 = new JRadioButton(TitleConstants.OTHER1);
		otherGroup.add(other1);
		other.setBackground(Color.white);
		other1.setBackground(Color.white);
		nilmGroup.add(organisms);
		nilmGroup.add(onf);
		nilmGroup.add(other);
		
		ecaGroup = new ButtonGroup();
		squamousCell = new JRadioButton(TitleConstants.SQUAMOUS_CELL);
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
		
		glandularCell = new JRadioButton(TitleConstants.GLANDULAR_CELL);
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
		ecaGroup.add(squamousCell);
		ecaGroup.add(glandularCell);
		
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
		
		remarksValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		grossDescValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		microNoteValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		omnArea.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		unsatisfactoryDueTo.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		
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
		List<Result> diagnosis = (List)record.getAttribute(RecordTable.RESULTS);
		if(diagnosis!= null)
		{
			Iterator<Result> i = diagnosis.iterator();
			while(i.hasNext())
			{
				Result curDiagnosis = i.next();
				int category = curDiagnosis.getCategory();
				String value = curDiagnosis.getValue();
				
				switch(category)
				{
				case ResultCategoriesConstants.ORGANISMS: 
				case ResultCategoriesConstants.ONF:
				case ResultCategoriesConstants.OTHER_NILM:	
					
					if(category == ResultCategoriesConstants.ORGANISMS)
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
					else if(category == ResultCategoriesConstants.ONF)
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
				case ResultCategoriesConstants.SC:
				case ResultCategoriesConstants.GC: 
							
					if(category == ResultCategoriesConstants.SC)
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
				case ResultCategoriesConstants.OMN: omn.setSelected(true);
											  omnArea.setText(value);
											  break;
				case ResultCategoriesConstants.SA: if(value.contains(TitleConstants.SATISFACTORY))
												satisfactory.setSelected(true);
											 else if(value.contains(TitleConstants.UNSATISFACTORY))
											 {
												 unsatisfactory.setSelected(true);
												 unsatisfactoryDueTo.setText(
														 value.substring(TitleConstants.UNSATISFACTORY.length() + 1));
											 }
											break;
				case ResultCategoriesConstants.S: superficialValue.setText(value);
				break;
				case ResultCategoriesConstants.I: intermediatesValue.setText(value);
				break;
				case ResultCategoriesConstants.P: parabasalsValue.setText(value);
				break;
				case ResultCategoriesConstants.R: remarksValue.setText(value);
				break;
				case ResultCategoriesConstants.MN: microNoteValue.setText(value);
				break;
				case ResultCategoriesConstants.GD: grossDescValue.setText(value);
				break;
				}
			}
		}
		updateGUI();
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
		superficialValue.setEditable(isEditable);
		intermediatesValue.setEditable(isEditable);
		parabasalsValue.setEditable(isEditable);
		if(!isEditable)
		{
			superficialsLabel.setForeground(Color.gray);
			intermediatesLabel.setForeground(Color.gray);
			parabasalsLabel.setForeground(Color.gray);
			superficialValue.setForeground(Color.gray);
			intermediatesValue.setForeground(Color.gray);
			parabasalsValue.setForeground(Color.gray);
		}
		else
		{
			superficialsLabel.setForeground(Color.black);
			intermediatesLabel.setForeground(Color.black);
			parabasalsLabel.setForeground(Color.black);
			superficialValue.setForeground(Color.black);
			intermediatesValue.setForeground(Color.black);
			parabasalsValue.setForeground(Color.black);
		}
		updateGUI();
	}
	@Override
	public void setPatientEditable(boolean isEditable)
	{
		overviewPanel.setPatientEditable(isEditable);
	}
	
	public void updateGUI()
	{	
		if(!nilm.isSelected())
			nilmGroup.clearSelection();
		
		if(!organisms.isSelected())
			organismsGroup.clearSelection();
		
		organisms.setEnabled(nilm.isSelected() && nilm.isEnabled());
		org1.setEnabled(organisms.isSelected() && organisms.isEnabled());
		org2.setEnabled(organisms.isSelected() && organisms.isEnabled());
		org3.setEnabled(organisms.isSelected() && organisms.isEnabled());
		org4.setEnabled(organisms.isSelected() && organisms.isEnabled());
		org5.setEnabled(organisms.isSelected() && organisms.isEnabled());
		
		if(!onf.isSelected())
			onfGroup.clearSelection();
		
		onf.setEnabled(nilm.isSelected() && nilm.isEnabled());
		onf1.setEnabled(onf.isSelected() && onf.isEnabled()); 
		onf2.setEnabled(onf.isSelected() && onf.isEnabled());
		onf3.setEnabled(onf.isSelected() && onf.isEnabled());
		onf1Box.setEnabled(onf1.isSelected() && onf1.isEnabled());
		
		if(!other.isSelected())
			otherGroup.clearSelection();

		other.setEnabled(nilm.isSelected() && nilm.isEnabled());
		other1.setEnabled(other.isSelected() && other.isEnabled());
		
		if(!eca.isSelected())
			ecaGroup.clearSelection();
		
		if(!squamousCell.isSelected())
			squamousGroup.clearSelection();
		
		squamousCell.setEnabled(eca.isSelected() && eca.isEnabled());
		squamous1.setEnabled(squamousCell.isSelected() && squamousCell.isEnabled());
		squamous2.setEnabled(squamousCell.isSelected() && squamousCell.isEnabled());
		squamous3.setEnabled(squamousCell.isSelected() && squamousCell.isEnabled());
		squamous4.setEnabled(squamousCell.isSelected() && squamousCell.isEnabled());
		squamous1Box.setEnabled(squamous1.isSelected() && squamous1.isEnabled());
		squamous3Box.setEnabled(squamous3.isSelected() && squamous3.isEnabled());
		
		if(!glandularCell.isSelected())
			glandularGroup.clearSelection();
		
		glandularCell.setEnabled(eca.isSelected() && eca.isEnabled());
		glandular1.setEnabled(glandularCell.isSelected() && glandularCell.isEnabled());
		glandular2.setEnabled(glandularCell.isSelected() && glandularCell.isEnabled());
		glandular3.setEnabled(glandularCell.isSelected() && glandularCell.isEnabled());
		glandular1Box.setEnabled(glandular1.isSelected() && glandular1.isEnabled());
		glandular3Box.setEnabled(glandular3.isSelected() && glandular3.isEnabled());
		
		if(!omn.isSelected())
			omnArea.setText("");
		
		omnArea.setEditable(omn.isSelected() && omn.isEnabled());
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
					omnArea.getText().length() > RecordConstants.RESULTS_LENGTH)
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
		CardLayout cl = (CardLayout)cardPanel.getLayout();
		Object source = e.getSource();
		if(source.equals(nextButton) || source.equals(previousButton))
		{
			if(source.equals(nextButton))
			{
				if(page < 3)
				{
					page++;
					cl.next(cardPanel);
				}
			}
			else if(source.equals(previousButton))
			{
				if(page > 1)
				{
					page--;
					cl.previous(cardPanel);
				}
			}
			if(page > 1)
				previousButton.setVisible(true);
			else previousButton.setVisible(false);
			if(page < 3)
				nextButton.setVisible(true);
			else nextButton.setVisible(false);
			pageLabel.setText(page + " of 3");
		}
		else 
		{
			
			JRadioButton button = (JRadioButton) source;
			if(button.equals(nilm) || button.equals(eca) || button.equals(omn))
			{
				if(nilm.isSelected() && !nilm.equals(source))
					nilm.setSelected(false);
				if(eca.isSelected() && !eca.equals(source))
					eca.setSelected(false);
				if(omn.isSelected() && !omn.equals(source))
					omn.setSelected(false);
			}
			updateGUI();
		}
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
		
		nextButton.addActionListener(this);
		previousButton.addActionListener(this);
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
		Record record = overviewPanel.getRecord();
		String specimenType = TitleConstants.OTHERS;
		if(conventionalSmear.isSelected())
			specimenType = TitleConstants.CONVENTIONAL;
		else if(liquidBased.isSelected())
			specimenType = TitleConstants.LIQUID_BASED;
		record.putAttribute(RecordTable.SPEC_TYPE, specimenType);
		
		List<Result> diagnosisList = new Vector<Result>();
		if(satisfactory.isSelected())
			diagnosisList.add(new Result(ResultCategoriesConstants.SA, satisfactory.getText()));
		else if(unsatisfactory.isSelected())
			diagnosisList.add(new Result(ResultCategoriesConstants.SA, 
					unsatisfactory.getText() + " " + unsatisfactoryDueTo.getText()));
		
		if(org1.isSelected() && org1.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.ORGANISMS, org1.getText()));
		else if(org2.isSelected() && org2.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.ORGANISMS, org2.getText()));
		else if(org3.isSelected() && org3.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.ORGANISMS, org3.getText()));
		else if(org4.isSelected() && org4.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.ORGANISMS, org4.getText()));
		else if(org5.isSelected() && org5.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.ORGANISMS, org5.getText()));
		
		if(onf1.isSelected() && onf1.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.ONF, 
					onf1.getText() + " " + onf1Box.getSelectedItem()));
		else if(onf2.isSelected() && onf2.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.ONF, onf2.getText()));
		else if(onf3.isSelected() && onf3.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.ONF, onf3.getText()));
		
		if(other1.isSelected() && other1.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.OTHER_NILM, other1.getText()));
		
		if(squamous1.isSelected() && squamous1.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.SC, 
					squamous1.getText() + " " + squamous1Box.getSelectedItem()));
		else if(squamous2.isSelected() && squamous2.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.SC, squamous2.getText()));
		else if(squamous3.isSelected() && squamous3.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.SC, 
				squamous3.getText() + " " + squamous3Box.getSelectedItem()));
		else if(squamous4.isSelected() && squamous4.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.SC, squamous4.getText()));
		
		if(glandular1.isSelected() && glandular1.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.GC, 
					glandular1.getText() + " " + glandular1Box.getSelectedItem()));
		else if(glandular2.isSelected() && glandular2.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.GC, glandular2.getText()));
		if(glandular3.isSelected() && glandular3.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.GC, 
					glandular3.getText() + " " + glandular3Box.getSelectedItem()));
		
		if(omn.isSelected() && omn.isEnabled())
			diagnosisList.add(new Result(ResultCategoriesConstants.OMN, omnArea.getText()));
		
		if(remarksValue.getText().length() > 0)
		{
			Result remark = new Result(ResultCategoriesConstants.R, remarksValue.getText());
			diagnosisList.add(remark);
		}
		if(grossDescValue.getText().length() > 0)
		{
			Result gd = new Result(ResultCategoriesConstants.GD, grossDescValue.getText());
			diagnosisList.add(gd);
		}
		if(microNoteValue.getText().length() > 0)
		{
			Result mn = new Result(ResultCategoriesConstants.MN, microNoteValue.getText());
			diagnosisList.add(mn);
		}
		if(superficialValue.getText().replaceAll("\\s", "").length() > 0)
			diagnosisList.add(new Result(ResultCategoriesConstants.S, superficialValue.getText()));
		if(intermediatesValue.getText().replaceAll("\\s", "").length() > 0)
			diagnosisList.add(new Result(ResultCategoriesConstants.I, intermediatesValue.getText()));
		if(parabasalsValue.getText().replaceAll("\\s", "").length() > 0)
			diagnosisList.add(new Result(ResultCategoriesConstants.P, parabasalsValue.getText()));
		
		record.putAttribute(RecordTable.RESULTS, diagnosisList);
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
		overviewPanel.getPatientForm().setClearExisting(isEnabled);
	}

	@Override
	public PatientForm getPatientForm() {
		// TODO Auto-generated method stub
		return overviewPanel.getPatientForm();
	} 
	
}