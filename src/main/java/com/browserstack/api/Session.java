package com.browserstack.api;

public class Session {
	
	private String user;
	private String pass;
	
	private double usedTime;
	private double totalTime;
	private int runningWindowsSessions;
	private int runningMacSessions;
	private int windowsSessionsLimit;
	private int macSessionsLimit;
	
	private boolean expired;
	
	public Session(String user, String pass) {
		this.user = user;
		this.pass = pass;
		this.usedTime = -1.0;
		this.totalTime = -1.0;
		this.runningMacSessions = -1;
		this.runningWindowsSessions = -1;
		this.windowsSessionsLimit = -1;
		this.macSessionsLimit = -1;
		this.expired = false;
	}
	
	public String getPass() {
		return pass;
	}
	
	public String getUser() {
		return user;
	}

	public double getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(double usedTime) {
		this.usedTime = usedTime;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}

	public int getRunningWindowsSessions() {
		return runningWindowsSessions;
	}

	public void setRunningWindowsSessions(int runningWindowsSessions) {
		this.runningWindowsSessions = runningWindowsSessions;
	}

	public int getRunningMacSessions() {
		return runningMacSessions;
	}

	public void setRunningMacSessions(int runningMacSessions) {
		this.runningMacSessions = runningMacSessions;
	}

	public int getWindowsSessionsLimit() {
		return windowsSessionsLimit;
	}

	public void setWindowsSessionsLimit(int windowsSessionsLimit) {
		this.windowsSessionsLimit = windowsSessionsLimit;
	}

	public int getMacSessionsLimit() {
		return macSessionsLimit;
	}

	public void setMacSessionsLimit(int macSessionsLimit) {
		this.macSessionsLimit = macSessionsLimit;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

}
