package ru.develop.anketamanager.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ru.develop.anketamanager.xml.Anketa;

public class MediaDeviceXCG
{
   public Anketa Load(File file) throws JAXBException
   {
	   Anketa res=null;
	   JAXBContext context = JAXBContext.newInstance("generated");		 
	   Unmarshaller unmarshaller = context.createUnmarshaller();
	   unmarshaller.setProperty(file.getAbsoluteFile().getName(),Boolean.TRUE);
	   res = (Anketa) unmarshaller.unmarshal(file);    
       return res;
   }
   
   public boolean Save(File file,Anketa a) throws JAXBException
   {
	   JAXBContext context = JAXBContext.newInstance("generated");		 
	   Marshaller marshaller = context.createMarshaller();	   
	   marshaller.marshal(a,file);
       return true;
   }
}
