/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package org.wikipedia;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;

import com.bitplan.mediawiki.japi.MediaWikiApiImpl;
import com.bitplan.mediawiki.japi.MediawikiApi;
import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Edit;
import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Login;
import com.bitplan.mediawiki.japi.api.P;

/**
 * Mediawiki-Japi compatible Wrapper for Wiki.java
 * 
 * @author wf
 *
 */
public class Mediawiki extends MediaWikiApiImpl implements MediawikiApi {
  // delegate to the one class Wiki solution
  Wiki wiki;

  private String domain;

  private String scriptPath;

  private String siteurl;

  private boolean debug;

  @Override
  public String getSiteurl() {
    return siteurl;
  }

  @Override
  public void setSiteurl(String siteurl) throws Exception {
    this.siteurl = siteurl;
    URL url = new URL(siteurl);
    domain = url.getHost();
    domain = domain.startsWith("www.") ? domain.substring(4) : domain;
    scriptPath = url.getPath();
    if ("".equals(scriptPath)) {
      scriptPath = "/w";
    }
    wiki = new Wiki();
    String prot = url.getProtocol();

    wiki.init(prot, domain, scriptPath);
    // zip doesn't work as of 2015-01-20
    wiki.setUsingCompressedRequests(false);
  }

  /**
   * @return the scriptPath
   */
  public String getScriptPath() {
    return scriptPath;
  }

  /**
   * @param scriptPath
   *          the scriptPath to set
   */
  public void setScriptPath(String scriptPath) {
    this.scriptPath = scriptPath;
  }

  @Override
  public void init(String siteurl, String scriptPath) throws Exception {
    setSiteurl(siteurl + "/" + scriptPath);
  }

  @Override
  public String getVersion() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public General getSiteInfo() throws Exception {
    Map<String, Object> siteinfo = wiki.getSiteInfo();
    String xml = (String) siteinfo.get("xml");
    Api api = super.fromXML(xml);
    General result = api.getQuery().getGeneral();
    return result;
  }

  @Override
  public Login login(String username, String password) throws Exception {
    wiki.login(username, password);
    Login result = new Login();
    return result;
  }

  @Override
  public boolean isLoggedIn() {
    boolean result=wiki.user!=null;
    return result;
  }

  @Override
  public String getPageContent(String pageTitle) throws Exception {
    String result = wiki.getPageText(pageTitle);
    return result;
  }
  
  @Override
  public String getSectionText(String pageTitle, int sectionNumber)
      throws Exception {
    return wiki.getSectionText(pageTitle, sectionNumber);
  }

  @Override
  public void logout() throws Exception {
    wiki.logout();
  }

  @Override
  public Edit edit(String pageTitle, String text, String summary)
      throws Exception {
    Edit result = new Edit();
    String pageContent = getPageContent(pageTitle);
    if (pageContent != null && pageContent.contains(protectionMarker)) {
      LOGGER.log(Level.WARNING, "page " + pageTitle + " is protected!");
    } else {
      wiki.edit(pageTitle, text, summary);
    }
    return result;
  }

  @Override
  /**
   * get a current IsoTimeStamp
   * FIXME redundant implementation same functioin com.bitplan.mediawiki.japi.api
   * @return - the current timestamp
   */
  public String getIsoTimeStamp() {
    TimeZone tz = TimeZone.getTimeZone("UTC");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
    df.setTimeZone(tz);
    String nowAsISO = df.format(new Date());
    return nowAsISO;
  }

  @Override
  public void setDebug(boolean pDebug) {
    this.debug = pDebug;
  }

  @Override
  public boolean isDebug() {
    return debug;
  }

  @Override
  public void upload(File file, String filename, String contents, String reason)
      throws Exception {
    // simply delegate
    this.wiki.upload(file, filename, contents, reason);
  }

  @Override
  public List<P> getAllPages(String apfrom, int aplimit) throws Exception {
    // FIXME - implement
    return null;
  }

 

}
