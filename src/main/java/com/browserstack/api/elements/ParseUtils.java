package com.browserstack.api.elements;

import org.codehaus.jackson.JsonNode;

import com.browserstack.api.Session;

public class ParseUtils {
	
	private static final String EXPIRY_MESSAGE = "You have run out of API time";
	
	public static Worker populateWorker(JsonNode node){
		Worker worker = new Worker();
		// parse and fill up worker info
		if(node.has("id"))
			worker.setID(node.path("id").getIntValue());
		if(node.has("status"))
			worker.setStatus(node.path("status").getTextValue());
		Browser browser = populateBrowser(node);
		worker.setBrowser(browser);
		return worker;
	}

	public static Browser populateBrowser(JsonNode node) {
		Browser b = new Browser();
		if(node.has("device"))
			b.setDevice(node.path("device").getTextValue());
		if(node.has("browser"))
			b.setName(node.path("browser").getTextValue());
		if(node.has("os"))
			b.setOs(node.path("os").getTextValue());
		if(node.has("os_version"))
			b.setOsVersion(node.path("os_version").getTextValue());
		if(node.has("browser_version"))
			b.setVersion(node.path("browser_version").getTextValue());
		return b;
	}
	
	public static Session populateSession(Session session, JsonNode node) {
		if(node.has("message") && node.path("message").getTextValue().equalsIgnoreCase(EXPIRY_MESSAGE)) {
			session.setExpired(true);
		} else {
			session.setExpired(false);
			if(node.has("used_time"))
				session.setUsedTime(node.path("used_time").getDoubleValue());
			if(node.has("total_available_time"))
				session.setTotalTime(node.path("total_available_time").getDoubleValue());
			if(node.has("running_windows_sessions"))
				session.setRunningWindowsSessions(node.path("running_windows_sessions").getIntValue());
			if(node.has("windows_sessions_limit"))
				session.setWindowsSessionsLimit(node.path("windows_sessions_limit").getIntValue());
			if(node.has("running_mac_sessions"))
				session.setRunningMacSessions(node.path("running_mac_sessions").getIntValue());
			if(node.has("mac_sessions_limit"))
				session.setMacSessionsLimit(node.path("mac_sessions_limit").getIntValue());
		}
		return session;
	}

}
