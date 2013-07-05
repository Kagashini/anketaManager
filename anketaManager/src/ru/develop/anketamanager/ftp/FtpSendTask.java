package ru.develop.anketamanager.ftp;



import java.net.UnknownHostException;

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
	      _progressInfoElement.setText("Begin");
	    }

	    @Override
	    protected Void doInBackground(String... urls) {
	    	 try
				{			
					FTPAgent.Exec(_cmd, new StringOutputStream(_progressInfoElement),this);
					//publishProgress();
				}
				catch(UnknownHostException exc)
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
	      regInfo("End");
	    }
	    
	    
	    protected void onCancelled() {
	        super.onCancelled();
	        regError("Cancel");
	        
	      }  
	
	  }
	  