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

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.junit.Test;

import com.bitplan.mediawiki.japi.user.Crypt;

/**
 * test the Crypt class
 * @author wf
 *
 */
public class TestCrypt {

  @Test
  public void testCrypt() throws Exception {
    String expected="01234567890unsecure";
    String cypher="koyYMmY93wJS_aqpp_PmyxZJKPH5FhSG";
    String secret="juMmHMtvrfDADGkRlnJRCMYkd4kUzRE3";
    String salt="aPntWu5u";
    Crypt crypt=new Crypt(cypher,salt);
    String secret1=crypt.encrypt(expected);
    String secret2=crypt.encrypt2(expected);
    assertEquals(secret1,secret);
    assertEquals(secret2,secret);
    String decoded=crypt.decrypt(secret);
    assertEquals(expected,decoded);
   }

}
