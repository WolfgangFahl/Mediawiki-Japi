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

import java.util.List;
import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.P;

/**
 * test https://www.mediawiki.org/wiki/API:Allpages
 * 
 * @author wf
 *
 */
public class TestAPI_Allpages extends APITestbase {

  @Test
  public void testAllpages() throws Exception {
    ExampleWiki lWiki = ewm.get("mediawiki-japi-test1_24");
    lWiki.login();
    String apfrom = null;
    int aplimit = 25000;
    List<P> pages = lWiki.wiki.getAllPages(apfrom, aplimit);
    if (debug) {
      LOGGER.log(Level.INFO, "page #=" + pages.size());
    }
    assertTrue(pages.size() >= 7);
  }

}
