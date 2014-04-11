package org.introse.gui.dialogbox;

import javax.swing.JDialog;

import org.introse.gui.event.CustomListener;

public abstract class SearchDialog extends JDialog
{
	public SearchDialog(String title)
	{
		super(null, "Search Patient", ModalityType.APPLICATION_MODAL);
	}
	public abstract void addListener(CustomListener listener);
	public abstract Object getSearchCriteria();
	public abstract void showGUI();
    public abstract void clear();
}

