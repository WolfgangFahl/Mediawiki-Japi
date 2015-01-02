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

import java.util.logging.Level;

import com.bitplan.jmediawiki.api.Api;

import static org.junit.Assert.*;

/**
 * Base class for API tests
 * 
 * @author wf
 *
 */
public class TestAPI {

	/**
	 * set to true for debugging
	 */
	protected boolean debug = false;

	protected ExampleWiki wiki;

	/**
	 * Logging may be enabled by setting debug to true
	 */
	protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger("com.bitplan.jmediawiki");

	/**
	 * construct a Test
	 */
	public TestAPI() {
		wiki = new ExampleWiki("http://www.mediawiki.org");
		// wiki = new ExampleWiki("http://capri.bitplan.com","/mediawiki");
	}

	/**
	 * get a query Result
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Api getQueryResult(String query) throws Exception {
		wiki.setDebug(debug);
		Api api = wiki.getQueryResult(query);
		return api;
	}

	/**
	 * check the given value to exist
	 * 
	 * @param name
	 * @param value
	 */
	protected void check(String name, Object value) {
		assertNotNull(value);
		if (debug) {
			LOGGER.log(Level.INFO, name + "='" + value.toString() + "'");
		}
	}
}
