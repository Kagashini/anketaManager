package ru.develop.anketamanager;

import java.io.File;
import java.io.Serializable;

import ru.develop.anketamanager.ftp.FtpSendTask;
import ru.develop.anketamanager.widget.dialog.FileDialog;
import ru.develop.anketamanager.widget.dialog.IFileDialogDepends;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.MediaDeviceXCG;
import ru.develop.anketamanager.xml.References;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityStep1 extends Activity implements OnClickListener{
	
	Anketa anketa=null;		
	Context contextA=null;
	
	EditText user;
	EditText password;
	TextView error;
	Button but;
	Button but_prev;
	Button but_next;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anketa_step1);
		
		ru.develop.anketamanager.xml.Init.Start();
		
		
		but = (Button)findViewById(R.id.but_login);
		if(but!=null)
			but.setOnClickListener(this);
		error = (TextView) findViewById(R.id.error_login);
		user = (EditText) findViewById(R.id.login);
		password = (EditText) findViewById(R.id.my_password);
		
		
	    but_next = (Button)findViewById(R.id.but_next);
		if(but_next!=null)
			but_next.setOnClickListener(this);
		but_prev = (Button)findViewById(R.id.but_prev);
		if(but_prev!=null)
			but_prev.setOnClickListener(this);
		
		anketa = (Anketa)getIntent().getSerializableExtra("anketa");
		 
		if(anketa==null) anketa = MediaDeviceXCG.Load(new File("/mnt/sdcard/anketa.xml"));
				 
	}


	      	    
	
	@Override
	public void onClick(View v) {
		Intent intent=null;
		
		switch (v.getId()) {       
        case R.id.but_login:     
        	FtpSendTask ft = new FtpSendTask("-b <hostname> <username> <password> <remote file> <local file>",error);
        	
        	
    		intent= new Intent(this, ActivityStep2.class);
    		intent.putExtra("anketa",anketa);    		
    	    startActivity(intent);
    	    
       	 break;
               	
		}			
		
	}

}
