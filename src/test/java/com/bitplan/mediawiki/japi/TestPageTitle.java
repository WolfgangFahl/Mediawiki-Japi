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

/**
 * test page Title handling
 * @author wf
 *
 */
public class TestPageTitle extends APITestbase {

  @Test 
  public void testCanonicalPageTitles() throws Exception {
    for (ExampleWiki lwiki : getWikis()) {
      Mediawiki mw=(Mediawiki) lwiki.wiki;
      SiteInfo siteinfo = mw.getSiteInfo();
      PageInfo pageinfo=new PageInfo("Template:Testtemplate",siteinfo);
      if (debug) {
        System.out.println(pageinfo.lang);
        System.out.println(pageinfo.namespaceId);
        System.out.println(pageinfo.nameSpaceName);
        System.out.println(pageinfo.pageTitle);
        System.out.println(pageinfo.canonicalPageTitle);
      }
      assertEquals("en",pageinfo.lang);
      assertEquals(10,pageinfo.namespaceId);
      assertEquals("Template",pageinfo.nameSpaceName);
      assertEquals("Template:Testtemplate",pageinfo.pageTitle);
      assertEquals("Template:Testtemplate",pageinfo.canonicalPageTitle);
    }
  }
}
