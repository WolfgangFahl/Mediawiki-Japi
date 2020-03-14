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
    ExampleWiki sourceWiki = ewm.get("wikipedia_org_test2");
    ExampleWiki targetWiki = ewm.get("mediawiki-japi-test1_31");
    String[] pageTitles= {"PictureTestPage"};
    if (hasWikiUser(targetWiki)) {
      PushPages.push(sourceWiki.getMediaWikiJapi(),targetWiki.getMediaWikiJapi(), pageTitles);
    }
  }

}
