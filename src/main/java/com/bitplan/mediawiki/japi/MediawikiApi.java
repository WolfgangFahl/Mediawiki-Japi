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

import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Login;

/**
 * Mediawiki API Interface
 * see <a href="https://www.mediawiki.org/wiki/API:Main_page">https://www.mediawiki.org/wiki/API:Main_page</a>
 * @author wf
 *
 */
public interface MediawikiApi {
	/**
	 * @return the siteurl
	 */
	public String getSiteurl();
	
	/**
	 * set the siteurl
	 * @param siteurl
	 */
	public void setSiteurl(String siteurl);
	
	/**
	 * get the Version of this Mediawiki
	 * @return
	 * @throws Exception 
	 */
	public String getVersion() throws Exception;
	
	/**
	 * get the general siteinfo
	 * @return
	 * @throws Exception 
	 */
	public General getSiteInfo() throws Exception;
	
	/**
	 * login the given user with the given password
	 * 
	 * See <a href="https://www.mediawiki.org/wiki/API:Login">API:Login</a>
	 * 
	 * @param username
	 * @param password
	 * @return the Login information as returned by the API
	 * @throws Exception 
	 */
	public Login login(String username, String password) throws Exception;
	
	/**
	 * 
	 * Log the current user out
	 * See <a href="https://www.mediawiki.org/wiki/API:Logout">API:Logout</a>
	 * @throws Exception 
	 */
	public void logout() throws Exception;
	
	/**
	 * get the page Content for the given page Title
	 * @param pageTitle
	 * @return the content of the page
	 * @throws Exception
	 */
	public String getPageContent(String pageTitle) throws Exception;
	
	/**
	 * are exceptions thrown when an api error code is received?
	 * @return the throwExceptionOnError
	 */
	public boolean isThrowExceptionOnError();

	/**
	 * set to true if exceptions should be thrown when api error codes are received
	 * default is true
	 * @param throwExceptionOnError the throwExceptionOnError to set
	 */
	public void setThrowExceptionOnError(boolean throwExceptionOnError);
}
