package org.introse.core.database;

import java.awt.*;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.List;

import org.introse.Constants.CategoriesConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.TitleConstants;
import org.introse.core.PrintedLine;
import org.introse.core.Record;
import org.introse.Constants;
import org.introse.core.Patient;
import org.introse.core.CustomCalendar;
import org.introse.core.Diagnosis;

public class Printer implements Printable{

	private int[] pageBreaks;
	private ArrayList<PrintedLine> al_lines;
	private Record record;
	
	public Printer(){
		this.al_lines = new ArrayList<PrintedLine>();
	}
	
	private ArrayList<String> parseString(ArrayList<String> list_strings, FontMetrics metrics, PageFormat pf){

		int width = (int) pf.getImageableWidth();
		
		String temp = list_strings.get(list_strings.size()-1);
		if(metrics.charsWidth(temp.toCharArray(), 0, temp.toCharArray().length) <= width)
			return list_strings;
		else{
			String[] container = temp.split(" ");
			int end = getIndexOfFittingString (container, metrics, pf);
			String temp2 = container[0];
			for(int i=1; i<end; i++){
				temp2 = temp2 + " " + container[i];
			}
			String temp3 = container[end];
			end++;
			for(int i = end; i<container.length;i++){
				temp3 = temp3 + " " + container[i];
			}
			int tempindex = list_strings.indexOf(temp);
			list_strings.set(tempindex, temp2);
			list_strings.add(temp3);
			return parseString(list_strings, metrics, pf);
		}
	}
	
	private int getIndexOfFittingString (String[] a_string, FontMetrics metrics, PageFormat pf){

		int width = (int) pf.getImageableWidth();
		int i = 0;
		String temp = a_string[i];
		while(metrics.charsWidth(temp.toCharArray(), 0, temp.toCharArray().length) <= width){
			i++;
			temp = temp + " " + a_string[i];
		}
		
		return i;
	}

	private PageFormat setFormat(PrinterJob printJob) {
		PageFormat pf = printJob.defaultPage();
		Paper p = pf.getPaper();
		p.setSize(8.5 * 72, 11 * 72);
		p.setImageableArea(0.5 * 72, 0.0 * 72, 7.5 * 72, 10.5 * 72);
		pf.setPaper(p);
		return pf;     
	}
	
	private void addHeader(){
		al_lines.add(null);
		al_lines.add(new PrintedLine("MANILA DOCTOR'S HOSPITAL", 1));
		al_lines.add(new PrintedLine("Department of Laboratory Medicine", 1));
		al_lines.add(new PrintedLine("667 U.N. Ave., Ermita, Manila", 1));
		al_lines.add(new PrintedLine("Tel. No. 5288108/ 5243011 loc. 8108", 1));
		al_lines.add(null);
	}
	
	private void initRecord(FontMetrics metrics, PageFormat pf){
		String spaceString = " ";
		int space = metrics.charsWidth(spaceString.toCharArray(), 0, spaceString.toCharArray().length);
		int width = (int) pf.getImageableWidth();
		
		//constants	
		String numberLabel;
		if(this.record != null){
			switch((char)this.record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
			case 'H': numberLabel = Constants.PrintConstants.HISTOPATHOLOGY_NUMBER_LABEL;
					break;
			case 'C': numberLabel = Constants.PrintConstants.CYTOLOGY_NUMBER_LABEL;
					break;
			case 'G': numberLabel = Constants.PrintConstants.GYNECOLOGY_NUMBER_LABEL;
					break;
			default: numberLabel = "";
			}
			
			String roomLabel = Constants.PrintConstants.ROOM_LABEL;
			String patientLabel = Constants.PrintConstants.PATIENT_NAME_LABEL;
			String ageLabel = Constants.PrintConstants.AGE_LABEL;
			String genderLabel = Constants.PrintConstants.GENDER_LABEL;
			String specimenLabel = Constants.PrintConstants.SPECIMEN_LABEL;
			String physicianLabel = Constants.PrintConstants.PHYSICIAN_LABEL;
			String dateReceivedLabel = Constants.PrintConstants.DATE_RECEIVED_LABEL;
			String dateCompletedLabel = Constants.PrintConstants.DATE_COMPLETED_LABEL;
			
			String title;
			
			switch((char)this.record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
			case 'H': title = Constants.PrintConstants.HISTOPATHOLOGY_TITLE;
					break;
			case 'C': title = Constants.PrintConstants.CYTOLOGY_TITLE;
					break;
			case 'G': title = Constants.PrintConstants.GYNECOLOGY_TITLE;
					break;
			default: title = "";
			}
		
			String commentsLabel = Constants.PrintConstants.COMMENTS_LABEL;;
			String descriptionLabel = Constants.PrintConstants.DESCRIPTION_LABEL;;
			
	
			//present in all records
			int recordnumber = (int) this.record.getAttribute(Constants.RecordTable.RECORD_NUMBER);
			int numZeroes;
			if(recordnumber > 999)
				numZeroes = 4;
			else if(recordnumber > 99)
				numZeroes = 3;
			else if(recordnumber > 9)
				numZeroes = 2;
			else numZeroes = 1;
			String refNumber = "" + recordnumber;
			for(int i = numZeroes; i < 4; i++)
			{
				refNumber = "0" + refNumber;
			}
			String number = (char)this.record.getAttribute(Constants.RecordTable.RECORD_TYPE) + (int) this.record.getAttribute(Constants.RecordTable.RECORD_YEAR) + "-" + refNumber;
			
			String room;
			if(this.record.getAttribute(Constants.RecordTable.ROOM) == null){
				room = "N/A";
			}
			else{
				room = (String) this.record.getAttribute(Constants.RecordTable.ROOM);
			}
			
			String patientName = ((String)((Patient) this.record.getAttribute(Constants.RecordTable.PATIENT)).getAttribute(Constants.PatientTable.LAST_NAME)).toUpperCase() + ", " + 
								 (String)((Patient) this.record.getAttribute(Constants.RecordTable.PATIENT)).getAttribute(Constants.PatientTable.FIRST_NAME) + " " +
								 (String)((Patient) this.record.getAttribute(Constants.RecordTable.PATIENT)).getAttribute(Constants.PatientTable.MIDDLE_NAME);
								 
			String age = ((CustomCalendar)((Patient)this.record.getAttribute(Constants.RecordTable.PATIENT)).getAttribute(Constants.PatientTable.BIRTHDAY)).getAge() + "";
			
			String gender = ((String)((Patient)this.record.getAttribute(Constants.RecordTable.PATIENT)).getAttribute(Constants.PatientTable.GENDER));
			
			String specimen;
			
			switch((char)this.record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
			case 'G':	specimen = (String)this.record.getAttribute(Constants.RecordTable.SPEC_TYPE);
						break;
			default: specimen = (String)this.record.getAttribute(Constants.RecordTable.SPECIMEN);
			
			}
			
			
			String physician = (String)this.record.getAttribute(Constants.RecordTable.PHYSICIAN);
			String dateReceived = this.record.getAttribute(Constants.RecordTable.DATE_RECEIVED).toString();;
			String dateCompleted = this.record.getAttribute(Constants.RecordTable.DATE_COMPLETED).toString();
			
			List<Diagnosis> l_diagnosis = (List)record.getAttribute(RecordTable.DIAGNOSIS);
			
			String comments = (String) this.record.getAttribute(Constants.RecordTable.REMARKS);
			String description = (String) this.record.getAttribute(Constants.RecordTable.GROSS_DESC) + "\n" + (String) this.record.getAttribute(Constants.RecordTable.MICRO_NOTE);				
			
			String numberCombo = numberLabel + number;
			String roomCombo = roomLabel + room;
			int numberLength = metrics.charsWidth(numberCombo.toCharArray(), 0, numberCombo.toCharArray().length);
			int roomLength = metrics.charsWidth(roomCombo.toCharArray(), 0, roomCombo.toCharArray().length);
			int line1Space = width - numberLength - roomLength;
			int numSpaces1 = line1Space/space;
			
			for(int i=0; i<numSpaces1; i++){
				numberCombo = numberCombo + " ";
			}
			al_lines.add(new PrintedLine(numberCombo + roomCombo, 0));
			
			String patientCombo = patientLabel + patientName;
			String ageCombo = ageLabel + age;
			String genderCombo = genderLabel + gender;
			int patientLength = metrics.charsWidth(patientCombo.toCharArray(), 0, patientCombo.toCharArray().length);
			int ageLength = metrics.charsWidth(ageCombo.toCharArray(), 0, ageCombo.toCharArray().length);
			int genderLength = metrics.charsWidth(genderCombo.toCharArray(), 0, genderCombo.toCharArray().length);
			int line2Space = (width - patientLength - ageLength - genderLength)/2;
			int numSpaces2 = line2Space/space;
			
			for(int i=0; i<numSpaces2; i++){
				patientCombo = patientCombo + " ";
			}
			patientCombo = patientCombo + ageCombo;
			for(int i=0; i<numSpaces2; i++){
				patientCombo = patientCombo + " ";
			}
			al_lines.add(new PrintedLine(patientCombo + genderCombo, 0));
			
			String specimenCombo = specimenLabel + specimen;
			String physicianCombo = physicianLabel + physician;
			int specimenLength = metrics.charsWidth(specimenCombo.toCharArray(), 0, specimenCombo.toCharArray().length);
			int physicianLength = metrics.charsWidth(physicianCombo.toCharArray(), 0, physicianCombo.toCharArray().length);
			int line3Space = width - physicianLength - specimenLength;
			int numSpaces3 = line3Space/space;
			
			for(int i=0; i<numSpaces3; i++){
				specimenCombo = specimenCombo + " ";
			}
			al_lines.add(new PrintedLine(specimenCombo + physicianCombo, 0));
			
			String dRCombo = dateReceivedLabel + dateReceived;
			String dCCombo = dateCompletedLabel + dateCompleted;
			int dRLength = metrics.charsWidth(dRCombo.toCharArray(), 0, dRCombo.toCharArray().length);
			int dCLength = metrics.charsWidth(dCCombo.toCharArray(), 0, dCCombo.toCharArray().length);
			int line4Space = width - dRLength - dCLength;
			int numSpaces4 = line4Space/space;
			
			for(int i=0; i<numSpaces4; i++){
				dRCombo = dRCombo + " ";
			}
			al_lines.add(new PrintedLine(dRCombo + dCCombo, 0));
	
			al_lines.add(null);
			al_lines.add(new PrintedLine(title, 1));
			al_lines.add(null);
			
			switch((char)this.record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
			case 'H':	al_lines.add(new PrintedLine(Constants.PrintConstants.DIAGNOSIS_LABEL_H,0));
						al_lines.add(null);
						break;
			case 'C':	al_lines.add(new PrintedLine(Constants.PrintConstants.DIAGNOSIS_LABEL_C,0));
						al_lines.add(null);
						break;
			case 'G' :	break;
			
			}
			
			switch((char)this.record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
			case 'G': this.addGynecologyDiagnosis(l_diagnosis, metrics, pf);
					break;
			default: this.addHistoCytoDiagnosis(l_diagnosis, metrics, pf);
			}
	
			al_lines.add(null);
			al_lines.add(new PrintedLine(commentsLabel, 0));
			al_lines.add(null);
			
			String[] a_comments = comments.split("\n");
			
			for(int j=0;j<a_comments.length;j++){
				ArrayList<String> al_comments = new ArrayList<String>();
				al_comments.add(a_comments[j]);
				al_comments = parseString(al_comments, metrics, pf);
				
				for(int i=0; i<al_comments.size();i++){
					al_lines.add(new PrintedLine(al_comments.get(i), 0));
				}
			}
			
			al_lines.add(null);
			al_lines.add(new PrintedLine(descriptionLabel,0));
			al_lines.add(null);
			
			String[] a_description = description.split("\n");
			
			for(int j=0;j<a_description.length;j++){
				ArrayList<String> al_description = new ArrayList<String>();
				al_description.add(a_description[j]);
				al_description = parseString(al_description, metrics, pf);
				
				for(int i=0; i<al_description.size();i++){
					al_lines.add(new PrintedLine(al_description.get(i), 0));
				}
			}
			al_lines.add(null);
		}
		
	}
	
	private void addSignature(){
		String pathologistLabel = Constants.PrintConstants.PATHOLOGIST_LABEL;
		String pathologist = (String) record.getAttribute(Constants.RecordTable.PATHOLOGIST);
		al_lines.add(new PrintedLine("_____________________", 2));
		al_lines.add(new PrintedLine(pathologist, 2));
		al_lines.add(new PrintedLine(pathologistLabel, 2));
	}
	
	private void addHistoCytoDiagnosis(List<Diagnosis> l_diagnosis, FontMetrics metrics, PageFormat pf){
		String diagnosis = l_diagnosis.get(0).getValue();
		String[] a_diagnosis = diagnosis.split("\n");
		
		for(int j=0;j<a_diagnosis.length;j++){
			ArrayList<String> al_diagnosis = new ArrayList<String>();
			al_diagnosis.add(a_diagnosis[j]);
			al_diagnosis = parseString(al_diagnosis, metrics, pf);
			
			for(int i=0; i<al_diagnosis.size();i++){
				this.al_lines.add(new PrintedLine(al_diagnosis.get(i), 0));
			}
		}
	}
	
	private void addGynecologyDiagnosis(List<Diagnosis> l_diagnosis, FontMetrics metrics, PageFormat pf){
		String diagnosis = "";
		int cat = -1;
		for(int i=0; i<l_diagnosis.size();i++){
			int category = l_diagnosis.get(i).getCategory();
			String value = l_diagnosis.get(i).getValue();
			
			switch(category)
			{
			case Constants.CategoriesConstants.ORGANISMS: 
			case Constants.CategoriesConstants.ONF:
			case Constants.CategoriesConstants.OTHER:	
			case Constants.CategoriesConstants.SC:
			case Constants.CategoriesConstants.GC: 		
			case Constants.CategoriesConstants.OMN: 
				if(category == Constants.CategoriesConstants.ORGANISMS||category == Constants.CategoriesConstants.ONF||category == Constants.CategoriesConstants.OTHER){
					diagnosis = diagnosis + "[X] " + Constants.PrintConstants.NILM;
					cat = 0;
					if(category == Constants.CategoriesConstants.ORGANISMS){
						diagnosis = diagnosis + "\n      [X] " + Constants.PrintConstants.ORGANISMS;
						diagnosis = diagnosis + "\n              -" + value;
					}
					else if (category == Constants.CategoriesConstants.ONF){
						diagnosis = diagnosis + "\n      [X] " + Constants.PrintConstants.ONF;
						diagnosis = diagnosis + "\n              -" + value;
					}
					else{
						diagnosis = diagnosis + "\n      [X] " + Constants.PrintConstants.OTHER;
						diagnosis = diagnosis + "\n              -" + value;
					}
				}
				else if(category == CategoriesConstants.SC){
					cat = 1;
					diagnosis = diagnosis + "      [X] " + Constants.PrintConstants.SQUAMOUS;
					diagnosis = diagnosis + "\n              -" + value;
				}
				else if(category == CategoriesConstants.GC){
					cat = 2;
					diagnosis = diagnosis + "      [X] " + Constants.PrintConstants.GLANDULAR;
					diagnosis = diagnosis + "\n              -" + value;
				}
				else if(category == CategoriesConstants.OMN){
					cat = 3;
					diagnosis = diagnosis + "      [X] " + Constants.PrintConstants.OMN;
					diagnosis = diagnosis + "\n              -" + value;
				}
			break;
			case Constants.CategoriesConstants.SA: 
				al_lines.add(new PrintedLine(Constants.PrintConstants.SPEC_ADEQ, 0));
				if(value.contains(TitleConstants.SATISFACTORY)){
					al_lines.add(new PrintedLine("      [X] " + value.toUpperCase(), 0));
					al_lines.add(new PrintedLine("      [ ] " + Constants.PrintConstants.UNSATIS, 0));
				}
				else if(value.contains(TitleConstants.UNSATISFACTORY))
				{
					al_lines.add(new PrintedLine("      [ ] " + Constants.PrintConstants.SATIS, 0));
					al_lines.add(new PrintedLine("      [X] " + value.toUpperCase(), 0));
				}
				
				
			}
		}
		al_lines.add(null);
		al_lines.add(new PrintedLine(Constants.PrintConstants.INTER_RES,1));
		al_lines.add(null);
		String[] a_diagnosis;
		switch(cat){
		case -1: 	al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.NILM,0));
					al_lines.add(null);
					al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.SQUAMOUS,0));
					al_lines.add(null);
					al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.GLANDULAR,0));
					al_lines.add(null);
					al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.OMN,0));
					break;
		case 0:  a_diagnosis = diagnosis.split("\n"); 
				for(int i =0; i<a_diagnosis.length; i++){
					ArrayList<String> al_diagnosis = new ArrayList<String>();
					al_diagnosis.add(a_diagnosis[i]);
					al_diagnosis = parseString(al_diagnosis, metrics, pf);
					
					for(int j=0; j<al_diagnosis.size();j++){
						al_lines.add(new PrintedLine(al_diagnosis.get(j), 0));
					}
				}
				al_lines.add(null);
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.SQUAMOUS,0));
				al_lines.add(null);
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.GLANDULAR,0));
				al_lines.add(null);
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.OMN,0));
				break;
		case 1: a_diagnosis = diagnosis.split("\n"); 
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.NILM,0));
				al_lines.add(null);
				for(int i =0; i<a_diagnosis.length; i++){
					ArrayList<String> al_diagnosis = new ArrayList<String>();
					al_diagnosis.add(a_diagnosis[i]);
					al_diagnosis = parseString(al_diagnosis, metrics, pf);
			
					for(int j=0; j<al_diagnosis.size();j++){
						al_lines.add(new PrintedLine(al_diagnosis.get(j), 0));
					}
					
				}
				al_lines.add(null);
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.GLANDULAR,0));
				al_lines.add(null);
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.OMN,0));
				break;
		case 2: a_diagnosis = diagnosis.split("\n"); 
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.NILM,0));
				al_lines.add(null);
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.SQUAMOUS,0));
				al_lines.add(null);
				for(int i =0; i<a_diagnosis.length; i++){
					ArrayList<String> al_diagnosis = new ArrayList<String>();
					al_diagnosis.add(a_diagnosis[i]);
					al_diagnosis = parseString(al_diagnosis, metrics, pf);
					
					for(int j=0; j<al_diagnosis.size();j++){
						al_lines.add(new PrintedLine(al_diagnosis.get(j), 0));
					}
				}
				al_lines.add(null);
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.OMN,0));
				break;
		case 3: a_diagnosis = diagnosis.split("\n"); 
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.NILM,0));
				al_lines.add(null);
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.SQUAMOUS,0));
				al_lines.add(null);
				al_lines.add(new PrintedLine("[ ] "+Constants.PrintConstants.GLANDULAR,0));
				al_lines.add(null);
				for(int i =0; i<a_diagnosis.length; i++){
					ArrayList<String> al_diagnosis = new ArrayList<String>();
					al_diagnosis.add(a_diagnosis[i]);
					al_diagnosis = parseString(al_diagnosis, metrics, pf);
			
					for(int j=0; j<al_diagnosis.size();j++){
						al_lines.add(new PrintedLine(al_diagnosis.get(j), 0));
					}
				}
		}
	}
	
	public void startPrint(Record record){
		this.al_lines.clear();
		this.record = record;
		PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this, setFormat(job));
		 boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              /* The job did not successfully complete */
             }
         }
	}
	
	public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
		
        Font font = new Font("Courier", Font.PLAIN, 12);
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();

        if (pageBreaks == null) {
        	this.addHeader();
            this.initRecord(metrics, pf);
            this.addSignature();
            int linesPerPage = (int)(pf.getImageableHeight()/lineHeight);
            int numBreaks = (al_lines.size()-1)/linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b=0; b<numBreaks; b++) {
                pageBreaks[b] = (b+1)*linesPerPage; 
            }
        }

        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        int y = 0; 
        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
        int end   = (pageIndex == pageBreaks.length)
                         ? al_lines.size() : pageBreaks[pageIndex];
						 
        for (int line=start; line<end; line++) {
            y += lineHeight;
			if(al_lines.get(line) == null){
				continue;
			}
			else if(al_lines.get(line).getAlign() == 0){
				g.drawString(al_lines.get(line).getString(), 0, y);
			}
			else if(al_lines.get(line).getAlign() == 1){
				char[] a = al_lines.get(line).getString().toCharArray();
				int half = metrics.charsWidth(a, 0, a.length)/2;
				int center = (int) pf.getImageableWidth()/2;
				int x = center - half;
				g.drawString(al_lines.get(line).getString(), x, y);
			}
			else{
				char[] a = al_lines.get(line).getString().toCharArray();
				int x = (int) pf.getImageableWidth() - metrics.charsWidth(a, 0, a.length);
				g.drawString(al_lines.get(line).getString(), x, y);
			}
        }

        return PAGE_EXISTS;
    } 

}