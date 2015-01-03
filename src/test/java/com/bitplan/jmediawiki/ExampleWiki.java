/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 */
package com.bitplan.jmediawiki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.bind.annotation.XmlRootElement;

import com.bitplan.jmediawiki.api.Page;
import com.bitplan.jmediawiki.user.WikiUser;

/**
 * example wikis to be used for test
 * 
 * @author wf
 *
 */
@XmlRootElement(name = "examplewiki")
public class ExampleWiki extends JMediawiki {

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
	protected Map<String, List<ExamplePage>> examplePages = new HashMap<String,List<ExamplePage>>();

	protected int expectedPages;

	private String logo;

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
		expectedPages=count;
	}
	
	public void setLogo(String logo) {
		this.logo=logo;
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
	 * @return
	 */
	public WikiUser getWikiUser() {
		WikiUser wuser = WikiUser.getUser(wikiId);
		if (debug) {
			LOGGER.log(Level.INFO, "user=" + wuser.getUsername());
		}
		return wuser;
	}

	/**
	 * example Page
	 * @author wf
	 *
	 */
	public class ExamplePage extends Page {
		String contentPart;

		/**
		 * construct an example Page with the given title and the given part of the content
		 * @param title
		 * @param contentPart
		 */
		public ExamplePage(String title,String contentPart) {
			this.title=title;
			this.contentPart=contentPart;
		}

		public String getContentPart() {
			return contentPart;
		}
		
	}
	
	/**
	 * return the example Page for the given testId
	 * @param testId
	 * @return
	 */
	public List<ExamplePage> getExamplePages(String testId) {
		List<ExamplePage> result = examplePages.get(testId);
		return result;
	}

	/**
	 * add the given page to the example pages
	 * @param testId
	 * @param page
	 */
	public void addExamplePage(String testId, ExamplePage page) {
		if (!examplePages.containsKey(testId)) {
			List<ExamplePage> pages=new ArrayList<ExamplePage>();
			examplePages.put(testId, pages);
		} 
		List<ExamplePage> pages=examplePages.get(testId);
		pages.add(page);
	}

	/**
	 * get the given Example wiki
	 * 
	 * @param wikiId
	 * @return
	 */
	public static ExampleWiki get(String wikiId) {
		if (exampleWikis.size() == 0) {
			// Mediawiki site
			ExampleWiki wiki = new ExampleWiki("mediawiki_org",
					"http://www.mediawiki.org", JMediawiki.DEFAULT_SCRIPTPATH);
			wiki.setExpectedPages(290000);
			wiki.setLogo("//upload.wikimedia.org/wikipedia/mediawiki/b/bc/Wiki.png");
			ExamplePage testPage1=wiki.new ExamplePage("2011 Wikimedia fundraiser","{{Wikimedia engineering project information");
			wiki.addExamplePage("testGetPages",testPage1);
	
			ExamplePage testPage2=wiki.new ExamplePage("2012 Wikimedia fundraiser","{{Wikimedia engineering project information");
			wiki.addExamplePage("testGetPages",testPage2);
	
			// test site on localhost
			// uncommment to enable
			// /**
			wiki = new ExampleWiki("mediawiki_test2", "http://localhost",
					"/mediawiki-test2");
			wiki.setLogo("http://localhost/mediawiki-test2/images/4/4e/BITPlanLogo2012_197x118.png");
			wiki.setExpectedPages(3);
			testPage1=wiki.new ExamplePage("Testpage 1","this is test page 1");
			wiki.addExamplePage("testGetPages",testPage1);
			testPage2=wiki.new ExamplePage("Testpage 2","This is Test page 2");
			wiki.addExamplePage("testGetPages",testPage2);
	
			// bitplan internal wiki
			// wiki = new
			// ExampleWiki("capri_bitplan","http://capri.bitplan.com","/mediawiki");
			// */
		}
		ExampleWiki result=exampleWikis.get(wikiId);
		return result;
	}

}
