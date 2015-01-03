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

import org.junit.Test;

import com.bitplan.mediawiki.japi.Mediawiki;
import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Page;

/**
 * Junit Test to show off how to use Mediawiki-Japi
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
		Mediawiki wiki=new Mediawiki("http://en.wikipedia.org");
		String content=wiki.getPageContent("Main Page");
		assertTrue(content.contains("Wikipedia"));
	}
}
