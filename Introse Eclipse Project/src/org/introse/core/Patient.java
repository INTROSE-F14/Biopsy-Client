package org.introse.core;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Patient
{
	private String lastName;
	private String firstName;
	private String middleName;
	private char gender;
	private Calendar birthday;
	private List<Record> records;
	
	public Patient(String lastName, String firstName, String middleName, char gender, Calendar birthday)
	{
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.gender = gender;
		this.birthday = birthday;
		records = new Vector<Record>();
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getMiddleName()
	{
		return middleName;
	}
	
	public int getAge()
	{
		int birthYear = birthday.get(Calendar.YEAR);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		return currentYear - birthYear;
	}
	
	public char getGender()
	{
		return gender;
	}
	
	public Calendar getBirthday()
	{
		return birthday;
	}
	
	public Record getRecord(String referenceNumber)
	{
		Iterator<Record> i = records.iterator();
		while(i.hasNext())
		{
			Record record = i.next();
			if(record.getReferenceNumber().equals(referenceNumber))
				return record;
		}
		return null;
	}
	
	public void setName(String firstName, String lastName, String middleName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
	}
	
	public void setGender(char gender)
	{
		this.gender = gender;
	}
	
	public void setBirthday(Calendar date)
	{
		this.birthday = date;
	}
	
	public void addRecord(Record record)
	{
		records.add(record);
	}
	
	public void addRecord(List<Record> records)
	{
		this.records.addAll(records);
	}
	
	public void removeRecord(Record record)
	{
		records.remove(record);
	}
}