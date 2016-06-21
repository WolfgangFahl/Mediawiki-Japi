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
import com.bitplan.mediawiki.japi.api.Module;
import com.bitplan.mediawiki.japi.api.Paraminfo;
import com.bitplan.mediawiki.japi.api.Warnings;

/**
 * test Api description access
 * 
 * @author wf
 *
 */
public class TestApiDescription extends APITestbase {

  /**
   * 
   * @throws Exception
   */
  @Test
  public void testGetApiDescription() throws Exception {
    // ExampleWiki lwiki=ewm.get("mediawiki-japi-test1_24");
    // com.bitplan.mediawiki.japi.Mediawiki wiki=(Mediawiki) lwiki.wiki;
    Mediawiki wiki = new Mediawiki("https://en.wikipedia.org");
    // wiki.setDebug(true);
    // |phpfm|query+allpages|query+siteinfo
    String params = "&modules=main%7Clogin%7Clogout%7Cphpfm&helpformat=none";
    Api result = wiki.getActionResult("paraminfo", params);
    assertNotNull(result);
    Warnings warnings = result.getWarnings();
    assertNull(warnings);
    Paraminfo paraminfo = result.getParaminfo();
    assertNotNull(paraminfo);
    List<Module> modules = paraminfo.getModules();
    if (debug) {
      for (Module module : modules) {
        System.out.println(module.getName());
      }
    }
    assertEquals(4, modules.size());

  }

  // https://github.com/wiztools/xsd-gen
}
