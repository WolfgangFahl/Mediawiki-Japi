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

import static org.junit.Assert.*;

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
	public void testLoginToken() throws Exception {
		WikiUser wuser = WikiUser.getUser("mediawiki_org");
		// do not keep uncommented - password will be visible in log
		// wiki.setDebug(true);
		Api api = wiki.getActionResult("login", "&lgname=" + wuser.getUsername());
		Login login = api.getLogin();
		assertNotNull(login);
		assertEquals("NeedToken",login.getResult());
		assertNotNull(login.getToken());
		assertNotNull(login.getSessionid());
	}
	
	@Test
	public void testLogin() throws Exception {
		WikiUser wuser=WikiUser.getUser("mediawiki_org");
		// avoid uncommenting - will show password information ...
		Login login=wiki.login(wuser.getUsername(),wuser.getPassword());
		assertEquals("Success",login.getResult());
		assertNotNull(login.getLguserid());
		assertEquals(wuser.getUsername(),login.getLgusername());
		assertNotNull(login.getLgtoken());
		// make sure logout also works
		wiki.logout();
		// FIXME - test effect of logout
	}
	
	@Test
	public void testLoginNotExists() throws Exception {
		Login login=wiki.login("someUserThatDoesNotExist","somePassword");
		assertEquals("NotExists",login.getResult());
	}
	
}
