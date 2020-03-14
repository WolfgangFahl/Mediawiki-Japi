package com.bitplan.mediawiki.japi;

import org.junit.Test;

/**
 * test push pages 
 * @author wf
 *
 */
public class TestPushPages extends APITestbase {

  @Test
  public void testPushPages() throws Exception {
    String sourceWikiId="wikipedia_org_test2";
    String targetWikiId="mediawiki-japi-test1_31";
    ExampleWiki sourceWiki= ewm.get(sourceWikiId);
    ExampleWiki targetWiki = ewm.get(targetWikiId);
    String[] pageTitles= {"PictureTestPage"};
    if (hasWikiUser(targetWiki)) {
      targetWiki.login();
      PushPages pp=new PushPages(sourceWiki.getMediaWikiJapi(),targetWiki.getMediaWikiJapi());
      pp.push(pageTitles);
    }
  }

}
