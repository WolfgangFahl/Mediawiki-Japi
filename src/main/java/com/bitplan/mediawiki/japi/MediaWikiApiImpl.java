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

import java.util.logging.Level;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.api.Edit;
import com.bitplan.mediawiki.japi.api.Error;

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
    String content = getPageContent(pageTitle);
    Edit result = targetWiki.edit(pageTitle, content, summary);
    return result;
  }
}
