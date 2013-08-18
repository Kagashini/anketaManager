package ru.develop.anketamanager.ftp;



import java.io.File;
import java.net.UnknownHostException;

import ru.develop.anketamanager.xml.MediaDeviceXCG;

import android.os.AsyncTask;
import android.widget.TextView;

 
public class FtpSendTask extends AsyncTask<String, Integer, Void> {
	  	TextView _progressInfoElement = null;
		String _cmd=null;
		public FtpSendTask(String ftp_command,TextView pie)
		{
			_progressInfoElement = pie;
			_cmd= ftp_command;
		    //"77.238.105.49 -l -h -k -n"
		}

		private void regInfo(String i)
		{
			_progressInfoElement.setText(i);
			_progressInfoElement.setTextColor(android.graphics.Color.rgb(50, 128, 0));
		}
		
		private void regError(String i)
		{
			_progressInfoElement.setText(i);
			_progressInfoElement.setTextColor(android.graphics.Color.rgb(0, 0, 0));
		}
		
	    @Override
	    protected void onPreExecute() {
	      super.onPreExecute();
	      regInfo("Получение файла с FTP-сервера! Пожалуйста подождите!");
	    }

	    @Override
	    protected Void doInBackground(String... urls) {
	    			
	    	try
	    	{
				//MyFTPClient client = new MyFTPClient(_progressInfoElement);				        
				// if(client.ftpConnect("62.213.82.99","crm_user", "nbkRen889", 21))
				// {
				//	 client.ftpDownload("anketa.xml",_progressInfoElement.getContext().getFilesDir()+"/anketa.xml");
				//	 client.ftpDisconnect();
				// }
	    		 FTPAgent.Exec(_cmd, new StringOutputStream(_progressInfoElement),this);
			     publishProgress();			  	 
	    	}
	    	catch(Exception exc)
	    	{
	    		regError(exc.getMessage());	    	
	    	}
	      return null;
	    }


	    @Override
	    protected void onProgressUpdate(Integer... values) {
	      super.onProgressUpdate(values);
	      //regInfo("Downloaded " + values[0] + " files");
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	      super.onPostExecute(result);
	      regInfo("Задача получение файла с FTP-сервера, завершена!");	      
	    }
	    
	    
	    protected void onCancelled() {
	        super.onCancelled();
	        regError("Выполнена отмена задачи получения файла с FTP-сервера!");
	        
	      }  
	
	  }
	  