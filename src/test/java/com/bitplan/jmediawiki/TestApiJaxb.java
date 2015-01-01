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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.bitplan.jmediawiki.api.Api;
import com.bitplan.jmediawiki.api.Query;
import com.bitplan.jmediawiki.api.Statistics;

/**
 * test the API JAXB wrapping
 * @author wf
 *
 */
public class TestApiJaxb {

	@Test
	/**
	 * test Jaxb Api wrapping
	 * @throws Exception
	 */
	public void testApi() throws Exception {
		String xml="<?xml version=\"1.0\"?>"+
	    "<api>\n"+
		  "  <query>\n"+
	    "    <statistics pages=\"18973\" articles=\"9634\" views=\"33431\" edits=\"44019\" images=\"202\" users=\"10\" activeusers=\"4\" admins=\"6\" jobs=\"0\" />\n"+
		  "  </query>\n"+
	    "</api>\n";
		Api api=Api.fromXML(xml);
		assertNotNull(api);
		Query query = api.getQuery();
		assertNotNull(query);
		Statistics statistics = query.getStatistics();
		assertNotNull(statistics);
		assertEquals(18973L,statistics.getPages().intValue());
		assertEquals(9634,statistics.getArticles().intValue());
		// assertEquals(33431,statistics.getViews());
		assertEquals(44019,statistics.getEdits().intValue());
		assertEquals(202,statistics.getImages().intValue());
		assertEquals(10,statistics.getUsers().intValue());
		assertEquals(4,statistics.getActiveusers().intValue());
		assertEquals(6,statistics.getAdmins().intValue());
		assertEquals(0,statistics.getJobs().intValue());
	}
	
}
