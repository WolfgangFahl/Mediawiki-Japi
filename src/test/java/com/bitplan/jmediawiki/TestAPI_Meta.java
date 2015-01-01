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

import org.junit.Test;

import com.bitplan.jmediawiki.api.Api;
import com.bitplan.jmediawiki.api.General;
import com.bitplan.jmediawiki.api.Query;
import com.bitplan.jmediawiki.api.Statistics;

/**
 * Test for 
 * http://www.mediawiki.org/wiki/API:Meta
 */
public class TestAPI_Meta extends TestAPI {

	/**
	 * http://www.mediawiki.org/wiki/API:Meta#siteinfo_.2F_si
	 * @throws Exception
	 */
	@Test
	public void testGeneralSiteInfo() throws Exception {
		Api api=getQueryResult("&meta=siteinfo");
	  assertNotNull(api);
	  General general = api.getQuery().getGeneral();
	  assertNotNull(general);
	  assertEquals("//upload.wikimedia.org/wikipedia/mediawiki/b/bc/Wiki.png",general.getLogo());
	}
	
	@Test
	/**
	 * test statistics
	 * @throws Exception
	 */
	public void testStatistics() throws Exception {
		Api api=getQueryResult("&meta=siteinfo&siprop=statistics");
	  assertNotNull(api);
		Query query = api.getQuery();
		assertNotNull(query);
		Statistics statistics = query.getStatistics();
		assertNotNull(statistics);
		assertTrue(statistics.getPages().intValue()>20000);
	}
}
