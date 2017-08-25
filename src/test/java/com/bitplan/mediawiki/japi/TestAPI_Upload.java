/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2017 BITPlan GmbH https://github.com/BITPlan
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

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Ii;

/**
 * test https://www.mediawiki.org/wiki/API:Upload
 * 
 * @author wf
 *
 */
public class TestAPI_Upload extends APITestbase {

	/**
	 * test uploading a file
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpload() throws Exception {
    ExampleWiki lwiki=ewm.get("mediawiki-japi-test1_24");
    lwiki.login();
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("upload/Radcliffe_Chastenay_-_Les_Mysteres_d_Udolphe_frontispice_T6.jpg").getFile());
    String filename=file.getName();
    String contents="http://commons.wikimedia.org/wiki/File:Radcliffe_Chastenay_-_Les_Mysteres_d_Udolphe_frontispice_T6.jpg";
    String reason="test upload "+lwiki.wiki.getIsoTimeStamp();
    lwiki.wiki.upload(file, filename, contents, reason);
    String targetUrl=lwiki.wiki.getImageInfo("File:"+filename).getUrl();
    assertTrue("url '"+targetUrl+"' should exist",this.urlExists(targetUrl));
	}
	
	/**
	 * test uploading via image information
	 * @throws Exception
	 */
	@Test
	public void testUploadViaII() throws Exception {
	  ExampleWiki sourceWiki = ewm.get("sourceWiki");
	  // sourceWiki.wiki.setDebug(true);
	  String imageName="Index.png";
	  String imageTitle="File:"+imageName;
    Ii ii=sourceWiki.getImageInfo(imageTitle);
    ExampleWiki targetWiki =ewm.get("targetWiki");
    String pageContent=sourceWiki.wiki.getPageContent(imageTitle);
    targetWiki.login();
    targetWiki.wiki.upload(ii, imageName, pageContent);
    // targetWiki.wiki.setDebug(true);
    String targetUrl=targetWiki.wiki.getImageInfo(imageTitle).getUrl();
    assertTrue("url '"+targetUrl+"' should exist",this.urlExists(targetUrl));
	}
	
}
