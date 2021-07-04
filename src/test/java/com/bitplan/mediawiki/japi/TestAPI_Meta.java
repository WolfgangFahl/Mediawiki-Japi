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

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Extensiondistributor;
import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Limit;
import com.bitplan.mediawiki.japi.api.Ns;
import com.bitplan.mediawiki.japi.api.Query;
import com.bitplan.mediawiki.japi.api.Snapshots;
import com.bitplan.mediawiki.japi.api.Statistics;

/**
 * Test for http://www.mediawiki.org/wiki/API:Meta
 */
public class TestAPI_Meta extends APITestbase {

	/**
	 * http://www.mediawiki.org/wiki/API:Meta#siteinfo_.2F_si
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGeneralSiteInfo() throws Exception {
		for (ExampleWiki lwiki : getWikis()) {
			// lwiki.wiki.setDebug(true);
			// FIXME add to interface
			General general = lwiki.getGeneral();
			assertNotNull(general);
			boolean mayBeNull = true;
			check("generator", general.getGenerator());
			check("logo", general.getLogo(), mayBeNull);
			String wikiVersion = general.getGenerator();
			debug = true;
			if (debug)
				LOGGER.log(Level.INFO, wikiVersion);
			if (wikiVersion.compareToIgnoreCase("Mediawiki 1.20") >= 0) {
				String expectedLogo = lwiki.getLogo();
				String logo = general.getLogo();
				if (debug) {
					LOGGER.log(Level.INFO, logo);
				}
				assertTrue(lwiki.wiki.getSiteurl() + "/" + lwiki.wiki.getScriptPath(),
						general.getLogo().endsWith(lwiki.getLogo()));
				check("favicon", general.getFavicon());
				check("langconversion", general.getLangconversion());
				check("linkprefix", general.getLinkprefix());
				check("linkprefixcharset", general.getLinkprefixcharset());
				check("linktrail", general.getLinktrail());
				// MediaWiki 1.27.0-wmf.20 does not have this
				// check("maxuploadsize", general.getMaxuploadsize());
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
				String generator = general.getGenerator();
				if (generator.compareToIgnoreCase("Mediawiki 1.25") >= 0) {
					String githash = general.getGitHash();
					check("githash", githash, true);
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
			check("imagewhitelistenabled", general.getImagewhitelistenabled(), mayBeNull);
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
	 * 
	 * @throws Exception
	 */
	public void testStatistics() throws Exception {
		debug=true;
		for (ExampleWiki lwiki : getWikis()) {
			Api api = getQueryResult(lwiki, "&meta=siteinfo&siprop=statistics");
			assertNotNull(api);
			Query query = api.getQuery();
			assertNotNull(query);
			Statistics statistics = query.getStatistics();
			assertNotNull(statistics);
			if (debug) {
				LOGGER.log(Level.INFO,String.format("%s: %3d pages",lwiki.getWikiId(),statistics.getPages()));
			}
			int expectedPages=lwiki.getExpectedPages();
			assertTrue(lwiki.wiki.getSiteurl() + lwiki.wiki.getScriptPath() + " " + statistics.getPages() + ">"
					+ lwiki.getExpectedPages(), statistics.getPages().intValue() >= expectedPages);
		}
	}

	/**
	 * test namespace retrieving http://www.mediawiki.org/wiki/Help:Namespaces
	 * 
	 * @throws Exception
	 */
	@Test
	public void testNameSpaces() throws Exception {
		for (ExampleWiki lwiki : getWikis()) {
			Mediawiki mw = (Mediawiki) lwiki.wiki;
			Map<String, Ns> namespaces = mw.getSiteInfo().getNamespaces();
			assertNotNull(namespaces);
			assertTrue(namespaces.size() > 12);
			// debug=true;
			if (debug) {
				for (Ns namespace : namespaces.values()) {
					LOGGER.log(Level.INFO,
							namespace.getId() + ":" + namespace.getValue() + "->" + namespace.getCanonical());
				}
			}
		}
	}

	/**
	 * set up the name space for the given lang and list of namespacenames
	 * 
	 * @param lang
	 * @param nameSpaceApiXml
	 * @return - the wiki created
	 * @throws Exception
	 */
	public Mediawiki setUpNameSpace(String nameSpaceApiXml) throws Exception {
		Mediawiki result = new Mediawiki();
		Api api = Api.fromXML(nameSpaceApiXml);
		Query query = api.getQuery();
		result.setUpSiteInfo(query);
		return result;
	}

	/**
	 * test the mapping of namespaces from one wiki to another
	 * 
	 * @throws Exception
	 */
	@Test
	public void testNameSpaceMapping() throws Exception {
		// http://fr.wikipedia.org/w/api.php?action=query&meta=siteinfo&siprop=namespaces&format=xml
		String nameSpaceApiXmlFr = "<?xml version=\"1.0\"?><api><query><general lang='fr'></general><namespaces><ns _idx=\"-2\" id=\"-2\" case=\"first-letter\" canonical=\"Media\" xml:space=\"preserve\">Média</ns><ns _idx=\"-1\" id=\"-1\" case=\"first-letter\" canonical=\"Special\" xml:space=\"preserve\">Spécial</ns><ns _idx=\"0\" id=\"0\" case=\"first-letter\" content=\"\" xml:space=\"preserve\" /><ns _idx=\"1\" id=\"1\" case=\"first-letter\" subpages=\"\" canonical=\"Talk\" xml:space=\"preserve\">Discussion</ns><ns _idx=\"2\" id=\"2\" case=\"first-letter\" subpages=\"\" canonical=\"User\" xml:space=\"preserve\">Utilisateur</ns><ns _idx=\"3\" id=\"3\" case=\"first-letter\" subpages=\"\" canonical=\"User talk\" xml:space=\"preserve\">Discussion utilisateur</ns><ns _idx=\"4\" id=\"4\" case=\"first-letter\" subpages=\"\" canonical=\"Project\" xml:space=\"preserve\">Wikipédia</ns><ns _idx=\"5\" id=\"5\" case=\"first-letter\" subpages=\"\" canonical=\"Project talk\" xml:space=\"preserve\">Discussion Wikipédia</ns><ns _idx=\"6\" id=\"6\" case=\"first-letter\" canonical=\"File\" xml:space=\"preserve\">Fichier</ns><ns _idx=\"7\" id=\"7\" case=\"first-letter\" subpages=\"\" canonical=\"File talk\" xml:space=\"preserve\">Discussion fichier</ns><ns _idx=\"8\" id=\"8\" case=\"first-letter\" canonical=\"MediaWiki\" xml:space=\"preserve\">MediaWiki</ns><ns _idx=\"9\" id=\"9\" case=\"first-letter\" subpages=\"\" canonical=\"MediaWiki talk\" xml:space=\"preserve\">Discussion MediaWiki</ns><ns _idx=\"10\" id=\"10\" case=\"first-letter\" subpages=\"\" canonical=\"Template\" xml:space=\"preserve\">Modèle</ns><ns _idx=\"11\" id=\"11\" case=\"first-letter\" subpages=\"\" canonical=\"Template talk\" xml:space=\"preserve\">Discussion modèle</ns><ns _idx=\"12\" id=\"12\" case=\"first-letter\" subpages=\"\" canonical=\"Help\" xml:space=\"preserve\">Aide</ns><ns _idx=\"13\" id=\"13\" case=\"first-letter\" subpages=\"\" canonical=\"Help talk\" xml:space=\"preserve\">Discussion aide</ns><ns _idx=\"14\" id=\"14\" case=\"first-letter\" subpages=\"\" canonical=\"Category\" xml:space=\"preserve\">Catégorie</ns><ns _idx=\"15\" id=\"15\" case=\"first-letter\" subpages=\"\" canonical=\"Category talk\" xml:space=\"preserve\">Discussion catégorie</ns><ns _idx=\"100\" id=\"100\" case=\"first-letter\" subpages=\"\" canonical=\"Portail\" xml:space=\"preserve\">Portail</ns><ns _idx=\"101\" id=\"101\" case=\"first-letter\" subpages=\"\" canonical=\"Discussion Portail\" xml:space=\"preserve\">Discussion Portail</ns><ns _idx=\"102\" id=\"102\" case=\"first-letter\" subpages=\"\" canonical=\"Projet\" xml:space=\"preserve\">Projet</ns><ns _idx=\"103\" id=\"103\" case=\"first-letter\" subpages=\"\" canonical=\"Discussion Projet\" xml:space=\"preserve\">Discussion Projet</ns><ns _idx=\"104\" id=\"104\" case=\"first-letter\" subpages=\"\" canonical=\"Référence\" xml:space=\"preserve\">Référence</ns><ns _idx=\"105\" id=\"105\" case=\"first-letter\" subpages=\"\" canonical=\"Discussion Référence\" xml:space=\"preserve\">Discussion Référence</ns><ns _idx=\"828\" id=\"828\" case=\"first-letter\" subpages=\"\" canonical=\"Module\" xml:space=\"preserve\">Module</ns><ns _idx=\"829\" id=\"829\" case=\"first-letter\" subpages=\"\" canonical=\"Module talk\" xml:space=\"preserve\">Discussion module</ns><ns _idx=\"2600\" id=\"2600\" case=\"first-letter\" canonical=\"Topic\" defaultcontentmodel=\"flow-board\" xml:space=\"preserve\">Sujet</ns></namespaces></query></api>";
		// http://de.wikipedia.org/w/api.php?action=query&meta=siteinfo&siprop=namespaces&format=xml
		String nameSpaceApiXmlDe = "<?xml version=\"1.0\"?><api><query><general lang='de'></general><namespaces><ns _idx=\"-2\" id=\"-2\" case=\"first-letter\" canonical=\"Media\" xml:space=\"preserve\">Medium</ns><ns _idx=\"-1\" id=\"-1\" case=\"first-letter\" canonical=\"Special\" xml:space=\"preserve\">Spezial</ns><ns _idx=\"0\" id=\"0\" case=\"first-letter\" content=\"\" xml:space=\"preserve\" /><ns _idx=\"1\" id=\"1\" case=\"first-letter\" subpages=\"\" canonical=\"Talk\" xml:space=\"preserve\">Diskussion</ns><ns _idx=\"2\" id=\"2\" case=\"first-letter\" subpages=\"\" canonical=\"User\" xml:space=\"preserve\">Benutzer</ns><ns _idx=\"3\" id=\"3\" case=\"first-letter\" subpages=\"\" canonical=\"User talk\" xml:space=\"preserve\">Benutzer Diskussion</ns><ns _idx=\"4\" id=\"4\" case=\"first-letter\" subpages=\"\" canonical=\"Project\" xml:space=\"preserve\">Wikipedia</ns><ns _idx=\"5\" id=\"5\" case=\"first-letter\" subpages=\"\" canonical=\"Project talk\" xml:space=\"preserve\">Wikipedia Diskussion</ns><ns _idx=\"6\" id=\"6\" case=\"first-letter\" canonical=\"File\" xml:space=\"preserve\">Datei</ns><ns _idx=\"7\" id=\"7\" case=\"first-letter\" subpages=\"\" canonical=\"File talk\" xml:space=\"preserve\">Datei Diskussion</ns><ns _idx=\"8\" id=\"8\" case=\"first-letter\" canonical=\"MediaWiki\" xml:space=\"preserve\">MediaWiki</ns><ns _idx=\"9\" id=\"9\" case=\"first-letter\" subpages=\"\" canonical=\"MediaWiki talk\" xml:space=\"preserve\">MediaWiki Diskussion</ns><ns _idx=\"10\" id=\"10\" case=\"first-letter\" canonical=\"Template\" xml:space=\"preserve\">Vorlage</ns><ns _idx=\"11\" id=\"11\" case=\"first-letter\" subpages=\"\" canonical=\"Template talk\" xml:space=\"preserve\">Vorlage Diskussion</ns><ns _idx=\"12\" id=\"12\" case=\"first-letter\" subpages=\"\" canonical=\"Help\" xml:space=\"preserve\">Hilfe</ns><ns _idx=\"13\" id=\"13\" case=\"first-letter\" subpages=\"\" canonical=\"Help talk\" xml:space=\"preserve\">Hilfe Diskussion</ns><ns _idx=\"14\" id=\"14\" case=\"first-letter\" subpages=\"\" canonical=\"Category\" xml:space=\"preserve\">Kategorie</ns><ns _idx=\"15\" id=\"15\" case=\"first-letter\" subpages=\"\" canonical=\"Category talk\" xml:space=\"preserve\">Kategorie Diskussion</ns><ns _idx=\"100\" id=\"100\" case=\"first-letter\" subpages=\"\" canonical=\"Portal\" xml:space=\"preserve\">Portal</ns><ns _idx=\"101\" id=\"101\" case=\"first-letter\" subpages=\"\" canonical=\"Portal Diskussion\" xml:space=\"preserve\">Portal Diskussion</ns><ns _idx=\"828\" id=\"828\" case=\"first-letter\" subpages=\"\" canonical=\"Module\" xml:space=\"preserve\">Modul</ns><ns _idx=\"829\" id=\"829\" case=\"first-letter\" subpages=\"\" canonical=\"Module talk\" xml:space=\"preserve\">Modul Diskussion</ns></namespaces></query></api>";
		Mediawiki sourceWiki = setUpNameSpace(nameSpaceApiXmlDe);
		Mediawiki targetWiki = setUpNameSpace(nameSpaceApiXmlFr);
		String targetNameSpace = sourceWiki.getSiteInfo().mapNamespace("Vorlage", targetWiki.getSiteInfo());
		assertEquals("Modèle", targetNameSpace);
	}

	@Test
	public void testGetNameSpaceFromPageTitle() throws Exception {
		String pageTitles[] = { "Template:someTemplate", "MainPage", "Template:someTemplate:with weird colons:",
				"File:SomeFilewith File: again" };
		String expected[] = { "Template", null, "Template", "File", "Modèle:French template" };
		int index = 0;
		for (String pageTitle : pageTitles) {
			String template = PageInfo.getNameSpaceName(pageTitle);
			assertEquals(expected[index], template);
			index++;
		}
	}

}
