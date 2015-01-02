/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package com.bitplan.jmediawiki;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.ws.rs.core.NewCookie;

import com.bitplan.jmediawiki.api.Api;
import com.bitplan.jmediawiki.api.Login;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

/**
 * access to Mediawiki api
 * 
 * @author wf
 *
 */
public class JMediawiki implements IMediawiki {

	/**
	 * set to true for debugging
	 */
	protected boolean debug = false;

	/**
	 * Logging may be enabled by setting debug to true
	 */
	protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger("com.bitplan.jmediawiki");

	protected String siteurl;
	protected String scriptPath = "/w";
	// FIXME - default should be json soon
	protected String format = "xml";
	protected String apiPath = "/api.php?";

	// the client and it's cookies
	private Client client;
	private ArrayList<Object> cookies;

	/**
	 * enabel debugging
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return the siteurl
	 */
	public String getSiteurl() {
		return siteurl;
	}

	/**
	 * @param siteurl
	 *          the siteurl to set
	 */
	public void setSiteurl(String siteurl) {
		this.siteurl = siteurl;
	}

	/**
	 * @return the scriptPath
	 */
	public String getScriptPath() {
		return scriptPath;
	}

	/**
	 * @param scriptPath
	 *          the scriptPath to set
	 */
	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	/**
	 * construct a Mediawik for the given url
	 * 
	 * @param siteurl
	 *          - the url to use
	 */
	public JMediawiki(String siteurl) {
		this.siteurl = siteurl;
		ApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
		config.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);
		client = ApacheHttpClient.create(config);
		// client=Client.create();
		// client.addFilter(new CookieFilter());
	}

	

	/**
	 * Cookie handling
	 * http://stackoverflow.com/questions/6713893/jersey-client-adding
	 * -cookies-to-request
	 * 
	 * @author wf
	 *
	 */
	public class CookieFilter extends ClientFilter {

		@Override
		public ClientResponse handle(ClientRequest request)
				throws ClientHandlerException {
			if (cookies != null) {
				request.getHeaders().put("Cookie", cookies);
			}
			ClientResponse response = getNext().handle(request);
			if (response.getCookies() != null) {
				if (cookies == null) {
					cookies = new ArrayList<Object>();
				}
				// simple addAll just for illustration (should probably check for
				// duplicates and expired cookies)
				List<NewCookie> responseCookies = response.getCookies();
				for (NewCookie newCookie:responseCookies) {
					LOGGER.log(Level.INFO,newCookie.getName()+"="+newCookie.getValue());
					cookies.add(newCookie);
				}
			}
			return response;
		}
	}

	/**
	 * get the result for the given action and aprams
	 * 
	 * @param action
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Api getActionResult(String action, String params) throws Exception {
		String queryUrl = siteurl + scriptPath + apiPath + "&action=" + action
				+ params + "&format=" + format;
		if (debug)
			LOGGER.log(Level.INFO, queryUrl);
		WebResource resource = client.resource(queryUrl);
		String xml;
		if ("login".equals(action)) {
			xml = resource.post(String.class);
		} else {
			xml = resource.get(String.class);
		}
		xml = xml.replace(">", ">\n");
		if (debug)
			LOGGER.log(Level.INFO, xml);
		Api api = Api.fromXML(xml);
		return api;
	}

	/**
	 * get the Result for the given query
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Api getQueryResult(String query) throws Exception {
		Api result = this.getActionResult("query", query);
		return result;
	}
	
	/**
	 * request parameter encoding
	 * @param param
	 * @return
	 */
	protected String encode(String param) {
		@SuppressWarnings("deprecation")
		String result=URLEncoder.encode(param);
		return result;
	}

	// login implementation
	public Login login(String username, String password) throws Exception {
		username=encode(username);
		password=encode(password);
		Api apiResult = getActionResult("login", "&lgname=" + username
				+ "&lgpassword=" + password);
		Login login = apiResult.getLogin();
		String token = login.getToken();
		apiResult = getActionResult("login", "&lgname=" + username + "&lgpassword="
				+ password + "&lgtoken=" + token);
		login= apiResult.getLogin();
		return login;
	}
	
	/**
	 * end the session
	 */
	public void logout() {
		if (cookies!=null) {
			cookies.clear();
			cookies=null;
		}
	}

}
