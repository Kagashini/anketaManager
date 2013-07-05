package ru.develop.anketamanager.ifaces;

public interface IAnketaMarketInfo {
	String getDirectPurchase();
	void setDirectPurchase(String v);
	
	
	String getPurchasesCompetitors();
	void setPurchasesCompetitors(String v);
	
	String getSupplierCompetitors();
	void setSupplierCompetitors(String v);	
	
	int getCountOutlets();
	void setCountOutlets(int co);
	
	float getTotalTurnover();
	void setTotalTurnover(float v);
	
	int getShareOfTotalTerema();
	void setShareOfTotalTerema(String v);
	
	String  getDiscountsCompetitors();
	void setDiscountsCompetitors(String v);
	
	String getAdvCompaniesCompetitors();
	void AdvCompaniesCompetitors(String v);
	
	String getPromoBrand();
	void setPromoBrand(String v);
}
