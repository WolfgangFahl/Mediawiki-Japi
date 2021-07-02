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

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.bitplan.mediawiki.japi.ExampleWiki.ExamplePage;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * manager for the Examples
 * 
 * @author wf
 *
 */
public class ExampleWikiManager {
  final String SOURCE_WIKI = "mw31test";
  final String TARGET_WIKI = "mw35test";
  final String IMGSRC_WIKI = "mw31test";
  
  // FIXME this is not only for test ...
  Injector injector;
  /**
   * create me with the given module
   * @param module
   */
  public ExampleWikiManager(AbstractModule module) {
     injector= Guice.createInjector(module);
     defaultWiki=injector.getInstance(MediawikiApi.class);
  }

  // lSiteurl,Mediawiki.DEFAULT_SCRIPTPATH
  /**
   * Map of example Wikis
   */
  private Map<String, ExampleWiki> exampleWikis = new HashMap<String, ExampleWiki>();
  protected static Map<String, String> aliases = new HashMap<String, String>();

  MediawikiApi defaultWiki;

  // the wiki to use for single tests / write access
  public final String MAIN_TESTWIKI_ID = "wikipedia_org_test2"; // "mediawiki_test2";
                                                          // //

  /**
   * add the given exampleWiki
   * @param exampleWiki
   * @return the exampleWiki
   */
  public ExampleWiki add(ExampleWiki exampleWiki) {
    exampleWikis.put(exampleWiki.getWikiId(), exampleWiki);
    return exampleWiki;
  }
  
  /***
   * add an ExampleWiki with the given features
   * @param wikiId
   * @param siteUrl
   * @param defaultScriptpath
   * @return
   * @throws Exception
   */
  private ExampleWiki add(String wikiId, String siteUrl,
      String scriptPath) throws Exception {
    MediawikiApi wiki=injector.getInstance(MediawikiApi.class);
    wiki.init(siteUrl, scriptPath);
    ExampleWiki exampleWiki=new ExampleWiki(wikiId,wiki);
    add(exampleWiki);
    return exampleWiki;
  }
  
  /**
   * creates an ExampleWiki from the given csvLine
   * 
   * @param csvLine
   * @return
   * @throws Exception
   */
  public ExampleWiki getExampleWikiFromCSV(String csvLine, MediawikiApi wiki)
      throws Exception {
    ExampleWiki exampleWiki = null;
    StringTokenizer st = new StringTokenizer(csvLine, ";");
    if (st.hasMoreTokens()) {
      st.nextToken(); // what is to do with the first column?
      String lSiteurl = st.nextToken();
      String lWikiId = st.nextToken();
      wiki.setSiteurl(lSiteurl);
      exampleWiki = new ExampleWiki(lWikiId, wiki);
      st.nextToken(); // what is to do with the version?
      String pages = st.nextToken();
      pages = pages.replace(",", "");
      exampleWiki.setExpectedPages(Integer.valueOf(pages).intValue());
      exampleWiki.setLogo("logo"); // FIXME ...
    }
    return exampleWiki;
  }

  /**
   * get the CSV data
   * 
   * @param urlString
   * @throws Exception
   */
  public void readCSV(String urlString) throws Exception {
    // get csv-String
    String csv = Mediawiki.getStringFromUrl(urlString);
    String[] csvlines = csv.split("\n");
    int lineIndex = 0;
    for (String csvLine : csvlines) {
      if (lineIndex++ > 0) {
        this.add(this.getExampleWikiFromCSV(csvLine, this.defaultWiki));
      }
    }
  }

  /**
   * get the given Example wiki
   * 
   * @param wikiId
   * @return - the example wiki for the given wikiId
   * @throws Exception
   */
  public ExampleWiki get(String wikiId) throws Exception {
    // this code should be (partly?) replaced by csv-access
    if (getExampleWikis().size() == 0) {
      // String urlString = "http://mediawiki-japi.bitplan.com/mediawiki-japi/index.php/Special:Ask/-5B-5BCategory:ExampleWiki-5D-5D-20-5B-5Bsiteurl::%2B-5D-5D/-3FSiteurl/-3FWikiid/-3FMwversion/-3FMwMinExpectedPages/format%3Dcsv/sep%3D;/offset%3D0";
      // FIXME uncomment to activate
      // readCSV(urlString);
      // Mediawiki site
      ExampleWiki wiki = add("wikipedia_org_test2",
          //"http://www.mediawiki.org",
          "https://test2.wikipedia.org",
          Mediawiki.DEFAULT_SCRIPTPATH);
      wiki.setExpectedPages(30000);
      wiki.setLogo("//test2.wikipedia.org/static/images/project-logos/test2wiki.png");
      ExamplePage testPage1 = wiki.new ExamplePage("Asteroid",
          "planet");
      wiki.addExamplePage("testGetPages", testPage1);

      ExamplePage testPage2 = wiki.new ExamplePage("Life",
          "humans");
      wiki.addExamplePage("testGetPages", testPage2);

      /*
       * ExamplePage testPage1b = wiki.new ExamplePage("Testpage 1",
       * "This is test page 1",true); wiki.addExamplePage("testGetPages",
       * testPage1b); wiki.addExamplePage("testEditPages", testPage1b);
       */
      // test sites on mediawiki-japi.bitplan.com
      // uncommment to enable
      // /**
      String versions[] = { "27","31", "35", "36" };
      int basePort=9080;
      int i=0;
      for (String version : versions) {
        wiki = add("mw" + version+"test",
            "http://localhost:" + (basePort+i),"");
        i++;
        wiki.setLogo("wiki.png");
        wiki.setExpectedPages(3);
        testPage1 = wiki.new ExamplePage("Testpage 1", "This is test page 1",
            true);
        wiki.addExamplePage("testGetPages", testPage1);
        wiki.addExamplePage("testEditPages", testPage1);
        testPage2 = wiki.new ExamplePage("Testpage 2", "This is test page 2",
            true);
        wiki.addExamplePage("testGetPages", testPage2);
        wiki.addExamplePage("testEditPages", testPage2);
        ExamplePage testPage3=wiki.new ExamplePage("TestEditSection","=== section 1 ===\n=== section 2 ===\n",true);
        wiki.addExamplePage("testSectionEdit",testPage3);
      }
      // bitplan internal wiki
      // wiki = new
      // ExampleWiki("capri_bitplan","http://capri.bitplan.com","/mediawiki");
      // */
      // Please modify this code according to the wikis you used ...
      // this is for a copy test - you need read access to the SOURCE_WIKI and
      // write access
      // to the TARGET_WIKI
     
      aliases.put("sourceWiki", SOURCE_WIKI);
      aliases.put("targetWiki", TARGET_WIKI);
      aliases.put("imgsrcWiki", IMGSRC_WIKI);

      ExampleWiki sourceWiki = getExampleWikis().get(SOURCE_WIKI);
      ExamplePage testImage=wiki.new ExamplePage("File:Index.png","",false);
      sourceWiki.addExamplePage("testCopy",testImage);
      ExamplePage testPage3 = wiki.new ExamplePage("Testpage 3",
          "This is test page 3", true);
      // this page will by copied so it's only in one wiki for a start
      sourceWiki.addExamplePage("testEditPages", testPage3);
      sourceWiki.addExamplePage("testCopy", testPage3);
      ExamplePage testTemplate=wiki.new ExamplePage("Template:TestTemplate","<noinclude>\n" + 
          "This is a test Template for the mediawiki-japi Java API copyToWiki function\n" + 
          "</noinclude>\n" + 
          "",true);
      sourceWiki.addExamplePage("testCopy",testTemplate);
    }
    // check whether the id is an alias
    if (aliases.containsKey(wikiId)) {
      wikiId = aliases.get(wikiId);
    }
    ExampleWiki result = getExampleWikis().get(wikiId);
    return result;
  }

  /**
   * @return the exampleWikis
   */
  public Map<String, ExampleWiki> getExampleWikis() {
    return exampleWikis;
  }

  /**
   * @param exampleWikis
   *          the exampleWikis to set
   */
  public void setExampleWikis(Map<String, ExampleWiki> exampleWikis) {
    this.exampleWikis = exampleWikis;
  }
}
