package org.introse.gui.form;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.introse.core.Patient;
import org.introse.core.PatientTable;
import org.introse.gui.MainMenu;
import org.introse.gui.event.CustomListener;

public class PatientForm extends JPanel implements Form
{
	private JTextField lastNameValue;
	private JTextField firstNameValue;
	private JTextField middleNameValue;
	private JComboBox<String> genderValue;
	private JTextField roomValue;
	private JButton loadExisting;
	
	private JLabel lastNameLabel;
	private JLabel firstNameLabel;
	private JLabel middleNameLabel;
	private JLabel genderLabel;
	private JLabel roomLabel;
	private JLabel birthdayLabel;
	
	private JComboBox<String> birthday;
	private JComboBox<String> birthmonth;
	private JComboBox<String> birthyear;
	private JPanel birthdate;
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
	}
	
	private void initializeComponents()
	{
		insidePanel = new JPanel(new GridBagLayout());
		insidePanel.setBackground(Color.white);
		lastNameLabel = new JLabel("Last name");
		firstNameLabel = new JLabel("First name");
		middleNameLabel = new JLabel("Middle name");
		genderLabel = new JLabel("Gender");
		roomLabel = new JLabel("Room");
		birthdayLabel = new JLabel("Birthday");
		
		lastNameValue = new JTextField(10);
		firstNameValue = new JTextField(10);
		middleNameValue= new JTextField(10);
		String[] gender = {"M","F"};
		genderValue= new JComboBox<String>(gender);
		genderValue.setBorder(null);
		roomValue= new JTextField(10);
		
		lastNameValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(MainMenu.SUBHEADER));
		lastNameValue.setHorizontalAlignment(JTextField.CENTER);
		firstNameValue.setFont(lastNameValue.getFont());
		firstNameValue.setHorizontalAlignment(JTextField.CENTER);
		middleNameValue.setFont(lastNameValue.getFont());
		middleNameValue.setHorizontalAlignment(JTextField.CENTER);
		genderValue.setFont(lastNameValue.getFont());
		roomValue.setHorizontalAlignment(JTextField.CENTER);
		roomValue.setFont(lastNameValue.getFont());	
		
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
		birthday= new JComboBox<String>(monthDays);
		birthmonth= new JComboBox<String>(monthNames);
		birthyear= new JComboBox<String>(years);
		birthday.setBorder(null);
		birthmonth.setBorder(null);
		birthyear.setBorder(null);
		
		birthdate = new JPanel(new GridBagLayout());
		birthdate.setBackground(Color.white);
	
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		int year = c.get(Calendar.YEAR);
		birthday.setSelectedItem(""+day);
		birthmonth.setSelectedIndex(month);
		birthyear.setSelectedItem(""+year);
		birthday.setFont(lastNameValue.getFont());
		birthmonth.setFont(lastNameValue.getFont());
		birthyear.setFont(lastNameValue.getFont());
		loadExisting = new JButton("Load Existing");
		loadExisting.setVisible(false);
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,5,5);
		birthdate.add(birthday, c);
		c.gridx = 1;
		birthdate.add(birthmonth, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,5,0);
		birthdate.add(birthyear, c);
		
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
		insidePanel.add(birthdate, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,1,10);
		insidePanel.add(roomValue, c);
		c.gridx = 0;
		c.gridy = 3;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,10,5,10);
		insidePanel.add(genderLabel, c);
		c.gridx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,5,10);
		insidePanel.add(birthdayLabel, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,5,10);
		insidePanel.add(roomLabel, c);
		c.gridy = 4;
		insidePanel.add(loadExisting, c);
		
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
		String room = (String)patient.getAttribute(PatientTable.ROOM.toString());
		if(room != null)
		roomValue.setText(room);
		else roomValue .setText("N/A");
		Calendar birthday = (Calendar)patient.getAttribute(PatientTable.BIRTHDAY.toString());
		if(birthday!= null)
		{
			int month = birthday.get(Calendar.MONTH);
			int day = birthday.get(Calendar.DATE);
			int year = birthday.get(Calendar.YEAR);
			this.birthday.setSelectedItem(""+day);
			birthmonth.setSelectedIndex(month);
			birthyear.setSelectedItem(""+year);
		}
	}
	
	public void setEditable(boolean isEditable)
	{
		lastNameValue.setEditable(isEditable);
		firstNameValue.setEditable(isEditable);
		middleNameValue.setEditable(isEditable);
		genderValue.setEnabled(isEditable);
		roomValue.setEditable(isEditable);
		birthday.setEnabled(isEditable);
		birthmonth.setEnabled(isEditable);
		birthyear.setEnabled(isEditable);
	}

	public Object getObject() 
	{
		Patient patient = new Patient();
		patient.putAttribute(PatientTable.PATIENT_ID.toString(), patientID);
		patient.putAttribute(PatientTable.LAST_NAME.toString(), lastNameValue.getText());
		patient.putAttribute(PatientTable.FIRST_NAME.toString(), firstNameValue.getText());
		patient.putAttribute(PatientTable.MIDDLE_NAME.toString(), middleNameValue.getText());
		patient.putAttribute(PatientTable.GENDER.toString(), (String)genderValue.getSelectedItem());
		patient.putAttribute(PatientTable.ROOM.toString(), roomValue.getText());
		
		Calendar birthDate = Calendar.getInstance();
		int day= Integer.parseInt((String)this.birthday.getSelectedItem());
		int month= this.birthmonth.getSelectedIndex();
		int year= Integer.parseInt((String)this.birthyear.getSelectedItem());
		birthDate.clear();
		birthDate.set(year, month, day);
		patient.putAttribute(PatientTable.BIRTHDAY.toString(), birthDate);
		return patient;
	}

	public boolean areFieldsValid() 
	{
		int day= this.birthday.getSelectedIndex();
		int month= this.birthmonth.getSelectedIndex();
		int year= Integer.parseInt((String)this.birthyear.getSelectedItem());
		if(!(lastNameValue.getText().length() > 0))
			return false;
		if(!(firstNameValue.getText().length() > 0))
			return false;
		if(!(middleNameValue.getText().length() > 0))
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
		loadExisting.setActionCommand("LOAD_EXISTING_PATIENT");
	}
}