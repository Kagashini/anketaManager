package ru.develop.anketamanager.xmlnew;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import ru.develop.anketamanager.xml.ActivityKind;
import ru.develop.anketamanager.xml.Region;
import ru.develop.anketamanager.xml.VisitPurpose;

public class Anketa implements Serializable 
{

	General general;
	List<Brand> brands;
	
	public General getGeneral() {   return general;  }
	public void setGeneral(General value) { this.general = value;  }
	
	public List<Brand> getBrands() {   return brands;  }
	public void setBrands(List<Brand> value) { this.brands = value;  }
	
	public static Anketa Load(File file)
	{
		Anketa result=null;
			
		  try
		   {
			  XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			    // включаем поддержку namespace (по умолчанию выключена)
			    factory.setNamespaceAware(true);
			    // создаем парсер
			    XmlPullParser xpp = factory.newPullParser();
			    
			    
			    // даем парсеру на вход Reader
			    
		
			    byte [] magicTag = new byte [3]; 
			    FileInputStream sd =  new FileInputStream(file);		    
			    sd.read(magicTag);
			    boolean n_ofs=(magicTag[0]==-17&&magicTag[1]==-69&&magicTag[2]==-65);		    
			    //sd.close();
		            // открываем поток для записи
	       		BufferedReader rd = null;
	       		
	       		if(n_ofs)        		
	       			rd = new BufferedReader(new InputStreamReader(sd,"UTF-8"));
	       			else       			
		        	    rd = new BufferedReader(new FileReader(file));
	       			
		        	
		        	
		        	xpp.setInput(rd);
		        	            
		        //
			   
		        	String number = null;
		      	   	String date = null;
		      	   	String initiator = null;
		      	   	String customer = null;
		      	   	String contact = null;
		      	   	String trademark = null;
		      	    String address = null;
		      	    String visitpurpose = null;
		      	   
		        	  General general=null;	        	 	        	  
				      List<Brand> brands=null;
				      Brand brand = null;
				      			      
				      String nn="";
				      while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) 
				      {
				    	  int t = xpp.getEventType();
				    	  nn = ((XmlPullParser.START_TAG==t||t==XmlPullParser.END_TAG) && xpp.getName()!=null)?xpp.getName().toString():"";
				    	  int d=xpp.getDepth();
				    	  
				    	   switch (t) {
					        // начало тэга
					        case XmlPullParser.START_TAG:
					          if(nn.equals("General")&&d==2)
					          {
					        	if(general==null)
					        		general = new General();
					          }
					          
					            //if(nn.equals("Assortment")&&d==2)
					        	  				          
					          if(nn.equals("Brands")&&d==3)
					          {	
					        	if(brands==null)
					        		brands = new ArrayList<Brand>();
					          }				        	
					          
					          if(nn.equals("Brand")&&d==4)
					          {				        	
					        	  if(brand==null)
					        	  brand = new Brand();
					        	  
					        	  for(int a=0;a<xpp.getAttributeCount();a++)
					        	  {
					        		  String attr_name = xpp.getAttributeName(a);
					        		  String attr_value = xpp.getAttributeValue(a);
					        		  if("name".equals(attr_name.toLowerCase()))				        		  
					        			  brand.setName(attr_value);
					        		  if("column".equals(attr_name.toLowerCase()))				        		  
					        			  brand.setColumn(attr_value);
					        		  if("value".equals(attr_name.toLowerCase()))				        		  
					        			  brand.setValue(attr_value);				        		  
					        	  }
					          }
					          
					          
					          	if(nn.equals("Number")&&d==3)		        	
					        		number="";			        					        	
					        	if(nn.equals("Date")&&d==3)		        	
					        		date="";
					        	if(nn.equals("Initiator")&&d==3)		        	
					        		initiator="";
					        	if(nn.equals("Customer")&&d==3)		        	
					        		customer="";
					        	if(nn.equals("Contact")&&d==3)		        	
					        		contact="";
					        	if(nn.equals("TradeMark")&&d==3)		        	
					        		trademark="";
					        	if(nn.equals("Address")&&d==3)		        	
					        		address="";
					        	if(nn.equals("VisitPurpose")&&d==3)		        	
					        		visitpurpose="";
					        	
					          break;
					          
					        // конец тэга
					        case XmlPullParser.END_TAG:
					        	if(nn.equals("General")&&d==2)
					        	{
					        		if(result==null)
					        			result = new Anketa();

								    result.setGeneral(general);
								    
					        		general= null;
					        	}
					        				
					        	if(nn.equals("Brands")&&d==3)
						        {	
					        		result.setBrands(brands);
					        		brands = null;
						        }
					        	
					        	if(nn.equals("Brand")&&d==4)
					        	{			        		
					        		if(brands==null)
						        		brands = new ArrayList<Brand>();
					        		brands.add(brand);
					        		brand = null;
					        	}
					        	if(nn.equals("Number")&&d==3)		        	
					        		number=null;			        					        	
					        	if(nn.equals("Date")&&d==3)		        	
					        		date=null;
					        	if(nn.equals("Initiator")&&d==3)		        	
					        		initiator=null;
					        	if(nn.equals("Customer")&&d==3)		        	
					        		customer=null;
					        	if(nn.equals("Contact")&&d==3)		        	
					        		contact=null;
					        	if(nn.equals("TradeMark")&&d==3)		        	
					        		trademark=null;
					        	if(nn.equals("Address")&&d==3)		        	
					        		address=null;
					        	if(nn.equals("VisitPurpose")&&d==3)		        	
					        		visitpurpose=null;
					          break;
					        // содержимое тэга
					        case XmlPullParser.TEXT:
					        		
					        	if(number!=null)
					        	{
					        		if(general==null) general=new General();
					        		number=number+xpp.getText();
					        		general.setNumber(number);
					        	}
					        	if(date!=null)		        	
					        	{
					        		if(general==null) general=new General();
					        		date=date+xpp.getText();
					        		general.setDate(date);
					        	}
					        	if(initiator!=null)		        	
					        	{
					        		if(general==null) general=new General();
					        		initiator=initiator+xpp.getText();
					        		general.setInitiator(initiator);
					        	}
					        	if(customer!=null)		        	
					        	{
					        		if(general==null) general=new General();
					        		customer=customer+xpp.getText();
					        		general.setCustomer(customer);
					        	}
					        	if(contact!=null)		        	
					        	{
					        		if(general==null) general=new General();
					        		contact=contact+xpp.getText();
					        		general.setContact(contact);
					        	}
					        	if(trademark!=null)		        	
					        	{
					        		if(general==null) general=new General();
					        		trademark=trademark+xpp.getText();
					        		general.setTrademark(trademark);
					        	}
					        	if(address!=null)		        	
					        	{
					        		if(general==null) general=new General();
					        		address=address+xpp.getText();
					        		general.setAddress(address);
					        	}
					        	if(visitpurpose!=null)		        	
					        	{
					        		if(general==null) general=new General();
					        		visitpurpose=visitpurpose+xpp.getText();
					        		general.setVisitPurpose(visitpurpose);
					        	}
					          break;
					        }
				     
				        // следующий элемент
				        xpp.next();
				      }


				      rd.close();
				      sd.close();   
		   }
		  catch (XmlPullParserException e) 
		  {
			 e.printStackTrace();
		  }
		  catch (IOException e) 
		  {
			 e.printStackTrace();
		  }			
		  catch(Exception gexc)
		  {
			      gexc.printStackTrace();
		  }
		  
				
		return result;
	}
	

	
	public static void Save(File file,Anketa anketa)
	{
	
	
		 	XmlSerializer serializer = Xml.newSerializer();
		    String ns = "";//"http://www.w3.org/2001/XMLSchema";
				   
		    
		 	StringWriter writer = new StringWriter();
		 	
		 	try 
		    {
		        serializer.setOutput(writer);
		        serializer.startDocument("UTF-8", true);
		        if(anketa!=null)
		        {		        	
		        serializer.startTag(ns,"Anketa");
		        		        		       
		        
		        
		        General general = anketa.getGeneral();		        
		        if(general!=null)
		        {
		        	serializer.startTag(ns,"General");
		        	
		        	
		        	serializer.startTag(ns,"Number");
		        	serializer.text(general.getNumber());
		        	serializer.endTag(ns,"Number");
		        	
		        	serializer.startTag(ns,"Date");
		        	serializer.text(general.getDate());
		        	serializer.endTag(ns,"Date");
		        	
		        	serializer.startTag(ns,"Initiator");
		        	serializer.text(general.getInitiator());
		        	serializer.endTag(ns,"Initiator");
		        	
		        	
		        	serializer.startTag(ns,"Customer");
		        	serializer.text(general.getCustomer());
		        	serializer.endTag(ns,"Customer");
		        	
		        	serializer.startTag(ns,"Contact");
		        	serializer.text(general.getContact());
		        	serializer.endTag(ns,"Contact");
		        	
		        	serializer.startTag(ns,"TradeMark");
		        	serializer.text(general.getTrademark());
		        	serializer.endTag(ns,"TradeMark");
		        	
		        	serializer.startTag(ns,"Address");
		        	serializer.text(general.getAddress());
		        	serializer.endTag(ns,"Address");
		        	
		        	serializer.startTag(ns,"VisitPurpose");
		        	serializer.text(general.getVisitPurpose());
		        	serializer.endTag(ns,"VisitPurpose");
		        	
		        	serializer.endTag(ns,"General");
		        }
		        
		        serializer.startTag(ns,"Assortment");
	        	
		        
		        List<Brand> brands = anketa.getBrands();
		        if(brands!=null&&brands.size()>0)
		        {
		        	serializer.startTag(ns,"Brands");
		        	for(Brand brand: brands)
		        	{
		        		serializer.startTag(ns,"Brand");
			        	serializer.attribute(ns,"name",brand.getName());
			        	serializer.attribute(ns,"column",brand.getColumn());
			        	serializer.attribute(ns,"value",brand.getValue());
			        	serializer.endTag(ns,"Brand");
		        	}
		        	serializer.endTag(ns,"Brands");
		        }
		        
		        
		        serializer.endTag(ns,"Assortment");
		        
		        serializer.endTag(ns,"Anketa");
		        }
		        
		        		       
		        serializer.endDocument();
		        String xml = writer.toString();
		        
		        try
		        {
		        	if(file.exists())
		        		file.delete();
		        	else
		        	{
		        		file.getParentFile().mkdirs();
		        	}
		        		
		            // открываем поток для записи
		            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		            // пишем данные
		            bw.write(xml);
		            // <a> поток
		            bw.close();	            
		        }
		       catch(IOException ioe)
		       {
		    	  String tt= ioe.getMessage();
		        }		   
		        catch (Exception e) 
		        {
		        	throw new RuntimeException(e);
		        }		     
		    
		   }		        
		   catch(Exception gexc)
	  	   {
			    gexc.printStackTrace();
		   }		
		    
	}
		        
		  
		    
		    
}