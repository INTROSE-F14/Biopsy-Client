package org.introse;

public abstract class Constants 
{
	public abstract class NetworkConstants
	{
		public static final int PORT = 4444;
		public static final int TIMEOUT = 5000;
		public static final int AUTHENTICATION_FAILED = 2;
		public static final int AUTHENTICATION_SUCCESSFUL = 1;
		public static final int SERVER_ERROR = 0;
	}
	
	public abstract class StyleConstants
	{
		public static final float HEADER = 25;
		public static final float SUBHEADER = 18;
		public static final float MENU = 15;
		public static final String HOVER = "#E6E8FA";
		public static final String PRESSED  = "#CCCCCC";
		public static final String SELECTED = "#33b5e5";
		public static final String NORMAL = "#FFFFFF";
	}
	
	public abstract class RecordConstants
	{
		public static final int HISTOPATHOLOGY_RECORD = 0;
		public static final int GYNECOLOGY_RECORD = 1;
		public static final int CYTOLOGY_RECORD = 2;
		public static final int PATIENT = 3;
		public static final int OTHERS = 4;
	}
	
	public abstract class TitleConstants
	{
		public static final String HISTOPATHOLOGY = "Histopathology";
		public static final String GYNECOLOGY = "Gynecology";
		public static final String CYTOLOGY = "Cytology";
		public static final String PATIENTS = "Patients";
		public static final String PATHOLOGISTS = "Pathologists";
		public static final String PHYSICIANS = "Physicians";
		public static final String SPECIMENS = "Specimens";
		public static final String PREFERENCES = "Preferences";
		public static final String RECORDS = "Records";
		public static final String SEARCH_RESULT = "Search result";
	}
	
	public abstract class ActionConstants
	{
		public static final String NEW_HISTOPATHOLOGY = "New Histopathology";
		public static final String NEW_GYNENECOLOGY = "New Gynecology";
		public static final String NEW_CYTOTOLOGY = "New Cytology";
		public static final String NEW_PATIENT = "New Patient";
		public static final String NEW_SPECIMEN = "New Specimen";
		public static final String NEW_PHYSICIAN = "New Physician";
		public static final String NEW_PATHOLOGIST = "New Pathologist";
		public static final String LOAD_PATIENT = "LOAD_EXISTING_PATIENT";
		public static final String SEARCH_PATIENT = "Search patient";
		public static final String SEARCH_RECORD = "search record";
		public static final int VIEW = 0;
		public static final int EDIT = 1;
		public static final int NEW = 2;
		public static final String REFRESH = "REFRESH";
		public static final String LOG_OUT = "LOG_OUT";
		public static final String LOG_IN = "LOG_IN";
		public static final String EDIT_RECORD = "EDIT_RECORD";
		public static final String SAVE = "SAVE_RECORD";
		public static final String CANCEL = "CANCEL_EDIT";
                public static final String CLEAR = "CLEAR";
		public static final String PRINT = "PRINT";
		public static final String SEARCH= "Search";
	}
	
	public abstract class RecordTable
	{
		public static final String REF_NUM = "internalreferencenumber";
		public static final String RECORD_TYPE = "recordtype";
		public static final String PATIENT_ID = "patientid";
		public static final String PHYSICIAN = "physician";
		public static final String PATHOLOGIST = "pathologist";
		public static final String DATE_RECEIVED =  "datereceived";
		public static final String DATE_COMPLETED = "datecompleted";
		public static final String SPECIMEN = "specimen";
		public static final String DIAGNOSIS =  "diagnosis";
		public static final String REMARKS = "remarks";
	}
	
	public abstract class PatientTable
	{
		public static final String PATIENT_ID = "patientid";
		public static final String LAST_NAME = "lastname";
		public static final String FIRST_NAME = "firstname";
		public static final String MIDDLE_NAME = "middlename";
		public static final String BIRTHDAY  = "birthday";
		public static final String GENDER = "gender";
		public static final String ROOM = "room";
	}
}
