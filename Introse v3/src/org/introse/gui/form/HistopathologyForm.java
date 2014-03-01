package org.introse.gui.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.introse.Constants;
import org.introse.Constants.RecordTable;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Record;
import org.introse.gui.window.MainMenu;

public class HistopathologyForm extends JPanel implements Form
{
	private JTextField refNumberValue;
	private JTextField specimenValue;
	private JTextField physicianValue;
	private JTextField pathologistValue;
	private JTextArea diagnosisValue;
	private JTextArea remarksValue;
	private JScrollPane diagnosisScroller;
	private JScrollPane remarksScroller;
	
	private JLabel refNumberLabel;
	private JLabel specimenLabel;
	private JLabel physicianLabel;
	private JLabel pathologistLabel;
	private JLabel dateReceivedLabel;
	private JLabel dateCompletedLabel;
	private JLabel diagnosisLabel;
	private JLabel remarksLabel;
	
	private JComboBox<String> dayReceived;
	private JComboBox<String> monthReceived;
	private JComboBox<String> yearReceived;
	private JComboBox<String> dayCompleted;
	private JComboBox<String> monthCompleted;
	private JComboBox<String> yearCompleted;
	private JPanel dateReceivedPanel;
	private JPanel dateCompletedPanel;
	private JPanel insidePanel;
	
	public HistopathologyForm()
	{
		super(new GridBagLayout());
		setBackground(Color.white);
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder
				(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
				"Record Information")));
		initializeComponents();
		layoutComponents();
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.insets = new Insets(0,0,5,5);
		dateReceivedPanel.add(dayReceived, c);
		c.gridx = 1;
		dateReceivedPanel.add(monthReceived, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,5,0);
		dateReceivedPanel.add(yearReceived, c);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,5,5);
		dateCompletedPanel.add(dayCompleted, c);
		c.gridx = 1;
		dateCompletedPanel.add(monthCompleted, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,5,0);
		dateCompletedPanel.add(yearCompleted, c);

		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,0,10);
		insidePanel.add(refNumberValue, c);
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0,0,0,0);
		insidePanel.add(specimenValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,0,10,10);
		insidePanel.add(refNumberLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0,0,10,0);
		insidePanel.add(specimenLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0,0,0,10);
		insidePanel.add(pathologistValue, c);
		c.gridx = 1;
		c.insets = new Insets(0,0,0,0);
		insidePanel.add(physicianValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0,0,10,10);
		insidePanel.add(pathologistLabel, c);
		c.gridx = 1;
		c.insets = new Insets(0,0,10,0);
		insidePanel.add(physicianLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 4;
		c.gridx = 0;
		c.insets = new Insets(0,0,0,10);
		insidePanel.add(dateReceivedPanel, c);
		c.gridx = 1;
		c.insets = new Insets(0,0,0,0);
		insidePanel.add(dateCompletedPanel, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = 5;
		c.gridx = 0;
		c.insets = new Insets(0,0,10,10);
		insidePanel.add(dateReceivedLabel, c);
		c.gridx = 1;
		c.insets = new Insets(0,0,10,0);
		insidePanel.add(dateCompletedLabel, c);
		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(0,0,0,0);
		insidePanel.add(diagnosisLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,10,0);
		insidePanel.add(diagnosisScroller, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = 8;
		c.gridx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,0);
		insidePanel.add(remarksLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 2;
		insidePanel.add(remarksScroller, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(20,20,20,20);
		add(insidePanel, c);
	}
	
	private void initializeComponents()
	{	
		insidePanel = new JPanel(new GridBagLayout());
		insidePanel.setBackground(Color.white);
		refNumberLabel = new JLabel("Internal Reference Number");
		specimenLabel = new JLabel("Specimen(s)");
		physicianLabel = new JLabel("Physician");
		pathologistLabel = new JLabel("Pathologist");
		dateReceivedLabel = new JLabel("Date Recieved");
		dateCompletedLabel = new JLabel("Date Completed");
		diagnosisLabel = new JLabel("Diagnosis");
		remarksLabel = new JLabel("Remarks");
		
		refNumberValue= new JTextField(15);
		refNumberValue.setEditable(false);
		specimenValue= new JTextField(15);
		physicianValue= new JTextField(15);
		pathologistValue= new JTextField(15);
		diagnosisValue= new JTextArea(3,30);
		remarksValue= new JTextArea(3,30);
		
		diagnosisScroller = new JScrollPane(diagnosisValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		remarksScroller = new JScrollPane(remarksValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		diagnosisScroller.setPreferredSize(new Dimension(500, 70));
		remarksScroller.setPreferredSize(new Dimension(500, 70));
		refNumberValue.setHorizontalAlignment(JTextField.CENTER);
		refNumberValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(Font.BOLD).deriveFont(Constants.StyleConstants.SUBHEADER));
		specimenValue.setHorizontalAlignment(JTextField.CENTER);
		specimenValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		physicianValue.setHorizontalAlignment(JTextField.CENTER);
		physicianValue.setFont(specimenValue.getFont());
		pathologistValue.setHorizontalAlignment(JTextField.CENTER);
		pathologistValue.setFont(specimenValue.getFont());
		diagnosisValue.setFont(specimenValue.getFont().deriveFont(Constants.StyleConstants.MENU));
		remarksValue.setFont(diagnosisValue.getFont());
		
		String[] monthNames = {"January", "February", 
				 "March", "April", "May", "June", "July", "August", 
				 "September", "October", "November", "December"};
		String[] monthDays = {"1","2", "3","4","5","6","7","8",
				"9","10","11","12","13","14","15","16","17","18","19","20",
				"21","22","23","24","25","26","27","28","29","30","31"};
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String[] years = new String[61];
		int j = 0;
		for(int i = currentYear - 50; i <= currentYear + 10; i++)
		{
			years[j] = "" + i;
			j++;
		}
		dayReceived = new JComboBox<String>(monthDays);
		monthReceived = new JComboBox<String>(monthNames);
		yearReceived = new JComboBox<String>(years);
		dayReceived.setBorder(null);
		monthReceived.setBorder(null);
		yearReceived.setBorder(null);
		dayCompleted = new JComboBox<String>(monthDays);
		monthCompleted = new JComboBox<String>(monthNames);
		yearCompleted = new JComboBox<String>(years);
		dayCompleted.setBorder(null);
		monthCompleted.setBorder(null);
		yearCompleted.setBorder(null);
		
		dateReceivedPanel = new JPanel(new GridBagLayout());
		dateCompletedPanel = new JPanel(new GridBagLayout());
		dateReceivedPanel.setBackground(Color.white);
		dateCompletedPanel.setBackground(Color.white);
	
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		int year = c.get(Calendar.YEAR);
		dayReceived.setSelectedItem(""+day);
		monthReceived.setSelectedIndex(month);
		yearReceived.setSelectedItem(""+year);
		dayCompleted.setSelectedItem(""+day);
		monthCompleted.setSelectedIndex(month);
		yearCompleted.setSelectedItem(""+year);

		dayReceived.setFont(specimenValue.getFont());
		monthReceived.setFont(specimenValue.getFont());
		yearReceived.setFont(specimenValue.getFont());
		dayCompleted.setFont(specimenValue.getFont());
		monthCompleted.setFont(specimenValue.getFont());
		yearCompleted.setFont(specimenValue.getFont());
		
	}
	
	public void setFields(Object object)
	{	
		Record record = (Record)object;
		String refNumber = (String)record.getAttribute(RecordTable.REF_NUM.toString());
		String specimen = (String)record.getAttribute(RecordTable.SPECIMEN.toString());
		String physician = (String)record.getAttribute(RecordTable.PHYSICIAN.toString());
		String pathologist = (String)record.getAttribute(RecordTable.PATHOLOGIST.toString());
		
		if(refNumber != null)
		refNumberValue.setText(refNumber);
		if(specimen != null)
		specimenValue.setText(specimen);
		if(physician != null)
		physicianValue.setText(physician);
		if(pathologist != null)
		pathologistValue.setText(pathologist);
		
		Calendar dateReceived = (Calendar)record.getAttribute(RecordTable.DATE_RECEIVED.toString());
		if(dateReceived != null)
		{
			int month = dateReceived.get(Calendar.MONTH);
			int day = dateReceived.get(Calendar.DATE);
			int year = dateReceived.get(Calendar.YEAR);
			dayReceived.setSelectedItem(""+day);
			monthReceived.setSelectedIndex(month);
			yearReceived.setSelectedItem(""+year);
		}
		
		Calendar dateCompleted = (Calendar)record.getAttribute(RecordTable.DATE_COMPLETED.toString());
		if(dateCompleted != null)
		{
			int month = dateCompleted.get(Calendar.MONTH);
			int day = dateCompleted.get(Calendar.DATE);
			int year = dateCompleted.get(Calendar.YEAR);
			dayCompleted.setSelectedItem(""+day);
			monthCompleted.setSelectedIndex(month);
			yearCompleted.setSelectedItem("" + year);
		}
		
		String diagnosis = (String)record.getAttribute(RecordTable.DIAGNOSIS.toString());
		String remarks = (String)record.getAttribute(RecordTable.REMARKS.toString());
		if(diagnosis != null)
		diagnosisValue.setText(diagnosis);
		if(remarks != null)
		remarksValue.setText(remarks);
	}
	
	public void setEditable(boolean isEditable)
	{
		yearReceived.setEnabled(isEditable);
		monthReceived.setEnabled(isEditable);
		dayReceived.setEnabled(isEditable);
		yearCompleted.setEnabled(isEditable);
		monthCompleted.setEnabled(isEditable);
		dayCompleted.setEnabled(isEditable);
		specimenValue.setEditable(isEditable);
		physicianValue.setEditable(isEditable);
		pathologistValue.setEditable(isEditable);
		diagnosisValue.setEditable(isEditable);
		remarksValue.setEditable(isEditable);
	}
	
	public boolean areFieldsValid()
	{
		int dayReceived = this.dayReceived.getSelectedIndex();
		int monthReceived = this.monthReceived.getSelectedIndex();
		int yearReceived = Integer.parseInt((String)this.yearReceived.getSelectedItem());
		int dayCompleted = this.dayCompleted.getSelectedIndex();
		int monthCompleted = this.monthCompleted.getSelectedIndex();
		int yearCompleted = Integer.parseInt((String)this.yearCompleted.getSelectedItem());

		if(!(specimenValue.getText().length() > 0))
			return false;
		if(!(physicianValue.getText().length() > 0))
			return false;
		if(!(pathologistValue.getText().length() > 0))
			return false;
		if(!(diagnosisValue.getText().length() > 0))
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
		return true;
	}
	
	public Object getObject()
	{
		Record record = new HistopathologyRecord();
		record.putAttribute(RecordTable.REF_NUM.toString(), refNumberValue.getText());
		record.putAttribute(RecordTable.SPECIMEN.toString(), specimenValue.getText());
		record.putAttribute(RecordTable.PATHOLOGIST.toString(), pathologistValue.getText());
		record.putAttribute(RecordTable.PHYSICIAN.toString(), physicianValue.getText());
		record.putAttribute(RecordTable.REMARKS.toString(), remarksValue.getText());
		record.putAttribute(RecordTable.DIAGNOSIS.toString(), diagnosisValue.getText());
		record.putAttribute(RecordTable.RECORD_TYPE.toString(), Constants.RecordConstants.HISTOPATHOLOGY_RECORD);
		Calendar receivedDate = Calendar.getInstance();
		int dayReceived = Integer.parseInt((String)this.dayReceived.getSelectedItem());
		int monthReceived = this.monthReceived.getSelectedIndex();
		int yearReceived = Integer.parseInt((String)this.yearReceived.getSelectedItem());
		receivedDate.clear();
		receivedDate.set(yearReceived, monthReceived, dayReceived);
		
		Calendar completedDate = Calendar.getInstance();
		int dayCompleted = Integer.parseInt((String)this.dayCompleted.getSelectedItem());
		int monthCompleted = this.monthCompleted.getSelectedIndex();
		int yearCompleted = Integer.parseInt((String)this.yearCompleted.getSelectedItem());
		completedDate.clear();
		completedDate.set(yearCompleted, monthCompleted, dayCompleted);
		record.putAttribute(RecordTable.DATE_RECEIVED.toString(), receivedDate);
		record.putAttribute(RecordTable.DATE_COMPLETED.toString(), completedDate);
		
		return record;
	}
}