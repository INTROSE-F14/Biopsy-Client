package org.introse.core;

import java.util.List;

public class Dictionary {
	
	public static List<String> pathologists;
	public static List<String> physicians;
	public static List<String> specimens;
	
	public static void setPathologists(List<String> pathologists)
	{
		Dictionary.pathologists = pathologists;
	}
	
	public static void setPhysicians(List<String> physicians)
	{
		Dictionary.physicians = physicians;
	}
	
	public static void setSpecimens(List<String> specimens)
	{
		Dictionary.specimens = specimens;
	}
}
