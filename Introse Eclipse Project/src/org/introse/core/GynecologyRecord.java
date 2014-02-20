package org.introse.core;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.Calendar;

public class GynecologyRecord extends Record
{


	public GynecologyRecord(Patient patient, String referenceNumber,
			String specimen, String physician, String pathologist,
			Calendar dateReceived, Calendar dateCompleted, String diagnosis,
			String comments, int recordType) {
		super(patient, referenceNumber, specimen, physician, pathologist, dateReceived,
				dateCompleted, diagnosis, comments, recordType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pages) throws PrinterException 
	{
		return 0;
	}
	
}