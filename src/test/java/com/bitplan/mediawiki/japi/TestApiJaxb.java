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

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Query;
import com.bitplan.mediawiki.japi.api.Statistics;

/**
 * test the API JAXB wrapping
 * @author wf
 *
 */
public class TestApiJaxb {

	@Test
	/**
	 * test Jaxb Api wrapping
	 * @throws Exception
	 */
	public void testApiFromXml() throws Exception {
		String xml="<?xml version=\"1.0\"?>"+
	    "<api>\n"+
		  "  <query>\n"+
	    "    <statistics pages=\"18973\" articles=\"9634\" views=\"33431\" edits=\"44019\" images=\"202\" users=\"10\" activeusers=\"4\" admins=\"6\" jobs=\"0\" />\n"+
		  "  </query>\n"+
	    "</api>\n";
		Api api=Api.fromXML(xml);
		assertNotNull(api);
		Query query = api.getQuery();
		assertNotNull(query);
		Statistics statistics = query.getStatistics();
		assertNotNull(statistics);
		assertEquals(18973L,statistics.getPages().intValue());
		assertEquals(9634,statistics.getArticles().intValue());
		// assertEquals(33431,statistics.getViews());
		assertEquals(44019,statistics.getEdits().intValue());
		assertEquals(202,statistics.getImages().intValue());
		assertEquals(10,statistics.getUsers().intValue());
		assertEquals(4,statistics.getActiveusers().intValue());
		assertEquals(6,statistics.getAdmins().intValue());
		assertEquals(0,statistics.getJobs().intValue());
	}
	
}
