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

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.bitplan.mediawiki.japi.ExampleWiki.ExamplePage;

/**
 * test https://www.mediawiki.org/wiki/API:Edit
 * 
 * @author wf
 *
 */
public class TestAPI_Edit extends APITestbase {

	/**
	 * test getting the edit token
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetEditToken() throws Exception {
		for (ExampleWiki lwiki : wikis) {
			List<ExamplePage> exampleEditPages = lwiki
					.getExamplePages("testEditPages");
			if (exampleEditPages.size() > 0) {
				lwiki.login();
				lwiki.setDebug(true);
				for (ExamplePage examplePage : exampleEditPages) {
					String token = lwiki.getEditToken(examplePage.getTitle());
					assertNotNull(token);
				}
			}
		}
	}
}
