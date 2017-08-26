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
import static org.junit.Assert.assertNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;

/**
 * test http://www.mediawiki.org/wiki/API:Account_creation
 * 
 * @author wf
 *
 */
public class TestCreateAccount extends APITestbase {

  /**
   * test getting the create Account token
   * 
   * @throws Exception
   */
  @Test
  public void testCreateAccount() throws Exception {
    ExampleWiki lwiki = ewm.get("mediawiki-japi-test1_24");
    Mediawiki wiki = (Mediawiki) lwiki.wiki;
    if (hasWikiUser(lwiki)) {
      lwiki.login();
      // lwiki.wiki.setDebug(true);
      DateFormat df = new SimpleDateFormat("yyyyMMddHHmmSS");
      String nowAsISO = df.format(new Date());
      Api api = wiki.createAccount("JohnDoe" + nowAsISO, "wf@bitplan.com",
          "John%20Doe", true, "SMWCon2015-05", "en");
      assertNull(api.getWarnings());
      String result = api.getCreateaccount().getResult();
      assertEquals("Success", result);
    }
  }

}
