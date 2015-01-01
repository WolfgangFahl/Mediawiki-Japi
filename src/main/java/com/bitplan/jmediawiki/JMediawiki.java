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

import java.util.logging.Level;

import com.bitplan.jmediawiki.api.Api;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * access to Mediawiki api
 * 
 * @author wf
 *
 */
public class JMediawiki {

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
	}

	/**
	 * get the result for the given action and aprams
	 * @param action
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Api getActionResult(String action,String params) throws Exception {
		String queryUrl = siteurl + scriptPath + apiPath +"&action="+action+ params + "&format="
				+ format;
		if (debug)
			LOGGER.log(Level.INFO, queryUrl);
		WebResource resource = Client.create().resource(queryUrl);
		String xml;
		if ("login".equals(action)) {
			xml=resource.post(String.class);
		} else {
			xml=resource.get(String.class);
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
		Api result=this.getActionResult("query", query);
		return result;
	}

}
