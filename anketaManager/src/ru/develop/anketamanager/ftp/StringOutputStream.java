
package ru.develop.anketamanager.ftp;

import java.io.IOException;
import java.io.OutputStream;

import android.widget.TextView;

public class StringOutputStream extends OutputStream {

	  StringBuilder mBuf;
	  TextView _tv;
	  public StringOutputStream(TextView tv)
	  {	  
		  mBuf=new StringBuilder();
	  }
	  
	  public void write(int _byte) throws IOException {
	    mBuf.append((char) _byte);	  
	    if(_tv!=null) 
	    synchronized(_tv)
	    {
	    _tv.setText(getString());
	    }	   
	  }

	  public String getString() {
	    return mBuf.toString();
	  }
	}
