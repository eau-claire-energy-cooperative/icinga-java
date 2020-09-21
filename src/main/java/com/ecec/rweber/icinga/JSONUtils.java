package com.ecec.rweber.icinga;

import org.json.simple.JSONArray;

public abstract class JSONUtils {

	public static JSONArray createArray(String ... params){
		JSONArray result = new JSONArray();
		
		for(int count = 0; count < params.length; count ++)
		{
			result.add(params[count]);
		}
		
		return result;
	}

}
