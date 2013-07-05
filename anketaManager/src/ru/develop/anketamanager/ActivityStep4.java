package ru.develop.anketamanager;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;

import ru.develop.anketamanager.widgets.Adapters.KindActivityExpandableListAdapter;
import ru.develop.anketamanager.widgets.Adapters.RegionExpandableListAdapter;
import ru.develop.anketamanager.xml.ActivityKind;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.AnketaAdditionInfo;
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

public class ActivityStep4 extends Activity implements OnClickListener{
	
	Anketa anketa=null;
	Button but_prev;
	Button but_next;
	EditText to_order;
	EditText express_deliveries;
	EditText amount_deliveries;
	EditText intro_prodiuctlines;
	EditText training_seminar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		to_order = (EditText) findViewById(R.id.expressdeliveries);
		express_deliveries = (EditText) findViewById(R.id.address);
		amount_deliveries = (EditText) findViewById(R.id.address);
		intro_prodiuctlines = (EditText) findViewById(R.id.address);
		training_seminar = (EditText) findViewById(R.id.address);
		
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
		   AnketaAdditionInfo ai = anketa.getAdditionInfo();
		   if(ai==null)
			   ai = new AnketaAdditionInfo();
		   ai.setToOrder(to_order.getText().toString());
		   ai.setExpressDeliveries(Integer.parseInt(express_deliveries.getText().toString()));
		   ai.setAmountDeliveries(new BigDecimal(amount_deliveries.getText().toString()));
		   ai.setIntroProductLines(Integer.parseInt(intro_prodiuctlines.getText().toString()));
		   anketa.setAdditionInfo(ai);  
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
    		intent= new Intent(this, ActivityStep5.class);
    		setData();
    		intent.putExtra("anketa",(Serializable)anketa);
    	    startActivity(intent);
       	 break;
        case R.id.but_prev:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep3.class);
    		setData();
    		intent.putExtra("anketa",(Serializable)anketa);
    	    startActivity(intent);
       	 	break;       	 
		}			
		
	}
}
