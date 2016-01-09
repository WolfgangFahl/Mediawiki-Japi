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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Rc;

/**
 * http://www.mediawiki.org/wiki/API:Recentchanges
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
		Api api = getQueryResult("&list=recentchanges&rcprop=title%7Cids%7Csizes%7Cflags%7Cuser&rclimit=3");
		List<Rc> rcList = api.getQuery().getRecentchanges();
		assertEquals(3, rcList.size());
		for (Rc rc:rcList) {
			assertNotNull(rc.getTitle());
		}
	}
	
	/**
	 * get recent changes specifying the number of days
	 * @throws Exception
	 */
	@Test
	public void testGetRecentChangesWithDays() throws Exception {
		Mediawiki lwiki = super.getWiki().getMediaWikiJapi();
		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -30);
		Date date30daysbefore = cal.getTime();
		SimpleDateFormat mwTimeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String rcstart=mwTimeStampFormat.format(today);
		String rcend=mwTimeStampFormat.format(date30daysbefore);
		int rclimit=5;
		List<Rc> rcList=lwiki.getRecentChanges(rcstart,rcend,rclimit);
		assertNotNull(rcList);
		// debug=true;
		if (debug) {
			LOGGER.log(Level.INFO,"found "+rcList.size()+" recent changes");
			for (Rc rc:rcList) {
				LOGGER.log(Level.INFO,rc.getTitle());
			}
		}
		assertTrue(rcList.size()==rclimit);
	}
}
