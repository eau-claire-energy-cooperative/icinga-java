package com.ecec.rweber.icinga.action;


import org.json.simple.JSONObject;

import com.ecec.rweber.icinga.filter.IcingaFilter;

public class ScheduleDowntimeAction extends IcingaAction{
	private String m_author = null;
	private String m_comment = null;
	private long m_start = 0;
	private long m_end = 0;
	private boolean m_fixed = true;
	private int m_flexDuration = 7200; //2 hr in seconds
			
	public ScheduleDowntimeAction(ObjectType type, IcingaFilter filter, String author, String comment) {
		this(type,filter,author,comment,System.currentTimeMillis()/1000); //start time is now
	}

	public ScheduleDowntimeAction(ObjectType type, IcingaFilter filter, String author, String comment, long start){
		this(type,filter,author,comment,start,start + 7200); //end is 2 hrs from now
	}
	
	public ScheduleDowntimeAction(ObjectType type, IcingaFilter filter, String author, String comment, long start, long end){
		super(IcingaAction.SCHEDULE_DOWNTIME,filter,type);
		
		m_author = author;
		m_comment = comment;
		m_start = start;
		m_end = end;
	}
	
	//default is true (https://www.icinga.com/docs/icinga2/latest/doc/08-advanced-topics/#fixed-and-flexible-downtimes)
	public ScheduleDowntimeAction setFixed(boolean isFixed){
		m_fixed = isFixed;
		
		return this;
	}
	
	public ScheduleDowntimeAction setFlexDuration(int duration){
		m_flexDuration = duration; //must be in seconds
		
		return this;
	}
	
	@Override
	public String getPostData() {
		JSONObject result = new JSONObject();
		
		//required
		result.put("filter", this.getFilter());
		result.put("type", m_type.name());
		
		result.put("start_time",m_start);
		result.put("end_time",m_end);
		result.put("author", m_author);
		result.put("comment", m_comment);
		result.put("fixed", m_fixed);
		result.put("duration", m_flexDuration);
		result.put("child_options", 1);
		
		return result.toJSONString();
	}

}
