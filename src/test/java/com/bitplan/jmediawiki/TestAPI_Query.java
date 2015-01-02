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
		Api api = getQueryResult("&list=allpages&apfrom=Kre&aplimit=5");
		List<P> pageRefList = api.getQuery().getAllpages();
		assertEquals(5, pageRefList.size());
	}

	@Test
	public void testGetPages() throws Exception {
		Api api= getQueryResult("&titles=2011_Wikimedia_fundraiser%7C2012_Wikimedia_fundraiser&prop=revisions&rvprop=content");
		List<Page> pages = api.getQuery().getPages();
		assertEquals(2,pages.size());
		for (Page page:pages) {
			if (debug) {
				LOGGER.log(Level.INFO,page.getTitle());
			}
			assertTrue(page.getTitle().contains("Wikimedia fundraiser"));
			Rev rev= page.getRevisions().get(0);
			if (debug) {
				LOGGER.log(Level.INFO,rev.getValue());
			}
			assertTrue(rev.getValue().contains("{{Wikimedia engineering project information"));
		}
	}
}
