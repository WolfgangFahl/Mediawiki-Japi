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

import static org.junit.Assert.assertNotNull;

import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.jmediawiki.api.Api;
import com.bitplan.jmediawiki.api.Login;
import com.bitplan.jmediawiki.user.WikiUser;

/**
 * Test http://www.mediawiki.org/wiki/API:Login
 * @author wf
 *
 */
public class TestAPI_Login extends TestAPI {

	/**
	 * test secret access to user data
	 * @throws Exception
	 */
	@Test
	public void testGetUser() throws Exception {
		WikiUser wuser = WikiUser.getUser("mediawiki_org");
		if (debug) {
			LOGGER.log(Level.INFO,"user=" + wuser.getUsername());
		}
		assertNotNull(wuser.getEmail());
		assertNotNull(wuser.getPassword());
	}

	/**
	 * http://www.mediawiki.org/wiki/API:Login
	 * 
	 * @author wf
	 *
	 */
	@Test
	public void testLogin() throws Exception {
		WikiUser wuser = WikiUser.getUser("mediawiki_org");
		// do not keep uncommented - password will be visible in log
		// wiki.setDebug(true);
		Api api = wiki.getActionResult("login", "&lgname=" + wuser.getUsername() + "&lgpassword="
				+ wuser.getPassword());
		Login login = api.getLogin();
		assertNotNull(login);
		assertNotNull(login.getToken());
		assertNotNull(login.getSessionid());
	}
}
