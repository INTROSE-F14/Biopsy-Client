package org.introse.core.database;
/*
 * Class Name: FileHelper
 * Brief Description: 
 * 	Class used to retrieve important files
 * 
 * Attributes:
 * 	PROGRAM_NAME- (String) Name of the program
 * 	PROGRAM_DATA_PATH - (String) path where the program data are saved/stored
 * 	PROPERTIES_FILE_NAME -(String) file name of the file containing program settings
 * 
 * Methods:
 * 	createBackupFile,
 * 	getSchemaFile(),
 * 	getPropertiesFile()
 */



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.introse.core.Preferences;


public class FileHelper 
{
	public static final String PROGRAM_DATA_PATH = "C:\\Users\\" + System.getProperty("user.name") + "\\" + Preferences.PROGRAM_NAME;
	public static final String PROPERTIES_FILE_NAME = "properties.txt";
	
	
	public static boolean createProgramDirectory()
	{
		File directory = new File(getBackupDirectory());
		return directory.mkdirs();
	}
	/* Method Name: createBackupFile
	 * Brief Description:
	 * 	creates a backup file in the backup directory
	 * 
	 * Return type: File
	 * Parameters: N/A
	 */
	public static File createBackupFile()
	{
		File file = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd-HH-mm");
		String fileName = sdf.format(date) + ".bcb";
		file = new File(PROGRAM_DATA_PATH + "\\Backup\\" + fileName);
		file.getParentFile().mkdirs();
		return file;
	}
	
	public static File createDiagnosisExportFile()
	{
		File file = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd-HH-mm");
		String fileName = "[Diagnosis]" + sdf.format(date) + ".csv";
		file = new File(PROGRAM_DATA_PATH + "\\Export\\" + fileName);
		file.getParentFile().mkdirs();
		return file;
	}
	
	public static File createRecordExportFile()
	{
		File file = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd-HH-mm");
		String fileName = "[Records]" + sdf.format(date) + ".csv";
		file = new File(PROGRAM_DATA_PATH + "\\Export\\" + fileName);
		file.getParentFile().mkdirs();
		return file;
	}
	
	public static File createPatientExportFile()
	{
		File file = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd-HH-mm");
		String fileName = "[Patients]" + sdf.format(date) + ".csv";
		file = new File(PROGRAM_DATA_PATH + "\\Export\\" + fileName);
		file.getParentFile().mkdirs();
		return file;
	}
	
	/* Method Name: getPropertiesFile
	 * Brief Description:
	 * 	Retrieves the file containing the program settings
	 * 
	 * Return type: File
	 * Parameters: N/A
	 */
	public static File getPropertiesFile()
	{
		File file = null;
		file = new File(PROGRAM_DATA_PATH + "\\Settings\\" + PROPERTIES_FILE_NAME);
		return file;
	}
	
	public static String getBackupDirectory()
	{
		return PROGRAM_DATA_PATH + "\\Backup\\";
	}
}
