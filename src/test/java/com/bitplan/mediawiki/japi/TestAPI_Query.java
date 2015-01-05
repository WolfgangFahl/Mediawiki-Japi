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
import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.ExampleWiki.ExamplePage;
import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.P;
import com.bitplan.mediawiki.japi.api.Page;
import com.bitplan.mediawiki.japi.api.Rev;

/**
 * test some of the Mediawiki API calls at
 * http://www.mediawiki.org/wiki/API:Query
 * 
 * @author wf
 *
 */
public class TestAPI_Query extends APITestbase {

	/**
	 * test the Allpages API call http://www.mediawiki.org/wiki/API:Allpages
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetAllPages() throws Exception {
		for (ExampleWiki lwiki : getWikis()) {
			Api api = getQueryResult(lwiki, "&list=allpages&apfrom=Kre&aplimit=3");
			List<P> pageRefList = api.getQuery().getAllpages();
			assertEquals(lwiki.getSiteurl(),3, pageRefList.size());
		}
	}

	@Test
	public void testGetPages() throws Exception {
		for (ExampleWiki lwiki : getWikis()) {
			List<ExamplePage> examplePages = lwiki.getExamplePages("testGetPages");
			List<String> titles=lwiki.getTitleList(examplePages);
			List<Page> pages=lwiki.getPages(titles);
			
			assertEquals(2, pages.size());
			int index=0;
			for (Page page : pages) {
				ExamplePage expected=examplePages.get(index++);
				if (debug) {
					LOGGER.log(Level.INFO, page.getTitle());
				}
				assertEquals(lwiki.getSiteurl(),expected.getTitle(),page.getTitle());
				assertTrue(lwiki.getSiteurl()+"/"+lwiki.scriptPath+":"+expected.getTitle(),page.getRevisions().size()>0);
				Rev rev = page.getRevisions().get(0);
				if (debug) {
					LOGGER.log(Level.INFO, rev.getValue());
				}
				assertTrue(rev.getValue().contains(expected.getContentPart()));
			}
		}
	}
}
