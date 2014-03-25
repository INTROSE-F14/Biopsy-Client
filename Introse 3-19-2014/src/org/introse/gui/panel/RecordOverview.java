package org.introse.gui.panel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.introse.Constants;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.core.CustomCalendar;
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
	private int recordType;
	private PatientForm patientForm;
	
	public RecordOverview(int recordType)
	{
		super(new GridBagLayout());
		this.recordType = recordType;
		initializeComponents();
		layoutComponents();
	}
	
	private void initializeComponents()
	{	
		patientForm = new PatientForm();
		refNumberLabel = new JLabel("Internal Reference Number");
		specimenLabel = new JLabel("Specimen(s)");
		physicianLabel = new JLabel("Physician");
		pathologistLabel = new JLabel("Pathologist");
		dateReceivedLabel = new JLabel("Date Recieved");
		dateCompletedLabel = new JLabel("Date Completed");
		roomLabel = new JLabel("Patient's Room");
		refNumberValue= new JLabel();
		specimenValue= new JTextField(15);
		physicianValue= new JTextField(15);
		pathologistValue= new JTextField(15);
		roomValue = new JTextField(15);
		
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
		receivedDate = new DatePicker(50);
		completedDate = new DatePicker(50);
	
		Calendar c = Calendar.getInstance();
		receivedDate.setDate(c);
		completedDate.setDate(c);

		completedDate.setPickerFont(specimenValue.getFont());
		receivedDate.setPickerFont(specimenValue.getFont());
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
		add(refNumberValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		add(refNumberLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = y++;
		add(patientForm, c);
		c.gridwidth = 1;
		c.gridy = y;
		c.insets = new Insets(0,0,0,10);
		add(roomValue, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		add(specimenValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,10);
		add(roomLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		add(specimenLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,0,10);
		add(pathologistValue, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		add(physicianValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,10);
		add(pathologistLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		add(physicianLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = y;
		c.gridx = 0;
		c.insets = new Insets(0,0,0,10);
		add(receivedDate, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		add(completedDate, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y;
		c.gridx = 0;
		c.insets = new Insets(0,0,10,10);
		add(dateReceivedLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		add(dateCompletedLabel, c);
	}
	
	public void setRecordFields(Object object)
	{	
		Record record = (Record)object;
		String refNumber = (String)record.getAttribute(RecordTable.REF_NUM.toString());
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
		if(!(specimenValue.getText().length() > 0) || 
				specimenValue.getText().length() > RecordConstants.SPECIMEN_LENGTH)
			return false;
		if(!(physicianValue.getText().length() > 0) || 
				physicianValue.getText().length() > RecordConstants.PHYSICIAN_LENGTH)
			return false;
		if(!(pathologistValue.getText().length() > 0) ||
				pathologistValue.getText().length() > RecordConstants.PATHOLOGIST_LENGTH)
			return false;
		if(!patientForm.areFieldsValid())
			return false;
		return true;
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

		record.putAttribute(RecordTable.RECORD_TYPE.toString(), recordType);
		record.putAttribute(RecordTable.REF_NUM.toString(), refNumberValue.getText());
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
