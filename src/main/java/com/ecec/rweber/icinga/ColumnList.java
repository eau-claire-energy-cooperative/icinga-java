package com.ecec.rweber.icinga;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ecec.rweber.icinga.attributes.IcingaAttribute;

public class ColumnList extends ArrayList<IcingaAttribute>{
	private static final long serialVersionUID = 7164058207663265444L;

	public JSONArray getAttributes() {
		JSONArray result = new JSONArray();
		
		for(int count = 0; count < this.size(); count ++)
		{
			result.add(this.get(count).getAttribute());
		}
		
		return result;
	}

	public JSONArray getJoins(){
		JSONArray result = new JSONArray();
		
		for(int count = 0; count < this.size(); count ++)
		{
			result.add(this.get(count).getFullAttribute());
		}
		
		return result;
	}
	
	public static String printColumns(ResponseRow aRow, ColumnList list){
		String result = "";
		
		String value = null;
		for(int count = 0; count < list.size(); count ++)
		{
			if(aRow.containsKey(list.get(count)))
			{
				value = aRow.getObject(list.get(count)).toString();
				result = result + value;
				
				int tabs = 4-value.length() /8;
				
				while(tabs >= 0)
				{
					result = result + "\t";
					tabs = tabs -1;
				}
			}
			else
			{
				result = result + "\t\t\t\t";
			}
		}
		
		return result;
	}
}
