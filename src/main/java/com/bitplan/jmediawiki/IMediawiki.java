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
 * @author wf
 *
 */
public interface IMediawiki {
	/**
	 * login the given user with the given password
	 * @param username
	 * @param password
	 * @return 
	 * @throws Exception 
	 */
	public Login login(String username, String password) throws Exception;
	
	/**
	 * log me out
	 */
	public void logout();
}
