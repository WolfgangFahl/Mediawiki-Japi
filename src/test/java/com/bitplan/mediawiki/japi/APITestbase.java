/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2019 BITPlan GmbH https://github.com/BITPlan
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

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import com.bitplan.mediawiki.guice.ComBITPlanWikiModule;
import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.user.WikiUser;

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

	protected ExampleWiki wiki;
	private Collection<ExampleWiki> wikis;

	/**
	 * Logging may be enabled by setting debug to true
	 */
	protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger("com.bitplan.mediawiki.japi");
	protected ExampleWikiManager ewm;
	
	/**
	 * construct a Test
	 * @throws Exception 
	 */
	public APITestbase()  {
		try {
		  ComBITPlanWikiModule module = new ComBITPlanWikiModule();
		  // AbstractModule module=new OrgWikiModule();
		  ewm=new ExampleWikiManager(module);
      setWiki(ewm.get(ewm.MAIN_TESTWIKI_ID));
      setWikis(ewm.getExampleWikis().values());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
	}

	/**
	 * get a query Result
	 * @param pWiki - the example wiki to ge the result for
	 * @param query - the query to use
	 * @return the query result
	 * @throws Exception
	 */
	public Api getQueryResult(ExampleWiki pWiki,String query) throws Exception {
		pWiki.wiki.setDebug(debug);
	  Api api =pWiki.getMediaWikiJapi().getQueryResult(query);
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
	 * get the editable wikis
	 * @return
	 */
	 public Collection<ExampleWiki> getEditableWikis() {
	   Collection<ExampleWiki> result=new ArrayList<ExampleWiki>();
	    for (ExampleWiki wiki:wikis) {
	      if (!wiki.wikiId.contains("org")) {
	        result.add(wiki);
	      }
	    }
	    return result;
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
	
	/**
   * check that a given url exists
   * @param url
   * @return true if the access is successful
   * @throws Exception
   */
  public boolean urlExists(String url) throws Exception {
    URL u = new URL ( url);
    HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
    huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD"); 
    huc.connect () ; 
    int code = huc.getResponseCode() ;
    return code==200;
  }
  
  public static boolean showHelp=true;
  /**
   * check whether we have the wiki user configured to run the tests
   * @param exampleWiki
   * @return
   */
  public boolean hasWikiUser(ExampleWiki exampleWiki) {
    String user = System.getProperty("user.name");
    if (user.equals("travis")) {
      return false;
    }
    File propFile = WikiUser.getPropertyFile(exampleWiki.wikiId);
    boolean result=propFile.exists();
    if (!result && showHelp) {
      String help=WikiUser.help(exampleWiki.wikiId, exampleWiki.wikiId);
      System.err.println(help);
      showHelp=false;
    }
    return  result;
  }
}
