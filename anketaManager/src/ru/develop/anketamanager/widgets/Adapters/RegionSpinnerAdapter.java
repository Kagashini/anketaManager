package ru.develop.anketamanager.widgets.Adapters;

import java.util.List;

import ru.develop.anketamanager.xml.References;
import android.R;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Region;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class RegionSpinnerAdapter extends BaseAdapter implements SpinnerAdapter
		 {

	Activity context=null;
	References references=null;
	public RegionSpinnerAdapter(Activity c,References refs)
	{
		context = c;
		references=refs;
	}
	
	@Override
    public int getCount() {
		if(references!=null)
		{
		  List<ru.develop.anketamanager.xml.Region> regs = references.getRegions();
		  if(regs!=null)
		  {
			  return regs.size();
		  }		  
		}
		return 0;
    }

    @Override
    public Object getItem(int pos) {
        // TODO Auto-generated method stub    	
    	if(references!=null)
		{
		  List<ru.develop.anketamanager.xml.Region> regs = references.getRegions();
		  if(regs!=null)
		  {
			  return regs.get(pos);		      
		  }		  
		}    	
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(context);
        if(references!=null)
		{
		  List<ru.develop.anketamanager.xml.Region> regs = references.getRegions();
		  if(regs!=null)
		  {		
		      //text.setTextColor(Color.BLACK);
		      text.setText(regs.get(position).getНаименование().toString());		   
		  }		  
		}
        return text;
    }

	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if(references!=null)
		{
		  List<ru.develop.anketamanager.xml.Region> regs = references.getRegions();
		  if(regs!=null)
		  {
			  TextView text = new TextView(context);
		      //text.setTextColor(Color.BLACK);
		      text.setText(regs.get(position).getНаименование().toString());
		      return text;
		  }		  
		}
		return null;
	}
	
}
