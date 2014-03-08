package org.introse.gui.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
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
import org.introse.Constants.RecordTable;
import org.introse.Constants.StyleConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomCalendar;
import org.introse.core.Diagnosis;
import org.introse.core.GynecologyRecord;
import org.introse.core.Record;
import org.introse.gui.combobox.DatePicker;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.MainMenu;


public class GynecologyForm extends JPanel implements Form, ActionListener
{
	private JTextField refNumberValue;
	private JTextField specimenValue;
	private JTextField physicianValue;
	private JTextField pathologistValue;
	private JTextField roomValue;
	private JTextArea remarksValue;
	private JScrollPane remarksScroller;
	private JScrollPane omnScroller;
	
	private JLabel refNumberLabel;
	private JLabel specimenLabel;
	private JLabel physicianLabel;
	private JLabel pathologistLabel;
	private JLabel dateReceivedLabel;
	private JLabel dateCompletedLabel;
	private JLabel remarksLabel;
	private JLabel roomLabel;
	private DatePicker receivedDate;
	private DatePicker completedDate;
	private JPanel insidePanel;
	private JPanel diagnosisPanel;
	
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
	private JButton showDiagnosis;
	private ImageIcon collapse;
	private ImageIcon uncollapse;
	
	public GynecologyForm()
	{
		super(new GridBagLayout());
		setBackground(Color.white);
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder
				(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
				"Record Information")));
		initializeComponents();
		layoutComponents();
		updateButtons();
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		int y = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,0,0);
		insidePanel.add(refNumberValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		insidePanel.add(refNumberLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridy = y;
		c.insets = new Insets(0,0,0,10);
		insidePanel.add(roomValue, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		insidePanel.add(specimenValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,10);
		insidePanel.add(roomLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		insidePanel.add(specimenLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,0,10);
		insidePanel.add(pathologistValue, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		insidePanel.add(physicianValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,10);
		insidePanel.add(pathologistLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		insidePanel.add(physicianLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = y;
		c.gridx = 0;
		c.insets = new Insets(0,0,0,10);
		insidePanel.add(receivedDate, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		insidePanel.add(completedDate, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y;
		c.gridx = 0;
		c.insets = new Insets(0,0,10,10);
		insidePanel.add(dateReceivedLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		insidePanel.add(dateCompletedLabel, c);
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.CENTER;
		insidePanel.add(showDiagnosis, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,10,0);
		insidePanel.add(diagnosisPanel, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y++;
		c.gridx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,0);
		insidePanel.add(remarksLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 2;
		insidePanel.add(remarksScroller, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(20,20,20,20);
		add(insidePanel, c);
		
		
		y = 0;
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y++;
		///////////////////////////
		c.insets = new Insets(0,0,0,0);
		diagnosisPanel.add(nilm, c);
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		diagnosisPanel.add(organisms, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		diagnosisPanel.add(org1, c);
		c.gridy = y++;
		diagnosisPanel.add(org2, c);
		c.gridy = y++;
		diagnosisPanel.add(org3, c);
		c.gridy = y++;
		diagnosisPanel.add(org4, c);
		c.gridy = y++;
		diagnosisPanel.add(org5, c);
		c.insets = new Insets(0,20,0,0);
		c.gridy = y++;
		diagnosisPanel.add(onf, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		diagnosisPanel.add(onf1, c);
		c.gridy = y++;
		c.insets = new Insets(0,60,0,0);
		diagnosisPanel.add(onf1Box, c);
		c.insets = new Insets(0,40,0,0);
		c.gridy = y++;
		diagnosisPanel.add(onf2, c);
		c.gridy = y++;
		diagnosisPanel.add(onf3, c);
		
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		diagnosisPanel.add(other, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		diagnosisPanel.add(other1, c);
		
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		diagnosisPanel.add(eca, c);
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		diagnosisPanel.add(squamousCell, c);
		c.insets = new Insets(0,40,0,0);
		c.gridy = y++;
		diagnosisPanel.add(squamous1, c);
		c.gridy = y++;
		c.insets = new Insets(0,60,0,0);
		diagnosisPanel.add(squamous1Box, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		diagnosisPanel.add(squamous2, c);
		c.gridy = y++;
		diagnosisPanel.add(squamous3, c);
		c.gridy = y++;
		c.insets = new Insets(0,60,0,0);
		diagnosisPanel.add(squamous3Box, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		diagnosisPanel.add(squamous4, c);
		
		c.gridy = y++;
		c.insets = new Insets(0,20,0,0);
		diagnosisPanel.add(glandularCell, c);
		c.gridy = y++;
		c.insets = new Insets(0,40,0,0);
		diagnosisPanel.add(glandular1, c);
		c.gridy = y++;
		c.insets = new Insets(0,60,0,0);
		diagnosisPanel.add(glandular1Box, c);
		c.insets = new Insets(0,40,0,0);
		c.gridy = y++;
		diagnosisPanel.add(glandular2, c);
		c.gridy = y++;
		diagnosisPanel.add(glandular3, c);
		c.gridy = y++;
		c.insets = new Insets(0,60,0,0);
		diagnosisPanel.add(glandular3Box, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		diagnosisPanel.add(omn, c);
		c.gridy = y++;
		diagnosisPanel.add(omnScroller, c);
	}
	
	private void initializeComponents()
	{	
		collapse = new ImageIcon(getClass().getResource("/res/icons/collapse.png"));
		uncollapse = new ImageIcon(getClass().getResource("/res/icons/uncollapse.png"));
		insidePanel = new JPanel(new GridBagLayout());
		insidePanel.setBackground(Color.white);
		refNumberLabel = new JLabel("Internal Reference Number");
		specimenLabel = new JLabel("Specimen(s)");
		physicianLabel = new JLabel("Physician");
		pathologistLabel = new JLabel("Pathologist");
		dateReceivedLabel = new JLabel("Date Recieved");
		dateCompletedLabel = new JLabel("Date Completed");
		roomLabel = new JLabel("Patient's Room");
		showDiagnosis= new JButton("Interpretation and Result");
		showDiagnosis.setIcon(collapse);
		showDiagnosis.setBorderPainted(false);
		showDiagnosis.setContentAreaFilled(false);
		showDiagnosis.setHorizontalTextPosition(SwingConstants.LEADING);
		showDiagnosis.setFont(MainMenu.SECONDARY_FONT.deriveFont(StyleConstants.HEADER));
		showDiagnosis.setOpaque(true);
		showDiagnosis.setBackground(Color.white);
		showDiagnosis.setIconTextGap(10);
		remarksLabel = new JLabel("Remarks");
		
		refNumberValue= new JTextField(15);
		refNumberValue.setEditable(false);
		specimenValue= new JTextField(15);
		physicianValue= new JTextField(15);
		pathologistValue= new JTextField(15);
		remarksValue= new JTextArea(3,30);
		roomValue = new JTextField(15);
		
		diagnosisPanel = new JPanel(new GridBagLayout());
		diagnosisPanel.setBackground(Color.white);
		diagnosisPanel.setBorder(null);
		nilm = new JCheckBox(TitleConstants.NILM);
		eca = new JCheckBox(TitleConstants.ECA);
		omn = new JCheckBox(TitleConstants.OMN);
		nilm.setBackground(Color.white);
		eca.setBackground(Color.white);
		omn.setBackground(Color.white);
		
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
		remarksScroller = new JScrollPane(remarksValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		remarksScroller.setPreferredSize(new Dimension(500, 70));
		refNumberValue.setHorizontalAlignment(JTextField.CENTER);
		refNumberValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(Font.BOLD).deriveFont(Constants.StyleConstants.SUBHEADER));
		specimenValue.setHorizontalAlignment(JTextField.CENTER);
		specimenValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		physicianValue.setHorizontalAlignment(JTextField.CENTER);
		physicianValue.setFont(specimenValue.getFont());
		pathologistValue.setHorizontalAlignment(JTextField.CENTER);
		pathologistValue.setFont(specimenValue.getFont());
		remarksValue.setFont(specimenValue.getFont());
		roomValue.setFont(specimenValue.getFont());
		roomValue.setHorizontalAlignment(JTextField.CENTER);
		receivedDate = new DatePicker(50);
		completedDate = new DatePicker(50);
		receivedDate.setPickerFont(specimenValue.getFont());
		completedDate.setPickerFont(specimenValue.getFont());

		Calendar c = Calendar.getInstance();
		receivedDate.setDate(c);
		completedDate.setDate(c);
		diagnosisPanel.setVisible(false);
	}
	
	public void setFields(Object object)
	{	
		Record record = (Record)object;
		String refNumber = (String)record.getAttribute(RecordTable.REF_NUM.toString());
		String specimen = (String)record.getAttribute(RecordTable.SPECIMEN.toString());
		String physician = (String)record.getAttribute(RecordTable.PHYSICIAN.toString());
		String pathologist = (String)record.getAttribute(RecordTable.PATHOLOGIST.toString());
		String room = (String)record.getAttribute(RecordTable.ROOM);
		if(refNumber != null)
			refNumberValue.setText(refNumber);
		if(specimen != null)
			specimenValue.setText(specimen);
		if(physician != null)
			physicianValue.setText(physician);
		if(pathologist != null)
			pathologistValue.setText(pathologist);
		if(room!= null)
			roomValue.setText(room);
		
		CustomCalendar dateReceived = (CustomCalendar)record.getAttribute(RecordTable.DATE_RECEIVED);
		if(dateReceived != null)
			receivedDate.setDate(dateReceived);
		
		CustomCalendar dateCompleted = (CustomCalendar)record.getAttribute(RecordTable.DATE_COMPLETED);
		if(dateCompleted != null)
			completedDate.setDate(dateCompleted);
		
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
				}
			}
		}
		String remarks = (String)record.getAttribute(RecordTable.REMARKS.toString());
		if(remarks != null)
		remarksValue.setText(remarks);
		updateButtons();
	}
	
	public void setEditable(boolean isEditable)
	{
		receivedDate.setEnabled(isEditable);
		completedDate.setEnabled(isEditable);
		specimenValue.setEditable(isEditable);
		physicianValue.setEditable(isEditable);
		pathologistValue.setEditable(isEditable);
		remarksValue.setEditable(isEditable);
		roomValue.setEditable(isEditable);
		nilm.setEnabled(isEditable);
		eca.setEnabled(isEditable);
		omn.setEnabled(isEditable);
		updateButtons();
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
	}
	
	public boolean areFieldsValid()
	{
		int dayReceived = receivedDate.getDay();
		int monthReceived = receivedDate.getMonth();
		int yearReceived = receivedDate.getYear();
		int dayCompleted = completedDate.getDay();
		int monthCompleted = completedDate.getMonth();
		int yearCompleted = completedDate.getYear();

		if(!(specimenValue.getText().length() > 0))
			return false;
		if(!(physicianValue.getText().length() > 0))
			return false;
		if(!(pathologistValue.getText().length() > 0))
			return false;
		if(!(remarksValue.getText().length() > 0))
			return false;
		if((monthReceived == 3 || monthReceived == 5 || 
				monthReceived == 8 || monthReceived == 10) && dayReceived > 29)
			return false;
		if((monthCompleted == 3 || monthCompleted == 5 || 
				monthCompleted == 8 || monthCompleted == 10) && dayCompleted > 29)
			return false;
		if(monthCompleted == 1 && !(yearCompleted % 4 == 0 && yearCompleted % 100 != 0 || yearCompleted % 400 == 0) 
				&& dayCompleted > 27)
			return false;
		if(monthReceived == 1 && !(yearReceived % 4 == 0 && yearReceived % 100 != 0 || yearReceived % 400 == 0) 
				&& dayReceived > 27)
			return false;
		if(monthCompleted == 1 && dayCompleted > 28)
			return false;
		if(monthReceived == 1 && dayReceived > 28)
			return false;
		
		if(!nilm.isSelected() && !eca.isSelected() && !omn.isSelected())
			return false;
		if(nilm.isSelected())
		{
			if(!organisms.isSelected() && !onf.isSelected() && !other.isSelected())
				return false;
			
			if(organisms.isSelected())
			{
				if(!org1.isSelected() && !org2.isSelected() && 
						!org3.isSelected() && !org4.isSelected() && !org5.isSelected())
					return false;
			}
			if(onf.isSelected())
			{
				if(!onf1.isSelected() && !onf2.isSelected() && !onf3.isSelected())
					return false;
			}
			if(other.isSelected())
				if(!other1.isSelected())
					return false;
		}
		if(eca.isSelected())
		{
			if(!squamousCell.isSelected() && !glandularCell.isSelected())
				return false;
			if(squamousCell.isSelected())
			{
				if(!squamous1.isSelected() && !squamous2.isSelected() && !squamous3.isSelected()
						&& !squamous4.isSelected())
					return false;
			}
			if(glandularCell.isSelected())
			{
				if(!glandular1.isSelected() && !glandular2.isSelected() && !glandular3.isSelected())
					return false;
			}
		}
		if(omn.isSelected())
			if(omnArea.getText().replaceAll("\\s", "").length() < 1)
				return false;
		
		return true;
	}
	
	public Object getObject()
	{
		String referenceNumber = refNumberValue.getText();
		Record record = new GynecologyRecord();
		record.putAttribute(RecordTable.REF_NUM.toString(), referenceNumber);
		record.putAttribute(RecordTable.SPECIMEN.toString(), specimenValue.getText());
		record.putAttribute(RecordTable.PATHOLOGIST.toString(), pathologistValue.getText());
		record.putAttribute(RecordTable.PHYSICIAN.toString(), physicianValue.getText());
		record.putAttribute(RecordTable.REMARKS.toString(), remarksValue.getText());
		record.putAttribute(RecordTable.RECORD_TYPE.toString(), Constants.RecordConstants.GYNECOLOGY_RECORD);
		if(roomValue.getText().replaceAll("\\s", "").length() > 0)
			record.putAttribute(RecordTable.ROOM, roomValue.getText());
		CustomCalendar dateReceived = new CustomCalendar();
		int dayReceived = receivedDate.getDay();
		int monthReceived = receivedDate.getMonth();
		int yearReceived = receivedDate.getYear();
		dateReceived.set(monthReceived, dayReceived, yearReceived);
		
		CustomCalendar dateCompleted = new CustomCalendar();
		int dayCompleted = completedDate.getDay();
		int monthCompleted = completedDate.getMonth();
		int yearCompleted = completedDate.getYear();
		dateCompleted.set(monthCompleted, dayCompleted, yearCompleted);
		record.putAttribute(RecordTable.DATE_RECEIVED.toString(), dateReceived);
		record.putAttribute(RecordTable.DATE_COMPLETED.toString(), dateCompleted);
		
		List<Diagnosis> diagnosisList = new Vector<Diagnosis>();
		
		if(org1.isSelected() && org1.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ORGANISMS, org1.getText(), referenceNumber));
		else if(org2.isSelected() && org2.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ORGANISMS, org2.getText(), referenceNumber));
		else if(org3.isSelected() && org3.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ORGANISMS, org3.getText(), referenceNumber));
		else if(org4.isSelected() && org4.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ORGANISMS, org4.getText(), referenceNumber));
		else if(org5.isSelected() && org5.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ORGANISMS, org5.getText(), referenceNumber));
		
		if(onf1.isSelected() && onf1.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ONF, 
					onf1.getText() + " " + onf1Box.getSelectedItem(), referenceNumber));
		else if(onf2.isSelected() && onf2.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ONF, onf2.getText(), referenceNumber));
		else if(onf3.isSelected() && onf3.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.ONF, onf3.getText(), referenceNumber));
		
		if(other1.isSelected() && other1.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.OTHER, other1.getText(), referenceNumber));
		
		if(squamous1.isSelected() && squamous1.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.SC, 
					squamous1.getText() + " " + squamous1Box.getSelectedItem(), referenceNumber));
		else if(squamous2.isSelected() && squamous2.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.SC, squamous2.getText(), referenceNumber));
		else if(squamous3.isSelected() && squamous3.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.SC, 
				squamous3.getText() + " " + squamous3Box.getSelectedItem(), referenceNumber));
		else if(squamous4.isSelected() && squamous4.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.SC, squamous4.getText(), referenceNumber));
		
		if(glandular1.isSelected() && glandular1.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.GC, 
					glandular1.getText() + " " + glandular1Box.getSelectedItem(), referenceNumber));
		else if(glandular2.isSelected() && glandular2.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.GC, glandular2.getText(), referenceNumber));
		if(glandular3.isSelected() && glandular3.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.GC, 
					glandular3.getText() + " " + glandular3Box.getSelectedItem(), referenceNumber));
		
		if(omn.isSelected() && omn.isEnabled())
			diagnosisList.add(new Diagnosis(CategoriesConstants.OMN, omnArea.getText(), referenceNumber));
		record.putAttribute(RecordTable.DIAGNOSIS, diagnosisList);
		return record;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(showDiagnosis))
		{
			diagnosisPanel.setVisible(!diagnosisPanel.isVisible());
			revalidate();
			if(diagnosisPanel.isVisible())
				showDiagnosis.setIcon(uncollapse);
			else showDiagnosis.setIcon(collapse);
		}
		else updateButtons();
	}

	@Override
	public void addListener(CustomListener listener) 
	{
		showDiagnosis.addMouseListener(listener);
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
		showDiagnosis.addActionListener(this);
		
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
	}
	
}