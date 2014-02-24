package org.introse.gui.panel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.introse.core.CytologyRecord;
import org.introse.core.GynecologyRecord;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Patient;
import org.introse.core.PatientTable;
import org.introse.core.Record;
import org.introse.core.RecordTable;
import org.introse.gui.MainMenu;
import org.introse.gui.customcomponent.ListItem;
import org.introse.gui.customcomponent.ListProvider;
import org.introse.gui.event.CustomListener;


public class ContentPanel extends JPanel {

	public static final String HISTO_PANEL = "Histopathology";
	public static final String GYNE_PANEL = "Gynecology";
	public static final String CYTO_PANEL = "Cytology";
	public static final String PATIENT_PANEL = "Patients";
	public static final String PATH_PANEL = "Pathologists";
	public static final String PHYS_PANEL = "Physicians";
	public static final String SPEC_PANEL = "Specimens";
	public static final String PREFERENCES_VIEW = "Preferences";
	public static final String RECORDS_VIEW = "Records";
	public static final String NEW_HISTO = "New Histopathology";
	public static final String NEW_GYNE = "New Gynecology";
	public static final String NEW_CYTO = "New Cytology";
	public static final String NEW_PATIENT = "New Patient";
	public static final String NEW_SPECIMEN = "New Specimen";
	public static final String NEW_PHYSICIAN = "New Physician";
	public static final String NEW_PATHOLOGIST = "New Pathologist";
	
	private CustomListener listener;
	private String currentView;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel panel1;
	private JPanel settingsPanel;
	private JPanel detailsPanel;
	private JButton newButton;
	private JButton searchButton;
	private JButton refreshButton;
	private JTextField filterField;
	private JLabel headerLabel;
	
	private ListProvider histopathologyList;
	private ListProvider gynecologyList;
	private ListProvider cytologyList;
	private MainMenu menu;
	
	public ContentPanel(MainMenu menu)
	{
		super(new CardLayout());
		this.menu = menu;
		createPanel1();
		createSettingsPanel();
		add(RECORDS_VIEW, panel1);
		add(PREFERENCES_VIEW, settingsPanel);
		currentView = RECORDS_VIEW;
	}
	
	public void createPanel1()
	{
		panel1 = new JPanel(new GridBagLayout());
		panel1.setBackground(Color.white);
		
		topPanel = new JPanel(new GridBagLayout());
		topPanel.setBackground(Color.white);
		newButton = new JButton("create");
		refreshButton = new JButton("refresh");
		searchButton = new JButton("search");
		
		newButton.setFont(MainMenu.SECONDARY_FONT.deriveFont(MainMenu.MENU));
		refreshButton.setFont(newButton.getFont());
		searchButton.setFont(newButton.getFont());
		newButton.setContentAreaFilled(false);
		refreshButton.setContentAreaFilled(false);
		searchButton.setContentAreaFilled(false);
		newButton.setBorderPainted(false);
		refreshButton.setBorderPainted(false);
		searchButton.setBorderPainted(false);
		newButton.setFocusPainted(false);
		refreshButton.setFocusPainted(false);
		searchButton.setFocusPainted(false);
		filterField = new JTextField(25);
		headerLabel = new JLabel("Header");
		headerLabel.setFont(MainMenu.SECONDARY_FONT.deriveFont(MainMenu.SUBHEADER));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 1.0;
		topPanel.add(newButton, c);
		c.gridx = 1;
		topPanel.add(searchButton, c);
		c.gridx = 2;
		topPanel.add(refreshButton, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		topPanel.add(filterField, c);
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(10,0,0,0);
		topPanel.add(headerLabel, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.insets = new Insets(0,0,10,0);
		c.fill = GridBagConstraints.BOTH;
		panel1.add(topPanel, c);
		
		bottomPanel = new JPanel(new CardLayout());
		bottomPanel.setBackground(Color.white);
		//bottomPanel will contain the general
		//list of histopathology records/gyne/cyto/patients
		histopathologyList = new ListProvider();
		bottomPanel.add(HISTO_PANEL, histopathologyList.getPanel());
		gynecologyList = new ListProvider();
		bottomPanel.add(GYNE_PANEL, gynecologyList.getPanel());
		cytologyList = new ListProvider();
		bottomPanel.add(CYTO_PANEL, cytologyList.getPanel());
		JPanel patientPanel = new JPanel(new GridLayout(0, 1));
		JPanel pathPanel = new JPanel(new GridLayout(0, 1)); //list for pathologists
		JPanel physPanel = new JPanel(new GridLayout(0, 1)); //list for physicians
		JPanel specPanel = new JPanel(new GridLayout(0, 1)); //list for specimens
		bottomPanel.add(PATIENT_PANEL,patientPanel);
		bottomPanel.add(PATH_PANEL, pathPanel);
		bottomPanel.add(PHYS_PANEL, physPanel);
		bottomPanel.add(SPEC_PANEL, specPanel);
		changeSubView(HISTO_PANEL);
		
		c.gridy = 1;
		c.gridheight = 2;
		panel1.add(bottomPanel, c);
		
		detailsPanel = new JPanel(new CardLayout());
		JPanel emptyPanel = new JPanel();
		emptyPanel.setBackground(Color.white);
		detailsPanel.setBackground(Color.white);
		detailsPanel.setPreferredSize(new Dimension(500,500));
		detailsPanel.add("First", emptyPanel);
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		panel1.add(detailsPanel, c);
	}
	
	public void createSettingsPanel()
	{
		settingsPanel = new JPanel(new GridBagLayout());
		settingsPanel.setBackground(Color.white);
		JLabel header = new JLabel("Preferences");
		header.setFont(MainMenu.PRIMARY_FONT.deriveFont(MainMenu.HEADER));
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		settingsPanel.add(header, c);
	}
	
	public void addListener(CustomListener listener)
	{
		this.listener = listener;
		histopathologyList.addListener(listener);
		cytologyList.addListener(listener);
		gynecologyList.addListener(listener);
		newButton.addActionListener(listener);
		searchButton.addActionListener(listener);
		refreshButton.addActionListener(listener);
		newButton.setActionCommand("NEW");
		searchButton.setActionCommand("SEARCH");
		refreshButton.setActionCommand("REFRESH");
	}
	
	public void changeSubView(String view)
	{
		CardLayout cl = (CardLayout)bottomPanel.getLayout();
		cl.show(bottomPanel, view);
		headerLabel.setText(view);
		if(detailsPanel != null)
		{
			CardLayout clDetails = (CardLayout)detailsPanel.getLayout();
			clDetails.first(detailsPanel);
		}
	}
	
	public void changeMainView(String view)
	{
		currentView = view;
		CardLayout cl = (CardLayout)getLayout();
		cl.show(this, view);
	}

	public String getCurrentView()
	{
		return currentView;
	}
	
	public void updateCytologyList()
	{
		List<ListItem> list = new Vector<ListItem>();
		CytologyRecord record = new CytologyRecord();
		record.putAttribute(RecordTable.RECORD_TYPE.toString(), 2);
		List<Object> records = menu.getRecordDao().search(record);
		
		Iterator<Object> i = records.iterator();
		while(i.hasNext())
		{
			Record curRecord = (CytologyRecord)i.next();
			String patientID = (String)curRecord.getAttribute(RecordTable.PATIENT_ID.toString());
			Patient patient = new Patient(patientID);
			Patient p = (Patient)menu.getPatientDao().get(patient);
			String patientName = p.getAttribute(PatientTable.LAST_NAME.toString()) + 
					", " + p.getAttribute(PatientTable.FIRST_NAME.toString()) + " " + 
					p.getAttribute(PatientTable.LAST_NAME.toString());
			ListItem item = new ListItem((String)curRecord.getAttribute(RecordTable.REF_NUM.toString()),
					(String)curRecord.getAttribute(RecordTable.REF_NUM.toString()), 
					(String)curRecord.getAttribute(RecordTable.SPECIMEN.toString()), patientName, 
					(String)curRecord.getAttribute(RecordTable.PATHOLOGIST.toString()),
					ListProvider.CYTOLOGY_RECORD);
			list.add(item);
		}
		cytologyList.updateList(list);
	}
	
	public void updateGynecologyList()
	{
		List<ListItem> list = new Vector<ListItem>();
		GynecologyRecord record = new GynecologyRecord();
		record.putAttribute(RecordTable.RECORD_TYPE.toString(), 1);
		List<Object> records = menu.getRecordDao().search(record);
		
		Iterator<Object> i = records.iterator();
		while(i.hasNext())
		{
			Record curRecord = (GynecologyRecord)i.next();
			String patientID = (String)curRecord.getAttribute(RecordTable.PATIENT_ID.toString());
			Patient patient = new Patient(patientID);
			Patient p = (Patient)menu.getPatientDao().get(patient);
			String patientName = p.getAttribute(PatientTable.LAST_NAME.toString()) + 
					", " + p.getAttribute(PatientTable.FIRST_NAME.toString()) + " " + 
					p.getAttribute(PatientTable.LAST_NAME.toString());
			ListItem item = new ListItem((String)curRecord.getAttribute(RecordTable.REF_NUM.toString()),
					(String)curRecord.getAttribute(RecordTable.REF_NUM.toString()), 
					(String)curRecord.getAttribute(RecordTable.SPECIMEN.toString()), patientName, 
					(String)curRecord.getAttribute(RecordTable.PATHOLOGIST.toString()),
					ListProvider.GYNECOLOGY_RECORD);
			list.add(item);
		}
		gynecologyList.updateList(list);
	}
	
	public void updateHistopathologyList()
	{
		List<ListItem> list = new Vector<ListItem>();
		HistopathologyRecord record = new HistopathologyRecord();
		record.putAttribute(RecordTable.RECORD_TYPE.toString(), 0);
		List<Object> records = menu.getRecordDao().search(record);
		Iterator<Object> i = records.iterator();
		while(i.hasNext())
		{
			Record curRecord = (HistopathologyRecord)i.next();
			String patientID = (String)curRecord.getAttribute(RecordTable.PATIENT_ID.toString());
			Patient patient = new Patient(patientID);
			Patient p = (Patient)menu.getPatientDao().get(patient);
			String patientName = p.getAttribute(PatientTable.LAST_NAME.toString()) + 
					", " + p.getAttribute(PatientTable.FIRST_NAME.toString()) + " " + 
					p.getAttribute(PatientTable.LAST_NAME.toString());
			ListItem item = new ListItem((String)curRecord.getAttribute(RecordTable.REF_NUM.toString()),
					(String)curRecord.getAttribute(RecordTable.REF_NUM.toString()), 
					(String)curRecord.getAttribute(RecordTable.SPECIMEN.toString()), patientName, 
					(String)curRecord.getAttribute(RecordTable.PATHOLOGIST.toString()),
					ListProvider.HISTOPATHOLOGY_RECORD);
			list.add(item);
		}
		histopathologyList.updateList(list);
	}
	
	public void viewItem(ListItem item)
	{
		Object object = null;
		String id = item.getID();
		switch(item.getType())
		{
			case ListProvider.HISTOPATHOLOGY_RECORD:
				object = new HistopathologyRecord();
				((HistopathologyRecord)object).putAttribute(RecordTable.REF_NUM.toString(), id);
				object = (HistopathologyRecord)menu.getRecordDao().get(object);
				break;
				
			case ListProvider.CYTOLOGY_RECORD:
				object = new CytologyRecord();
				((CytologyRecord)object).putAttribute(RecordTable.REF_NUM.toString(), id);
				object = (HistopathologyRecord)menu.getRecordDao().get(object);
				break;
			case ListProvider.GYNECOLOGY_RECORD:
				object = new HistopathologyRecord();
				((HistopathologyRecord)object).putAttribute(RecordTable.REF_NUM.toString(), id);
				object = (HistopathologyRecord)menu.getRecordDao().get(object);
				break;
		}
		if(object != null)
			showDetails(object);
	}
	
	public void showDetails(Object object)
	{
		JPanel objectDetails = new JPanel(new GridBagLayout());
		objectDetails.setBackground(Color.white);
		GridBagConstraints c = new GridBagConstraints();
		
		if(object instanceof Record)
		{
			Record record = (Record)object;
			Patient patient = new Patient();
			patient.putAttribute(PatientTable.PATIENT_ID.toString(), 
					record.getAttribute(RecordTable.PATIENT_ID.toString()));
			patient = (Patient)menu.getPatientDao().get(patient);
			
			JPanel patientPanel = new JPanel(new GridBagLayout());
			patientPanel.setPreferredSize(new Dimension(450, 200));
			patientPanel.setBackground(Color.white);
			patientPanel.setBorder(BorderFactory.createTitledBorder("Patient Information"));
			
			JLabel lastNameLabel = new JLabel("Last name");
			JLabel firstNameLabel = new JLabel("First name");
			JLabel middleNameLabel = new JLabel("Middle name");
			JLabel genderLabel = new JLabel("Gender");
			JLabel roomLabel = new JLabel("Room");
			JLabel ageLabel = new JLabel("Age");
			
			JLabel lastNameValue = new JLabel((String)patient.getAttribute(PatientTable.LAST_NAME.toString()));
			JLabel firstNameValue = new JLabel((String)patient.getAttribute((PatientTable.FIRST_NAME.toString())));
			JLabel middleNameValue= new JLabel((String)patient.getAttribute(PatientTable.MIDDLE_NAME.toString()));
			JLabel genderValue= new JLabel((String)patient.getAttribute(PatientTable.GENDER.toString()));
			String room = (String)patient.getAttribute(PatientTable.ROOM.toString());
			JLabel roomValue;
			if(room != null)
			roomValue= new JLabel(room);
			else roomValue = new JLabel("N/A");
			Calendar birthday = (Calendar)patient.getAttribute(PatientTable.BIRTHDAY.toString());
			int age =  Calendar.getInstance().get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
			JLabel ageValue= new JLabel(""+age);
			
			lastNameValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(MainMenu.SUBHEADER));
			firstNameValue.setFont(lastNameValue.getFont());
			middleNameValue.setFont(lastNameValue.getFont());
			genderValue.setFont(lastNameValue.getFont());
			roomValue.setFont(lastNameValue.getFont());
			ageValue.setFont(lastNameValue.getFont());
			
			c.gridx = 0;
			c.gridy = 0;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10,10,1,20);
			patientPanel.add(lastNameValue, c);
			c.gridx = 1;
			c.insets = new Insets(10,0,1,20);
			patientPanel.add(firstNameValue, c);
			c.gridx = 2;
			c.insets = new Insets(10,0,1,20);
			patientPanel.add(middleNameValue, c);
			c.gridx = 0;
			c.gridy = 1;
			c.insets = new Insets(0,10,10,10);
			patientPanel.add(lastNameLabel, c);
			c.gridx = 1;
			c.insets = new Insets(0,0,10,10);
			patientPanel.add(firstNameLabel, c);
			c.gridx = 2;
			c.insets = new Insets(0,0,10,10);
			patientPanel.add(middleNameLabel, c);
			c.gridx = 0;
			c.gridy = 2;
			c.insets = new Insets(0,10,1,20);
			patientPanel.add(genderValue, c);
			c.gridx = 1;
			c.insets = new Insets(0,0,1,20);
			patientPanel.add(ageValue, c);
			c.gridx = 2;
			c.insets = new Insets(0,0,1,20);
			patientPanel.add(roomValue, c);
			c.gridx = 0;
			c.gridy = 3;
			c.insets = new Insets(0,10,10,10);
			patientPanel.add(genderLabel, c);
			c.gridx = 1;
			c.insets = new Insets(0,0,10,10);
			patientPanel.add(ageLabel, c);
			c.gridx = 2;
			c.insets = new Insets(0,0,10,10);
			patientPanel.add(roomLabel, c);
			
			
			JPanel recordPanel = new JPanel(new GridBagLayout());
			recordPanel.setBackground(Color.white);
			recordPanel.setBorder(BorderFactory.createTitledBorder("Record Information"));
			recordPanel.setPreferredSize(new Dimension(450, 300));
			JLabel refNumberLabel = new JLabel("Internal Reference Number");
			JLabel specimenLabel = new JLabel("Specimen(s)");
			JLabel physicianLabel = new JLabel("Physician");
			JLabel pathologistLabel = new JLabel("Pathologist");
			JLabel dateReceivedLabel = new JLabel("Date Recieved");
			JLabel dateCompletedLabel = new JLabel("Date Completed");
			JLabel diagnosisLabel = new JLabel("Diagnosis");
			JLabel remarksLabel = new JLabel("Remarks");
			
			
			JLabel refNumberValue= new JLabel((String)record.getAttribute(RecordTable.REF_NUM.toString()));
			JLabel specimenValue= new JLabel((String)record.getAttribute(RecordTable.SPECIMEN.toString()));
			JLabel physicianValue= new JLabel((String)record.getAttribute(RecordTable.PHYSICIAN.toString()));
			JLabel pathologistValue= new JLabel((String)record.getAttribute(RecordTable.PATHOLOGIST.toString()));
			
			String[] monthNames = {"January", "February", 
					 "March", "April", "May", "June", "July", "August", 
					 "September", "October", "November", "December"};
			Calendar dateReceived = (Calendar)record.getAttribute(RecordTable.DATE_RECEIVED.toString());
			int month = dateReceived.get(Calendar.MONTH);
			int day = dateReceived.get(Calendar.DATE);
			int year = dateReceived.get(Calendar.YEAR);
			
			JLabel dateReceivedValue= new JLabel(monthNames[month] + " " + day + ", " + year);
			Calendar dateCompleted = (Calendar)record.getAttribute(RecordTable.DATE_COMPLETED.toString());
			month = dateCompleted.get(Calendar.MONTH);
			day = dateCompleted.get(Calendar.DATE);
			year = dateCompleted.get(Calendar.YEAR);
			JLabel dateCompletedValue= new JLabel(monthNames[month] +" " + day + ", " + year);
			JLabel diagnosisValue= new JLabel((String)record.getAttribute(RecordTable.DIAGNOSIS.toString()));
			JLabel remarksValue= new JLabel((String)record.getAttribute(RecordTable.REMARKS.toString()));
			
			refNumberValue.setFont(lastNameValue.getFont().deriveFont(Font.BOLD));
			specimenValue.setFont(lastNameValue.getFont());
			physicianValue.setFont(lastNameValue.getFont());
			pathologistValue.setFont(lastNameValue.getFont());
			dateReceivedValue.setFont(lastNameValue.getFont());
			dateCompletedValue.setFont(lastNameValue.getFont());
			diagnosisValue.setFont(lastNameValue.getFont());
			remarksValue.setFont(lastNameValue.getFont());
			
			

			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 3;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10,10,1,10);
			recordPanel.add(refNumberValue, c);
			c.gridx = 0;
			c.gridy = 1;
			c.insets = new Insets(0,10,20,10);
			recordPanel.add(refNumberLabel, c);
			c.gridwidth = 1;
			c.gridy = 2;
			c.insets = new Insets(0,10,1,20);
			recordPanel.add(specimenValue, c);
			c.gridx = 1;
			c.insets = new Insets(0,0,1,20);
			recordPanel.add(pathologistValue, c);
			c.gridx = 2;
			c.insets = new Insets(0,0,1,10);
			recordPanel.add(physicianValue, c);
			c.gridx = 0;
			c.gridy = 3;
			c.insets = new Insets(0,0,20,20);
			recordPanel.add(specimenLabel, c);
			c.gridx = 1;
			c.insets = new Insets(0,0,20,20);
			recordPanel.add(pathologistLabel, c);
			c.gridx = 2;
			c.insets = new Insets(0,0,20,10);
			recordPanel.add(physicianLabel, c);
			c.gridy = 4;
			c.gridx = 0;
			c.insets = new Insets(0,10, 1, 20);
			recordPanel.add(dateReceivedValue, c);
			c.gridx = 2;
			c.insets = new Insets(0,0,1,10);
			recordPanel.add(dateCompletedValue, c);
			c.gridy = 5;
			c.gridx = 0;
			c.insets = new Insets(0,10,20,20);
			recordPanel.add(dateReceivedLabel, c);
			c.gridx = 2;
			c.insets = new Insets(0,0,20,10);
			recordPanel.add(dateCompletedLabel, c);
			c.gridx = 1;
			c.gridy = 6;
			c.insets = new Insets(0,10,1,20);
			recordPanel.add(diagnosisLabel, c);
			c.gridx = 0;
			c.gridy = 7;
			c.gridwidth = 3;
			c.gridheight = 3;
			c.insets = new Insets(0,10,10,10);
			recordPanel.add(diagnosisValue, c);
			c.gridy = 10;
			c.gridx = 1;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.insets = new Insets(0,10,1,20);
			recordPanel.add(remarksLabel, c);
			c.gridx = 0;
			c.gridy = 11;
			c.gridwidth = 3;
			c.gridheight = 3;
			c.insets = new Insets(0,10,10,10);
			recordPanel.add(remarksValue, c);
			
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.insets = new Insets(10,10,10,10);
			objectDetails.add(patientPanel, c);
			c.gridy = 1;
			objectDetails.add(recordPanel, c);
		}
		JScrollPane scroller = new JScrollPane(objectDetails);
		scroller.setBorder(null);
		scroller.setPreferredSize(new Dimension(500,500));
		detailsPanel.add("OBJECT_DETAIL", scroller);
		CardLayout cl = (CardLayout)detailsPanel.getLayout();
		cl.last(detailsPanel);
	}
}
