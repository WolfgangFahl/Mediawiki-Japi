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

import com.bitplan.jmediawiki.api.Login;

/**
 * Mediawiki Interface
 * see <a href=https://www.mediawiki.org/wiki/API:Main_page'>https://www.mediawiki.org/wiki/API:Main_page</a>
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
	 * login the given user with the given password
	 * 
	 * See <a href="https://www.mediawiki.org/wiki/API:Login">API:Login</a>
	 * 
	 * @param username
	 * @param password
	 * @return 
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
