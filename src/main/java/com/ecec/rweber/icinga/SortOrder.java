package com.ecec.rweber.icinga;

import java.util.Comparator;

import com.ecec.rweber.icinga.attributes.IcingaAttribute;

public class SortOrder implements Comparator<ResponseRow> {
	private IcingaAttribute m_key = null;
	private boolean m_isDesc = true;
	
	
	public SortOrder(IcingaAttribute key) {
		this(key,true);
	}
	
	public SortOrder(IcingaAttribute key, boolean desc) {
		m_key = key;
		m_isDesc = desc;
	}

	@Override
	public int compare(ResponseRow row1, ResponseRow row2) {
		int result = 0;
		
		//make sure the keys exist
		if(row1.containsKey(m_key) && row2.containsKey(m_key))
		{
			result = row1.getString(m_key).toLowerCase().compareTo(row2.getString(m_key).toLowerCase());
		}
		
		if(!m_isDesc)
		{
			//flip result if we're ascending
			result = result * -1;
		}
		
		return result;
	}

}
