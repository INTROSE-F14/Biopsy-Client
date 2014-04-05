package org.introse.gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.introse.Constants;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.core.CustomCalendar;
import org.introse.core.CustomDocument;
import org.introse.core.CytologyRecord;
import org.introse.core.GynecologyRecord;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.gui.combobox.DatePicker;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.PatientForm;
import org.introse.gui.window.MainMenu;

public class RecordOverview extends JPanel 
{

	private JTextField specimenValue, physicianValue, pathologistValue, roomValue;
	private JLabel refNumberLabel,  refNumberValue, specimenLabel, physicianLabel, pathologistLabel, dateReceivedLabel,
	dateCompletedLabel, roomLabel;
	private DatePicker receivedDate, completedDate;
	private char recordType;
	private PatientForm patientForm;
	private JPanel recordForm;
	
	public RecordOverview(char recordType)
	{
		super(new GridBagLayout());
		this.recordType = recordType;
		initializeComponents();
		layoutComponents();
	}
	
	private void initializeComponents()
	{	
		patientForm = new PatientForm();
		recordForm = new JPanel(new GridBagLayout());
		recordForm.setBackground(Color.white);
		refNumberLabel = new JLabel("Internal Reference Number");
		specimenLabel = new JLabel("Specimen(s)");
		physicianLabel = new JLabel("Physician");
		pathologistLabel = new JLabel("Pathologist");
		dateReceivedLabel = new JLabel("Date Recieved");
		dateCompletedLabel = new JLabel("Date Completed");
		roomLabel = new JLabel("Patient's Room");
		refNumberValue= new JLabel();
		specimenValue= new JTextField(30);
		physicianValue= new JTextField(30);
		pathologistValue= new JTextField(30);
		roomValue = new JTextField(30);
		
		refNumberValue.setHorizontalAlignment(JTextField.CENTER);
		refNumberValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(Font.BOLD).deriveFont(Constants.StyleConstants.SUBHEADER));
		specimenValue.setHorizontalAlignment(JTextField.CENTER);
		specimenValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		physicianValue.setHorizontalAlignment(JTextField.CENTER);
		physicianValue.setFont(specimenValue.getFont());
		pathologistValue.setHorizontalAlignment(JTextField.CENTER);
		pathologistValue.setFont(specimenValue.getFont());
		roomValue.setFont(specimenValue.getFont());
		roomValue.setHorizontalAlignment(JTextField.CENTER);
		receivedDate = new DatePicker(50, false);
		completedDate = new DatePicker(50, false);
	
		Calendar c = Calendar.getInstance();
		receivedDate.setDate(c);
		completedDate.setDate(c);

		completedDate.setPickerFont(specimenValue.getFont());
		receivedDate.setPickerFont(specimenValue.getFont());
		
		specimenValue.setDocument(new CustomDocument(RecordConstants.SPECIMEN_LENGTH));
		physicianValue.setDocument(new CustomDocument(RecordConstants.PHYSICIAN_LENGTH));
		pathologistValue.setDocument(new CustomDocument(RecordConstants.PATHOLOGIST_LENGTH));
		roomValue.setDocument(new CustomDocument(RecordConstants.ROOM_LENGTH));
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int y = 0;
		c.gridwidth = 1;
		c.gridy = y;
		c.insets = new Insets(0,0,0,35);
		recordForm.add(roomValue, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		recordForm.add(specimenValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,35);
		recordForm.add(roomLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		recordForm.add(specimenLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,0,35);
		recordForm.add(pathologistValue, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		recordForm.add(physicianValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,35);
		recordForm.add(pathologistLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		recordForm.add(physicianLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = y;
		c.gridx = 0;
		c.insets = new Insets(0,0,0,35);
		recordForm.add(receivedDate, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		recordForm.add(completedDate, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y;
		c.gridx = 0;
		c.insets = new Insets(0,0,10,35);
		recordForm.add(dateReceivedLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		recordForm.add(dateCompletedLabel, c);
		
		c = new GridBagConstraints();
		y = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,0,0);
		add(refNumberValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y++;
		c.insets = new Insets(0,0,20,0);
		add(refNumberLabel, c);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		add(patientForm, c);
		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 0;
		c.gridy = y;
		add(recordForm, c);
	}
	
	public void setRecordFields(Object object)
	{	
		Record record = (Record)object;
		String refNumber = null;
		if(record.getAttribute(RecordTable.RECORD_NUMBER) != null)
		{
			int number = (int)record.getAttribute(RecordTable.RECORD_NUMBER);
			int i;
			if(number > 999)
				i = 4;
			else if(number > 99)
				i = 3;
			else if(number > 9)
				i = 2;
			else i = 1;
			refNumber = "" + number;
			for(int j = i; j < 4; j++)
			{
				refNumber = "0" + refNumber;
			}
			refNumber = ""+record.getAttribute(RecordTable.RECORD_TYPE) + 
				record.getAttribute(RecordTable.RECORD_YEAR) + "-" + refNumber;
		}
		String specimen = (String)record.getAttribute(RecordTable.SPECIMEN.toString());
		String physician = (String)record.getAttribute(RecordTable.PHYSICIAN.toString());
		String pathologist = (String)record.getAttribute(RecordTable.PATHOLOGIST.toString());
		String room = (String)record.getAttribute(RecordTable.ROOM);
		if(refNumber != null)
			refNumberValue.setText(refNumber);
		else refNumberValue.setText("Waiting for Completion");
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
		
		JTextField defaultTextField = new JTextField();
		specimenValue.setBorder(defaultTextField.getBorder());
		physicianValue.setBorder(defaultTextField.getBorder());
		pathologistValue.setBorder(defaultTextField.getBorder());
	}
	
	public void setPatientFields(Patient patient)
	{
		patientForm.setFields(patient);
	}
	
	public void setRecordEditable(boolean isEditable)
	{
		receivedDate.setEnabled(isEditable);
		completedDate.setEnabled(isEditable);
		specimenValue.setEditable(isEditable);
		physicianValue.setEditable(isEditable);
		pathologistValue.setEditable(isEditable);
		roomValue.setEditable(isEditable);
	}
	
	public void setPatientEditable(boolean isEditable)
	{
		patientForm.setViewOnly(!isEditable);
	}
	
	public boolean areFieldsValid()
	{
		boolean isValid = true;
		JTextField defaultTextField = new JTextField();
		if(!(specimenValue.getText().length() > 0) || 
				specimenValue.getText().length() > RecordConstants.SPECIMEN_LENGTH)
		{
			specimenValue.setBorder(BorderFactory.createLineBorder(Color.red));
			isValid = false;
		}
		else specimenValue.setBorder(defaultTextField.getBorder());
		if(!(physicianValue.getText().length() > 0) || 
				physicianValue.getText().length() > RecordConstants.PHYSICIAN_LENGTH)
		{
			physicianValue.setBorder(BorderFactory.createLineBorder(Color.red));
			isValid = false;
		}
		else physicianValue.setBorder(defaultTextField.getBorder());
		if(!(pathologistValue.getText().length() > 0) ||
				pathologistValue.getText().length() > RecordConstants.PATHOLOGIST_LENGTH)
		{
			pathologistValue.setBorder(BorderFactory.createLineBorder(Color.red));
			isValid = false;
		}
		else pathologistValue.setBorder(defaultTextField.getBorder());
		if(!patientForm.areFieldsValid())
			isValid = false;
		return isValid;
	}
	
	public Record getRecord()
	{
		Record record = null;
		switch(recordType)
		{
		case RecordConstants.HISTOPATHOLOGY_RECORD: 
			record = new HistopathologyRecord();
			break;
		case RecordConstants.GYNECOLOGY_RECORD: 
			record = new GynecologyRecord();
			break;
		case RecordConstants.CYTOLOGY_RECORD: 
			record = new CytologyRecord();
		}

		record.putAttribute(RecordTable.RECORD_TYPE, recordType);
		if(!refNumberValue.getText().equals("Waiting for Completion"))
		{
			record.putAttribute(RecordTable.RECORD_YEAR, Integer.parseInt(refNumberValue.getText().substring(1, 3)));
			record.putAttribute(RecordTable.RECORD_NUMBER, Integer.parseInt(refNumberValue.getText().substring(4, 8)));
		}
		else
		{
			String year = Calendar.getInstance().get(Calendar.YEAR)+"";
			record.putAttribute(RecordTable.RECORD_YEAR, Integer.parseInt(year.substring(2, 4)));
		}
		record.putAttribute(RecordTable.SPECIMEN.toString(), specimenValue.getText());
		record.putAttribute(RecordTable.PATHOLOGIST.toString(), pathologistValue.getText());
		record.putAttribute(RecordTable.PHYSICIAN.toString(), physicianValue.getText());
		
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
		
		record.putAttribute(RecordTable.PATIENT, patientForm.getObject());
		return record;
	}
	
	public Patient getPatient()
	{
		return (Patient)patientForm.getObject();
	}
	
	public PatientForm getPatientForm()
	{
		return patientForm;
	}
	
	public void addListener(CustomListener listener)
	{
		patientForm.addListener(listener);
	}
}
