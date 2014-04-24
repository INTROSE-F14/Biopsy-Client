package org.introse;



import java.awt.HeadlessException;
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
import org.introse.core.Diagnosis;
import org.introse.core.Dictionary;
import org.introse.core.DictionaryWord;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.core.dao.MysqlDiagnosisDao;
import org.introse.core.dao.MysqlDictionaryDao;
import org.introse.core.dao.MysqlPatientDao;
import org.introse.core.dao.MysqlRecordDao;
import org.introse.core.database.FileHelper;
import org.introse.core.network.Client;
import org.introse.core.workers.BackupWorker;
import org.introse.core.workers.DiagnosisRetrieveWorker;
import org.introse.core.workers.DiagnosisUpdateWorker;
import org.introse.core.workers.DictionaryListGenerator;
import org.introse.core.workers.DictionaryRetrieveWorker;
import org.introse.core.workers.DictionaryUpdateWorker;
import org.introse.core.workers.ExportWorker;
import org.introse.core.workers.LoginWorker;
import org.introse.core.workers.PatientListGenerator;
import org.introse.core.workers.PatientRetrieveWorker;
import org.introse.core.workers.PatientUpdateWorker;
import org.introse.core.workers.RecordListGenerator;
import org.introse.core.workers.RecordRetrieveWorker;
import org.introse.core.workers.RecordUpdateWorker;
import org.introse.core.workers.RestoreWorker;
import org.introse.gui.dialogbox.LoadingDialog;
import org.introse.gui.dialogbox.PatientLoader;
import org.introse.gui.dialogbox.PopupDialog;
import org.introse.gui.dialogbox.PrintDialog;
import org.introse.gui.dialogbox.SearchDialog;
import org.introse.gui.dialogbox.SearchPatientDialog;
import org.introse.gui.dialogbox.SearchRecordDialog;
import org.introse.gui.event.CustomListener;
import org.introse.gui.event.ListListener;
import org.introse.gui.event.NavigationListener;
import org.introse.gui.event.TabListener;
import org.introse.gui.form.CytologyForm;
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
	private static LoginWindow loginWindow;
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
	private static final TabListener tabListener = new TabListener(driver);
	private static final NavigationListener navListener = new NavigationListener(driver);
	
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
		loginWindow = new LoginWindow();
		loginWindow.addListener(listener);
		loginWindow.showGUI();
	}
	
	public void login()
	{
		final LoadingDialog loadingDialog = new LoadingDialog(loginWindow, "Loading", "Logging in");
		if(loginWorker == null || loginWorker.isDone())
		{
			loginWindow.setLoginButtonEnabled(false);
			loginWorker = new LoginWorker(loginWindow, driver, client);
			loginWorker.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals("ALMOST DONE"))
						loadingDialog.dispose();
				}
			});
			loginWorker.execute();
			loadingDialog.showGui();
		}
	}
	
	public void startMainMenu()
	{
		loginWindow = null;
		recordDao = new MysqlRecordDao();
		patientDao = new MysqlPatientDao();
		diagnosisDao = new MysqlDiagnosisDao();
		dictionaryDao = new MysqlDictionaryDao();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mainMenu = new MainMenu();
				mainMenu.addListListener(listListener);
				mainMenu.addListener(listener);
				mainMenu.addNavigationListener(navListener);
				mainMenu.addTabListener(tabListener);
				loadWords();
				changeView(TitleConstants.HISTOPATHOLOGY);
				mainMenu.showGUI();
			}});
	}
	
	public void loadWords()
	{
		ListPanel path = mainMenu.getContentPanel().getPanel(TitleConstants.PATHOLOGISTS);
		ListPanel phys = mainMenu.getContentPanel().getPanel(TitleConstants.PHYSICIANS);
		ListPanel spec = mainMenu.getContentPanel().getPanel(TitleConstants.SPECIMENS);
		final DictionaryRetrieveWorker pathologistWorker = new DictionaryRetrieveWorker
				(dictionaryDao, path.getStart(), path.getRange(), DictionaryConstants.PATHOLOGIST);
		pathologistWorker.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) 
			{
				try 
				{
					if(evt.getPropertyName().equals("DONE"))
						Dictionary.setPathologists((List<String>)pathologistWorker.get());
				} catch (InterruptedException | ExecutionException e) 
				{e.printStackTrace();}
			}
		});
		pathologistWorker.execute();
		
		final DictionaryRetrieveWorker physicianWorker = new DictionaryRetrieveWorker
				(dictionaryDao, phys.getStart(), phys.getRange(), DictionaryConstants.PHYSICIAN);
		physicianWorker.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) 
			{
				try 
				{
					if(evt.getPropertyName().equals("DONE"))
						Dictionary.setPhysicians((List<String>)physicianWorker.get());
				} catch (InterruptedException | ExecutionException e) 
				{e.printStackTrace();}
			}
		});
		physicianWorker.execute();
		
		final DictionaryRetrieveWorker specimenWorker = new DictionaryRetrieveWorker
				(dictionaryDao, spec.getStart(), spec.getRange(), DictionaryConstants.SPECIMEN);
		specimenWorker.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) 
			{
				try 
				{
					if(evt.getPropertyName().equals("DONE"))
						Dictionary.setSpecimens((List<String>)specimenWorker.get());
				} catch (InterruptedException | ExecutionException e) 
				{e.printStackTrace();}
			}
		});
		specimenWorker.execute();
	}
	
	public void changeView(String view)
	{
		refresh(view, false);
		mainMenu.getContentPanel().changeView(view);
	}
	
	public void setCountLabel(String view, int count)
	{
		mainMenu.getContentPanel().setCountLabel(view, count);
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
		Record record = new Record();
		switch(view)
		{
					case Constants.TitleConstants.HISTOPATHOLOGY: 
						record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.HISTOPATHOLOGY_RECORD);
						updateRList(record, view, reset);
						break;
					case Constants.TitleConstants.GYNECOLOGY:  
						record.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.GYNECOLOGY_RECORD);
						updateRList(record, view, reset);
									  break;
					case Constants.TitleConstants.CYTOLOGY:  
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
					case TitleConstants.DICTIONARY: 
						DictionaryPanel panel = mainMenu.getContentPanel().getDickPanel();
						String currentView = panel.getCurrentView();
						if(currentView.equals(TitleConstants.PATHOLOGISTS))
								updateWList(DictionaryConstants.PATHOLOGIST, currentView);
						else if(currentView.equals(TitleConstants.PHYSICIANS))
							updateWList(DictionaryConstants.PHYSICIAN, currentView);
						else updateWList(DictionaryConstants.SPECIMEN, currentView);
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
	
	public void updateWList(final int type, final String view)
	{
		final ListPanel listPanel = mainMenu.getContentPanel().getPanel(view);
		listPanel.showPanel(TitleConstants.REFRESH_PANEL);
		
		final DictionaryRetrieveWorker countGetter = new DictionaryRetrieveWorker(dictionaryDao, type);
		countGetter.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("DONE"))
				{
					try 
					{
						listPanel.setListSize((int)countGetter.get());
						setCountLabel(view, listPanel.getListSize());
						final DictionaryListGenerator listWorker = new DictionaryListGenerator(dictionaryDao, type, 
								listPanel.getStart(), listPanel.getRange());
						listWorker.addPropertyChangeListener(new PropertyChangeListener() {
							
							@Override
							public void propertyChange(PropertyChangeEvent evt) 
							{
								if(evt.getPropertyName().equals("DONE"))
								{
									try {
										loadWords();
										List<ListItem> words = listWorker.get();
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
					} catch (InterruptedException | ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		countGetter.execute();
	}
	
	public void updateRList(final Record record, final String view, boolean reset)
	{
		final ListPanel listPanel = 
				mainMenu.getContentPanel().getPanel(view);
		listPanel.showPanel(TitleConstants.REFRESH_PANEL);
		
		if(reset)
			listPanel.setStart(0);
		
		final RecordRetrieveWorker countGetter = new RecordRetrieveWorker(recordDao, record, RecordRetrieveWorker.GET_COUNT);
		countGetter.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("DONE"))
				{
					try 
					{
						listPanel.setListSize((int)countGetter.get());
						setCountLabel(view, listPanel.getListSize());
						final RecordRetrieveWorker recordSearcher = new RecordRetrieveWorker(recordDao, 
								record, listPanel.getStart(), listPanel.getRange(), RecordRetrieveWorker.SEARCH);
						recordSearcher.addPropertyChangeListener(new PropertyChangeListener() {
							
							@Override
							public void propertyChange(PropertyChangeEvent evt) {
								if(evt.getPropertyName().equals("DONE"))
								{
									List<Record> records;
									try 
									{
										records = (List<Record>)recordSearcher.get();
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
									} catch (InterruptedException | ExecutionException e1) 
									{e1.printStackTrace();}
								}
							}
						});
						recordSearcher.execute();
					} catch (InterruptedException | ExecutionException e2) 
					{e2.printStackTrace();}
				}	
			}
		});
		countGetter.execute();
	}
	
	public void updatePList(final Patient p, final String view, boolean reset)
	{
		final ListPanel listPanel = 
				mainMenu.getContentPanel().getPanel(view);
		listPanel.showPanel(TitleConstants.REFRESH_PANEL);
		if(reset)
			listPanel.setStart(0);
		final PatientRetrieveWorker countGetter = new PatientRetrieveWorker(patientDao, p);
		countGetter.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) 
			{
				if(evt.getPropertyName().equals("DONE"))
				{
					try {
						listPanel.setListSize((int)countGetter.get());
						setCountLabel(view, listPanel.getListSize());
	
						final PatientRetrieveWorker worker = new 
								PatientRetrieveWorker(patientDao, p, listPanel.getStart(), 
										listPanel.getRange());
						worker.addPropertyChangeListener(new PropertyChangeListener() 
						{
							@Override
							public void propertyChange(PropertyChangeEvent evt) 
							{
								if(evt.getPropertyName().equals("DONE"))
								{
									try 
									{
										List<Patient> list = (List<Patient>)worker.get();
										final PatientListGenerator listWorker = new PatientListGenerator(list, true);
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
									} catch (InterruptedException | ExecutionException e) 
									{e.printStackTrace();}
								}
							}
						});
						worker.execute();
					} catch (InterruptedException | ExecutionException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		countGetter.execute();
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
						loadExistingPatient((Patient)object);
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
	
	public void showDetails(final Object object)
	{
		if(object instanceof Record)
		{
			final LoadingDialog loadingDialog = new LoadingDialog(mainMenu, "Loading", "Loading record");
			final Record record = (Record)object;
			Patient patient = new Patient();
			patient.putAttribute(PatientTable.PATIENT_ID, 
					record.getAttribute(Constants.RecordTable.PATIENT_ID));
			
			final PatientRetrieveWorker patientFinder = new PatientRetrieveWorker(patientDao, patient, 0, 0);
			patientFinder.addPropertyChangeListener(new PropertyChangeListener() 
			{	
				@Override
				public void propertyChange(PropertyChangeEvent evt) 
				{
					try 
					{
						final Patient p = (Patient)patientFinder.get();
						final DiagnosisRetrieveWorker diagnosisRetriever = new DiagnosisRetrieveWorker(diagnosisDao, record);
						diagnosisRetriever.addPropertyChangeListener(new PropertyChangeListener() {
							
							@Override
							public void propertyChange(PropertyChangeEvent evt) {
								if(evt.getPropertyName().equals("DONE"))
								{
									RecordForm recordForm = null;
									List<Diagnosis> diagnosis;
									try {
										diagnosis = (List<Diagnosis>)diagnosisRetriever.get();
										record.putAttribute(RecordTable.DIAGNOSIS, diagnosis);
										record.putAttribute(RecordTable.PATIENT, p);
										
										char recordType = (char)record.getAttribute(RecordTable.RECORD_TYPE);
										switch(recordType)
										{
										case RecordConstants.HISTOPATHOLOGY_RECORD: recordForm = new HistopathologyForm();
										break;
										case RecordConstants.GYNECOLOGY_RECORD: recordForm = new GynecologyForm();
										break;
										case RecordConstants.CYTOLOGY_RECORD: recordForm = new CytologyForm();										
										}
										recordForm.setFields(record, p);
										detailPanel = new RecordPanel((JPanel)recordForm, Constants.ActionConstants.VIEW);
										detailPanel.addListener(listener);
										mainMenu.getContentPanel().setDetailsPanel(detailPanel);
										loadingDialog.dispose();
										mainMenu.getContentPanel().changeView(TitleConstants.DETAIL_PANEL);
									} catch (InterruptedException
											| ExecutionException e) 
									{e.printStackTrace();}
								}
							}
						});
						diagnosisRetriever.execute();
					
					} catch (InterruptedException | ExecutionException e) 
					{e.printStackTrace();}
					
				}
			});
			patientFinder.execute();
			loadingDialog.showGui();
		}
		
		else if(object instanceof Patient)
		{
			Patient patient = (Patient)object;
			PatientForm form = new PatientForm();
			form.setFields(patient);
			detailPanel = new PatientPanel(form, Constants.ActionConstants.VIEW);
			detailPanel.addListener(listener);
			mainMenu.getContentPanel().setDetailsPanel(detailPanel);
			mainMenu.getContentPanel().changeView(TitleConstants.DETAIL_PANEL);
		}
		
	}
	
	public void editCurrentForm()
	{
		detailPanel.setMode(Constants.ActionConstants.EDIT);
	}
	
	public void saveCurrentForm()
	{
		if(detailPanel.areFieldsValid())
		{
			final LoadingDialog loadingDialog = new LoadingDialog(mainMenu, "Saving", "Saving, please wait");
			SwingWorker bigWorker = new SwingWorker()
			{
				@Override
				protected void done()
				{
					this.firePropertyChange("DONE", null, null);
				}
				@Override
				protected Object doInBackground() throws Exception 
				{
				if(detailPanel instanceof RecordPanel)
				{
					final Record r = ((RecordPanel)detailPanel).getRecordForm().getRecord();
					final Patient p = ((RecordPanel)detailPanel).getRecordForm().getPatient();
					DetailPanel rP = (DetailPanel)detailPanel;
					int patientID = (int)p.getAttribute(PatientTable.PATIENT_ID);
					System.out.println(patientID+"");
					if(patientID != -1)
						r.putAttribute(RecordTable.PATIENT_ID, patientID);
					else p.putAttribute(PatientTable.PATIENT_ID, null);
					
					final DiagnosisUpdateWorker diagnosisWorker = new DiagnosisUpdateWorker(diagnosisDao, r);
					diagnosisWorker.addPropertyChangeListener(new PropertyChangeListener() 
					{
						@Override
						public void propertyChange(PropertyChangeEvent evt) 
						{
							if(evt.getPropertyName().equals("DONE"))
							{
								List<Diagnosis> diagnosis = (List<Diagnosis>)r.getAttribute(RecordTable.DIAGNOSIS);
								if(diagnosis != null)
								{
									Iterator<Diagnosis> i = diagnosis.iterator();
									while(i.hasNext())
									{
										Diagnosis d = i.next();
										d.setReferenceNumber(((char)r.getAttribute(RecordTable.RECORD_TYPE)),
												(int)r.getAttribute(RecordTable.RECORD_YEAR), (int)r.getAttribute(RecordTable.RECORD_NUMBER));
									}
									final DiagnosisUpdateWorker addWorker = new DiagnosisUpdateWorker(diagnosisDao, diagnosis);
									addWorker.addPropertyChangeListener(new PropertyChangeListener()
									{	
										@Override
										public void propertyChange(PropertyChangeEvent evt) 
										{
											if(evt.getPropertyName().equals("DONE"))
											{
												((RecordPanel)detailPanel).getRecordForm().setFields(r, p);
											}	
										}
									});
									addWorker.execute();
								}
								else ((RecordPanel)detailPanel).getRecordForm().setFields(r, p);
							}
						}
					});
					
					switch(rP.getMode())
					{
					case Constants.ActionConstants.NEW:  if(patientID == -1)
														 {
															final PatientUpdateWorker patientAdder = new PatientUpdateWorker(patientDao, p, PatientUpdateWorker.ADD);
															patientAdder.addPropertyChangeListener(new PropertyChangeListener() 
															{
																@Override
																public void propertyChange(PropertyChangeEvent evt) {
																	if(evt.getPropertyName().equals("DONE"))
																	{
																		 try 
																		 {
																			int pID = patientAdder.get();
																			p.putAttribute(PatientTable.PATIENT_ID, pID);
																			r.putAttribute(RecordTable.PATIENT_ID, pID);
																			final RecordUpdateWorker recordWorker = new 
																					RecordUpdateWorker(recordDao, r, RecordUpdateWorker.ADD);
																			recordWorker.addPropertyChangeListener(new PropertyChangeListener() {
																				
																				@Override
																				public void propertyChange(PropertyChangeEvent evt) {
																					if(evt.getPropertyName().equals("DONE"))
																					{
																						try 
																						{
																							int recordNumber = recordWorker.get();
																							r.putAttribute(RecordTable.RECORD_NUMBER, recordNumber);
																							diagnosisWorker.execute();
																						} catch (
																								InterruptedException
																								| ExecutionException e) 
																						{e.printStackTrace();}
																					}
																				}
																			});
																			recordWorker.execute();
																		} catch (
																				InterruptedException
																				| ExecutionException e) 
																		{e.printStackTrace();}
																	}
																}
															});
															patientAdder.execute();
														 }
														 else 
														 {
															 final RecordUpdateWorker recordWorker = new 
																		RecordUpdateWorker(recordDao, r, RecordUpdateWorker.ADD);
															 recordWorker.addPropertyChangeListener(new PropertyChangeListener() {
																	
																	@Override
																	public void propertyChange(PropertyChangeEvent evt) {
																		if(evt.getPropertyName().equals("DONE"))
																		{
																			try 
																			{
																				int recordNumber = recordWorker.get();
																				r.putAttribute(RecordTable.RECORD_NUMBER, recordNumber);
																				diagnosisWorker.execute();
																			} catch (
																					InterruptedException
																					| ExecutionException e) 
																			{e.printStackTrace();}
																		}
																	}
																});
																recordWorker.execute();
														 }
														 break;
					case Constants.ActionConstants.EDIT: final RecordUpdateWorker recordWorker = new 
															RecordUpdateWorker(recordDao, r, RecordUpdateWorker.UPDATE);
															recordWorker.addPropertyChangeListener(new PropertyChangeListener() {
																
																@Override
																public void propertyChange(PropertyChangeEvent evt) {
																	if(evt.getPropertyName().equals("DONE"))
																	{
																		diagnosisWorker.execute();
																	}
																	
																}
															});
															recordWorker.execute();
														 break;
					}
				}
				else if(detailPanel instanceof PatientPanel)
				{
					PatientUpdateWorker worker = null;
					Patient p = (Patient)detailPanel.getObject();
					DetailPanel rP = (DetailPanel)detailPanel;
					switch(rP.getMode())
					{
					case ActionConstants.NEW:	p.putAttribute(PatientTable.PATIENT_ID, null);
												worker = new PatientUpdateWorker(patientDao, p, PatientUpdateWorker.ADD);
												break;
					case ActionConstants.EDIT:	worker = new PatientUpdateWorker(patientDao, p, PatientUpdateWorker.UPDATE);
					}
					if(worker != null)
						worker.execute();
				}
				return null;
				}
				
			};
			bigWorker.addPropertyChangeListener(new PropertyChangeListener() 
			{	
				@Override
				public void propertyChange(PropertyChangeEvent evt) 
				{
					if(evt.getPropertyName().equals("DONE"))
					{
						loadingDialog.dispose();
						detailPanel.setMode(Constants.ActionConstants.VIEW);
					}
				}
			});
			bigWorker.execute();
			loadingDialog.showGui();
			
		}
		else new PopupDialog(mainMenu, "Save Error", "Please fill up all required fields.", "OK").showGui();
	}
	
	public void cancelCurrentForm()
	{
		final LoadingDialog loadingDialog = new LoadingDialog(mainMenu, "Loading", "Loading, please wait");
		if(detailPanel.getMode() == Constants.ActionConstants.EDIT)
		{
			if(detailPanel instanceof RecordPanel)
			{
				Record record = (Record)detailPanel.getObject();
				final RecordRetrieveWorker recordGetter = new RecordRetrieveWorker(recordDao, 
						record, RecordRetrieveWorker.GET);
				recordGetter.addPropertyChangeListener(new PropertyChangeListener() 
				{	
					@Override
					public void propertyChange(PropertyChangeEvent evt) 
					{
						if(evt.getPropertyName().equals("DONE"))
						{
							try 
							{
								final Record actualRecord = (Record)recordGetter.get();
								final DiagnosisRetrieveWorker diagnosisWorker = new DiagnosisRetrieveWorker(diagnosisDao, actualRecord);
								diagnosisWorker.addPropertyChangeListener(new PropertyChangeListener() 
								{	
									@Override
									public void propertyChange(PropertyChangeEvent evt) {
										if(evt.getPropertyName().equals("DONE"))
										{
											List<Diagnosis> diagnosis;
											try 
											{
												diagnosis = (List<Diagnosis>)diagnosisWorker.get();
												actualRecord.putAttribute(RecordTable.DIAGNOSIS, diagnosis);
												Patient patient = new Patient();
												patient.putAttribute(PatientTable.PATIENT_ID, actualRecord.getAttribute(RecordTable.PATIENT_ID));
												
												final PatientRetrieveWorker patientWorker = new PatientRetrieveWorker(patientDao, patient, 0, 0);
												patientWorker.addPropertyChangeListener(new PropertyChangeListener()
												{	
													@Override
													public void propertyChange(PropertyChangeEvent evt) 
													{
														if(evt.getPropertyName().equals("DONE"))
														{
															try 
															{
																((RecordPanel)detailPanel).getRecordForm().setFields(actualRecord, (Patient)patientWorker.get());
																detailPanel.setMode(Constants.ActionConstants.VIEW);
																loadingDialog.dispose();
															} catch (InterruptedException | ExecutionException e) 
															{e.printStackTrace();}
														}
													}
												});
												patientWorker.execute();
											} catch (InterruptedException
													| ExecutionException e1) 
											{e1.printStackTrace();}
										}
									}
								});
								diagnosisWorker.execute();
							} catch (InterruptedException | ExecutionException e1) 
							{e1.printStackTrace();}
						}
					}
				});
				recordGetter.execute();
				loadingDialog.showGui();
			}
			else if(detailPanel instanceof PatientPanel)
			{
				final PatientRetrieveWorker patientWorker = new PatientRetrieveWorker(patientDao, (Patient)detailPanel.getObject(), 0, 0);
				patientWorker.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if(evt.getPropertyName().equals("DONE"))
						{
							try 
							{
								((PatientPanel)detailPanel).getPatientForm().setFields((Patient)patientWorker.get());
								detailPanel.setMode(Constants.ActionConstants.VIEW);
								loadingDialog.dispose();
							} catch (InterruptedException | ExecutionException e) 
							{e.printStackTrace();}
						}
						
					}
				});
				patientWorker.execute();
				loadingDialog.showGui();
			}
		}
		else 
		{
			removeDetailsPanel();
			changeView(getPreviousView());
		}
	}
	
	public void printCurrentForm()
	{
		final LoadingDialog loadingDialog = new LoadingDialog(mainMenu, "Loading", "Loading, please wait");
		final Record r = ((RecordPanel)detailPanel).getRecordForm().getRecord();
		final Patient p = ((RecordPanel)detailPanel).getRecordForm().getPatient();
		r.putAttribute(RecordTable.PATIENT, p);
		final DiagnosisRetrieveWorker diagnosisWorker = new DiagnosisRetrieveWorker(diagnosisDao, r);
		diagnosisWorker.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("DONE"))
				{
					List<Diagnosis> diagnosis;
					try 
					{
						diagnosis = (List<Diagnosis>)diagnosisWorker.get();
						r.putAttribute(RecordTable.DIAGNOSIS, diagnosis);
						loadingDialog.dispose();
						PrintDialog pd = new PrintDialog(r);
					} catch (InterruptedException | ExecutionException e) 
					{e.printStackTrace();}
				}
			}
		});
		diagnosisWorker.execute();
		loadingDialog.showGui();
	}

	public void createNew(int type)
	{
		JPanel panel = null;
		JPanel recordForm = null;
		PatientForm patientForm = null;
		Record record = new Record();
		Patient patient = new Patient();
		patient.putAttribute(PatientTable.PATIENT_ID, -1);
		switch(type)
		{
		case Constants.RecordConstants.HISTOPATHOLOGY_RECORD:	
											recordForm = new HistopathologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.GYNECOLOGY_RECORD:		
											recordForm = new GynecologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.CYTOLOGY_RECORD:			
											recordForm = new CytologyForm();
											((RecordForm)recordForm).setFields(record, patient);
											panel = new RecordPanel(recordForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case Constants.RecordConstants.PATIENT:			
											patientForm = new PatientForm();
											patientForm.setFields(patient);
											panel = new PatientPanel(patientForm, Constants.ActionConstants.NEW);
											((DetailPanel)panel).addListener(listener);
		}
		this.detailPanel = (DetailPanel)panel;
		mainMenu.getContentPanel().setDetailsPanel(panel);
		mainMenu.getContentPanel().changeView(TitleConstants.DETAIL_PANEL);
	}
	
	public void loadExistingPatient(Patient patient)
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
		if(detailPanel != null && detailPanel instanceof RecordPanel)
		{
			if(((RecordPanel)detailPanel).getRecordForm() instanceof GynecologyForm)
				loader = new PatientLoader(patientDao, PatientLoader.FEMALE);
			else loader = new PatientLoader(patientDao, PatientLoader.ANY);
		}
		else loader = new PatientLoader(patientDao, PatientLoader.ANY);
		loader.addListListener(listListener);
		loader.showGUI();
	}
	
	public void selectRestorePath()
	{
		RestorePanel restorePanel = mainMenu.getContentPanel().getToolsPanel().getRestorePanel();
		final JFileChooser chooser = new JFileChooser(FileHelper.getBackupDirectory());
		FileNameExtensionFilter backupFilter = new FileNameExtensionFilter("BCB file", "bcb");
		chooser.setFileFilter(backupFilter);
		chooser.setAcceptAllFileFilterUsed(false);
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
		final LoadingDialog loadingDialog = new LoadingDialog(mainMenu, "Loading", "Checking data to be backed up");
		final SwingWorker dataCountGetter = new SwingWorker<Integer, Void>()
		{
			@Override
			protected Integer doInBackground() throws Exception 
			{
				int records = recordDao.getCount();
				int patients = patientDao.getCount();
				int words = dictionaryDao.getCount();
				return records + patients + words;
			}
			
			@Override
			protected void done()
			{
				firePropertyChange("DONE", null, null);
			}
		};
		dataCountGetter.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("DONE"))
				{
					try 
					{
						loadingDialog.dispose();
						if((int)dataCountGetter.get() > 0)
						{
							BackupPanel backupPanel = mainMenu.getContentPanel().getToolsPanel().getBackupPanel();
							FileNameExtensionFilter backupFilter = new FileNameExtensionFilter("BCB file", "bcb");
							JFileChooser chooser = new JFileChooser();
							chooser.setSelectedFile(FileHelper.createBackupFile());
							chooser.setFileFilter(backupFilter);
							chooser.setAcceptAllFileFilterUsed(false);
							int returnVal = chooser.showDialog(mainMenu.getContentPanel(), "Create backup");
							if(returnVal == JFileChooser.APPROVE_OPTION)
							{
								String path = chooser.getSelectedFile().getAbsolutePath();
								if(!path.endsWith(".bcb"))
									path = path.concat(".bcb");
								backupPanel.setBackupPath(path);
								backupPanel.setStatus(StatusConstants.PREPARING);
								backup();
							}
						}
						else new PopupDialog(mainMenu, "Backup stopped", "There are no data to be backed up", "OK").showGui();
					} catch (HeadlessException | InterruptedException
							| ExecutionException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		dataCountGetter.execute();
		loadingDialog.showGui();
	}
	
	public void selectExportPath()
	{
		ExportPanel exportPanel = mainMenu.getContentPanel().getToolsPanel().getExportPanel();
		FileNameExtensionFilter exportFilter = new FileNameExtensionFilter("CSV file", "csv");
		JFileChooser exportFileChooser = new JFileChooser();
		exportFileChooser.setSelectedFile(FileHelper.createExportFile());
		exportFileChooser.setFileFilter(exportFilter);
		exportFileChooser.setAcceptAllFileFilterUsed(false);
		int returnVal = exportFileChooser.showDialog(mainMenu.getContentPanel(), "Export patient");
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			String exportPath = exportFileChooser.getSelectedFile().getAbsolutePath();
			if(!exportPath.endsWith(".csv"))
				exportPath = exportPath.concat(".csv");
			exportPanel.setExportPath(exportPath);
			exportPanel.setStatus(StatusConstants.PREPARING);
			export();
		}
	}
	
	public void backup()
	{
		final BackupPanel backupPanel = mainMenu.getContentPanel().getToolsPanel().getBackupPanel();
		String backupPath = backupPanel.getBackupPath();
		File backup = new File(backupPath);
		final BackupWorker backupWorker = new BackupWorker(diagnosisDao, 
				recordDao, patientDao, dictionaryDao, new File(backupPath), backupPanel);
		backupWorker.addPropertyChangeListener(new PropertyChangeListener()
		{
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
		if(backup.exists())
		{
			PopupDialog popup = new PopupDialog(mainMenu, "Confirm backup", 
					"This will overwrite existing file, do you want to continue?","Yes","No");
			popup.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) 
				{
					if(evt.getPropertyName().equals("POSITIVE"))
					{
						backupWorker.execute();
						backupPanel.setStatus(StatusConstants.ONGOING);
					}
					else backupPanel.setStatus(StatusConstants.DEFAULT);
				}
			});
			popup.showGui();
		}
		else
		{
			backupWorker.execute();
			backupPanel.setStatus(StatusConstants.ONGOING);
		}
	}
	
	public void export()
	{
		final ExportPanel exportPanel = mainMenu.getContentPanel().getToolsPanel().getExportPanel();
		String exportPath = exportPanel.getExportPath();
		File exportFile = new File(exportPath);
		final ExportWorker exportWorker = new ExportWorker(exportFile, exportPanel);
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
		if(exportFile.exists())
		{
			PopupDialog popup = new PopupDialog(mainMenu, "Confirm export",
					"This will overwrite existing file, do you want to continue?","Yes","No");
			popup.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals("POSITIVE"))
					{
						exportWorker.execute();
						exportPanel.setStatus(StatusConstants.ONGOING);
					}
					else exportPanel.setStatus(StatusConstants.DEFAULT);
				}
			});
			popup.showGui();
		}
		else
		{
			exportWorker.execute();
			exportPanel.setStatus(StatusConstants.ONGOING);
		}
	}
	
	public void restore()
	{
		final RestorePanel restorePanel = mainMenu.getContentPanel().getToolsPanel().getRestorePanel();
		String restorePath = restorePanel.getRestorePath();
		restorePanel.getProgressBar().setIndeterminate(true);
		final RestoreWorker restoreWorker = new RestoreWorker(new File(restorePath),
				patientDao, recordDao, diagnosisDao, dictionaryDao, restorePanel);
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
		final DictionaryPanel panel = mainMenu.getContentPanel().getDickPanel();
		final String word = panel.getWord();
		
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
			final int actualType = type;
			if(word.replaceAll("\\s", "").length() > 0)
			{
				final LoadingDialog loadingDialog = new LoadingDialog(mainMenu, "Loading", "Adding word");
				final DictionaryUpdateWorker wordChecker = new DictionaryUpdateWorker
						(dictionaryDao, word, type, DictionaryUpdateWorker.CHECK);
				wordChecker.addPropertyChangeListener(new PropertyChangeListener() 
				{
					@Override
					public void propertyChange(PropertyChangeEvent evt) 
					{
						if(evt.getPropertyName().equals("DONE"))
						{
							try 
							{
								loadingDialog.dispose();
								if((boolean)wordChecker.get())
								{
									DictionaryUpdateWorker wordAdder =
											new DictionaryUpdateWorker(dictionaryDao, word, actualType, DictionaryUpdateWorker.ADD);
									wordAdder.addPropertyChangeListener(new PropertyChangeListener() 
									{	
										@Override
										public void propertyChange(PropertyChangeEvent evt) {
											if(evt.getPropertyName().equals("DONE"))
											{
												panel.reset();
												changeView(getCurrentView());
											}
										}
									});
									wordAdder.execute();
								}
								else new PopupDialog(mainMenu, "Error saving word", word + " already exists", "OK").showGui();
							} catch (InterruptedException | ExecutionException e) 
							{e.printStackTrace();}
						}
					}
				});
				wordChecker.execute();
				loadingDialog.showGui();
			}
		}
	}
	
	public void delete(final Object object)
	{
		final LoadingDialog loadingDialog = new LoadingDialog(mainMenu, "Loading", "Deleting selected item, please wait");
		PopupDialog dialog = null;
		if(object instanceof Record)
		{
			final Record r = (Record) object;
			dialog = new PopupDialog(mainMenu, "Confirm delete",
					TitleConstants.CONFIRM_DELETE_RECORD, "Yes", "No");
			dialog.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals("POSITIVE"))
					{
						final DiagnosisUpdateWorker diagnosisWorker = new DiagnosisUpdateWorker(diagnosisDao, r);
						diagnosisWorker.addPropertyChangeListener(new PropertyChangeListener() {
							
							@Override
							public void propertyChange(PropertyChangeEvent evt) {
								if(evt.getPropertyName().equals("DONE"))
								{
									final RecordUpdateWorker recordDeleter = new 
											RecordUpdateWorker(recordDao, r, RecordUpdateWorker.DELETE);
									recordDeleter.addPropertyChangeListener(new PropertyChangeListener() 
									{	
										@Override
										public void propertyChange(PropertyChangeEvent evt) 
										{
											if(evt.getPropertyName().equals("DONE"))
											{
												loadingDialog.dispose();
												changeView(getCurrentView());
											}
										}
									});
									recordDeleter.execute();
								}
							}
						});
						diagnosisWorker.execute();
						loadingDialog.showGui();
					}
					
				}
			});
			dialog.showGui();
		}
		else if(object instanceof Patient)
		{
			final Patient p = (Patient)object;
			dialog= new PopupDialog(mainMenu, "Confirm delete", 
					TitleConstants.CONFIRM_DELETE_PATIENT,
					"Yes","No");
			dialog.addPropertyChangeListener(new PropertyChangeListener() 
			{	
				@Override
				public void propertyChange(PropertyChangeEvent evt) 
				{
					if(evt.getPropertyName().equals("POSITIVE"))
					{
						final Record r = new Record();
						final int patientId =  (int)p.getAttribute(PatientTable.PATIENT_ID);
						r.putAttribute(RecordTable.PATIENT_ID,patientId);
						final RecordRetrieveWorker countGetter = new RecordRetrieveWorker(recordDao, r, RecordRetrieveWorker.GET_COUNT);
						countGetter.addPropertyChangeListener(new PropertyChangeListener() 
						{	
							@Override
							public void propertyChange(PropertyChangeEvent evt) 
							{
								if(evt.getPropertyName().equals("DONE"))
								{
									
									final RecordRetrieveWorker recordSearcher = new RecordRetrieveWorker(recordDao,r, RecordRetrieveWorker.SEARCH);
									recordSearcher.addPropertyChangeListener(new PropertyChangeListener() 
									{							
										@Override
										public void propertyChange(PropertyChangeEvent evt) 
										{
											if(evt.getPropertyName().equals("DONE"))
											{
												List<Record> records;
												try 
												{
													records = (List<Record>)recordSearcher.get();
													Iterator<Record> i = records.iterator();
													while(i.hasNext())
													{
														Record curRecord = i.next();
														DiagnosisUpdateWorker diagnosisWorker = new DiagnosisUpdateWorker(diagnosisDao, curRecord);
														diagnosisWorker.execute();
													}
													final RecordUpdateWorker recordDeleter = new 
															RecordUpdateWorker(recordDao, r, RecordUpdateWorker.DELETE);
													recordDeleter.addPropertyChangeListener(new PropertyChangeListener() 
													{	
														@Override
														public void propertyChange(PropertyChangeEvent evt) 
														{
															if(evt.getPropertyName().equals("DONE"))
															{
																PatientUpdateWorker patientDeleter = new PatientUpdateWorker(patientDao, p, PatientUpdateWorker.DELETE);
																patientDeleter.addPropertyChangeListener(new PropertyChangeListener() {
																	
																	@Override
																	public void propertyChange(PropertyChangeEvent evt) {
																		if(evt.getPropertyName().equals("DONE"))
																		{
																			loadingDialog.dispose();
																			changeView(getCurrentView());
																		}
																		
																	}
																});
																patientDeleter.execute();	
															}
														}
													});
													recordDeleter.execute();
													
												} catch (InterruptedException
														| ExecutionException e) 
												{e.printStackTrace();}
											}
											
										}
									});
									recordSearcher.execute();
								}
							}
						});
						countGetter.execute();
						loadingDialog.showGui();
					}
				}
			});
			dialog.showGui();
		}
		else if(object instanceof DictionaryWord)
		{
			final DictionaryWord word = (DictionaryWord)object;
			dialog = new PopupDialog(mainMenu, "Confirm delete",
					TitleConstants.CONFIRM_DELETE_WORD, 
					"Yes","No");
			dialog.addPropertyChangeListener(new PropertyChangeListener() 
			{	
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals("POSITIVE"))
					{
						DictionaryUpdateWorker wordDeleter = new DictionaryUpdateWorker
								(dictionaryDao, word.getWord(), word.getType(), DictionaryUpdateWorker.DELETE);
						wordDeleter.addPropertyChangeListener(new PropertyChangeListener() {
							
							@Override
							public void propertyChange(PropertyChangeEvent evt) {
								if(evt.getPropertyName().equals("DONE"))
								{
									loadingDialog.dispose();
									changeView(getCurrentView());
								}
							}
						});
						wordDeleter.execute();
						loadingDialog.showGui();
					}
				}
			});
			dialog.showGui();
		}
	}
}
