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

import java.util.List;

import org.junit.Test;

import com.bitplan.jmediawiki.api.Api;
import com.bitplan.jmediawiki.api.Rc;

/**
 * http://www.mediawiki.org/wiki/API:Recentchanges
 * @author wf
 *
 */
public class TestAPI_Recentchanges extends TestAPI {
	/**
	 * test http://www.mediawiki.org/wiki/API:Recentchanges
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetRecentChanges() throws Exception {
		Api api = getQueryResult("&list=recentchanges&rcprop=title%7Cids%7Csizes%7Cflags%7Cuser&rclimit=3");
		List<Rc> rcList = api.getQuery().getRecentchanges();
		assertEquals(3, rcList.size());
	}
}
