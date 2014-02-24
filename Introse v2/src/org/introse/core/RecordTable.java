package org.introse.core;

public enum RecordTable 
{
	REF_NUM, RECORD_TYPE, PATIENT_ID, PHYSICIAN, 
	PATHOLOGIST, DATE_RECEIVED,	DATE_COMPLETED,
	SPECIMEN, DIAGNOSIS, REMARKS;
	
	
	public String toString()
	{
		switch(this)
		{
		case REF_NUM: return "internalreferencenumber";
		case RECORD_TYPE: return "recordtype";
		case PATIENT_ID: return "patientid";
		case DATE_COMPLETED: return "datecompleted";
		case SPECIMEN: return "specimen";
		case PATHOLOGIST: return "pathologist";
		case PHYSICIAN: return "physician";
		case DATE_RECEIVED: return "datereceived";
		case DIAGNOSIS: return "diagnosis";
		case REMARKS: return "remarks";
		}
		return "Undefined";
	}
}
