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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.ExampleWiki.ExamplePage;
import com.bitplan.mediawiki.japi.Mediawiki.TokenResult;
import com.bitplan.mediawiki.japi.api.Edit;

/**
 * test https://www.mediawiki.org/wiki/API:Edit
 * 
 * @author wf
 *
 */
public class TestAPI_Edit extends APITestbase {

  @Test
  public void testSplitParams() throws Exception {
    Mediawiki wiki = new Mediawiki();
    String params = "&a=1&b=2";
    Map<String, String> paramMap = wiki.getParamMap(params);
    assertEquals(2, paramMap.size());
  }

  /**
   * test getting the edit token
   * 
   * @throws Exception
   */
  @Test
  public void testGetEditToken() throws Exception {
    for (ExampleWiki lwiki : getWikis()) {
      List<ExamplePage> exampleEditPages = lwiki
          .getExamplePages("testEditPages");
      if (exampleEditPages.size() > 0) {
        lwiki.login();
        // lwiki.setDebug(true);
        for (ExamplePage examplePage : exampleEditPages) {
          TokenResult token = lwiki.getMediaWikiJapi().getEditToken(
              examplePage.getTitle());
          assertNotNull(lwiki.getWikiId(), token);
          assertEquals("token", token.tokenName);
        }
      }
    }
  }

  @Test
  public void testhttps() {
    // this is a fake test for educational purposes ..
    // String text=lwiki.getPageContent("https://www.wikipedia.de");
    // assertTrue(lwiki.contains("Wikimedia Deutschland"));
  }

  /**
   * test editing a page
   * 
   * @throws Exception
   */
  @Test
  public void TestEdit() throws Exception {
    for (ExampleWiki lwiki : getWikis()) {
      List<ExamplePage> exampleEditPages = lwiki
          .getExamplePages("testEditPages");
      if (exampleEditPages.size() > 0) {
        lwiki.login();
        // lwiki.setDebug(true);
        for (ExamplePage examplePage : exampleEditPages) {
          String summary = "edit by TestAPI_Edit at "
              + lwiki.wiki.getIsoTimeStamp();
          Edit edit = lwiki.wiki.edit(examplePage.getTitle(),
              examplePage.getContentPart(), summary);
          // FIXME - check when there is no success e.g. for a page that is
          // protected
          // the Success check should be in the Mediawiki class and throw an
          // exception if not successful
          assertEquals("Success", edit.getResult());
          String newContent = lwiki.wiki.getPageContent(examplePage.getTitle());
          assertEquals(examplePage.getContentPart(), newContent);
        }
      }
    }
  }

  @Test
  public void TestURLengthLimit() throws Exception {
    ExampleWiki lwiki = ewm.get("mediawiki-japi-test1_24");
    lwiki.login();
    // lwiki.setDebug(true);
    String text = "012345678901234567890123456789012345678901234567890123456789\n";
    // blow up text up to level 10: len=62464
    for (int i = 1; i <= 10; i++) {
      text = text + text;
      if (i >= 6) { // test from level 6: len=3904 - usual bail out would be at
                    // 7808
        boolean show = debug;
        if (show)
          LOGGER.log(Level.INFO, "level " + i + ": len=" + text.length());
        String title = "urllengthlimit";
        Edit edit = lwiki.wiki.edit(title, text, "len:" + text.length());
        assertEquals("Success", edit.getResult());
        String newContent = lwiki.wiki.getPageContent(title);
        assertEquals("" + text.length() + "<>" + newContent.length(),
            text.trim(), newContent.trim());
      }
    }
  }

  @Test
  public void testEditNoLogin() throws Exception {
    ExampleWiki lwiki = ewm.get("mediawiki-japi-test1_24");
    lwiki.login();
    // FIXME need TestEditNoLogin - should throw an Exception with Message
    // Action 'edit' is not allowed for
    // current user
  }

  
  /**
   * test copying a page from a source Wiki to a target Wiki
   * 
   * @throws Exception
   */
  @Test
  public void testCopy() throws Exception {
    // debug = true;
    ExampleWiki sourceWiki = ewm.get("sourceWiki");
    sourceWiki.wiki.setDebug(debug);
    ExampleWiki targetWiki = ewm.get("targetWiki");
    targetWiki.login();
    // targetWiki.setDebug(true);
    List<ExamplePage> examplePages = sourceWiki.getExamplePages("testCopy");
    // List<String> titles=sourceWiki.getTitleList(examplePages);
    for (ExamplePage examplePage : examplePages) {
      String pageTitle = examplePage.getTitle();
      String summary = "created/edited by TestAPI_Edit at "
          + sourceWiki.wiki.getIsoTimeStamp();
      sourceWiki.getMediaWikiJapi().copyToWiki(targetWiki.wiki,
          pageTitle, summary);
      
      if (pageTitle.startsWith("File:")) {
        String sourceUrl=sourceWiki.wiki.getImageInfo(pageTitle).getUrl();
        assertTrue("url '"+sourceUrl+"' should exist",this.urlExists(sourceUrl));
        String targetUrl=targetWiki.wiki.getImageInfo(pageTitle).getUrl();
        assertTrue("url '"+targetUrl+"' should exist",this.urlExists(targetUrl));
      } else {
        String sourceContent = sourceWiki.wiki.getPageContent(pageTitle);
        String targetContent = targetWiki.wiki.getPageContent(pageTitle);
        if (debug) {
          LOGGER.log(Level.INFO, "source " + sourceWiki.wiki.getSiteurl()
              + sourceWiki.wiki.getScriptPath() + " " + examplePage.getTitle());
          LOGGER.log(Level.INFO, "target " + targetWiki.wiki.getSiteurl()
              + targetWiki.wiki.getScriptPath() + " " + examplePage.getTitle());
        }
        assertEquals(sourceContent, targetContent);
      }
    }
  }

  /**
   * test editing a section
   * 
   * @throws Exception
   */
  @Test
  public void testEditSection() throws Exception {
    ExampleWiki targetWiki = ewm.get("targetWiki");
    ExamplePage editSectionPage = targetWiki.getExamplePages("testSectionEdit")
        .get(0);
    String pageTitle = editSectionPage.getTitle();
    targetWiki.login();
    String summary = "created/edited by TestAPI_Edit at "
        + targetWiki.wiki.getIsoTimeStamp();
    targetWiki.wiki.edit(pageTitle, editSectionPage.getContentPart(), summary);
    String section2 = targetWiki.wiki.getSectionText(pageTitle, 2);
    assertEquals("=== section 2 ===", section2);
    String section3Title = "section 3";
    String section3Content = "section 3 content";
    targetWiki.wiki.edit(pageTitle, section3Content, summary, true, false, -1,
        section3Title, null);
    String section3Edit = targetWiki.wiki.getSectionText(pageTitle, 3);
    // System.out.println(section3Edit);
    assertEquals("== section 3 ==\n" + "\n" + "section 3 content", section3Edit);
  }

}
