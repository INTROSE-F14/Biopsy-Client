package org.introse.gui.panel;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;

public abstract class DetailPanel extends JPanel
{

	public static final int VIEW = 0;
	public static final int EDIT = 1;
	public static final int NEW = 2;
	public static final int HISTOPATHOLOGY = 0;
	public static final int GYNECOLOGY = 1;
	public static final int CYTOLOGY = 2;
	public static final int PATIENT = 3;
	protected int mode;
	
	public DetailPanel(LayoutManager lm, int mode)
	{
		super(lm);
		this.mode = mode;
	}
	public abstract void setMode(int mode);
	public abstract Record getRecord();
	public abstract Patient getPatient();
	public abstract void addListener(CustomListener listener);
	public abstract boolean areFieldsValid();
	public int getMode()
	{
		return mode;
	}

}
