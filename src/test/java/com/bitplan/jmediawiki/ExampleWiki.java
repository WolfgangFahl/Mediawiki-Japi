/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 */
package com.bitplan.jmediawiki;

/**
 * example wikis to be used for test
 * @author wf
 *
 */
public class ExampleWiki extends JMediawiki {

	/**
	 * creates an ExampleWiki for the given site
	 * @param siteurl
	 */
	public ExampleWiki(String siteurl) {
		super(siteurl);
	}

	/**
	 * creates an ExampleWiki for the given site and scriptpath
	 * @param siteurl
	 * @param scriptpath
	 */
	public ExampleWiki(String siteurl, String scriptpath) {
		super(siteurl,scriptpath);
	}

}
