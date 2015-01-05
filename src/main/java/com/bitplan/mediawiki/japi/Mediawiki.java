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

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;

import javax.ws.rs.core.MediaType;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Edit;
import com.bitplan.mediawiki.japi.api.Error;
import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Login;
import com.bitplan.mediawiki.japi.api.Page;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

/**
 * access to Mediawiki api
 * 
 * @author wf
 *
 */
public class Mediawiki implements MediawikiApi {

	/**
	 *  current Version 
	 */
	protected static final String VERSION="0.0.2";
	
	/**
	 * if true main can be called without calling system.exit() when finished
	 */
	public static boolean testMode = false;
	
	/**
	 * see <a href='https://www.mediawiki.org/wiki/API:Main_page#Identifying_your_client'>Identifying your client:User-Agent</a>
	 */
	protected static final String USER_AGENT = "Mediawiki-Japi/"+VERSION+" (https://github.com/WolfgangFahl/Mediawiki-Japi; support@bitplan.com)";

	/**
	 * default script path
	 */
	public static final String DEFAULT_SCRIPTPATH = "/w";

	
	/**
	 * set to true if exceptions should be thrown on Error
	 */
	protected boolean throwExceptionOnError=true;

	/**
	 * Logging may be enabled by setting debug to true
	 */
	protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger("com.bitplan.mediawiki.japi");

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

	/**
	 * @return the throwExceptionOnError
	 */
	public boolean isThrowExceptionOnError() {
		return throwExceptionOnError;
	}

	/**
	 * @param throwExceptionOnError the throwExceptionOnError to set
	 */
	public void setThrowExceptionOnError(boolean throwExceptionOnError) {
		this.throwExceptionOnError = throwExceptionOnError;
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
	 */
	public Mediawiki() {
		this(null);
	}


	/**
	 * construct a Mediawiki for the given url using the default Script path 
	 * 
	 * @param siteurl - the url to use
	 */
	public Mediawiki(String siteurl) {
		this(siteurl,DEFAULT_SCRIPTPATH);
	}
	
	/**
	 * construct a Mediawiki for the given url and scriptpath
	 * @param siteurl - the url to use
	 * @param scriptpath - the scriptpath to use
	 */
	public Mediawiki(String siteurl, String scriptpath) {
		this.siteurl = siteurl;
		this.scriptPath=scriptpath;
		ApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
		config.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);
		client = ApacheHttpClient.create(config);
	}
	
	/**
	 * get a current IsoTimeStamp
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
	 * get the result for the given action and aprams
	 * 
	 * @param action
	 * @param params
	 * @return the API result for the action
	 * @throws Exception
	 */
	public Api getActionResult(String action, String params) throws Exception {
		String queryUrl = siteurl + scriptPath + apiPath + "&action=" + action
				+ params + "&format=" + format;
		if (debug)
			LOGGER.log(Level.INFO, queryUrl);
		WebResource resource = client.resource(queryUrl);
		String xml;
		// decide for the method to use for api access
		if ("login".equals(action)) {
			xml = resource.header("USER-AGENT", USER_AGENT).post(String.class); 
		} else if ("edit".equals(action)) {
			xml = resource.header("USER-AGENT", USER_AGENT).type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class); 
		} else {
			xml = resource.header("USER-AGENT", USER_AGENT).get(String.class);
		}
		if (debug) {
			// convert the xml to a more readable format
			String xmlDebug = xml.replace(">", ">\n");
			LOGGER.log(Level.INFO, xmlDebug);
		}
		// retrieve the JAXB wrapper representation from the xml received
		Api api = Api.fromXML(xml);
		// check whether an error code was sent
		Error error = api.getError();
		// if there is an error - handle it
		if (error!=null) {
			// prepare the error message
			String errMsg="error code="+error.getCode()+" info:'"+error.getInfo()+"'";
			// log it
			LOGGER.log(Level.SEVERE,errMsg);
			// and throw an error if this is configured
			if (this.isThrowExceptionOnError()) {
				throw new Exception(errMsg);
			}
		}
		return api;
	}

	/**
	 * get the Result for the given query
	 * 
	 * @param query
	 * @return the API result for the query
	 * @throws Exception 
	 */
	public Api getQueryResult(String query) throws Exception {
		Api result = this.getActionResult("query", query);
		return result;
	}
	
	/**
	 * request parameter encoding
	 * @param param
	 * @return an encoded url parameter
	 */
	protected String encode(String param) {
		@SuppressWarnings("deprecation")
		String result=URLEncoder.encode(param);
		return result;
	}

	/**
	 * normalize the given page title
	 * @param title
	 * @return the normalized title e.g. replacing blanks
	 * FIXME encode is not good enough
	 */
	protected String normalize(String title) {
		String result=encode(title);
		return result;
	}
	
	/**
	 * get the general siteinfo
	 * @return the siteinfo
	 * @throws Exception 
	 */
	public General getSiteInfo() throws Exception {
		if (siteinfo==null) {
			Api api = getQueryResult("&meta=siteinfo");
			siteinfo=api.getQuery().getGeneral();
		}
		return siteinfo;
	}

	/**
	 * get the Version of this wiki
	 * @throws Exception 
	 */
	public String getVersion() throws Exception {
		if (mediawikiVersion==null) {
			General lGeneral=getSiteInfo();
			mediawikiVersion=lGeneral.getGenerator();
		}
		return mediawikiVersion;
	}
	
	// login implementation
	public Login login(String username, String password) throws Exception {
		username=encode(username);
		password=encode(password);
		Api apiResult = getActionResult("login", "&lgname=" + username
				+ "&lgpassword=" + password);
		Login login = apiResult.getLogin();
		String token = login.getToken();
		apiResult = getActionResult("login", "&lgname=" + username + "&lgpassword="
				+ password + "&lgtoken=" + token);
		login= apiResult.getLogin();
		return login;
	}
	
	/**
	 * end the session
	 * @throws Exception 
	 */
	public void logout() throws Exception {
		Api apiResult=getActionResult("logout","");
		if (apiResult!=null) {
			// FIXME check apiResult			
		}
		if (cookies!=null) {
			cookies.clear();
			cookies=null;
		}
	}

	/**
	 * get the page Content for the given page Title
	 * @param pageTitle
	 * @return the page Content
	 * @throws Exception 
	 */
	public String getPageContent(String pageTitle) throws Exception {
		Api api = getQueryResult("&prop=revisions&rvprop=content&titles="+normalize(pageTitle));
		Page page=api.getQuery().getPages().get(0);
		String content=page.getRevisions().get(0).getValue();	
		return content;
	}
	
	enum TokenMode { token1_19, token1_20_23,token1_24  }
	/**
	 * get an edit token for the given page Title
	 * @param pageTitle
	 * @return the edit token for the page title
	 * @throws Exception 
	 */
	public String getEditToken(String pageTitle) throws Exception {
		pageTitle=normalize(pageTitle);
		String editversion = "";
		String action = "query";
		String params = "&meta=tokens";
		TokenMode tokenMode;
		if (getVersion().compareToIgnoreCase("Mediawiki 1.24") >= 0) {
			editversion = "Versions 1.24 and later";
			tokenMode=TokenMode.token1_24;
		} else if (getVersion().compareToIgnoreCase("Mediawiki 1.20") >= 0) {
			editversion = "Versions 1.20-1.23";
			tokenMode=TokenMode.token1_20_23;
			action = "tokens";
			params = "";
		} else {
			editversion = "Version 1.19 and earlier";
			tokenMode=TokenMode.token1_19;
			params = "&prop=info&7Crevisions&intoken=edit&titles="+pageTitle;
		}
		if (debug) {
			LOGGER.log(Level.INFO,
					"handling edit for wiki version " + getVersion() + " as "
							+ editversion + " with action=" + action + params);
		}
		Api api = getActionResult(action, params);
		String token=null;
		switch (tokenMode) {
		  case token1_19:
		  	token=api.getQuery().getPages().get(0).getEdittoken();
			break;
		  case token1_20_23:
		  	token=api.getTokens().getEdittoken();
		default:
			break;
		}
		return token;
	}
	
	/**
	 * https://www.mediawiki.org/wiki/API:Edit
	 */
	@Override
	public Edit edit(String pagetitle, String text, String summary)
			throws Exception {
		String token=getEditToken(pagetitle);
		String params="&title="+encode(pagetitle)+
				"&text="+encode(text)+
				"&summary="+encode(summary)+
				"&token="+encode(token);
		Api api = this.getActionResult("edit", params);
		Edit result=api.getEdit();
		return result;
	}

	/**
	 * handle the given Throwable (in commandline mode)
	 * 
	 * @param t
	 */
	public void handle(Throwable t) {
		System.out.flush();
		System.err.println(t.getClass().getSimpleName()+":"+t.getMessage());
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
		System.err
				.println("  usage: java com.bitplan.mediawiki.japi.Mediawiki");
		parser.printUsage(System.err);
		exitCode = 1;
	}
	
	/**
	 * show Help
	 */
	public void showHelp() {
		String msg="Help\n"+"Mediawiki-Japi version "+VERSION+" has no functional command line interface\n"
		+"Please visit http://mediawiki-japi.bitplan.com for usage instructions";
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
	 * main instance - this is the non-static version of main - it will
	 * run as a static main would but return it's exitCode to the static main
	 * the static main will then decide whether to do a System.exit(exitCode) or not.
	 * @param args - command line arguments
	 * @return - the exit Code to be used by the static main program
	 */
	protected int maininstance(String[] args) {
		parser = new CmdLineParser(this);
		try {
			parser.parseArgument(args);
			if (debug)
				showVersion();
			if (this.showVersion)  {
				showVersion();
			} else if (this.showHelp) {
				showHelp();
			} else {
				// FIXME - do something
				// implement actions
				System.err.println("Commandline interface is not functional in "+VERSION+" yet");
				exitCode=1;
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
	 * entry point e.g. for java -jar called
	 * provides a command line interface
	 * @param args
	 */
	public static void main(String args[]) {
		Mediawiki wiki=new Mediawiki();
		int result = wiki.maininstance(args);
		if (!testMode && result != 0)
			System.exit(result);
	}

}
