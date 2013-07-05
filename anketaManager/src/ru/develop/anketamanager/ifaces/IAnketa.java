package ru.develop.anketamanager.ifaces;
import java.util.*;

public interface IAnketa {
	String getCustomer();
	void setCustomer(String c); 
	
	IRegion getRegion();
	void setRegion(IRegion r);
	
	String getAddress();
	void setAddress(String a);
			
	IKindActivity getKindActivity();
	void setKindActivity(IKindActivity ki);
	
	Date getDate();
	void setDate(Date d);
	
	IVisitPurpose getVisitPurpose();
	void setVisitPurpose(IVisitPurpose vp);
	
	List<IAnketaProductInfo> getProductRange();
	void setProductRange(List<IAnketaProductInfo> api);
	
	IAnketaAdditionInfo getAdditionInfo();
	void setAdditionInfo(IAnketaAdditionInfo aai);
	
	IAnketaMarketInfo getMarketInfo();	
	void setMarketInfo(IAnketaMarketInfo ami);
}
