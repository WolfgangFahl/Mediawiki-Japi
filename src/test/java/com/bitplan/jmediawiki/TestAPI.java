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

import com.bitplan.jmediawiki.api.Api;

/**
 * Base class for API tests
 * @author wf
 *
 */
public class TestAPI {
	
	/**
	 * set to true for debugging
	 */
	protected boolean debug=false;

	protected JMediawiki wiki;
	
	/**
	 *  Logging may be enabled by setting debug to true
	 */
	protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger("com.bitplan.jmediawiki");
	
  public TestAPI() {
		wiki=new JMediawiki("http://www.mediawiki.org");
		wiki.setDebug(debug);
  }
  
	/**
	 * get a query Result
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Api getQueryResult(String query) throws Exception {
		Api api=wiki.getQueryResult(query);
		return api;
	}
}
