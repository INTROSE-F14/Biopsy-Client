package org.introse;



import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.introse.Constants.PatientTable;
import org.introse.core.CytologyRecord;
import org.introse.core.Database;
import org.introse.core.GynecologyRecord;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Patient;
import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.core.dao.PatientDao;
import org.introse.core.dao.RecordDao;
import org.introse.core.network.Client;
import org.introse.gui.dialogbox.ErrorDialog;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.CytologyForm;
import org.introse.gui.form.Form;
import org.introse.gui.form.GynecologyForm;
import org.introse.gui.form.HistopathologyForm;
import org.introse.gui.form.PatientForm;
import org.introse.gui.panel.ContentPanel;
import org.introse.gui.panel.DetailPanel;
import org.introse.gui.panel.ListItem;
import org.introse.gui.panel.PatientPanel;
import org.introse.gui.panel.RecordPanel;
import org.introse.gui.window.LoginWindow;
import org.introse.gui.window.MainMenu;


public class ProjectDriver 
{
	private static LoginWindow loginForm;
	private final Client client = new Client();
	private static final CustomListener listener =  new CustomListener(new ProjectDriver());
	private MainMenu mainMenu;
	private RecordDao recordDao;
	private PatientDao patientDao;
	private List<ListItem> patientList;
	private List<ListItem> histopathologyList;
	private List<ListItem> gynecologyList;
	private List<ListItem> cytologyList;
	private DetailPanel panel;
	
	public static void main(String[] args) 
	{
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) 
		{e.printStackTrace();}
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGui();
			}
		});
	}
	
	public static void createAndShowGui()
	{
		loginForm = new LoginWindow();
		loginForm.setActionListener(listener);
		loginForm.showGUI();
	}
	
	
	public void login()
	{
		String password = new String(loginForm.getPassword());
		int loginStatus = client.connectToServer(password);
		switch(loginStatus)
		{
			case Constants.NetworkConstants.AUTHENTICATION_SUCCESSFUL: 
				String[] serverInfo = client.getServerInfo().split(":");
				String dbUsername = serverInfo[0];
				String dbPassword = serverInfo[1];
				String dbName = serverInfo[2];
				int port = Integer.parseInt(serverInfo[3]);
				String ip = serverInfo[4];
				Database db = new Database(ip, dbUsername, dbPassword, dbName,port);
				Preferences.setDatabase(db);
				loginForm.exit();
				startMainMenu();
				break;
			case Constants.NetworkConstants.AUTHENTICATION_FAILED:
				new ErrorDialog("Login Failed", "You entered an invalid password").showGui();
				break;
			case Constants.NetworkConstants.SERVER_ERROR:
				new ErrorDialog("Server Error", "An error occured while trying to connect to the server").showGui();
				break;
		}
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public void startMainMenu()
	{
		recordDao = new RecordDao(client);
		patientDao = new PatientDao(client);
		showMainMenu();
	}
	
	private void showMainMenu()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mainMenu = new MainMenu();
				mainMenu.addListener(listener);
				refresh();
				mainMenu.showGUI();
			}});
	}	
	
	
	public void changeView(String view, boolean removeDetails)
	{
		mainMenu.getContentPanel().changeView(view);
		if(removeDetails)
			removeDetailsPanel();
		applyFilter();
	}
	
	public void setSelectedButton(Object button)
	{
		if(button instanceof String)
		mainMenu.getNavigationPanel().setSelectedButton((String)button);
		else mainMenu.getNavigationPanel().setSelectedButton(button);
	}
	
	public void logout()
	{
		mainMenu.exit();
		createAndShowGui();
	}

	public List<ListItem> updateHistopathologyList()
	{
		List<ListItem> histopathologyList = new Vector<ListItem>();
		HistopathologyRecord record = new HistopathologyRecord();
		record.putAttribute(Constants.RecordTable.RECORD_TYPE, 0);
		List<Object> records = recordDao.search(record);
		Iterator<Object> i = records.iterator();
		while(i.hasNext())
		{
			Record curRecord = (HistopathologyRecord)i.next();
			int patientID = (int)curRecord.getAttribute(Constants.RecordTable.PATIENT_ID);
			Patient patient = new Patient(patientID);
			Patient p = (Patient)patientDao.get(patient);
			String patientName = p.getAttribute(PatientTable.LAST_NAME) + 
					", " + p.getAttribute(PatientTable.FIRST_NAME) + " " + 
					p.getAttribute(PatientTable.MIDDLE_NAME);
			ListItem item = new ListItem((String)curRecord.getAttribute(Constants.RecordTable.REF_NUM),
					(String)curRecord.getAttribute(Constants.RecordTable.REF_NUM), 
					(String)curRecord.getAttribute(Constants.RecordTable.SPECIMEN), patientName, 
					(String)curRecord.getAttribute(Constants.RecordTable.PATHOLOGIST),
					Constants.RecordConstants.HISTOPATHOLOGY_RECORD);
			histopathologyList.add(item);
		}
		return histopathologyList;
	}
	
	public List<ListItem> updateCytologyList()
	{
		List<ListItem> cytologyList = new Vector<ListItem>();
		CytologyRecord record = new CytologyRecord();
		record.putAttribute(Constants.RecordTable.RECORD_TYPE, 2);
		List<Object> records = recordDao.search(record);
		
		Iterator<Object> i = records.iterator();
		while(i.hasNext())
		{
			Record curRecord = (CytologyRecord)i.next();
			int patientID = (int)curRecord.getAttribute(Constants.RecordTable.PATIENT_ID);
			Patient patient = new Patient(patientID);
			Patient p = (Patient)patientDao.get(patient);
			String patientName = p.getAttribute(PatientTable.LAST_NAME) + 
					", " + p.getAttribute(PatientTable.FIRST_NAME) + " " + 
					p.getAttribute(PatientTable.MIDDLE_NAME);
			ListItem item = new ListItem((String)curRecord.getAttribute(Constants.RecordTable.REF_NUM),
					(String)curRecord.getAttribute(Constants.RecordTable.REF_NUM), 
					(String)curRecord.getAttribute(Constants.RecordTable.SPECIMEN), patientName, 
					(String)curRecord.getAttribute(Constants.RecordTable.PATHOLOGIST),
					Constants.RecordConstants.CYTOLOGY_RECORD);
			cytologyList.add(item);
		}
		return cytologyList;
	}
	
	public List<ListItem> updateGynecologyList()
	{
		List<ListItem> gynecologyList = new Vector<ListItem>();
		GynecologyRecord record = new GynecologyRecord();
		record.putAttribute(Constants.RecordTable.RECORD_TYPE, 1);
		List<Object> records = recordDao.search(record);
		
		Iterator<Object> i = records.iterator();
		while(i.hasNext())
		{
			Record curRecord = (GynecologyRecord)i.next();
			int patientID = (int)curRecord.getAttribute(Constants.RecordTable.PATIENT_ID);
			Patient patient = new Patient(patientID);
			Patient p = (Patient)patientDao.get(patient);
			String patientName = p.getAttribute(PatientTable.LAST_NAME) + 
					", " + p.getAttribute(PatientTable.FIRST_NAME) + " " + 
					p.getAttribute(PatientTable.MIDDLE_NAME);
			ListItem item = new ListItem((String)curRecord.getAttribute(Constants.RecordTable.REF_NUM),
					(String)curRecord.getAttribute(Constants.RecordTable.REF_NUM), 
					(String)curRecord.getAttribute(Constants.RecordTable.SPECIMEN), patientName, 
					(String)curRecord.getAttribute(Constants.RecordTable.PATHOLOGIST),
					Constants.RecordConstants.GYNECOLOGY_RECORD);
			gynecologyList.add(item);
		}
		return gynecologyList;
	}
	
	public List<ListItem> updatePatientList()
	{
		List<ListItem> patientList = new Vector<ListItem>();
		List<Object> patients = patientDao.getAll();
		
		Iterator<Object> i = patients.iterator();
		while(i.hasNext())
		{
			Patient patient = (Patient)i.next();
			int patientID = (int)patient.getAttribute(PatientTable.PATIENT_ID);
			String patientName = patient.getAttribute(PatientTable.LAST_NAME) + 
					", " + patient.getAttribute(PatientTable.FIRST_NAME) + " " + 
					patient.getAttribute(PatientTable.MIDDLE_NAME);
			String gender = (String)patient.getAttribute(PatientTable.GENDER);
			Calendar c = (Calendar)patient.getAttribute(PatientTable.BIRTHDAY);
			int age = Calendar.getInstance().get(Calendar.YEAR) - c.get(Calendar.YEAR);
			ListItem item = new ListItem(""+patientID, patientName, gender, patientName, 
					age+"", Constants.RecordConstants.PATIENT);
			patientList.add(item);
		}
		return patientList;
	}
	
	public void refresh()
	{
		recordDao.updateList();
		patientDao.updateList();
		histopathologyList = updateHistopathologyList();
		gynecologyList = updateGynecologyList();
		cytologyList = updateCytologyList();
		patientList = updatePatientList();
		applyFilter();
	}
	
	public void removeDetailsPanel()
	{
		mainMenu.getContentPanel().setDetailsPanel(null);
		panel = null;
	}

	public void applyFilter()
	{
		ContentPanel panel = mainMenu.getContentPanel();
		String view = panel.getCurrentView();
		switch(view)
		{
			case Constants.TitleConstants.HISTOPATHOLOGY:
											panel.updateHistopathologyList(filterList(mainMenu.getContentPanel().getFilter(), 
											histopathologyList));
							  break;
			case Constants.TitleConstants.GYNECOLOGY: 
											panel.updateGynecologyList(filterList(mainMenu.getContentPanel().getFilter(), 
							 				gynecologyList));
							 break;
			case Constants.TitleConstants.CYTOTOLOGY: 
											panel.updateCytologyList(filterList(mainMenu.getContentPanel().getFilter(), 
											cytologyList));
							 break;
			case Constants.TitleConstants.PATIENTS: 
											panel.updatePatientList(filterList(mainMenu.getContentPanel().getFilter(), 
											patientList));
		}
	}
	
	public List<ListItem> filterList(String filter, List<ListItem> currentList)
	{
		if(filter.length() < 1)
			return currentList;
		List<ListItem> filteredList = new Vector<ListItem>();
		Iterator<ListItem> i = currentList.iterator();
		while(i.hasNext())
		{
			ListItem item = i.next();
			if(item.getHeader1().toLowerCase().startsWith(filter.toLowerCase()))
				filteredList.add(item);
		}
		return filteredList;
	}
	
	public void viewItem(ListItem item)
	{
		Object object = null;
		String id = item.getID();
		switch(item.getType())
		{
			case Constants.RecordConstants.HISTOPATHOLOGY_RECORD:
				object = new HistopathologyRecord();
				((HistopathologyRecord)object).putAttribute(Constants.RecordTable.REF_NUM, id);
				object = (HistopathologyRecord)recordDao.get(object);
				break;
				
			case Constants.RecordConstants.CYTOLOGY_RECORD:
				object = new CytologyRecord();
				((CytologyRecord)object).putAttribute(Constants.RecordTable.REF_NUM, id);
				object = (CytologyRecord)recordDao.get(object);
				break;
			case Constants.RecordConstants.GYNECOLOGY_RECORD:
				object = new GynecologyRecord();
				((GynecologyRecord)object).putAttribute(Constants.RecordTable.REF_NUM, id);
				object = (GynecologyRecord)recordDao.get(object);
				break;
			case Constants.RecordConstants.PATIENT:
				object = new Patient();
				((Patient)object).putAttribute(PatientTable.PATIENT_ID, Integer.parseInt(id));
				object = (Patient)patientDao.get(object);
				break;
		}
		if(object != null)
		{
			if(panel != null && panel instanceof RecordPanel)
				if(panel.getMode() == Constants.ActionConstants.NEW)
				{
					loadExistingPatient(object);
					return;
				}
			showDetails(object);
		}
	}
	
	public void showDetails(Object object)
	{
		JPanel panel = null;
		if(object instanceof Record)
		{
			Record record = (Record)object;
			Patient patient = new Patient();
			patient.putAttribute(PatientTable.PATIENT_ID, 
					record.getAttribute(Constants.RecordTable.PATIENT_ID));
			patient = (Patient)patientDao.get(patient);
			
			JPanel recordForm = null;
			JPanel patientForm = null;
			
			if(object instanceof HistopathologyRecord)
				recordForm = new HistopathologyForm();
			else if(object instanceof GynecologyRecord)
				recordForm = new GynecologyForm();
			else if(object instanceof CytologyRecord)
				recordForm = new CytologyForm();
			patientForm = new PatientForm();
			
			((Form)recordForm).setFields(record);
			((Form)patientForm).setFields(patient);
			
			panel = new RecordPanel(recordForm, patientForm, Constants.ActionConstants.VIEW);
			((DetailPanel)panel).addListener(listener);
		}
		
		else if(object instanceof Patient)
		{
			Patient patient = (Patient)object;
			PatientForm form = new PatientForm();
			form.setFields(patient);
			panel = new PatientPanel(form, Constants.ActionConstants.VIEW);
			((DetailPanel)panel).addListener(listener);
		}
		this.panel = (DetailPanel)panel;
		mainMenu.getContentPanel().setDetailsPanel(panel);
	}
	
	public void editCurrentForm()
	{
		panel.setMode(Constants.ActionConstants.EDIT);
	}
	
	public void saveCurrentForm()
	{
		if(panel.areFieldsValid())
		{
			System.out.println("fields valid");
			if(panel instanceof RecordPanel)
			{
				Patient p = panel.getPatient();
				Record r = panel.getRecord();
				DetailPanel rP = (DetailPanel)panel;
				switch(rP.getMode())
				{
				case Constants.ActionConstants.NEW:  patientDao.add(p);
				case Constants.ActionConstants.EDIT: r.putAttribute(Constants.RecordTable.PATIENT_ID, 
										   			 p.getAttribute(PatientTable.PATIENT_ID));
													 recordDao.add(r);
													 break;
				}
			}
			else if(panel instanceof PatientPanel)
			{
				patientDao.add(panel.getPatient());
			}
			panel.setMode(Constants.ActionConstants.VIEW);
			refresh();
		}
		else new ErrorDialog("Save Error", "Some fields are invalid").showGui();
	}
	
	public void cancelCurrentForm()
	{
		if(((DetailPanel)panel).getMode() == Constants.ActionConstants.EDIT)
		{
			if(panel instanceof RecordPanel)
			{
				Record record = panel.getRecord();
				record = (Record)recordDao.get(record);
				((RecordPanel)panel).getRecordForm().setFields(record);
				Patient patient = panel.getPatient();
				((RecordPanel)panel).getPatientForm().setFields(patientDao.get(patient));
			}
			else if(panel instanceof PatientPanel)
			{
				((PatientPanel)panel).getPatientForm().setFields(patientDao.get(panel.getPatient()));
			}
			panel.setMode(Constants.ActionConstants.VIEW);
		}
		else removeDetailsPanel();
	}
	
	public void createNew(int type)
	{
		JPanel panel = null;
		JPanel recordForm = null;
		JPanel patientForm = null;
		Record record;
		Patient patient;
		switch(type)
		{
		case Constants.RecordConstants.HISTOPATHOLOGY_RECORD:	
											record = new HistopathologyRecord();
											record.putAttribute(Constants.RecordTable.REF_NUM, 
													generateReferenceNumber(Constants.RecordConstants.HISTOPATHOLOGY_RECORD));
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientList.size() + 1);
											recordForm = new HistopathologyForm();
											patientForm = new PatientForm();
											((Form)patientForm).setFields(patient);
											((Form)recordForm).setFields(record);
											panel = new RecordPanel(recordForm, patientForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.GYNECOLOGY_RECORD:		
											record = new GynecologyRecord();
											record.putAttribute(Constants.RecordTable.REF_NUM,
													generateReferenceNumber(Constants.RecordConstants.GYNECOLOGY_RECORD));
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientList.size() + 1);
											recordForm = new GynecologyForm();
											patientForm = new PatientForm();
											((Form)patientForm).setFields(patient);
											((Form)recordForm).setFields(record);
											panel = new RecordPanel(recordForm, patientForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.CYTOLOGY_RECORD:			
											record = new CytologyRecord();
											record.putAttribute(Constants.RecordTable.REF_NUM,
													generateReferenceNumber(Constants.RecordConstants.CYTOLOGY_RECORD));
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientList.size() + 1);
											recordForm = new CytologyForm();
											patientForm = new PatientForm();
											((Form)patientForm).setFields(patient);
											((Form)recordForm).setFields(record);
											panel = new RecordPanel(recordForm, patientForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.PATIENT:			
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientList.size() + 1);
											patientForm = new PatientForm();
											((Form)patientForm).setFields(patient);
											panel = new PatientPanel(patientForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
		}
		this.panel = (DetailPanel)panel;
		mainMenu.getContentPanel().setDetailsPanel(panel);
	}
	
	public String generateReferenceNumber(int type)
	{
		String prefix = "";
		String refNum = "";
		String recordNum = "";
		
		Record record = null;
		int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
		switch(type)
		{
		case Constants.RecordConstants.HISTOPATHOLOGY_RECORD: 
										 prefix = "H" + year + "-";
										 record = new HistopathologyRecord();
										 break;
		case Constants.RecordConstants.GYNECOLOGY_RECORD: 
										prefix = "G" + year + "-";
										record = new GynecologyRecord();
										break;
		case Constants.RecordConstants.CYTOLOGY_RECORD: 
										prefix = "C" + year + "-";
										record = new CytologyRecord();
		}
		
		int i = 0;
		do
		{
			recordNum = "";
			if(i < 10)
				recordNum = "000" + i;
			else if(i < 100)
				recordNum = "00" + i;
			else if(i < 1000)
				recordNum = "0" + i;
			else recordNum = ""+i;
			refNum = prefix + recordNum;
			record.putAttribute(Constants.RecordTable.REF_NUM, refNum);
			i++;
		}
		while(recordDao.get(record) != null);
		return refNum;
	}
	
	public void loadExistingPatient(Object patient)
	{
		((RecordPanel)panel).getPatientForm().setFields(patient);
	}
}
