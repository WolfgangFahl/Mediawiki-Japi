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
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Query;

/**
 * test the API for Semantic Mediawiki 
 */
public class TestAPI_SMW extends APITestbase {
  
  @Test
  public void testBrowseBySubject() throws Exception {
    // semantic-mediawiki.org has a certificate that
    // is not in the chain understood by java
    SSLWiki.ignoreCertificates=true;
    SSLWiki wiki = new SSLWiki("https://www.semantic-mediawiki.org","/w");
    String params="&subject=Demo:Amsterdam";
    Api api=wiki.getActionResult("browsebysubject", params,null,null,"json");
    assertNotNull("the API result should not be null",api);
    Query query = api.getQuery();
    assertNotNull(query);
    /*assertNotNull(query.subject);
    assertNotNull(query.serializer);
    assertNotNull(query.version);
    assertNotNull(query.data);
    assertEquals("Amsterdam#202#",query.subject);
    assertEquals("SMW\\Serializers\\SemanticDataSerializer",query.serializer);
    assertEquals("0.1",query.version);
    List<Property> data = query.data;
    assertEquals(18,data.size());
    for (Property prop:data) {
      assertNotNull(prop.dataitem);
      for (DataItem item:prop.dataitem) {
        assertNotNull(item.type);
        assertNotNull(item.item);
        // debug=true;
        if (debug)
          System.out.println(prop.property+" "+item.type+"="+item.item);
      }
    }
    */
  }
}