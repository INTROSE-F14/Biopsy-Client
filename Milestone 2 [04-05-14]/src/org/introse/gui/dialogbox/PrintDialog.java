package org.introse.gui.dialogbox;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.introse.Constants;
import org.introse.Constants.CategoriesConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomCalendar;
import org.introse.core.Diagnosis;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;

public class PrintDialog extends JDialog implements ActionListener{

	private JButton btn_print, btn_exit;
	private JTextPane tp_textpane;
	private JScrollPane sp_scrollpane;	
	private StyledDocument sd_doc;
		
	public PrintDialog(Record record){
	
		setTitle("Print Dialog");
		setSize(800,630);

		//Center
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getSize();
		screenSize.height = screenSize.height/2;
		screenSize.width = screenSize.width/2;
		size.height = size.height/2;
		size.width = size.width/2;
		int y = screenSize.height - size.height;
		int x = screenSize.width - size.width;

		setLocation(x, y);
		setResizable(false);
		setModal(true);
		setLayout(null);

		this.tp_textpane= new JTextPane();
		this.sd_doc = this.tp_textpane.getStyledDocument();
		this.tp_textpane.setFont(new Font("Courier", Font.PLAIN, 12));
		this.tp_textpane.setEditable(false);

		this.sp_scrollpane = new JScrollPane( this.tp_textpane );
        this.sp_scrollpane.setPreferredSize( new Dimension( 730, 450 ) );
		this.sp_scrollpane.setSize(730,450);
		this.sp_scrollpane.setLocation(30,30);
		this.sp_scrollpane.setBorder(BorderFactory.createLoweredBevelBorder());
        this.add( this.sp_scrollpane );
		
		this.btn_print = new JButton("Print");
		this.btn_print.setSize(150,25);
		this.btn_print.setLocation(610,485);
		this.btn_print.addActionListener(this);
		this.add(this.btn_print);

		this.btn_exit = new JButton("Back to Main Menu");
		this.btn_exit.setSize(160,25);
		this.btn_exit.setLocation(320,535);
		this.btn_exit.addActionListener(this);
		this.add(this.btn_exit);
		
		this.generateForm(record);
		this.setVisible(true);
	}
	
	public void addListener(CustomListener listener){
		btn_print.addActionListener(listener);
		btn_print.setActionCommand(Constants.ActionConstants.PRINT);
		btn_exit.addActionListener(listener);
	}
	
	private void centerAlign(){
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		sd_doc.setParagraphAttributes(sd_doc.getLength()+1, sd_doc.getLength(), center, false);
	}
	
	private void leftAlign(){
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_JUSTIFIED);
		sd_doc.setParagraphAttributes(sd_doc.getLength()+1, sd_doc.getLength(), left, false);
	}
	
	private void rightAlign(){
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		sd_doc.setParagraphAttributes(sd_doc.getLength()+1, sd_doc.getLength(), right, false);
	}
	
	public void generateForm(Record record){
		String numberLabel = "";
		this.clear();
		if(record != null){
			switch((char)record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
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
			String pathologistLabel = Constants.PrintConstants.PATHOLOGIST_LABEL;
			String dateReceivedLabel = Constants.PrintConstants.DATE_RECEIVED_LABEL;
			String dateCompletedLabel = Constants.PrintConstants.DATE_COMPLETED_LABEL;
			
			String title;
			
			switch((char)record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
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
			int recordnumber = (int) record.getAttribute(Constants.RecordTable.RECORD_NUMBER);
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
			String number = (char)record.getAttribute(Constants.RecordTable.RECORD_TYPE) + ("" + (int) record.getAttribute(Constants.RecordTable.RECORD_YEAR)) + "-" + refNumber;
			
			String room;
			if(record.getAttribute(Constants.RecordTable.ROOM) == null){
				room = "N/A";
			}
			else{
				room = (String) record.getAttribute(Constants.RecordTable.ROOM);
			}
			
			String patientName = ((String)((Patient) record.getAttribute(Constants.RecordTable.PATIENT)).getAttribute(Constants.PatientTable.LAST_NAME)).toUpperCase() + ", " + 
								 (String)((Patient) record.getAttribute(Constants.RecordTable.PATIENT)).getAttribute(Constants.PatientTable.FIRST_NAME) + " " +
								 (String)((Patient) record.getAttribute(Constants.RecordTable.PATIENT)).getAttribute(Constants.PatientTable.MIDDLE_NAME);
								 
			String age = ((CustomCalendar)((Patient) record.getAttribute(Constants.RecordTable.PATIENT)).getAttribute(Constants.PatientTable.BIRTHDAY)).getAge() + "";
			
			String gender = ((String)((Patient) record.getAttribute(Constants.RecordTable.PATIENT)).getAttribute(Constants.PatientTable.GENDER));
			
			String specimen;
			
			switch((char) record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
			case 'G':	specimen = (String) record.getAttribute(Constants.RecordTable.SPEC_TYPE);
						break;
			default: specimen = (String) record.getAttribute(Constants.RecordTable.SPECIMEN);
			
			}
			
			String pathologist = (String) record.getAttribute(Constants.RecordTable.PATHOLOGIST);
			String physician = (String) record.getAttribute(Constants.RecordTable.PHYSICIAN);
			String dateReceived =  record.getAttribute(Constants.RecordTable.DATE_RECEIVED).toString();;
			String dateCompleted = record.getAttribute(Constants.RecordTable.DATE_COMPLETED).toString();
			
			List<Diagnosis> l_diagnosis = (List)record.getAttribute(RecordTable.DIAGNOSIS);
			
			String comments = (String) record.getAttribute(Constants.RecordTable.REMARKS);
			String description = (String) record.getAttribute(Constants.RecordTable.GROSS_DESC) + "\n\n" + (String) record.getAttribute(Constants.RecordTable.MICRO_NOTE);				
			
			MutableAttributeSet mas_bold = new SimpleAttributeSet();
			MutableAttributeSet mas_boldunderline = new SimpleAttributeSet();
			StyleConstants.setBold(mas_bold, true);
			StyleConstants.setBold(mas_boldunderline, true);
			StyleConstants.setUnderline(mas_boldunderline, true);
			
			try{
				this.addHeader();
				this.leftAlign();
				sd_doc.insertString(sd_doc.getLength(), String.format("\n" + numberLabel + number + getSpaces(numberLabel+number,roomLabel+room) + roomLabel + room), null);
				sd_doc.insertString(sd_doc.getLength(), String.format("\n" + patientLabel + patientName + getSpaces(patientLabel+patientName,ageLabel+age, genderLabel+gender) + ageLabel + age + getSpaces(patientLabel+patientName,ageLabel+age, genderLabel+gender) + genderLabel + gender), null);
				sd_doc.insertString(sd_doc.getLength(), String.format("\n" + specimenLabel + specimen + getSpaces(specimenLabel+specimen,physicianLabel+physician) + physicianLabel + physician), null);
				sd_doc.insertString(sd_doc.getLength(), String.format("\n" + dateReceivedLabel + dateReceived + getSpaces(dateReceivedLabel+dateReceived,dateCompletedLabel+dateCompleted) + dateCompletedLabel + dateCompleted), null);
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				this.centerAlign();
				sd_doc.insertString(sd_doc.getLength(), "\n"+title, mas_boldunderline);
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				this.leftAlign();
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				switch((char)record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
				case 'G': this.addGynecologyDiagnosis(l_diagnosis);
						break;
				default: this.addHistoCytoDiagnosis(l_diagnosis);
				}
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				sd_doc.insertString(sd_doc.getLength(),"\n" + commentsLabel, null);
				sd_doc.insertString(sd_doc.getLength(), "\n" + comments, null);
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				sd_doc.insertString(sd_doc.getLength(), "\n" + descriptionLabel, null);
				sd_doc.insertString(sd_doc.getLength(), "\n" + description, null);
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				this.rightAlign();
				this.addSignature(pathologistLabel, pathologist);
			}
			catch(Exception e){
				
			}
		}
	}
	
	private String getSpaces(String s1, String s2){
		FontMetrics fm = this.tp_textpane.getFontMetrics(tp_textpane.getFont());
		int width = sp_scrollpane.getWidth()-36;
		int length1 = fm.stringWidth(s1);
		int length2 = fm.stringWidth(s2);
		int space = fm.charWidth(' ');
		int numSpaces = (width-length1-length2)/space;
		String longSpace = "";
		for(int i=0; i<numSpaces;i++){
			longSpace = longSpace + " ";
		}
		return longSpace;
	}
	
	private String getSpaces(String s1, String s2, String s3){
		FontMetrics fm = this.tp_textpane.getFontMetrics(tp_textpane.getFont());
		int width = sp_scrollpane.getWidth()-36;
		int length1 = fm.stringWidth(s1);
		int length2 = fm.stringWidth(s2);
		int length3 = fm.stringWidth(s3);
		int space = fm.charWidth(' ');
		int numSpaces = ((width-length1-length2-length3)/2)/space;
		String longSpace = "";
		System.out.println("Width: " + width + "\nLength 1: " + length1 + "\nLength 2: " + length2 + "\nNumSpaces: " + numSpaces);
		for(int i=0; i<numSpaces;i++){
			longSpace = longSpace + " ";
		}
		return longSpace;
		
	}
	
	private void clear(){
		try{
			this.sd_doc.remove(0,sd_doc.getLength());
		}
		catch(Exception e){
			
		}
	}
	
	private void addHeader(){
		MutableAttributeSet mas_bold = new SimpleAttributeSet();
		MutableAttributeSet mas_boldunderline = new SimpleAttributeSet();
		StyleConstants.setBold(mas_bold, true);
		StyleConstants.setBold(mas_boldunderline, true);
		StyleConstants.setUnderline(mas_boldunderline, true);
	try{
		this.centerAlign();
		sd_doc.insertString(sd_doc.getLength(), String.format("MANILA DOCTOR'S HOSPITAL"), mas_boldunderline );
		sd_doc.insertString(sd_doc.getLength(), String.format("\nDepartment of Laboratory Medicine"), mas_bold );
		sd_doc.insertString(sd_doc.getLength(), String.format("\n667 U.N. Ave., Ermita, Manila"), mas_bold );
		sd_doc.insertString(sd_doc.getLength(), String.format("\nTel. No. 5288108/ 5243011 loc. 8108"), mas_bold );
		sd_doc.insertString(sd_doc.getLength(), "\n", null);
	}
	catch (Exception ex){
		System.out.println(ex); 
	}
}
	
	private void addHistoCytoDiagnosis(List<Diagnosis> l_diagnosis){
		try{
		sd_doc.insertString(sd_doc.getLength(), "\n" + l_diagnosis.get(0).getValue(), null);
		}
		catch(Exception e){
			
		}
	}
	
	private void addGynecologyDiagnosis(List<Diagnosis> l_diagnosis){
		MutableAttributeSet mas_boldunderline = new SimpleAttributeSet();
		StyleConstants.setBold(mas_boldunderline, true);
		StyleConstants.setUnderline(mas_boldunderline, true);
		try{
			String diagnosis = "";
			int cat = -1;
			for(int i=0; i<l_diagnosis.size();i++){
				int category = l_diagnosis.get(i).getCategory();
				String value = l_diagnosis.get(i).getValue();
				
				switch(category){
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
						diagnosis = diagnosis + "[X] " + Constants.PrintConstants.SQUAMOUS;
						diagnosis = diagnosis + "\n              -" + value;
					}
					else if(category == CategoriesConstants.GC){
						cat = 2;
						diagnosis = diagnosis + "[X] " + Constants.PrintConstants.GLANDULAR;
						diagnosis = diagnosis + "\n              -" + value;
					}
					else if(category == CategoriesConstants.OMN){
						cat = 3;
						diagnosis = diagnosis + "[X] " + Constants.PrintConstants.OMN;
						diagnosis = diagnosis + "\n              -" + value;
					}
				break;
				case Constants.CategoriesConstants.SA: 
					sd_doc.insertString(sd_doc.getLength(),Constants.PrintConstants.SPEC_ADEQ, null);
					if(value.contains(TitleConstants.SATISFACTORY)){
						sd_doc.insertString(sd_doc.getLength(), "\n" + "      [X] " + value.toUpperCase(), null);
						sd_doc.insertString(sd_doc.getLength(), "\n" + "      [ ] " + Constants.PrintConstants.UNSATIS, null);
					}
					else if(value.contains(TitleConstants.UNSATISFACTORY))
					{
						sd_doc.insertString(sd_doc.getLength(), "\n" + "      [ ] " + Constants.PrintConstants.SATIS, null);
						sd_doc.insertString(sd_doc.getLength(), "\n" + "      [X] " + value.toUpperCase(), null);
					}
								
				}
			}
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				this.centerAlign();
				sd_doc.insertString(sd_doc.getLength(), "\n" + Constants.PrintConstants.INTER_RES, mas_boldunderline);
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				this.leftAlign();
				switch(cat){
				case -1: 	sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.NILM, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.SQUAMOUS, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.GLANDULAR, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.OMN, null);
							break;
				case 0: 	
							sd_doc.insertString(sd_doc.getLength(), "\n" + diagnosis, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.SQUAMOUS, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.GLANDULAR, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.OMN, null);
							break;
				case 1: 	
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.NILM, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + diagnosis, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.GLANDULAR, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.OMN, null);
							break;
				case 2: 	
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.NILM, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.SQUAMOUS, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + diagnosis, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.OMN, null);
							break;
				case 3: 	
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.NILM, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.SQUAMOUS, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + "[ ] "+Constants.PrintConstants.GLANDULAR, null);
							sd_doc.insertString(sd_doc.getLength(), "\n", null);
							sd_doc.insertString(sd_doc.getLength(), "\n" + diagnosis, null);
					}
		
		}
		catch(Exception e){
		
		}
	}
	
	private void addSignature(String pathologistLabel, String pathologist){
		
		try{
			sd_doc.insertString(sd_doc.getLength(), "\n" + "_____________________", null);
			sd_doc.insertString(sd_doc.getLength(), "\n" + pathologist, null);
			sd_doc.insertString(sd_doc.getLength(), "\n" + pathologistLabel, null);
		}
		catch(Exception e){
			
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_print){
			Thread printThread = new Thread("Print thread") {
				public void run() {
					try {
						tp_textpane.print(); 
					}
					catch (PrinterException e) {
						e.printStackTrace(); 
					}
				}
			};
			printThread.start();
		}
		else if(e.getSource() == btn_exit){
			this.dispose();
		}
		
	}
}