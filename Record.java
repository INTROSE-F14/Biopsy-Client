public class Record
{
	public Record(String idNum, String specimen, String physician, String pathologist, String biopsy, Date dateReceived, Date dateCompleted)
	{
		this.idnum = idNum;
		this.specimen = specimen;
		this.physician = physician;
		this.pathologist = pathologist;
		this.biopsy = biopsy;
		this.dateReceived = dateReceived;
		this.dateCompleted = dateCompleted;
	}

	public String getIDNum()
	{
		return this.idNum;
	}

	public String getSpecimen()
	{
		return this.specimen;
	}

	public String getPhysician()
	{
		return this.physician;
	}

	public String getPathologist()
	{
		return this.pathologist;
	}

	public String getBiopsy()
	{
		return this.biopsy;
	}

	public Date getDateReceived()
	{
		return this.dateReceived;
	}

	public Date getDateCompleted()
	{
		return this.dateCompleted;
	}

	public void setIDNum(String idNum)
	{}

	public void setSpecimen(String specimen)
	{}

	public void setPhysician(String physician)
	{}

	public void setPathologist(String pathologist)
	{}

	public void setBiopsy(String biopsy)
	{}

	public void setDateReceived(Date dateReceived)
	{}

	public void setDateCompleted(Date dateCompleted)
	{}
}