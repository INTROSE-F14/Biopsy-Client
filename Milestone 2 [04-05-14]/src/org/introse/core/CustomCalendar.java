package org.introse.core;

import java.util.Calendar;

public class CustomCalendar 
{
	private int month;
	private int day;
	private int year;
	private final String[] months = {"January", "February","March","April","May","June","July","August",
			"September","October","November","December"};
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
	
	public int getAge(){
		int age = getCurrentYear() - getYear();  
		if (getCurrentMonth() < getMonth()) {
		  age--;  
		} else if (getCurrentMonth() == getMonth()
		    && getCurrentMonth() < getMonth()) {
		  age--;  
		}
		return age;
	}
	
	@Override
	public String toString()
	{
		return months[month] + " " + day + ", " + year;
	}
	
	public String toNumericFormat()
	{
		String monthfinal = month + "";
		String dayfinal = day + "";
		if(month < 10){
			monthfinal = "0" + monthfinal;
		}
		if(day < 10){
			dayfinal = "0" + dayfinal;
		}
		return monthfinal + "/" + dayfinal + "/" + year;
	}
	
}
