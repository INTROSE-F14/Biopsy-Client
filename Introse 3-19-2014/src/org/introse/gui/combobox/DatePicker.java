package org.introse.gui.combobox;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.introse.core.CustomCalendar;

public class DatePicker extends JPanel implements ItemListener{

	private static String[]  monthNames = {"January", "February", 
		 "March", "April", "May", "June", "July", "August", 
		 "September", "October", "November", "December"};
	private static String[] monthDays = {"1","2", "3","4","5","6","7","8",
		"9","10","11","12","13","14","15","16","17","18","19","20",
		"21","22","23","24","25","26","27","28","29","30","31"};
	
	private JComboBox<String> day;
	private JComboBox<String> month;
	private JComboBox<String> year;
	
	public DatePicker(int lowerLimit)
	{
		super(new GridBagLayout());
		setBackground(Color.white);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String[] years = new String[lowerLimit + 1];
		int j = 0;
		for(int i = currentYear; i >= currentYear - lowerLimit; i--)
		{
			years[j] = "" + i;
			j++;
		}
		day= new JComboBox<String>(monthDays);
		month= new JComboBox<String>(monthNames);
		year= new JComboBox<String>(years);
		month.addItemListener(this);
		year.addItemListener(this);
		day.setBorder(null);
		month.setBorder(null);
		year.setBorder(null);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,0,0,5);
		c.gridx = 0;
		add(day, c);
		c.gridx = 1;
		add(month, c);
		c.gridx = 2;
		c.insets = new Insets(0,0,0,0);
		add(year, c);
	}
	
	public void setDate(CustomCalendar date)
	{
		int month = date.getMonth();
		int day = date.getDay();
		int year = date.getYear();
		this.day.setSelectedItem(""+day);
		this.month.setSelectedIndex(month);
		this.year.setSelectedItem(""+year);
	}
	
	public void setDate(Calendar date)
	{
		int month = date.get(Calendar.MONTH);
		int day = date.get(Calendar.DATE);
		int year = date.get(Calendar.YEAR);
		this.day.setSelectedItem(""+day);
		this.month.setSelectedIndex(month);
		this.year.setSelectedItem(""+year);
	}
	
	public int getDay()
	{
		return day.getSelectedIndex() + 1;
	}
	
	public int getMonth()
	{
		return month.getSelectedIndex();
	}
	
	public int getYear()
	{
		return Integer.parseInt((String)year.getSelectedItem());
	}
	
	public void setPickerFont(Font font)
	{
		this.day.setFont(font);
		this.month.setFont(font);
		this.year.setFont(font);
	}
	
	@Override
	public void setEnabled(boolean isEnabled)
	{
		this.day.setEnabled(isEnabled);
		this.month.setEnabled(isEnabled);
		this.year.setEnabled(isEnabled);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		Object source = e.getSource();
		int change = e.getStateChange();
		
		if(change == ItemEvent.SELECTED)
		{
			if(source == month)
			{
				int selectedMonth = month.getSelectedIndex();
				
				if(selectedMonth == 0 || selectedMonth == 2 || selectedMonth == 4 || 
						selectedMonth == 6 || selectedMonth == 7 || selectedMonth == 9 ||
						selectedMonth == 0 || selectedMonth == 11)
				{
					int i = day.getItemCount() + 1;
					String d = "" + i;
					while(day.getItemCount() < 31)
					{
						day.addItem(d);
						i++;
						d = "" + i;
					}
				}
				else if(selectedMonth == 3 || selectedMonth == 5 || 
						selectedMonth == 8 || selectedMonth == 10)
				{
					if(day.getItemCount() == 31)
						day.removeItemAt(30);
					else
					{
						int i = day.getItemCount() + 1;
						String d = "" + i;
						while(day.getItemCount() < 30)
						{
							day.addItem(d);
							i++;
							d = "" + i;
						}
					}
				}
				else if(selectedMonth == 1)
				{
					int selectedYear = Integer.parseInt((String)year.getSelectedItem());
					if(selectedYear % 4 == 0 || (selectedYear % 100 == 0 && selectedYear % 400 == 0))
					{
						if(day.getItemCount() == 31)
							day.removeItemAt(30);
						if(day.getItemCount() == 30)
							day.removeItemAt(29);
						if(day.getItemCount() == 29)
							return;
						
						int i = day.getItemCount() + 1;
						String d = "" + i;
						while(day.getItemCount() < 29)
						{
							day.addItem(d);
							i++;
							d = "" + i;
						}
					}
					else
					{
						if(day.getItemCount() == 31)
							day.removeItemAt(30);
						if(day.getItemCount() == 30)
							day.removeItemAt(29);
						if(day.getItemCount() == 29)
							day.removeItemAt(28);
					}
				}
			}
			else if(source == year)
			{
				if(month.getSelectedIndex() == 1)
				{
					int selectedYear = Integer.parseInt((String)year.getSelectedItem());
					if(selectedYear % 4 == 0 || (selectedYear % 100 == 0 && selectedYear % 400 == 0))
					{
						if(day.getItemCount() == 31)
							day.removeItemAt(30);
						if(day.getItemCount() == 30)
							day.removeItemAt(29);
						if(day.getItemCount() == 29)
							return;
						
						int i = day.getItemCount() + 1;
						String d = "" + i;
						while(day.getItemCount() < 29)
						{
							day.addItem(d);
							i++;
							d = "" + i;
						}
					}
					else
					{
						if(day.getItemCount() == 31)
							day.removeItemAt(30);
						if(day.getItemCount() == 30)
							day.removeItemAt(29);
						if(day.getItemCount() == 29)
							day.removeItemAt(28);
					}
				}
			}
		}
		
	}
}
