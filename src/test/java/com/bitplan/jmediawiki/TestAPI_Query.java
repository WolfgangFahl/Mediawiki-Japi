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

import java.util.List;
import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.jmediawiki.ExampleWiki.ExamplePage;
import com.bitplan.jmediawiki.api.Api;
import com.bitplan.jmediawiki.api.P;
import com.bitplan.jmediawiki.api.Page;
import com.bitplan.jmediawiki.api.Rev;

/**
 * test some of the Mediawiki API calls at
 * http://www.mediawiki.org/wiki/API:Query
 * 
 * @author wf
 *
 */
public class TestAPI_Query extends TestAPI {

	/**
	 * test the Allpages API call http://www.mediawiki.org/wiki/API:Allpages
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetAllPages() throws Exception {
		for (ExampleWiki lwiki : wikis) {
			Api api = getQueryResult(lwiki, "&list=allpages&apfrom=Kre&aplimit=3");
			List<P> pageRefList = api.getQuery().getAllpages();
			assertEquals(lwiki.getSiteurl(),3, pageRefList.size());
		}
	}

	@Test
	public void testGetPages() throws Exception {
		for (ExampleWiki lwiki : wikis) {
			List<ExamplePage> examplePages = lwiki.getExamplePages("testGetPages");
			String titles="";
			String delim="";
			for (ExamplePage page:examplePages) {
				titles=titles+delim+wiki.normalize(page.getTitle());
				delim="%7C";
			}
			Api api = getQueryResult(lwiki,
					"&titles=" + titles
							+ "&prop=revisions&rvprop=content");
			List<Page> pages = api.getQuery().getPages();
			assertEquals(2, pages.size());
			int index=0;
			for (Page page : pages) {
				ExamplePage expected=examplePages.get(index++);
				if (debug) {
					LOGGER.log(Level.INFO, page.getTitle());
				}
				assertEquals(lwiki.getSiteurl(),expected.getTitle(),page.getTitle());
				Rev rev = page.getRevisions().get(0);
				if (debug) {
					LOGGER.log(Level.INFO, rev.getValue());
				}
				assertTrue(rev.getValue().contains(expected.getContentPart()));
			}
		}
	}
}
