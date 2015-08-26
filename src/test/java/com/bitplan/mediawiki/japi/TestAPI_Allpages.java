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

import com.bitplan.mediawiki.japi.api.Img;
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
  
  @Test
  public void testAllImages() throws Exception {
    ExampleWiki lWiki = ewm.get("mediawiki-japi-test1_24");
    lWiki.login();
    String aistart="20080823180546";
    String aiend="20990101235959";
    // lWiki.wiki.setDebug(true);
    List<Img> images = lWiki.wiki.getAllImagesByTimeStamp(aistart,aiend);
    if (debug)
      System.out.println(images.size());
    assertEquals(2,images.size());
    String[] expected={"Radcliffe_Chastenay_-_Les_Mysteres_d_Udolphe_frontispice_T6.jpg","Wuthering_Heights_NT.pdf"};
    for (int i=0;i<expected.length;i++) {
      assertEquals(expected[i],images.get(i).getName());
    }
  }

}
