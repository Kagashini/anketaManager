package ru.develop.anketamanager;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.util.Iterator;
import java.util.List;

import ru.develop.anketamanager.ftp.FtpSendTask;
import ru.develop.anketamanager.utils.util_login_pass;
import ru.develop.anketamanager.widget.dialog.FileDialog;
import ru.develop.anketamanager.widget.dialog.IFileDialogDepends;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.MediaDeviceXCG;
import ru.develop.anketamanager.xml.References;
import android.R.string;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityStep6 extends Activity implements OnClickListener{
	util_login_pass keyPair=null;
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
			keyPair = (util_login_pass)getIntent().getSerializableExtra("keypair");
		_currContext = this;
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
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
       	 break;  
        case R.id.but_Send:
        	refresh(MediaDeviceXCG.getDefaultFile(this,"anketa")); 
        	//fd.openFileDialog(fdd);
       	 break;  
		}			
		
	}
	

	Context _currContext=null;   	    
	Intent intent=null;
	
	
	void refresh(File file)
	{
	  try {
		anketa.save(file);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	 // References refs = MediaDeviceXCG.LoadRefs(MediaDeviceXCG.getDefaultFile(this,"anketa"));
	  MediaDeviceXCG.Save(file,anketa,null);
	  String server = "62.213.82.99"; 
	  String password = keyPair.getPass();
	  String user = keyPair.getLogin();
	  FtpSendTask ft = new FtpSendTask(3, server,user,password,"kolpakov",this.getFilesDir().toString(),// MediaDeviceXCG.getDefaultFile(this, "anketa"),
		new Handler()
  	{        		
  		@Override
  		public void handleMessage(Message msg) {        	
  			super.handleMessage(msg);
  			error.setText((String)msg.obj);
  			if(msg.arg1>0) //ошибка
  				error.setTextColor(android.graphics.Color.rgb(50, 128, 0));
  			if(msg.arg1==0) //успех
  				error.setTextColor(android.graphics.Color.rgb(31, 182, 73));
  			if(msg.arg1<0) //логгер
  				error.setTextColor(android.graphics.Color.rgb(189,25, 71));
  			
  			if(msg.arg2==2) //Если нет ошибок то идем дальше
  			{
  				if(anketa==null) 
  					anketa = MediaDeviceXCG.Load(MediaDeviceXCG.getDefaultFile(_currContext, "anketa"));
  				
  				intent= new Intent(_currContext, ActivityStep1.class);
  	    		intent.putExtra("anketa",anketa);
  	    		intent.putExtra("keypair", keyPair);
  	    	    startActivity(intent);
  			}
  		}        		
  	});
			  
			  
	  	ft.execute();	
	}
		
}
