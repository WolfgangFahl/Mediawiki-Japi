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

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import com.bitplan.mediawiki.japi.Mediawiki;
import com.bitplan.mediawiki.japi.api.Login;
import com.bitplan.mediawiki.japi.user.WikiUser;

/**
 * an SSL wiki
 * 
 * @author wf http://stackoverflow.com/questions/2703161/how-to-ignore-ssl-
 *         certificate -errors-in-apache-httpclient-4-0
 */
public class SSLWiki extends Mediawiki {
  

	/**
	 * avoid ssl issue with hostname check
	 * 
	 * @author wf
	 *
	 */
	public static class IgnoreHostName implements HostnameVerifier {
		boolean debug = false;
		protected static Logger LOGGER = Logger.getLogger("org.sidif.wiki");

		@Override
		public boolean verify(String hostname, SSLSession sslSession) {
			if (debug) {
				LOGGER.log(Level.INFO, "verifying hostname " + hostname
						+ " with IgnoreHostName - will return true");
			}
			return true;
		}

	}

	/**
	 * modifies the trustmanager
	 * 
	 * @author wf FIXME - move else where
	 */
	private static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] certificates,
				String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] certificates,
				String authType) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	private String wikiid;

	/**
	 * @return the wikiid
	 */
	public String getWikiid() {
		return wikiid;
	}

	/**
	 * @param wikiid
	 *          the wikiid to set
	 */
	public void setWikiid(String wikiid) {
		this.wikiid = wikiid;
	}

	/**
	 * initialize this wiki
	 */
	public void init() throws Exception {
		// configure the SSLContext with a TrustManager
		SSLContext ctx = SSLContext.getInstance("TLS");
		// ctx.init(new KeyManager[0], new TrustManager[] { new
		// DefaultTrustManager() }, new SecureRandom());
		// SSLContext.setDefault(ctx);
		HostnameVerifier hv = new IgnoreHostName();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}

	@Override
	public void init(String siteurl, String scriptpath) throws Exception {
		// make httpclient shut up see http://stackoverflow.com/a/15798443/1497139
		org.apache.log4j.Logger.getLogger("org.apache.commons.httpclient")
				.setLevel(org.apache.log4j.Level.ERROR);
		org.apache.log4j.Logger.getLogger("httpclient.wire.header").setLevel(
				org.apache.log4j.Level.WARN);
		org.apache.log4j.Logger.getLogger("httpclient.wire.content").setLevel(
				org.apache.log4j.Level.WARN);
		java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(
				java.util.logging.Level.FINEST);
		java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(
				java.util.logging.Level.FINEST);
		super.init(siteurl, scriptpath);
	}

	/**
	 * constructor
	 * 
	 * @param url
	 * @throws Exception
	 */
	public SSLWiki(String url) throws Exception {
		super(url);
		init();
	}

	/**
	 * construct me from an url and scriptPath
	 * 
	 * @param url
	 * @param scriptPath
	 * @throws Exception
	 */
	public SSLWiki(String url, String scriptPath) throws Exception {
		super(url, scriptPath);
		init();
	}

	/**
	 * constructor with three params
	 * 
	 * @param url
	 * @param scriptPath
	 * @param wikiid
	 * @throws Exception
	 */
	public SSLWiki(String url, String scriptPath, String wikiid) throws Exception {
		super(url, scriptPath);
		this.wikiid = wikiid;
		init();
	}

	/**
	 * log me in with the configured user
	 * 
	 * @throws Exception
	 */
	public void login() throws Exception {
		WikiUser wuser = WikiUser.getUser(getWikiid(), getSiteurl());
		if (wuser == null) {
			throw new Exception("user for " + getWikiid() + "("+getSiteurl()+") not configured");
		}
		// wiki.setDebug(true);
		try {
			Login login = login(wuser.getUsername(), wuser.getPassword());
			LOGGER.log(Level.INFO, this.siteurl+this.scriptPath+this.apiPath+":"+login.getResult());
			if (!"Success".equals(login.getResult())) {
			  throw new Exception("login for '"+wuser.getUsername()+"' at '"+getWikiid()+"("+this.getSiteurl()+this.getScriptPath()+")' failed: "+login.getResult());
			}
		} catch (javax.net.ssl.SSLHandshakeException she) {
			String msg="login via SSL  to "+this.getSiteurl()+" failed\n";
			msg+="Exception: "+she.getMessage();
			throw new Exception(msg);
		}
	}

  
}
