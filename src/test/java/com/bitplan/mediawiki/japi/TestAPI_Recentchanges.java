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
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Rc;

/**
 * http://www.mediawiki.org/wiki/API:Recentchanges
 * 
 * @author wf
 *
 */
public class TestAPI_Recentchanges extends APITestbase {
  /**
   * test http://www.mediawiki.org/wiki/API:Recentchanges
   * 
   * @throws Exception
   */
  @Test
  public void testGetRecentChanges() throws Exception {
    Api api = getQueryResult(
        "&list=recentchanges&rcprop=title%7Cids%7Csizes%7Cflags%7Cuser&rclimit=3");
    List<Rc> rcList = api.getQuery().getRecentchanges();
    assertEquals(3, rcList.size());
    for (Rc rc : rcList) {
      assertNotNull(rc.getTitle());
    }
  }

  /**
   * show a list of recent changes
   * 
   * @param rcList
   */
  public void showRCList(List<Rc> rcList) {
    LOGGER.log(Level.INFO, "found " + rcList.size() + " recent changes");
    for (Rc rc : rcList) {
      LOGGER.log(Level.INFO,
          rc.getTitle() + "(" + rc.getPageid() + "/" + rc.getTimestamp() + ")");
    }
  }

  /**
   * get recent changes specifying the number of days
   * 
   * @throws Exception
   */
  @Test
  public void testGetRecentChangesWithDays() throws Exception {
    // debug=true;
    Mediawiki lwiki = super.getWiki().getMediaWikiJapi();
    if (debug)
      lwiki.setDebug(debug);
    Date today = new Date();
    Calendar cal = new GregorianCalendar();
    cal.setTime(today);
    cal.add(Calendar.DAY_OF_MONTH, -30);
    Date date30daysbefore = cal.getTime();
    String rcstart = lwiki.dateToMWTimeStamp(today);
    String rcend = lwiki.dateToMWTimeStamp(date30daysbefore);
    int rclimit = 4;
    List<Rc> rcList = lwiki.getRecentChanges(rcstart, rcend, rclimit);
    assertNotNull(rcList);

    if (debug) {
      showRCList(rcList);

    }
    assertTrue(rcList.size() > 0);
  }

  @Test
  public void testGetMostRecentChanges() throws Exception {
    // debug=true;
    ExampleWiki lewiki = ewm.get("mediawiki-japi-test1_27");
    if (hasWikiUser(lewiki)) {
      lewiki.login();
      Mediawiki lwiki = lewiki.getMediaWikiJapi();
      String pageTitle = "Examplepage for recent change";
      String summary = "changed for testGetMostRecentChanges at "
          + lwiki.getIsoTimeStamp();
      String change1 = "Change it to change1";
      lwiki.edit(pageTitle, change1, summary);
      String change2 = "Change it to change2";
      lwiki.edit(pageTitle, change2, summary);
      if (debug)
        lwiki.setDebug(debug);
      int rcLimit = 5;
      int days = 30;
      List<Rc> rcList = lwiki.getMostRecentChanges(days, rcLimit);
      assertNotNull(rcList);

      if (debug) {
        showRCList(rcList);
      }
    }
  }

  @Test
  public void sortByTitleAndFilterDoubles() throws Exception {
    List<Rc> rcList = new ArrayList<Rc>();
    String[] titles = { "Title1", "Title2", "Title2", "Title1", "Title3" };
    String[] ts = { "2015-01-01", "2015-01-01", "2016-01-02", "2016-01-01",
        "2015-07-01" };
    SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy-MM-dd");
    int index = 0;
    for (String title : titles) {
      Rc testRc = new Rc();
      testRc.setTitle(title);
      Date tsDate = tsFormat.parse(ts[index++]);
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(tsDate);
      XMLGregorianCalendar xdate = DatatypeFactory.newInstance()
          .newXMLGregorianCalendar(cal);
      testRc.setTimestamp(xdate);
      rcList.add(testRc);
    }
    ExampleWiki lewiki = ewm.get("mediawiki-japi-test1_24");
    Mediawiki lwiki = lewiki.getMediaWikiJapi();
    // debug=true;
    List<Rc> sortedFilteredList = lwiki.sortByTitleAndFilterDoubles(rcList);
    if (debug) {
      showRCList(rcList);
      showRCList(sortedFilteredList);
    }

  }
}
