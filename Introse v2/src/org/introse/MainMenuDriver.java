package org.introse;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.introse.core.CytologyRecord;
import org.introse.core.GynecologyRecord;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Patient;
import org.introse.core.PatientTable;
import org.introse.core.Record;
import org.introse.core.RecordTable;
import org.introse.core.dao.PatientDao;
import org.introse.core.dao.RecordDao;
import org.introse.core.network.Client;
import org.introse.gui.MainMenu;
import org.introse.gui.customcomponent.ErrorDialog;
import org.introse.gui.customcomponent.ListItem;
import org.introse.gui.customcomponent.ListProvider;
import org.introse.gui.event.CustomListener;
import org.introse.gui.form.CytologyForm;
import org.introse.gui.form.Form;
import org.introse.gui.form.GynecologyForm;
import org.introse.gui.form.HistopathologyForm;
import org.introse.gui.panel.ContentPanel;
import org.introse.gui.panel.DetailPanel;
import org.introse.gui.panel.PatientForm;
import org.introse.gui.panel.PatientPanel;
import org.introse.gui.panel.RecordPanel;

public class MainMenuDriver 
{
	private MainMenu mainMenu;
	private RecordDao recordDao;
	private PatientDao patientDao;
	private CustomListener listener;
	private Client client;
	private List<ListItem> patientList;
	private List<ListItem> histopathologyList;
	private List<ListItem> gynecologyList;
	private List<ListItem> cytologyList;
	private DetailPanel panel;
	public static final int VIEW_ITEM = 0;
	public static final int USE_ITEM = 1;
	
	public MainMenuDriver(ProjectDriver driver)
	{
		this.client = driver.getClient();
		this.listener = driver.getListener();
		recordDao = new RecordDao(client);
		patientDao = new PatientDao(client);
	}
	
	public void main()
	{
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
		mainMenu.getContentPanel().changeView(view, removeDetails);
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
		ProjectDriver.createAndShowGui();
	}

	public List<ListItem> updateHistopathologyList()
	{
		List<ListItem> histopathologyList = new Vector<ListItem>();
		HistopathologyRecord record = new HistopathologyRecord();
		record.putAttribute(RecordTable.RECORD_TYPE.toString(), 0);
		List<Object> records = recordDao.search(record);
		Iterator<Object> i = records.iterator();
		while(i.hasNext())
		{
			Record curRecord = (HistopathologyRecord)i.next();
			int patientID = (int)curRecord.getAttribute(RecordTable.PATIENT_ID.toString());
			Patient patient = new Patient(patientID);
			Patient p = (Patient)patientDao.get(patient);
			String patientName = p.getAttribute(PatientTable.LAST_NAME.toString()) + 
					", " + p.getAttribute(PatientTable.FIRST_NAME.toString()) + " " + 
					p.getAttribute(PatientTable.MIDDLE_NAME.toString());
			ListItem item = new ListItem((String)curRecord.getAttribute(RecordTable.REF_NUM.toString()),
					(String)curRecord.getAttribute(RecordTable.REF_NUM.toString()), 
					(String)curRecord.getAttribute(RecordTable.SPECIMEN.toString()), patientName, 
					(String)curRecord.getAttribute(RecordTable.PATHOLOGIST.toString()),
					ListProvider.HISTOPATHOLOGY_RECORD);
			histopathologyList.add(item);
		}
		return histopathologyList;
	}
	
	public List<ListItem> updateCytologyList()
	{
		List<ListItem> cytologyList = new Vector<ListItem>();
		CytologyRecord record = new CytologyRecord();
		record.putAttribute(RecordTable.RECORD_TYPE.toString(), 2);
		List<Object> records = recordDao.search(record);
		
		Iterator<Object> i = records.iterator();
		while(i.hasNext())
		{
			Record curRecord = (CytologyRecord)i.next();
			int patientID = (int)curRecord.getAttribute(RecordTable.PATIENT_ID.toString());
			Patient patient = new Patient(patientID);
			Patient p = (Patient)patientDao.get(patient);
			String patientName = p.getAttribute(PatientTable.LAST_NAME.toString()) + 
					", " + p.getAttribute(PatientTable.FIRST_NAME.toString()) + " " + 
					p.getAttribute(PatientTable.MIDDLE_NAME.toString());
			ListItem item = new ListItem((String)curRecord.getAttribute(RecordTable.REF_NUM.toString()),
					(String)curRecord.getAttribute(RecordTable.REF_NUM.toString()), 
					(String)curRecord.getAttribute(RecordTable.SPECIMEN.toString()), patientName, 
					(String)curRecord.getAttribute(RecordTable.PATHOLOGIST.toString()),
					ListProvider.CYTOLOGY_RECORD);
			cytologyList.add(item);
		}
		return cytologyList;
	}
	
	public List<ListItem> updateGynecologyList()
	{
		List<ListItem> gynecologyList = new Vector<ListItem>();
		GynecologyRecord record = new GynecologyRecord();
		record.putAttribute(RecordTable.RECORD_TYPE.toString(), 1);
		List<Object> records = recordDao.search(record);
		
		Iterator<Object> i = records.iterator();
		while(i.hasNext())
		{
			Record curRecord = (GynecologyRecord)i.next();
			int patientID = (int)curRecord.getAttribute(RecordTable.PATIENT_ID.toString());
			Patient patient = new Patient(patientID);
			Patient p = (Patient)patientDao.get(patient);
			String patientName = p.getAttribute(PatientTable.LAST_NAME.toString()) + 
					", " + p.getAttribute(PatientTable.FIRST_NAME.toString()) + " " + 
					p.getAttribute(PatientTable.MIDDLE_NAME.toString());
			ListItem item = new ListItem((String)curRecord.getAttribute(RecordTable.REF_NUM.toString()),
					(String)curRecord.getAttribute(RecordTable.REF_NUM.toString()), 
					(String)curRecord.getAttribute(RecordTable.SPECIMEN.toString()), patientName, 
					(String)curRecord.getAttribute(RecordTable.PATHOLOGIST.toString()),
					ListProvider.GYNECOLOGY_RECORD);
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
			int patientID = (int)patient.getAttribute(PatientTable.PATIENT_ID.toString());
			String patientName = patient.getAttribute(PatientTable.LAST_NAME.toString()) + 
					", " + patient.getAttribute(PatientTable.FIRST_NAME.toString()) + " " + 
					patient.getAttribute(PatientTable.MIDDLE_NAME.toString());
			String gender = (String)patient.getAttribute(PatientTable.GENDER.toString());
			Calendar c = (Calendar)patient.getAttribute(PatientTable.BIRTHDAY.toString());
			int age = Calendar.getInstance().get(Calendar.YEAR) - c.get(Calendar.YEAR);
			ListItem item = new ListItem(""+patientID, patientName, gender, patientName, 
					age+"", ListProvider.PATIENT);
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
			case ContentPanel.HISTO_PANEL: panel.updateHistopathologyList(filterList(mainMenu.getContentPanel().getFilter(), 
											histopathologyList));
							  break;
			case ContentPanel.GYNE_PANEL: panel.updateGynecologyList(filterList(mainMenu.getContentPanel().getFilter(), 
							 				gynecologyList));
							 break;
			case ContentPanel.CYTO_PANEL: panel.updateCytologyList(filterList(mainMenu.getContentPanel().getFilter(), 
											cytologyList));
							 break;
			case ContentPanel.PATIENT_PANEL: panel.updatePatientList(filterList(mainMenu.getContentPanel().getFilter(), 
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
			case ListProvider.HISTOPATHOLOGY_RECORD:
				object = new HistopathologyRecord();
				((HistopathologyRecord)object).putAttribute(RecordTable.REF_NUM.toString(), id);
				object = (HistopathologyRecord)recordDao.get(object);
				break;
				
			case ListProvider.CYTOLOGY_RECORD:
				object = new CytologyRecord();
				((CytologyRecord)object).putAttribute(RecordTable.REF_NUM.toString(), id);
				object = (CytologyRecord)recordDao.get(object);
				break;
			case ListProvider.GYNECOLOGY_RECORD:
				object = new GynecologyRecord();
				((GynecologyRecord)object).putAttribute(RecordTable.REF_NUM.toString(), id);
				object = (GynecologyRecord)recordDao.get(object);
				break;
			case ListProvider.PATIENT:
				object = new Patient();
				((Patient)object).putAttribute(PatientTable.PATIENT_ID.toString(), Integer.parseInt(id));
				object = (Patient)patientDao.get(object);
				break;
		}
		if(object != null)
		{
			if(panel != null && panel instanceof RecordPanel)
				if(panel.getMode() == DetailPanel.NEW)
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
			patient.putAttribute(PatientTable.PATIENT_ID.toString(), 
					record.getAttribute(RecordTable.PATIENT_ID.toString()));
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
			
			panel = new RecordPanel(recordForm, patientForm, DetailPanel.VIEW);
			((DetailPanel)panel).addListener(listener);
		}
		
		else if(object instanceof Patient)
		{
			Patient patient = (Patient)object;
			PatientForm form = new PatientForm();
			form.setFields(patient);
			panel = new PatientPanel(form, DetailPanel.VIEW);
			((DetailPanel)panel).addListener(listener);
		}
		this.panel = (DetailPanel)panel;
		mainMenu.getContentPanel().setDetailsPanel(panel);
	}
	
	public void editCurrentForm()
	{
		panel.setMode(DetailPanel.EDIT);
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
				System.out.println(r.getAttribute(RecordTable.REF_NUM.toString()));
				DetailPanel rP = (DetailPanel)panel;
				switch(rP.getMode())
				{
				case DetailPanel.NEW:  patientDao.add(p);
				case DetailPanel.EDIT: r.putAttribute(RecordTable.PATIENT_ID.toString(), 
						   			   p.getAttribute(PatientTable.PATIENT_ID.toString()));
									   recordDao.add(r);
									   break;
				}
			}
			else if(panel instanceof PatientPanel)
			{
				patientDao.add(panel.getPatient());
			}
			panel.setMode(DetailPanel.VIEW);
			refresh();
		}
		else new ErrorDialog("Save Error", "Some fields are invalid").showGui();
	}
	
	public void cancelCurrentForm()
	{
		if(((DetailPanel)panel).getMode() == DetailPanel.EDIT)
		{
			if(panel instanceof RecordPanel)
			{
				Record record = panel.getRecord();
				if(record == null)
					System.out.println("NULL");
				else System.out.println(record.getAttribute(RecordTable.REF_NUM.toString()));
				record = (Record)recordDao.get(record);
				if(record == null)
					System.out.println("NULL 2");
				else System.out.println(record.getAttribute(RecordTable.REF_NUM.toString()));
				
				((RecordPanel)panel).getRecordForm().setFields(record);
				Patient patient = panel.getPatient();
				((RecordPanel)panel).getPatientForm().setFields(patientDao.get(patient));
			}
			else if(panel instanceof PatientPanel)
			{
				((PatientPanel)panel).getPatientForm().setFields(patientDao.get(panel.getPatient()));
			}
			panel.setMode(DetailPanel.VIEW);
		}
		else{
			removeDetailsPanel();
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
		case DetailPanel.HISTOPATHOLOGY:	record = new HistopathologyRecord();
											record.putAttribute(RecordTable.REF_NUM.toString(), 
													generateReferenceNumber(DetailPanel.HISTOPATHOLOGY));
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID.toString(), patientList.size() + 1);
											recordForm = new HistopathologyForm();
											patientForm = new PatientForm();
											((Form)patientForm).setFields(patient);
											((Form)recordForm).setFields(record);
											panel = new RecordPanel(recordForm, patientForm, DetailPanel.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case DetailPanel.GYNECOLOGY:		record = new GynecologyRecord();
											record.putAttribute(RecordTable.REF_NUM.toString(),
													generateReferenceNumber(DetailPanel.GYNECOLOGY));
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID.toString(), patientList.size() + 1);
											recordForm = new GynecologyForm();
											patientForm = new PatientForm();
											((Form)patientForm).setFields(patient);
											((Form)recordForm).setFields(record);
											panel = new RecordPanel(recordForm, patientForm, DetailPanel.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case DetailPanel.CYTOLOGY:			record = new CytologyRecord();
											record.putAttribute(RecordTable.REF_NUM.toString(),
													generateReferenceNumber(DetailPanel.CYTOLOGY));
											patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID.toString(), patientList.size() + 1);
											recordForm = new CytologyForm();
											patientForm = new PatientForm();
											((Form)patientForm).setFields(patient);
											((Form)recordForm).setFields(record);
											panel = new RecordPanel(recordForm, patientForm, DetailPanel.NEW);
											((DetailPanel)panel).addListener(listener);
											break;
		case DetailPanel.PATIENT:			patient = new Patient();
											patient.putAttribute(PatientTable.PATIENT_ID.toString(), patientList.size() + 1);patientForm = new PatientForm();
											((Form)patientForm).setFields(patient);
											panel = new PatientPanel(patientForm, DetailPanel.NEW);
											((DetailPanel)panel).addListener(listener);
		}
		this.panel = (DetailPanel)panel;
		mainMenu.getContentPanel().setDetailsPanel(panel);
	}
	
	public String generateReferenceNumber(int type)
	{
		Record record = null;
		String prefix = "";
		String refNum = "";
		String recordNum = "";
		int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
		int i = 0;
		switch(type)
		{
		case DetailPanel.HISTOPATHOLOGY: prefix = "H" + year + "-";
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
					record = new HistopathologyRecord();
					record.putAttribute(RecordTable.REF_NUM.toString(), refNum);
					i++;
				}
				while(recordDao.get(record) != null);
				break;
		case DetailPanel.GYNECOLOGY: prefix = "G" + year + "-";
				do
				{
					recordNum = "";
					if(i < 10)
						recordNum= "000" + i;
					else if(i < 100)
						recordNum= "00" + i;
					else if(i < 1000)
						recordNum= "0" + i;
					else recordNum= ""+ i;
					refNum = prefix + recordNum;
					record = new GynecologyRecord();
					record.putAttribute(RecordTable.REF_NUM.toString(), refNum);
					i++;
				}
				while(recordDao.get(record) != null);
				break;
		case DetailPanel.CYTOLOGY: prefix = "C" + year + "-";
				do
				{
					if(i < 10)
						recordNum= "000" + i;
					else if(i < 100)
						recordNum= "00" + i;
					else if(i < 1000)
						recordNum= "0" + i;
					else recordNum= ""+ i;
					refNum = prefix + recordNum;
					record = new CytologyRecord();
					record.putAttribute(RecordTable.REF_NUM.toString(), refNum);
					i++;
				}
				while(recordDao.get(record) != null);
		}
		return refNum;
	}
	
	public void loadExistingPatient(Object patient)
	{
		((RecordPanel)panel).getPatientForm().setFields(patient);
	}
}
