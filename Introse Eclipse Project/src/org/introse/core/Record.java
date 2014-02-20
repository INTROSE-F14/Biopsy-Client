package org.introse.core;

import java.awt.print.Printable;
import java.util.Calendar;

public abstract class Record implements Printable
{
	private Patient patient;
	private String referenceNumber;
	private String specimen;
	private String physician;
	private String pathologist;
	private Calendar dateReceived;
	private Calendar dateCompleted;
	private int recordType;
	private String diagnosis;
	private String remarks;
	
	public Record(Patient patient, String referenceNumber, String specimen, String physician, String pathologist, 
	Calendar dateReceived, Calendar dateCompleted, String diagnosis, String comments, int recordType)
	{
		this.patient = patient;
		this.referenceNumber = referenceNumber;
		this.specimen = specimen;
		this.physician = physician;
		this.pathologist = pathologist;
		this.dateReceived = dateReceived;
		this.dateCompleted = dateCompleted;
		this.recordType = recordType;
	}

	//Getters
	public Patient getPatient()
	{
		return patient;
	}

	public String getReferenceNumber()
	{
		return referenceNumber;
	}

	public String getSpecimen()
	{
		return specimen;
	}

	public String getPhysician()
	{
		return physician;
	}

	public String getPathologist()
	{
		return pathologist;
	}

	public String getDiagnosis()
	{
		return diagnosis;
	}

	public Calendar getDateReceived()
	{
		return dateReceived;
	}

	public Calendar getDateCompleted()
	{
		return dateCompleted;
	}

	public String getRemarks()
	{
		return remarks;
	}
	
	public int getRecordType()
	{
		return recordType;
	}

	//Setters
	public void setSpecimen(String specimen)
	{
		this.specimen = specimen;
	}

	public void setPhysician(String physician)
	{
		this.physician = physician;
	}

	public void setPathologist(String pathologist)
	{
		this.pathologist = pathologist;
	}

	public void setDiagnosis(String diagnosis)
	{
		this.diagnosis = diagnosis;
	}

	public void setDateReceived(Calendar dateReceived)
	{
		this.dateReceived = dateReceived;
	}
	
	public void setDateCompleted(Calendar dateCompleted)
	{
		this.dateCompleted = dateCompleted;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}
	
	public void setRecordType(int recordType)
	{
		this.recordType = recordType;
	}
}
