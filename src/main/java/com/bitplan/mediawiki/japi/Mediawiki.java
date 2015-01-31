/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/WolfgangFahl/Mediawiki-Japi
 * and the license for Mediawiki-Japi applies
 * 
 */
package com.bitplan.mediawiki.japi;

import java.io.File;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.bitplan.mediawiki.japi.api.Edit;
import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Login;
import com.bitplan.mediawiki.japi.api.P;
import com.bitplan.mediawiki.japi.api.Page;
import com.bitplan.mediawiki.japi.api.Tokens;
import com.bitplan.mediawiki.japi.api.Warnings;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

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
  protected static final String VERSION = "0.0.3";

  /**
   * if true main can be called without calling system.exit() when finished
   */
  public static boolean testMode = false;

  /**
   * see <a href=
   * 'https://www.mediawiki.org/wiki/API:Main_page#Identifying_your_client'>Iden
   * t i f y i n g your client:User-Agent</a>
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
  protected String mediawikiVersion;
  protected General siteinfo;

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
   *          - the form data
   * @return - the client Response
   * @throws Exception
   */
  public ClientResponse getPostResponse(String queryUrl, String params,
      TokenResult token, Map<String, String> pFormData) throws Exception {
    MultivaluedMap<String, String> lFormData = new MultivaluedMapImpl();
    if (pFormData != null) {
      for (String key : pFormData.keySet()) {
        lFormData.add(key, pFormData.get(key));
      }
    }
    if (token != null) {
      switch (token.tokenMode) {
      case token1_24:
        lFormData.add(token.tokenName, token.token);
        break;
      default:
        params += token.asParam();
      }
    }
    Builder resource = getResource(queryUrl + params);
    // FIXME allow to specify contenttype (not needed for Mediawiki itself but
    // could be good for interfacing )
    ClientResponse response = resource.type(
        MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,
        lFormData);
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
      response=resource.put(ClientResponse.class);
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
      handleError("status "+response.getStatus()+":'"+responseText+"'");
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
      Map<String, String> pFormData) throws Exception {
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
   * request parameter encoding
   * 
   * @param param
   * @return an encoded url parameter
   * @throws Exception
   */
  protected String encode(String param) throws Exception {
    String result = URLEncoder.encode(param, "UTF-8");
    return result;
  }

  /**
   * normalize the given page title
   * 
   * @param title
   * @return the normalized title e.g. replacing blanks FIXME encode is not good
   *         enough
   * @throws Exception
   */
  protected String normalize(String title) throws Exception {
    String result = encode(title);
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
      titles = titles + delim + normalize(title);
      delim = "%7C";
    }
    return titles;
  }

  /**
   * get the general siteinfo
   * 
   * @return the siteinfo
   * @throws Exception
   */
  public General getSiteInfo() throws Exception {
    if (siteinfo == null) {
      Api api = getQueryResult("&meta=siteinfo");
      siteinfo = api.getQuery().getGeneral();
    }
    return siteinfo;
  }

  /**
   * get the Version of this wiki
   * 
   * @throws Exception
   */
  public String getVersion() throws Exception {
    if (mediawikiVersion == null) {
      General lGeneral = getSiteInfo();
      mediawikiVersion = lGeneral.getGenerator();
    }
    return mediawikiVersion;
  }

  // login implementation
  public Login login(String username, String password) throws Exception {
    username = encode(username);
    password = encode(password);
    Api apiResult = getActionResult("login", "&lgname=" + username
        + "&lgpassword=" + password, null, null);
    Login login = apiResult.getLogin();
    TokenResult token = new TokenResult();
    token.token = login.getToken();
    token.tokenName = "lgtoken";
    token.tokenMode = TokenMode.token1_19;
    apiResult = getActionResult("login", "&lgname=" + username + "&lgpassword="
        + password, token, null);
    login = apiResult.getLogin();
    return login;
  }

  /**
   * end the session
   * 
   * @throws Exception
   */
  public void logout() throws Exception {
    Api apiResult = getActionResult("logout", "", null, null);
    if (apiResult != null) {
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
   * @return the page Content
   * @throws Exception
   */
  public String getPageContent(String pageTitle) throws Exception {
    Api api = getQueryResult("&prop=revisions&rvprop=content&titles="
        + normalize(pageTitle));
    Page page = api.getQuery().getPages().get(0);
    String content = null;
    if (page != null) {
      if (page.getRevisions().size() > 0) {
        content = page.getRevisions().get(0).getValue();
      }
    } else {
      String errMsg = "pageTitle '" + pageTitle + "' not found";
      // log it
      LOGGER.log(Level.SEVERE, errMsg);
      // and throw an error if this is configured
      if (this.isThrowExceptionOnError()) {
        throw new Exception(errMsg);
      }
    }
    return content;
  }

  /**
   * get a list of pages for the given titles see <a
   * href='http://www.mediawiki.org/wiki/API:Query'>API:Query</a>
   * 
   * @param titleList
   * @return the list of pages retrieved
   * @throws Exception
   * 
   *           FIXME should be part of the Java Interface
   */
  public List<Page> getPages(List<String> titleList) throws Exception {
    String titles = this.getTitles(titleList);
    Api api = getQueryResult("&titles=" + titles
        + "&prop=revisions&rvprop=content");
    List<Page> pages = api.getQuery().getPages();
    return pages;
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
     * +\ are handled correctly see <a href=
     * 'https://www.mediawiki.org/wiki/Manual:Edit_token'>Manual:Edit_token</a>
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
   * get an edit token for the given page Title see <a
   * href='https://www.mediawiki.org/wiki/API:Tokens'>API:Tokens</a>
   * 
   * @param pageTitle
   * @return the edit token for the page title
   * @throws Exception
   */
  public TokenResult getEditToken(String pageTitle) throws Exception {
    pageTitle = normalize(pageTitle);
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
      params = "&type=edit";
    } else {
      editversion = "Version 1.19 and earlier";
      tokenMode = TokenMode.token1_19;
      params = "&prop=info&7Crevisions&intoken=edit&titles=" + pageTitle;
    }
    if (debug) {
      LOGGER.log(Level.INFO, "handling edit for wiki version " + getVersion()
          + " as " + editversion + " with action=" + action + params);
    }
    Api api = getActionResult(action, params);
    if (api.getWarnings() != null) {
      Warnings warnings = api.getWarnings();
      if (warnings.getTokens() != null) {
        Tokens warningTokens = warnings.getTokens();
        String errMsg = warningTokens.getValue();
        handleError(errMsg);
      }
    }
    TokenResult token = new TokenResult();
    token.tokenMode = tokenMode;
    token.tokenName = "token";
    switch (tokenMode) {
    case token1_19:
      token.setToken(api.getQuery().getPages().get(0).getEdittoken());
      break;
    case token1_20_23:
      token.setToken(api.getTokens().getEdittoken());
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
    Edit result = new Edit();
    String pageContent = getPageContent(pageTitle);
    if (pageContent != null && pageContent.contains(protectionMarker)) {
      LOGGER.log(Level.WARNING, "page " + pageTitle + " is protected!");
    } else {
      TokenResult token = getEditToken(pageTitle);
      Map<String, String> lFormData = new HashMap<String, String>();
      lFormData.put("text", text);
      lFormData.put("title", pageTitle);
      lFormData.put("summary", summary);
      String params = "";
      Api api = this.getActionResult("edit", params, token, lFormData);
      result = api.getEdit();
    }
    return result;
  }

  /**
   * https://www.mediawiki.org/wiki/API:Upload
   */
  @Override
  public void upload(File file, String filename, String contents, String reason)
      throws Exception {

  }
  
  /**
   * getAllPages
   * @param apfrom - may be null or empty
   * @param aplimit
   * @return
   * @throws Exception 
   */
  public List<P> getAllPages(String apfrom,int aplimit) throws Exception {   
    String query="&list=allpages";
    if (apfrom!=null && !apfrom.trim().equals("")) {
      query+="&apfrom="+apfrom;
    }
    query+="&aplimit="+aplimit;
    Api api = getQueryResult(query);
    List<P> pageRefList = api.getQuery().getAllpages();
    return pageRefList;
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

}
