package com.ecec.rweber.icinga.filter;

public class OrFilter extends JoinFilter{

	public OrFilter() {
		super("||");
	}

	public OrFilter(IcingaFilter ... filters){
		this();
		
		this.addFilters(filters);
	}
}
