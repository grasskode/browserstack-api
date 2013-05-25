package com.browserstack.api.elements;

public class Browser {
	
	private String name;
	private String version;
	private String os;
	private String osVersion;
	private String device;
	
	public Browser() {
		name = null;
		version = null;
		os = null;
		osVersion = null;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public void setOs(String os) {
		this.os = os;
	}
	
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	public void setDevice(String device) {
		this.device = device;
	}
	
	public String getDevice() {
		return device;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOs() {
		return os;
	}
	
	public String getOsVersion() {
		return osVersion;
	}
	
	public String getVersion() {
		return version;
	}

}
