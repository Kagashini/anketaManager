package ru.develop.anketamanager.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Init {
 
public static void Start() throws JAXBException
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
	 JAXBContext context = JAXBContext.newInstance("generated");		 
	 Marshaller marshaller = context.createMarshaller();
	 marshaller.setProperty("anketa.input",Boolean.TRUE);
	 marshaller.marshal(refs,System.out);
 }
	
}
