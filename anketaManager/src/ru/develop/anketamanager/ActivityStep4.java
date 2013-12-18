package ru.develop.anketamanager;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import ru.develop.anketamanager.utils.util_login_pass;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.AnketaAdditionInfo;
import ru.develop.anketamanager.xml.MediaDeviceXCG;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActivityStep4 extends Activity implements OnClickListener{
	util_login_pass keyPair=null;
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
		setContentView(R.layout.activity_anketa_step4);
		
		to_order = (EditText) findViewById(R.id.to_order);
		express_deliveries = (EditText) findViewById(R.id.express_deliveries);
		amount_deliveries = (EditText) findViewById(R.id.amount_deliveries);
		intro_prodiuctlines = (EditText) findViewById(R.id.intro_prodiuctlines);
		training_seminar = (EditText) findViewById(R.id.training_seminar);
		
		 but_next = (Button)findViewById(R.id.but_next);
			if(but_next!=null)
				but_next.setOnClickListener(this);
			but_prev = (Button)findViewById(R.id.but_prev);
			if(but_prev!=null)
				but_prev.setOnClickListener(this);
		
		   anketa = (Anketa)getIntent().getSerializableExtra("anketa");
		   keyPair = (util_login_pass)getIntent().getSerializableExtra("keypair");
	}

	private void setData()
	{
		if(anketa!=null)
		{
		   AnketaAdditionInfo ai = anketa.getAdditionInfo();
		   if(ai==null)
			   ai = new AnketaAdditionInfo();
		   ai.setToOrder(to_order.getText().toString());
		   try
		   {
		   ai.setExpressDeliveries(Integer.parseInt(express_deliveries.getText().toString()));
		   }	
		   catch(Exception e)
		   {		   
		   }
		   try
		   {
		   ai.setAmountDeliveries(new BigDecimal(amount_deliveries.getText().toString()));
		   }
		   catch(Exception e)
		   {		   
		   }
		   
		   try
		   {
		   ai.setIntroProductLines(Integer.parseInt(intro_prodiuctlines.getText().toString()));
		   }
		   catch(Exception e)
		   {		   
		   }
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
    		intent.putExtra("anketa",anketa);
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
       	 break;
        case R.id.but_prev:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep3.class);
    		setData();
    		intent.putExtra("anketa",anketa);
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
       	 	break;       	 
		}			
		
	}
}
