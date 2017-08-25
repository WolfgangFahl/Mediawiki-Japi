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
import java.util.Calendar;
import java.util.List;

import com.bitplan.mediawiki.japi.api.Bl;
import com.bitplan.mediawiki.japi.api.Delete;
import com.bitplan.mediawiki.japi.api.Edit;
import com.bitplan.mediawiki.japi.api.Ii;
import com.bitplan.mediawiki.japi.api.Im;
import com.bitplan.mediawiki.japi.api.Img;
import com.bitplan.mediawiki.japi.api.Iu;
import com.bitplan.mediawiki.japi.api.Login;
import com.bitplan.mediawiki.japi.api.P;
import com.bitplan.mediawiki.japi.api.S;

/**
 * Mediawiki API Interface see <a
 * href="https://www.mediawiki.org/wiki/API:Main_page"
 * >https://www.mediawiki.org/wiki/API:Main_page</a>
 * 
 * @author wf
 *
 */
public interface MediawikiApi {
  /**
   * @return the siteurl
   */
  public String getSiteurl();

  /**
   * set the siteurl
   * 
   * @param siteurl
   * @throws Exception
   *           - if the url is not o.k.
   */
  public void setSiteurl(String siteurl) throws Exception;

  /**
   * set the scriptPath of this Wiki
   * 
   * @param scriptPath
   *          - the scriptPath to set
   */
  public void setScriptPath(String scriptPath);

  /**
   * get the script Path
   * 
   * @return
   */
  public String getScriptPath();

  /**
   * get the Version of this Mediawiki
   * 
   * @return the the version string
   * @throws Exception
   */
  public String getVersion() throws Exception;

  /**
   * get the general siteinfo
   * 
   * @return the siteinfo
   * @throws Exception
   */
  public SiteInfo getSiteInfo() throws Exception;

  /**
   * get the Image Info for the given pageTitle
   * 
   * @param pageTitle
   *          - the pageTitle to get the ImageInfo for
   * @return - the Image Info
   * @throws Exception
   */
  public Ii getImageInfo(String pageTitle) throws Exception;

  /**
   * overrideable method to do pre setup stuff
   * 
   * @param siteurl
   *          - the url to use
   * @param scriptPath
   *          - the script path to use
   * @throws Exception
   */
  public void init(String siteurl, String scriptPath) throws Exception;

  /**
   * login the given user with the given password
   * 
   * See <a href="https://www.mediawiki.org/wiki/API:Login">API:Login</a>
   * 
   * @param username
   * @param password
   * @return the Login information as returned by the API
   * @throws Exception
   * @since 0.0.1
   */
  public Login login(String username, String password) throws Exception;

  /**
   * check whether there is a User logged In
   * 
   * @return
   */
  public boolean isLoggedIn();

  /**
   * 
   * Log the current user out See <a
   * href="https://www.mediawiki.org/wiki/API:Logout">API:Logout</a>
   * 
   * @throws Exception
   * @since 0.0.1
   */
  public void logout() throws Exception;

  /**
   * get the page Content for the given page Title
   * 
   * @param pageTitle
   * @return the content of the page
   * @throws Exception
   * @since 0.0.1
   */
  public String getPageContent(String pageTitle) throws Exception;
  
  /**
   * get the html rendered version of a page
   * @param pageTitle
   * @return the html for a page
   * @throws Exception
   * @since 0.0.15
   */
  public String getPageHtml(String pageTitle) throws Exception;

  /**
   * get the Images on the given page
   * 
   * @param pageTitle
   * @param imLimit
   * @return
   * @throws Exception
   */
  public List<Im> getImagesOnPage(String pageTitle, int imLimit)
      throws Exception;

  /**
   * get the content of the given section
   * 
   * @param pageTitle
   * @param sectionNumber
   * @return the content of the section;
   * @throws Exception
   */
  public String getSectionText(String pageTitle, int sectionNumber)
      throws Exception;

  /**
   * delete the given page for the given reason
   * 
   * @param title
   * @param reason
   * @return
   * @throws Exception
   * @since 0.0.1
   */
  public Delete delete(String title, String reason) throws Exception;

  /**
   * Edits a page by setting its text to the supplied value.
   *
   * @param text
   *          the text of the page
   * @param pagetitle
   *          the title of the page
   * @param summary
   *          the edit summary. Summaries longer than 200 characters are
   *          truncated server-side.
   * @return the edit stage as a wrapped Edit API response
   * @throws Exception
   *           - if the edit fails
   * @see #getPageContent
   * @since 0.0.1
   */
  public Edit edit(String pagetitle, String text, String summary)
      throws Exception;

  /**
   * edit
   * 
   * @param pageTitle
   * @param text
   * @param summary
   * @param minor
   *          - true if this is a minor edit
   * @param bot
   *          whether to mark the edit as a bot edit (ignored if one does not
   *          have the necessary permissions)
   * @param sectionNumber
   *          the section to edit. Use -1 to specify a new section and -2 to
   *          disable section editing.
   * @param sectionTitle
   *          the title of a new section
   * @param basetime
   *          the timestamp of the revision on which <tt>text</tt> is based,
   *          used to check for edit conflicts. <tt>null</tt> disables this.
   * @return the edit stage as a wrapped Edit API response * @throws Exception -
   *         if the edit fails
   * @see #getPageContent
   * @since 0.0.4
   */
  public Edit edit(String pageTitle, String text, String summary,
      boolean minor, boolean bot, int sectionNumber, String sectionTitle,
      Calendar basetime) throws Exception;

  /**
   * Uploads an image. Equivalent to [[Special:Upload]].
   * 
   * @param file
   *          the image file
   * @param filename
   *          the target file name (may contain File)
   * @param pageContent
   *          the contents of the image description page, set to "" if
   *          overwriting an existing file
   * @param comment
   *          an upload summary (defaults to <tt>contents</tt>, use "" to not
   *          specify one)
   * @throws Exception
   *           - if the upload fails
   * @since 0.0.3
   */
  public void upload(File file, String filename, String pageContent,
      String comment) throws Exception;

  /**
   * upload the image described in the given imageinfo
   * 
   * @param ii
   * @param fileName
   * @param pageContent
   *          the contents of the image description page, set to "" if
   *          overwriting an existing file
   * @throws Exception
   * @since 0.0.5
   */
  public void upload(Ii ii, String fileName, String pageContent)
      throws Exception;

  /**
   * getAllPages
   * 
   * @param apfrom
   *          - may be null or empty
   * @param aplimit
   * @return
   * @throws Exception
   */
  public List<P> getAllPages(String apfrom, int aplimit) throws Exception;

  /**
   * get all Images by the given time stamps
   * 
   * @param aistart
   *          - start time stamp
   * @param aiend
   *          - end time stamp
   * @param ailimit
   *          - how many images to get
   * @throws Exception
   */
  public List<Img> getAllImagesByTimeStamp(String aistart, String aiend,
      int ailimit) throws Exception;

  /**
   * get the sections for the given pageTitle
   * 
   * @param pageTitle
   * @return
   * @throws Exception
   */
  public List<S> getSections(String pageTitle) throws Exception;

  /**
   * set the given protectionMarker for this wiki if a page has this
   * protectionMarker and edit will not be done
   * 
   * @param protectionMarker
   */
  public void setProtectionMarker(String protectionMarker);

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
      String summary) throws Exception;

  /**
   * are exceptions thrown when an api error code is received?
   * 
   * @return the throwExceptionOnError
   */
  public boolean isThrowExceptionOnError();

  /**
   * set to true if exceptions should be thrown when api error codes are
   * received default is true
   * 
   * @param throwExceptionOnError
   *          the throwExceptionOnError to set
   */
  public void setThrowExceptionOnError(boolean throwExceptionOnError);

  /**
   * get an ISO time stamp (utility function) FIXME may have to move out of the
   * main API
   * 
   * @return
   */
  public String getIsoTimeStamp();

  /**
   * set the debug mode
   * 
   * @param pDebug
   */
  public void setDebug(boolean pDebug);

  /**
   * get the state of the debug mode
   * 
   * @return - true if debugging is activated
   * @return
   */
  public boolean isDebug();
  
  /**
   * normalize the given page title
   * @param title - a raw page title
   * @return a valid page title see https://www.mediawiki.org/wiki/Manual:Page_title
   * @throws Exception
   */
  public String normalizeTitle(String title) throws Exception;

  /**
   * get the backlinks for the given pagetitle
   * 
   * @param pagetitle
   * @param params
   *          - extra params
   * @limit - the limit for the number of results to return
   * @throws Exception
   */
  public List<Bl> getBacklinks(String pageTitle, String params, int limit)
      throws Exception;

  /**
   * get the imageusage for the given imageTitle
   * 
   * @param imageTitle
   * @param params
   *          - extra params
   * @limit - the limit for the number of results to return
   * @throws Exception
   */
  public List<Iu> getImageUsage(String imageTitle, String params, int limit)
      throws Exception;

  /**
   * get the imageInfos for all images on the given page Title
   * 
   * @param pageTitle
   * @limit - the limit for the number of results to return
   * @throws Exception
   * 
   * @return the list of ImageInfos
   */
  public List<Ii> getImageInfosForPage(String pageTitle, int limit) throws Exception;

}
