/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package com.bitplan.jmediawiki;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.bitplan.jmediawiki.api.Api;
import com.bitplan.jmediawiki.api.Extensiondistributor;
import com.bitplan.jmediawiki.api.General;
import com.bitplan.jmediawiki.api.Limit;
import com.bitplan.jmediawiki.api.Query;
import com.bitplan.jmediawiki.api.Snapshots;
import com.bitplan.jmediawiki.api.Statistics;

/**
 * Test for 
 * http://www.mediawiki.org/wiki/API:Meta
 */
public class TestAPI_Meta extends TestAPI {

	/**
	 * http://www.mediawiki.org/wiki/API:Meta#siteinfo_.2F_si
	 * @throws Exception
	 */
	@Test
	public void testGeneralSiteInfo() throws Exception {
	  // debug=true;
		Api api=getQueryResult("&meta=siteinfo");
	  assertNotNull(api);
	  General general = api.getQuery().getGeneral();
	  assertNotNull(general);
	  assertEquals("//upload.wikimedia.org/wikipedia/mediawiki/b/bc/Wiki.png",general.getLogo());
	  check("language",general.getLang());
	  check("articlepath",general.getArticlepath());
	  check("base",general.getBase());
	  check("case",general.getCase());
	  check("dbtype",general.getDbtype());
	  check("dbversion",general.getDbversion());
	  check("fallback",general.getFallback());
	  check("favicon",general.getFavicon());
	  check("generator",general.getGenerator());
	  check("gitbranch",general.getGitBranch());
	  check("githash",general.getGitHash());
	  check("hhvmversion",general.getHhvmversion());
	  check("imagewhitelistenabled",general.getImagewhitelistenabled());
	  check("langconversion",general.getLangconversion());
	  check("legaltitlechars",general.getLegaltitlechars());
	  check("linkprefix",general.getLinkprefix());
	  check("linkprefixcharset",general.getLinkprefixcharset());
	  check("linktrail",general.getLinktrail());
	  check("logo",general.getLogo());
	  check("mainpage",general.getMainpage());
	  check("maxuploadsize",general.getMaxuploadsize());
	  check("misermode",general.getMisermode());
	  check("phpsapi",general.getPhpsapi());
	  check("phpversion",general.getPhpversion());
	  check("script",general.getScript());
	  check("scriptpath",general.getScriptpath());
	  check("server",general.getServer());
	  check("servername",general.getServername());
	  check("sitename",general.getSitename());
	  List<Limit> imageLimits = general.getImagelimits().getLimit();
	  assertNotNull(imageLimits);
	  for (Limit imageLimit:imageLimits) {
	  	check("\twidth",imageLimit.getWidth());
	  	check("\theight",imageLimit.getHeight());
	  }
	  Extensiondistributor extdist = general.getExtensiondistributor();
	  assertNotNull(extdist);
	  check("ext-list",extdist.getList());
	  Snapshots snapshots = extdist.getSnapshots();
	  assertNotNull(snapshots);
	  for (String snapshot:snapshots.getSnapshot()) {
	  	check("snapshot",snapshot.trim());
	  }
	}

	@Test
	/**
	 * test statistics
	 * @throws Exception
	 */
	public void testStatistics() throws Exception {
		Api api=getQueryResult("&meta=siteinfo&siprop=statistics");
	  assertNotNull(api);
		Query query = api.getQuery();
		assertNotNull(query);
		Statistics statistics = query.getStatistics();
		assertNotNull(statistics);
		assertTrue(statistics.getPages().intValue()>20000);
	}
}
