package org.introse.gui.form;

import java.awt.CardLayout;
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
import org.introse.core.CustomDocument;
import org.introse.core.Patient;
import org.introse.gui.combobox.DatePicker;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.LoginWindow;

public class GynePatientForm extends JPanel implements Form
{
	private JTextField lastNameValue, firstNameValue, middleNameValue;
	private JComboBox<String> genderValue;
	private JLabel _lastNameValue, _firstNameValue,_middleNameValue, _genderValue, _birthdayValue;
	private JButton loadExisting;
	
	private JLabel lastNameLabel, firstNameLabel, middleNameLabel, genderLabel, birthdayLabel;
	private JLabel _lastNameLabel, _firstNameLabel, _middleNameLabel, _genderLabel, _birthdayLabel;
	
	private DatePicker birthday;
	private JPanel viewOnlyPanel, editablePanel;

	private int patientID;
	
	public GynePatientForm()
	{
		super(new CardLayout());
		setBackground(Color.white);
		initializeComponents();
		layoutComponents();
		setBorder(null);
		add("VIEW", viewOnlyPanel);
		add("EDIT", editablePanel);
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
		viewOnlyPanel = new JPanel(new GridBagLayout());
		viewOnlyPanel.setBackground(Color.white);
		_lastNameValue = new JLabel();
		_firstNameValue = new JLabel();
		_middleNameValue = new JLabel();
		_genderValue = new JLabel();
		_birthdayValue = new JLabel();
		
		editablePanel = new JPanel(new GridBagLayout());
		editablePanel.setBackground(Color.white);
		lastNameLabel = new JLabel("*Last name");
		firstNameLabel = new JLabel("*First name");
		middleNameLabel = new JLabel("*Middle name");
		genderLabel = new JLabel("*Gender");
		birthdayLabel = new JLabel("*Birthday");
		
		_lastNameLabel = new JLabel("Last name");
		_firstNameLabel = new JLabel("First name");
		_middleNameLabel = new JLabel("Middle name");
		_genderLabel = new JLabel("Gender");
		_birthdayLabel = new JLabel("Birthday");
		
		lastNameValue = new JTextField(20);
		firstNameValue = new JTextField(20);
		middleNameValue= new JTextField(20);
		String[] gender = {"F"};
		genderValue= new JComboBox<String>(gender);
		genderValue.setBorder(null);
		
		lastNameValue.setFont(LoginWindow.PRIMARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		lastNameValue.setHorizontalAlignment(JTextField.CENTER);
		firstNameValue.setFont(lastNameValue.getFont());
		firstNameValue.setHorizontalAlignment(JTextField.CENTER);
		middleNameValue.setFont(lastNameValue.getFont());
		middleNameValue.setHorizontalAlignment(JTextField.CENTER);
		genderValue.setFont(lastNameValue.getFont());
		birthday = new DatePicker(100, false);
		Calendar c = Calendar.getInstance();
		birthday.setDate(c);
		birthday.setPickerFont(lastNameValue.getFont());
		loadExisting = new JButton("Load Existing Patient");
		loadExisting.setVisible(false);
		
		_lastNameValue.setFont(LoginWindow.PRIMARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		_lastNameValue.setHorizontalAlignment(JLabel.CENTER);
		_firstNameValue.setFont(_lastNameValue.getFont());
		_firstNameValue.setHorizontalAlignment(JLabel.CENTER);
		_middleNameValue.setFont(_lastNameValue.getFont());
		_middleNameValue.setHorizontalAlignment(JLabel.CENTER);
		_genderValue.setFont(_lastNameValue.getFont());
		_genderValue.setHorizontalAlignment(JLabel.CENTER);
		_birthdayValue.setFont(_lastNameValue.getFont());
		_birthdayValue.setHorizontalAlignment(JLabel.CENTER);
		
		firstNameValue.setDocument(new CustomDocument(PatientConstants.FIRST_NAME_LENGTH));
		lastNameValue.setDocument(new CustomDocument(PatientConstants.LAST_NAME_LENGTH));
		middleNameValue.setDocument(new CustomDocument(PatientConstants.MIDDLE_NAME_LENGTH));
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 1, 20);
		editablePanel.add(lastNameValue, c);
		c.gridx = 1;
		c.insets = new Insets(0, 0, 1, 20);
		editablePanel.add(firstNameValue, c);
		c.gridx = 2;
		c.insets = new Insets(0, 0, 1, 0);
		editablePanel.add(middleNameValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 10, 20);
		editablePanel.add(lastNameLabel, c);
		c.gridx = 1;
		c.insets = new Insets(0, 0, 10,20);
		editablePanel.add(firstNameLabel, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,10,0);
		editablePanel.add(middleNameLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0,0,1,20);
		editablePanel.add(genderValue, c);
		c.gridx = 1;
		c.insets = new Insets(0,0,1,20);
		editablePanel.add(birthday, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,1,0);
		editablePanel.add(loadExisting, c);
		c.gridx = 0;
		c.gridy = 3;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,0,5,20);
		editablePanel.add(genderLabel, c);
		c.gridx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,5,20);
		editablePanel.add(birthdayLabel, c);
		
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 0, 1, 20);
		viewOnlyPanel.add(_lastNameValue, c);
		c.gridx = 1;
		c.insets = new Insets(5, 0, 1, 20);
		viewOnlyPanel.add(_firstNameValue, c);
		c.gridx = 2;
		c.insets = new Insets(5, 0, 1, 0);
		viewOnlyPanel.add(_middleNameValue, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 10, 20);
		viewOnlyPanel.add(_lastNameLabel, c);
		c.gridx = 1;
		c.insets = new Insets(0, 0, 10,20);
		viewOnlyPanel.add(_firstNameLabel, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,10,0);
		viewOnlyPanel.add(_middleNameLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0,0,1,20);
		viewOnlyPanel.add(_genderValue, c);
		c.gridx = 1;
		c.insets = new Insets(0,0,1,20);
		viewOnlyPanel.add(_birthdayValue, c);
		c.gridx = 0;
		c.gridy = 3;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,0,5,20);
		viewOnlyPanel.add(_genderLabel, c);
		c.gridx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,5,20);
		viewOnlyPanel.add(_birthdayLabel, c);
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
		{
			lastNameValue.setText(lastName);
			_lastNameValue.setText(lastName);
		}
		if(firstName != null)
		{
			firstNameValue.setText(firstName);
			_firstNameValue.setText(firstName);
		}
		if(middleName != null)
		{
			middleNameValue.setText(middleName);
			_middleNameValue.setText(middleName);
		}
		if(gender != null)
		{
			genderValue.setSelectedItem(gender);
			_genderValue.setText(gender);
		}
		CustomCalendar birthday = (CustomCalendar)patient.getAttribute(PatientTable.BIRTHDAY.toString());
		if(birthday!= null)
		{
			this.birthday.setDate(birthday);
			_birthdayValue.setText(birthday.toString());
		}
		
		JTextField defaultTextField = new JTextField();
		lastNameValue.setBorder(defaultTextField.getBorder());
		firstNameValue.setBorder(defaultTextField.getBorder());
		middleNameValue.setBorder(defaultTextField.getBorder());
	}
	
	public void setViewOnly(boolean isViewOnly)
	{
		CardLayout layout = (CardLayout)getLayout();
		if(isViewOnly)
			layout.show(this, "VIEW");
		else layout.show(this, "EDIT");
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
		boolean isValid = true;
		JTextField defaultTextField = new JTextField();
		if(!(lastNameValue.getText().length() > 0) || 
				lastNameValue.getText().length() > PatientConstants.LAST_NAME_LENGTH)
		{
			lastNameValue.setBorder(BorderFactory.createLineBorder(Color.red));
			isValid = false;
		}
		else lastNameValue.setBorder(defaultTextField.getBorder());
		if(!(firstNameValue.getText().length() > 0) ||
				firstNameValue.getText().length() > PatientConstants.FIRST_NAME_LENGTH)
		{
			firstNameValue.setBorder(BorderFactory.createLineBorder(Color.red));
			isValid = false;
		}
		else firstNameValue.setBorder(defaultTextField.getBorder());
		if(!(middleNameValue.getText().length() > 0) ||
				middleNameValue.getText().length() > PatientConstants.MIDDLE_NAME_LENGTH)
		{
			middleNameValue.setBorder(BorderFactory.createLineBorder(Color.red));
			isValid = false;
		}
		else middleNameValue.setBorder(defaultTextField.getBorder());
		return isValid;
	}
	
    public int returnYear()
    {
        int val = 0;
        val = birthday.getYear();
        return val;
    }        
    
    public int returnMonth()
    {
        int val = 0;
        val = birthday.getMonth();
        return val;
    }
    
    public int returnDay()
    {
        int val = 0;
        val = birthday.getDay();
        return val;
    }

	public void addListener(CustomListener listener)
	{
		loadExisting.addActionListener(listener);
		loadExisting.setActionCommand(Constants.ActionConstants.LOAD_PATIENT);
		loadExisting.addMouseListener(listener);
	}
}