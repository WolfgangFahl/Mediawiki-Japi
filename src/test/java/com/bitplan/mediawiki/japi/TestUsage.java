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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Junit Test to show off how to use Mediawiki-Japi
 * 
 * @author wf
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUsage {

  /**
   * http://www.mediawiki.org/wiki/API:Query#Sample_query
   * http://en.wikipedia.org
   * /w/api.php?action=query&prop=revisions&rvprop=content
   * &titles=Main%20Page&format=xml
   * 
   * @throws Exception
   */
  @Test
  public void testSampleQuery() throws Exception {
    Mediawiki wiki = new Mediawiki("http://en.wikipedia.org");
    String content = wiki.getPageContent("Main Page");
    assertTrue(content.contains("Wikipedia"));
  }

  @Test
  /**
   * Test for issue #3
   * @throws Exception
   */
  public void testMultipleImplementations() throws Exception {
    MediawikiApi[] wikiapis = { new Mediawiki(), new org.wikipedia.Mediawiki() };
    for (MediawikiApi wikiapi : wikiapis) {
      wikiapi.setSiteurl("http://en.wikipedia.org");
      String content = wikiapi.getPageContent("Main Page");
      boolean debug=true;
      if (debug) {
        System.out.println(content);
      }
      assertTrue(content.contains("Wikipedia"));
    }
  }
}
