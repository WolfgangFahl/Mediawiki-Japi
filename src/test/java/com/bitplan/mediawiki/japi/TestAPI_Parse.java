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
        String sectionMsg = section.getIndex() + "-"+section.getLevel()+":"+section.getToclevel()+" "+section.getNumber()+" "+section.getAnchor();
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
    String pageTitle="Quikwriting";
    String html=wiki.getPageHtml(pageTitle);
    // debug=true;
    if (debug) {
      LOGGER.log(Level.INFO,html);
    }
    assertTrue(html.contains("<a href=\"/wiki/Graffiti_(Palm_OS)\""));
  }

}
