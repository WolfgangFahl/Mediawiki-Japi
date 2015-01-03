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

import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;

/**
 * test https://www.mediawiki.org/wiki/API:Edit
 * 
 * @author wf
 *
 */
public class TestAPI_Edit extends TestAPI {

	/**
	 * test getting the edit token
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetEditToken() throws Exception {
		for (ExampleWiki lwiki : wikis) {
			lwiki.setDebug(true);
			String editversion = "";
			String action="query";
			String params="&meta=tokens";
			if (lwiki.getVersion().compareToIgnoreCase("Mediawiki 1.24") >= 0) {
				editversion = "Versions 1.24 and later";
			} else if (lwiki.getVersion().compareToIgnoreCase("Mediawiki 1.20") >= 0) {
				editversion = "Versions 1.20-1.23";
				action="tokens";
				params="";
			} else {
				editversion = "Version 1.19 and earlier";
				params="&prop=info";
			}
			if (debug) {
				LOGGER.log(Level.INFO,
						"handling edit for wiki version " + lwiki.getVersion() + " as "
								+ editversion+" with action="+action+params);
			}
			Api api = lwiki.getActionResult(action, params);
			assertNotNull(api);
		}
	}
}
