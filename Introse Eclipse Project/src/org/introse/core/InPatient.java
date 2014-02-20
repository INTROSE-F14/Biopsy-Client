package org.introse.core;

import java.util.Calendar;

public class InPatient extends Patient
{
	private String room;
	
	public InPatient(String lastName, String firstName, String middleName,
			char gender, Calendar birthday, String room) 
	{
		super(lastName, firstName, middleName, gender, birthday);
		this.room = room;

	}
	
	public String getRoom()
	{
		return room;
	}
	
	public void setRoom(String room)
	{
		this.room = room;
	}
}