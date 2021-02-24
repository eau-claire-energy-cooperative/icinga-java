package com.ecec.rweber.icinga.attributes;

public enum DowntimeAttributes implements IcingaAttribute{
	ACTIVE("active"),
	AUTHOR("author"),
	COMMENT("comment"),
	DURATION("duration"),
	END_TIME("end_time"),
	ENTRY_TIME("entry_time"),
	FIXED("fixed"),
	SCHEDULED_BY("scheduled_by"),
	START_TIME("start_time"),
	WAS_CANCELLED("was_cancelled"),
	ZONE("zone");
	
	private String name = null;
	
	DowntimeAttributes(String n){
		this.name = n;
	}
	
	@Override
	public String getFullAttribute(){
		return "downtime." + name;
	}
	
	@Override
	public String getAttribute() {
		return name;
	}
	
	@Override
	public String toString(){
		return this.getAttribute();
	}

}
