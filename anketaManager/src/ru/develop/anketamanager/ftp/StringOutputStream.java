
package ru.develop.anketamanager.ftp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPFile;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class StringOutputStream extends OutputStream {

	  StringBuilder mBuf;
	  Handler _callback;
	  
	  int flag_execute=0;
	  
	  public StringOutputStream(Handler h)
	  {	  
		  mBuf=new StringBuilder();
		  _callback=h;
	  }
	  
	 public void set_Success()
     {
		  flag_execute = flag_execute|2;
	 }
	  
	 public void set_Fail()
	 {
	  flag_execute = flag_execute|1;
	 }
	 
	 public void set_IncorrectLogin()
	 {
	  flag_execute = flag_execute|3;
	 }
	 
	 public boolean get_IncorrectLogin()
	 {
	  return flag_execute == 3;
	 }
	 
	 public boolean get_Fail()
	 {
	  return flag_execute == 1;
	 }
	 
	 int code_reply_cmd=0;	 
	 
	 public void hold_reply(int s)
	 {		 
		 code_reply_cmd = s;
	 }
	 
	 public boolean checkChangeDirSuccess()
	 {
		 return code_reply_cmd==250;		 
	 }
	 
	 
	 List<String> _listFiles = new ArrayList<String>();
     public void BeginListFiles()
     {
    	 _listFiles.clear(); 
     }

     
     public void AddListFiles(String filename)
     {
    	 _listFiles.add(filename);     
     }

     public String [] getFileList()
     {
    	 String [] result = new String[_listFiles.size()];
    	 for(int r=0;r<result.length;r++)
    		 result[r]=_listFiles.get(r);
    	 return result;
     }
	 
	 public void info(String text)
	 {
		 if(_callback!=null) 
			    synchronized(_callback)
			    {
		Message msg = new Message();
		msg.obj = text;
		msg.arg1 = 5;
		msg.arg2 = flag_execute;
		_callback.sendMessage(msg);
			    }
	 }
	  
	 
	 public void error(String text)
	 {
		 if(_callback!=null) 
			    synchronized(_callback)
			    {
		Message msg = new Message();
		msg.obj = text;
		msg.arg1 = 1;
		msg.arg2 = flag_execute;
		_callback.sendMessage(msg);
			    }
	 }
	  
	  public void write(int _byte) throws IOException {
	    mBuf.append((char) _byte);	  
	    if(_callback!=null) 
	    synchronized(_callback)
	    {
	    	Message msg = new Message();
			msg.obj = getString();
			msg.arg1 = -1;
			msg.arg2 = flag_execute;
			_callback.sendMessage(msg);	    
	    }	   
	  }

	  public String getString() {
	    return mBuf.toString();
	  }
	}
