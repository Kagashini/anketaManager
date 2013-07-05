package ru.develop.anketamanager.ifaces;

import java.util.Date;

public interface IFileUnit {
	String getName();
	void setName(String n); 
	
	Date getDate();
	void setDate(Date d);
	
	IAuthor getAutor();
	void setAuthor(IAuthor a);
}
