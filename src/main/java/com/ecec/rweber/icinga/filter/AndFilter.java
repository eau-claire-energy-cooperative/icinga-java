package com.ecec.rweber.icinga.filter;

public class AndFilter extends JoinFilter{
	
	public AndFilter(){
		super("&&");
	}
	
	public AndFilter(IcingaFilter ...filters) {
		this();
		
		this.addFilters(filters);
	}

}
