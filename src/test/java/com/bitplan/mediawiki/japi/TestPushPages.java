/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2021 BITPlan GmbH https://github.com/BITPlan
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
  public static PushPages getTestPushPages() throws Exception {
    PushPages pp = null;
    String sourceWikiId = "wikipedia_org_test2";
    String targetWikiId = "mw31test";
    if (!TestSuite.isTravis()) {
      pp = new PushPages(sourceWikiId, targetWikiId,true);
    }
    return pp;
  }

  @Test
  public void testPushPagesCheck() throws Exception {
    String[] pageTitles = { "PictureTestPage" };
    PushPages pp = getTestPushPages();
    if (pp != null) {
      pp.setCheck(true);
      // uncomment to see images in desktop environment
      // pp.setShowDebug(true);
      pp.push(pageTitles);
    }
  }

  @Test
  public void testPushPages() throws Exception {
    String[] pageTitles = { "PictureTestPage" };
    PushPages pp = getTestPushPages();
    if (pp != null)
      pp.push(pageTitles);
  }

}
