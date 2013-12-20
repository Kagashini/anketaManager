package ru.develop.anketamanager.xmlnew;

import java.io.Serializable;



public class General implements Serializable {
	   protected String number;
	   protected String date;
	   protected String initiator;
	   protected String customer;
	   protected String contact;
	   protected String trademark;
	   protected String address;
	   protected String visitpurpose;

	   
	   public String getNumber() {   return number;  }
	   public void setNumber(String value) { this.number = value;  }
	   
	   public String getDate() {   return date;  }
	   public void setDate(String value) { this.date = value;  }
	   
	   public String getInitiator() {   return initiator;  }
	   public void setInitiator(String value) { this.initiator = value;  }	
	   
	   public String getCustomer() {   return customer;  }
	   public void setCustomer(String value) { this.customer = value;  }
	   
	   public String getContact() {   return contact;  }
	   public void setContact(String value) { this.contact = value;  }
	   
	   public String getTrademark() {   return trademark;  }
	   public void setTrademark(String value) { this.trademark = value;  }
	   
	   public String getAddress() {   return address;  }
	   public void setAddress(String value) { this.address = value;  }
	   
	   public String getVisitPurpose() {   return visitpurpose;  }
	   public void setVisitPurpose(String value) { this.visitpurpose = value;  }
	   
}
