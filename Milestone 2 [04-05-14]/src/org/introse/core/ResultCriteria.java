package org.introse.core;

public class ResultCriteria {
	
	private String diagnosis, remarks, grossDesc, microNotes;

	public ResultCriteria(String diagnosis, String remarks, String grossDesc, String microNotes)
	{
		this.diagnosis = diagnosis;
		this.remarks = remarks;
		this.grossDesc = grossDesc;
		this.microNotes = microNotes;
	}
	
	public String getDiagnosis()
	{
		if(diagnosis != null)
		return diagnosis.toLowerCase();
		return diagnosis;
	}
	
	public String getRemarks()
	{
		if(remarks!=null)
		return remarks.toLowerCase();
		return remarks;
	}
	
	public String getGrossDesc()
	{
		if(grossDesc != null)
		return grossDesc.toLowerCase();
		return grossDesc;
	}
	
	public String getMicroNotes()
	{
		if(microNotes != null)
		return microNotes.toLowerCase();
		return microNotes;
	}
	
	public boolean isEmpty()
	{
		if(microNotes == null && diagnosis == null && remarks == null && grossDesc == null)
			return true;
		return false;
	}
}
