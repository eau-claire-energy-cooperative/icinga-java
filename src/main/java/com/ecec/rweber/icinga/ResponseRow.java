package com.ecec.rweber.icinga;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.ecec.rweber.icinga.attributes.ContactAttributes;
import com.ecec.rweber.icinga.attributes.DowntimeAttributes;
import com.ecec.rweber.icinga.attributes.HostAttributes;
import com.ecec.rweber.icinga.attributes.IcingaAttribute;
import com.ecec.rweber.icinga.attributes.ServiceAttributes;

public class ResponseRow implements Serializable, JSONAware {
	private static final long serialVersionUID = -1679468598491354796L;
	private Map<String,Object> m_data = null;
	private String m_endpoint = null;
	
	protected ResponseRow(IcingaQuery origQuery) {
		m_endpoint = origQuery.getEndpointTarget();
		m_data = new HashMap<String,Object>();
	}

	public void put(String key, Object value){
		m_data.put(key, value);
	}
	
	public Boolean getBoolean(IcingaAttribute a){
		Boolean result = null;
		
		if(this.containsKey(a))
		{
			result = Boolean.parseBoolean(this.getString(a));
		}
		
		return result;
	}
	
	public Double getNumber(IcingaAttribute a){
		Double result = null;
		
		if(this.containsKey(a))
		{
			result = Double.parseDouble(this.getString(a));
		}
		
		return result;
	}
	
	public String getString(IcingaAttribute a){
		String result = null;
		
		if(this.containsKey(a))
		{
			result = getObject(a).toString();
		}
		
		return result;
	}
	
	public Object getObject(IcingaAttribute a){
		Object result = null;
		
		//if the query type matches the attribute type
		if((m_endpoint.equals(IcingaQuery.SERVICE_ENDPOINT) && a instanceof ServiceAttributes) || (m_endpoint.equals(IcingaQuery.HOST_ENDPOINT) && a instanceof HostAttributes) || (m_endpoint.equals(IcingaQuery.CONTACT_ENDPOINT) && a instanceof ContactAttributes) || (m_endpoint.equals(IcingaQuery.DOWNTIME_ENDPOINT) && a instanceof DowntimeAttributes))
		{
			if(m_data.containsKey(a.getAttribute()))
			{
				result = m_data.get(a.getAttribute());
			}
		}
		else
		{
			//it's part of the joins table
			if(m_data.containsKey(a.getFullAttribute()))
			{
				result = m_data.get(a.getFullAttribute());
			}
		}
		
		return result;
	}
	
	public boolean containsKey(IcingaAttribute a){
		return this.getObject(a) != null;
	}

	@Override
	public String toJSONString() {
		JSONObject json = new JSONObject(m_data);
		
		return json.toJSONString();
	}

}
