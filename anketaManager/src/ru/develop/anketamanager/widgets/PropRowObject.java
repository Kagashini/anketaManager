package ru.develop.anketamanager.widgets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;



public class PropRowObject 
	{
	   public String Name;
	   public boolean CanWrite;
	   public boolean CanRead;
	   

		//Получим свойства
		public static PropRowObject [] getProps(Object rowObject,boolean sort)
		{
			List<PropRowObject> res= new ArrayList<PropRowObject >(); 
			Dictionary<String,PropRowObject > res_tmp= new Hashtable<String,PropRowObject >(); 		
			Method [] ms = rowObject.getClass().getDeclaredMethods();
			for(Method m:ms)
			{			
				String _name =m.getName();
				PropRowObject  p = res_tmp.get(_name);			
				p=p==null? new PropRowObject():p;			
				p.CanRead = _name.startsWith("get");
				p.CanWrite = _name.startsWith("set");
				if(p.CanRead||p.CanWrite)
				{
					p.Name = _name;
					res_tmp.put(_name, p);
					res.add(p);
				}
				
			}
			
			PropRowObject  [] r = new PropRowObject[res.size()];
			res.toArray(r);
			if(sort)
			Arrays.sort(r,new Comparator<PropRowObject>() {
				@Override
				public int compare(PropRowObject lhs, PropRowObject rhs) 
				{
					return lhs.Name.compareTo(rhs.Name);
				};
			});
			return r;
		}

		//Получим значение свойства
		public static Object getPropValue(Object rowObject,String propName) 
		{
			 Object res = null;
			 if(rowObject==null)
				 return res;
			 Method m = null;
			try {
				m = rowObject
						 			.getClass()
						 			.getMethod(propName);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 if(m!=null)
				try {
					res = m.invoke(rowObject, new Object[]{});
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 return res;
		}
	}
	