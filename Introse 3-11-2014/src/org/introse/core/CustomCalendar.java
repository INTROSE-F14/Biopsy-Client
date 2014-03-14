package org.introse.core;

import java.util.Calendar;

public class CustomCalendar 
{
	private int month;
	private int day;
	private int year;
	
	public CustomCalendar()
	{
		Calendar c = Calendar.getInstance();
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DATE);
		year = c.get(Calendar.YEAR);
	}
	
	public int getCurrentMonth()
	{
		return Calendar.getInstance().get(Calendar.MONTH);
	}
	
	public int getCurrentYear()
	{
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public int getCurrentDay()
	{
		return Calendar.getInstance().get(Calendar.DATE);
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void set(int month, int day, int year)
	{
		this.day = day;
		this.month = month;
		this.year = year;
	}
}
