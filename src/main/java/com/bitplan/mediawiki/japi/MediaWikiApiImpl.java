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

import java.net.URLEncoder;
import java.util.logging.Level;

import org.apache.commons.lang3.StringEscapeUtils;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Edit;
import com.bitplan.mediawiki.japi.api.Error;
import com.bitplan.mediawiki.japi.api.Ii;
import com.bitplan.mediawiki.japi.api.Tokens;
import com.bitplan.mediawiki.japi.api.Warnings;

/**
 * common implementation parts
 * @author wf
 *
 */
public abstract class MediaWikiApiImpl implements MediawikiApi {
  
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

  private String mediawikiVersion;
  
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
    if (api==null) {
      String errMsg="api result is null";
      handleError(errMsg);
    }
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
  
  // we do not implement this ... 
  public abstract SiteInfo getSiteInfo() throws Exception;
  
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
    SiteInfo siteinfo = this.getSiteInfo();
    SiteInfo targetSiteInfo = targetWiki.getSiteInfo();
    PageInfo sourcePageInfo=new PageInfo(pageTitle,siteinfo);
    String targetPageTitle=pageTitle;
    Edit result=null;
    String nameSpaceName="";
    if (sourcePageInfo.namespace!=null) {
      nameSpaceName=sourcePageInfo.nameSpaceName;
      String targetNameSpace=siteinfo.mapNamespace(nameSpaceName, targetSiteInfo);
      targetPageTitle=pageTitle.replaceFirst(nameSpaceName+":",targetNameSpace+":");
    }
    String content = getPageContent(pageTitle);
    // "File:" namespace (ID: 6) used see http://www.mediawiki.org/wiki/Manual:Namespace#Built-in_namespaces
    if (sourcePageInfo.namespaceId==6) {
      // get the image information 
      Ii ii = this.getImageInfo(pageTitle);
      String filename=pageTitle.replaceFirst("File:","");
      if (nameSpaceName!=null) {
        filename=filename.replaceFirst(nameSpaceName+":", "");
      }
      targetWiki.upload(ii,filename,content);
      result=new Edit();
      result.setTitle(filename);
    } else {
      result = targetWiki.edit(targetPageTitle, content, summary);
    }
    return result;
  }
  
  /**
   * get the Version of this wiki
   * 
   * @throws Exception
   */
  public String getVersion() throws Exception {
    // is there a cached value of the mediaWikiVersion 
    if (mediawikiVersion==null) {
      // get the version (just once)
      mediawikiVersion=getSiteInfo().getVersion();
    }
    return mediawikiVersion;
  }
  
  /**
   * set the version via API
   * @param version
   */
  public void setVersion(String version) {
    if (mediawikiVersion==null) {
      mediawikiVersion=version;
    } else {
      throw new IllegalStateException("version "+mediawikiVersion+" already set");
    }
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
   * decode the given html markup
   * @param html
   * @return decoded html
   * @throws Exception
   */
  protected String decode(String html) throws Exception {
    String result=StringEscapeUtils.unescapeHtml4(html);
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
  public String normalizeTitle(String title) throws Exception {
    String result = encode(title);
    result=result.replace("+","_");
    return result;
  }
}
