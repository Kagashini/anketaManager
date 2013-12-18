package ru.develop.anketamanager;

import java.io.IOException;
import java.security.KeyPair;

import ru.develop.anketamanager.ftp.FtpSendTask;
import ru.develop.anketamanager.utils.Ping;
import ru.develop.anketamanager.utils.util_login_pass;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.MediaDeviceXCG;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityStep1 extends Activity implements OnClickListener{
	
	Anketa anketa=null;
	util_login_pass keyPair=null;
	Context contextA=null;
	
	EditText user;
	EditText password;
	EditText directory;
	TextView error;
	Button but;
	Button but_prev;
	Button but_next;
	Button but_ok;
	Button but_cancel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anketa_step1);
		
		//ru.develop.anketamanager.xml.Init.Start4(this);
		
		but = (Button)findViewById(R.id.but_login);
		if(but!=null)
			but.setOnClickListener(this);
		error = (TextView) findViewById(R.id.error_login);
		user = (EditText) findViewById(R.id.login);
		password = (EditText) findViewById(R.id.my_password);
		directory = (EditText) findViewById(R.id.directory);
		
		
		
	    but_next = (Button)findViewById(R.id.but_next);
		if(but_next!=null)
			but_next.setOnClickListener(this);
		but_prev = (Button)findViewById(R.id.but_prev);
		if(but_prev!=null)
			but_prev.setOnClickListener(this);
		
		but_ok = (Button)findViewById(R.id.but_login);
		if(but_ok!=null)
			but_ok.setOnClickListener(this);
		but_cancel = (Button)findViewById(R.id.but_cancel);
		if(but_cancel!=null)
			but_cancel.setOnClickListener(this);
	
		
		//but.setEnabled(true);
    	//user.setEnabled(true);
    	//password.setEnabled(true);
		but_ok.setEnabled(true);
    	but_cancel.setEnabled(true);
    	directory.setEnabled(true);
		
		anketa = (Anketa)getIntent().getSerializableExtra("anketa");
		keyPair = (util_login_pass)getIntent().getSerializableExtra("keypair");		
		
				
		
		if(anketa==null) anketa = MediaDeviceXCG.Load(MediaDeviceXCG.getDefaultFile(this, "anketa"));
		
		if(keyPair!=null)
		{
			directory.setText(keyPair.getDir());
			user.setText(keyPair.getLogin());
			password.setText(keyPair.getPass());
		}
		_currContext = this;
				
		if(error!=null)
			error.setText("");
		if(keyPair==null)
		{
		user.setText("crm_user");
		password.setText("nbkRen889");
		}
		
		//Если не доступен хост
				try {
					if(!Ping.Avaible("62.213.82.99"))
					{
						but_cancel.callOnClick();
						return;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
	}


	Context _currContext=null;   	    
	Intent intent=null;
	
	@Override
	public void onClick(View v) {
		
		
		
		switch (v.getId()) {       
        case R.id.but_login:                     	
        	Handler h_post = new Handler()
        	{        		
        		@Override
        		public void handleMessage(Message msg) {        	
        			super.handleMessage(msg);
        			error.setText((String)msg.obj);
        			
        			if(msg.arg1==1) //ошибка
        			{
        				//but.setEnabled(true);
        	        	//user.setEnabled(true);
        	        	//password.setEnabled(true);
        				but_ok.setEnabled(true);
        	        	but_cancel.setEnabled(true);
        	        	directory.setEnabled(true);
        				error.setTextColor(android.graphics.Color.rgb(50, 128, 0));
        			}
        			if(msg.arg1==5) //инфо
        			{        				
        				error.setTextColor(android.graphics.Color.rgb(21, 182, 73));
        			}
        			if(msg.arg1==0) //успех
        			{     
        				//but.setEnabled(true);
        	        	//user.setEnabled(true);
        	        	//password.setEnabled(true);
        				but_ok.setEnabled(true);
        	        	but_cancel.setEnabled(true);
        	        	directory.setEnabled(true);
        				error.setTextColor(android.graphics.Color.rgb(31, 182, 73));
        			}
        			if(msg.arg1<0) //логгер
        				error.setTextColor(android.graphics.Color.rgb(189,25, 71));
        			
        			if(msg.arg2==2) //Если нет ошибок, то идем дальше
        			{
        				if(anketa==null) 
        					anketa = MediaDeviceXCG.Load(MediaDeviceXCG.getDefaultFile(_currContext, "anketa"));
        				
        				keyPair.setLogin(user.getText().toString());
        				keyPair.setPass(password.getText().toString());
        				keyPair.setDir(directory.getText().toString());
        				
        				intent= new Intent(_currContext, ActivityStep2.class);
        				intent.putExtra("keypair", keyPair);
        	    		intent.putExtra("anketa",anketa);    		
        	    	    startActivity(intent);
        			}
        			
        		}        		
        	};
        	
        	//but.setEnabled(false);
        	//user.setEnabled(false);
        	//password.setEnabled(false);
        	but_ok.setEnabled(false);
        	but_cancel.setEnabled(false);
        	directory.setEnabled(false);
        	
        	FtpSendTask ft = new FtpSendTask(0,"62.213.82.99",user.getText().toString(),password.getText().toString(),"kolpakov"
        			,this.getFilesDir().toString(),h_post);
        	ft.execute();
        	
        	
        	
       	 break;
        	case R.id.but_cancel:
        		intent= new Intent(_currContext, ActivityStep2.class);
				intent.putExtra("keypair", keyPair);
	    		intent.putExtra("anketa",anketa);    		
	    	    startActivity(intent);
        		break;
               	
		}			
		
	}

}
