package com.ecec.rweber.icinga;

import java.util.Arrays;
import java.util.Collections;

import org.json.simple.JSONObject;

import com.ecec.rweber.icinga.attributes.IcingaAttribute;
import com.ecec.rweber.icinga.filter.AndFilter;
import com.ecec.rweber.icinga.filter.ComparisonFilter;
import com.ecec.rweber.icinga.filter.IcingaFilter;

public class IcingaQuery {
	public static final String CONTACT_ENDPOINT	= "v1/objects/users";
	public static final String HOST_ENDPOINT = "v1/objects/hosts";
	public static final String SERVICE_ENDPOINT = "v1/objects/services";
	public static final String DOWNTIME_ENDPOINT = "v1/objects/downtimes";
	
	private String m_endpoint = null;
	private ColumnList m_attrs = null;
	private ColumnList m_joins = null;
	private IcingaFilter m_filter = null;
	private SortOrder m_order = null;
	
	public IcingaQuery(String target) {
		m_endpoint = target;
		m_attrs = new ColumnList();
		m_joins = new ColumnList();
	}
	
	protected String getEndpointTarget(){
		return m_endpoint;
	}
	
	protected SortOrder getOrder(){
		return m_order;
	}
	
	protected String getPostData(){
		JSONObject result = new JSONObject();
		
		if(m_joins != null && m_joins.size() > 0)
		{
			result.put("joins", m_joins.getJoins());
		}
		
		if(m_filter != null)
		{
			if(m_filter instanceof ComparisonFilter)
			{
				//we need to wrap it in an AND filter
				m_filter = new AndFilter(m_filter);
			}
			
			result.put("filter", m_filter.createFilter());
		}
		
		if(m_attrs != null && m_attrs.size() > 0)
		{
			result.put("attrs",m_attrs.getAttributes());
			
		}

		return result.toJSONString();
	}
	
	public IcingaQuery join(IcingaAttribute relation){
		m_joins.add(relation);
		
		return this;
	}
	
	public IcingaQuery addFilter(IcingaFilter f){
		m_filter = f;
		
		return this;
	}

	public IcingaQuery orderBy(SortOrder o){
		m_order = o;
		
		return this;
	}
	
	public IcingaQuery addAttribute(IcingaAttribute a){
		m_attrs.add(a);
		
		return this;
	}
	
	public IcingaQuery addAttributes(IcingaAttribute ... attributes){
		m_attrs.addAll(Arrays.asList(attributes));
		
		return this;
	}
	
	public ColumnList getAttributes(){
		ColumnList result = new ColumnList();
		result.addAll(this.m_attrs);
		result.addAll(this.m_joins);
		
		return result;
	}
}
