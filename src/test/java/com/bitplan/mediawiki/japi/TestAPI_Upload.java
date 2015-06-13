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

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.junit.Test;

import com.bitplan.mediawiki.japi.ExampleWiki.ExamplePage;
import com.bitplan.mediawiki.japi.Mediawiki.TokenResult;
import com.bitplan.mediawiki.japi.api.Edit;
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
