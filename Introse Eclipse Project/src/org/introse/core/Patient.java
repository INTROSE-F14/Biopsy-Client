
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Patient
{
	private HashMap<String, Object> attributes;
	public static final String ID = "Id";
	public static final String LAST_NAME = "Last name";
	public static final String FIRST_NAME = "First name";
	public static final String MIDDLE_NAME = "Middle name";
	public static final String GENDER = "Gender";
	public static final String BIRTHDAY = "Birthday";
	public static final String ROOM = "Room";
	private List<Record> records;
	
	public Patient()
	{
		attributes = new HashMap<>();
		records = new Vector<Record>();
	}
	
	public Patient(Object ID)
	{
		attributes = new HashMap<>();
		attributes.put(Patient.ID, ID);
		records = new Vector<Record>();
	}
	
	public Patient(HashMap<String, Object> attributes)
	{	
		this.attributes = attributes;
		records = new Vector<Record>();
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
	

	public Object getAttribute(String key)
	{
		return attributes.get(key);
	}
	
	public void putAttribute(String key, Object newValue)
	{
		if(attributes.containsKey(key))
		attributes.remove(attributes.get(key));
		attributes.put(key, newValue);
	}
	
	public HashMap<String, Object> getHashMap()
	{
		return attributes;
	}
	
	public boolean matches(Patient patient)
	{
		if(patient.getClass().equals(getClass()))
		{
			HashMap<String, Object> attributes = patient.getHashMap();
			Set<String> keys = attributes.keySet();
			Iterator<String> i = keys.iterator();
			while(i.hasNext())
			{
				String currentKey = i.next();
				System.out.println(currentKey);
				Object foreignValue = attributes.get(currentKey);
				Object myValue = this.attributes.get(currentKey);
				
				if(myValue != null)
				{
					
					//checking for calendar
					if(foreignValue instanceof Calendar || foreignValue instanceof Date)
					{
						if(!myValue.toString().equals(foreignValue.toString()))
							return false;
					}
					else if(foreignValue instanceof String)
					{
						if(!(((String)myValue).equalsIgnoreCase((String)foreignValue)))
							return false;
					}
					//checking for primitive values
					else
					{
						if(!myValue.equals(foreignValue))
							return false;
					}
				}
				else return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Patient))
			return false;
		Patient p = (Patient)o;
		Object pId = p.getAttribute(Patient.ID);
		Object myId = getAttribute(Patient.ID);
		if(pId != null && myId != null)
			return pId.equals(myId);
		return super.equals(o);
	}
}
