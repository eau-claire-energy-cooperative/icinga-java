package com.ecec.rweber.icinga.filter;

import com.ecec.rweber.icinga.attributes.IcingaAttribute;

public class ComparisonFilter extends IcingaFilter{
	private IcingaAttribute field = null;
	private FilterOperator operator = null;
	private String value = null;
	
	public ComparisonFilter(IcingaAttribute host, FilterOperator operator, int value){
		init(host,operator,value + "");
	}
	
	public ComparisonFilter(IcingaAttribute host, FilterOperator operator, boolean value) {
		init(host,operator,value + "");
	}
	
	public ComparisonFilter(IcingaAttribute host, FilterOperator operator, String value) {
		//wrap strings to help users
		init(host,operator,"\"" + value + "\"");
	}

	private void init(IcingaAttribute host, FilterOperator operator, String value){
		this.field = host; 
		this.operator = operator;
		this.value = value;
	}
	
	@Override
	public String createFilter() {
		String result = "";
		
		if(operator == FilterOperator.LIKE)
		{
			result = "match(" + value + "," + field.getFullAttribute() + ")";
		}
		else if(operator == FilterOperator.CONTAINS_VALUE)
		{
			//syntax is: value in group
			result = value + " " + operator.getOperator() + " " + field.getFullAttribute();
		}
		else
		{
			result = field.getFullAttribute() + " " + operator.getOperator() + " " + value;
		}
		return result;
	}

}
