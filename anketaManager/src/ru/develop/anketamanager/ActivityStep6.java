package ru.develop.anketamanager;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import ru.develop.anketamanager.ftp.FtpSendTask;
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
import android.widget.TextView;

public class ActivityStep6 extends Activity implements OnClickListener{
	
	Anketa anketa=null;
	Context contextA=null;
	FileDialog fd=null;
	FileDialogDepends fdd=null;
	
	
	TextView error;
	Button but_save;
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
		setContentView(R.layout.activity_anketa_step6);
		
		 error = (TextView) findViewById(R.id.error_login);
		
		 but_next = (Button)findViewById(R.id.but_next);
			if(but_next!=null)
				but_next.setOnClickListener(this);
			but_prev = (Button)findViewById(R.id.but_prev);
			if(but_prev!=null)
				but_prev.setOnClickListener(this);
		
			but_save = (Button)findViewById(R.id.but_Send);
			if(but_save!=null)
				but_save.setOnClickListener(this);
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
        case R.id.but_next:        	
    		intent= new Intent(this, ActivityStep1.class);
    		intent.putExtra("anketa",anketa);
    	    startActivity(intent);
       	 break;  
        case R.id.but_Send:
        	refresh(new File("/mnt/sdcard/anketa.xml")); 
        	//fd.openFileDialog(fdd);
       	 break;  
		}			
		
	}
	
	void refresh(File file)
	{
	  try {
		anketa.save(new File("/mnt/sdcard/anketa2.xml"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  MediaDeviceXCG.Save(file,anketa);
	  FtpSendTask ft = new FtpSendTask("-s -b <hostname> <username> <password> <remote file> <local file>",error);
	  		
	}
		
}
