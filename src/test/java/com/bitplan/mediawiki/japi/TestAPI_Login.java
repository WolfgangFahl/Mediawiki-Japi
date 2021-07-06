/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2021 BITPlan GmbH https://github.com/BITPlan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *
 *  http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bitplan.mediawiki.japi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.Mediawiki.TokenResult;
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
		if (hasWikiUser(getWiki())) {
			WikiUser wuser = getWiki().getWikiUser();
			assertNotNull("User credentials not found/configured", wuser);
			check("email", wuser.getEmail());
			assertNotNull(wuser.getPassword());
		}
	}
	
	/**
	 * test the Version Comparison to be lenient
	 * @see <a href="https://github.com/WolfgangFahl/Mediawiki-Japi/issues/58">Issue 58</a>
	 * @throws Exception
	 */
	@Test
	public void testVersionComparison() throws Exception {
		Mediawiki mw=new Mediawiki();
		mw.setVersion("1.35.2");
		assertTrue(mw.isVersion128());
	}

	/**
	 * http://www.mediawiki.org/wiki/API:Login
	 */
	@Test
	public void testLoginToken() throws Exception {
		for (ExampleWiki lwiki : getWikis()) {
			if (hasWikiUser(lwiki)) {
				WikiUser wuser = lwiki.getWikiUser();
				if (wuser == null)
					throw new Exception("wiki user for " + lwiki.wikiId + " not configured properly");
				Mediawiki ltWiki = lwiki.getMediaWikiJapi();
				// do not keep uncommented - password will be visible in log
				// lwiki.getMediaWikiJapi().setDebug(true);
				TokenResult token = ltWiki.prepareLogin(wuser.getUsername());
				assertNotNull(token);
				Login login = ltWiki.login(wuser.getUsername(), wuser.getPassword());
				assertNotNull(login);
				assertNotNull("lguserid should not be null for " + lwiki.getWikiId(), login.getLguserid());
			}
		}
	}

	/**
	 * test Login and logout see
	 * <a href='http://www.mediawiki.org/wiki/API:Login'>API:Login</a> beware of
	 * https://phabricator.wikimedia.org/T137805 deprecation of action=login with
	 * main account
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogin() throws Exception {
		for (ExampleWiki lwiki : getEditableWikis()) {
			LOGGER.log(Level.INFO,String.format("Testing login for %s",lwiki.wikiId));
			if (hasWikiUser(lwiki)) {
				WikiUser wuser = lwiki.getWikiUser();
				if (wuser == null) {
					fail(WikiUser.help(lwiki.wikiId, lwiki.wiki.getSiteurl()));
				}
				// avoid uncommenting - will show password information ...
				// lwiki.getMediaWikiJapi().debug=true;
				assertFalse(lwiki.wiki.isLoggedIn());
				Login login = lwiki.wiki.login(wuser.getUsername(), wuser.getPassword());
				assertNotNull(login.getLguserid());
				String wUsername = wuser.getUsername().toLowerCase();
				String lUsername = login.getLgusername().toLowerCase();
				assertEquals(wUsername, lUsername);
				assertEquals("Success", login.getResult());
				assertNotNull(String.format("There should be a login token for %s", lwiki.wikiId), login.getLgtoken());
				assertTrue(lwiki.wiki.isLoggedIn());
				// make sure logout also works
				lwiki.wiki.logout();
				assertFalse(lwiki.wiki.isLoggedIn());
			}
		}
	}

	@Test
	public void testLoginWrongPassword() throws Exception {
		for (ExampleWiki lwiki : getEditableWikis()) {
			if (hasWikiUser(lwiki)) {
				MediawikiApi ltwiki = lwiki.wiki;
				WikiUser wuser = lwiki.getWikiUser();
				if (wuser == null) {
					fail(WikiUser.help(lwiki.wikiId, lwiki.wiki.getSiteurl()));
				}
				// avoid uncommenting - will show password information ...
				// lwiki.debug = true;
				assertFalse(ltwiki.isLoggedIn());
				// spoilt the password
				Login login = lwiki.wiki.login(wuser.getUsername(), "not" + wuser.getPassword());
				assertNull(login.getLguserid());
				assertNull(login.getLgusername());
				assertEquals("Failed", login.getResult());
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
		for (ExampleWiki lwiki : getEditableWikis()) {
			if (hasWikiUser(lwiki)) {
				MediawikiApi ltwiki = lwiki.wiki;
				// System.out.println(ltwiki.getSiteurl());
				Login login = ltwiki.login("someUserThatDoesNotExist", "somePassword");
				assertEquals("Failed", login.getResult());
			}
		}
	}

	@Test
	public void testXMLJaxb() throws Exception {
		String xmls[] = {
				"<?xml version=\"1.0\"?><api><login result=\"NeedToken\" token=\"bd66872088b91aee85074b5c12e8ae54\" cookieprefix=\"gfwiki\" sessionid=\"b5afedfda4a183e44c9bd696b82b5191\" /></api>",
				"\n\n<?xml version=\"1.0\"?><api><login result=\"NeedToken\" token=\"c2235f17cde5d357a36bb2737c42bf55\" cookieprefix=\"masterwiki\" sessionid=\"b148720e4b9757584fcc3ad2d2f59e6a\" /></api>" };
		String tokens[] = { "bd66872088b91aee85074b5c12e8ae54", "c2235f17cde5d357a36bb2737c42bf55" };
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
