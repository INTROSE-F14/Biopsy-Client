package org.introse;



import java.awt.Color;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

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
import org.introse.core.database.DatabaseHelper;
import org.introse.core.database.FileHelper;
import org.introse.core.network.Client;
import org.introse.gui.dialogbox.ErrorDialog;
import org.introse.gui.dialogbox.PatientLoader;
import org.introse.gui.dialogbox.SearchDialog;
import org.introse.gui.dialogbox.SearchPatientDialog;
import org.introse.gui.dialogbox.SearchRecordDialog;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.CytologyForm;
import org.introse.gui.form.Form;
import org.introse.gui.form.GynecologyForm;
import org.introse.gui.form.HistopathologyForm;
import org.introse.gui.form.PatientForm;
import org.introse.gui.form.RecordForm;
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
	private PatientLoader loader;
	private Object lastSearch;
	
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
		Thread thread = new Thread(
				new Runnable()
				{
					public void run()
					{
						loginForm.setLoadingVisible(true);
						String password = new String(loginForm.getPassword());
						int loginStatus = client.connectToServer(password);
						loginForm.setLoadingVisible(false);
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
				});
		thread.start();
	}
	
	private void startMainMenu()
	{
		recordDao = new MysqlRecordDao();
		patientDao = new MysqlPatientDao();
		diagnosisDao = new MysqlDiagnosisDao();
		System.out.println(FileHelper.createProgramDirectory());
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mainMenu = new MainMenu();
				mainMenu.addListener(listener);
				refresh(TitleConstants.ALL);
				changeView(TitleConstants.HISTOPATHOLOGY);
				mainMenu.showGUI();
			}});
	}
	
	public void changeView(String view)
	{
		mainMenu.getContentPanel().changeView(view);
	}
	
	public String getPreviousView()
	{
		return mainMenu.getContentPanel().getPreviousView();
	}
	
	public String getCurrentView()
	{
		return mainMenu.getContentPanel().getCurrentView();
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
			Record record = (Record)listItem;
			int patientID = (int)record.getAttribute(RecordTable.PATIENT_ID);
			Patient p = new Patient();
			p.putAttribute(PatientTable.PATIENT_ID, patientID);
			record.putAttribute(RecordTable.PATIENT, patientDao.get(p));
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
	
	public void refresh(String view)
	{
		Record record = null;
		switch(view)
		{
			case Constants.TitleConstants.HISTOPATHOLOGY: 
				record = new HistopathologyRecord();
				record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.HISTOPATHOLOGY_RECORD);
				histopathologyList = generateRecordList(recordDao.search(record));
							
							  break;
			case Constants.TitleConstants.GYNECOLOGY:  
				record = new GynecologyRecord();
				record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.GYNECOLOGY_RECORD);
				gynecologyList = generateRecordList(recordDao.search(record));
							 
							  break;
			case Constants.TitleConstants.CYTOLOGY:  
				record = new CytologyRecord();
				record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.CYTOLOGY_RECORD);
				cytologyList = generateRecordList(recordDao.search(record));
							 
							  break;
			case Constants.TitleConstants.PATHOLOGISTS:  
							
							  break;
			case Constants.TitleConstants.PATIENTS: 
				patientList = generatePatientList(patientDao.getAll());
							
								break;
			case Constants.TitleConstants.PHYSICIANS:  
							
							  break;
			case Constants.TitleConstants.SPECIMENS:  
				
							
							  break;
			case Constants.TitleConstants.SEARCH_RESULT:
				if(lastSearch instanceof Record)
					searchList = generateRecordList(recordDao.search((Record)lastSearch));
				else searchList = generatePatientList(patientDao.search((Patient)lastSearch));
							  break;
			case "ALL":
				record = new HistopathologyRecord();
				record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.HISTOPATHOLOGY_RECORD);
				histopathologyList = generateRecordList(recordDao.search(record));
				record = new GynecologyRecord();
				record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.GYNECOLOGY_RECORD);
				gynecologyList = generateRecordList(recordDao.search(record));
				record = new CytologyRecord();
				record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.CYTOLOGY_RECORD);
				cytologyList = generateRecordList(recordDao.search(record));
				patientList = generatePatientList(patientDao.getAll());
				break;	  
		}
		applyFilter(view);
	}
	
	public void removeDetailsPanel()
	{
		mainMenu.getContentPanel().setDetailsPanel(null);
		panel = null;
	}

	public void applyFilter(String view)
	{
		ContentPanel panel = mainMenu.getContentPanel();
		if(view.equals(TitleConstants.ALL))
			view = panel.getCurrentView();
		List<ListItem> list = null;
		switch(view)
		{
		
			case Constants.TitleConstants.HISTOPATHOLOGY:
											list = filterList(mainMenu.getContentPanel().getFilter(), 
											histopathologyList);
							  break;
			case Constants.TitleConstants.GYNECOLOGY: 
											list = filterList(mainMenu.getContentPanel().getFilter(), 
							 				gynecologyList);
							 break;
			case Constants.TitleConstants.CYTOLOGY: 
											list = filterList(mainMenu.getContentPanel().getFilter(), 
											cytologyList);
							 break;
			case Constants.TitleConstants.PATIENTS: 
											list = filterList(mainMenu.getContentPanel().getFilter(), 
											patientList);
											break;
			case TitleConstants.SEARCH_RESULT:
											list = filterList(mainMenu.getContentPanel().getFilter(),
													searchList);
											break;
		}
		panel.updateList(list, view);
	}
	
	public List<ListItem> filterList(String filter, List<ListItem> currentList)
	{
		if(filter.length() < 1 || filter.equals(TitleConstants.QUICK_FILTER))
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
					j = listItem.getLabelCount();
				}
		}
		return filteredList;
	}
	
	public void viewItem(ListItem listItem)
	{
		Object object = listItem;
		if(object != null)
		{
			
			if(panel != null && panel instanceof RecordPanel && listItem instanceof Patient)
			{	
				if(panel.getMode() == Constants.ActionConstants.NEW)
				{
					System.out.println("load");
					loadExistingPatient(object);
					return;
				}
			}
			else if(searchDialog != null && listItem instanceof Patient)
			{
				System.out.println("Search");
				Patient patient = (Patient)object;
				((SearchRecordDialog)searchDialog).setPatient(patient);
				loader.dispose();
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
			
			RecordForm recordForm = null;
			List<Diagnosis> diagnosis = diagnosisDao.getDiagnosis(record);
			record.putAttribute(RecordTable.DIAGNOSIS, diagnosis);
			record.putAttribute(RecordTable.PATIENT, patient);
			
			if(object instanceof HistopathologyRecord)
				recordForm = new HistopathologyForm();
			else if(object instanceof GynecologyRecord)
				recordForm = new GynecologyForm();
			else if(object instanceof CytologyRecord)
				recordForm = new CytologyForm();
			
			recordForm.setFields(record, patient);
			
			panel = new RecordPanel((JPanel)recordForm, Constants.ActionConstants.VIEW);
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
		mainMenu.getContentPanel().changeView(TitleConstants.DETAIL_PANEL);
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
				Record r = ((RecordPanel)panel).getRecordForm().getRecord();
				Patient p = ((RecordPanel)panel).getRecordForm().getPatient();
				DetailPanel rP = (DetailPanel)panel;
				r.putAttribute(Constants.RecordTable.PATIENT_ID, 
						 p.getAttribute(PatientTable.PATIENT_ID));				
				switch(rP.getMode())
				{
				case Constants.ActionConstants.NEW:  if(patientDao.get(p) == null)
													 	patientDao.add(p);
													 recordDao.assignReferenceNumber(r);
													 recordDao.add(r);
													 break;
													 
				case Constants.ActionConstants.EDIT: recordDao.update(r);
													 break;
				}
				diagnosisDao.delete(r);
				List<Diagnosis> diagnosis = (List)r.getAttribute(RecordTable.DIAGNOSIS);
				if(diagnosis != null)
				{
					Iterator<Diagnosis> i = diagnosis.iterator();
					while(i.hasNext())
					{
						Diagnosis d = i.next();
						d.setReferenceNumber(((char)r.getAttribute(RecordTable.RECORD_TYPE)),
								(int)r.getAttribute(RecordTable.RECORD_YEAR), (int)r.getAttribute(RecordTable.RECORD_NUMBER));
						diagnosisDao.add(d);
					}
				}
				((RecordPanel)panel).getRecordForm().setFields(r, p);
			}
			else if(panel instanceof PatientPanel)
			{
				Patient p = (Patient)panel.getObject();
				DetailPanel rP = (DetailPanel)panel;
				switch(rP.getMode())
				{
				case ActionConstants.NEW:	patientDao.add(p);
											break;
				case ActionConstants.EDIT:	patientDao.update(p);
				}
			}
			panel.setMode(Constants.ActionConstants.VIEW);
			refresh(mainMenu.getContentPanel().getPreviousView());
		}
		else new ErrorDialog("Save Error", "Some fields are invalid").showGui();
	}
	
	public void cancelCurrentForm()
	{
		if(((DetailPanel)panel).getMode() == Constants.ActionConstants.EDIT)
		{
			if(panel instanceof RecordPanel)
			{
				Record record = (Record)panel.getObject();
				record = (Record)recordDao.get(record);
				List<Diagnosis> diagnosis = diagnosisDao.getDiagnosis(record);
				record.putAttribute(RecordTable.DIAGNOSIS, diagnosis);
				Patient patient = new Patient();
				patient.putAttribute(PatientTable.PATIENT_ID, record.getAttribute(RecordTable.PATIENT_ID));
				patient = (Patient)patientDao.get(patient);
				((RecordPanel)panel).getRecordForm().setFields(record, patient);
			}
			else if(panel instanceof PatientPanel)
			{
				((PatientPanel)panel).getPatientForm().setFields((Patient)patientDao.get((Patient)panel.getObject()));
			}
			panel.setMode(Constants.ActionConstants.VIEW);
		}
		else
		{
			removeDetailsPanel();
			changeView(getPreviousView());
		}
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
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientList.size() + 1);
											recordForm = new HistopathologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.GYNECOLOGY_RECORD:		
											record = new GynecologyRecord();
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientList.size() + 1);
											recordForm = new GynecologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.CYTOLOGY_RECORD:			
											record = new CytologyRecord();
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientList.size() + 1);
											recordForm = new CytologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
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
		mainMenu.getContentPanel().changeView(TitleConstants.DETAIL_PANEL);
	}
	
	public void loadExistingPatient(Object patient)
	{
		((RecordForm)((RecordPanel)panel).getRecordForm()).setPatient((Patient)patient);
		loader.dispose();
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

	public void displaySearchResult()
	{
		ContentPanel panel = mainMenu.getContentPanel();
		if(searchDialog instanceof SearchRecordDialog)
		{
			Record record = (Record)searchDialog.getSearchCriteria();
			lastSearch = record;
			List<Record> matches = recordDao.search(record);
			searchList = generateRecordList(matches);
		}
		else
		{
			Patient patient = (Patient)searchDialog.getSearchCriteria();
			lastSearch = patient;
			List<Patient> matches = patientDao.search(patient);
			searchList = generatePatientList(matches);
		}
		searchDialog.dispose();
		searchDialog = null;
		panel.updateList(searchList, TitleConstants.SEARCH_RESULT);
		changeView(TitleConstants.SEARCH_RESULT);
		removeDetailsPanel();
		setSelectedButton("");
	}
	
	public void openPatientLoad()
	{
		loader = new PatientLoader(patientList, listener);
		loader.showGUI();
	}
	
	public void selectRestorePath()
	{
		final JFileChooser chooser = new JFileChooser(FileHelper.getBackupDirectory());
		FileNameExtensionFilter backupFilter = new FileNameExtensionFilter("BCB file", "bcb");
		chooser.setFileFilter(backupFilter);
		int returnVal = chooser.showOpenDialog(mainMenu.getContentPanel());
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			mainMenu.getContentPanel().getBackupAndRestorePanel().
				setRestorePath(chooser.getSelectedFile().getAbsolutePath());
			mainMenu.getContentPanel().getBackupAndRestorePanel().setRestoreEnabled(true);
		}
	}
	
	public void selectBackupPath()
	{
		final JFileChooser chooser = new JFileChooser();
		chooser.setSelectedFile(FileHelper.createBackupFile());
		FileNameExtensionFilter backupFilter = new FileNameExtensionFilter("BCB file", "bcb");
		chooser.setFileFilter(backupFilter);
		int returnVal = chooser.showDialog(mainMenu.getContentPanel(), "Create backup");
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			mainMenu.getContentPanel().getBackupAndRestorePanel().
				setBackupPath(chooser.getSelectedFile().getAbsolutePath());
			mainMenu.getContentPanel().getBackupAndRestorePanel().setBackupEnabled(true);
		}
	}
	
	public void backup()
	{
		String backupPath = mainMenu.getContentPanel().getBackupAndRestorePanel().getBackupPath();
		DatabaseHelper helper = new DatabaseHelper(patientDao, recordDao, diagnosisDao);
		helper.backup(new File(backupPath));
		mainMenu.getContentPanel().getBackupAndRestorePanel().setBackupPath("");
		mainMenu.getContentPanel().getBackupAndRestorePanel().setBackupEnabled(false);
	}
	
	public void restore()
	{
		String restorePath = mainMenu.getContentPanel().getBackupAndRestorePanel().getRestorePath();
		DatabaseHelper helper = new DatabaseHelper(patientDao, recordDao, diagnosisDao);
		helper.restore(new File(restorePath));
		mainMenu.getContentPanel().getBackupAndRestorePanel().setRestorePath("");
		mainMenu.getContentPanel().getBackupAndRestorePanel().setRestoreEnabled(false);
	}
}
