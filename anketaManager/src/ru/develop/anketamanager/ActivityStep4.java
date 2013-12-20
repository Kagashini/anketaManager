package ru.develop.anketamanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.develop.anketamanager.ftp.FtpSendTask;
import ru.develop.anketamanager.utils.GifMovieView;
import ru.develop.anketamanager.utils.util_login_pass;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.AnketaAdditionInfo;
import ru.develop.anketamanager.xml.MediaDeviceXCG;
import ru.develop.anketamanager.xmlnew.Brand;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ActivityStep4 extends Activity implements OnClickListener{
	util_login_pass keyPair=null;
	ru.develop.anketamanager.xmlnew.Anketa anketa=null;
	Button but_prev;
	Button but_next;
	
	Button but_quit;
	Button but_send;
	TableLayout table;
	

	GifMovieView loading; 
	TextView loading_text;
	
	LinearLayout indicator;
	
	Context _currContext=null;   	 
	Intent intent=null;
	
	//EditText to_order;
	//EditText express_deliveries;
	//EditText amount_deliveries;
	//EditText intro_prodiuctlines;
	//EditText training_seminar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anketa_step4);
		_currContext = this;
		/*
		to_order = (EditText) findViewById(R.id.to_order);
		express_deliveries = (EditText) findViewById(R.id.express_deliveries);
		amount_deliveries = (EditText) findViewById(R.id.amount_deliveries);
		intro_prodiuctlines = (EditText) findViewById(R.id.intro_prodiuctlines);
		training_seminar = (EditText) findViewById(R.id.training_seminar);
		*/
		 but_next = (Button)findViewById(R.id.but_next);
			if(but_next!=null)
				but_next.setOnClickListener(this);
			but_prev = (Button)findViewById(R.id.but_prev);
			if(but_prev!=null)
				but_prev.setOnClickListener(this);
			
			but_quit = (Button)findViewById(R.id.but_quit);
			if(but_quit!=null)
				but_quit.setOnClickListener(this);
			
			but_send = (Button)findViewById(R.id.but_Send);
			if(but_send!=null)
				but_send.setOnClickListener(this);
				
			indicator = (LinearLayout)findViewById(R.id.indicator);
			indicator.setVisibility(8);
			
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
			
			
		   table = (TableLayout) findViewById(R.id.table_brands);		   
			
		   anketa = (ru.develop.anketamanager.xmlnew.Anketa)getIntent().getSerializableExtra("anketa");
		   keyPair = (util_login_pass)getIntent().getSerializableExtra("keypair");
		   
		   
		   if(anketa!=null)
		   {					  
			   OnFocusChangeListener fl= new OnFocusChangeListener() {

	                @Override
	                public void onFocusChange(View v, boolean hasFocus) {
	                    if(!hasFocus){
	                      try
	                      {
	                    	Object tag = v.getTag();
	                    		          
	                    	Brand  b = null;
	                        if("ru.develop.anketamanager.xmlnew.Brand".equals(tag.getClass().getName()))
	                        {
	                        	b =(Brand)tag;
	                        	b.setValue(
	                        			((EditText)v).getText().toString()	                        
	                        			);
	                        }
	                        else
	                        {
	                        	String[] nc=(String[])v.getTag();
	                        	if(nc!=null&&nc.length==2)
	                        	{
	                        	b = new Brand();
	                        	b.setName(nc[0]);
	                        	b.setColumn(nc[1]);
	                        	b.setValue(((EditText)v).getText().toString());
	                        	anketa.getBrands().add(b);
	                        	}
	                        }
	                      }
	                      catch(Exception e)
	                      {
	                    	  
	                      String esdf=e.getMessage();
	                      }
	                    }
	                }
	            };
			   List<String> columns = new ArrayList<String>();
			   List<String> names = new ArrayList<String>();			   
			   for(Brand brand: anketa.getBrands())
			   {
				   String column =brand.getColumn();
				   String name   =brand.getName();
				   if(!columns.contains(column))
					   columns.add(column);
				   if(!names.contains(name))
					   names.add(name);
			   }
			   
			   table.setStretchAllColumns(true);
			   
			   TableRow header = new TableRow(this);
			   TextView header_caption_name = new TextView(this);
			   header_caption_name.setText("Бренд");
			   header.addView(header_caption_name);
			   for(String c:columns)
			   {				   
				   TextView header_caption = new TextView(this);
				   header_caption.setText(c);
				   header_caption.setPadding(10,10, 10, 10);
				   header.addView(header_caption);				   
			   }
			   table.addView(header);
			   
			   
			   for(String n: names)
			   {
				   TableRow row = new TableRow(this);
				   TextView header_caption = new TextView(this);
				   header_caption.setText(n);
				   row.addView(header_caption);
				   
				   for(String c:columns)
				   {				   
					   EditText cell = new EditText(this);
					   boolean is_find = false; 
					   cell.setPadding(10,10, 10, 10);
					   for(Brand brand: anketa.getBrands())
					   {						   
						   if(c.equals(brand.getColumn())&&n.equals(brand.getName()))
						   {
							  cell.setText(brand.getValue());							  
							  cell.setTag(brand);							  							  
							  is_find = true;
						   }
					   }					
					   
					   if(!is_find)
						   cell.setTag(new String[] { n,c });					   
					   cell.setOnFocusChangeListener(fl);
					   row.addView(cell);
				   }				   
				   table.addView(row);
			   }
		   }
	}

	/*private void setData()
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
	}*/
	
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
		
		switch (v.getId()) {
        case R.id.but_next:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep5.class);
    		//setData();
    		intent.putExtra("anketa",anketa);
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
       	 break;
        case R.id.but_prev:
        	// TODO Auto-generated method stub
    		intent= new Intent(this, ActivityStep3.class);
    		//setData();
    		intent.putExtra("anketa",anketa);
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
       	 	break;       
        case R.id.but_quit:
        	intent= new Intent(this, ActivityStep2.class);
    		//setData();
    		intent.putExtra("anketa",anketa);
    		intent.putExtra("keypair", keyPair);
    	    startActivity(intent);
        	break;
        	
        case R.id.but_Send:
        	File file_xml = new File(this.getFilesDir(),keyPair.getDir()+"/"+FtpSendTask.outbox+"/"+keyPair.getFile());
        	ru.develop.anketamanager.xmlnew.Anketa.Save(file_xml, anketa);
        	if(file_xml.exists())
        	{
        	FtpSendTask ft = new FtpSendTask(
        			4,
        			getResources().getString(R.string.ftp_server),
        			keyPair.getLogin(),
        			keyPair.getPass(),
        			keyPair.getDir(),
        			this.getFilesDir().getAbsolutePath(),
        			new Handler() { 
        				@Override
        				public void handleMessage(Message msg2) 
        				{
        					super.handleMessage(msg2);
        					        					
                			if(msg2.arg1==1) //ошибка
                			{
                				table.setVisibility(0);
                				indicator.setVisibility(8);
                	        	but_quit.setEnabled(true);
                	        	but_prev.setEnabled(true);
                	        	but_send.setEnabled(true);
                				//error.setTextColor(android.graphics.Color.rgb(50, 128, 0));
//                				loading.setVisible(false);
//                				loading_text.setText("");
                			}
                			if(msg2.arg1==5) //инфо
                			{        				
  //              				error.setTextColor(android.graphics.Color.rgb(21, 182, 73));
                			}
                			if(msg2.arg1==0) //успех
                			{                     				
                				//error.setTextColor(android.graphics.Color.rgb(31, 182, 73));
                			}
                			//if(msg2.arg1<0) //логгер
                			//	error.setTextColor(android.graphics.Color.rgb(189,25, 71));
                			
        					if(msg2.arg1==0&&msg2.arg2==2) //Если нет ошибок, то идем дальше
                			{
        						loading.setVisible(false);
        						loading_text.setText("Обмен данными с сервером выполнен!");
        					//if(anketa==null) 
            				//	anketa = MediaDeviceXCG.Load(MediaDeviceXCG.getDefaultFile(_currContext, "anketa"));        				        			
            				
        						table.setVisibility(0);
        						indicator.setVisibility(8);
                	        	but_quit.setEnabled(true);
                	        	but_prev.setEnabled(true);
                	        	but_send.setEnabled(true);
                	        	                	        	
                			}
        				};	
        			});
        	loading.setVisible(true);
        	loading_text.setText("Выполняется обмен данными с сервером!\nПожалуйста подождите!");
        	table.setVisibility(8);
        	indicator.setVisibility(0);
        	but_quit.setEnabled(false);
        	but_prev.setEnabled(false);
        	but_send.setEnabled(false);
        	ft.execute();      
        	}
        	break;
		}			
		
	}
}
