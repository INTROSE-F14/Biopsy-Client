package org.introse.gui.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.introse.Constants;
import org.introse.Constants.CategoriesConstants;
import org.introse.Constants.RecordTable;
import org.introse.core.CustomCalendar;
import org.introse.core.CytologyRecord;
import org.introse.core.Diagnosis;
import org.introse.core.Record;
import org.introse.gui.combobox.DatePicker;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.MainMenu;


public class CytologyForm extends JPanel implements Form
{

	private JTextField refNumberValue;
	private JTextField specimenValue;
	private JTextField physicianValue;
	private JTextField pathologistValue;
	private JTextArea diagnosisValue;
	private JTextArea remarksValue;
	private JTextField roomValue;
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
	private JLabel roomLabel;
	private JPanel insidePanel;
	
	private DatePicker receivedDate;
	private DatePicker completedDate;
	
	public CytologyForm()
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
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.LINE_START;
		insidePanel.add(diagnosisLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,10,0);
		insidePanel.add(diagnosisScroller, c);
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
		roomLabel = new JLabel("Patient's Room");
		refNumberValue= new JTextField(15);
		refNumberValue.setEditable(false);
		specimenValue= new JTextField(15);
		physicianValue= new JTextField(15);
		pathologistValue= new JTextField(15);
		diagnosisValue= new JTextArea(3,30);
		remarksValue= new JTextArea(3,30);
		roomValue = new JTextField(15);
		
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
		roomValue.setFont(specimenValue.getFont());
		roomValue.setHorizontalAlignment(JTextField.CENTER);
		receivedDate = new DatePicker(50);
		completedDate = new DatePicker(50);
	
		Calendar c = Calendar.getInstance();
		receivedDate.setDate(c);
		completedDate.setDate(c);

		completedDate.setPickerFont(specimenValue.getFont());
		receivedDate.setPickerFont(specimenValue.getFont());
		
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
		
		CustomCalendar dateReceived = (CustomCalendar)record.getAttribute(RecordTable.DATE_RECEIVED.toString());
		if(dateReceived != null)
			receivedDate.setDate(dateReceived);
		
		CustomCalendar dateCompleted = (CustomCalendar)record.getAttribute(RecordTable.DATE_COMPLETED.toString());
		if(dateCompleted != null)
			completedDate.setDate(dateCompleted);
		
		List<Diagnosis> diagnosis = (List)record.getAttribute(RecordTable.DIAGNOSIS);
		if(diagnosis != null && diagnosis.size() > 0)
			diagnosisValue.setText(diagnosis.get(0).getValue());
		
		String remarks = (String)record.getAttribute(RecordTable.REMARKS.toString());
		if(remarks != null)
		remarksValue.setText(remarks);
	}
	
	public void setEditable(boolean isEditable)
	{
		receivedDate.setEnabled(isEditable);
		completedDate.setEnabled(isEditable);
		specimenValue.setEditable(isEditable);
		physicianValue.setEditable(isEditable);
		pathologistValue.setEditable(isEditable);
		diagnosisValue.setEditable(isEditable);
		remarksValue.setEditable(isEditable);
		roomValue.setEditable(isEditable);
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
		Record record = new CytologyRecord();
		record.putAttribute(RecordTable.REF_NUM.toString(), refNumberValue.getText());
		record.putAttribute(RecordTable.SPECIMEN.toString(), specimenValue.getText());
		record.putAttribute(RecordTable.PATHOLOGIST.toString(), pathologistValue.getText());
		record.putAttribute(RecordTable.PHYSICIAN.toString(), physicianValue.getText());
		record.putAttribute(RecordTable.REMARKS.toString(), remarksValue.getText());
		record.putAttribute(RecordTable.RECORD_TYPE.toString(), Constants.RecordConstants.CYTOLOGY_RECORD);
		if(roomValue.getText().replaceAll("\\s", "").length() > 0)
			record.putAttribute(RecordTable.ROOM, roomValue.getText());
		CustomCalendar dateReceived = new CustomCalendar();
		int dayReceived = receivedDate.getDay();
		int monthReceived = receivedDate.getMonth();
		int yearReceived = receivedDate.getYear();
		dateReceived.set(monthReceived, dayReceived, yearReceived);;
		
		CustomCalendar dateCompleted = new CustomCalendar();
		int dayCompleted = completedDate.getDay();
		int monthCompleted = completedDate.getMonth();
		int yearCompleted = completedDate.getYear();
		dateCompleted.set(monthCompleted, dayCompleted, yearCompleted);
		record.putAttribute(RecordTable.DATE_RECEIVED.toString(), dateReceived);
		record.putAttribute(RecordTable.DATE_COMPLETED.toString(), dateCompleted);
		
		Diagnosis diagnosis = new Diagnosis(CategoriesConstants.OTHERS, diagnosisValue.getText(), 
				refNumberValue.getText());
		List<Diagnosis> newDiagnosis = new Vector<Diagnosis>();
		newDiagnosis.add(diagnosis);
		record.putAttribute(RecordTable.DIAGNOSIS, newDiagnosis);
		
		return record;
	}

	@Override
	public void addListener(CustomListener listener) {
		// TODO Auto-generated method stub
		
	}

	
}