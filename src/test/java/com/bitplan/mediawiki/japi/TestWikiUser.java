package com.bitplan.mediawiki.japi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bitplan.mediawiki.japi.user.WikiUser;

/**
 * test WikiUser handling
 * @author wf
 *
 */
public class TestWikiUser {

  @Test
  public void testWikiUser() {
    String args[]= {"-a","-t"};
    WikiUser.testMode=true;
    int returnCode=WikiUser.main(args);
    assertEquals(0,returnCode);
  }

}
