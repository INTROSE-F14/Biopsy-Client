package org.introse;



import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.introse.Constants.ActionConstants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.TitleConstants;
import org.introse.core.CytologyRecord;
import org.introse.core.Diagnosis;
import org.introse.core.GynecologyRecord;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Patient;
import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.core.dao.MysqlDiagnosisDao;
import org.introse.core.dao.MysqlPatientDao;
import org.introse.core.dao.MysqlRecordDao;
import org.introse.core.network.Client;
import org.introse.gui.dialogbox.ErrorDialog;
import org.introse.gui.dialogbox.SearchDialog;
import org.introse.gui.dialogbox.SearchPatientDialog;
import org.introse.gui.dialogbox.SearchRecordDialog;
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
	private MysqlRecordDao recordDao;
	private MysqlPatientDao patientDao;
	private MysqlDiagnosisDao diagnosisDao;
	private List<ListItem> patientList;
	private List<ListItem> histopathologyList;
	private List<ListItem> gynecologyList;
	private List<ListItem> cytologyList;
	private List<ListItem> searchList;
	private DetailPanel panel;
	private SearchDialog searchDialog;
	
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
		loginForm.addListener(listener);
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
				Preferences.setDatabaseName(dbName);
				Preferences.setDatabaseAddress(ip, port);
				Preferences.setDatabaseUsername(dbUsername);
				Preferences.setDatabasePassword(dbPassword);
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
	
	private void startMainMenu()
	{
		recordDao = new MysqlRecordDao();
		patientDao = new MysqlPatientDao();
		diagnosisDao = new MysqlDiagnosisDao();

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
	
	public List<ListItem> generateRecordList(List<Record> records)
	{
		List<ListItem> recordList = new Vector<ListItem>();
		
		Iterator<Record> i = records.iterator();
		while(i.hasNext())
		{
			ListItem listItem = i.next();
			listItem.initializePanel();
			recordList.add(listItem);
		}
		return recordList;
	}
	
	public List<ListItem> generatePatientList(List<Patient> patients)
	{
		List<ListItem> patientList = new Vector<ListItem>();
		Iterator<Patient> i = patients.iterator();
		
		while(i.hasNext())
		{	
			ListItem listItem = i.next();
			listItem.initializePanel();
			patientList.add(listItem);
		}
		return patientList;
	}
	
	public void refresh()
	{
		
		Record record = new HistopathologyRecord();
		record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.HISTOPATHOLOGY_RECORD);
		histopathologyList = generateRecordList(recordDao.search(record));
		record = new GynecologyRecord();
		record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.GYNECOLOGY_RECORD);
		gynecologyList = generateRecordList(recordDao.search(record));
		record = new CytologyRecord();
		record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.CYTOLOGY_RECORD);
		cytologyList = generateRecordList(recordDao.search(record));
		patientList = generatePatientList(patientDao.getAll());
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
			case Constants.TitleConstants.CYTOLOGY: 
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
			ListItem listItem = i.next();
			for(int j = 0; j < listItem.getLabelCount(); j++)
			if(listItem.getLabel(j).toLowerCase().contains(filter.toLowerCase()))
			{
				filteredList.add(listItem);
				continue;
			}
		}
		return filteredList;
	}
	
	public void viewItem(ListItem listItem)
	{
		Object object = listItem;
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
			List<Diagnosis> diagnosis = diagnosisDao.getDiagnosis(record);
			record.putAttribute(RecordTable.DIAGNOSIS, diagnosis);
			
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
			if(panel instanceof RecordPanel)
			{
				Patient p = panel.getPatient();
				Record r = panel.getRecord();
				DetailPanel rP = (DetailPanel)panel;
				r.putAttribute(Constants.RecordTable.PATIENT_ID, 
						 p.getAttribute(PatientTable.PATIENT_ID));				
				switch(rP.getMode())
				{
				case Constants.ActionConstants.NEW:  if(patientDao.get(p) == null)
													 patientDao.add(p);
													 recordDao.add(r);
													 break;
													 
				case Constants.ActionConstants.EDIT: recordDao.update(r);
													 break;
				}
				diagnosisDao.delete(r);
				List<Diagnosis> diagnosis = (List)r.getAttribute(RecordTable.DIAGNOSIS);
				Iterator<Diagnosis> i = diagnosis.iterator();
				while(i.hasNext())
					diagnosisDao.add(i.next());
			}
			else if(panel instanceof PatientPanel)
			{
				Patient p = panel.getPatient();
				DetailPanel rP = (DetailPanel)panel;
				switch(rP.getMode())
				{
				case ActionConstants.NEW:	patientDao.add(p);
											break;
				case ActionConstants.EDIT:	patientDao.update(p);
				}
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
				List<Diagnosis> diagnosis = diagnosisDao.getDiagnosis(record);
				record.putAttribute(RecordTable.DIAGNOSIS, diagnosis);
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
	
	public void openPatientSearch()
	{
		if(searchDialog == null)
		{
			searchDialog = new SearchPatientDialog();
			searchDialog.addListener(listener);
		}
		else if(searchDialog instanceof SearchRecordDialog)
		{
			((SearchRecordDialog)searchDialog).dispose();
			searchDialog = new SearchPatientDialog();
			searchDialog.addListener(listener);
		}
		searchDialog.showGUI();
	}
	
	public void openRecordSearch()
	{
		if(searchDialog == null)
		{
			searchDialog = new SearchRecordDialog();
			searchDialog.addListener(listener);
		}
		else if(searchDialog instanceof SearchPatientDialog)
		{
			((SearchPatientDialog)searchDialog).dispose();
			searchDialog = new SearchRecordDialog();
			searchDialog.addListener(listener);
		}
		searchDialog.showGUI();
	}
	
    public void clearSearchFields(){
        this.searchDialog.clear();
    }

	public void displaySearchResult()
	{
		ContentPanel panel = mainMenu.getContentPanel();
		if(searchDialog instanceof SearchRecordDialog)
		{
			Record record = (Record)searchDialog.getSearchCriteria();
			List<Record> matches = recordDao.search(record);
			searchList = generateRecordList(matches);
		}
		else
		{
			Patient patient = (Patient)searchDialog.getSearchCriteria();
			List<Patient> matches = patientDao.search(patient);
			searchList = generatePatientList(matches);
		}
		panel.updateSearchList(searchList);
		panel.changeView(TitleConstants.SEARCH_RESULT);
		removeDetailsPanel();
		setSelectedButton("");
	}
}
