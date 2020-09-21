package com.ecec.rweber.icinga.action;

import org.json.simple.JSONObject;

import com.ecec.rweber.icinga.filter.IcingaFilter;

public class RemoveDowntimeAction extends IcingaAction{
	
	public RemoveDowntimeAction(ObjectType type, IcingaFilter filter) {
		super(IcingaAction.REMOVE_DOWNTIME,filter,type);
	}
	
	@Override
	public String getPostData() {
		JSONObject result = new JSONObject();
		
		result.put("type", m_type.name());
		result.put("filter", this.getFilter());
		
		return result.toJSONString();
	}

}
