package com.ecec.rweber.icinga.filter;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class JoinFilter extends IcingaFilter{
	private String m_type = null;
	private List<IcingaFilter> m_filters = null;

	public JoinFilter(String type) {
		m_type = type.toUpperCase();
		this.m_filters = new ArrayList<IcingaFilter>();
	}
	
	public void addFilters(IcingaFilter ... filters){
		
		for(int count = 0; count < filters.length; count ++)
		{
			m_filters.add(filters[count]);
		}
	}
	
	public void addFilter(IcingaFilter f){
		this.m_filters.add(f);
	}
	
	@Override
	public String createFilter() {
		String result = "(";
		
		for(int count = 0; count < m_filters.size(); count ++)
		{
			result = result + m_filters.get(count).createFilter();
			
			if((count + 1) < m_filters.size())
			{
				result = result + " " + m_type + " ";
			}
		}
		
		
		result = result + ")";
		
		return result;
	}

}
