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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String[] monthWithWildcard =  {"Month", "January", "February", 
		 "March", "April", "May", "June", "July", "August", 
		 "September", "October", "November", "December"};
	private static String[]  monthNames = {"January", "February", 
		 "March", "April", "May", "June", "July", "August", 
		 "September", "October", "November", "December"};
	
	private static String[] dayWithWildcard = {"Day", "1","2", "3","4","5","6","7","8",
		"9","10","11","12","13","14","15","16","17","18","19","20",
		"21","22","23","24","25","26","27","28","29","30","31"};
	private static String[] monthDays = {"1","2", "3","4","5","6","7","8",
		"9","10","11","12","13","14","15","16","17","18","19","20",
		"21","22","23","24","25","26","27","28","29","30","31"};
	
	private JComboBox<String> day;
	private JComboBox<String> month;
	private JComboBox<String> year;
	private boolean hasWildcard;
	
	public DatePicker(int lowerLimit, boolean hasWildcard)
	{
		super(new GridBagLayout());
		setBackground(Color.white);
		this.hasWildcard = hasWildcard;
		
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int j;
		String[] years;
		if(hasWildcard)
		{
			years = new String[lowerLimit + 2];
			j = 1;
			years[0] = "Year";
			day= new JComboBox<String>(dayWithWildcard);
			month= new JComboBox<String>(monthWithWildcard);
		}
		else
		{
			years = new String[lowerLimit + 1];
			j = 0;
			day= new JComboBox<String>(monthDays);
			month= new JComboBox<String>(monthNames);
		}
		for(int i = currentYear; i >= currentYear - lowerLimit; i--)
		{
			years[j] = "" + i;
			j++;
		}
		year= new JComboBox<String>(years);
		month.addItemListener(this);
		year.addItemListener(this);
		day.setBorder(null);
		month.setBorder(null);
		year.setBorder(null);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,5);
		c.gridx = 0;
		c.weightx = 1.0;
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
		if(!hasWildcard)
			return day.getSelectedIndex() + 1;
		else 
		{
			if(day.getSelectedIndex() == 0)
				return -1;
		}
		return day.getSelectedIndex();
	}
	
	public int getMonth()
	{
		if(hasWildcard && month.getSelectedIndex() == 0)
			return -1;
		return month.getSelectedIndex();
	}
	
	public int getYear()
	{
		if(!hasWildcard || (hasWildcard && year.getSelectedIndex() != 0))
			return Integer.parseInt((String)year.getSelectedItem());
		return -1;
		
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
				String selectedMonth = (String)month.getSelectedItem();
				if(selectedMonth.equals("January") || selectedMonth.equals("March")|| selectedMonth.equals("May")|| 
						selectedMonth.equals("July") || selectedMonth.equals("August")|| selectedMonth.equals("October")||
						selectedMonth.equals("December"))
				{
					int max = 32;
					int i = 0;
					if(!hasWildcard)
					{
						max = 31;
						i = day.getItemCount() + 1;
					}
					else i = day.getItemCount();
					String d = "" + i;
					while(day.getItemCount() < max)
					{
						day.addItem(d);
						i++;
						d = "" + i;
					}
				}
				else if(selectedMonth.equals("April")|| selectedMonth.equals("June") || 
						selectedMonth.equals("September") || selectedMonth.equals("November"))
				{
					int max = 31;
					if(!hasWildcard)
						max = 30;
					
					if(day.getItemCount() == max + 1)
						day.removeItemAt(max);
					else
					{
						int i = day.getItemCount() + 1;
						if(hasWildcard)
							i -= 1;
						String d = "" + i;
						while(day.getItemCount() < max)
						{
							day.addItem(d);
							i++;
							d = "" + i;
						}
					}
				}
				else if(selectedMonth.equals("February"))
				{
					if(!hasWildcard)
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
					else
					{
						if(year.getSelectedIndex() != 0)
						{
							int selectedYear = Integer.parseInt((String)year.getSelectedItem());
							if(selectedYear % 4 == 0 || (selectedYear % 100 == 0 && selectedYear % 400 == 0))
							{
								if(day.getItemCount() == 32)
									day.removeItemAt(31);
								if(day.getItemCount() == 31)
									day.removeItemAt(30);
								if(day.getItemCount() == 30)
									return;
								
								int i = day.getItemCount() + 1;
								String d = "" + i;
								while(day.getItemCount() < 30)
								{
									day.addItem(d);
									i++;
									d = "" + i;
								}
							}
							else
							{
								if(day.getItemCount() == 32)
									day.removeItemAt(31);
								if(day.getItemCount() == 31)
									day.removeItemAt(30);
								if(day.getItemCount() == 30)
									day.removeItemAt(29);
							}
						}
						else
						{
							if(day.getItemCount() == 32)
								day.removeItemAt(31);
							if(day.getItemCount() == 31)
								day.removeItemAt(30);
							if(day.getItemCount() == 30)
								day.removeItemAt(29);
						}
					}
				}
			}
			else if(source == year)
			{
				if((hasWildcard && year.getSelectedIndex() != 0) || !hasWildcard)
				{
					int feb = 1;
					if(hasWildcard)
						feb = 2;
					if(month.getSelectedIndex() == feb)
					{
						int selectedYear = Integer.parseInt((String)year.getSelectedItem());
						if(selectedYear % 4 == 0 || (selectedYear % 100 == 0 && selectedYear % 400 == 0))
						{
							if(!hasWildcard)
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
								if(day.getItemCount() == 32)
									day.removeItemAt(31);
								if(day.getItemCount() == 31)
									day.removeItemAt(30);
								if(day.getItemCount() == 30)
									return;
								
								int i = day.getItemCount();
								String d = "" + i;
								while(day.getItemCount() < 30)
								{
									day.addItem(d);
									i++;
									d = "" + i;
								}
							}
						}
						else
						{
							if(!hasWildcard)
							{
								if(day.getItemCount() == 31)
									day.removeItemAt(30);
								if(day.getItemCount() == 30)
									day.removeItemAt(29);
								if(day.getItemCount() == 29)
									day.removeItemAt(28);
							}
							else
							{
								if(day.getItemCount() == 32)
									day.removeItemAt(31);
								if(day.getItemCount() == 31)
									day.removeItemAt(30);
								if(day.getItemCount() == 30)
									day.removeItemAt(29);
							}
						}
					}
				}
			}
		}
		
	}
	
	public void reset()
	{
		day.setSelectedIndex(0);
		month.setSelectedIndex(0);
		year.setSelectedIndex(0);
	}
}
