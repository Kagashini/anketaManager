package ru.develop.anketamanager;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import ru.develop.anketamanager.widgets.Adapters.KindActivityExpandableListAdapter;
import ru.develop.anketamanager.widgets.Adapters.RegionExpandableListAdapter;
import ru.develop.anketamanager.xml.ActivityKind;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.Region;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class ActivityStep2 extends Activity implements OnClickListener{
	
	Anketa anketa=null;
	Button but_prev;
	Button but_next;
	EditText customer;
	EditText address;
	ExpandableListView region;
	ExpandableListView kindactivity;
	DatePicker date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		customer = (EditText) findViewById(R.id.expressdeliveries);
		address = (EditText) findViewById(R.id.address);
		date = (DatePicker) findViewById(R.id.date);
		region = (ExpandableListView) findViewById(R.id.region);
		kindactivity = (ExpandableListView) findViewById(R.id.kindactivity);
		
		
		 but_next = (Button)findViewById(R.id.but_next);
			if(but_next!=null)
				but_next.setOnClickListener(this);
			but_prev = (Button)findViewById(R.id.but_prev);
			if(but_prev!=null)
				but_prev.setOnClickListener(this);
		
			anketa = (Anketa)getIntent().getSerializableExtra("anketa");
			if(region!=null)
				region.setAdapter((ExpandableListAdapter)new RegionExpandableListAdapter(this));
			if(kindactivity!=null)
				kindactivity.setAdapter((ExpandableListAdapter)new KindActivityExpandableListAdapter(this));
	}

	private void setData()
	{
		if(anketa!=null)
		{
		   anketa.setCustomer(customer.getText().toString());
		   anketa.setAddress(customer.getText().toString());
		   /*anketa.setDate();
		    * int   day  = date.getDayOfMonth();
				int   month= date.getMonth();
				int   year = date.getYear();

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				return sdf.format(new Date(year, month, day));		
		    */		   
		   anketa.setRegion((Region)region.getSelectedItem());
		   anketa.setKindActivity((ActivityKind)kindactivity.getSelectedItem());
		}
	}
	
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // запишем в лог значения requestCode и resultCode
	    Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
	    // если пришло ОК
	    if (resultCode == RESULT_OK) 
	    {
	    	
	    }
	}
	      	    
	
	@Override
	public void onClick(View v) {
		Intent intent=null;
		
		switch (v.getId()) {
        case R.id.but_next:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep3.class);
    		intent.putExtra("anketa",(Serializable)anketa);
    	    startActivity(intent);
       	 break;
        case R.id.but_prev:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep1.class);
    		intent.putExtra("anketa",(Serializable)anketa);
    	    startActivity(intent);
       	 	break;       	 
		}			
		
	}
}
