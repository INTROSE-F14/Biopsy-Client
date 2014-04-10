package org.introse.core.dao;

import java.util.List;

import org.introse.core.DictionaryWord;

public interface DictionaryDao 
{
	public void add(String word, int type);
	public void delete(String word, int type);
	public int getCount();
	public int getCount(int type);
	public List<DictionaryWord> getAll(int type);
	public List<String> getWords(int type);
}
