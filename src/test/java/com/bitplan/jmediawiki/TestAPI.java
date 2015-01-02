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

import java.util.Collection;
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
	protected Collection<ExampleWiki> wikis;
	// the wiki to use for single tests / write access
	private static final String MAIN_TESTWIKI_ID = "mediawiki_org"; // "mediawiki_test2"; //

	/**
	 * Logging may be enabled by setting debug to true
	 */
	protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger("com.bitplan.jmediawiki");

	/**
	 * construct a Test
	 */
	public TestAPI() {
		wiki=ExampleWiki.get(MAIN_TESTWIKI_ID);
		wikis=ExampleWiki.exampleWikis.values();
	}

	/**
	 * get a query Result
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Api getQueryResult(ExampleWiki pWiki,String query) throws Exception {
		pWiki.setDebug(debug);
		Api api = pWiki.getQueryResult(query);
		return api;
	}
	
	/**
	 * get a query Result
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Api getQueryResult(String query) throws Exception {
		Api api = getQueryResult(wiki,query);
		return api;
	}

	/**
	 * check the given value to exist
	 * 
	 * @param name
	 * @param value
	 */
	protected void check(String name, Object value, boolean mayBeNull) {
		if (!mayBeNull)
			assertNotNull(name+" should not be null",value);
		if (debug) {
			LOGGER.log(Level.INFO, name + "='" + value.toString() + "'");
		}
	}
	
	/**
	 * check the given name and value
	 * @param name
	 * @param value
	 */
	protected void check(String name, Object value) {
		check(name,value,false);
	}
	
}
