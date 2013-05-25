package com.browserstack.api.elements;

public class Worker {
	
	private int ID;
	private String status;
	private Browser browser;
	
	public Worker() {
		ID = -1;
		status = null;
		browser = null;
	}
	
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Browser getBrowser() {
		return browser;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return ID+" : "+status;
	}

}
