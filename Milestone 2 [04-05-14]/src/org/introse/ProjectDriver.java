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
import org.introse.Constants.DictionaryConstants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.StatusConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.CytologyRecord;
import org.introse.core.Diagnosis;
import org.introse.core.Dictionary;
import org.introse.core.GynecologyRecord;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Patient;
import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.core.dao.MysqlDiagnosisDao;
import org.introse.core.dao.MysqlDictionaryDao;
import org.introse.core.dao.MysqlPatientDao;
import org.introse.core.dao.MysqlRecordDao;
import org.introse.core.database.FileHelper;
import org.introse.core.database.Printer;
import org.introse.core.network.Client;
import org.introse.core.workers.BackupWorker;
import org.introse.core.workers.DictionaryListGenerator;
import org.introse.core.workers.ExportWorker;
import org.introse.core.workers.PatientListGenerator;
import org.introse.core.workers.RecordListGenerator;
import org.introse.core.workers.RestoreWorker;
import org.introse.gui.dialogbox.PatientLoader;
import org.introse.gui.dialogbox.PopupDialog;
import org.introse.gui.dialogbox.SearchDialog;
import org.introse.gui.dialogbox.SearchPatientDialog;
import org.introse.gui.dialogbox.SearchRecordDialog;
import org.introse.gui.event.CustomListener;
import org.introse.gui.event.ListListener;
import org.introse.gui.form.CytologyForm;
import org.introse.gui.form.Form;
import org.introse.gui.form.GynecologyForm;
import org.introse.gui.form.HistopathologyForm;
import org.introse.gui.form.PatientForm;
import org.introse.gui.form.RecordForm;
import org.introse.gui.panel.BackupPanel;
import org.introse.gui.panel.DetailPanel;
import org.introse.gui.panel.DictionaryPanel;
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
	private MainMenu mainMenu;
	private MysqlRecordDao recordDao;
	private MysqlPatientDao patientDao;
	private MysqlDiagnosisDao diagnosisDao;
	private MysqlDictionaryDao dictionaryDao;
	private DetailPanel detailPanel;
	private SearchDialog searchDialog;
	private PatientLoader loader;
	private Object lastSearch;
	private SwingWorker<Integer, Void> loginWorker;
	private static final ProjectDriver driver = new ProjectDriver();
	private static final CustomListener listener =  new CustomListener(driver);
	private static final ListListener listListener = new ListListener(driver);
	private Printer printer;
	
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
		if(loginWorker == null || loginWorker.isDone())
		{
			loginForm.setLoadingVisible(true);
			loginForm.setLoginButtonEnabled(false);
			loginWorker = new SwingWorker<Integer, Void>()
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
							loginForm.setListening(2);
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
									PopupDialog dialog = new PopupDialog(loginForm, "Login Failed", 
											"You entered an invalid password.", "OK");
									dialog.addPropertyChangeListener(new PropertyChangeListener(){

										@Override
										public void propertyChange(PropertyChangeEvent evt) 
										{loginForm.setListening(1);}
									});
									dialog.showGui();
									break;
								case Constants.NetworkConstants.SERVER_ERROR:
									PopupDialog dialog2 = new PopupDialog(loginForm, "Server Error", 
											"An error occured while trying to connect to the server.", "OK");
									dialog2.addPropertyChangeListener(new PropertyChangeListener(){

										@Override
										public void propertyChange(PropertyChangeEvent evt) 
										{loginForm.setListening(1);}
									});
									dialog2.showGui();
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
		dictionaryDao = new MysqlDictionaryDao();
		printer = new Printer();
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mainMenu = new MainMenu();
				mainMenu.addListListener(listListener);
				mainMenu.addListener(listener);
				changeView(TitleConstants.HISTOPATHOLOGY);
				mainMenu.showGUI();
				loadWords();
			}});
	}
	
	public void loadWords()
	{
		ListPanel path = mainMenu.getContentPanel().getPanel(TitleConstants.PATHOLOGISTS);
		ListPanel phys = mainMenu.getContentPanel().getPanel(TitleConstants.PHYSICIANS);
		ListPanel spec = mainMenu.getContentPanel().getPanel(TitleConstants.SPECIMENS);
		Dictionary.setPathologists(dictionaryDao.getWords(DictionaryConstants.PATHOLOGIST, path.getStart(),
				path.getRange()));
		Dictionary.setPhysicians(dictionaryDao.getWords(DictionaryConstants.PHYSICIAN, phys.getStart(),
				phys.getRange()));
		Dictionary.setSpecimens(dictionaryDao.getWords(DictionaryConstants.SPECIMEN, spec.getStart(), 
				spec.getRange()));
	}
	
	public void changeView(String view)
	{
		refresh(view, false);
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
	
	public void refresh(String view, boolean reset)
	{
		Record record = null;
		switch(view)
		{
					case Constants.TitleConstants.HISTOPATHOLOGY: 
						record = new HistopathologyRecord();
						record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.HISTOPATHOLOGY_RECORD);
						updateRList(record, view, reset);
						break;
					case Constants.TitleConstants.GYNECOLOGY:  
						record = new GynecologyRecord();
						record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.GYNECOLOGY_RECORD);
						updateRList(record, view, reset);
									  break;
					case Constants.TitleConstants.CYTOLOGY:  
						record = new CytologyRecord();
						record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.CYTOLOGY_RECORD);
						updateRList(record, view, reset);
									break;
					case Constants.TitleConstants.PATIENTS: 
						updatePList((Patient)null, view, reset);
										break;
					case Constants.TitleConstants.SEARCH_RESULT:
						if(lastSearch instanceof Record)
							updateRList((Record)lastSearch, view, reset);
						else updatePList((Patient)lastSearch, view, reset);
									  break;
					case TitleConstants.SPECIMENS: updateWList(DictionaryConstants.SPECIMEN, view);
					break;
					case TitleConstants.PATHOLOGISTS: updateWList(DictionaryConstants.PATHOLOGIST, view);
					break;
					case TitleConstants.PHYSICIANS: updateWList(DictionaryConstants.PHYSICIAN, view);
			}
	}
	
	public void removeDetailsPanel()
	{
		mainMenu.getContentPanel().setDetailsPanel(null);
		detailPanel = null;
	}
	
	public void updateWList(int type, final String view)
	{
		final ListPanel listPanel = mainMenu.getContentPanel().getPanel(view);
		System.out.println(view + " " + dictionaryDao.getCount(type));
		listPanel.setListSize(dictionaryDao.getCount(type));
		listPanel.showPanel(TitleConstants.REFRESH_PANEL);
		final DictionaryListGenerator listWorker = new DictionaryListGenerator(dictionaryDao, type, 
				listPanel.getStart(), listPanel.getRange());
		listWorker.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) 
			{
				if(evt.getPropertyName().equals("DONE"))
				{
					try {
						List<ListItem> words = listWorker.get();
						switch(view)
						{
						case TitleConstants.PATHOLOGISTS: Dictionary.setPathologists(listWorker.getWords());
						break;
						case TitleConstants.PHYSICIANS: Dictionary.setPhysicians(listWorker.getWords());
						break;
						case TitleConstants.SPECIMENS: Dictionary.setSpecimens(listWorker.getWords());
						}
						listPanel.updateViewable(words);
						if(words.size() > 0)
							listPanel.showPanel(TitleConstants.LIST_PANEL);
						else listPanel.showPanel(TitleConstants.EMPTY_PANEL);
					} catch (InterruptedException | ExecutionException e)
					{e.printStackTrace();}
					
				}
			}
		});
		listWorker.execute();
	}
	
	public void updateRList(Record record, String view, boolean reset)
	{
		final ListPanel listPanel = 
				mainMenu.getContentPanel().getPanel(view);
		if(reset)
			listPanel.setStart(0);
		listPanel.setListSize(recordDao.getCount(record));
		listPanel.showPanel(TitleConstants.REFRESH_PANEL);
		final RecordListGenerator listWorker = new RecordListGenerator(recordDao.search(record, listPanel.getStart(), 
				listPanel.getRange()), patientDao);
		listWorker.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt) 
			{
				if(evt.getPropertyName().equals("DONE"))
				{
					try 
					{
						List<ListItem> list = listWorker.get();
						listPanel.updateViewable(list);
						if(list.size() < 1)
							listPanel.showPanel(TitleConstants.EMPTY_PANEL);
						else listPanel.showPanel(TitleConstants.LIST_PANEL);
					} catch (InterruptedException | ExecutionException e) 
					{e.printStackTrace();}
				}
			}
		});
		listWorker.execute();
	}
	
	public void updatePList(Patient p, final String view, boolean reset)
	{
		List<Patient> list;
		final ListPanel listPanel = 
				mainMenu.getContentPanel().getPanel(view);
		if(reset)
			listPanel.setStart(0);
		if(p == null)
		{
			listPanel.setListSize(patientDao.getCount());
			list = patientDao.getAll(listPanel.getStart(), listPanel.getRange());
		}
		else
		{
			listPanel.setListSize(patientDao.getCount(p));
			list = patientDao.search(p, listPanel.getStart(), listPanel.getRange());
		}
		
		listPanel.showPanel(TitleConstants.REFRESH_PANEL);
		

		final PatientListGenerator listWorker = new PatientListGenerator(list);
		listWorker.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt) 
			{
				if(evt.getPropertyName().equals("DONE"))
				{
					try 
					{
						
							List<ListItem> list = listWorker.get();
							listPanel.updateViewable(list);
							if(list.size() < 1)
								listPanel.showPanel(TitleConstants.EMPTY_PANEL);
							else listPanel.showPanel(TitleConstants.LIST_PANEL);
					} catch (InterruptedException | ExecutionException e) 
					{e.printStackTrace();}
				}
			}
		});
		listWorker.execute();
	}
	
	public void viewItem(ListItem listItem)
	{
		Object object = listItem;
		if(object != null)
		{
			if(listItem instanceof Patient)
			{
				if(detailPanel != null && detailPanel instanceof RecordPanel && detailPanel.getMode() == 
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
			
			detailPanel = new RecordPanel((JPanel)recordForm, Constants.ActionConstants.VIEW);
			detailPanel.addListener(listener);
		}
		
		else if(object instanceof Patient)
		{
			Patient patient = (Patient)object;
			PatientForm form = new PatientForm();
			form.setFields(patient);
			detailPanel = new PatientPanel(form, Constants.ActionConstants.VIEW);
			detailPanel.addListener(listener);
		}
		mainMenu.getContentPanel().setDetailsPanel(detailPanel);
		mainMenu.getContentPanel().changeView(TitleConstants.DETAIL_PANEL);
	}
	
	public void editCurrentForm()
	{
		detailPanel.setMode(Constants.ActionConstants.EDIT);
	}
	
	public void saveCurrentForm()
	{
		if(detailPanel.areFieldsValid())
		{
			if(detailPanel instanceof RecordPanel)
			{
				Record r = ((RecordPanel)detailPanel).getRecordForm().getRecord();
				Patient p = ((RecordPanel)detailPanel).getRecordForm().getPatient();
				DetailPanel rP = (DetailPanel)detailPanel;
				r.putAttribute(Constants.RecordTable.PATIENT_ID, 
						 p.getAttribute(PatientTable.PATIENT_ID));				
				switch(rP.getMode())
				{
				case Constants.ActionConstants.NEW: int recordNumber = recordDao.generateRecordNumber(r);
													System.out.println(recordNumber);
													if(recordNumber == -1 || recordNumber > 9999)
													{
														PopupDialog dialog = new PopupDialog(mainMenu, 
																"Error Saving record", "Record number full", "OK");
														dialog.showGui();
														return;
													}
													else
													{	
														 r.putAttribute(RecordTable.RECORD_NUMBER, recordNumber);
														 if(patientDao.get(p) == null)
														 	patientDao.add(p);
														 recordDao.add(r);
													}
													 break;
				case Constants.ActionConstants.EDIT: recordDao.update(r);
													 break;
				}
				diagnosisDao.delete(r);
				List<Diagnosis> diagnosis = (List<Diagnosis>)r.getAttribute(RecordTable.DIAGNOSIS);
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
				((RecordPanel)detailPanel).getRecordForm().setFields(r, p);
			}
			else if(detailPanel instanceof PatientPanel)
			{
				Patient p = (Patient)detailPanel.getObject();
				DetailPanel rP = (DetailPanel)detailPanel;
				switch(rP.getMode())
				{
				case ActionConstants.NEW:	patientDao.add(p);
											break;
				case ActionConstants.EDIT:	patientDao.update(p);
				}
			}
			detailPanel.setMode(Constants.ActionConstants.VIEW);
		}
		else new PopupDialog(mainMenu, "Save Error", "Please fill up all required fields.", "OK").showGui();
	}
	
	public void cancelCurrentForm()
	{
		if(detailPanel.getMode() == Constants.ActionConstants.EDIT)
		{
			System.out.println("Hello");
			if(detailPanel instanceof RecordPanel)
			{
				Record record = (Record)detailPanel.getObject();
				record = (Record)recordDao.get(record);
				List<Diagnosis> diagnosis = diagnosisDao.getDiagnosis(record);
				record.putAttribute(RecordTable.DIAGNOSIS, diagnosis);
				Patient patient = new Patient();
				patient.putAttribute(PatientTable.PATIENT_ID, record.getAttribute(RecordTable.PATIENT_ID));
				patient = (Patient)patientDao.get(patient);
				((RecordPanel)detailPanel).getRecordForm().setFields(record, patient);
			}
			else if(detailPanel instanceof PatientPanel)
			{
				((PatientPanel)detailPanel).getPatientForm().setFields((Patient)patientDao.get((Patient)detailPanel.getObject()));
			}
			detailPanel.setMode(Constants.ActionConstants.VIEW);
		}
		else 
		{
			removeDetailsPanel();
			changeView(getPreviousView());
		}
	}
	
	public void printCurrentForm()
	{
		Record r = ((RecordPanel)detailPanel).getRecordForm().getRecord();
		List<Diagnosis> diagnosis = diagnosisDao.getDiagnosis(r);
		r.putAttribute(RecordTable.DIAGNOSIS, diagnosis);
		printer.startPrint(r);
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
											patient.putAttribute(PatientTable.PATIENT_ID, patientDao.getCount() + 1);
											recordForm = new HistopathologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.GYNECOLOGY_RECORD:		
											record = new GynecologyRecord();
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientDao.getCount() + 1);
											recordForm = new GynecologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.CYTOLOGY_RECORD:			
											record = new CytologyRecord();
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientDao.getCount() + 1);
											recordForm = new CytologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.PATIENT:			
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID, patientDao.getCount() + 1);
											patientForm = new PatientForm();
											((Form)patientForm).setFields(patient);
											panel = new PatientPanel(patientForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
		}
		this.detailPanel = (DetailPanel)panel;
		mainMenu.getContentPanel().setDetailsPanel(panel);
		mainMenu.getContentPanel().changeView(TitleConstants.DETAIL_PANEL);
	}
	
	public void loadExistingPatient(Object patient)
	{
		((RecordForm)((RecordPanel)detailPanel).getRecordForm()).setPatient((Patient)patient);
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
		}
		else
		{
			Patient patient = (Patient)searchDialog.getSearchCriteria();
			lastSearch = patient;
		}
		searchDialog.dispose();
		searchDialog = null;
		refresh(TitleConstants.SEARCH_RESULT, true);
		mainMenu.getContentPanel().changeView(TitleConstants.SEARCH_RESULT);
		removeDetailsPanel();
		setSelectedButton("");
	}
	
	public void openPatientLoad()
	{
		loader = new PatientLoader(patientDao, listListener);
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
		JFileChooser exportFileChooser = new JFileChooser();
		exportFileChooser.setSelectedFile(FileHelper.createExportFile());
		exportFileChooser.setFileFilter(exportFilter);
		int returnVal = exportFileChooser.showDialog(mainMenu.getContentPanel(), "Export patient");
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			exportPanel.setExportPath(exportFileChooser.getSelectedFile().getAbsolutePath());
			exportPanel.setStatus(StatusConstants.PREPARING);
			export();
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
		String exportPath = exportPanel.getExportPath();
		final ExportWorker exportWorker = new ExportWorker(new File(exportPath), exportPanel);
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
	
	public void changeToolsView(String view)
	{
		mainMenu.getContentPanel().getToolsPanel().showPanel(view);
	}
	
	public MainMenu getMainMenu()
	{
		return mainMenu;
	}
	
	public int getDetailPanelStatus()
	{
		if(detailPanel == null)
			return -1;
		else return detailPanel.getMode();
	}
	
	public void loadNext()
	{
		ListPanel listpanel = mainMenu.getContentPanel().getPanel(getCurrentView());
		listpanel.next();
		refresh(getCurrentView(), false);
	}
	
	public void loadPrevious()
	{
		ListPanel listpanel = mainMenu.getContentPanel().getPanel(getCurrentView());
		listpanel.previous();
		refresh(getCurrentView(), false);
	}
	
	public void addCurrentWord()
	{
		DictionaryPanel panel = mainMenu.getContentPanel().getDickPanel(getCurrentView());
		String word = panel.getWord();
		
		if(!word.equals(TitleConstants.DICTIONARY_HINT))
		{
			int type = 0;
			switch(getCurrentView())
			{
			case TitleConstants.PATHOLOGISTS: type = DictionaryConstants.PATHOLOGIST;
			break;
			case TitleConstants.PHYSICIANS: type = DictionaryConstants.PHYSICIAN;
			break;
			case TitleConstants.SPECIMENS: type = DictionaryConstants.SPECIMEN;
			}
			if(word.replaceAll("\\s", "").length() > 0)
			{
				if(dictionaryDao.isUnique(word, type))
				{
					dictionaryDao.add(word, type);
					panel.reset();
					changeView(getCurrentView());
				}
				else new PopupDialog(mainMenu, "Error saving word", word + " already exists", "OK").showGui();
			}
		}
	}
}
