package ru.develop.anketamanager;

import java.io.Serializable;

import ru.develop.anketamanager.widgets.Adapters.AnketaGridAdapter;
import ru.develop.anketamanager.xml.Anketa;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class ActivityStep3 extends Activity implements OnClickListener{
	
	GridView gridView=null; 
	Anketa anketa=null;
	Button but_prev;
	Button but_next;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 but_next = (Button)findViewById(R.id.but_next);
			if(but_next!=null)
				but_next.setOnClickListener(this);
			but_prev = (Button)findViewById(R.id.but_prev);
			if(but_prev!=null)
				but_prev.setOnClickListener(this);

		anketa = (Anketa)getIntent().getSerializableExtra("anketa");
		gridView = (GridView)findViewById(R.id.gridView1);
		AnketaGridAdapter anketaAdapter = new AnketaGridAdapter(this,anketa); 
		gridView.setNumColumns(anketaAdapter.getColumnsCount()); 
		//Hook up our adapter to our ListView 
		gridView.setAdapter(anketaAdapter);
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
    		intent= new Intent(this, ActivityStep4.class);
    		intent.putExtra("anketa",(Serializable)anketa);
    	    startActivity(intent);
       	 break;
        case R.id.but_prev:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep2.class);
    		intent.putExtra("anketa",(Serializable)anketa);
    	    startActivity(intent);
       	 	break;       	 
		}			
	}
}
