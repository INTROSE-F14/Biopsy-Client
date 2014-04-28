package org.introse.gui.panel;

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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.introse.Constants;
import org.introse.Constants.DictionaryConstants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.StyleConstants;
import org.introse.core.CustomCalendar;
import org.introse.core.CustomDocument;
import org.introse.core.Dictionary;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.gui.combobox.DatePicker;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.PatientForm;
import org.introse.gui.window.LoginWindow;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;

public class RecordOverview extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> specimenValue, physicianValue, pathologistValue;
	private JTextField roomValue;
	private JLabel refNumberLabel,  refNumberValue, specimenLabel, physicianLabel, pathologistLabel, dateReceivedLabel,
	dateCompletedLabel, roomLabel, ageLabel, ageValue, patientLabel, recordLabel;
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
		if(recordType == RecordConstants.GYNECOLOGY_RECORD)
			patientForm.setFemaleOnly(true);
		else patientForm.setFemaleOnly(false);
		recordForm = new JPanel(new GridBagLayout());
		recordForm.setBackground(Color.white);
		refNumberLabel = new JLabel("Internal Reference Number");
		specimenLabel = new JLabel("*Specimen(s)");
		physicianLabel = new JLabel("*Physician");
		pathologistLabel = new JLabel("*Pathologist");
		dateReceivedLabel = new JLabel("Date Received");
		dateCompletedLabel = new JLabel("Date Completed");
		roomLabel = new JLabel("Patient's Room");
		ageLabel = new JLabel("Patient's Age");
		ageValue = new JLabel("N/A");
		patientLabel = new JLabel("Patient Information");
		recordLabel = new JLabel("Basic Record Information");
		refNumberValue= new JLabel();
		physicianValue= new JComboBox<String>();
		pathologistValue= new JComboBox<String>();
		specimenValue = new JComboBox<String>();
		roomValue = new JTextField(10);
		
		refNumberValue.setHorizontalAlignment(JTextField.CENTER);
		refNumberValue.setFont(LoginWindow.PRIMARY_FONT.deriveFont(Font.BOLD).deriveFont(Constants.StyleConstants.SUBHEADER));
		specimenValue.setFont(LoginWindow.PRIMARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		physicianValue.setFont(specimenValue.getFont());
		pathologistValue.setFont(specimenValue.getFont());
		roomValue.setFont(specimenValue.getFont());
		roomValue.setHorizontalAlignment(JTextField.CENTER);
		ageValue.setFont(specimenValue.getFont());
		ageValue.setHorizontalAlignment(JLabel.CENTER);
		patientLabel.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.CATEGORY));
		recordLabel.setFont(patientLabel.getFont());
		patientLabel.setForeground(Color.GRAY);
		recordLabel.setForeground(patientLabel.getForeground());
		receivedDate = new DatePicker(50, false);
		completedDate = new DatePicker(50, false);
	
		Calendar c = Calendar.getInstance();
		receivedDate.setDate(c);
		completedDate.setDate(c);
		completedDate.setPickerFont(specimenValue.getFont());
		receivedDate.setPickerFont(specimenValue.getFont());
		roomValue.setDocument(new CustomDocument(RecordConstants.ROOM_LENGTH));
		specimenValue.setEditable(true);
		pathologistValue.setEditable(true);
		physicianValue.setEditable(true);
		AutoCompleteSupport.install(specimenValue, GlazedLists.eventList(Dictionary.specimens));
		AutoCompleteSupport.install(pathologistValue, GlazedLists.eventList(Dictionary.pathologists));
		AutoCompleteSupport.install(physicianValue, GlazedLists.eventList(Dictionary.physicians));
		System.out.println(specimenValue.getEditor().getEditorComponent().getClass());
		((JTextField)specimenValue.getEditor().getEditorComponent()).setColumns(29);
		((JTextField)pathologistValue.getEditor().getEditorComponent()).setColumns(29);
		((JTextField)physicianValue.getEditor().getEditorComponent()).setColumns(29);
		
	}
	
	private void layoutComponents()
	{
		int y = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,0,35);
		recordForm.add(refNumberValue, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		recordForm.add(ageValue, c);
		c.gridx = 0;
		c.gridy = y;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,0,20,35);
		recordForm.add(refNumberLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,20,0);
		recordForm.add(ageLabel, c);
		c.gridy = y;
		c.gridx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,0,35);
		recordForm.add(specimenValue, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		recordForm.add(roomValue, c);
		c.gridx = 0;
		c.gridy = y;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,0,20,35);
		recordForm.add(specimenLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,20,0);
		recordForm.add(roomLabel, c);
		c.gridx = 0;
		c.gridy = y;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,0,35);
		recordForm.add(pathologistValue, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		recordForm.add(physicianValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,20,35);
		recordForm.add(pathologistLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,20,0);
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
		c.insets = new Insets(0,0,20,35);
		recordForm.add(dateReceivedLabel, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,20,0);
		recordForm.add(dateCompletedLabel, c);
		
		JSeparator divider1 = new JSeparator(SwingConstants.HORIZONTAL);
		JSeparator divider2 = new JSeparator(SwingConstants.HORIZONTAL);
		divider1.setPreferredSize(new Dimension(1,1));
		divider2.setPreferredSize(divider1.getPreferredSize());
		divider1.setBackground(Color.LIGHT_GRAY);
		divider2.setBackground(divider1.getBackground());
		c = new GridBagConstraints();
		y = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = y++;
		add(patientLabel, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,40,0);
		add(divider1, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,50,0);
		add(patientForm, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		add(recordLabel, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,40,0);
		add(divider2, c);
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
			specimenValue.setSelectedItem(specimen);
		if(physician != null)
			physicianValue.setSelectedItem(physician);
		if(pathologist != null)
			pathologistValue.setSelectedItem(pathologist);
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
		
		CustomCalendar dateReceived = new CustomCalendar();
		int dayReceived = receivedDate.getDay();
		int monthReceived = receivedDate.getMonth();
		int yearReceived = receivedDate.getYear();
		dateReceived.set(monthReceived, dayReceived, yearReceived);
		CustomCalendar bDay = (CustomCalendar)patient.getAttribute(PatientTable.BIRTHDAY);
		if(bDay != null)
			ageValue.setText(bDay.getYearDifference(dateReceived)+"");
	}
	
	public void setRecordEditable(boolean isEditable)
	{
		receivedDate.setEnabled(isEditable);
		completedDate.setEnabled(isEditable);
		specimenValue.setEnabled(isEditable);
		physicianValue.setEnabled(isEditable);
		pathologistValue.setEnabled(isEditable);
		roomValue.setEditable(isEditable);
		
		if(isEditable)
			completedDate.setDate(Calendar.getInstance());
	}
	
	public void setPatientEditable(boolean isEditable)
	{
		patientForm.setViewOnly(!isEditable);
	}
	
	public boolean areFieldsValid()
	{
		boolean isValid = true;
		JTextField defaultTextField = new JTextField();
		JTextField specimenField = (JTextField)specimenValue.getEditor().getEditorComponent();
		JTextField pathologistField = (JTextField)pathologistValue.getEditor().getEditorComponent();
		JTextField physicianField =  (JTextField)physicianValue.getEditor().getEditorComponent();
		if(specimenField.getText().length() < 1 || 
				specimenField.getText().length() > DictionaryConstants.WORD_LENGTH)
		{
			System.out.println(specimenValue.getSelectedItem());
			specimenValue.setBorder(BorderFactory.createLineBorder(Color.red));
			isValid = false;
		}
		else specimenValue.setBorder(defaultTextField.getBorder());
		if(physicianField.getText().length() < 1 || 
				physicianField.getText().length() > DictionaryConstants.WORD_LENGTH)
		{
			physicianValue.setBorder(BorderFactory.createLineBorder(Color.red));
			isValid = false;
		}
		else physicianValue.setBorder(defaultTextField.getBorder());
		if(pathologistField.getText().length() < 1 ||
				pathologistField.getText().length() > DictionaryConstants.WORD_LENGTH)
		{
			pathologistValue.setBorder(BorderFactory.createLineBorder(Color.red));
			isValid = false;
		}
		else pathologistValue.setBorder(defaultTextField.getBorder());
		
		int rYear = receivedDate.getYear();
		int rMonth = receivedDate.getMonth();
		int rDay = receivedDate.getDay();
		Calendar rDate = Calendar.getInstance();
		rDate.clear();
		rDate.set(rYear, rMonth, rDay);
		
		int cYear = completedDate.getYear();
		int cMonth = completedDate.getMonth();
		int cDay = completedDate.getDay();
		Calendar cDate = Calendar.getInstance();
		cDate.clear();
		cDate.set(cYear, cMonth, cDay);
		
		int pYear, pMonth, pDay;
		pYear = patientForm.returnYear();
        pMonth = patientForm.returnMonth();
        pDay = patientForm.returnDay();
		Calendar pDate = Calendar.getInstance();
		pDate.clear();
		pDate.set(pYear, pMonth, pDay);
		
		if(cDate.before(rDate) || cDate.before(pDate))
		{
			isValid = false;
			completedDate.setBorder(BorderFactory.createLineBorder(Color.red));
		}
		else completedDate.setBorder(null);
		if(rDate.before(pDate))
		{
			isValid = false;
			receivedDate.setBorder(BorderFactory.createLineBorder(Color.red));
		}
		else receivedDate.setBorder(null);
		
		if(!patientForm.areFieldsValid())
			isValid = false;
		return isValid;
	}
	
	public Record getRecord()
	{
		Record record = new Record();
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
		JTextField specimenField = (JTextField)specimenValue.getEditor().getEditorComponent();
		JTextField pathologistField = (JTextField)pathologistValue.getEditor().getEditorComponent();
		JTextField physicianField =  (JTextField)physicianValue.getEditor().getEditorComponent();
		record.putAttribute(RecordTable.SPECIMEN.toString(), specimenField.getText());
		record.putAttribute(RecordTable.PATHOLOGIST.toString(), pathologistField.getText());
		record.putAttribute(RecordTable.PHYSICIAN.toString(), physicianField.getText());
		
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
