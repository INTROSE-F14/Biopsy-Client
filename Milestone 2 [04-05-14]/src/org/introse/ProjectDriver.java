package org.introse;



import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.introse.Constants.ActionConstants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.StatusConstants;
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
import org.introse.core.database.FileHelper;
import org.introse.core.network.Client;
import org.introse.core.workers.BackupWorker;
import org.introse.core.workers.ExportWorker;
import org.introse.core.workers.FilterWorker;
import org.introse.core.workers.PatientListGenerator;
import org.introse.core.workers.RecordListGenerator;
import org.introse.core.workers.RestoreWorker;
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
import org.introse.gui.panel.BackupPanel;
import org.introse.gui.panel.ContentPanel;
import org.introse.gui.panel.DetailPanel;
import org.introse.gui.panel.ExportPanel;
import org.introse.gui.panel.ListItem;
import org.introse.gui.panel.ListPanel;
import org.introse.gui.panel.PatientPanel;
import org.introse.gui.panel.RecordPanel;
import org.introse.gui.panel.RestorePanel;
import org.introse.gui.window.LoginWindow;
import org.introse.gui.window.MainMenu;


public class ProjectDriver 
{
	public static LoginWindow loginForm;
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
	private FilterWorker filterWorker;
	
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
		if(loginForm.getPassword().length() > 0)
		{
			loginForm.setLoadingVisible(true);
			loginForm.setLoginButtonEnabled(false);
			final SwingWorker loginWorker = new SwingWorker<Integer, Void>()
			{
				@Override
				protected Integer doInBackground() throws Exception 
				{
					String password = new String(loginForm.getPassword());
					int loginStatus = client.connectToServer(password);
					return loginStatus;
				}
				
				@Override
				protected void done()
				{
					firePropertyChange("DONE", null, null);
				}
			};
			loginWorker.addPropertyChangeListener(new PropertyChangeListener() 
			{	
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals("DONE"))
					{
						int loginStatus;
						try 
						{
							loginStatus = (int)loginWorker.get();
							loginForm.setLoadingVisible(false);
							loginForm.setLoginButtonEnabled(true);
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
						} catch (InterruptedException | ExecutionException e) 
						{
							e.printStackTrace();
						}
					}
					
				}
			});
			loginWorker.execute();
		}
	}
	
	private void startMainMenu()
	{
		loginForm = null;
		recordDao = new MysqlRecordDao();
		patientDao = new MysqlPatientDao();
		diagnosisDao = new MysqlDiagnosisDao();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mainMenu = new MainMenu();
				mainMenu.addListener(listener);
				changeView(TitleConstants.HISTOPATHOLOGY);
				mainMenu.showGUI();
			}});
	}
	
	public void changeView(String view)
	{
		mainMenu.getContentPanel().changeView(view);
		refresh(view);
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
	
	public void refresh(String view)
	{
		Record record = null;
		switch(view)
		{
					case Constants.TitleConstants.HISTOPATHOLOGY: 
						record = new HistopathologyRecord();
						record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.HISTOPATHOLOGY_RECORD);
						updateRList(recordDao.search(record), view, true);	
						break;
					case Constants.TitleConstants.GYNECOLOGY:  
						record = new GynecologyRecord();
						record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.GYNECOLOGY_RECORD);
						updateRList(recordDao.search(record), view, true);
									  break;
					case Constants.TitleConstants.CYTOLOGY:  
						record = new CytologyRecord();
						record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.CYTOLOGY_RECORD);
						updateRList(recordDao.search(record), view, true);
									break;
					case Constants.TitleConstants.PATIENTS: 
						updatePList(patientDao.getAll(), view, true);
										break;
					case Constants.TitleConstants.SEARCH_RESULT:
						if(lastSearch instanceof Record)
							updateRList(recordDao.search((Record)lastSearch), view, true);
						else updatePList(patientDao.search((Patient)lastSearch), view, true);
									  break;
			}
	}
	
	public void removeDetailsPanel()
	{
		mainMenu.getContentPanel().setDetailsPanel(null);
		panel = null;
	}
	
	public void updateList(List<ListItem> list, String view)
	{
		mainMenu.getContentPanel().updateList(list, view);
	}
	
	public void updateRList(List<Record> records, final String view, 
			final boolean isFiltered)
	{	
		final ListPanel listPanel = mainMenu.getContentPanel().getPanel(view);
		listPanel.showPanel(TitleConstants.REFRESH_PANEL);
		
		final RecordListGenerator listWorker = new RecordListGenerator(records, patientDao);
		listWorker.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt) 
			{
				if(evt.getPropertyName().equals("DONE"))
				{
					try 
					{
						switch(view)
						{
						case TitleConstants.HISTOPATHOLOGY:
							histopathologyList = listWorker.get();
							break;
						case TitleConstants.GYNECOLOGY:
							gynecologyList = listWorker.get();
							break;
						case TitleConstants.CYTOLOGY:
							cytologyList = listWorker.get();
							break;
						case TitleConstants.SEARCH_RESULT:
							searchList = listWorker.get();
						}
						if(isFiltered)
						{
							final FilterWorker filterWorker = new FilterWorker(mainMenu.getContentPanel().getFilter(), 
									listWorker.get());
							filterWorker.addPropertyChangeListener(new PropertyChangeListener()
							{
								@Override
								public void propertyChange(PropertyChangeEvent evt) {
									if(evt.getPropertyName().equals("DONE"))
									{
										try 
										{
											List<ListItem> filteredList = filterWorker.get();
											updateList(filteredList, view);
											System.out.println("ASDASD: " + filteredList.size());
											if(filteredList.size() < 1)
												listPanel.showPanel(TitleConstants.EMPTY_PANEL);
											else listPanel.showPanel(TitleConstants.LIST_PANEL);
										} catch (InterruptedException | ExecutionException e) {
											e.printStackTrace();
										}
									}
								}
							});
							filterWorker.execute();
						}
						else 
						{
							List<ListItem> list = listWorker.get();
							updateList(listWorker.get(), view);
							System.out.println("ASDASDA " + list.size());
							if(list.size() < 1)
								listPanel.showPanel(TitleConstants.EMPTY_PANEL);
							else listPanel.showPanel(TitleConstants.LIST_PANEL);
						}
					} catch (InterruptedException | ExecutionException e) 
					{e.printStackTrace();}
				}
			}
		});
		listWorker.execute();
	}
	
	public void updatePList(List<Patient> patients, final String view, 
			final boolean isFiltered)
	{	
		final ListPanel listPanel = mainMenu.getContentPanel().getPanel(view);
		listPanel.showPanel(TitleConstants.REFRESH_PANEL);
		
		final PatientListGenerator listWorker = new PatientListGenerator(patients);
		listWorker.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt) 
			{
				if(evt.getPropertyName().equals("DONE"))
				{
					try 
					{
						switch(view)
						{
						case TitleConstants.PATIENTS:
							patientList = listWorker.get();
							break;
						case TitleConstants.SEARCH_RESULT:
							searchList = listWorker.get();
						}
						if(isFiltered)
						{
							final FilterWorker filterWorker = new FilterWorker(mainMenu.getContentPanel().getFilter(), 
									listWorker.get());
							filterWorker.addPropertyChangeListener(new PropertyChangeListener()
							{
								@Override
								public void propertyChange(PropertyChangeEvent evt) {
									if(evt.getPropertyName().equals("DONE"))
									{
										try 
										{
											List<ListItem> filteredList = filterWorker.get();
											updateList(filteredList, view);
											System.out.println("ASDASD: " + filteredList.size());
											if(filteredList.size() < 1)
												listPanel.showPanel(TitleConstants.EMPTY_PANEL);
											else listPanel.showPanel(TitleConstants.LIST_PANEL);
										} catch (InterruptedException | ExecutionException e) {
											e.printStackTrace();
										}
									}
								}
							});
							filterWorker.execute();
						}
						else 
						{
							List<ListItem> list = listWorker.get();
							updateList(listWorker.get(), view);
							System.out.println("ASDASDA " + list.size());
							if(list.size() < 1)
								listPanel.showPanel(TitleConstants.EMPTY_PANEL);
							else listPanel.showPanel(TitleConstants.LIST_PANEL);
						}
					} catch (InterruptedException | ExecutionException e) 
					{e.printStackTrace();}
				}
			}
		});
		listWorker.execute();
	}

	public void applyFilter(List<ListItem> list, final String view)
	{
		final ListPanel listPanel = mainMenu.getContentPanel().getPanel(view);
		listPanel.showPanel(TitleConstants.REFRESH_PANEL);
		
		final ContentPanel panel = mainMenu.getContentPanel();
		if(filterWorker == null || (filterWorker != null && filterWorker.isDone()))
		{
			filterWorker = new FilterWorker(panel.getFilter(), 
					list);
			filterWorker.addPropertyChangeListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals("DONE"))
					{
						try 
						{
							List<ListItem> filteredList = filterWorker.get();
							updateList(filteredList, view);
							if(filteredList.size() < 1)
								listPanel.showPanel(TitleConstants.EMPTY_PANEL);
							else listPanel.showPanel(TitleConstants.LIST_PANEL);
							
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
					}
				}
			});
			filterWorker.execute();
		}
	}
	
	public void viewItem(ListItem listItem)
	{
		Object object = listItem;
		if(object != null)
		{
			if(listItem instanceof Patient)
			{
				if(panel != null && panel instanceof RecordPanel && panel.getMode() == 
						Constants.ActionConstants.NEW)
				{
						loadExistingPatient(object);
						return;
				}
				else if(searchDialog != null && searchDialog instanceof SearchRecordDialog)
				{
					Patient patient = (Patient)object;
					((SearchRecordDialog)searchDialog).setPatient(patient);
					loader.dispose();
					return;
				}
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
											patient.putAttribute(PatientTable.PATIENT_ID, patientDao.getAll().size() + 1);
											recordForm = new HistopathologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.GYNECOLOGY_RECORD:		
											record = new GynecologyRecord();
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientDao.getAll().size() + 1);
											recordForm = new GynecologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.CYTOLOGY_RECORD:			
											record = new CytologyRecord();
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientDao.getAll().size() + 1);
											recordForm = new CytologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.PATIENT:			
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientDao.getAll().size() + 1);
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
		if(searchDialog instanceof SearchRecordDialog)
		{
			Record record = (Record)searchDialog.getSearchCriteria();
			lastSearch = record;
			List<Record> matches = recordDao.search(record);
			updateRList(matches, TitleConstants.SEARCH_RESULT, true);
		}
		else
		{
			Patient patient = (Patient)searchDialog.getSearchCriteria();
			lastSearch = patient;
			List<Patient> matches = patientDao.search(patient);
			updatePList(matches, TitleConstants.SEARCH_RESULT, true);
		}
		searchDialog.dispose();
		searchDialog = null;
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
		RestorePanel restorePanel = mainMenu.getContentPanel().getToolsPanel().getRestorePanel();
		final JFileChooser chooser = new JFileChooser(FileHelper.getBackupDirectory());
		FileNameExtensionFilter backupFilter = new FileNameExtensionFilter("BCB file", "bcb");
		chooser.setFileFilter(backupFilter);
		int returnVal = chooser.showOpenDialog(mainMenu.getContentPanel());
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			System.out.println(chooser.getSelectedFile().getAbsolutePath());
			restorePanel.setRestorePath(chooser.getSelectedFile().getAbsolutePath());
			restorePanel.setStatus(StatusConstants.PREPARING);
			restore();
		}
	}
	
	public void selectBackupPath()
	{
		BackupPanel backupPanel = mainMenu.getContentPanel().getToolsPanel().getBackupPanel();
		FileNameExtensionFilter backupFilter = new FileNameExtensionFilter("BCB file", "bcb");
		JFileChooser chooser = new JFileChooser();
		chooser.setSelectedFile(FileHelper.createBackupFile());
		chooser.setFileFilter(backupFilter);
		int returnVal = chooser.showDialog(mainMenu.getContentPanel(), "Create backup");
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			backupPanel.setBackupPath(chooser.getSelectedFile().getAbsolutePath());
			backupPanel.setStatus(StatusConstants.PREPARING);
			backup();
		}
	}
	
	public void selectExportPath()
	{
		ExportPanel exportPanel = mainMenu.getContentPanel().getToolsPanel().getExportPanel();
		FileNameExtensionFilter exportFilter = new FileNameExtensionFilter("CSV file", "csv");
		JFileChooser patientchooser = new JFileChooser();
		patientchooser.setSelectedFile(FileHelper.createPatientExportFile());
		patientchooser.setFileFilter(exportFilter);
		int returnVal = patientchooser.showDialog(mainMenu.getContentPanel(), "Export patient");
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			exportPanel.setExportPatientPath(patientchooser.getSelectedFile().getAbsolutePath());
			JFileChooser recordchooser = new JFileChooser();
			recordchooser.setSelectedFile(FileHelper.createRecordExportFile());
			recordchooser.setFileFilter(exportFilter);
			returnVal = recordchooser.showDialog(mainMenu.getContentPanel(), "Export record");
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				exportPanel.setExportRecordPath(recordchooser.getSelectedFile().getAbsolutePath());
				JFileChooser diagnosischooser = new JFileChooser();
				diagnosischooser.setSelectedFile(FileHelper.createDiagnosisExportFile());
				diagnosischooser.setFileFilter(exportFilter);
				returnVal = diagnosischooser.showDialog(mainMenu.getContentPanel(), "Export diagnosis");
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					exportPanel.setExportDiagnosisPath(diagnosischooser.getSelectedFile().getAbsolutePath());
					exportPanel.setStatus(StatusConstants.PREPARING);
					export();
				}
			}
		}
	}
	
	public void backup()
	{
		final BackupPanel backupPanel = mainMenu.getContentPanel().getToolsPanel().getBackupPanel();
		String backupPath = backupPanel.getBackupPath();
		final BackupWorker backupWorker = new BackupWorker(new File(backupPath), backupPanel);
		backupWorker.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("DONE"))
				{
					try {
						backupWorker.get();
						backupPanel.setStatus(StatusConstants.SUCCESS);
					} catch (InterruptedException | ExecutionException e) 
					{
						backupPanel.setStatus(StatusConstants.FAILED);
						e.printStackTrace();
					}
				}
				
			}
			
		});
		backupWorker.execute();
		backupPanel.setStatus(StatusConstants.ONGOING);
	}
	
	public void export()
	{
		final ExportPanel exportPanel = mainMenu.getContentPanel().getToolsPanel().getExportPanel();
		String patientPath = exportPanel.getExportPatientPath();
		String recordPath = exportPanel.getExportRecordPath();
		String diagnosisPath = exportPanel.getExportDiagnosisPath();
		final ExportWorker exportWorker = new ExportWorker(new File(patientPath), new File(recordPath),
				new File(diagnosisPath), exportPanel);
		exportWorker.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("DONE"))
				{
					try {
						exportWorker.get();
						exportPanel.setStatus(StatusConstants.SUCCESS);
					} catch (InterruptedException | ExecutionException e) 
					{
						exportPanel.setStatus(StatusConstants.FAILED);
						e.printStackTrace();
					}
				}
				
			}
			
		});
		exportWorker.execute();
		exportPanel.setStatus(StatusConstants.ONGOING);
	}
	
	public void restore()
	{
		final RestorePanel restorePanel = mainMenu.getContentPanel().getToolsPanel().getRestorePanel();
		String restorePath = restorePanel.getRestorePath();
		restorePanel.getProgressBar().setIndeterminate(true);
		final RestoreWorker restoreWorker = new RestoreWorker(new File(restorePath), patientDao, recordDao, diagnosisDao, restorePanel);
		restoreWorker.addPropertyChangeListener(new PropertyChangeListener()
		{

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("DONE"))
				{
					try {
						restoreWorker.get();
						restorePanel.setStatus(StatusConstants.SUCCESS);
					} catch (InterruptedException | ExecutionException e) 
					{
						restorePanel.setStatus(StatusConstants.FAILED);
						e.printStackTrace();
					}
				}
			}
		});
		restoreWorker.execute();
		restorePanel.setStatus(StatusConstants.ONGOING);
	}
	
	public List<ListItem> getList(String view)
	{
		switch(view)
		{
		case TitleConstants.HISTOPATHOLOGY: return histopathologyList;
		case TitleConstants.GYNECOLOGY: return gynecologyList;
		case TitleConstants.CYTOLOGY: return cytologyList;
		case TitleConstants.PATIENTS: return patientList;
		case TitleConstants.SEARCH_RESULT: return searchList;
		}
		return null;
	}
	
	public void changeToolsView(String view)
	{
		mainMenu.getContentPanel().getToolsPanel().showPanel(view);
	}
}
