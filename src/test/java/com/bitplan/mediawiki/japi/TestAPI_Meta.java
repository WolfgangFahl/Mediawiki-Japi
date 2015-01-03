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

import java.util.List;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Extensiondistributor;
import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Limit;
import com.bitplan.mediawiki.japi.api.Query;
import com.bitplan.mediawiki.japi.api.Snapshots;
import com.bitplan.mediawiki.japi.api.Statistics;

/**
 * Test for http://www.mediawiki.org/wiki/API:Meta
 */
public class TestAPI_Meta extends TestAPI {

	/**
	 * http://www.mediawiki.org/wiki/API:Meta#siteinfo_.2F_si
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGeneralSiteInfo() throws Exception {
		for (ExampleWiki lwiki : wikis) {
			General general = lwiki.getSiteInfo();
			assertNotNull(general);
			boolean mayBeNull = true;
			check("generator", general.getGenerator());
			check("logo", general.getLogo(), mayBeNull);
			if (general.getGenerator().compareToIgnoreCase("Mediawiki 1.20") >= 0) {
				assertEquals(lwiki.getLogo(), general.getLogo());
				check("favicon", general.getFavicon());
				check("githash", general.getGitHash());
				check("langconversion", general.getLangconversion());
				check("linkprefix", general.getLinkprefix());
				check("linkprefixcharset", general.getLinkprefixcharset());
				check("linktrail", general.getLinktrail());
				check("maxuploadsize", general.getMaxuploadsize());
				List<Limit> imageLimits = general.getImagelimits().getLimit();
				assertNotNull(imageLimits);
				for (Limit imageLimit : imageLimits) {
					check("\twidth", imageLimit.getWidth());
					check("\theight", imageLimit.getHeight());
				}
				Extensiondistributor extdist = general.getExtensiondistributor();
				if (extdist != null) {
					check("ext-list", extdist.getList());
					Snapshots snapshots = extdist.getSnapshots();
					assertNotNull(snapshots);
					for (String snapshot : snapshots.getSnapshot()) {
						check("snapshot", snapshot.trim());
					}
				}

			}
			check("language", general.getLang());
			check("articlepath", general.getArticlepath());
			check("base", general.getBase());
			check("case", general.getCase());
			check("dbtype", general.getDbtype());
			check("dbversion", general.getDbversion());
			check("fallback", general.getFallback());
			check("gitbranch", general.getGitBranch(), mayBeNull);
			check("hhvmversion", general.getHhvmversion(), mayBeNull);
			check("imagewhitelistenabled", general.getImagewhitelistenabled(),
					mayBeNull);
			check("legaltitlechars", general.getLegaltitlechars(), mayBeNull);
			check("mainpage", general.getMainpage());
			check("misermode", general.getMisermode(), mayBeNull);
			check("phpsapi", general.getPhpsapi());
			check("phpversion", general.getPhpversion());
			check("script", general.getScript());
			check("scriptpath", general.getScriptpath());
			check("server", general.getServer());
			check("servername", general.getServername(), mayBeNull);
			check("sitename", general.getSitename());
		}
	}

	@Test
	/**
	 * test statistics
	 * @throws Exception
	 */
	public void testStatistics() throws Exception {
		for (ExampleWiki lwiki : wikis) {
			Api api = getQueryResult(lwiki, "&meta=siteinfo&siprop=statistics");
			assertNotNull(api);
			Query query = api.getQuery();
			assertNotNull(query);
			Statistics statistics = query.getStatistics();
			assertNotNull(statistics);
			assertTrue(statistics.getPages().intValue() >= lwiki.getExpectedPages());
		}
	}
}
