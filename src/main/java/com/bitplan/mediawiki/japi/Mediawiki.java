/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/WolfgangFahl/Mediawiki-Japi
 * and the license for Mediawiki-Japi applies
 * 
 */
package com.bitplan.mediawiki.japi;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Error;
import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Login;
import com.bitplan.mediawiki.japi.api.Page;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

/**
 * access to Mediawiki api
 * 
 * @author wf
 *
 */
public class Mediawiki implements MediawikiApi {

	/**
	 *  current Version 
	 */
	protected static final String VERSION="0.0.2";
	
	/**
	 * see <a href='https://www.mediawiki.org/wiki/API:Main_page#Identifying_your_client'>Identifying your client:User-Agent</a>
	 */
	protected static final String USER_AGENT = "Mediawiki-Japi/"+VERSION+" (https://github.com/WolfgangFahl/Mediawiki-Japi; support@bitplan.com)";

	/**
	 * default script path
	 */
	public static final String DEFAULT_SCRIPTPATH = "/w";

	/**
	 * set to true for debugging
	 */
	protected boolean debug = false;
	
	/**
	 * set to true if exceptions should be thrown on Error
	 */
	protected boolean throwExceptionOnError=true;

	/**
	 * Logging may be enabled by setting debug to true
	 */
	protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger("com.bitplan.mediawiki.japi");

	protected String siteurl;
	protected String scriptPath = DEFAULT_SCRIPTPATH;
	// FIXME - default should be json soon
	protected String format = "xml";
	protected String apiPath = "/api.php?";

	// the client and it's cookies
	private Client client;
	private ArrayList<Object> cookies;

	// mediaWikiVersion and site info
	protected String mediawikiVersion;
	protected General siteinfo;

	/**
	 * enabel debugging
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return the throwExceptionOnError
	 */
	public boolean isThrowExceptionOnError() {
		return throwExceptionOnError;
	}

	/**
	 * @param throwExceptionOnError the throwExceptionOnError to set
	 */
	public void setThrowExceptionOnError(boolean throwExceptionOnError) {
		this.throwExceptionOnError = throwExceptionOnError;
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
	 * construct a Mediawiki for the given url using the default Script path 
	 * 
	 * @param siteurl - the url to use
	 */
	public Mediawiki(String siteurl) {
		this(siteurl,DEFAULT_SCRIPTPATH);
	}
	
	/**
	 * construct a Mediawiki for the given url and scriptpath
	 * @param siteurl - the url to use
	 * @param scriptpath - the scriptpath to use
	 */
	public Mediawiki(String siteurl, String scriptpath) {
		this.siteurl = siteurl;
		this.scriptPath=scriptpath;
		ApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
		config.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);
		client = ApacheHttpClient.create(config);
	}

	

	/**
	 * get the result for the given action and aprams
	 * 
	 * @param action
	 * @param params
	 * @return the API result for the action
	 * @throws Exception
	 */
	public Api getActionResult(String action, String params) throws Exception {
		String queryUrl = siteurl + scriptPath + apiPath + "&action=" + action
				+ params + "&format=" + format;
		if (debug)
			LOGGER.log(Level.INFO, queryUrl);
		WebResource resource = client.resource(queryUrl);
		String xml;
		// decide for the method to use for api access
		if ("login".equals(action)) {
			xml = resource.header("USER-AGENT", USER_AGENT).post(String.class);
		} else {
			xml = resource.header("USER-AGENT", USER_AGENT).get(String.class);
		}
		if (debug) {
			// convert the xml to a more readable format
			String xmlDebug = xml.replace(">", ">\n");
			LOGGER.log(Level.INFO, xmlDebug);
		}
		// retrieve the JAXB wrapper representation from the xml received
		Api api = Api.fromXML(xml);
		// check whether an error code was sent
		Error error = api.getError();
		// if there is an error - handle it
		if (error!=null) {
			// prepare the error message
			String errMsg="error code="+error.getCode()+" info:'"+error.getInfo()+"'";
			// log it
			LOGGER.log(Level.SEVERE,errMsg);
			// and throw an error if this is configured
			if (this.isThrowExceptionOnError()) {
				throw new Exception(errMsg);
			}
		}
		return api;
	}

	/**
	 * get the Result for the given query
	 * 
	 * @param query
	 * @return the API result for the query
	 * @throws Exception 
	 */
	public Api getQueryResult(String query) throws Exception {
		Api result = this.getActionResult("query", query);
		return result;
	}
	
	/**
	 * request parameter encoding
	 * @param param
	 * @return an encoded url parameter
	 */
	protected String encode(String param) {
		@SuppressWarnings("deprecation")
		String result=URLEncoder.encode(param);
		return result;
	}

	/**
	 * normalize the given page title
	 * @param title
	 * @return the normalized title e.g. replacing blanks
	 * FIXME encode is not good enough
	 */
	protected String normalize(String title) {
		String result=encode(title);
		return result;
	}
	
	/**
	 * get the general siteinfo
	 * @return
	 * @throws Exception 
	 */
	public General getSiteInfo() throws Exception {
		if (siteinfo==null) {
			Api api = getQueryResult("&meta=siteinfo");
			siteinfo=api.getQuery().getGeneral();
		}
		return siteinfo;
	}

	/**
	 * get the Version of this wiki
	 * @throws Exception 
	 */
	public String getVersion() throws Exception {
		if (mediawikiVersion==null) {
			General lGeneral=getSiteInfo();
			mediawikiVersion=lGeneral.getGenerator();
		}
		return mediawikiVersion;
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
	 * @throws Exception 
	 */
	public void logout() throws Exception {
		Api apiResult=getActionResult("logout","");
		if (apiResult!=null) {
			// FIXME check apiResult			
		}
		if (cookies!=null) {
			cookies.clear();
			cookies=null;
		}
	}

	/**
	 * get the page Content for the given page Title
	 * @param pageTitle
	 * @return the page Content
	 * @throws Exception 
	 */
	public String getPageContent(String pageTitle) throws Exception {
		Api api = getQueryResult("&prop=revisions&rvprop=content&titles="+normalize(pageTitle));
		Page page=api.getQuery().getPages().get(0);
		String content=page.getRevisions().get(0).getValue();	
		return content;
	}

	

}
