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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.junit.Ignore;
import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Warnings;

/**
 * test http://www.mediawiki.org/wiki/API:Account_creation
 * 
 * @author wf
 *
 */
public class TestCreateAccount extends APITestbase {

  /**
   * test creating an account with Mediawiki 27 and up style
   * @throws Exception
   */
  @Test
  public void testCreateAccountMW27Up() throws Exception {
    ExampleWiki lwiki = ewm.get("mw31test");
    Mediawiki wiki = (Mediawiki) lwiki.wiki;
    if (hasWikiUser(lwiki)) {
      lwiki.login();
      // uncomment to debug
      debug=true;
      lwiki.wiki.setDebug(debug);
      Mediawiki lmwiki = (com.bitplan.mediawiki.japi.Mediawiki)lwiki.wiki;
      Api api = lmwiki.getAuthManagerInfo();
      assertNotNull(api);
      String json = api.getRawJson();
      if (debug)
        LOGGER.log(Level.INFO,json);
    }
  }
  
  /**
   * test getting the create Account token
   * see https://www.mediawiki.org/wiki/API:Account_creation
   * 
   * @throws Exception
   */
  @Ignore
  public void testCreateAccount() throws Exception {
    ExampleWiki lwiki = ewm.get(ExampleWiki.DEFAULT_WIKI_ID);
    Mediawiki wiki = (Mediawiki) lwiki.wiki;
    if (hasWikiUser(lwiki)) {
      lwiki.login();
      // uncomment to debug
      lwiki.wiki.setDebug(true);
      DateFormat df = new SimpleDateFormat("yyyyMMddHHmmSS");
      String nowAsISO = df.format(new Date());
      Api api = wiki.createAccount("JohnDoe" + nowAsISO, "wf@bitplan.com",
          "John%20Doe", true, "SMWCon2015-05", "en");
      Warnings warnings = api.getWarnings();
      if (warnings!=null)
        System.err.println(warnings);
      assertNull(warnings);
      String result = api.getCreateaccount().getResult();
      assertEquals("Success", result);
    }
  }

}
