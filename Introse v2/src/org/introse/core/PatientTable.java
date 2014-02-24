package org.introse.core;

public enum PatientTable 
{
	PATIENT_ID, LAST_NAME, FIRST_NAME, MIDDLE_NAME, BIRTHDAY, 
	GENDER, ROOM;
	
	
	public String toString()
	{
		switch(this)
		{
		case LAST_NAME: return "lastname";
		case PATIENT_ID: return "patientid";
		case FIRST_NAME: return "firstname";
		case MIDDLE_NAME: return "middlename";
		case BIRTHDAY: return "birthday";
		case GENDER: return "gender";
		case ROOM: return "room";
		}
		return "Undefined";
	}
}
