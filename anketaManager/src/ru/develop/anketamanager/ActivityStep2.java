package ru.develop.anketamanager;

import java.io.File;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ru.develop.anketamanager.ftp.FtpSendTask;
import ru.develop.anketamanager.utils.util_login_pass;
import ru.develop.anketamanager.widgets.Adapters.KindActivitySpinnerAdapter;
import ru.develop.anketamanager.widgets.Adapters.RegionSpinnerAdapter;
import ru.develop.anketamanager.widgets.Adapters.VisitPurposeSpinnerAdapter;
import ru.develop.anketamanager.xml.ActivityKind;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.MediaDeviceXCG;
import ru.develop.anketamanager.xml.References;
import ru.develop.anketamanager.xml.Region;
import ru.develop.anketamanager.xml.VisitPurpose;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class ActivityStep2 extends Activity implements OnClickListener{
	
	util_login_pass keyPair=null;
	References refs=null;
	Anketa anketa=null;
	Button but_prev;
	Button but_next;
	/*EditText customer;
	EditText address;
	Spinner region;
	Spinner kindactivity;
	Spinner visitpurpose;
	DatePicker date;
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anketa_step2);
		
		/*
		refs=MediaDeviceXCG.LoadRefs(MediaDeviceXCG.getDefaultFile(this, "anketa"));
				
		customer = (EditText) findViewById(R.id.customer);
		address = (EditText) findViewById(R.id.address);
		date = (DatePicker) findViewById(R.id.date);
		region = (Spinner) findViewById(R.id.region);
		kindactivity = (Spinner) findViewById(R.id.kindactivity);
		visitpurpose = (Spinner) findViewById(R.id.visitpurpose);
		*/
		
		 but_next = (Button)findViewById(R.id.but_next);
			if(but_next!=null)
				but_next.setOnClickListener(this);
			but_prev = (Button)findViewById(R.id.but_prev);
			if(but_prev!=null)
				but_prev.setOnClickListener(this);
		
			anketa = (Anketa)getIntent().getSerializableExtra("anketa");
			keyPair = (util_login_pass)getIntent().getSerializableExtra("keypair");		
		/*
			if(region!=null)
			{
				region.setPrompt("Выбирите регион");				
				region.setAdapter(new RegionSpinnerAdapter(this,refs));				
			}
			if(kindactivity!=null)
			{
				kindactivity.setPrompt("Выбирите вид деятельности");				
				kindactivity.setAdapter(new KindActivitySpinnerAdapter(this,refs));
			}
			
			if(visitpurpose!=null)
			{
				visitpurpose.setPrompt("Выбирите цель посещения");				
				visitpurpose.setAdapter(new VisitPurposeSpinnerAdapter(this,refs));
			}
			*/			
	}

	private void setData()
	{
		if(anketa!=null)
		{
/*		
		   anketa.setCustomer(customer.getText().toString());
		   anketa.setAddress(customer.getText().toString());
		   GregorianCalendar d = new GregorianCalendar();
		   d.set(date.getYear(), date.getMonth(), date.getDayOfMonth());		  
		   anketa.setDate(d);
		   anketa.setRegion((Region)region.getSelectedItem());
		   anketa.setKindActivity((ActivityKind)kindactivity.getSelectedItem());
		   anketa.setVisitPurpose((VisitPurpose)visitpurpose.getSelectedItem());
		   */
		   //MediaDeviceXCG.Save(new File("/mnt/sdcard/anketa.xml"),anketa);
		}
	}
	
	   
	
	@Override
	public void onClick(View v) {
		Intent intent=null;
		
		switch (v.getId()) {
        case R.id.but_next:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep3.class);
    		setData();
    		intent.putExtra("anketa",anketa);   
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
       	 break;
        case R.id.but_prev:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep1.class);
    		setData();
    		intent.putExtra("anketa",anketa);
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
       	 	break;   
		}			
		
	}
}
