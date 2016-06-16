/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package com.bitplan.mediawiki.japi;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Login;
import com.bitplan.mediawiki.japi.jaxb.JaxbFactory;
import com.bitplan.mediawiki.japi.user.WikiUser;

/**
 * Test http://www.mediawiki.org/wiki/API:Login
 * 
 * @author wf
 *
 */
public class TestAPI_Login extends APITestbase {

	/**
	 * test secret access to user data
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetUser() throws Exception {
		WikiUser wuser = getWiki().getWikiUser();
		check("email", wuser.getEmail());
		assertNotNull(wuser.getPassword());
	}

	/**
	 * http://www.mediawiki.org/wiki/API:Login
	 */
	@Test
	public void testLoginToken() throws Exception {
		for (ExampleWiki lwiki : getWikis()) {
			WikiUser wuser = lwiki.getWikiUser();
			// do not keep uncommented - password will be visible in log
			// lwiki.setDebug(true);
			Api api = lwiki.getMediaWikiJapi().getActionResult("login",
					"&lgname=" + wuser.getUsername());
			Login login = api.getLogin();
			assertNotNull(login);
			assertEquals("NeedToken", login.getResult());
			assertNotNull(login.getToken());
			assertNotNull(login.getSessionid());
		}
	}

	/**
	 * test Login and logout see <a
	 * href='http://www.mediawiki.org/wiki/API:Login'>API:Login</a>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogin() throws Exception {
		for (ExampleWiki lwiki : getWikis()) {
			WikiUser wuser = lwiki.getWikiUser();
			if (wuser == null) {
				fail(WikiUser.help(lwiki.wikiId, lwiki.wiki.getSiteurl()));
			}
			// avoid uncommenting - will show password information ...
			// lwiki.debug = true;
			assertFalse(lwiki.wiki.isLoggedIn());
			Login login = lwiki.wiki.login(wuser.getUsername(), wuser.getPassword());
			assertNotNull(login.getLguserid());
			assertEquals(wuser.getUsername().toLowerCase(), login.getLgusername()
					.toLowerCase());
			assertEquals("Success", login.getResult());
			assertNotNull(login.getLgtoken());
			assertTrue(lwiki.wiki.isLoggedIn());
			// make sure logout also works
			lwiki.wiki.logout();
			assertFalse(lwiki.wiki.isLoggedIn());
		}
	}

	@Test
	public void testLoginWrongPassword() throws Exception {
		for (ExampleWiki lwiki : getWikis()) {
			MediawikiApi ltwiki = lwiki.wiki;
			if (!ltwiki.getSiteurl().contains("mediawiki.org")) {
				WikiUser wuser = lwiki.getWikiUser();
				if (wuser == null) {
					fail(WikiUser.help(lwiki.wikiId, lwiki.wiki.getSiteurl()));
				}
				// avoid uncommenting - will show password information ...
				// lwiki.debug = true;
				assertFalse(lwiki.wiki.isLoggedIn());
				// spoilt the password
				Login login = lwiki.wiki.login(wuser.getUsername(),
						"not" + wuser.getPassword());
				assertNull(login.getLguserid());
				assertNull(login.getLgusername());
				assertEquals("WrongPass", login.getResult());
				assertNull(login.getLgtoken());
				assertFalse(lwiki.wiki.isLoggedIn());
				// make sure logout also works
				lwiki.wiki.logout();
				assertFalse(lwiki.wiki.isLoggedIn());
			}
		}
	}

	@Test
	public void testLoginNotExists() throws Exception {
		for (ExampleWiki lwiki : getWikis()) {
			MediawikiApi ltwiki = lwiki.wiki;
			if (!ltwiki.getSiteurl().contains("mediawiki.org")) {
				// System.out.println(ltwiki.getSiteurl());
				Login login = lwiki.wiki.login("someUserThatDoesNotExist",
						"somePassword");
				assertEquals("NotExists", login.getResult());
			}
		}
	}

	@Test
	public void testXMLJaxb() throws Exception {
		String xmls[] = {
				"<?xml version=\"1.0\"?><api><login result=\"NeedToken\" token=\"bd66872088b91aee85074b5c12e8ae54\" cookieprefix=\"gfwiki\" sessionid=\"b5afedfda4a183e44c9bd696b82b5191\" /></api>",
				"\n\n<?xml version=\"1.0\"?><api><login result=\"NeedToken\" token=\"c2235f17cde5d357a36bb2737c42bf55\" cookieprefix=\"masterwiki\" sessionid=\"b148720e4b9757584fcc3ad2d2f59e6a\" /></api>" };
		String tokens[] = { "bd66872088b91aee85074b5c12e8ae54",
				"c2235f17cde5d357a36bb2737c42bf55" };
		JaxbFactory<Api> apijaxbfactory = new JaxbFactory<Api>(Api.class);
		int i = 0;
		for (String xml : xmls) {
			Api result = apijaxbfactory.fromXML(xml);
			assertNotNull(result);
			assertEquals(tokens[i], result.getLogin().getToken());
			i++;
		}
	}

}
