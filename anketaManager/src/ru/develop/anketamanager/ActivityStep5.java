package ru.develop.anketamanager;

import java.io.File;
import java.math.BigDecimal;

import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.AnketaMarketInfo;
import ru.develop.anketamanager.xml.MediaDeviceXCG;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
		setContentView(R.layout.activity_anketa_step5);
		
		direct_purchase = (EditText) findViewById(R.id.direct_purchase);
		pusrchase_competitors = (EditText) findViewById(R.id.pusrchase_competitors);
		supplier_competitors = (EditText) findViewById(R.id.supplier_competitors);
		count_outlets = (EditText) findViewById(R.id.count_outlets);
		total_turnover = (EditText) findViewById(R.id.total_turnover);
		share_of_total_terema = (EditText) findViewById(R.id.share_of_total_terema);
		discounts_competitors = (EditText) findViewById(R.id.discounts_competitors);
		adv_companies_competitors = (EditText) findViewById(R.id.adv_companies_competitors);
		promo_brand = (EditText) findViewById(R.id.promo_brand);		
		
		
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
		   
		   try
		   {
		   mi.setCountOutlets(Integer.parseInt(count_outlets.getText().toString()));
		   }		
		   catch(Exception e)
		   {		   
		   }
		   
		   mi.setSupplierCompetitors(supplier_competitors.getText().toString());
		   
		   try
		   {
		   mi.setTotalTurnover(new BigDecimal(total_turnover.getText().toString()));
		   }
		   catch(Exception e)
		   {		   
		   }		   
		   
		   
		   try
		   {
		   mi.setShareOfTotalTerema(Integer.parseInt(share_of_total_terema.getText().toString()));
		   }
		   catch(Exception e)
		   {		   
		   }
		   
		   try
		   {
		   mi.setDiscountsCompetitors(Integer.parseInt(discounts_competitors.getText().toString()));
		   }
		   catch(Exception e)
		   {		   
		   }
		   mi.setPromoBrand(promo_brand.getText().toString());
		   anketa.setMarketInfo(mi);		 
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
    		intent.putExtra("anketa",anketa);
    	    startActivity(intent);
       	 break;
        case R.id.but_prev:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep4.class);
    		setData();
    		intent.putExtra("anketa",anketa);
    	    startActivity(intent);
       	 	break;       	 
		}			
		
	}
}
