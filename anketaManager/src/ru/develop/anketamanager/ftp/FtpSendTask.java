package ru.develop.anketamanager.ftp;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.ls.LSInput;

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
		private void Ping()
		{	
					task_name = "проверка доступа к серверу";
					StringOutputStream s_ = new StringOutputStream(_callback);		  
					String _cmd=String.format("%s %s %s %s %s","-c PWD",_host,_user,_password,_remote);
					
					try
					{
						FTPAgent.Exec(_cmd,s_,this);						
						Message msg = new Message();
						msg.arg1 = s_.get_Fail()?1:0;
						if(msg.arg1!=1)
						msg.arg2 = 2;
						_callback.sendMessage(msg);						 
		  		  //   publishProgress();			  	 
			    	}
			    	catch(Exception exc)
			    	{	    	    
			    		regError(exc.getMessage());	    	
			    	}					
		}
				
		
		
		//проверка наличия удаленного каталога
		private void CheckRemoteDirectory()
		{	
			task_name = "получение данных";
			StringOutputStream s_ = new StringOutputStream(_callback);		  
			String _cmd=String.format("%s %s %s %s %s","-c CWD",_host,_user,_password,_remote);
			
			try
			{
				FTPAgent.Exec(_cmd,s_,this);
				resultFtpIncorrectAuth = s_.get_IncorrectLogin();
				if(resultFtpIncorrectAuth || !s_.checkChangeDirSuccess())
					regError(s_.getString());
				else
					regSuccess("");
  		  //   publishProgress();			  	 
	    	}
	    	catch(Exception exc)
	    	{	    	    
	    		regError(exc.getMessage());	    	
	    	}
	    	
			
		}
		
		public static String outbox="Outbox";
		public static String inbox="Inbox";
		
		//загрузка файлов с FTP-сервера в локальный каталог
		private boolean DownloadFiles()
		{
			boolean result = false;
			task_name = "получение данных";
			StringOutputStream s_ = new StringOutputStream(_callback);		  		
			String _cmd=String.format("%s %s %s %s %s","-l",_host,_user,_password,this._remote);
			//String _cmd=String.format("%s %s %s %s %s","-c OPTS",_host,_user,_password,"UTF8 ON");
			 
			
			try
			{						 
				result = FTPAgent.Exec(_cmd,s_,this);
				// _cmd=String.format("%s %s %s %s %s","-b -l",_host,_user,_password,this._remote+"/"+inbox);
				//FTPAgent.Exec(_cmd,s_,this);
				resultFtpIncorrectAuth = s_.get_IncorrectLogin();
				if(!resultFtpIncorrectAuth)
				{
					File dir_local = new File(_local);					
					
					if(!dir_local.exists())
						dir_local.mkdirs();
					
					String [] list_files= s_.getFileList();
					String local;
					String remote;
					for(int f=0;f<list_files.length;f++)
					{
						local=dir_local.getAbsolutePath()+"/"+list_files[f];
						remote=this._remote+"\\"+list_files[f];
						local = local.replace(' ','^');
						remote = remote.replace(' ','^');
						_cmd=String.format("%s %s %s %s %s %s","-b",_host,_user,_password,remote,local);						
						FTPAgent.Exec(_cmd,s_,this);						
					}
				}
  		  //   publishProgress();			  	 
	    	}
	    	catch(Exception exc)
	    	{	    	    
	    		regError(exc.getMessage());	    	
	    	}
			return result;
		}
		
		//Выгрузка файлов на сервер FTP с слокального каталога
		private boolean UploadFiles()
		{	
			boolean result = false;
			task_name = "отправка данных";			
			StringOutputStream s_ = new StringOutputStream(_callback);		  
			String _cmd=String.format("%s %s %s %s %s","-l",_host,_user,_password,_remote);
			
			try
			{
				result =  FTPAgent.Exec(_cmd,s_,this);
				resultFtpIncorrectAuth = s_.get_IncorrectLogin();
				if(!resultFtpIncorrectAuth)
				{
					File dir_local = new File(_local);
					if(dir_local.exists()&&dir_local.isDirectory())
					{
						File[] files_local = dir_local.listFiles();
						String remote;
						String local;
						for(int f=0;f<files_local.length;f++)
						{
							remote = _remote+"\\"+files_local[f].getName();
							local = files_local[f].getAbsolutePath();
							_cmd=String.format("%s %s %s %s %s %s","-b -s",_host,_user,_password,remote,local);
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
			return result;
		}
					
		
		//обмен файлами
		private boolean ExchangeFiles()
		{
			boolean result = false;
			
		
			String local = this._local;
			String remote = this._remote;
			
			this._local = new File(new File(local),remote+"/"+outbox).getAbsolutePath();
			this._remote = "\\"+ remote+"\\"+outbox;
			File dir_local=new File(this._local);
			if(dir_local.exists())
			{				
				//Если исходящие отправлены
				if(UploadFiles())
				{								
					
					//То получим входящие
					this._local = new File(new File(local),remote+"/"+inbox).getAbsolutePath();
					
					
					//Удалим файлы из папки входящих
					File _inbox = new File(this._local);
					if(_inbox.exists())
					{
						File  [] files_to_delete = _inbox.listFiles();
						for(int f=0;f<files_to_delete.length;f++)
						{
							try
							{
							files_to_delete[f].delete();
							}
							catch(Exception e)
							{
								
							}
						}
					}
					
					this._remote = "\\"+remote+"\\"+inbox;
					result = DownloadFiles();				
				}
			}
			else
			{
				//Получим входящие
				this._local = new File(new File(local),remote+"/"+inbox).getAbsolutePath();
				
				//Удалим файлы из папки входящих
				File _inbox = new File(this._local);
				if(_inbox.exists())
				{
					File  [] files_to_delete = _inbox.listFiles();
					for(int f=0;f<files_to_delete.length;f++)
					{
						try
						{
						files_to_delete[f].delete();
						}
						catch(Exception e)
						{
							
						}
					}
				}
				this._remote = "\\"+ remote+"\\"+inbox;
				result = DownloadFiles();				
			}
			
			//Удалим файлы из папки исходящих
			if(dir_local.exists()&&result)
			{
				File  [] files_to_delete = dir_local.listFiles();
				for(int f=0;f<files_to_delete.length;f++)
				{
					try
					{
					files_to_delete[f].delete();
					}
					catch(Exception e)
					{
						
					}
				}
			}
			
			this._local  = local;
			this._remote = remote;
			
			regSuccess("Обмен данными завершен");
			return result;
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
	    					Ping();
	    					break;
	    				case 1:
	    					CheckRemoteDirectory();
	    				break;
	    				case 2:
	    					DownloadFiles();
	    					break;
	    				case 3:
	    					UploadFiles();
	    					break;
	    				case 4:
	    					ExchangeFiles();
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
	  