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

import static org.junit.Assert.*;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Query;


/**
 * https://github.com/WolfgangFahl/Mediawiki-Japi/issues/23
 * @author wf
 *
 */
public class TestIssues {

	@Test
	/**
	 * test for <a href='https://github.com/WolfgangFahl/Mediawiki-Japi/issues/23'>Issue 23</a>
	 * @throws Exception
	 */
	public void testSampleQuery() throws Exception {
		Mediawiki wiki = new Mediawiki("https://en.wikipedia.org");
		String content = wiki.getPageContent("Main Page");
		assertTrue(content.contains("Wikipedia"));
	}
	
	@Test
	/***
	 * test for <a href='https://github.com/WolfgangFahl/Mediawiki-Japi/issues/47'>Issue 47 getVersion fails for MW 1.33</a> 
	 * @throws Exception
	 */
  public void testVersionXML() throws Exception {
    String xml="<?xml version=\"1.0\"?><api batchcomplete=\"\"><query><general mainpage=\"Main Page\" base=\"http://royal-family.bitplan.com/index.php/Main_Page\" sitename=\"BITPlan royal-family Wiki\" logo=\"http://royal-family.bitplan.com/images/royal-family/thumb/6/63/Profiwikiicon.png/132px-Profiwikiicon.png\" generator=\"MediaWiki 1.33.0\" phpversion=\"7.2.19-0ubuntu0.18.04.2\" phpsapi=\"apache2handler\" dbtype=\"mysql\" dbversion=\"10.1.41-MariaDB-0ubuntu0.18.04.1\" langconversion=\"\" titleconversion=\"\" linkprefixcharset=\"\" linkprefix=\"\" linktrail=\"/^([a-z]+)(.*)$/sD\" legaltitlechars=\" %!&quot;$&amp;&#039;()*,\\-.\\/0-9:;=?@A-Z\\\\^_`a-z~\\x80-\\xFF+\" invalidusernamechars=\"@:\" fixarabicunicode=\"\" fixmalayalamunicode=\"\" case=\"first-letter\" lang=\"en\" fallback8bitEncoding=\"windows-1252\" writeapi=\"\" maxarticlesize=\"2097152\" timezone=\"Europe/Berlin\" timeoffset=\"120\" articlepath=\"/index.php/$1\" scriptpath=\"\" script=\"/index.php\" server=\"http://royal-family.bitplan.com\" servername=\"royal-family.bitplan.com\" wikiid=\"royal_familywiki\" time=\"2019-10-10T15:08:20Z\" uploadsenabled=\"\" maxuploadsize=\"104857600\" minuploadchunksize=\"1024\" favicon=\"http://royal-family.bitplan.com/favicon.ico\" centralidlookupprovider=\"local\" interwikimagic=\"\" categorycollation=\"uppercase\" citeresponsivereferences=\"\"><externalimages><prefix /></externalimages><fallback /><galleryoptions imagesPerRow=\"0\" imageWidth=\"120\" imageHeight=\"120\" captionLength=\"\" showBytes=\"\" showDimensions=\"\" mode=\"traditional\" /><thumblimits><limit _idx=\"0\">120</limit><limit _idx=\"1\">150</limit><limit _idx=\"2\">180</limit><limit _idx=\"3\">200</limit><limit _idx=\"4\">250</limit><limit _idx=\"5\">300</limit></thumblimits><imagelimits><limit _idx=\"0\" width=\"320\" height=\"240\" /><limit _idx=\"1\" width=\"640\" height=\"480\" /><limit _idx=\"2\" width=\"800\" height=\"600\" /><limit _idx=\"3\" width=\"1024\" height=\"768\" /><limit _idx=\"4\" width=\"1280\" height=\"1024\" /></imagelimits><allcentralidlookupproviders><_v>local</_v></allcentralidlookupproviders><magiclinks /></general><namespaces><ns _idx=\"-2\" id=\"-2\" case=\"first-letter\" canonical=\"Media\" xml:space=\"preserve\">Media</ns><ns _idx=\"-1\" id=\"-1\" case=\"first-letter\" canonical=\"Special\" xml:space=\"preserve\">Special</ns><ns _idx=\"0\" id=\"0\" case=\"first-letter\" content=\"\" xml:space=\"preserve\" /><ns _idx=\"1\" id=\"1\" case=\"first-letter\" subpages=\"\" canonical=\"Talk\" xml:space=\"preserve\">Talk</ns><ns _idx=\"2\" id=\"2\" case=\"first-letter\" subpages=\"\" canonical=\"User\" xml:space=\"preserve\">User</ns><ns _idx=\"3\" id=\"3\" case=\"first-letter\" subpages=\"\" canonical=\"User talk\" xml:space=\"preserve\">User talk</ns><ns _idx=\"4\" id=\"4\" case=\"first-letter\" subpages=\"\" canonical=\"Project\" xml:space=\"preserve\">BITPlan royal-family Wiki</ns><ns _idx=\"5\" id=\"5\" case=\"first-letter\" subpages=\"\" canonical=\"Project talk\" xml:space=\"preserve\">BITPlan royal-family Wiki talk</ns><ns _idx=\"6\" id=\"6\" case=\"first-letter\" canonical=\"File\" xml:space=\"preserve\">File</ns><ns _idx=\"7\" id=\"7\" case=\"first-letter\" subpages=\"\" canonical=\"File talk\" xml:space=\"preserve\">File talk</ns><ns _idx=\"8\" id=\"8\" case=\"first-letter\" subpages=\"\" canonical=\"MediaWiki\" xml:space=\"preserve\">MediaWiki</ns><ns _idx=\"9\" id=\"9\" case=\"first-letter\" subpages=\"\" canonical=\"MediaWiki talk\" xml:space=\"preserve\">MediaWiki talk</ns><ns _idx=\"10\" id=\"10\" case=\"first-letter\" subpages=\"\" canonical=\"Template\" xml:space=\"preserve\">Template</ns><ns _idx=\"11\" id=\"11\" case=\"first-letter\" subpages=\"\" canonical=\"Template talk\" xml:space=\"preserve\">Template talk</ns><ns _idx=\"12\" id=\"12\" case=\"first-letter\" subpages=\"\" canonical=\"Help\" xml:space=\"preserve\">Help</ns><ns _idx=\"13\" id=\"13\" case=\"first-letter\" subpages=\"\" canonical=\"Help talk\" xml:space=\"preserve\">Help talk</ns><ns _idx=\"14\" id=\"14\" case=\"first-letter\" canonical=\"Category\" xml:space=\"preserve\">Category</ns><ns _idx=\"15\" id=\"15\" case=\"first-letter\" subpages=\"\" canonical=\"Category talk\" xml:space=\"preserve\">Category talk</ns><ns _idx=\"102\" id=\"102\" case=\"first-letter\" canonical=\"Property\" xml:space=\"preserve\">Property</ns><ns _idx=\"103\" id=\"103\" case=\"first-letter\" subpages=\"\" canonical=\"Property talk\" xml:space=\"preserve\">Property talk</ns><ns _idx=\"106\" id=\"106\" case=\"first-letter\" canonical=\"Form\" xml:space=\"preserve\">Form</ns><ns _idx=\"107\" id=\"107\" case=\"first-letter\" subpages=\"\" canonical=\"Form talk\" xml:space=\"preserve\">Form talk</ns><ns _idx=\"108\" id=\"108\" case=\"first-letter\" canonical=\"Concept\" content=\"\" xml:space=\"preserve\">Concept</ns><ns _idx=\"109\" id=\"109\" case=\"first-letter\" subpages=\"\" canonical=\"Concept talk\" xml:space=\"preserve\">Concept talk</ns><ns _idx=\"112\" id=\"112\" case=\"first-letter\" canonical=\"smw/schema\" defaultcontentmodel=\"smw/schema\" xml:space=\"preserve\">smw/schema</ns><ns _idx=\"113\" id=\"113\" case=\"first-letter\" canonical=\"smw/schema talk\" xml:space=\"preserve\">smw/schema talk</ns><ns _idx=\"114\" id=\"114\" case=\"first-letter\" canonical=\"Rule\" xml:space=\"preserve\">Rule</ns><ns _idx=\"115\" id=\"115\" case=\"first-letter\" canonical=\"Rule talk\" xml:space=\"preserve\">Rule talk</ns></namespaces></query></api>";
    Api api = Api.fromXML(xml);
    assertNotNull(api);
    Query query = api.getQuery();
    General general = query.getGeneral();
    SiteInfoImpl siteinfo = new SiteInfoImpl(general, query.getNamespaces());
    String version = siteinfo.getVersion();
    assertEquals("MediaWiki 1.33.0",version);
  }
	
	/**
	 * check version strings
	 * @throws Exception
	 */
	@Test
	public void testIssue52() throws Exception {
	  String versions[]= {"MediaWiki 1.32.2", "MediaWiki 1.31.4", "MediaWiki 1.35.0-wmf.23","MediaWiki 1.36.0-wmf.10"};
	  boolean expected[] = {true,true,true,true};
	  Mediawiki mw = new Mediawiki();
	  int index=0;
	  for (String version:versions)  {
	    assertEquals(expected[index],mw.checkVersion128(version));
	    index++;
	  }
	}
	
	/**
	 * see <a href='https://github.com/WolfgangFahl/Mediawiki-Japi/issues/50'>Spaces in pageTitles for CommandLine #50</a>
	 */
	
	@Test
	public void testIssue50() {
	  
	}
	

}
