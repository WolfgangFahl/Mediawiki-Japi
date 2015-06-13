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

import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Edit;
import com.bitplan.mediawiki.japi.api.Error;
import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Ii;
import com.bitplan.mediawiki.japi.api.Ns;
import com.bitplan.mediawiki.japi.api.Tokens;
import com.bitplan.mediawiki.japi.api.Warnings;

/**
 * common implementation parts
 * @author wf
 *
 */
public abstract class MediaWikiApiImpl implements MediawikiApi {

  protected General siteinfo;
  protected Map<String,Ns> namespaces;
  protected Map<Integer,Ns> namespacesById;
  
  /**
   * Logging may be enabled by setting debug to true
   */
  protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
      .getLogger("com.bitplan.mediawiki.japi");

  /**
   * set to true if exceptions should be thrown on Error
   */
  protected boolean throwExceptionOnError = true;
  
  /**
   * protection Marker - if this shows in  page edits are suppressed and logged with a warning
   */
  protected String protectionMarker="<!-- This page is protected against edits by Mediawiki-Japi -->";
  
  /**
   * @return the throwExceptionOnError
   */
  public boolean isThrowExceptionOnError() {
    return throwExceptionOnError;
  }

  /**
   * @param throwExceptionOnError
   *          the throwExceptionOnError to set
   */
  public void setThrowExceptionOnError(boolean throwExceptionOnError) {
    this.throwExceptionOnError = throwExceptionOnError;
  }
  
  /**
   * @return the protectionMarker
   */
  public String getProtectionMarker() {
    return protectionMarker;
  }

  /**
   * @param protectionMarker the protectionMarker to set
   */
  public void setProtectionMarker(String protectionMarker) {
    this.protectionMarker = protectionMarker;
  }
  
  /**
   * handle the given error Message according to the exception setting
   * 
   * @param errMsg
   * @throws Exception
   */
  protected void handleError(String errMsg) throws Exception {
    // log it
    LOGGER.log(Level.SEVERE, errMsg);
    // and throw an error if this is configured
    if (this.isThrowExceptionOnError()) {
      throw new Exception(errMsg);
    }
  }
  
  /**
   * handle the given api error
   * @param error
   * @throws Exception
   */
  protected void handleError(Error error) throws Exception {
    String errMsg="error: "+error.getCode()+" info: "+error.getInfo();
    handleError(errMsg);
  }
  
  /**
   * 
   * @param api
   * @throws Exception
   */
  protected void handleError(Api api) throws Exception {
    if (api.getError() != null) {
      this.handleError(api.getError());
    }
    if (api.getWarnings() != null) {
      Warnings warnings = api.getWarnings();
      if (warnings.getTokens() != null) {
        Tokens warningTokens = warnings.getTokens();
        String errMsg = warningTokens.getValue();
        handleError(errMsg);
      }
    }
  }
  
  /**
   * return Api from the given xml
   * @param xml - the xml go unmarshal
   * @return
   * @throws Exception
   */
  public Api fromXML(String xml) throws Exception {
    // retrieve the JAXB wrapper representation from the xml received
    Api api = Api.fromXML(xml);
    // check whether an error code was sent
    Error error = api.getError();
    // if there is an error - handle it
    if (error != null) {
      // prepare the error message
      String errMsg = "error code=" + error.getCode() + " info:'"
          + error.getInfo() + "'";
      this.handleError(errMsg);
    }
    return api;
  }
  
  /**
   * get the Namespaces for this wiki
   * @return
   * @throws Exception
   */
  public Map<String,Ns> getNamespaces() throws Exception {
    if (namespaces==null) {
      getSiteInfo();
    }
    return namespaces;
  }
  
  /**
   * get the Namespaces for this wiki
   * @return
   * @throws Exception
   */
  public Map<Integer,Ns> getNamespacesById() throws Exception {
    if (namespacesById==null) {
      getSiteInfo();
    }
    return namespacesById;
  }
  
  /**
   * map the given namespace to the target wiki
   * @param ns
   * @param targetWiki
   * @return the namespace name for the target wiki
   * @throws Exception 
   */
  public String mapNamespace(String ns, MediawikiApi targetWiki) throws Exception {
    Map<String, Ns> sourceMap = this.getNamespaces();
    Map<Integer, Ns> targetMap = targetWiki.getNamespacesById();
    Ns sourceNs = sourceMap.get(ns);
    if (sourceNs==null) {
      LOGGER.log(Level.WARNING,"can not map unknown namespace "+ns);
      return ns;
    }
    Ns targetNs=targetMap.get(sourceNs.getId());
    if (targetNs==null) {
      LOGGER.log(Level.WARNING,"missing namespace "+sourceNs.getValue()+" id:"+sourceNs.getId()+" canonical:"+sourceNs.getCanonical());
      return ns;
    }
    return targetNs.getValue();
  }
  
  /**
   * copy the page for a given title from this wiki to the given target Wiki
   * uses https://www.mediawiki.org/wiki/API:Edit FIXME - make this an API
   * interface function FIXME - create a multi title version
   * 
   * @param targetWiki
   *          - the other wiki (could use a different API implementation ...)
   * @param pageTitle
   *          - the title of the page to copy
   * @param summary
   *          - the summary to use
   * @return - the Edit result
   * @throws Exception
   */
  public Edit copyToWiki(MediawikiApi targetWiki, String pageTitle,
      String summary) throws Exception {
    String sourceLang=this.getSiteInfo().getLang();
    String targetLang=targetWiki.getSiteInfo().getLang();
    if (isDebug())
      LOGGER.log(Level.INFO,"sourceLang:"+sourceLang+" targetLang:"+targetLang);
    Ns namespace=null;
    String nameSpaceName=getNameSpaceName(pageTitle);
    String targetPageTitle=pageTitle;
    Edit result=null;
    if (nameSpaceName!=null) {
      namespace = this.getNamespaces().get(nameSpaceName);
    }
    if (namespace!=null) {
      String targetNameSpace=this.mapNamespace(nameSpaceName, targetWiki);
      targetPageTitle=pageTitle.replaceFirst(nameSpaceName+":",targetNameSpace+":");
    }
    String content = getPageContent(pageTitle);
       // File: namespace used see http://www.mediawiki.org/wiki/Manual:Namespace#Built-in_namespaces
    if (namespace!=null && namespace.getId()==6) {
      // get the image information 
      Ii ii = this.getImageInfo(pageTitle);
      String filename=pageTitle.replaceFirst("File:","");
      if (nameSpaceName!=null) {
        filename=filename.replaceFirst(nameSpaceName+":", "");
      }
      targetWiki.upload(ii,filename,content);
    } else {
      result = targetWiki.edit(targetPageTitle, content, summary);
    }
    return result;
  }
  
  // try with http://regexpal.com/
  
  public static final String NAMESPACE_REGEX="([^:]*):";
  public static final Pattern namespacePattern=Pattern.compile(NAMESPACE_REGEX);

  /**
   * get the name space name
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

}
