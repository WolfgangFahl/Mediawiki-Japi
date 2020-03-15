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

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
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
  public static boolean ignoreCertificates = false;

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
      // return new X509Certificate[0];
    }
  }

  private WikiUser wikiUser;

  /**
   * initialize this wiki
   */
  public void init() throws Exception {
    // configure the SSLContext with a TrustManager
    SSLContext ctx = SSLContext.getInstance("TLS");
    if (ignoreCertificates) {
      ctx.init(new KeyManager[0],
          new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
      SSLContext.setDefault(ctx);
    }
    HostnameVerifier hv = new IgnoreHostName();
    HttpsURLConnection.setDefaultHostnameVerifier(hv);
  }

  /**
   * construct me from the given wiki user
   * 
   * @param wikiUser
   * @throws Exception
   */
  public SSLWiki(WikiUser wikiUser) throws Exception {
    Mediawiki.initLog4J();
    this.wikiUser = wikiUser;
    init();
    super.init(wikiUser.getUrl(), wikiUser.getScriptPath());
  }

  /**
   * create an SSLWiki from the given wikiId and user
   * 
   * @param wikiId
   * @param user
   * @return
   * @throws Exception
   */
  public static SSLWiki ofIdAndUser(String wikiId, String user)
      throws Exception {
    WikiUser wikiUser = WikiUser.getUser(wikiId, user);
    SSLWiki wiki = new SSLWiki(wikiUser);
    return wiki;
  }

  /**
   * create a wiki for the given wikiId
   * 
   * @param wikiId
   * @return
   * @throws Exception
   */
  public static SSLWiki ofId(String wikiId) throws Exception {
    String user = System.getProperty("user.name");
    return ofIdAndUser(wikiId, user);
  }

  /**
   * log me in with the configured user
   * 
   * @throws Exception
   */
  public Login login() throws Exception {
    WikiUser wuser=wikiUser;
    if (wuser == null) {
      throw new Exception(
          "user for " + wikiUser.getWikiid() + "(" + getSiteurl() + ") not configured");
    }
    // wiki.setDebug(true);
    try {
      Login login = login(wuser.getUsername(), wuser.getPassword());
      LOGGER.log(Level.INFO, this.siteurl + this.scriptPath + this.apiPath + ":"
          + login.getResult());
      if (!"Success".equals(login.getResult())) {
        throw new Exception("login for '" + wuser.getUsername() + "' at '"
            + wikiUser.getWikiid() + "(" + this.getSiteurl() + this.getScriptPath()
            + ")' failed: " + login.getResult());
      }
      return login;
    } catch (javax.net.ssl.SSLHandshakeException she) {
      String msg = "login via SSL  to " + this.getSiteurl() + " failed\n";
      msg += "Exception: " + she.getMessage();
      throw new Exception(msg);
    }
  }

}
