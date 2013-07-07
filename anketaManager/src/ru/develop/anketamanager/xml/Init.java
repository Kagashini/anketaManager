package ru.develop.anketamanager.xml;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.util.Log;
import android.util.Xml;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;

public class Init {
 
public static void Start() //throws JAXBException
 {
	 ObjectFactory factory = new ObjectFactory();
	 References refs = factory.createReferences();
	 ru.develop.anketamanager.xml.Region r1= factory.createRegion();
	 ru.develop.anketamanager.xml.Region r2= factory.createRegion();
	 ru.develop.anketamanager.xml.Region r3= factory.createRegion();
	 ActivityKind ak1 = factory.createActivityKind();
	 ActivityKind ak2 = factory.createActivityKind();
	 ActivityKind ak3 = factory.createActivityKind();
	 VisitPurpose vp1=factory.createVisitPurpose();
	 VisitPurpose vp2=factory.createVisitPurpose();
	 VisitPurpose vp3=factory.createVisitPurpose();
	
	  
	 
	 r1.setКод("0001");
	 r1.setНаименование("Москва");
	 r2.setКод("0002");
	 r2.setНаименование("Санкт-Петербург");
	 r3.setКод("0003");
	 r3.setНаименование("Екатеринбург");
	 
	 ak1.setКод("0001");
	 ak1.setНаименование("Торговля");
	 ak2.setКод("0002");
	 ak2.setНаименование("Услуги");
	 ak3.setКод("0003");
	 ak3.setНаименование("Производство");
	 
	 vp1.setКод("001");
	 vp1.setНаименование("Исполнение");
	 vp2.setКод("002");
	 vp2.setНаименование("Заявка");
	 vp3.setКод("003");
	 vp3.setНаименование("Прайс");
	 
	 List<ru.develop.anketamanager.xml.Region> regs = new ArrayList<ru.develop.anketamanager.xml.Region>();
	 List<ActivityKind> acts = new ArrayList<ActivityKind>();
	 List<VisitPurpose> visits = new ArrayList<VisitPurpose>();
	 regs.add(r1);
	 regs.add(r2);
	 regs.add(r3);
	 acts.add(ak1);
	 acts.add(ak2);
	 acts.add(ak3);
	 visits.add(vp1);
	 visits.add(vp2);
	 visits.add(vp3);
	 refs.setActivities(acts);		 
	 refs.setRegions(regs);
	 refs.setVisitPurposes(visits);
	 ru.develop.anketamanager.xml.MediaDeviceXCG.Save(new File("/mnt/sdcard/anketa.xml"),null,refs);
	// JAXBContext context = JAXBContext.newInstance("generated");		 
	// Marshaller marshaller = context.createMarshaller();
	 //marshaller.setProperty("anketa.input",Boolean.TRUE);
	 //marshaller.marshal(refs,System.out);
	 	 
 }
	

public static byte[] serializeObject(Object o) { 
    ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
 
    try { 
      ObjectOutput out = new ObjectOutputStream(bos); 
      out.writeObject(o); 
      out.close(); 
 
      // Get the bytes of the serialized object 
      byte[] buf = bos.toByteArray(); 
 
      return buf; 
    } catch(IOException ioe) { 
      Log.e("serializeObject", "error", ioe); 
 
      return null; 
    } 
  }


public static Object deserializeObject(byte[] b) { 
    try {     	
      ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b)); 
      Object object = in.readObject(); 
      in.close(); 
 
      return object; 
    } catch(ClassNotFoundException cnfe) { 
      Log.e("deserializeObject", "class not found error", cnfe); 
 
      return null; 
    } catch(IOException ioe) { 
      Log.e("deserializeObject", "io error", ioe); 
 
      return null; 
    } 
  } 


}
