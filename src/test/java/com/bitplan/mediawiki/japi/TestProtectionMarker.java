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
 * check that the protection marker concept works
 * @author wf
 *
 */
public class TestProtectionMarker  extends APITestbase{

  @Test
  public void testProtectionMarker() throws Exception {
    ExampleWiki lWiki=ewm.get("mediawiki-japi-test1_24");
    lWiki.login();
    // FIXME add this to the example test pages
    String protectionMarker="<!-- this page is protected against edits from Mediawiki-Japi-->";
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
