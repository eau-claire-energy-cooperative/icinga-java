package com.ecec.rweber.icinga.attributes;

public enum ServiceAttributes implements IcingaAttribute{
	ACKNOWLEDGED("acknowledgement"),
	ACKNOWLEDGEMENT_EXPIRY("acknowledgement_expiry"),
	ACTIVE("active"),
	ALIAS("display_name"),
	CHECK_INTERVAL("check_interval"),
	GROUPS("groups"),
	IS_DOWN("downtime_depth"),
	LAST_HARD_STATE("last_hard_state"),
	MAX_CHECK_ATTEMPTS("max_check_attempts"),
	NAME("name"),
	NEXT_CHECK("next_check"),
	RETRY_INTERVAL("retry_interval"),
	STATE("state"),
	STATE_CHANGE_TIME("last_state_change"), 
	VARS("vars"),
	ZONE("zone");
	
	private String name = null;
	
	ServiceAttributes(String n){
		this.name = n;
	}
	
	public String getFullAttribute(){
		return "service." + name;
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
