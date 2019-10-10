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

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bitplan.mediawiki.japi.api.Ns;

/**
 * helper class for canonical Page Titles
 */
public class PageInfo {
  /**
   * Logging may be enabled by setting debug to true
   */
  protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
      .getLogger("com.bitplan.mediawiki.japi");

  public static boolean debug = false;

  int namespaceId = -999;
  String lang;
  String pageTitle;
  String canonicalPageTitle;
  Ns namespace;
  String nameSpaceName;
  SiteInfo siteinfo;

  // try with http://regexpal.com/

  public static final String NAMESPACE_REGEX = "([^:]*):";
  public static final Pattern namespacePattern = Pattern
      .compile(NAMESPACE_REGEX);

  /**
   * @return the namespaceId
   */
  public int getNamespaceId() {
    return namespaceId;
  }

  /**
   * @param namespaceId
   *          the namespaceId to set
   */
  public void setNamespaceId(int namespaceId) {
    this.namespaceId = namespaceId;
  }

  /**
   * @return the lang
   */
  public String getLang() {
    return lang;
  }

  /**
   * @param lang
   *          the lang to set
   */
  public void setLang(String lang) {
    this.lang = lang;
  }

  /**
   * @return the pageTitle
   */
  public String getPageTitle() {
    return pageTitle;
  }

  /**
   * @param pageTitle
   *          the pageTitle to set
   */
  public void setPageTitle(String pageTitle) {
    this.pageTitle = pageTitle;
  }

  /**
   * @return the canonicalPageTitle
   */
  public String getCanonicalPageTitle() {
    return canonicalPageTitle;
  }

  /**
   * @param canonicalPageTitle
   *          the canonicalPageTitle to set
   */
  public void setCanonicalPageTitle(String canonicalPageTitle) {
    this.canonicalPageTitle = canonicalPageTitle;
  }

  /**
   * @return the namespace
   */
  public Ns getNamespace() {
    return namespace;
  }

  /**
   * @param namespace
   *          the namespace to set
   */
  public void setNamespace(Ns namespace) {
    this.namespace = namespace;
  }

  /**
   * @return the nameSpaceName
   */
  public String getNameSpaceName() {
    return nameSpaceName;
  }

  /**
   * @param nameSpaceName
   *          the nameSpaceName to set
   */
  public void setNameSpaceName(String nameSpaceName) {
    this.nameSpaceName = nameSpaceName;
  }

  /**
   * get the name space name
   * 
   * @param pageTitle
   * @return
   */
  public static String getNameSpaceName(String pageTitle) {
    Matcher matcher = namespacePattern.matcher(pageTitle);
    if (matcher.find()) {
      // LOGGER.log(Level.INFO,pageTitle);
      return matcher.group(1);
    }
    return null;
  }

  /**
   * construct me from a localized pageTitle in a given language
   * 
   * @param pageTitle
   * @param siteinfo
   * @throws Exception
   */
  public PageInfo(String pageTitle, SiteInfo siteinfo) throws Exception {
    this.pageTitle = pageTitle;
    this.canonicalPageTitle = pageTitle;
    this.siteinfo = siteinfo;
    nameSpaceName = getNameSpaceName(pageTitle);
    this.lang = siteinfo.getLang();
    namespace = null;
    // do we have to map a namespace?
    if (nameSpaceName != null) {
      // get the namespace
      namespace = siteinfo.getNamespaces().get(nameSpaceName);
      if (namespace == null) {
        String lookupName=nameSpaceName;
        // get it from the canonical namespaces
        namespace = siteinfo.getNamespacesByCanonicalName().get(lookupName);
      }
      // ok we found one
      if (namespace != null) {
        String canonical = namespace.getCanonical();
        this.canonicalPageTitle = pageTitle.replaceFirst(nameSpaceName + ":",
            canonical + ":");
        namespaceId = namespace.getId();
      } else {
        if (debug) {
          LOGGER.log(Level.WARNING, "namespace for namespacename '"
              + nameSpaceName + "' not found");
          for (Ns ns : siteinfo.getNamespacesByCanonicalName().values()) {
            LOGGER.log(Level.WARNING, ns.getValue() + "->" + ns.getCanonical());
          }
        }
      }
    }
    // normalize the title
    this.canonicalPageTitle = this.canonicalPageTitle.replace(" ", "_"); 
  }

  /**
   * get the dual version of the pageTitle
   * @return
   */
  public String dual() {
     String result=("'"+pageTitle+"' canonical '"+this.canonicalPageTitle+"'");
     return result;
  }
}