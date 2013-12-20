package ru.develop.anketamanager.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ping {
  public static boolean Avaible(String host) throws IOException, InterruptedException
 {	  		    
		    String str = "";
		    try {
		        Process process = Runtime.getRuntime().exec(
		                "/system/bin/ping -c 8 "+host);
		        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		        int i;
		        char[] buffer = new char[4096];
		        StringBuffer output = new StringBuffer();
		        while ((i = reader.read(buffer)) > 0)
		            output.append(buffer, 0, i);
		        reader.close();
		        
		        str = output.toString();
		        // Log.d(TAG, str);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }	
	   return true;
 }
}
