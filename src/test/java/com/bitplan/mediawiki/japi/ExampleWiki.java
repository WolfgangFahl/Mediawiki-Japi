/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 */
package com.bitplan.mediawiki.japi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.bind.annotation.XmlRootElement;

import com.bitplan.mediawiki.japi.Mediawiki;
import com.bitplan.mediawiki.japi.api.Page;
import com.bitplan.mediawiki.japi.user.WikiUser;

/**
 * example wikis to be used for test
 * 
 * @author wf
 *
 */
@XmlRootElement(name = "examplewiki")
public class ExampleWiki extends Mediawiki {

	/**
	 * Map of example Wikis
	 */
	public static Map<String, ExampleWiki> exampleWikis = new HashMap<String, ExampleWiki>();

	/**
	 * the id of the wiki
	 */
	protected String wikiId;

	/**
	 * Map of example titles
	 */
	protected Map<String, List<ExamplePage>> examplePages = new HashMap<String, List<ExamplePage>>();

	protected int expectedPages;

	private String logo;

	private WikiUser wikiuser;

	/**
	 * @return the wikiId
	 */
	public String getWikiId() {
		return wikiId;
	}

	/**
	 * @param wikiId
	 *          the wikiId to set
	 */
	public void setWikiId(String wikiId) {
		this.wikiId = wikiId;
	}

	/**
	 * @return the expectedPages
	 */
	public int getExpectedPages() {
		return expectedPages;
	}

	protected void setExpectedPages(int count) {
		expectedPages = count;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogo() {
		return logo;
	}

	/**
	 * creates an ExampleWiki for the given wikiId,site and scriptpath and adds it
	 * to the map of example Wikis
	 * 
	 * @param wikiId
	 * @param siteurl
	 * @param scriptpath
	 */
	public ExampleWiki(String wikiId, String siteurl, String scriptpath) {
		super(siteurl, scriptpath);
		this.wikiId = wikiId;
		exampleWikis.put(wikiId, this);
	}

	/**
	 * get the WikiUser for this example Wiki
	 * 
	 * @return the wiki user
	 */
	public WikiUser getWikiUser() {
		if (wikiuser == null) {
			wikiuser = WikiUser.getUser(wikiId, this.siteurl);
			if (debug) {
				LOGGER.log(Level.INFO, "user=" + wikiuser.getUsername());
			}
		}
		return wikiuser;
	}

	/**
	 * example Page
	 * 
	 * @author wf
	 *
	 */
	public class ExamplePage extends Page {
		String contentPart;
		boolean forEdit=false;

		/**
		 * construct an example Page with the given title and the given part of the
		 * content
		 * 
		 * @param title
		 * @param contentPart
		 * @param forEdit
		 */
		public ExamplePage(String title, String contentPart,boolean forEdit) {
			this.title = title;
			this.contentPart = contentPart;
			this.forEdit=forEdit;
		}
		
		public ExamplePage(String title, String contentPart) {
			this(title,contentPart,false);
		}

		public String getContentPart() {
			return contentPart;
		}

	}

	/**
	 * return the example Page for the given testId
	 * 
	 * @param testId
	 *          - the key for looking up the example page
	 * @return - the list of example pages with the given testId - or an empty list if there was nothing found
	 */
	public List<ExamplePage> getExamplePages(String testId) {
		List<ExamplePage> result = examplePages.get(testId);
		if (result==null)
			return new ArrayList<ExamplePage>();
		return result;
	}

	/**
	 * add the given page to the example pages
	 * 
	 * @param testId
	 * @param page
	 */
	public void addExamplePage(String testId, ExamplePage page) {
		if (!examplePages.containsKey(testId)) {
			List<ExamplePage> pages = new ArrayList<ExamplePage>();
			examplePages.put(testId, pages);
		}
		List<ExamplePage> pages = examplePages.get(testId);
		pages.add(page);
	}

	/**
	 * get the given Example wiki
	 * 
	 * @param wikiId
	 * @return - the example wiki for the given wikiId
	 */
	public static ExampleWiki get(String wikiId) {
		// this code should be (partly?) replaced by csv-access
		if (exampleWikis.size() == 0) {
			// Mediawiki site
			ExampleWiki wiki = new ExampleWiki("mediawiki_org",
					"http://www.mediawiki.org", Mediawiki.DEFAULT_SCRIPTPATH);
			wiki.setExpectedPages(290000);
			wiki.setLogo("//upload.wikimedia.org/wikipedia/mediawiki/b/bc/Wiki.png");
			ExamplePage testPage1 = wiki.new ExamplePage("2011 Wikimedia fundraiser",
					"{{Wikimedia engineering project information");
			wiki.addExamplePage("testGetPages", testPage1);

			ExamplePage testPage2 = wiki.new ExamplePage("2012 Wikimedia fundraiser",
					"{{Wikimedia engineering project information");
			wiki.addExamplePage("testGetPages", testPage2);

			// test sites on mediawiki-japi.bitplan.com
			// uncommment to enable
			// /**
			String versions[] = { "1_19", "1_23" }; // "1_24" };
			for (String version : versions) {
				wiki = new ExampleWiki("mediawiki-japi-test" + version,
						"http://mediawiki-japi.bitplan.com", "/mw" + version);
				wiki.setLogo("http://mediawiki-japi.bitplan.com/mw1_23/skins/common/images/wiki.png");
				wiki.setExpectedPages(3);
				testPage1 = wiki.new ExamplePage("Testpage 1", "This is test page 1",true);
				wiki.addExamplePage("testGetPages", testPage1);
				wiki.addExamplePage("testEditPages", testPage1);
				testPage2 = wiki.new ExamplePage("Testpage 2", "This is test page 2",true);
				wiki.addExamplePage("testGetPages", testPage2);
				wiki.addExamplePage("testEditPages", testPage2);
			}
			// bitplan internal wiki
			// wiki = new
			// ExampleWiki("capri_bitplan","http://capri.bitplan.com","/mediawiki");
			// */
		}
		ExampleWiki result = exampleWikis.get(wikiId);
		return result;
	}

	/**
	 * login to the example wiki
	 * 
	 * @throws Exception
	 */
	public void login() throws Exception {
		WikiUser lwikiuser = this.getWikiUser();
		super.login(lwikiuser.getUsername(), lwikiuser.getPassword());
	}

}
