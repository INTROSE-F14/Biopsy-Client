package org.introse.gui.dialogbox;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.List;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
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
import org.introse.core.WrapEditorKit;
import org.introse.gui.event.CustomListener;

public class PrintDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton btn_print, btn_exit;
	private JTextPane tp_textpane;
	private JScrollPane sp_scrollpane;	
	private StyledDocument sd_doc;
		
	public PrintDialog(Record record){
	
		setTitle("Print Preview");
		setSize(650,630);
		getContentPane().setBackground(Color.WHITE);

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
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setLayout(null);

		this.tp_textpane= new JTextPane();
		this.tp_textpane.setFont(new Font("Courier", Font.PLAIN, 12));
		this.tp_textpane.setEditable(false);
		this.tp_textpane.setMargin(new Insets(18, 18,18, 18));
		this.tp_textpane.setHighlighter(null);
		this.tp_textpane.setEditorKit(new WrapEditorKit());
		this.sd_doc = this.tp_textpane.getStyledDocument();
		
		this.sp_scrollpane = new JScrollPane(this.tp_textpane);
        this.sp_scrollpane.setPreferredSize( new Dimension(585, 450));
		this.sp_scrollpane.setSize(585,450);
		this.sp_scrollpane.setLocation(30,30);
		this.sp_scrollpane.setBorder(BorderFactory.createLineBorder(Color.decode("#bdc3c7")));
        this.add( this.sp_scrollpane );
		
		this.btn_print = new JButton();
		this.btn_print.setIcon(new ImageIcon(getClass().getResource("/res/icons/print2.png")));
		this.btn_print.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/print2Hover.png")));
		this.btn_print.setSize(100,50);
		this.btn_print.setLocation(220,520);
		this.btn_print.setBorderPainted(false);
		this.btn_print.setContentAreaFilled(false);
		this.btn_print.setRequestFocusEnabled(false);
		this.btn_print.addActionListener(this);
		this.add(this.btn_print);

		this.btn_exit = new JButton();
		this.btn_exit.setIcon(new ImageIcon(getClass().getResource("/res/icons/close.png")));
		this.btn_exit.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/closeHover.png")));
		this.btn_exit.setSize(100,50);
		this.btn_exit.setLocation(330,520);
		this.btn_exit.setBorderPainted(false);
		this.btn_exit.setContentAreaFilled(false);
		this.btn_exit.setRequestFocusEnabled(false);
		this.btn_exit.addActionListener(this);
		this.add(this.btn_exit);
		
		this.generateForm(record);
		this.setVisible(true);
	}
	
	private PageFormat getFormat() {
		PageFormat pf = new PageFormat();
		Paper p = pf.getPaper();
		p.setSize(8.5 * 72, 11 * 72);
		p.setImageableArea(0.25 * 72, 0.25 * 72, 7.75 * 72, 10.75 * 72);
		pf.setPaper(p);
		return pf;
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
			
			String room = "N/A";
			if(record.getAttribute(Constants.RecordTable.ROOM) != null)
				room = (String) record.getAttribute(Constants.RecordTable.ROOM);
			
			Patient patient = (Patient)record.getAttribute(RecordTable.PATIENT);
			String patientName = ((String)patient.getAttribute(Constants.PatientTable.LAST_NAME)).toUpperCase() + ", " + 
								 (String)patient.getAttribute(Constants.PatientTable.FIRST_NAME) + " " +
								 (String)patient.getAttribute(Constants.PatientTable.MIDDLE_NAME);
								 
			String age = "N/A";
			CustomCalendar bDay = (CustomCalendar)patient.getAttribute(Constants.PatientTable.BIRTHDAY);
			if(bDay != null)
					age = bDay.getYearDifference((CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_RECEIVED)) + "";
			String gender = ((String)patient.getAttribute(Constants.PatientTable.GENDER));
			
			String specimen;
			
			switch((char) record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
			case 'G':	specimen = (String) record.getAttribute(Constants.RecordTable.SPEC_TYPE);
						break;
			default: specimen = (String) record.getAttribute(Constants.RecordTable.SPECIMEN);
			
			}
			
			String pathologist = (String) record.getAttribute(Constants.RecordTable.PATHOLOGIST);
			String physician = (String) record.getAttribute(Constants.RecordTable.PHYSICIAN);
			String dateReceived =  ((CustomCalendar)record.getAttribute(Constants.RecordTable.DATE_RECEIVED)).toNumericFormat();;
			String dateCompleted = ((CustomCalendar) record.getAttribute(Constants.RecordTable.DATE_COMPLETED)).toNumericFormat();
			
			List<Diagnosis> l_diagnosis = (List)record.getAttribute(RecordTable.DIAGNOSIS);
			
			String comments = (String) record.getAttribute(Constants.RecordTable.REMARKS);
			String grossdesc = "";
			String micronote = "";
			if((String) record.getAttribute(Constants.RecordTable.GROSS_DESC) != null)
				grossdesc = (String) record.getAttribute(Constants.RecordTable.GROSS_DESC);
			if((String) record.getAttribute(Constants.RecordTable.MICRO_NOTE) != null)
				micronote = (String) record.getAttribute(Constants.RecordTable.MICRO_NOTE);

			String description = grossdesc + "\n\n" + micronote;				
			
			MutableAttributeSet mas_bold = new SimpleAttributeSet();
			MutableAttributeSet mas_boldunderline = new SimpleAttributeSet();
			StyleConstants.setBold(mas_bold, true);
			StyleConstants.setBold(mas_boldunderline, true);
			StyleConstants.setUnderline(mas_boldunderline, true);
			
			try{
				this.addHeader();
				this.leftAlign();
				sd_doc.insertString(sd_doc.getLength(), String.format("\n" + numberLabel + number), null);
				sd_doc.insertString(sd_doc.getLength(), String.format("\n" + patientLabel + patientName + getSpaces(patientLabel+patientName,ageLabel+age, genderLabel+gender) + ageLabel + age + getSpaces(patientLabel+patientName,ageLabel+age, genderLabel+gender) + genderLabel + gender), null);
				sd_doc.insertString(sd_doc.getLength(), String.format("\n" + specimenLabel + specimen),null);
				sd_doc.insertString(sd_doc.getLength(), String.format("\n" + physicianLabel + physician + getSpaces(physicianLabel+physician,roomLabel+room) + roomLabel + room), null);
				sd_doc.insertString(sd_doc.getLength(), String.format("\n" + dateReceivedLabel + dateReceived + getSpaces(dateReceivedLabel+dateReceived,dateCompletedLabel+dateCompleted) + dateCompletedLabel + dateCompleted), null);
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				this.centerAlign();
				sd_doc.insertString(sd_doc.getLength(), "\n"+title, mas_boldunderline);
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				this.leftAlign();
				switch((char)record.getAttribute(Constants.RecordTable.RECORD_TYPE)){
				case 'G': this.addGynecologyDiagnosis(l_diagnosis);
						break;
				default: this.addHistoCytoDiagnosis(l_diagnosis, (char)record.getAttribute(Constants.RecordTable.RECORD_TYPE));
				}
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				sd_doc.insertString(sd_doc.getLength(),"\n" + commentsLabel, mas_boldunderline);
				if(comments == null){
					sd_doc.insertString(sd_doc.getLength(), "\n", null);
				}
				else{
					sd_doc.insertString(sd_doc.getLength(), "\n" + comments, null);
				}
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
				sd_doc.insertString(sd_doc.getLength(), "\n" + descriptionLabel, mas_boldunderline);
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
		int width = (int) getFormat().getImageableWidth() - 36;
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
		int width = (int) getFormat().getImageableWidth() - 36;
		int length1 = fm.stringWidth(s1);
		int length2 = fm.stringWidth(s2);
		int length3 = fm.stringWidth(s3);
		int space = fm.charWidth(' ');
		int numSpaces = ((width-length1-length2-length3)/2)/space;
		String longSpace = "";
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
		tp_textpane.insertIcon(new ImageIcon(getClass().getResource("/res/icons/hospital_logo.png")));
		sd_doc.insertString(sd_doc.getLength(), String.format("\nMANILA DOCTORS HOSPITAL"), mas_boldunderline );
		sd_doc.insertString(sd_doc.getLength(), String.format("\nDepartment of Laboratory Medicine"), mas_bold );
		sd_doc.insertString(sd_doc.getLength(), String.format("\n667 U.N. Ave., Ermita, Manila"), mas_bold );
		sd_doc.insertString(sd_doc.getLength(), String.format("\nTel. No. 5288108/ 5243011 loc. 8108"), mas_bold );
		sd_doc.insertString(sd_doc.getLength(), "\n", null);
	}
	catch (Exception ex){
		System.out.println(ex); 
	}
}
	
	private void addHistoCytoDiagnosis(List<Diagnosis> l_diagnosis, char type){
		MutableAttributeSet mas_boldunderline = new SimpleAttributeSet();
		StyleConstants.setBold(mas_boldunderline, true);
		StyleConstants.setUnderline(mas_boldunderline, true);
		try{
			String diagnosisLabel = "";
			switch(type){
			case 'H': diagnosisLabel = Constants.PrintConstants.DIAGNOSIS_LABEL_H;
					break;
			case 'C':diagnosisLabel = Constants.PrintConstants.DIAGNOSIS_LABEL_C;
					break;
			default: break;
			}
			sd_doc.insertString(sd_doc.getLength(), "\n" + diagnosisLabel, mas_boldunderline);
			if(l_diagnosis.get(0) != null){
				sd_doc.insertString(sd_doc.getLength(), "\n" + l_diagnosis.get(0).getValue(), null);
			}
			else{
				sd_doc.insertString(sd_doc.getLength(), "\n", null);
			}
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
			String S = "", I = "", P = "";
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
				break;
				case Constants.CategoriesConstants.S: S = value;
				break;
				case Constants.CategoriesConstants.I: I = value;
				break;
				case Constants.CategoriesConstants.P: P = value;
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
		sd_doc.insertString(sd_doc.getLength(), "\n", null);
		sd_doc.insertString(sd_doc.getLength(), "\n" + Constants.PrintConstants.HE, null);
		sd_doc.insertString(sd_doc.getLength(), "\n      " + Constants.PrintConstants.HE_S + S + "       " +  Constants.PrintConstants.HE_I + I + "       " +  Constants.PrintConstants.HE_P + P, null);	
		}
		catch(Exception e){
		
		}
		
	}
	
	private void addSignature(String pathologistLabel, String pathologist){
		
		try{
			sd_doc.insertString(sd_doc.getLength(), "\n", null);
			sd_doc.insertString(sd_doc.getLength(), "\n" + "__________________________", null);
			sd_doc.insertString(sd_doc.getLength(), "\n" + pathologist + ", MD", null);
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
						MediaPrintableArea mpa = new MediaPrintableArea((float)0.25,(float)0.25,(float)8.25,(float)10.75, MediaPrintableArea.INCH);
						PrintRequestAttributeSet attr_set = new HashPrintRequestAttributeSet();
						attr_set.add(mpa);
						tp_textpane.print(null, new MessageFormat("Page {0}"), false, null, attr_set, false); 
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