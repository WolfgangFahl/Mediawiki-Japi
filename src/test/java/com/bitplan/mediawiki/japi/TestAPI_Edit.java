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

import java.util.List;

import org.junit.Test;

import com.bitplan.mediawiki.japi.ExampleWiki.ExamplePage;
import com.bitplan.mediawiki.japi.api.Edit;

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
		for (ExampleWiki lwiki : getWikis()) {
			List<ExamplePage> exampleEditPages = lwiki
					.getExamplePages("testEditPages");
			if (exampleEditPages.size() > 0) {
				lwiki.login();
				// lwiki.setDebug(true);
				for (ExamplePage examplePage : exampleEditPages) {
					String token = lwiki.getEditToken(examplePage.getTitle());
					assertNotNull(token);
				}
			}
		}
	}

	/**
	 * test editing a page
	 * 
	 * @throws Exception
	 */
	@Test
	public void TestEdit() throws Exception {
		for (ExampleWiki lwiki : getWikis()) {
			List<ExamplePage> exampleEditPages = lwiki.getExamplePages("testEditPages");
			if (exampleEditPages.size() > 0) {
				lwiki.login();
				// lwiki.setDebug(true);
				for (ExamplePage examplePage : exampleEditPages) {
					String summary = "edit by TestAPI_Edit at "+lwiki.getIsoTimeStamp();
					Edit edit=lwiki.edit(examplePage.getTitle(), examplePage.getContentPart(),
							summary);
					// FIXME - check when there is no success e.g. for a page that is protected
					// the Sucess check should be in the Mediawiki class and throw an exception if not successful
					assertEquals("Success",edit.getResult());
				}
			}
		}
	}
	
	// FIXME need TestEditNoLogin - should throw an Exception with Message Action 'edit' is not allowed for
	// current user
	
	/**
	 * test copying a page from a source Wiki to a target Wiki
	 * @throws Exception
	 */
	@Test
	public void TestCopy() throws Exception {
		ExampleWiki sourceWiki = ExampleWiki.get("sourceWiki");
		// sourceWiki.setDebug(true);
		ExampleWiki targetWiki = ExampleWiki.get("targetWiki");
		targetWiki.login();
		// targetWiki.setDebug(true);
		List<ExamplePage> examplePages = sourceWiki.getExamplePages("testCopy");
		// List<String> titles=sourceWiki.getTitleList(examplePages);
		for (ExamplePage examplePage:examplePages) {
			String summary="created/edited by TestAPI_Edit at "+sourceWiki.getIsoTimeStamp();
			String sourceContent=sourceWiki.getPageContent(examplePage.getTitle());
			sourceWiki.copyToWiki(targetWiki,examplePage.getTitle(), summary);
			String targetContent=targetWiki.getPageContent(examplePage.getTitle());
			assertEquals(sourceContent,targetContent);
		}
	}
}
