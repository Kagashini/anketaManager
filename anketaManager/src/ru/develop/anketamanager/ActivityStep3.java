package ru.develop.anketamanager;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import ru.develop.anketamanager.utils.util_login_pass;
import ru.develop.anketamanager.widgets.Adapters.AnketaGridAdapter;
import ru.develop.anketamanager.xml.ActivityKind;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.AnketaProductInfo;
import ru.develop.anketamanager.xml.AnketaProductInfoExt;
import ru.develop.anketamanager.xml.MediaDeviceXCG;
import ru.develop.anketamanager.xml.Region;
import ru.develop.anketamanager.xml.VisitPurpose;
import ru.develop.anketamanager.xmlnew.General;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ActivityStep3 extends Activity implements OnClickListener{
	util_login_pass keyPair=null;
	//TableLayout table=null; 
 	ru.develop.anketamanager.xmlnew.Anketa anketa=null;
	Button but_prev;
	Button but_next;
	
	Button but_quit;
	
	TextView number;
	TextView date;
	TextView initiator;
	TextView customer;
	TextView contact;
	TextView trademark;
	TextView address;
	TextView visitpurpose;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anketa_step3);
		
		but_next = (Button)findViewById(R.id.but_next);
			if(but_next!=null)
				but_next.setOnClickListener(this);
		but_prev = (Button)findViewById(R.id.but_prev);
			if(but_prev!=null)
				but_prev.setOnClickListener(this);
		but_quit = (Button)findViewById(R.id.but_quit);
			if(but_quit!=null)
				but_quit.setOnClickListener(this);
			
		anketa =(ru.develop.anketamanager.xmlnew.Anketa) getIntent().getSerializableExtra("anketa");		
		keyPair = (util_login_pass)getIntent().getSerializableExtra("keypair");	
		
		number = (TextView) findViewById(R.id.s3_number);
		date = (TextView) findViewById(R.id.s3_date);
		initiator = (TextView) findViewById(R.id.s3_initiator);
		customer = (TextView) findViewById(R.id.s3_customer);
		contact = (TextView) findViewById(R.id.s3_contact);
		trademark = (TextView) findViewById(R.id.s3_trademark);
		address = (TextView) findViewById(R.id.s3_address);
		visitpurpose = (TextView) findViewById(R.id.s3_visitpurpose);
		
		General g= anketa.getGeneral();
		number.setText(g.getNumber());
		date.setText(g.getDate());
		initiator.setText(g.getInitiator());
		customer.setText(g.getCustomer());
		contact.setText(g.getContact());
		trademark.setText(g.getTrademark());
		address.setText(g.getAddress());
		visitpurpose.setText(g.getVisitPurpose());
		
		
	}

	/*
	private void setData()
	{
		if(anketa!=null)
		{
		   List<AnketaProductInfo> pis = anketa.getProductRanges();
		   if(pis==null)
			   pis = new ArrayList<AnketaProductInfo>();
		   else 
			   pis.clear();
		   List<String> ext_columns = new ArrayList<String>();
		   for(int c=0;c<table.getChildCount();c++)
		   {
			   AnketaProductInfo pi = new AnketaProductInfo();
			   
			   TableRow row = (TableRow) table.getChildAt(c);
			   int col_row = row.getChildCount();			 
			   if(c==0)
			   {
				   for(int i=1;i<col_row;i++)
				   {
					   TextView tv = (TextView) row.getChildAt(i);				   
					   ext_columns.add(tv.getText().toString());
				   }
				   ;
				   continue;
			   }
				   
			   for(int i=1;i<col_row;i++)
			   {
				   EditText et = (EditText) row.getChildAt(i);				   
				   String  val = et.getText().toString();
				   if(i==0)
					   pi.setAdv(val);
				   if(i==1)
					   pi.setAvailability(val);
				   if(i==2)
					   pi.setBrand(val);
				   if(i==3)
					   pi.setComment(val);
				   if(i==4)
					   pi.setStands(val);
				   if(i==5)
				   {
					   try
					   {
					   pi.setTurnover(new BigDecimal(val));
					   }
					   catch(Exception e)
					   {
					   }
				   }
				   
				   if(i>6)
				   {
					   List<AnketaProductInfoExt> exts = pi.getExtensions();
					   if(exts==null) exts=new ArrayList<AnketaProductInfoExt>();
					   AnketaProductInfoExt pie = new AnketaProductInfoExt();
					   pie.setName(ext_columns.get(i));
					   pie.setValue(val);
					   exts.add(pie);
					   pi.setExtensions(exts);
				   }
			   }   
			   
			   pis.add(pi);
		   }
		   
		   anketa.setProductRanges(pis);
		}
		 		
	}
	*/
	      	    
	
	@Override
	public void onClick(View v) {
		Intent intent=null;
		
		switch (v.getId()) {
        case R.id.but_next:        	
    		intent= new Intent(this, ActivityStep4.class);
    		//setData();
    		intent.putExtra("anketa",anketa);
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
       	 break;
        case R.id.but_prev:        	
    		intent= new Intent(this, ActivityStep2.class);
    		//setData();
    		intent.putExtra("anketa",anketa);
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
       	 	break;       	
        case R.id.but_quit:
        	intent= new Intent(this, ActivityStep2.class);
    		//setData();
    		intent.putExtra("anketa",anketa);
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
        	break;
             
		}			
	}
}
