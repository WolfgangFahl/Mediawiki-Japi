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
