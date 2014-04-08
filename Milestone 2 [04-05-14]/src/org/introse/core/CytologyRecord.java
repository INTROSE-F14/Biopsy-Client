package org.introse.core;


import java.awt.Graphics;
import java.awt.print.PageFormat;

public class CytologyRecord extends Record
{
	
	public CytologyRecord()
	{
		super();
	}
	
	@Override
	public int print(Graphics g, PageFormat pf, int page)
	{
		return 0;
	}
}