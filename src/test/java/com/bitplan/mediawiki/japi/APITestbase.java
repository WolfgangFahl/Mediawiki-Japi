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

import java.util.Collection;
import java.util.logging.Level;

import com.bitplan.mediawiki.japi.api.Api;

import static org.junit.Assert.*;

/**
 * Base class for API tests
 * 
 * @author wf
 *
 */
public class APITestbase {

	/**
	 * set to true for debugging
	 */
	protected boolean debug = false;

	private ExampleWiki wiki;
	private Collection<ExampleWiki> wikis;

	/**
	 * Logging may be enabled by setting debug to true
	 */
	protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger("com.bitplan.mediawiki.japi");

	/**
	 * construct a Test
	 * @throws Exception 
	 */
	public APITestbase()  {
		try {
      setWiki(ExampleWiki.get(ExampleWiki.MAIN_TESTWIKI_ID));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
		setWikis(ExampleWiki.getExampleWikis().values());
	}

	/**
	 * get a query Result
	 * @param pWiki - the example wiki to ge the result for
	 * @param query - the query to use
	 * @return the query result
	 * @throws Exception
	 */
	public Api getQueryResult(ExampleWiki pWiki,String query) throws Exception {
		pWiki.setDebug(debug);
		Api api = pWiki.getQueryResult(query);
		return api;
	}
	
	/**
	 * get a query Result for the default exampleWiki
	 * 
	 * @param query - the query to use
	 * @return the query result
	 * @throws Exception
	 */
	public Api getQueryResult(String query) throws Exception {
		Api api = getQueryResult(getWiki(),query);
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
			LOGGER.log(Level.INFO, name + "='" + value + "'");
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

	/**
	 * @return the wikis
	 */
	public Collection<ExampleWiki> getWikis() {
		return wikis;
	}

	/**
	 * @param wikis the wikis to set
	 */
	public void setWikis(Collection<ExampleWiki> wikis) {
		this.wikis = wikis;
	}

	/**
	 * @return the wiki
	 */
	public ExampleWiki getWiki() {
		return wiki;
	}

	/**
	 * @param wiki the wiki to set
	 */
	public void setWiki(ExampleWiki wiki) {
		this.wiki = wiki;
	}
	
}
