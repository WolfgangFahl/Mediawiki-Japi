package com.bitplan.mediawiki.japi;

import org.junit.Test;

/**
 * test push pages
 * 
 * @author wf
 *
 */
public class TestPushPages extends APITestbase {

  /**
   * get a PushPages instance for testing
   * 
   * @return the PushPages to be tested
   * @throws Exception
   */
  public PushPages getTestPushPages() throws Exception {
    PushPages pp = null;
    String sourceWikiId = "wikipedia_org_test2";
    String targetWikiId = "mediawiki-japi-test1_31";
    if (!TestSuite.isTravis()) {
      pp = new PushPages(sourceWikiId, targetWikiId,true);
    }
    return pp;
  }

  @Test
  public void testPushPagesCheck() throws Exception {
    String[] pageTitles = { "PictureTestPage" };
    PushPages pp = this.getTestPushPages();
    if (pp != null) {
      pp.setCheck(true);
      pp.setShowDebug(true);
      pp.push(pageTitles);
    }
  }

  @Test
  public void testPushPages() throws Exception {
    String[] pageTitles = { "PictureTestPage" };
    PushPages pp = this.getTestPushPages();
    if (pp != null)
      pp.push(pageTitles);
  }

}
