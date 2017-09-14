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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Bl;
import com.bitplan.mediawiki.japi.api.Delete;
import com.bitplan.mediawiki.japi.api.Edit;
import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Ii;
import com.bitplan.mediawiki.japi.api.Im;
import com.bitplan.mediawiki.japi.api.Imageinfo;
import com.bitplan.mediawiki.japi.api.Img;
import com.bitplan.mediawiki.japi.api.Iu;
import com.bitplan.mediawiki.japi.api.Login;
import com.bitplan.mediawiki.japi.api.P;
import com.bitplan.mediawiki.japi.api.Page;
import com.bitplan.mediawiki.japi.api.Parse;
import com.bitplan.mediawiki.japi.api.Query;
import com.bitplan.mediawiki.japi.api.Rc;
import com.bitplan.mediawiki.japi.api.S;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.StreamDataBodyPart;

/**
 * access to Mediawiki api
 * 
 * @author wf
 *
 */
public class Mediawiki extends MediaWikiApiImpl implements MediawikiApi {

  /**
   * current Version
   */
  protected static final String VERSION = "0.0.19";

  /**
   * if true main can be called without calling system.exit() when finished
   */
  public static boolean testMode = false;

  /**
   * see <a href=
   * 'https://www.mediawiki.org/wiki/API:Main_page#Identifying_your_client'>
   * Iden t i f y i n g your client:User-Agent</a>
   */
  protected static final String USER_AGENT = "Mediawiki-Japi/"
      + VERSION
      + " (https://github.com/WolfgangFahl/Mediawiki-Japi; support@bitplan.com)";

  /**
   * default script path
   */
  public static final String DEFAULT_SCRIPTPATH = "/w";

  protected String siteurl;
  protected String scriptPath = DEFAULT_SCRIPTPATH;
  // FIXME - default should be json soon
  protected String format = "xml";
  protected String apiPath = "/api.php?";

  // the client and it's cookies
  private Client client;
  private ArrayList<Object> cookies;

  // mediaWikiVersion and site info
  protected String userid;

  SiteInfo siteinfo;

  /**
   * enable debugging
   * 
   * @param debug
   */
  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  @Override
  public boolean isDebug() {
    return this.debug;
  }

  /**
   * @return the siteurl
   */
  public String getSiteurl() {
    return siteurl;
  }

  /**
   * @param siteurl
   *          the siteurl to set
   */
  public void setSiteurl(String siteurl) {
    this.siteurl = siteurl;
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

  /**
   * construct me with no siteurl set
   * 
   * @throws Exception
   */
  public Mediawiki() throws Exception {
    this(null);
  }

  /**
   * construct a Mediawiki for the given url using the default Script path
   * 
   * @param siteurl
   *          - the url to use
   * @throws Exception
   */
  public Mediawiki(String siteurl) throws Exception {
    this(siteurl, DEFAULT_SCRIPTPATH);
  }

  /**
   * construct a Mediawiki for the given url and scriptpath
   * 
   * @param siteurl
   *          - the url to use
   * @param pScriptPath
   *          - the scriptpath to use
   * @throws Exception
   */
  public Mediawiki(String siteurl, String pScriptPath) throws Exception {
    init(siteurl, pScriptPath);
  }

  /**
   * overrideable e.g for SSL configuration
   * 
   * @throws Exception
   */
  @Override
  public void init(String siteurl, String scriptpath) throws Exception {
    ApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
    config.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES,
        true);
    client = ApacheHttpClient.create(config);
    client.setFollowRedirects(true);
    // org.apache.log4j.Logger.getLogger("httpclient").setLevel(Level.ERROR);
    this.siteurl = siteurl;
    this.scriptPath = scriptpath;
  }

  /**
   * get a current IsoTimeStamp
   * 
   * @return - the current timestamp
   */
  public String getIsoTimeStamp() {
    TimeZone tz = TimeZone.getTimeZone("UTC");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
    df.setTimeZone(tz);
    String nowAsISO = df.format(new Date());
    return nowAsISO;
  }

  /**
   * get a String from a given URL
   * 
   * @param urlString
   * @return
   */
  public static String getStringFromUrl(String urlString) {
    ApacheHttpClient lclient = ApacheHttpClient.create();
    WebResource webResource = lclient.resource(urlString);
    ClientResponse response = webResource.get(ClientResponse.class);

    if (response.getStatus() != 200) {
      throw new RuntimeException("HTTP error code : " + response.getStatus());
    }
    String result = response.getEntity(String.class);
    return result;
  }

  /**
   * get the given Builder for the given queryUrl this is a wrapper to be able
   * to debug all QueryUrl
   * 
   * @param queryUrl
   *          - either a relative or absolute path
   * @return
   * @throws Exception
   */
  public Builder getResource(String queryUrl) throws Exception {
    if (debug)
      LOGGER.log(Level.INFO, queryUrl);
    WebResource wrs = client.resource(queryUrl);
    Builder result = wrs.header("USER-AGENT", USER_AGENT);
    return result;
  }

  /**
   * get a Post response
   * 
   * @param queryUrl
   * @param params
   *          - direct query parameters
   * @param token
   *          - a token if any
   * @param pFormData
   *          - the form data - either as multipart of urlencoded
   * @return - the client Response
   * @throws Exception
   */
  public ClientResponse getPostResponse(String queryUrl, String params,
      TokenResult token, Object pFormDataObject) throws Exception {
    params = params.replace("|", "%7C");
    // modal handling of post
    FormDataMultiPart form = null;
    MultivaluedMap<String, String> lFormData = null;
    if (pFormDataObject instanceof FormDataMultiPart) {
      form = (FormDataMultiPart) pFormDataObject;
    } else {
      @SuppressWarnings("unchecked")
      Map<String, String> pFormData = (Map<String, String>) pFormDataObject;
      lFormData = new MultivaluedMapImpl();
      if (pFormData != null) {
        for (String key : pFormData.keySet()) {
          lFormData.add(key, pFormData.get(key));
        }
      }
    }
    if (token != null) {
      switch (token.tokenMode) {
      case token1_24:
        if (lFormData != null) {
          lFormData.add(token.tokenName, token.token);
        } else {
          form.field(token.tokenName, token.token);
        }
        break;
      default:
        params += token.asParam();
      }
    }
    Builder resource = getResource(queryUrl+params);
    // FIXME allow to specify content type (not needed for Mediawiki itself
    // but
    // could be good for interfacing )
    ClientResponse response = null;
    if (lFormData != null) {
      response = resource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
          .post(ClientResponse.class, lFormData);
    } else {
      response = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(
          ClientResponse.class, form);
    }
    return response;
  }

  public enum Method {
    Post, Get, Head, Put
  };

  /**
   * get a response for the given url and method
   * 
   * @param url
   * @param method
   * @return
   * @throws Exception
   */
  public ClientResponse getResponse(String url, Method method) throws Exception {
    Builder resource = getResource(url);
    ClientResponse response = null;
    switch (method) {
    case Get:
      response = resource.get(ClientResponse.class);
      break;
    case Post:
      response = resource.post(ClientResponse.class);
      break;
    case Head:
      response = resource.head();
      break;
    case Put:
      response = resource.put(ClientResponse.class);
      break;
    }
    return response;
  }

  /**
   * get the Response string
   * 
   * @param response
   * @return the String representation of a response
   * @throws Exception
   */
  public String getResponseString(ClientResponse response) throws Exception {
    if (debug)
      LOGGER.log(Level.INFO, "status: " + response.getStatus());
    String responseText = response.getEntity(String.class);
    if (response.getStatus() != 200) {
      handleError("status " + response.getStatus() + ":'" + responseText + "'");
    }
    return responseText;
  }

  /**
   * get a Map of parameter from a & delimited parameter list
   * 
   * @param params
   *          - the list of parameters
   * @return the map FIXME - should check that split creates and even number of
   *         entries - add test case for this
   */
  public Map<String, String> getParamMap(String params) {
    Map<String, String> result = new HashMap<String, String>();
    String[] paramlist = params.split("&");
    for (int i = 0; i < paramlist.length; i++) {
      String[] parts = paramlist[i].split("=");
      if (parts.length == 2)
        result.put(parts[0], parts[1]);
    }
    return result;
  }

  /**
   * get the result for the given action and params
   * 
   * @param action
   * @param params
   * @param token
   *          (may be null)
   * @return the API result for the action
   * @throws Exception
   */
  public Api getActionResult(String action, String params, TokenResult token,
      Object pFormData) throws Exception {
    String queryUrl = siteurl + scriptPath + apiPath + "&action=" + action
        + "&format=" + format;
    String xml;
    ClientResponse response;
    // decide for the method to use for api access
    response = this.getPostResponse(queryUrl, params, token, pFormData);
    xml = this.getResponseString(response);
    if (debug) {
      // convert the xml to a more readable format
      String xmlDebug = xml.replace(">", ">\n");
      LOGGER.log(Level.INFO, xmlDebug);
    }
    Api api = fromXML(xml);
    return api;
  }

  /**
   * get the result for the given action and query
   * 
   * @param action
   * @param params
   * @return the API result for the action
   * @throws Exception
   */
  public Api getActionResult(String action, String params) throws Exception {
    Api result = this.getActionResult(action, params, null, null);
    return result;
  }

  /**
   * get the Result for the given query
   * 
   * @param query
   * @return the API result for the query
   * @throws Exception
   */
  public Api getQueryResult(String query) throws Exception {
    Api result = this.getActionResult("query", query, null, null);
    return result;
  }

  /**
   * get a normalized | delimited (encoded as %7C) string of titles
   * 
   * @param examplePages
   *          - the list of pages to get the titles for
   * @return a string with all the titles e.g. Main%20Page%7CSome%20Page
   * @throws Exception
   */
  public String getTitles(List<String> titleList) throws Exception {
    String titles = "";
    String delim = "";
    for (String title : titleList) {
      titles = titles + delim + normalizeTitle(title);
      delim = "%7C";
    }
    return titles;
  }

  /**
   * get the siteinfo
   * 
   * @return the siteinfo
   * @throws Exception
   */
  public SiteInfo getSiteInfo() throws Exception {
    if (siteinfo == null) {
      Api api = getQueryResult("&meta=siteinfo&siprop=general%7Cnamespaces");
      Query query = api.getQuery();
      setUpSiteInfo(query);
    }
    return siteinfo;
  }

  /**
   * setup siteinfo for a query (e.g. for testing)
   * 
   * @param query
   */
  public void setUpSiteInfo(Query query) {
    General general = query.getGeneral();
    siteinfo = new SiteInfoImpl(general, query.getNamespaces());
  }
  
  /**
   * check whether this is MediaWiki 1.28 or higher
   * but make sure getVersion calls with readapidenied are ignored
   * see https://github.com/WolfgangFahl/Mediawiki-Japi/issues/32
   * @return
   */
  public boolean isVersion128() {
    String mwversion="Mediawiki 1.27 or before";
    try {
      mwversion=this.getVersion(); 
    } catch (Exception e) {
      LOGGER.log(Level.INFO,"Could not retrieve Mediawiki Version via API - will assume "+mwversion+" you might want to set the Version actively if you are on 1.28 and have the api blocked for non-logged in users");
    }
    boolean result=mwversion.compareToIgnoreCase("Mediawiki 1.28") >= 0;
    return result;
  }
  
  /**
   * prepare the login by getting the login token
   * @param username
   * @return the ApiResult
   * @throws Exception 
   */
  public TokenResult prepareLogin(String username) throws Exception {  
    username = encode(username);
    Api apiResult=null;
    TokenResult token = new TokenResult();
    token.tokenName = "lgtoken";
    token.tokenMode = TokenMode.token1_19;
    // see https://github.com/WolfgangFahl/Mediawiki-Japi/issues/31 
    if (this.isVersion128()) {
      apiResult=this.getQueryResult("&meta=tokens&type=login");
      super.handleError(apiResult); 
      token.token=apiResult.getQuery().getTokens().getLogintoken();
    } else {
      apiResult = getActionResult("login", "&lgname=" + username, null, null);
      super.handleError(apiResult); 
      Login login = apiResult.getLogin();
      token.token = login.getToken();
    }
    return token;
  }

  /**
   * second step of login process
   * @param token
   * @param username
   * @param password
   * @return
   * @throws Exception 
   */
  public Login login(TokenResult token,String username, String password) throws Exception {
    return login(token, username, password, null);
  }

  /**
   * second step of login process
   * @param token
   * @param username
   * @param password
   * @param domain
   * @return
   * @throws Exception 
   */
  public Login login(TokenResult token,String username, String password, String domain) throws Exception {
    username = encode(username);
    if (domain != null) {
      domain = encode(domain);
    }
    Api apiResult=null;
    // depends on MediaWiki version see
    // https://test2.wikipedia.org/w/api.php?action=help&modules=clientlogin
    if (this.isVersion128()) {
      Map<String, String> lFormData = new HashMap<String, String>();
      lFormData.put("lgpassword", password);
      lFormData.put("lgtoken",token.token);
      if (domain != null) {
        apiResult=getActionResult("login", "&lgdomain=" + domain + "&lgname=" + username, null, lFormData);
      } else {
        apiResult=getActionResult("login", "&lgname=" + username, null, lFormData);
      }
      // apiResult = getActionResult("clientlogin", "&lgname=" + username+"&loginreturnurl="+this.siteurl, null, lFormData);
    } else {
      password = encode(password);
      if (domain != null) {
        apiResult = getActionResult("login", "&lgdomain=" + domain + "&lgname=" + username + "&lgpassword="
            + password, token, null);
      } else {
        apiResult = getActionResult("login", "&lgname=" + username + "&lgpassword="
            + password, token, null);
      }
    } 
    Login login = apiResult.getLogin();
    userid = login.getLguserid();
    return login;
  }

  /**
   * login with the given username, password and domain
   * @param username
   * @param password
   * @param domain
   * @return Login
   * @throws Exception
   */
  public Login login(String username, String password, String domain) throws Exception {
    // login is a two step process
    // first we get a token
    TokenResult token=prepareLogin(username);
    // and then with the token we login using the password
    Login login=login(token,username,password, domain);
    return login;
  }

  /**
   * login with the given username and password
   * @param username
   * @param password
   * @return Login
   * @throws Exception
   */
  public Login login(String username, String password) throws Exception {
    return login(username, password, null);
  }
  
  
  @Override
  public boolean isLoggedIn() {
    boolean result = userid != null;
    return result;
  }

  /**
   * end the session
   * 
   * @throws Exception
   */
  public void logout() throws Exception {
    Api apiResult = getActionResult("logout", "", null, null);
    if (apiResult != null) {
      userid = null;
      // FIXME check apiResult
    }
    if (cookies != null) {
      cookies.clear();
      cookies = null;
    }
  }

  /**
   * get the page Content for the given page Title
   * 
   * @param pageTitle
   * @param queryParams
   *          - extra query params e.g. for sections
   * @param checkNotNull
   *          - check if the content should not be null
   * @return the page Content
   * @throws Exception
   */
  public String getPageContent(String pageTitle, String queryParams,
      boolean checkNotNull) throws Exception {
    Api api = getQueryResult("&prop=revisions&rvprop=content" + queryParams
        + "&titles=" + normalizeTitle(pageTitle));
    handleError(api);
    List<Page> pages = api.getQuery().getPages();
    String content = null;
    if (pages != null) {
      Page page = pages.get(0);
      if (page != null) {
        if (page.getRevisions().size() > 0) {
          content = page.getRevisions().get(0).getValue();
        }
      }
    }
    if (checkNotNull && content == null) {
      String errMsg = "pageTitle '" + pageTitle + "' not found";
      this.handleError(errMsg);
    }
    return content;
  }

  /**
   * get the page Content for the given page Title
   * 
   * @param pageTitle
   * @return the page Content
   * @throws Exception
   */
  public String getPageContent(String pageTitle) throws Exception {
    String result = this.getPageContent(pageTitle, "", false);
    return result;
  }

  /**
   * get the text for the given section
   * 
   * @param pageTitle
   * @param sectionNumber
   * @return
   * @throws Exception
   */
  public String getSectionText(String pageTitle, int sectionNumber)
      throws Exception {
    String result = this.getPageContent(pageTitle, "&rvsection="
        + sectionNumber, false);
    return result;
  }

  @Override
  public List<S> getSections(String pageTitle) throws Exception {
    String params = "&prop=sections&page=" + pageTitle;
    Parse parse=getParse(params);
    List<S> sections = parse.getSections();
    return sections;
  }
  
  @Override
  public String getPageHtml(String pageTitle) throws Exception {
    String params="&page="+encode(pageTitle);
    Parse parse=getParse(params);
    String html=parse.getText();
    return html;
  }
  
  /**
   * get the parse Result for the given params
   * @param params
   * @return the Parse Result
   * @throws Exception
   */
  public Parse getParse(String params) throws Exception {
    String action = "parse";
    Api api = getActionResult(action, params);
    super.handleError(api);
    return api.getParse();
  }

  /**
   * get a list of pages for the given titles see
   * <a href='http://www.mediawiki.org/wiki/API:Query'>API:Query</a>
   * 
   * @param titleList
   * @param rvprop
   *          - the revision properties
   * @return the list of pages retrieved
   * @throws Exception
   * 
   *           FIXME should be part of the Java Interface
   */
  public List<Page> getPages(List<String> titleList, String rvprop)
      throws Exception {
    String titles = this.getTitles(titleList);
    // https://www.mediawiki.org/wiki/API:Revisions#Parameters
    Api api = getQueryResult("&titles=" + titles + "&prop=revisions&rvprop="
        + rvprop);
    handleError(api);
    Query query = api.getQuery();
    if (query == null) {
      throw new Exception("query is null for getPages '" + titleList
          + "' rvprop='" + rvprop + "'");
    }
    List<Page> pages = query.getPages();
    return pages;
  }

  /**
   * get a list of pages for the given titles see
   * <a href='http://www.mediawiki.org/wiki/API:Query'>API:Query</a>
   * 
   * @param titleList
   * @return
   * @throws Exception
   */
  public List<Page> getPages(List<String> titleList) throws Exception {
    String rvprop = "content|ids|timestamp";
    List<Page> result = this.getPages(titleList, rvprop);
    return result;
  }

  /**
   * the different modes of handling tokens - depending on MediaWiki version
   */
  enum TokenMode {
    token1_19, token1_20_23, token1_24
  }

  /**
   * helper class to handle the different token modes
   * 
   * @author wf
   *
   */
  class TokenResult {
    String tokenName;
    String token;
    TokenMode tokenMode;

    /**
     * set my token - remove trailing backslash or +\ if necessary
     * 
     * @param pToken
     *          - the token to set
     */
    public void setToken(String pToken) {
      token = pToken;
    }

    /**
     * get me as a param string e.g. &lgtoken=1234 make sure the trailing \ or
     * +\ are handled correctly see
     * <a href= 'https://www.mediawiki.org/wiki/Manual:Edit_token'>Manual:
     * Edit_token</a>
     * 
     * @return - the resulting string
     * @throws Exception
     */
    public String asParam() throws Exception {
      String lToken = token;
      /*
       * switch (tokenMode) { case token1_24: lToken=lToken.replace("+","");
       * lToken=lToken.replace("\\",""); break; default:
       * 
       * }
       */
      // token=pToken+"%2B%5C";
      // http://wikimedia.7.x6.nabble.com/Error-badtoken-Info-Invalid-token-td4977853.html
      String result = "&" + tokenName + "=" + encode(lToken);
      if (debug)
        LOGGER.log(Level.INFO, "token " + token + "=>" + result);
      return result;
    }
  }

  /**
   * get an edit token for the given page Title see
   * <a href='https://www.mediawiki.org/wiki/API:Tokens'>API:Tokens</a>
   * 
   * @param pageTitle
   * @param type
   *          e.g. edit or delete
   * @return the edit token for the page title
   * @throws Exception
   */
  public TokenResult getEditToken(String pageTitle, String type)
      throws Exception {
    pageTitle = normalizeTitle(pageTitle);
    String editversion = "";
    String action = "query";
    String params = "&meta=tokens";
    TokenMode tokenMode;
    if (getVersion().compareToIgnoreCase("Mediawiki 1.24") >= 0) {
      editversion = "Versions 1.24 and later";
      tokenMode = TokenMode.token1_24;
      params = "&meta=tokens";
    } else if (getVersion().compareToIgnoreCase("Mediawiki 1.20") >= 0) {
      editversion = "Versions 1.20-1.23";
      tokenMode = TokenMode.token1_20_23;
      action = "tokens";
      params = "&type=" + type;
    } else {
      editversion = "Version 1.19 and earlier";
      tokenMode = TokenMode.token1_19;
      params = "&prop=info&7Crevisions&intoken=" + type + "&titles="
          + pageTitle;
    }
    if (debug) {
      LOGGER.log(Level.INFO, "handling " + type + " token for wiki version "
          + getVersion() + " as " + editversion + " with action=" + action
          + params);
    }
    Api api = getActionResult(action, params);
    handleError(api);
    TokenResult token = new TokenResult();
    token.tokenMode = tokenMode;
    token.tokenName = "token";
    switch (tokenMode) {
    case token1_19:
      Page page = api.getQuery().getPages().get(0);
      if (type.equals("edit")) {
        token.setToken(page.getEdittoken());
      } else if (type.equals("delete")) {
        token.setToken(page.getDeletetoken());
      }

      break;
    case token1_20_23:
      if (type.equals("edit")) {
        token.setToken(api.getTokens().getEdittoken());
      } else if (type.equals("delete")) {
        token.setToken(api.getTokens().getDeletetoken());
      }
      break;
    default:
      token.setToken(api.getQuery().getTokens().getCsrftoken());
      break;
    }
    return token;
  }

  /**
   * https://www.mediawiki.org/wiki/API:Edit
   */
  @Override
  public Edit edit(String pageTitle, String text, String summary)
      throws Exception {
    Edit result = this.edit(pageTitle, text, summary, true, false, -2, null,
        null);
    return result;
  }

  /**
   * https://www.mediawiki.org/wiki/API:Delete/de
   * 
   * @return info
   */
  @Override
  public Delete delete(String pageTitle, String reason) throws Exception {
    Delete result = new Delete();
    String pageContent = getPageContent(pageTitle);
    if (pageContent != null && pageContent.contains(protectionMarker)) {
      LOGGER.log(Level.WARNING, "page " + pageTitle + " is protected!");
    } else {
      TokenResult token = getEditToken(pageTitle, "delete");
      if (token.token == null) {
        throw new IllegalStateException("could not get "
            + token.tokenMode.toString() + " delete token for " + pageTitle
            + " ");
      }
      Map<String, String> lFormData = new HashMap<String, String>();
      lFormData.put("title", pageTitle);
      lFormData.put("reason", reason);
      String params = "";
      Api api = this.getActionResult("delete", params, token, lFormData);
      handleError(api);
      result = api.getDelete();
    }
    return result;
  }

  @Override
  public Edit edit(String pageTitle, String text, String summary,
      boolean minor, boolean bot, int sectionNumber, String sectionTitle,
      Calendar basetime) throws Exception {
    Edit result = new Edit();
    String pageContent = getPageContent(pageTitle);
    if (pageContent != null && pageContent.contains(protectionMarker)) {
      LOGGER.log(Level.WARNING, "page " + pageTitle + " is protected!");
    } else {
      TokenResult token = getEditToken(pageTitle, "edit");
      Map<String, String> lFormData = new HashMap<String, String>();
      lFormData.put("text", text);
      lFormData.put("title", pageTitle);
      lFormData.put("summary", summary);
      if (minor)
        lFormData.put("minor", "1");
      if (bot)
        lFormData.put("bot", "1");
      switch (sectionNumber) {
      case -1:
        lFormData.put("section", "new");
        if (sectionTitle != null)
          lFormData.put("sectiontitle", sectionTitle);
        break;
      case -2:
        break;
      default:
        lFormData.put("section", "" + sectionNumber);
        break;
      }
      String params = "";
      Api api = this.getActionResult("edit", params, token, lFormData);
      handleError(api);
      result = api.getEdit();
    }
    return result;
  }

  /**
   * https://www.mediawiki.org/wiki/API:Upload
   */
  @Override
  public synchronized void upload(File fileToUpload, String filename,
      String contents, String comment) throws Exception {
    this.upload(new FileInputStream(fileToUpload), filename, contents, comment);
  }

  /**
   * upload from the given inputstream
   * 
   * @param fileToUpload
   * @param filename
   * @param contents
   * @param comment
   * @throws Exception
   */
  public synchronized void upload(InputStream fileToUpload, String filename,
      String contents, String comment) throws Exception {
    TokenResult token = getEditToken("File:" + filename, "edit");
    final FormDataMultiPart multiPart = new FormDataMultiPart();
    // http://stackoverflow.com/questions/5772225/trying-to-upload-a-file-to-a-jax-rs-jersey-server
    multiPart.bodyPart(new StreamDataBodyPart("file", fileToUpload));
    multiPart.field("filename", filename);
    multiPart.field("ignorewarnings", "true");
    multiPart.field("text", contents);
    if (!comment.isEmpty())
      multiPart.field("comment", comment);
    String params = "";
    Api api = this.getActionResult("upload", params, token, multiPart);
    handleError(api);
  }

  @Override
  public void upload(Ii ii, String fileName, String pageContent)
      throws Exception {
    String url = ii.getUrl();
    InputStream imageInput = new URL(url).openStream();
    String comment = ii.getComment();
    this.upload(imageInput, fileName, pageContent, comment);
  }

  /**
   * getAllPages
   * 
   * @param apfrom
   *          - may be null or empty
   * @param aplimit
   * @return
   * @throws Exception
   */
  public List<P> getAllPages(String apfrom, int aplimit) throws Exception {
    String query = "&list=allpages";
    if (apfrom != null && !apfrom.trim().equals("")) {
      query += "&apfrom=" + apfrom;
    }
    query += "&aplimit=" + aplimit;
    Api api = getQueryResult(query);
    List<P> pageRefList = api.getQuery().getAllpages();
    return pageRefList;
  }

  @Override
  public List<Img> getAllImagesByTimeStamp(String aistart, String aiend,
      int ailimit) throws Exception {
    String query = "&list=allimages&aisort=timestamp";
    if (aistart != null && !aistart.trim().equals("")) {
      query += "&aistart=" + aistart;
    }
    if (aiend != null && !aiend.trim().equals("")) {
      query += "&aiend=" + aiend;
    }
    query += "&ailimit=" + ailimit;
    Api api = getQueryResult(query);
    handleError(api);
    List<Img> result = api.getQuery().getAllImages();
    return result;
  }

  @Override
  public List<Bl> getBacklinks(String pageTitle, String params, int bllimit)
      throws Exception {
    String query = "&list=backlinks&bltitle=" + normalizeTitle(pageTitle);
    query += "&bllimit=" + bllimit;
    query += params;
    Api api = getQueryResult(query);
    handleError(api);
    List<Bl> result = api.getQuery().getBacklinks();
    return result;
  }

  @Override
  public List<Iu> getImageUsage(String imageTitle, String params, int limit)
      throws Exception {
    String query = "&list=imageusage&iutitle=" + normalizeTitle(imageTitle);
    query += "&iulimit=" + limit;
    query += params;
    Api api = getQueryResult(query);
    handleError(api);
    List<Iu> result = api.getQuery().getImageusage();
    return result;
  }

  /**
   * handle the given Throwable (in commandline mode)
   * 
   * @param t
   */
  public void handle(Throwable t) {
    System.out.flush();
    System.err.println(t.getClass().getSimpleName() + ":" + t.getMessage());
    if (debug)
      t.printStackTrace();
  }

  /**
   * show the Version
   */
  public static void showVersion() {
    System.err.println("Mediawiki-Japi Version: " + VERSION);
    System.err.println();
    System.err
        .println(" github: https://github.com/WolfgangFahl/Mediawiki-Japi");
    System.err.println("");
  }

  /**
   * show a usage
   */
  public void usage(String msg) {
    System.err.println(msg);

    showVersion();
    System.err.println("  usage: java com.bitplan.mediawiki.japi.Mediawiki");
    parser.printUsage(System.err);
    exitCode = 1;
  }

  /**
   * show Help
   */
  public void showHelp() {
    String msg = "Help\n"
        + "Mediawiki-Japi version "
        + VERSION
        + " has no functional command line interface\n"
        + "Please visit http://mediawiki-japi.bitplan.com for usage instructions";
    usage(msg);
  }

  private CmdLineParser parser;
  static int exitCode;
  /**
   * set to true for debugging
   */
  @Option(name = "-d", aliases = { "--debug" }, usage = "debug\nadds debugging output")
  protected boolean debug = false;

  @Option(name = "-h", aliases = { "--help" }, usage = "help\nshow this usage")
  boolean showHelp = false;

  @Option(name = "-v", aliases = { "--version" }, usage = "showVersion\nshow current version if this switch is used")
  boolean showVersion = false;

  /**
   * main instance - this is the non-static version of main - it will run as a
   * static main would but return it's exitCode to the static main the static
   * main will then decide whether to do a System.exit(exitCode) or not.
   * 
   * @param args
   *          - command line arguments
   * @return - the exit Code to be used by the static main program
   */
  protected int maininstance(String[] args) {
    parser = new CmdLineParser(this);
    try {
      parser.parseArgument(args);
      if (debug)
        showVersion();
      if (this.showVersion) {
        showVersion();
      } else if (this.showHelp) {
        showHelp();
      } else {
        // FIXME - do something
        // implement actions
        System.err.println("Commandline interface is not functional in "
            + VERSION + " yet");
        exitCode = 1;
        // exitCode = 0;
      }
    } catch (CmdLineException e) {
      // handling of wrong arguments
      usage(e.getMessage());
    } catch (Exception e) {
      handle(e);
      exitCode = 1;
    }
    return exitCode;
  }

  /**
   * entry point e.g. for java -jar called provides a command line interface
   * 
   * @param args
   */
  public static void main(String args[]) {
    Mediawiki wiki;
    try {
      wiki = new Mediawiki();
      int result = wiki.maininstance(args);
      if (!testMode && result != 0)
        System.exit(result);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * create the given user account
   * 
   * @param name
   * @param eMail
   * @param realname
   * @param mailpassword
   * @param reason
   * @param language
   * @throws Exception
   */
  public Api createAccount(String name, String eMail, String realname,
      boolean mailpassword, String reason, String language) throws Exception {
    String params = "&name=" + name;
    params += "&email=" + eMail;
    params += "&realname=" + realname;
    params += "&mailpassword=" + mailpassword;
    params += "&reason=" + reason;
    params += "&token=";
    Api api = getActionResult("createaccount", params);
    handleError(api);
    String token = api.getCreateaccount().getToken();
    params += token;
    api = getActionResult("createaccount", params);
    return api;
  }

  @Override
  public Ii getImageInfo(String pageTitle) throws Exception {
    // example
    // https://en.wikipedia.org/wiki/Special:ApiSandbox#action=query&prop=imageinfo&format=xml&iiprop=timestamp|user|userid|comment|parsedcomment|canonicaltitle|url|size|dimensions|sha1|mime|thumbmime|mediatype|metadata|commonmetadata|extmetadata|archivename|bitdepth|uploadwarning&titles=File%3AAlbert%20Einstein%20Head.jpg
    String props = "timestamp";
    props += "%7Cuser%7Cuserid%7Ccomment%7Cparsedcomment%7Curl%7Csize%7Cdimensions";
    props += "%7Csha1%7Cmime%7Cthumbmime%7Cmediatype%7Carchivename%7Cbitdepth";
    Api api = getQueryResult("&prop=imageinfo&iiprop=" + props + "&titles="
        + normalizeTitle(pageTitle));
    handleError(api);
    Ii ii = null;
    List<Page> pages = api.getQuery().getPages();
    if (pages != null) {
      Page page = pages.get(0);
      if (page != null) {
        Imageinfo imageinfo = page.getImageinfo();
        if (imageinfo != null) {
          ii = imageinfo.getIi();
        } else {
          String errMsg = "imageinfo for pageTitle '" + pageTitle
              + "' not found";
          this.handleError(errMsg);
        }
      }
    }
    if (ii == null) {
      String errMsg = "pageTitle '" + pageTitle + "' not found";
      this.handleError(errMsg);
    }
    return ii;
  }

  @Override
  public List<Im> getImagesOnPage(String pageTitle, int imLimit)
      throws Exception {
    String query = "&titles=" + normalizeTitle(pageTitle)
        + "&prop=images&imlimit=" + imLimit;
    Api api = getQueryResult(query);
    handleError(api);
    List<Im> result = new ArrayList<Im>();
    Query lquery = api.getQuery();
    if (lquery != null) {
      List<Page> pages = lquery.getPages();
      if (pages.size() > 0) {
        Page page = pages.get(0);
        result = page.getImages();
      }
    }
    return result;
  }

  @Override
  public List<Ii> getImageInfosForPage(String pageTitle, int imLimit)
      throws Exception {
    List<Im> images = this.getImagesOnPage(pageTitle, imLimit);
    List<Ii> result = new ArrayList<Ii>();
    for (Im image : images) {
      Ii imageinfo = this.getImageInfo(image.getTitle());
      if (imageinfo.getCanonicaltitle() == null) {
        imageinfo.setCanonicaltitle(image.getTitle());
      }
      result.add(imageinfo);
    }
    return result;
  }

  /**
   * get the recent changes see https://www.mediawiki.org/wiki/API:RecentChanges
   * 
   * @param rcstart
   *          The timestamp to start listing from (May not be more than
   *          $wgRCMaxAge into the past, which on Wikimedia wikis is 30 days[1])
   * @param rcend
   * @param rclimit
   * @return - the list of recent changes
   * @throws Exception
   */
  public List<Rc> getRecentChanges(String rcstart, String rcend, Integer rclimit)
      throws Exception {
    String query = "&list=recentchanges&rcprop=title%7Ctimestamp%7Csha1%7Cids%7Csizes%7Cflags%7Cuser";
    if (rclimit != null) {
      query += "&rclimit=" + rclimit;
    }
    if (rcstart != null) {
      query += "&rcstart=" + rcstart;
    }
    if (rcend != null) {
      query += "&rcend=" + rcend;
    }
    Api api = getQueryResult(query);
    handleError(api);
    List<Rc> rcList = api.getQuery().getRecentchanges();
    rcList = sortByTitleAndFilterDoubles(rcList);
    return rcList;
  }

  /**
   * sort the given List by title and filter double titles
   * 
   * @param rcList
   * @return
   */
  public List<Rc> sortByTitleAndFilterDoubles(List<Rc> rcList) {
    List<Rc> result = new ArrayList<Rc>();
    List<Rc> sorted = new ArrayList<Rc>();
    sorted.addAll(rcList);
    Collections.sort(sorted, new Comparator<Rc>() {
      @Override
      public int compare(Rc lRc, Rc rRc) {
        int result = lRc.getTitle().compareTo(rRc.getTitle());
        if (result == 0) {
          result = rRc.getTimestamp().compare(lRc.getTimestamp());
        }
        return result;
      }
    });
    Rc previous = null;
    for (Rc rc : sorted) {
      if (previous == null || (!rc.getTitle().equals(previous.getTitle()))) {
        result.add(rc);
      }
      previous = rc;
    }
    Collections.sort(result, new Comparator<Rc>() {
      @Override
      public int compare(Rc lRc, Rc rRc) {
        int result = rRc.getTimestamp().compare(lRc.getTimestamp());
        return result;
      }
    });
    return result;
  }

  /**
   * convert a data to a MediaWiki API timestamp
   * 
   * @param date
   * @return
   */
  public String dateToMWTimeStamp(Date date) {
    SimpleDateFormat mwTimeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String result = mwTimeStampFormat.format(date);
    return result;
  }

  /**
   * get the most recent changes
   * 
   * @param days
   * @param rcLimit
   * @return
   * @throws Exception
   */
  public List<Rc> getMostRecentChanges(int days, int rcLimit) throws Exception {
    Date today = new Date();
    Calendar cal = new GregorianCalendar();
    cal.setTime(today);
    cal.add(Calendar.DAY_OF_MONTH, -days);
    Date date30daysbefore = cal.getTime();
    String rcstart = dateToMWTimeStamp(today);
    String rcend = dateToMWTimeStamp(date30daysbefore);
    List<Rc> rcList = this.getRecentChanges(rcstart, rcend, rcLimit);
    List<Rc> result = this.sortByTitleAndFilterDoubles(rcList);
    return result;
  }

}
