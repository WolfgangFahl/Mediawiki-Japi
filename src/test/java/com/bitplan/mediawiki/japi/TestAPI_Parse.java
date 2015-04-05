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

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.S;

/**
 * test https://www.mediawiki.org/wiki/API:Parse
 * 
 * @author wf
 *
 */
public class TestAPI_Parse extends APITestbase {

  @Test
	public void testGetSectionList() throws Exception {
    // http://stackoverflow.com/questions/16840447/retrieve-the-content-of-a-section-via-mediawiki-api
    Mediawiki wiki=new Mediawiki("http://en.wikipedia.org","/w");
    // wiki.debug=true;
    String action="parse";
    String params="&prop=sections&page=License";
    Api api = wiki.getActionResult(action, params);
    List<S> sections = api.getParse().getSections();
    assertNotNull(sections);
    assertTrue(sections.size()>5);
	}
	
}
