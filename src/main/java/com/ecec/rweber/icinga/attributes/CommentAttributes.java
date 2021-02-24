package com.ecec.rweber.icinga.attributes;

public enum CommentAttributes implements IcingaAttribute{
	AUTHOR("author"),
	HOST("host_name"),
	ENTRY_TIME("entry_time"),
	ENTRY_TYPE("entry_type"),
	EXPIRE_TIME("expire_time"),
	SERVICE("service_name"),
	TEXT("text");
	
	private String name = null;

	CommentAttributes(String n){
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
		return "comment." + name;
	}
}
