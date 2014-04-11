package org.introse.gui.panel;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;

public abstract class DetailPanel extends JPanel
{
	protected int mode;
	protected JScrollPane scroller;
	
	public DetailPanel(LayoutManager lm, int mode)
	{
		super(lm);
		this.mode = mode;
		scroller = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.5), 
				(int)(Preferences.getScreenHeight() * 0.9)));
		scroller.setBorder(null);
	}
	public abstract void setMode(int mode);
	public abstract Object getObject();
	public abstract void addListener(CustomListener listener);
	public abstract boolean areFieldsValid();
	public int getMode()
	{
		return mode;
	}
	
	public JScrollPane getScroller()
	{
		return scroller;
	}

}
