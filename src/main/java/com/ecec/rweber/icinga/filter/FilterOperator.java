package com.ecec.rweber.icinga.filter;

public enum FilterOperator {

	LIKE("like"),
	EQUAL("=="),
	NOT_EQUAL("!="),
	GREATER_THAN(">"),
	CONTAINS_VALUE("in"),
	LESS_THAN("<");
	
	private String operator = null;
	
	FilterOperator(String o){
		this.operator = o;
	}
	
	public String getOperator() {
		return operator;
	}
}
