package ru.develop.anketamanager.ifaces;

import java.util.List;

public interface IAnketaProductInfo {
	String getBrand();
	void setBrand(String v);
	
	float  getTurnover();
	void setTurnover(float v);
	
	String getAvailability();
	void setAvailability(String v);
	
	String getStands();
	void setStands(String v);
	
	String getAdv();
	void setAdv(String v);
	
	String getComment();
	void setComment(String v);
	
	List<IAnketaProductInfoExt> getExtensions();
	void setExtensions(List<IAnketaProductInfoExt> v);
	
}
