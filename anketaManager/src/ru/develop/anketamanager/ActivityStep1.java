package ru.develop.anketamanager;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.JAXBException;

import ru.develop.anketamanager.widget.dialog.FileDialog;
import ru.develop.anketamanager.widget.dialog.IFileDialogDepends;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.MediaDeviceXCG;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityStep1 extends Activity implements OnClickListener{
	
	Anketa anketa=null;		
	Context contextA=null;
	FileDialog fd=null;
	FileDialogDepends fdd=null;
	
	Button but_prev;
	Button but_next;
	
	  public class FileDialogDepends implements IFileDialogDepends{
	        public FileDialogDepends refresh(File file){
	           refresh(file);	           
	           return this;
	        }
	     };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		contextA = ActivityStep1.this;
	    fdd=new FileDialogDepends();    
	    fd=new FileDialog(contextA);
	    but_next = (Button)findViewById(R.id.but_next);
		if(but_next!=null)
			but_next.setOnClickListener(this);
		but_prev = (Button)findViewById(R.id.but_prev);
		if(but_prev!=null)
			but_prev.setOnClickListener(this);
		 anketa = (Anketa)getIntent().getSerializableExtra("anketa");
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
        case R.id.but_Load:
       	  fd.openFileDialog(fdd);
       	 try {
       		ru.develop.anketamanager.xml.Init.Start();
       		} catch (JAXBException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		}
       	 break;
        case R.id.but_next:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep2.class);
    		intent.putExtra("anketa",(Serializable)anketa);
    	    startActivity(intent);
       	 break;
        case R.id.but_prev:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityMain.class);
    	    intent.putExtra("anketa",(Serializable)anketa);
    		startActivity(intent);
    	    
       	 	break;       	 
		}			
		
	}
	void refresh(File file)
	{
	  MediaDeviceXCG md = new MediaDeviceXCG();
	  try {
		anketa = md.Load(file);
	} catch (JAXBException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
