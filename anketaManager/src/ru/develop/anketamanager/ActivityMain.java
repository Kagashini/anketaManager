package ru.develop.anketamanager;

import javax.xml.bind.JAXBException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMain extends Activity implements OnClickListener{
	
	
	Button but;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		but = (Button)findViewById(R.id.but_next);
		if(but!=null)
			but.setOnClickListener(this);
	}

	/*@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // запишем в лог значения requestCode и resultCode
	    Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
	    // если пришло ОК
	    if (resultCode == RESULT_OK) 
	    {
	    	
	    }
	}
	*/
	      	    
	
	//@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			ru.develop.anketamanager.xml.Init.Start();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intent= new Intent(this, ActivityStep1.class);
	    startActivity(intent);
	}
}
