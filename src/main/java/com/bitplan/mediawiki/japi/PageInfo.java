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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bitplan.mediawiki.japi.api.Ns;

/**
 * helper class for canonical Page Titles
 */
public class PageInfo {
  int namespaceId=-999;
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
    this.siteinfo = siteinfo;
    nameSpaceName=getNameSpaceName(pageTitle);
    this.lang=siteinfo.getLang();
    namespace=null;
    if (nameSpaceName!=null) {
      namespace =siteinfo.getNamespaces().get(nameSpaceName);
      if (namespace==null) {
        namespace = siteinfo.getNamespacesByCanonicalName().get(nameSpaceName);        
      }
      String canonical=namespace.getCanonical();
      this.canonicalPageTitle=pageTitle.replaceFirst(nameSpaceName+":",canonical+":");
      namespaceId=namespace.getId();
    }
  }
}