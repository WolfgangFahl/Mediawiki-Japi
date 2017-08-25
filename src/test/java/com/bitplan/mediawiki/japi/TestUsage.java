/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2017 BITPlan GmbH https://github.com/BITPlan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *
 *  http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    Mediawiki wiki = new Mediawiki("https://en.wikipedia.org");
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
    // MediawikiApi[] wikiapis = {  new org.wikipedia.Mediawiki() };
    for (MediawikiApi wikiapi : wikiapis) {
      wikiapi.setSiteurl("https://test.wikipedia.org");
      String content = wikiapi.getPageContent("Main Page");
      boolean debug=false;
      if (debug) {
        System.out.println(content);
      }
      assertTrue(content.contains("Wikipedia"));
    }
  }
}
