/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2017 BITPlan GmbH https://github.com/BITPlan
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.ExampleWiki.ExamplePage;
import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Ii;
import com.bitplan.mediawiki.japi.api.P;
import com.bitplan.mediawiki.japi.api.Page;
import com.bitplan.mediawiki.japi.api.Rev;

/**
 * test some of the Mediawiki API calls at
 * http://www.mediawiki.org/wiki/API:Query
 * 
 * @author wf
 *
 */
public class TestAPI_Query extends APITestbase {

  /**
   * test the Allpages API call http://www.mediawiki.org/wiki/API:Allpages
   * 
   * @throws Exception
   */
  @Test
  public void testGetAllPages() throws Exception {
    for (ExampleWiki lwiki : getWikis()) {
      Api api = getQueryResult(lwiki, "&list=allpages&apfrom=Kre&aplimit=3");
      List<P> pageRefList = api.getQuery().getAllpages();
      assertEquals(lwiki.wiki.getSiteurl(), 3, pageRefList.size());
    }
  }

  @Test
  public void testGetPages() throws Exception {
    for (ExampleWiki lwiki : getWikis()) {
      List<ExamplePage> examplePages = lwiki.getExamplePages("testGetPages");
      List<String> titles = lwiki.getTitleList(examplePages);
      List<Page> pages = lwiki.getMediaWikiJapi().getPages(titles);

      assertEquals(2, pages.size());
      int index = 0;
      for (Page page : pages) {
        ExamplePage expected = examplePages.get(index++);
        if (debug) {
          LOGGER.log(Level.INFO, page.getTitle());
        }
        assertEquals(lwiki.wiki.getSiteurl(), expected.getTitle(),
            page.getTitle());
        // FIXME add to interface
        assertTrue(
            lwiki.wiki.getSiteurl() + "/"
                + lwiki.getMediaWikiJapi().getScriptPath() + ":"
                + expected.getTitle(), page.getRevisions().size() > 0);
        Rev rev = page.getRevisions().get(0);
        if (debug) {
          LOGGER.log(Level.INFO, rev.getValue());
        }
        assertTrue(rev.getValue().contains(expected.getContentPart()));
      }
    }
  }

  @Test
  public void testImageInfo() throws Exception {
	/// choose the 1.23 targetWiki since it has proper imageinfo implementation
    ExampleWiki imageWiki = ewm.get("targetWiki");
    //debug=true;
    if (debug)
    	imageWiki.getMediaWikiJapi().setDebug(debug);
    Ii ii=imageWiki.getImageInfo("File:Index.png");
    if (debug) {
      System.out.println(ii.getUrl());
      // System.out.println(ii.getCanonicaltitle());
      System.out.println(ii.getWidth()+"x"+ii.getHeight());
    }
    assertEquals("http://mediawiki-japi.bitplan.com/mw1_23/images/a/ae/Index.png",ii.getUrl());
    int expectedWidth=32;
    int expectedHeight=32;
    assertTrue(expectedHeight==ii.getHeight());
    assertTrue(expectedWidth==ii.getWidth());
  }
}
