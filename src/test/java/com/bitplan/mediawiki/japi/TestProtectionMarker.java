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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * check that the protection marker concept works
 * 
 * @author wf
 *
 */
public class TestProtectionMarker extends APITestbase {

  @Test
  public void testProtectionMarker() throws Exception {
    ExampleWiki lWiki = ewm.get("mediawiki-japi-test1_24");
    if (hasWikiUser(lWiki)) {
      lWiki.login();
      // FIXME add this to the example test pages
      String protectionMarker = "<!-- this page is protected against edits from Mediawiki-Japi-->";
      String pageTitle = "ProtectionMarkerTest";
      String notWantedMarker = "This text should not be here. Please contact webmaster@bitplan.com immediately!";
      lWiki.wiki.setProtectionMarker(protectionMarker);
      String summary = "TestProtectionMarker at "
          + lWiki.wiki.getIsoTimeStamp();
      lWiki.wiki.edit(pageTitle, notWantedMarker, summary);
      String pageContent = lWiki.wiki.getPageContent(pageTitle);
      assertFalse(pageContent.contains(notWantedMarker));
      assertTrue(pageContent.contains(protectionMarker));
    }
  }

}
