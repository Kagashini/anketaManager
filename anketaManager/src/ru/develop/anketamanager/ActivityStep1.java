package ru.develop.anketamanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.util.jar.Attributes;

import ru.develop.anketamanager.ftp.FtpSendTask;
import ru.develop.anketamanager.utils.GifMovieView;
import ru.develop.anketamanager.utils.Ping;
import ru.develop.anketamanager.utils.util_login_pass;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.MediaDeviceXCG;
import ru.develop.anketamanager.xmlnew.Brand;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ActivityStep1 extends Activity implements OnClickListener{
	
	//Anketa anketa=null;
	ru.develop.anketamanager.xmlnew.Anketa anketa=null;
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
	
	
	GifMovieView loading; 
	TextView loading_text;
	
	
	
	FtpSendTask ft_check=null;
	FtpSendTask ft_exchange=null;
	FtpSendTask ft_exchange2=null;
	
	@Override
	protected void onStop() 
	{
		super.onStop();
		_finish();
	};
	
	@Override
	protected void onDestroy() 
	{
	}
	
	void _finish()
	{
		if(ft_check!=null&&ft_check.getStatus()!=AsyncTask.Status.FINISHED)
		{
			 ft_check.cancel(true);
			 try {
				ft_check.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 ft_check=null;
		}
		
		if(ft_exchange!=null&&ft_exchange.getStatus()!=AsyncTask.Status.FINISHED)
		{
			 ft_exchange.cancel(true);
			 try {
				ft_exchange.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 ft_exchange=null;
		}
		
		if(ft_exchange2!=null&&ft_exchange2.getStatus()!=AsyncTask.Status.FINISHED)
		{
			 ft_exchange2.cancel(true);
			 try {
				ft_exchange2.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 ft_exchange2=null;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anketa_step1);
		
		
		InputStream stream = null; 
		try { 
			stream = getAssets().open("ajax-loader.gif"); 
			} 
		catch (IOException e) 
		{ e.printStackTrace(); } 
		
		
		FrameLayout loading_container = (FrameLayout) findViewById(R.id.loading_indicator);		 				
		loading = new GifMovieView(stream,loading_container);
		//loading.setVisible(true);
		loading_container.addView(loading,1000,40);
		
		loading_text = (TextView)findViewById(R.id.error_loading_indicator);
		
		
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
		
		error.setVisibility(8);
		
		//but.setEnabled(true);
    	//user.setEnabled(true);
    	//password.setEnabled(true);
		but_ok.setEnabled(true);
    	but_cancel.setEnabled(true);
    	directory.setEnabled(true);
		
		anketa = (ru.develop.anketamanager.xmlnew.Anketa)getIntent().getSerializableExtra("anketa");
		keyPair = (util_login_pass)getIntent().getSerializableExtra("keypair");		
		
						
		//if(anketa==null) anketa = MediaDeviceXCG.Load(MediaDeviceXCG.getDefaultFile(this, "anketa"));
		
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
		user.setText(getResources().getString(R.string.ftp_user));
		password.setText(getResources().getString(R.string.ftp_pass));
		}
		
		but_cancel.setEnabled(false);
		but_ok.setEnabled(false);
		
		//Если не доступен хост
		ft_check = new FtpSendTask(
    			0,
    			getResources().getString(R.string.ftp_server),
    			user.getText().toString(),
    			password.getText().toString(),
    			directory.getText().toString(),
    			this.getFilesDir().toString(),
    			new Handler() { 
    				@Override
    				public void handleMessage(Message msg) {
    					// TODO Auto-generated method stub
    					super.handleMessage(msg);
    					if(msg.arg1==1)
    					{
    						loading_text.setText("Работа в автономном режиме!");
    						but_cancel.setEnabled(true);
    						but_cancel.callOnClick();
    						return;	
    					}
    					loading_text.setText("");
    					loading.setVisible(false);
    					 but_cancel.setEnabled(!directory.getText().toString().isEmpty());
    					 but_ok.setEnabled(!directory.getText().toString().isEmpty());
    				}});
		loading_text.setText("Проверка доступа к сети!");
		loading.setVisible(true);
		ft_check.execute();
				
		directory.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {}
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count)
	        {
	        	but_cancel.setEnabled(!directory.getText().toString().isEmpty());
	        	but_ok.setEnabled(!directory.getText().toString().isEmpty());
	        }
	    });		
			
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
        				loading.setVisible(false);
        				loading_text.setText("");
        				AlertDialog.Builder infoBuilder = new AlertDialog.Builder(_currContext);
        				infoBuilder.setTitle("");
        				infoBuilder.setCancelable(true);        				
        				infoBuilder.setMessage(getResources().getString(R.string.hint_directory_error));
        				infoBuilder.show();
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
        				//but_ok.setEnabled(true);
        	        	//but_cancel.setEnabled(true);
        	        	//directory.setEnabled(true);
        				error.setTextColor(android.graphics.Color.rgb(31, 182, 73));
        			}
        			if(msg.arg1<0) //логгер
        				error.setTextColor(android.graphics.Color.rgb(189,25, 71));
        			
        			if(msg.arg2==2) //Если нет ошибок, то идем дальше
        			{        		
        				loading_text.setText("Выполняется обмен данными с сервером!\nПожалуйста подождите!");        				
        				ft_exchange2 = new FtpSendTask(
        	        			4,
        	        			getResources().getString(R.string.ftp_server),
        	        			user.getText().toString(),
        	        			password.getText().toString(),
        	        			directory.getText().toString(),
        	        			_currContext.getFilesDir().getAbsolutePath(),//new File(_currContext.getFilesDir(),directory.getText().toString()).getAbsolutePath(),
        	        			new Handler() { 
        	        				@Override
        	        				public void handleMessage(Message msg2) 
        	        				{
        	        					super.handleMessage(msg2);
        	        					
        	        					error.setText((String)msg2.obj);
        	                			
        	                			if(msg2.arg1==1) //ошибка
        	                			{
        	                				but_ok.setEnabled(true);
        	                	        	but_cancel.setEnabled(true);
        	                	        	directory.setEnabled(true);
        	                				error.setTextColor(android.graphics.Color.rgb(50, 128, 0));
        	                				loading.setVisible(false);
        	                				loading_text.setText("");
        	                			}
        	                			if(msg2.arg1==5) //инфо
        	                			{        				
        	                				error.setTextColor(android.graphics.Color.rgb(21, 182, 73));
        	                			}
        	                			if(msg2.arg1==0) //успех
        	                			{     
        	                				but_ok.setEnabled(true);
        	                	        	but_cancel.setEnabled(true);
        	                	        	directory.setEnabled(true);
        	                				error.setTextColor(android.graphics.Color.rgb(31, 182, 73));
        	                			}
        	                			if(msg2.arg1<0) //логгер
        	                				error.setTextColor(android.graphics.Color.rgb(189,25, 71));
        	                			
        	        					if(msg2.arg1==0&&msg2.arg2==2) //Если нет ошибок, то идем дальше
        	                			{
        	        						loading.setVisible(false);
        	        						loading_text.setText("Обмен данными с сервером выполнен!");
        	        					//if(anketa==null) 
        	            				//	anketa = MediaDeviceXCG.Load(MediaDeviceXCG.getDefaultFile(_currContext, "anketa"));        				        			
        	            				
        	            				if(keyPair==null)
        	            					keyPair = new util_login_pass();
        	            				keyPair.setLogin(user.getText().toString());
        	            				keyPair.setPass(password.getText().toString());
        	            				keyPair.setDir(directory.getText().toString());
        	            				
        	            				intent= new Intent(_currContext, ActivityStep2.class);
        	            				intent.putExtra("keypair", keyPair);
        	            	    		intent.putExtra("anketa",anketa);    		
        	            	    	    startActivity(intent);
        	                			}
        	        				};	
        	        			});
        	        	ft_exchange2.execute();        				
        			}
        			
        		}        		
        	};
        	
        	//but.setEnabled(false);
        	//user.setEnabled(false);
        	//password.setEnabled(false);
        	but_ok.setEnabled(false);
        	but_cancel.setEnabled(false);
        	directory.setEnabled(false);
        	
        	
        	ft_exchange = new FtpSendTask(
        			1,
        			getResources().getString(R.string.ftp_server),
        			user.getText().toString(),
        			password.getText().toString(),
        			directory.getText().toString(),
        			this.getFilesDir().toString(),
        			h_post);
        	loading.setVisible(true);
        	loading_text.setText("Проверка папки на сервере!");
        	ft_exchange.execute();    
        	
        	  	
        	
        	
        	
       	 break;
        	case R.id.but_cancel:
        		if(keyPair==null)
        		{
        			keyPair = new util_login_pass();        		
        			keyPair.setLogin(user.getText().toString());
        			keyPair.setPass(password.getText().toString());
        			keyPair.setDir(directory.getText().toString());
        		}
        		intent= new Intent(_currContext, ActivityStep2.class);
				intent.putExtra("keypair", keyPair);
	    		intent.putExtra("anketa",anketa);    		
	    	    startActivity(intent);
        		break;
               	
		}			
		
	}

}
