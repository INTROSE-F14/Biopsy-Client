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
		public static final float CATEGORY = 25;
		public static final float HEADER = 45;
		public static final float SUBHEADER = 18;
		public static final float MENU = 15;
		public static final String SECONDARY_COLOR = "#3d36cc";//blue-violet
		public static final String PRIMARY_COLOR  = "#FFFFFF";//white
		public static final String TERTIARY_COLOR = "#edebeb";//dark gray
		public static final String QUARTERNARY_COLOR = "#f2f1f1";//light gray
		public static final String GRAY = "#545353";
	}
	
	public abstract class RecordConstants
	{
		public static final char HISTOPATHOLOGY_RECORD = 'H';
		public static final char GYNECOLOGY_RECORD = 'G';
		public static final char CYTOLOGY_RECORD = 'C';
		public static final int PATIENT = 2;
		public static final int OTHERS = 3;
		public static final int RECORD = 0;
		public static final int DIAGNOSIS = 1;
		public static final int REMARKS_LENGTH = 10000;
		public static final int ROOM_LENGTH = 15;
		public static final int DIAGNOSIS_LENGTH = 10000;
		public static final int GROSS_LENGTH = 10000;
		public static final int MICRO_LENGTH = 10000;
	}
	
	public abstract class PatientConstants
	{
		public static final int LAST_NAME_LENGTH = 30;
		public static final int FIRST_NAME_LENGTH = 30;
		public static final int MIDDLE_NAME_LENGTH = 30;
	}

	public abstract class TitleConstants
	{
		public static final String HORM_EVAL = "Hormonal Evaluation (for vaginal smears only)";
		public static final String SUPERFICIALS = "Superficials";
		public static final String INTERMEDIATES = "Intermediates";
		public static final String PARABASALS = "Parabasals";
		public static final String MAINTENANCE = "Maintenance";
		public static final String CONFIRM_DELETE_WORD =  "Are you sure you want to delete this word?";
		public static final String CONFIRM_DELETE_RECORD = "Are you sure you want to delete this record?";
		public static final String CONFIRM_DELETE_PATIENT = "Are you sure you want to delete this patient? This will also delete all records associated to this patient";
		public static final String DICTIONARY_HINT = "Add words, phrases, or names (eg 'Juan dela Cruz')";
		public static final String DICTIONARY = "Dictionary";
		public static final String DISCARD_CHANGES_MESSAGE = "All changes made will be discarded, do you want to continue?";
		public static final String REFRESH_PANEL = "Refresh";
		public static final String EMPTY_PANEL = "List is empty";
		public static final String LIST_PANEL = "List";
		public static final String LOGIN_WINDOW = "Login - Biopsy Client";
		public static final String ALL="ALL";
		public static final String DIAGNOSIS = "Diagnosis";
		public static final String SEARCH_RECORD = "Search record";
		public static final String SEARCH_PATIENT = "Search patient";
		public static final String RECORD_OVERVIEW = "Record Overview";
		public static final String RESULTS = "Interpretation and Results";
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
		public static final String SPEC_TYPE = "Specimen Type";
		public static final String CONVENTIONAL = "Conventional smear";
		public static final String LIQUID_BASED = "Liquid based preparation";
		public static final String OTHERS = "Others";
		public static final String SPEC_ADEQ = "Specimen Adequacy";
		public static final String SATISFACTORY = "Satisfactory for evaluation";
		public static final String UNSATISFACTORY = "Unsatisfactory for evaluation due to";
		public static final String NILM = "Negative for Intraepithelial Lesion or Malignancy (NILM)";
		public static final String ECA = "Epithelial Cell Abnormalities";
		public static final String OMN = "Other Malignant Neoplasms";
		public static final String ORGANISMS = "Organisms";
		public static final String ONF = "Other non-neoplastic findings";
		public static final String OTHER = "Other";
		public static final String SQUAMOUS_CELL = "Squamous Cell";
		public static final String GLANDULAR_CELL = "Glandular Cell";
		public static final String ORG1 = "Trichomonas vaginalis";
		public static final String ORG2 = "Fungal organisms morphologically consistent with Candida species";
		public static final String ORG3	= "Shift in flora suggestive of bacterial vaginosis";
		public static final String ORG4 = "Bacteria morphologically consistent with Actinomyces species";
		public static final String ORG5 = "Cellular changes associated with Herpes simplex virus";
		public static final String ONF1 = "Reactive cellular changes associated with:";
		public static final String ONF2 = "Glandular cells status post hysterectomy";
		public static final String ONF3 = "Atrophy";
		public static final String ONF1BOX1 = "inflammation (includes typical repair)";
		public static final String ONF1BOX2 = "radiation";
		public static final String ONF1BOX3 = "Intrauterine contraceptive device (IUD)";
			
		public static final String OTHER1 = "Endometrial cells";
		public static final String SQUAMOUS1 = "Atypical squamous cells";
		public static final String SQUAMOUS2 = "Low grade squamous intraepithelial lesion (LSIL) encompassing HPV/mild dysplasia/CIN I";
		public static final String SQUAMOUS3 = "High grade squamous intraepithelial lesion (HSIL)";
		public static final String SQUAMOUS1BOX1 = "of undetermined significance (ASC-US)";
		public static final String SQUAMOUS1BOX2 = "cannot exclude HSIL (ASC-H)";
		public static final String SQUAMOUS3BOX1 = "encompassing: moderate and severe dysplasia/CIN2/CIN3/CIS";
		public static final String SQUAMOUS3BOX2 = "with features suspicious for invasion (if invasion suspected)";
		public static final String SQUAMOUS4 = "Squamous cell carcinoma";
		public static final String GLANDULAR1 = "Atypical";
		public static final String GLANDULAR2 = "Endocervical Adenocarcinoma in situ";
		public static final String GLANDULAR3 = "Adenocarcinoma";
		
		public static final String GLANDULAR1BOX1 = "endocervical cells (NOS or specify in comment)";
		public static final String GLANDULAR1BOX2 = "endometrial cells (NOS or specify in comment)";
		public static final String GLANDULAR1BOX3 = "glandular cells (NOS or specify in comment)";
		public static final String GLANDULAR1BOX4 = "endocervical cells, favor neoplastic";
		public static final String GLANDULAR1BOX5 = "glandular cells, favor neoplastic";
		public static final String GLANDULAR3BOX1 = "endocervical";
		public static final String GLANDULAR3BOX2 = "endometrial";
		public static final String GLANDULAR3BOX3 = "extrauterine";
		public static final String GLANDULAR3BOX4 = "not otherwise specified (NOS)";
		public static final String QUICK_FILTER = "Quick filter";
		public static final String DETAIL_PANEL = "Detail panel";
		public static final String LOAD_PATIENT = "Load patient";
	}
	
	public abstract class ActionConstants
	{
		public static final String DELETE = "Delete";
		public static final String ADD_WORD = "Add word";
		public static final String SELECT_EXPORT = "Select export";
		public static final String SELECT_RESTORE = "Select restore";
		public static final String SELECT_BACKUP = "Select backup";
		public static final String BACK = "Back";
		public static final String NEW_HISTOPATHOLOGY = "New Histopathology";
		public static final String NEW_GYNENECOLOGY = "New Gynecology";
		public static final String NEW_CYTOTOLOGY = "New Cytology";
		public static final String NEW_PATIENT = "New Patient";
		public static final String NEW_SPECIMEN = "New Specimen";
		public static final String NEW_PHYSICIAN = "New Physician";
		public static final String NEW_PATHOLOGIST = "New Pathologist";
		public static final String LOAD_PATIENT = "LOAD_EXISTING_PATIENT";
		public static final String SEARCH_PATIENT = "Search patient";
		public static final String SEARCH_RECORD = "Search record";
		public static final int VIEW = 0;
		public static final int EDIT = 1;
		public static final int NEW = 2;
		public static final String REFRESH = "REFRESH";
		public static final String LOG_OUT = "LOG_OUT";
		public static final String LOG_IN = "LOG_IN";
		public static final String EDIT_RECORD = "EDIT_RECORD";
		public static final String SAVE = "SAVE_RECORD";
		public static final String CANCEL = "CANCEL_EDIT";
		public static final String PRINT = "PRINT";
		public static final String SEARCH= "Search";
		public static final String BACKUP = "Backup";
		public static final String RESTORE = "Restore";
		public static final String EXPORT = "Export";
		public static final String VIEW_TOOLSOVERVIEW = "view tools";
		public static final String VIEW_BACKUP = "View backup";
		public static final String VIEW_RESTORE = "View restore";
		public static final String VIEW_EXPORT = "View export";
		public static final String NEXT = "Next";
		public static final String PREVIOUS = "Previous";
	}
	
	public abstract class RecordTable
	{
		public static final String PATIENT = "Patient";
		public static final String RECORD_YEAR = "recordyear";
		public static final String RECORD_NUMBER = "recordnumber";
		public static final String RECORD_TYPE = "recordtype";
		public static final String PATIENT_ID = "patientid";
		public static final String PHYSICIAN = "physician";
		public static final String PATHOLOGIST = "pathologist";
		public static final String DATE_RECEIVED =  "datereceived";
		public static final String DATE_COMPLETED = "datecompleted";
		public static final String SPECIMEN = "specimen";
		public static final String REMARKS = "remarks";
		public static final String DIAGNOSIS = "diagnosis";
		public static final String ROOM = "room";
		public static final String SPEC_TYPE = "specimentype";
		public static final String GROSS_DESC = "grossdescription";
		public static final String MICRO_NOTE = "microscopicNotes";
	}
	
	public abstract class PatientTable
	{
		public static final String PATIENT_ID = "patientid";
		public static final String LAST_NAME = "lastname";
		public static final String FIRST_NAME = "firstname";
		public static final String MIDDLE_NAME = "middlename";
		public static final String BIRTHDAY  = "birthday";
		public static final String GENDER = "gender";
	}
	
	public abstract class DiagnosisTable
	{
		public static final String CATEGORY_ID = "category_id";	
		public static final String RECORD_YEAR = "recordyear";
		public static final String RECORD_NUMBER = "recordnumber";
		public static final String RECORD_TYPE = "recordtype";
		public static final String VALUE = "diagnosis_value";
	}
	
	public abstract class CategoriesTable
	{
		public static final String CATEGORY_ID = "category_id";
		public static final String NAME = "category_name";
		public static final String PARENT_CATEGORY = "parent_category";
	}
	
	public abstract class CategoriesConstants
	{
		public static final int NILM = 1;
		public static final int ECA = 2;
		public static final int OMN = 3;
		public static final int OTHERS = 4;
		public static final int ORGANISMS = 5;
		public static final int ONF = 6;
		public static final int OTHER = 7;
		public static final int SC = 8;
		public static final int GC = 9;
		public static final int SA = 10;
		public static final int HE = 11;
		public static final int S = 12;
		public static final int I = 13;
		public static final int P = 14;
	}
	
	public abstract class StatusConstants
	{
		public static final int SUCCESS = 0;
		public static final int FAILED = 1;
		public static final int PREPARING = 2;
		public static final int DEFAULT = 3;
		public static final int ONGOING = 4;
	}
	
	public abstract class PrintConstants
	{
		public static final String CYTOLOGY_NUMBER_LABEL = "CYTOLOGY NO: ";
		public static final String HISTOPATHOLOGY_NUMBER_LABEL = "HISTOPATHOLOGY NO: ";
		public static final String GYNECOLOGY_NUMBER_LABEL = "GYNECOLOGY NO: ";
		public static final String PATIENT_NAME_LABEL = "NAME: ";
		public static final String ROOM_LABEL = "ROOM: ";
		public static final String AGE_LABEL = "AGE: ";
		public static final String GENDER_LABEL = "SEX: ";
		public static final String SPECIMEN_LABEL = "SPECIMEN: ";
		public static final String PHYSICIAN_LABEL = "PHYSICIAN: Dr. ";
		public static final String PATHOLOGIST_LABEL = "PATHOLOGIST";
		public static final String DATE_RECEIVED_LABEL = "DATE RECEIVED: ";
		public static final String DATE_COMPLETED_LABEL = "DATE COMPLETED: ";
		public static final String HISTOPATHOLOGY_TITLE = "HISPATHOLOGY REPORT";
		public static final String CYTOLOGY_TITLE = "NON GYNECOLOGICAL CYTOLOGY REPORT";
		public static final String GYNECOLOGY_TITLE = "GYNECOLOGICAL CYTOLOGY REPORT";
		public static final String DIAGNOSIS_LABEL_C = "CYTOPATHOLOGIC DIAGNOSIS";
		public static final String DIAGNOSIS_LABEL_H = "HISTOPATHOLOGIC DIAGNOSIS";
		public static final String COMMENTS_LABEL = "COMMENTS";
		public static final String DESCRIPTION_LABEL = "GROSS & MICROSCOPIC DESCRIPTIONS";
		public static final String SPEC_ADEQ = "SPECIMEN ADEQUACY";
		public static final String SATIS = "SATISFACTORY FOR EVALUATION";
		public static final String UNSATIS = "UNSATISFACTORY FOR EVALUATION DUE TO ";
		public static final String INTER_RES = "INTERPRETATION/RESULT";
		public static final String NILM = "NEGATIVE FOR INTRAEPITHELIAL LESION OR MALIGNANCY";
		public static final String ORGANISMS = "ORGANISMS";
		public static final String ONF = "OTHER NON-NEOPLASTIC FINDINGS";
		public static final String OTHER = "OTHER";
		public static final String SQUAMOUS = "SQUAMOUS CELL ABNORMALITIES";
		public static final String GLANDULAR = "GLANDULAR CELL ABNORMALITIES";
		public static final String OMN = "OTHER MALIGNANT NEOPLASMS";
	}

	public abstract class DictionaryTable
	{
		public static final String WORD = "word";
		public static final String TYPE = "wordtype";
	}
	
	public abstract class DictionaryConstants
	{
		public static final int WORD_LENGTH = 100;
		public static final int SPECIMEN = 0;
		public static final int PATHOLOGIST = 1;
		public static final int PHYSICIAN = 2;
	}
}
