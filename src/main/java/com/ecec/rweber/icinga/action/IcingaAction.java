package com.ecec.rweber.icinga.action;

import com.ecec.rweber.icinga.filter.AndFilter;
import com.ecec.rweber.icinga.filter.ComparisonFilter;
import com.ecec.rweber.icinga.filter.IcingaFilter;

public abstract class IcingaAction {
	public static final String ACKNOWLEDGE = "v1/actions/acknowledge-problem";
	public static final String SCHEDULE_DOWNTIME = "v1/actions/schedule-downtime";
	public static final String REMOVE_DOWNTIME = "v1/actions/remove-downtime";
	
	protected String m_action = null;
	protected ObjectType m_type = ObjectType.Host;
	protected IcingaFilter m_filter = null;
	
	public IcingaAction(String action, IcingaFilter filter, ObjectType type) {
		m_action = action;
		m_type = type;
		m_filter = filter;
	}
	
	public abstract String getPostData();
	
	protected String getFilter(){
		String result = "";
		
		if(m_filter != null)
		{
			if(m_filter instanceof ComparisonFilter)
			{
				//wrap in an And filter
				result = new AndFilter(m_filter).createFilter();
			}
			else
			{
				result = m_filter.createFilter();
			}
		}
			
		return result;
	}
	
	public String getAction(){
		
		return m_action;
	}
	
	public enum ObjectType {
		Host,
		Service;
	}
}
