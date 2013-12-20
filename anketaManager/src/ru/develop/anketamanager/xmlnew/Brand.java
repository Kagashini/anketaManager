package ru.develop.anketamanager.xmlnew;
import java.io.Serializable;

public class Brand implements Serializable {

	   protected String name;
	   protected String column;
	   protected String value;

	   
	   public String getName() {   return name;  }
	   public void setName(String value) { this.name = value;  }
	   
	   public String getColumn() {   return column;  }
	   public void setColumn(String value) { this.column = value;  }
	   
	   public String getValue() {   return value;  }
	   public void setValue(String value) { this.value = value;  }

}
