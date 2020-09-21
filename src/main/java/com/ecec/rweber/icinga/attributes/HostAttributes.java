package com.ecec.rweber.icinga.attributes;

public enum HostAttributes implements IcingaAttribute{
	ACKNOWLEDGED("acknowledgement"),
	ACKNOWLEDGEMENT_EXPIRY("acknowledgement_expiry"),
	ACTIVE("active"),
	ADDRESS("address"),
	ALIAS("display_name"),
	CHECK_INTERVAL("check_interval"),
	CURRENT_STATE("state"),
	GROUPS("groups"),
	IS_DOWN("downtime_depth"),
	LAST_CHECK("last_check"),
	LAST_HARD_STATE("last_hard_state"),
	MAX_CHECK_ATTEMPTS("max_check_attempts"),
	NEXT_CHECK("next_check"),
	NAME("name"),
	OUTPUT("last_check_result"),
	RETRY_INTERVAL("retry_interval"),
	STATE_CHANGE_TIME("last_state_change"),
	VARS("vars"),
	ZONE("zone");
	
	private String name = null;
	
	HostAttributes(String n){
		this.name = n;
	}
	
	@Override
	public String getFullAttribute(){
		return "host." + name;
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
