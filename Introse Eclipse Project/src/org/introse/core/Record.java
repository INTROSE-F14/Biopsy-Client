
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public abstract class Record 
{
	protected HashMap<String, Object> attributes;
	public static final String PATIENT = "Patient";//Patient
	public static final String REF_NUMBER = "Reference Number";//String
	public static final String SPECIMEN = "Specimen";//String
	public static final String PHYSICIAN = "Physician";//String
	public static final String PATHOLOGIST = "Pathologist";//String
	public static final String DIAGNOSIS = "Diagnosis";//String
	public static final String DATE_RECEIVED = "Date Received";//Calendar
	public static final String DATE_COMPLETED ="Date Completed";//Calendar
	public static final String REMARKS = "Remarks";//String
	public static final String RECORD_TYPE = "Record Type";//int
	public static final String LAST_MODIFY = "Last Date Modified";//Calendar
	
	
	public Record()
	{
		attributes = new HashMap<>();
	}
	
	public Record(Object refNumber)
	{
		attributes = new HashMap<>();
		attributes.put(Record.REF_NUMBER, refNumber);
	}
	
	public Record(HashMap<String, Object> attributes)
	{	
		this.attributes = attributes;
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
	
	public boolean matches(Record record)
	{
		if(record.getClass().equals(getClass()))
		{
			HashMap<String, Object> attributes = record.getHashMap();
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
					//checking for patient (two patients are equal if their IDs are equal)
					if(foreignValue instanceof Patient)
					{
						Patient p = (Patient)foreignValue;
						Patient p2 = (Patient)myValue;
						if(!p2.equals(p))
							return false;
					}
					//checking for calendar
					else if(foreignValue instanceof Calendar || foreignValue instanceof Date)
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
	public boolean equals(Object b)
	{
		if(!(getClass().equals(b.getClass())))
			return false;
		Record r = (Record)b;
		Object rId = r.getAttribute(Record.REF_NUMBER);
		Object myId = getAttribute(Record.REF_NUMBER);
		if(rId != null && myId != null)
			return rId.equals(myId);
		return super.equals(b);
	}
}
