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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.junit.Ignore;
import org.junit.Test;

import com.bitplan.mediawiki.japi.ExampleWiki.ExamplePage;
import com.bitplan.mediawiki.japi.Mediawiki.TokenResult;
import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Edit;

/**
 * test http://www.mediawiki.org/wiki/API:Account_creation
 * 
 * @author wf
 *
 */
public class TestCreateAccount extends APITestbase {

	/**
	 * test getting the create Account token
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateAccount() throws Exception {
		ExampleWiki lwiki = ewm.get("mediawiki-japi-test1_24");
		Mediawiki wiki = (Mediawiki) lwiki.wiki;
		lwiki.login();
		// lwiki.wiki.setDebug(true);
		Api api=wiki.createAccount("JohnDoe4","wf@bitplan.com","John%20Doe",true,"SMWCon2015-05","en");
		assertNull(api.getWarnings());
		String result=api.getCreateaccount().getResult();
		assertEquals("Success",result);
	}

}
