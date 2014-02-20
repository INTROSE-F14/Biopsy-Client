import java.util.Date;

public class Record
{
	private Patient patient;
	private String referenceNumber;
	private String specimen;
	private String physician;
	private String pathologist;
	private Date dateReceived;
	private Date dateCompleted;
	private int recordType;
	private String diagnosis;
	private String comments;
	
	public Record(Patient patient, String referenceNumber, String specimen, String physician, String pathologist, 
	Date dateReceived, Date dateCompleted, String diagnosis, String comments, int recordType)
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

	public Date getDateReceived()
	{
		return dateReceived;
	}

	public Date getDateCompleted()
	{
		return dateCompleted;
	}

	public String getRemarks()
	{
		return comments;
	}

	//Setters
	public void setSpecimen(String specimen)
	{}

	public void setPhysician(String physician)
	{}

	public void setPathologist(String pathologist)
	{}

	public void setBiopsyFindings(String biopsyFindings)
	{}

	public void setDateReceived(Date dateReceived)
	{}
	
	public void setDateCompleted(Date dateCompleted)
	{}

	public void setRenarks(String remarks)
	{}
}
