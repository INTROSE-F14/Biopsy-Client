package org.introse.gui.form;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.introse.Constants;
import org.introse.Constants.PatientConstants;
import org.introse.Constants.PatientTable;
import org.introse.core.CustomCalendar;
import org.introse.core.Patient;
import org.introse.gui.combobox.DatePicker;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.MainMenu;

public class PatientForm extends JPanel implements Form
{
	private JTextField lastNameValue;
	private JTextField firstNameValue;
	private JTextField middleNameValue;
	private JComboBox<String> genderValue;
	private JButton loadExisting;
	
	private JLabel lastNameLabel;
	private JLabel firstNameLabel;
	private JLabel middleNameLabel;
	private JLabel genderLabel;
	private JLabel birthdayLabel;
	private DatePicker birthday;
	private JPanel insidePanel;
	private int patientID;
	
	public PatientForm()
	{
		super(new GridBagLayout());
		setBackground(Color.white);
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
				"Patient Information"));
		initializeComponents();
		layoutComponents();
	}
	
	public void setLoadExisting(boolean isLoadable)
	{
		loadExisting.setVisible(isLoadable);
		loadExisting.setBorderPainted(false);
		loadExisting.setContentAreaFilled(false);
		loadExisting.setOpaque(true);
		loadExisting.setBackground(Color.decode(Constants.StyleConstants.NORMAL));
		loadExisting.setIcon(new ImageIcon(getClass().getResource("/res/icons/load.png")));
		loadExisting.setIconTextGap(7);
	}
	
	private void initializeComponents()
	{
		insidePanel = new JPanel(new GridBagLayout());
		insidePanel.setBackground(Color.white);
		lastNameLabel = new JLabel("Last name");
		firstNameLabel = new JLabel("First name");
		middleNameLabel = new JLabel("Middle name");
		genderLabel = new JLabel("Gender");
		birthdayLabel = new JLabel("Birthday");
		
		lastNameValue = new JTextField(10);
		firstNameValue = new JTextField(10);
		middleNameValue= new JTextField(10);
		String[] gender = {"M","F"};
		genderValue= new JComboBox<String>(gender);
		genderValue.setBorder(null);
		
		lastNameValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		lastNameValue.setHorizontalAlignment(JTextField.CENTER);
		firstNameValue.setFont(lastNameValue.getFont());
		firstNameValue.setHorizontalAlignment(JTextField.CENTER);
		middleNameValue.setFont(lastNameValue.getFont());
		middleNameValue.setHorizontalAlignment(JTextField.CENTER);
		genderValue.setFont(lastNameValue.getFont());
		birthday = new DatePicker(100);
		Calendar c = Calendar.getInstance();
		birthday.setDate(c);
		birthday.setPickerFont(lastNameValue.getFont());
		loadExisting = new JButton("Load Existing");
		loadExisting.setVisible(false);
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 10, 1, 10);
		insidePanel.add(lastNameValue, c);
		c.gridx = 1;
		c.insets = new Insets(5, 0, 1, 10);
		insidePanel.add(firstNameValue, c);
		c.gridx = 2;
		c.insets = new Insets(5, 0, 1, 10);
		insidePanel.add(middleNameValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 10);
		insidePanel.add(lastNameLabel, c);
		c.gridx = 1;
		c.insets = new Insets(0, 0, 10,10);
		insidePanel.add(firstNameLabel, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,10,10);
		insidePanel.add(middleNameLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0,10,1,10);
		insidePanel.add(genderValue, c);
		c.gridx = 1;
		c.insets = new Insets(0,0,1,10);
		insidePanel.add(birthday, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,1,10);
		insidePanel.add(loadExisting, c);
		c.gridx = 0;
		c.gridy = 3;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,10,5,10);
		insidePanel.add(genderLabel, c);
		c.gridx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,5,10);
		insidePanel.add(birthdayLabel, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(20,20,20,20);
		add(insidePanel, c);
	}
	
	public void setFields(Object object)
	{
		Patient patient = (Patient)object;
		patientID = (int)patient.getAttribute(PatientTable.PATIENT_ID.toString());
		
		String lastName = (String)patient.getAttribute(PatientTable.LAST_NAME.toString());
		String firstName = (String)patient.getAttribute((PatientTable.FIRST_NAME.toString()));
		String middleName = (String)patient.getAttribute(PatientTable.MIDDLE_NAME.toString());
		String gender = (String)patient.getAttribute(PatientTable.GENDER.toString());
		
		if(lastName != null)
		lastNameValue.setText(lastName);
		if(firstName != null)
		firstNameValue.setText(firstName);
		if(middleName != null)
		middleNameValue.setText(middleName);
		if(gender != null)
		genderValue.setSelectedItem(gender);
		CustomCalendar birthday = (CustomCalendar)patient.getAttribute(PatientTable.BIRTHDAY.toString());
		if(birthday!= null)
			this.birthday.setDate(birthday);
	}
	
	public void setEditable(boolean isEditable)
	{
		lastNameValue.setEditable(isEditable);
		firstNameValue.setEditable(isEditable);
		middleNameValue.setEditable(isEditable);
		genderValue.setEnabled(isEditable);
		birthday.setEnabled(isEditable);
	}

	public Object getObject() 
	{
		Patient patient = new Patient();
		patient.putAttribute(PatientTable.PATIENT_ID.toString(), patientID);
		patient.putAttribute(PatientTable.LAST_NAME.toString(), lastNameValue.getText());
		patient.putAttribute(PatientTable.FIRST_NAME.toString(), firstNameValue.getText());
		patient.putAttribute(PatientTable.MIDDLE_NAME.toString(), middleNameValue.getText());
		patient.putAttribute(PatientTable.GENDER.toString(), (String)genderValue.getSelectedItem());
		
		CustomCalendar birthDate = new CustomCalendar();
		int day= birthday.getDay();
		int month= birthday.getMonth();
		int year= birthday.getYear();
		birthDate.set(month, day, year);
		patient.putAttribute(PatientTable.BIRTHDAY.toString(), birthDate);
		return patient;
	}

	public boolean areFieldsValid() 
	{
		int day= birthday.getDay();
		int month= birthday.getMonth();
		int year= birthday.getYear();		
		if(!(lastNameValue.getText().replaceAll("\\s", "").length() > 0) || 
				lastNameValue.getText().length() > PatientConstants.LAST_NAME_LENGTH)
			return false;
		if(!(firstNameValue.getText().replaceAll("\\s", "").length() > 0) ||
				firstNameValue.getText().length() > PatientConstants.FIRST_NAME_LENGTH)
			return false;
		if(!(middleNameValue.getText().replaceAll("\\s", "").length() > 0) ||
				middleNameValue.getText().length() > PatientConstants.MIDDLE_NAME_LENGTH)
			return false;
		if((month== 3 || month== 5 || 
				month== 8 || month== 10) && day> 29)
			return false;
		if(month == 1 && !(year% 4 == 0 && year% 100 != 0 || year% 400 == 0) 
				&& day> 27)
			return false;
		if(month== 1 && day> 28)
			return false;
		return true;
	}
	
	public void addListener(CustomListener listener)
	{
		loadExisting.addActionListener(listener);
		loadExisting.setActionCommand(Constants.ActionConstants.LOAD_PATIENT);
		loadExisting.addMouseListener(listener);
	}
}