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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.S;

/**
 * test https://www.mediawiki.org/wiki/API:Parse
 * 
 * @author wf
 *
 */
public class TestAPI_Parse extends APITestbase {

  @Test
  public void testGetSectionList() throws Exception {
    // http://stackoverflow.com/questions/16840447/retrieve-the-content-of-a-section-via-mediawiki-api
    Mediawiki wiki = new Mediawiki("https://en.wikipedia.org", "/w");
    // wiki.debug=true;
    String[] pageTitles = { "License", "Hierarchy" };
    // debug=true;
    for (String pageTitle : pageTitles) {
      List<S> sections = wiki.getSections(pageTitle);
      assertNotNull(sections);
      assertTrue(sections.size() > 5);
      for (S section : sections) {
        String sectionMsg = section.getIndex() + "-" + section.getLevel() + ":"
            + section.getToclevel() + " " + section.getNumber() + " "
            + section.getAnchor();
        if (debug) {
          LOGGER.log(Level.INFO, sectionMsg);
        }
      }
    }
  }

  @Test
  public void testGetPageHtml() throws Exception {
    Mediawiki wiki = new Mediawiki("https://en.wikipedia.org");
    wiki.setDebug(debug);
    String[] pageTitles = { "Quikwriting", "Überlingen" };
    String[] expected= {"<a href=\"/wiki/Graffiti_(Palm_OS)\"","Constance"};
    int i=0;
    for (String pageTitle : pageTitles) {
      String html = wiki.getPageHtml(pageTitle);
      // debug=true;
      if (debug) {
        LOGGER.log(Level.INFO, html);
      }
      assertTrue(html.contains(expected[i++]));
    }
  }

}
