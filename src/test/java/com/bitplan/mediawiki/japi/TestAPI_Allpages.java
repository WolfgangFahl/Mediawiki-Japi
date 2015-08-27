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

import com.bitplan.mediawiki.japi.api.Bl;
import com.bitplan.mediawiki.japi.api.Ii;
import com.bitplan.mediawiki.japi.api.Im;
import com.bitplan.mediawiki.japi.api.Imageinfo;
import com.bitplan.mediawiki.japi.api.Img;
import com.bitplan.mediawiki.japi.api.Iu;
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
    String aistart = "20080823180546";
    String aiend = "20990101235959";
    int ailimit = 500;
    // lWiki.wiki.setDebug(true);
    List<Img> images = lWiki.wiki.getAllImagesByTimeStamp(aistart, aiend,
        ailimit);
    if (debug)
      System.out.println(images.size());
    assertEquals(2, images.size());
    String[] expected = {
        "Radcliffe_Chastenay_-_Les_Mysteres_d_Udolphe_frontispice_T6.jpg",
        "Wuthering_Heights_NT.pdf" };
    for (int i = 0; i < expected.length; i++) {
      assertEquals(expected[i], images.get(i).getName());
    }
    // lWiki.wiki.setDebug(true);
    String[] expectedUsage = { "Picture Example", "PDF Example" };
    int i = 0;
    for (Img img : images) {
      List<Iu> ius = lWiki.wiki.getImageUsage("File:" + img.getName(), "", 50);
      assertTrue(ius.size() > 1);
      assertEquals(expectedUsage[i], ius.get(0).getTitle());
      i++;
    }
  }

  @Test
  public void testBacklink() throws Exception {
    ExampleWiki lWiki = ewm.get("mediawiki-japi-test1_24");
    lWiki.login();
    // lWiki.wiki.setDebug(true);
    String pageTitle = "Picture Example";
    List<Bl> bls = lWiki.wiki.getBacklinks(pageTitle, "", 50);
    assertTrue(bls.size() > 0);
    assertEquals("PDF Example", bls.get(0).getTitle());
  }

  @Test
  public void testImagesOnPage() throws Exception {
    ExampleWiki lWiki = ewm.get("mediawiki-japi-test1_24");
    lWiki.login();
    // lWiki.wiki.setDebug(true);
    String pageTitles[] = { "Picture Example", "PDF Example", "Image Example" };
    String[][] expected = {
        { "File:Radcliffe Chastenay - Les Mysteres d Udolphe frontispice T6.jpg" },
        { "File:Wuthering Heights NT.pdf" },
        {
            "File:Radcliffe Chastenay - Les Mysteres d Udolphe frontispice T6.jpg",
            "File:Wuthering Heights NT.pdf" } };
    int i = 0;
    for (String pageTitle : pageTitles) {
      List<Im> images = lWiki.wiki.getImagesOnPage(pageTitle, 100);
      assertEquals(expected[i].length, images.size());
      for (int j = 0; j < images.size(); j++) {
        assertEquals(expected[i][j], images.get(j).getTitle());
      }
      i++;
    }
  }

  @Test
  public void testImagesInfosForPage() throws Exception {
    ExampleWiki lWiki = ewm.get("mediawiki-japi-test1_24");
    lWiki.login();
    // lWiki.wiki.setDebug(true);
    String[][] expected = { { "images/0/0c/" }, { "images/a/a0" },
        { "images/0/0c/", "images/a/a0" } };
    String pageTitles[] = { "Picture Example", "PDF Example", "Image Example" };
    int i = 0;
    for (String pageTitle : pageTitles) {
      List<Ii> imageInfos = lWiki.wiki.getImageInfosForPage(pageTitle, 100);
      assertEquals(expected[i].length, imageInfos.size());
      for (int j = 0; j < imageInfos.size(); j++) {
        Ii imageInfo=imageInfos.get(j);
        assertTrue(imageInfo.getUrl(), imageInfo.getUrl()
            .contains(expected[i][j]));
        System.out.println(imageInfo.getCanonicaltitle());
      }
      i++;
    }
  }
}
