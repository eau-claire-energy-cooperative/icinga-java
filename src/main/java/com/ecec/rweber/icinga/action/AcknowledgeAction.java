package com.ecec.rweber.icinga.action;

import org.json.simple.JSONObject;

import com.ecec.rweber.icinga.filter.IcingaFilter;

public class AcknowledgeAction extends IcingaAction{
	private String m_author = null;
	private String m_comment = null;
	private long m_expire = 0;
	
	public AcknowledgeAction(ObjectType type, IcingaFilter filter, String author, String comment){
		super(IcingaAction.ACKNOWLEDGE,filter,type);
		
		m_type = type;
		m_author = author;
		m_comment = comment;
	}
	
	public void setExpiration(int minutes){
		//expire x seconds from now
		m_expire = (System.currentTimeMillis()/1000) + (minutes * 60);
	}
	
	@Override
	public String getPostData() {
		JSONObject result = new JSONObject();
		
		result.put("type",m_type.name());
		result.put("filter", this.getFilter());
		result.put("author", m_author);
		result.put("comment", m_comment);
		result.put("notify", true);
		
		if(m_expire != 0)
		{
			result.put("expiry", m_expire);
		}
		
		return result.toJSONString();
	}

}
