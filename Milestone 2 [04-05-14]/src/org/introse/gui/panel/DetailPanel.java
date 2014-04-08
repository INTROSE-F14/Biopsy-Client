package org.introse.gui.panel;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import org.introse.gui.event.CustomListener;

public abstract class DetailPanel extends JPanel
{
	protected int mode;
	
	public DetailPanel(LayoutManager lm, int mode)
	{
		super(lm);
		this.mode = mode;
	}
	public abstract void setMode(int mode);
	public abstract Object getObject();
	public abstract void addListener(CustomListener listener);
	public abstract boolean areFieldsValid();
	public int getMode()
	{
		return mode;
	}
}
