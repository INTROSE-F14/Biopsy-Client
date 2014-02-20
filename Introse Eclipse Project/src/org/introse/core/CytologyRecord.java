package org.introse.core;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.Calendar;

public class CytologyRecord extends Record
{

		public CytologyRecord(Patient patient, String referenceNumber,
			String specimen, String physician, String pathologist,
			Calendar dateReceived, Calendar dateCompleted, String diagnosis,
			String comments, int recordType) 
		{
			super(patient, referenceNumber, specimen, physician, pathologist, dateReceived,
				dateCompleted, diagnosis, comments, recordType);
		
		}

		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
				throws PrinterException {
			// TODO Auto-generated method stub
			return 0;
		}
}