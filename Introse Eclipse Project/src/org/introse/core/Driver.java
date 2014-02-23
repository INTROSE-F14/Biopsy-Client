import java.util.Calendar;



public class Driver {
	
	public static void main(String[] args)
	{
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(1995, 4, 21);
		Patient jrAquino = new Patient(2);
		Record record = new HistopathologyRecord("h2014-1234");
		record.putAttribute(Record.DATE_RECEIVED, c);
		
		
		Calendar c2 = Calendar.getInstance();
		c2.clear();
		c2.set(1995, 4, 21);
		Patient rjAquino = new Patient(1);
		Record record2 = new HistopathologyRecord("h2014-1234");
		record2.putAttribute(Record.PATIENT, rjAquino);
		record2.putAttribute(Record.DATE_RECEIVED, c2);
		
		System.out.println(record2.matches(record));
		
		
	}

}
