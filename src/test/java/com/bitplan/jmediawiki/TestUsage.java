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
import com.bitplan.jmediawiki.api.Page;

/**
 * Junit Test to show off how to use JMediawiki
 * @author wf
 *
 */
public class TestUsage {

	
	/**
	 * http://www.mediawiki.org/wiki/API:Query#Sample_query
	 * http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&titles=Main%20Page&format=xml
	 * @throws Exception 
	 */
	@Test
	public void testSampleQuery() throws Exception {
		JMediawiki wiki=new JMediawiki("http://en.wikipedia.org");
		Api api = wiki.getQueryResult("&prop=revisions&rvprop=content&titles=Main%20Page");
		Page page=api.getQuery().getPages().get(0);
		assertEquals("Main Page",page.getTitle());
		String content=page.getRevisions().get(0).getValue();	
		assertTrue(content.contains("Wikipedia"));
	}
}
