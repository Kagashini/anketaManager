package ru.develop.anketamanager.widgets.Adapters;



import java.util.List;

import ru.develop.anketamanager.widgets.PropRowObject;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.AnketaProductInfo;
import ru.develop.anketamanager.xml.AnketaProductInfoExt;
import android.app.Activity;
import android.graphics.Color;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class AnketaGridAdapter extends BaseAdapter {

	
	private Activity context=null;
	private List<AnketaProductInfo> anketaPil = null;	
	
	public AnketaGridAdapter(Activity c,Anketa a)
	{
	  this.context = c;
	  this.anketaPil = a.getProductRanges();	  
	  this.columns = PropRowObject.getProps(anketaPil,false);
	  _correct();
	}
	
	PropRowObject [] columns = null; 
	
	
	
	

	///Корректировка
	
	private int getExtColumnsCount()
	{
	  int res = 0;
	  for(AnketaProductInfo api:anketaPil)
	  {
		 int s=api.getExtensions().size();
	     res = s>res?s:res;
	  }
	  return res;
	}
	
	private PropRowObject [] getExtColumns()
	{
	  PropRowObject [] res = new PropRowObject[getExtColumnsCount()];
	  for(AnketaProductInfo api:anketaPil)
	  {
		 int j=0;
		 for(AnketaProductInfoExt apie:api.getExtensions())
		 {
			 res[j] = new PropRowObject();
			 res[j].Name = apie.getName();
		 }		 
	  }
	  if(columns!=null)
	  {
		  PropRowObject [] union = new PropRowObject[res.length+columns.length];
		  int j=0;
		  for(PropRowObject p:columns)
			  union[j++] = p;
		  for(PropRowObject p:res)
			  union[j++] = p;
		  columns = union;
	  }
	  return res;
	}
	
	
	
	private void _correct()
	{
		  offset_checker =this.columns.length;  
		  getExtColumns();
	}
	
	int offset_checker=0;
	private boolean checkExtIsNeed(int position)
	{
		int col = position % (columns!=null?columns.length-offset_checker:1); 				 
		int row = position / (columns!=null?columns.length-offset_checker:1);
		return 
				(row>0&&
				 col>0&&
				 col-offset_checker>=0&&			
			     anketaPil.get(row).getExtensions().get(col-offset_checker)!=null);
  }
	
	
	
	
	///Корректировка
	
	
	
	
	private int getColumns()
	{
	  return columns!=null?columns.length:0;
	}
	
	private int GetCol(int pos){ 
	    int col = pos % (columns!=null?columns.length:1); 
	    return col; 
	} 
	private int GetRow(int pos){ 
	    int row = pos / (columns!=null?columns.length:1); 
	    return row; 
	} 
	
	
	private String PositionToString(int pos)
	{ 
		String res = "<Пусто>"; 
		if(columns!=null)
		{
	    int row = GetRow(pos); 
	    int col = GetCol(pos);
	   
	    
	    if (row == 0) { 
	        return columns[row].Name; 
	    } 
	    Object rowObject =  anketaPil.get(row);
	    String propName = columns[col].Name;	    
	    Object _val = PropRowObject.getPropValue(rowObject,propName);	    
	    res = _val!=null?_val.toString():res;
		}
		return res;
	}
	
	
	
	@Override
	public int getCount() 
	{		
		return getColumns()*anketaPil.size();		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		int row = GetRow(position); 
		   

	    // Get our text for position 
	    String item =PositionToString(position);

	    

	    // find if we have a control for the cell, if not create it 
	    EditText txtName = null;
	    txtName = ((EditText)convertView)==null ? new EditText(context):txtName; 
	    //txtName.setEnabled(checkExtIsNeed(position));

	    // Если первая строка, то заголовок 
	    if (row == 0) 
	    { 	    	
	        txtName.setBackgroundColor(Color.rgb(0x0d,0x12,0xf5)); 
	        txtName.setTextColor(Color.rgb(0x0,0x0,0x0)); 
	    } 
	   

	    //Assign item's values to the various subviews 
	    txtName.setText(item, TextView.BufferType.NORMAL); 
	   

	    //Finally return the view 
	    return txtName; 
	}
	
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return PositionToString(position);
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	 
	
	public int getColumnsCount()
	{		
	  return columns!=null?columns.length:0;
	}
}
