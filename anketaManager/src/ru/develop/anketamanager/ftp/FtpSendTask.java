package ru.develop.anketamanager.ftp;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

 
public class FtpSendTask extends AsyncTask<Void, Integer, Void> {
	  	
		int _type=0;
		String _host=null;
		String _user=null;
		String _password=null;
		String _remote=null;
		String _local=null;
		Handler _callback = null;
		
		
		String task_name = "получения данных";
		
		public FtpSendTask(int type, String host,String user,String password,String remote,String local,Handler callback)
		{	
			this._type = type;
			this._host = host;
			this._user = user;
			this._password = password;
			this._remote = remote;
			this._local = local;
			
			_callback = callback;
			
		}
	
		
		
		
		
		//Получить имя задачи
		private String getTaskName()
		{
			return String.format("%s%s", String.valueOf(task_name.charAt(0)).toUpperCase(),task_name.substring(1));
		}
		
		
		private void regInfo(String i)
		{						
			Message msg = new Message();
			msg.obj = i;
			msg.arg1 = 5;
			_callback.sendMessage(msg);
			
		}
		
		private void regError(String i)
		{
			Message msg = new Message();
			msg.obj = i;
			msg.arg1 = 1;
			msg.arg2 = 1;
			_callback.sendMessage(msg);
		}
		
		
		private void regSuccess(String i)
		{						
			Message msg = new Message();
			msg.obj = i;
			msg.arg1 = 0;
			msg.arg2 = 2;
			_callback.sendMessage(msg);
			
		}
		
		
		boolean resultFtp=false;
		boolean resultFtpIncorrectAuth=false;

		
		
		
		//проверка наличия удаленного каталога
		public void CheckRemoteDirectory()
		{	
			task_name = "получение данных";
			StringOutputStream s_ = new StringOutputStream(_callback);		  
			String _cmd=String.format("%s %s %s %s","-l",_host,_user,_password,_remote);
			
			try
			{
				FTPAgent.Exec(_cmd,s_,this);
				resultFtpIncorrectAuth = s_.get_IncorrectLogin();
  		  //   publishProgress();			  	 
	    	}
	    	catch(Exception exc)
	    	{	    	    
	    		regError(exc.getMessage());	    	
	    	}
	    	
			
		}
		
		//загрузка файлов с FTP-сервера в локальный каталог
		public void DownloadFiles()
		{
			task_name = "получение данных";
			StringOutputStream s_ = new StringOutputStream(_callback);		  
			String _cmd=String.format("%s %s %s %s","-l",_host,_user,_password,this._remote);
			
			try
			{
				FTPAgent.Exec(_cmd,s_,this);
				resultFtpIncorrectAuth = s_.get_IncorrectLogin();
				if(!resultFtpIncorrectAuth)
				{
					String i= s_.mBuf.toString();
					List<String> result = new ArrayList<String>();					
					java.util.regex.Pattern regex = java.util.regex.Pattern.compile("\\d*");
					java.util.regex.Matcher mc= regex.matcher(i);
					while(!mc.hitEnd())
					{
						result.add(
								i.substring(mc.end(0),mc.end(0))
						);
					}			
				}
  		  //   publishProgress();			  	 
	    	}
	    	catch(Exception exc)
	    	{	    	    
	    		regError(exc.getMessage());	    	
	    	}
		}
		
		//Выгрузка файлов на сервер FTP с слокального каталога
		public void UploadFiles()
		{			
			task_name = "отправка данных";			
			StringOutputStream s_ = new StringOutputStream(_callback);		  
			String _cmd=String.format("%s %s %s %s","-l",_host,_user,_password,_remote);
			
			try
			{
				FTPAgent.Exec(_cmd,s_,this);
				resultFtpIncorrectAuth = s_.get_IncorrectLogin();
				if(!resultFtpIncorrectAuth)
				{
					File dir_local = new File(_local);
					if(dir_local.isDirectory())
					{
						File[] files_local = dir_local.listFiles();
						for(int f=0;f<files_local.length;f++)
						{
							_cmd=String.format("%s %s %s %s","-b -s",_host,_user,_password,files_local[f].getAbsoluteFile());
							FTPAgent.Exec(_cmd,s_,this);
						}
					}
				}
  		  //   publishProgress();			  	 
	    	}
	    	catch(Exception exc)
	    	{	    	    
	    		regError(exc.getMessage());	    	
	    	}
		}
		
		
	    @Override
	    protected void onPreExecute() {
	      super.onPreExecute();
	      //regInfo("Получение файла с FTP-сервера! Пожалуйста подождите!");
	      regInfo(String.format("%s с FTP-сервера!\nПожалуйста подождите!",getTaskName()));
	    }

	    @Override
	    protected Void doInBackground(Void... urls) {
	    		
	    			switch(_type)
	    			{
	    				case 0:
	    					CheckRemoteDirectory();
	    				break;
	    				case 1:
	    					DownloadFiles();
	    					break;
	    				case 2:
	    					UploadFiles();
	    					break;
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
	      if(resultFtp)
	    	  regSuccess(String.format("Задача %s с FTP-сервера, завершена успешно!",getTaskName()));
	      else 
	    	  if(resultFtpIncorrectAuth)
	    		  regError("Неверный логин или пароль!");
	      //else regError("Задача получение файла с FTP-сервера, завершена!");	      
	    }
	    
	    
	    protected void onCancelled() {
	        super.onCancelled();
	        regError(String.format("Выполнена отмена задачи %s с FTP-сервера!",getTaskName()));
	        
	      }  
	
	  }
	  