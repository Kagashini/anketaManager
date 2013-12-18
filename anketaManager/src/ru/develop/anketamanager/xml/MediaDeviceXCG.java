package ru.develop.anketamanager.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.SimpleFormatter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Environment;
import android.os.MemoryFile;
import android.provider.ContactsContract.Directory;
import android.util.Log;
import android.util.Xml;

import ru.develop.anketamanager.widgets.PropRowObject;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;

import ru.develop.anketamanager.xml.Anketa;

public class MediaDeviceXCG
{
	public static File getDefaultFile(Context context,String file_name)
	{				
		File res = new File(context.getFilesDir(),file_name+".xml");			
		return res;
	}
	
	
	public static References LoadRefs(File file)// throws JAXBException
	{
		   References res=null;
		  // JAXBContext context = JAXBContext.newInstance("generated");		 
		  // Unmarshaller unmarshaller = context.createUnmarshaller();
		  // unmarshaller.setProperty(file.getAbsoluteFile().getName(),Boolean.TRUE);
		  // res = (Anketa) unmarshaller.unmarshal(file);    
		   res = new References();
		   
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
        	sd.close();
        	
	            // открываем поток для записи
        		BufferedReader rd = null;
        		
        		if(n_ofs) 
        			rd = new BufferedReader(new InputStreamReader(sd,"UTF-8"));
        			else
	        	    rd = new BufferedReader(new FileReader(file));
	        	
	        	
	        	xpp.setInput(rd);
	        	            
	        //
		   
		  
			      boolean myblock=false;
			      ActivityKind activity=null;
			      Region region=null;
			      VisitPurpose vp=null;
			      boolean code = false;
			      boolean name = false;
			      String nn="";
			      while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) 
			      {
			    	  int t = xpp.getEventType();
			    	  if(XmlPullParser.START_TAG==t||t==XmlPullParser.END_TAG)
			    		  nn =(xpp.getName()!=null)?xpp.getName().toString():"";
			    	  int d=xpp.getDepth();
			    	  
			    	   switch (t) {
				        // начало тэга
				        case XmlPullParser.START_TAG:
				          if(nn.equals("References")&&d==2)
				        	  myblock = true;
				          
				          if(nn.equals("Activities")&&d==3)
				        	{	
				        		if(res.activities==null)
				        		res.activities = new ArrayList<ActivityKind>();
				        	}
				        	if(nn.equals("Regions")&&d==3)
				        	{
				        		if(res.regions==null)
				        		res.regions = new ArrayList<Region>();				        		
				        	}
				        	if(nn.equals("VisitPurposes")&&d==3)
				        	{
				        		if(res.visitPurposes==null)
				        		res.visitPurposes = new ArrayList<VisitPurpose>();				        		
				        	}
				        	
				          
				          if(nn.equals("ActivityKind")&&d==4)
				        		activity = new ActivityKind();
				        	if(nn.equals("Region")&&d==4)
				        		region = new Region();
				        	if(nn.equals("VisitPurpose")&&d==4)
				        		vp = new VisitPurpose();
				        	if(nn.equals("Код")&&d==5)
				        		code= true;
				        	if(nn.equals("Наименование")&&d==5)
				        	 name = true;
				          break;
				        // конец тэга
				        case XmlPullParser.END_TAG:
				        	if(nn.equals("References")&&d==2)
				        		myblock = false;
				        	
				        	
				        	if(nn.equals("ActivityKind")&&d==4)
				        	{			        		
				        		if(res.activities==null)
					        		res.activities = new ArrayList<ActivityKind>();
				        		res.activities.add(activity);
				        		activity = null;
				        	}
				        	if(nn.equals("Region")&&d==4)
				        	{
				        		if(res.regions==null)
					        		res.regions = new ArrayList<Region>();
				        		res.regions.add(region);
				        		region = null;
				        	}
				        	if(nn.equals("VisitPurpose")&&d==4)
				        	{
				        		if(res.visitPurposes==null)
					        		res.visitPurposes = new ArrayList<VisitPurpose>();
				        		res.visitPurposes.add(vp);
				        		vp=null;
				        	}
				        	if(nn.equals("Код")&&d==5)		        	
				        		code= false;			        					        	
				        	if(nn.equals("Наименование")&&d==5)
				        		name = false;
				          break;
				        // содержимое тэга
				        case XmlPullParser.TEXT:			          
				          if(code)			        	
				          {
				           if(activity!=null) activity.setКод(xpp.getText());
				           if(region!=null) region.setКод(xpp.getText());
				           if(vp!=null) vp.setКод(xpp.getText());
				          }
				          if(name)
				          {
				        	   if(activity!=null) activity.setНаименование(xpp.getText());
					           if(region!=null) region.setНаименование(xpp.getText());
					           if(vp!=null) vp.setНаименование(xpp.getText());
				          }				          
				        	 
				          break;
				        }
			     
			        // следующий элемент
			        xpp.next();
			      }

			    } catch (XmlPullParserException e) {
			      e.printStackTrace();
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
		   catch(Exception gexc)
		   {
			      gexc.printStackTrace();
		   }
		   
	       return res;
	}
	
	
	
   public static Anketa Load(File file)// throws JAXBException
   {
	   Anketa res=null;
	  // JAXBContext context = JAXBContext.newInstance("generated");		 
	  // Unmarshaller unmarshaller = context.createUnmarshaller();
	  // unmarshaller.setProperty(file.getAbsoluteFile().getName(),Boolean.TRUE);
	  // res = (Anketa) unmarshaller.unmarshal(file);    
	   res = new Anketa();
	   
       return res;
   }
   
   public static boolean Save(File file,Anketa a) //throws JAXBException
   {
	   return Save(file,a,null);   
   }
   
   public static boolean Save(File file,Anketa a,References refs) //throws JAXBException
   {
	  // JAXBContext context = JAXBContext.newInstance("generated");		 
	  // Marshaller marshaller = context.createMarshaller();	   
	  // marshaller.marshal(a,file);
	   
	    XmlSerializer serializer = Xml.newSerializer();
	    String ns = "";//"http://www.w3.org/2001/XMLSchema";
	   	    
	 	StringWriter writer = new StringWriter();
	    try {
	        serializer.setOutput(writer);
	        serializer.startDocument("UTF-8", true);
	        serializer.startTag(ns,"Root");
        	
        	
	        
	        if(refs!=null)
	        {
	        	serializer.startTag(ns,"References");
	        	Serialize(ns,serializer,refs);
	        	serializer.endTag(ns,"References");
	        }
	        
	        if(a!=null)
	        {
	        	serializer.startTag(ns,"Anketa");
	        	Serialize(ns,serializer,a);
	        	serializer.endTag(ns,"Anketa");
	        }
	        
	        serializer.endTag(ns,"Root");
	        serializer.endDocument();
	        String xml = writer.toString();
	        try
	        {
	            // открываем поток для записи
	            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	            // пишем данные
	            bw.write(xml);
	            // <a> поток
	            bw.close();	            
	        }
	        catch(IOException ioe)
	        {
	         Log.w("sfsd", ioe.getMessage());
	        }
	        
	        
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } 
	   
       return true;
   }
   
   
   private void Save1(File file,References refs,Anketa a)
   {
	   refs = null; 
	   
	   XmlSerializer serializer = Xml.newSerializer();
	    String ns = "http://www.w3.org/2001/XMLSchema";
	   
	    
	 	StringWriter writer = new StringWriter();
	    try {
	        serializer.setOutput(writer);
	        serializer.startDocument("UTF-8", true);
	        if(refs!=null){
	        	
	        
	        serializer.startTag(ns, "References");
	        for(ActivityKind act: refs.activities)
	        {
	         serializer.startTag(ns,"ActivityKind");	         
	         serializer.startTag(ns,"Код");
	         serializer.text(act.getКод().toString());
	         serializer.endTag(ns,"Код");
	         serializer.startTag(ns,"Наименование");
	         serializer.text(act.getНаименование().toString());
	         serializer.endTag(ns,"Наименование");
	         serializer.endTag(ns, "ActivityKind");
	        }
	        
	        for(Region reg: refs.regions)
	        {
	         serializer.startTag(ns,"Region");	         
	         serializer.startTag(ns,"Код");
	         serializer.text(reg.getКод().toString());
	         serializer.endTag(ns,"Код");
	         serializer.startTag(ns,"Наименование");
	         serializer.text(reg.getНаименование().toString());
	         serializer.endTag(ns,"Наименование");
	         serializer.endTag(ns, "Region");
	        }
	        
	        for(VisitPurpose vp: refs.visitPurposes)
	        {
	         serializer.startTag(ns,"VisitPurpose");	         
	         serializer.startTag(ns,"Код");
	         serializer.text(vp.getКод().toString());
	         serializer.endTag(ns,"Код");
	         serializer.startTag(ns,"Наименование");
	         serializer.text(vp.getНаименование().toString());
	         serializer.endTag(ns,"Наименование");
	         serializer.endTag(ns, "VisitPurpose");
	        }
	        
	        serializer.endTag(ns, "References");
	        
	        
	        }
	        
	        a = (a==null)?new Anketa():null;
	        
	        if(a==null) return;
	        
	        serializer.startTag(ns,"Anketa");
	        
	        serializer.startTag(ns,"Customer");	        
	        serializer.endTag(ns,"Customer");
	        
	        serializer.startTag(ns,"Region");	         
	        serializer.startTag(ns,"Код");
	        serializer.text(a.region.getКод().toString());
	        serializer.endTag(ns,"Код");
	        serializer.startTag(ns,"Наименование");
	        serializer.text(a.region.getНаименование().toString());
	        serializer.endTag(ns,"Наименование");
	        serializer.endTag(ns, "Region");
	        
	        serializer.endTag(ns,"Anketa");
	        
	        
	        
	        
	        serializer.endDocument();
	        String xml = writer.toString();
	        
	        try
	        {
	            // открываем поток для записи
	            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	            // пишем данные
	            bw.write(xml);
	            // <a> поток
	            bw.close();	            
	        }
	        catch(IOException ioe)
	        {
	        
	        }
	        
	        
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } 
   }
   
   
   private static boolean IsArray(Object o)
   {
	   boolean res = false;
	   try
	   {
	     res =  ((List<Object>)o)!=null;
	   }
	   catch(Exception e)
	   {
	   
	   }
	   return res;
   }
   
   private static void Serialize(String ns,XmlSerializer serializer,Object o) throws IllegalArgumentException, IllegalStateException, IOException
   {
	   for(PropRowObject ro:PropRowObject.getProps(o,false))
	   {	  
		   if(ro.Name.substring(3)=="set") continue;
		   Object po = PropRowObject.getPropValue(o,ro.Name);
		   if(po!=null)
		   {
			   serializer.startTag(ns,ro.Name);		   
		   if(IsArray(po))
		   {
			   for(Object f:(List<Object>)po)
			   {
				   String nodeName = f.getClass().getName();
				   nodeName = nodeName.substring(nodeName.lastIndexOf(".")+1);
				   serializer.startTag(ns,nodeName);
				   Serialize(ns,serializer,f);
				   serializer.endTag(ns,nodeName);
			   }
		   }
		   else
		   {
			  // if(((Calendar) po)!=null)
				//   serializer.text(new SimpleFormatter("yyyy.MM.dd").toString());
			  // else
			   {
				   if(po.getClass().getName().equals("java.lang.String"))				   
					   serializer.text(po.toString());
				   else Serialize(ns,serializer,po);				  
			   }
		   }
	       serializer.endTag(ns,ro.Name);		
	   	   }
	   }
   
   }
   
   
  

}
