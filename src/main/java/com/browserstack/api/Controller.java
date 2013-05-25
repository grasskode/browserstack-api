package com.browserstack.api;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.browserstack.api.elements.Browser;
import com.browserstack.api.elements.ParseUtils;
import com.browserstack.api.elements.Worker;

public class Controller {
	
	public static final String BASE_URL = "http://api.browserstack.com";
	public static final String VERSION = "3";
	
	/**
	 * Updates the given session with the current status
	 * 
	 * @param session
	 */
	public static void updateStatus(Session session) {
		HttpClient client = new HttpClient();
		client.getParams().setAuthenticationPreemptive(true);
		Credentials defaultcreds = new UsernamePasswordCredentials(session.getUser(), session.getPass());
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		HttpMethod get = new GetMethod(BASE_URL+"/"+VERSION+"/status");
		get.setDoAuthentication(true);
		try {
			int status = client.executeMethod(get);
			switch (status) {
			case 200 : 
				String response = get.getResponseBodyAsString();
				// parse and fill up browser info
				ObjectMapper m = new ObjectMapper();
				JsonNode rootNode = m.readTree(response);
				session = ParseUtils.populateSession(session, rootNode);
				break;
			default : // Throw custom exception
						System.out.println("Non 200 response for GET call : "+status);
						System.out.println(get.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	      // Release the connection.
	      get.releaseConnection();
	    }
	}
	
	/**
	 * Gets the list of active workers for the given user session
	 * 
	 * @param session
	 * @return list of active workers
	 */
	public static List<Worker> getWorkers(Session session) {
		List<Worker> workers = new LinkedList<Worker>();
		HttpClient client = new HttpClient();
		client.getParams().setAuthenticationPreemptive(true);
		Credentials defaultcreds = new UsernamePasswordCredentials(session.getUser(), session.getPass());
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		HttpMethod get = new GetMethod(BASE_URL+"/"+VERSION+"/workers");
		get.setDoAuthentication(true);
		try {
			int status = client.executeMethod(get);
			switch (status) {
			case 200 : 
				String response = get.getResponseBodyAsString();
				// parse and fill up browser info
				ObjectMapper m = new ObjectMapper();
				JsonNode rootNode = m.readTree(response);
				Iterator<JsonNode> iter = rootNode.getElements();
				while(iter.hasNext()){
					JsonNode info = iter.next();
					workers.add(ParseUtils.populateWorker(info));
				}
				break;
			default : // Throw custom exception
						System.out.println("Non 200 response for GET call : "+status);
						System.out.println(get.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	      // Release the connection.
	      get.releaseConnection();
	    }
		return workers;
	}
	
	/**
	 * Gets the worker corresponding to the given session and id.
	 * 
	 * @param session
	 * @param id
	 * @return Worker
	 */
	public static Worker getWorker(Session session, int id) {
		if(id < 0)
			return null;
		
		Worker worker = new Worker();
		HttpClient client = new HttpClient();
		client.getParams().setAuthenticationPreemptive(true);
		Credentials defaultcreds = new UsernamePasswordCredentials(session.getUser(), session.getPass());
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		HttpMethod get = new GetMethod(BASE_URL+"/"+VERSION+"/worker/"+id);
		get.setDoAuthentication(true);
		try {
			int status = client.executeMethod(get);
			switch (status) {
			case 200 : 
				String response = get.getResponseBodyAsString();
				ObjectMapper m = new ObjectMapper();
				JsonNode rootNode = m.readTree(response);
				worker = ParseUtils.populateWorker(rootNode);
				break;
			default : // Throw custom exception
						System.out.println("Non 200 response for GET call : "+status);
						System.out.println(get.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	      // Release the connection.
	      get.releaseConnection();
	    }
		
		return worker;
	}
	
	/**
	 * Deletes the worker corresponding to the given session and worker
	 * 
	 * @param session
	 * @param worker
	 * @return true if the worker was deleted successfully, false otherwise
	 */
	public static boolean deleteWorker(Session session, Worker worker) {
		return deleteWorker(session, worker.getID());
	}
	
	/**
	 * Deletes the worker corresponding to the given session and id
	 * 
	 * @param session
	 * @param id
	 * @return true if the worker was deleted successfully, false otherwise
	 */
	public static boolean deleteWorker(Session session, int id) {
		if(id < 0)
			return false;
		
		boolean success = false;
		HttpClient client = new HttpClient();
		client.getParams().setAuthenticationPreemptive(true);
		Credentials defaultcreds = new UsernamePasswordCredentials(session.getUser(), session.getPass());
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		HttpMethod delete = new DeleteMethod(BASE_URL+"/"+VERSION+"/worker/"+id);
		delete.setDoAuthentication(true);
		try {
			int status = client.executeMethod(delete);
			switch (status) {
			case 200 : 
				success = true;
				break;
			default : // Throw custom exception
						System.out.println("Non 200 response for GET call : "+status);
						System.out.println(delete.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	      // Release the connection.
	      delete.releaseConnection();
	    }
		
		return success;
	}
	
	/**
	 * Create a new worker for the current session and browser
	 * 
	 * @param session
	 * @param browser
	 * @param url
	 * @return newly created Worker
	 */
	public static Worker createWorker(Session session, Browser browser, String url) {
		return createWorker(session, browser, url, 0);
	}
	
	public static Worker createWorker(Session session, Browser browser, String url, int timeout) {
		int id = -1;
		HttpClient client = new HttpClient();
		client.getParams().setAuthenticationPreemptive(true);
		Credentials defaultcreds = new UsernamePasswordCredentials(session.getUser(), session.getPass());
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		PostMethod post = new PostMethod(BASE_URL+"/"+VERSION+"/worker");
		post.setDoAuthentication(true);
		List<NameValuePair> data = new LinkedList<NameValuePair>();
		if(browser.getOs() != null)
			data.add(new NameValuePair("os", browser.getOs()));
		if(browser.getOsVersion() != null)
			data.add(new NameValuePair("os_version", browser.getOsVersion()));
		if(browser.getName() != null)
			data.add(new NameValuePair("browser", browser.getName()));
		if(browser.getDevice() != null)
			data.add(new NameValuePair("device", browser.getDevice()));
		if(browser.getVersion() != null)
			data.add(new NameValuePair("browser_version", browser.getVersion()));
		data.add(new NameValuePair("url", url));
		data.add(new NameValuePair("timeout", String.valueOf(timeout)));
		post.setRequestBody(data.toArray(new NameValuePair[data.size()]));
		try {
			int status = client.executeMethod(post);
			switch (status) {
			case 200 : 
				String response = post.getResponseBodyAsString();
				// parse and fill up browser info
				ObjectMapper m = new ObjectMapper();
				JsonNode rootNode = m.readTree(response);
				id = rootNode.path("id").getIntValue();
				break;
			default : // Throw custom exception
						System.out.println("Non 200 response for GET call : "+status);
						System.out.println(post.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	      // Release the connection.
	      post.releaseConnection();
	    }
		
		return getWorker(session, id);
	}
	
	
	public static List<Browser> getBrowsers(Session session) {
		List<Browser> browsers = new LinkedList<Browser>();
		HttpClient client = new HttpClient();
		client.getParams().setAuthenticationPreemptive(true);
		Credentials defaultcreds = new UsernamePasswordCredentials(session.getUser(), session.getPass());
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		HttpMethod get = new GetMethod(BASE_URL+"/"+VERSION+"/browsers?flat=true");
		get.setDoAuthentication(true);
		try {
			int status = client.executeMethod(get);
			switch (status) {
			case 200 : 
				String response = get.getResponseBodyAsString();
				// parse and fill up browser info
				ObjectMapper m = new ObjectMapper();
				JsonNode rootNode = m.readTree(response);
				Iterator<JsonNode> iter = rootNode.getElements();
				while(iter.hasNext()){
					JsonNode info = iter.next();
					browsers.add(ParseUtils.populateBrowser(info));
				}
				break;
			default : // Throw custom exception
						System.out.println("Non 200 response for GET call : "+status);
						System.out.println(get.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	      // Release the connection.
	      get.releaseConnection();
	    }
		return browsers;
	}
	
	public static void main(String[] args) {
		Session session = new Session("user", "pass");
//		Controller.getBrowsers(session);
//		Browser b = new Browser();
//		b.setOs("Windows");
//		b.setOsVersion("7");
//		b.setName("firefox");
//		b.setVersion("3.6");
//		Worker w = Controller.createWorker(session, b, "http://www.google.com");
//		System.out.println(w.getID());
//		List<Worker> workers = Controller.getWorkers(session);
//		for(Worker worker : workers)
//			System.out.println(worker);
//		System.out.println(Controller.getWorker(session, w.getID()));
		updateStatus(session);
		System.out.println(session);
	}

}
