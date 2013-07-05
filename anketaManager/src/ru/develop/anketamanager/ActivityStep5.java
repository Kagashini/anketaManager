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
import ru.develop.anketamanager.xml.AnketaMarketInfo;
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

public class ActivityStep5 extends Activity implements OnClickListener{
	
	Anketa anketa=null;
	Button but_prev;
	Button but_next;
	EditText direct_purchase;
	EditText pusrchase_competitors;
	EditText supplier_competitors;
	EditText count_outlets;
	EditText total_turnover;
	EditText share_of_total_terema;
	EditText discounts_competitors;
	EditText adv_companies_competitors;
	EditText promo_brand;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		direct_purchase = (EditText) findViewById(R.id.expressdeliveries);
		pusrchase_competitors = (EditText) findViewById(R.id.address);
		supplier_competitors = (EditText) findViewById(R.id.expressdeliveries);
		count_outlets = (EditText) findViewById(R.id.address);
		total_turnover = (EditText) findViewById(R.id.expressdeliveries);
		share_of_total_terema = (EditText) findViewById(R.id.address);
		discounts_competitors = (EditText) findViewById(R.id.expressdeliveries);
		adv_companies_competitors = (EditText) findViewById(R.id.address);
		promo_brand = (EditText) findViewById(R.id.expressdeliveries);		
		
		
		 but_next = (Button)findViewById(R.id.but_next);
			if(but_next!=null)
				but_next.setOnClickListener(this);
			but_prev = (Button)findViewById(R.id.but_prev);
			if(but_prev!=null)
				but_prev.setOnClickListener(this);
		
			anketa = (Anketa)getIntent().getSerializableExtra("anketa");
			
	}

	private void setData()
	{
		if(anketa!=null)
		{
		   AnketaMarketInfo mi = anketa.getMarketInfo();		   
		   if(mi==null)
			   mi = new AnketaMarketInfo(); 
		   mi.setDirectPurchase(direct_purchase.getText().toString());
		   mi.setAdvCompaniesCompetitors(adv_companies_competitors.getText().toString());
		   mi.setCountOutlets(Integer.parseInt(count_outlets.getText().toString()));		   
		   mi.setSupplierCompetitors(supplier_competitors.getText().toString());
		   mi.setTotalTurnover(new BigDecimal(total_turnover.getText().toString()));
		   mi.setShareOfTotalTerema(Integer.parseInt(share_of_total_terema.getText().toString()));
		   mi.setDiscountsCompetitors(Integer.parseInt(discounts_competitors.getText().toString()));
		   mi.setPromoBrand(promo_brand.getText().toString());
		   anketa.setMarketInfo(mi);
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
    		intent= new Intent(this, ActivityStep6.class);
    		setData();
    		intent.putExtra("anketa",(Serializable)anketa);
    	    startActivity(intent);
       	 break;
        case R.id.but_prev:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep4.class);
    		setData();
    		intent.putExtra("anketa",(Serializable)anketa);
    	    startActivity(intent);
       	 	break;       	 
		}			
		
	}
}
