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
    // ExampleWiki lwiki=ewm.get("mediawiki-japi-test1_27");
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
