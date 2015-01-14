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

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.ExampleWiki.ExamplePage;
import com.bitplan.mediawiki.japi.Mediawiki.TokenResult;
import com.bitplan.mediawiki.japi.api.Edit;

/**
 * test https://www.mediawiki.org/wiki/API:Edit
 * 
 * @author wf
 *
 */
public class TestAPI_Edit extends APITestbase {

	@Test
	public void testSplitParams() throws Exception {
		Mediawiki wiki=new Mediawiki();
		String params="&a=1&b=2";
		Map<String, String> paramMap = wiki.getParamMap(params);
		assertEquals(2,paramMap.size());
	}
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
					TokenResult token = lwiki.getEditToken(examplePage.getTitle());
					assertNotNull(lwiki.getWikiId(),token);
					assertEquals("token",token.tokenName);
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
				//lwiki.setDebug(true);
				for (ExamplePage examplePage : exampleEditPages) {
					String summary = "edit by TestAPI_Edit at "+lwiki.getIsoTimeStamp();
					Edit edit=lwiki.edit(examplePage.getTitle(), examplePage.getContentPart(),
							summary);
					// FIXME - check when there is no success e.g. for a page that is protected
					// the Success check should be in the Mediawiki class and throw an exception if not successful
					assertEquals("Success",edit.getResult());
				}
			}
		}
	}
	@Test
	public void TestURLengthLimit() throws Exception {
	  ExampleWiki lwiki=ExampleWiki.get("mediawiki-japi-test1_24");
	  lwiki.login();
	  // lwiki.setDebug(true);
	  String text="012345678901234567890123456789012345678901234567890123456789\n";
	  // blow up text upto level 10: len=62464
	  for (int i=1;i<=10;i++) {
	    text=text+text;
	    if (i>=6) { // test from level 6: len=3904 - usual bail out would be at 7808
	      boolean show=debug;
	      if (show)
	        LOGGER.log(Level.INFO,"level "+i+": len="+text.length());
	      Edit edit=lwiki.edit("urllengthlimit", text, "len:"+text.length());
	      assertEquals("Success",edit.getResult());
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
