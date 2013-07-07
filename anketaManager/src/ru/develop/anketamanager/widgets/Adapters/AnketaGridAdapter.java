package ru.develop.anketamanager.widgets.Adapters;



import java.util.List;

import ru.develop.anketamanager.widgets.PropRowObject;
import ru.develop.anketamanager.xml.Anketa;
import ru.develop.anketamanager.xml.AnketaProductInfo;
import ru.develop.anketamanager.xml.AnketaProductInfoExt;
import android.app.Activity;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AnketaGridAdapter implements OnClickListener,OnEditorActionListener {

	
	private Activity context=null;
	TableLayout table = null;
	private List<AnketaProductInfo> anketaPil = null;	
	
	
	public AnketaGridAdapter(Activity c,TableLayout t,Anketa a)
	{
	  this.table = t;		
	  this.context = c;
	  this.anketaPil = a.getProductRanges();	
	  if(this.anketaPil!=null&&this.anketaPil.size()>0)
		this.columns = PropRowObject.getProps(anketaPil.get(0),true);
	  else 
	    this.columns = PropRowObject.getProps(new AnketaProductInfo(),true);
	  
	  _correct();	  	
	  
	  TableRow header = new TableRow(context);
	  
	  TextView plus_cell = new TextView(context);
	  plus_cell.setWidth(25);
	  header.addView(plus_cell);
	  for(int ci=0;ci<this.columns.length;ci++)	  
	  {
		  TextView hrc = new TextView(context);
		  hrc.setWidth(85);
		  hrc.setText(nameCol(ci++));
		  hrc.setTextColor(Color.BLUE);
		  header.addView(hrc);
	  }
	  table.addView(header);
	  
	  
	  for(int r=0;r<getCount();r++)
	  {
		  TableRow row = new TableRow(context);
		  Button plus_cell2 = new Button(context);
		  plus_cell2.setWidth(5);
		  plus_cell2.setHeight(5);
		  plus_cell2.setText("+");		  
		  plus_cell2.setOnClickListener(this);
		  row.addView(plus_cell2);
		  for(int ci=0;ci<this.columns.length;ci++)	  
		  {
			  EditText cell = new EditText(context);
			  cell.setOnEditorActionListener(this);
			  cell.setWidth(85);
			  cell.setText(getVal(ci,r));			  
			  row.addView(cell);
		  }  
		  table.addView(row);
	  }
	}
	
	PropRowObject [] columns = null; 
	
	@Override
	public void onClick(View v) {
		TableRow _row = new TableRow(context);
		 Button plus_cell2 = new Button(context);
		  plus_cell2.setWidth(5);
		  plus_cell2.setHeight(5);
		  plus_cell2.setText("+");		  
		  plus_cell2.setOnClickListener(this);
		  _row.addView(plus_cell2);
		table.addView(_row);
	}
	
	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		
		return false;
	}

	///Корректировка
	
	private int getExtColumnsCount()
	{
	  int res = 0;
	  if(anketaPil!=null)
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
	  if(anketaPil!=null)
	  for(AnketaProductInfo api:anketaPil)
	  {
		 int j=0;
		 if(api.getExtensions()!=null)
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
	
	
	String getVal(int ci,int r)
	{
		if(anketaPil!=null)
		{
			AnketaProductInfo pi=anketaPil.get(r);
			if(ci==0)
				return pi.getAdv();
			if(ci==1)
				return pi.getAvailability();
			if(ci==2)
				return pi.getBrand();
			if(ci==3)
				return pi.getComment();
			if(ci==4)
				return pi.getStands();
			if(ci==5)
				return pi.getTurnover().toString();
			
			List<AnketaProductInfoExt> ext = pi.getExtensions();
			if(ext!=null&&ext.size()>(5+ci))
			{
				return ext.get(5+ci).getValue();
			}
				
		}
		return "";
	}
	
	
	private String  nameCol(int col)
	{
		if(col==0)	    		
    		return "Реклама";    	
		if(col==1)	    		
    		return "Наличие";    	
		if(col==2)
    		return "Бренд";
		if(col==3)	    		
    		return "Коментарий";    	
		if(col==4)	    		
    		return "Стенды";    	
		if(col==5)
    		return "Оборот за";
    	return columns[col].Name;    	   
	}
	
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
	    
	    if(anketaPil!=null)
	    {
	    Object rowObject =  anketaPil.get(row);
	    String propName = columns[col].Name;	    
	    Object _val = PropRowObject.getPropValue(rowObject,propName);	    
	    res = _val!=null?_val.toString():res;
	    }
		}
		return res;
	}
	
	
	
	
	public int getCount() 
	{		
		if(anketaPil!=null)
		return getColumns()*anketaPil.size()+1;
		return 1;
	}
	 
	
	public int getColumnsCount()
	{		
	  return columns!=null?columns.length:0;
	}
}
