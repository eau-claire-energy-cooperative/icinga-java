package com.ecec.rweber.icinga.attributes;

public enum ContactAttributes implements IcingaAttribute{
	ALIAS("display_name"),
	EMAIL("email"),
	GROUPS("groups"),
	NAME("name"),
	VARS("vars");
	
	private String name = null;

	ContactAttributes(String n){
		this.name = n;
	}
	
	@Override
	public String getAttribute() {
		return name;
	}
	
	@Override
	public String toString(){
		return this.getAttribute();
	}

	@Override
	public String getFullAttribute() {
		return "user." + name;
	}
}
