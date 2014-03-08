package org.introse.gui.dialogbox;

import org.introse.gui.event.CustomListener;

public interface SearchDialog
{
	public void addListener(CustomListener listener);
	public Object getSearchCriteria();
	public void showGUI();
    public abstract void clear();
}

