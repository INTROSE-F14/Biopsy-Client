package org.introse.core.workers;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.SwingWorker;

import org.introse.core.DictionaryWord;
import org.introse.core.dao.DictionaryDao;
import org.introse.gui.panel.ListItem;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;

public class DictionaryListGenerator extends SwingWorker<List<ListItem>, Void> {

	private DictionaryDao dictionaryDao;
	private int type;
	private EventList<String> words;
	
	public DictionaryListGenerator(DictionaryDao dictionaryDao, int type)
	{
		this.dictionaryDao = dictionaryDao;
		this.type = type;
		words = new BasicEventList<String>();
	}
	
	@Override
	protected List<ListItem> doInBackground() throws Exception 
	{
		
		List<ListItem> wordList = new Vector<ListItem>();
		int size = dictionaryDao.getCount(type);
		int completed = 0;
		
		List<DictionaryWord> words = dictionaryDao.getAll(type);
		Iterator<DictionaryWord> i = words.iterator();
		while(i.hasNext())
		{
			DictionaryWord word = i.next();
			this.words.add(word.getWord());
			ListItem listItem = (ListItem)word;
			wordList.add(listItem);
			completed++;
			setProgress(completed * 100 / size);
		}
		return wordList;
	}
	
	@Override 
	protected void done()
	{
		firePropertyChange("DONE", null, null);
	}
	
	public EventList<String> getWords()
	{
		return words;
	}

}
