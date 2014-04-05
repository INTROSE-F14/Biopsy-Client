package org.introse.core.workers;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.SwingWorker;

import org.introse.Constants.TitleConstants;
import org.introse.gui.panel.ListItem;

public class FilterWorker extends SwingWorker<List<ListItem>, Void> 
{
	private String filter;
	private List<ListItem> currentList;
	
	public FilterWorker(String filter, List<ListItem> currentList)
	{
		this.filter = filter;
		this.currentList = currentList;
	}
	
	@Override
	protected List<ListItem> doInBackground() throws Exception 
	{
		if(filter.length() < 1 || filter.equals(TitleConstants.QUICK_FILTER))
			return currentList;
		List<ListItem> filteredList = new Vector<ListItem>();
		Iterator<ListItem> i = currentList.iterator();
		while(i.hasNext())
		{
			ListItem listItem = i.next();
			for(int j = 0; j < listItem.getLabelCount(); j++)
				if(listItem.getLabel(j).toLowerCase().contains(filter.toLowerCase()))
				{
					filteredList.add(listItem);
					j = listItem.getLabelCount();
				}
		}
		return filteredList;
	}
	
	@Override
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}

}
