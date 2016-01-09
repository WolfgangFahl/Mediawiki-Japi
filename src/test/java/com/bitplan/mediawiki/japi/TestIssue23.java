package com.bitplan.mediawiki.japi;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * https://github.com/WolfgangFahl/Mediawiki-Japi/issues/23
 * @author wf
 *
 */
public class TestIssue23 {

	@Test
	public void testSampleQuery() throws Exception {
		Mediawiki wiki = new Mediawiki("http://en.wikipedia.org");
		String content = wiki.getPageContent("Main Page");
		assertTrue(content.contains("Wikipedia"));
	}

}
