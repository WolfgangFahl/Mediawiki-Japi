package com.bitplan.mediawiki.japi;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.bitplan.mediawiki.japi.api.Ii;
import com.bitplan.mediawiki.japi.api.Page;

/**
 * transfer pages from one wiki to another
 * 
 * @author wf
 *
 */
public class PushPages {

  public static final int DEFAULT_IMAGE_LIMIT = 100;
  private int imageLimit;
  private SSLWiki sourceWiki;
  private SSLWiki targetWiki;
  private boolean check = false; // dry run mode
  private boolean showDebug = false;
  public static boolean debug=false;

  public boolean isCheck() {
    return check;
  }

  public void setCheck(boolean check) {
    this.check = check;
  }

  public boolean isShowDebug() {
    return showDebug;
  }

  public void setShowDebug(boolean showDebug) {
    this.showDebug = showDebug;
  }

  /**
   * construct me with the given parameters
   * 
   * @param sourceWikiId
   * @param targetWikiId
   * @param login 
   * @throws Exception
   */
  public PushPages(String sourceWikiId, String targetWikiId, boolean login) throws Exception {
    init(sourceWikiId, targetWikiId, DEFAULT_IMAGE_LIMIT,login);
  }

  /**
   * construct me with the given parameters
   * 
   * @param sourceWikiId
   * @param targetWikiId
   * @param imageLimit
   * @param login
   * @throws Exception
   */
  public PushPages(String sourceWikiId, String targetWikiId, int imageLimit, boolean login)
      throws Exception {
    init(sourceWikiId, targetWikiId, imageLimit,login);
  }

  /**
   * construct me with the given parameters
   * 
   * @param sourceWiki
   * @param targetWiki
   * @param login
   * @throws Exception 
   */
  public PushPages(SSLWiki sourceWiki, SSLWiki targetWiki,boolean login) throws Exception {
    init(sourceWiki, targetWiki, DEFAULT_IMAGE_LIMIT,login);
  }

  /**
   * construct me with the given parameters
   * 
   * @param sourceWiki
   * @param targetWiki
   * @param imageLimit
   * @param login
   * @throws Exception 
   */
  public PushPages(SSLWiki sourceWiki, SSLWiki targetWiki, int imageLimit, boolean login) throws Exception {
    init(sourceWiki, targetWiki, imageLimit,login);
  }

  /**
   *  initialize me with the given parameters
   * @param sourceWikiId
   * @param targetWikiId
   * @param imageLimit
   * @param login
   * @throws Exception
   */
  public void init(String sourceWikiId, String targetWikiId, int imageLimit, boolean login)
      throws Exception {
    SSLWiki sourceWiki = SSLWiki.ofId(sourceWikiId);
    SSLWiki targetWiki = SSLWiki.ofId(targetWikiId);
    this.init(sourceWiki, targetWiki, imageLimit,login);
  }

  /**
   * initialize me with the given parameters
   * 
   * @param sourceWiki
   * @param targetWiki
   * @param imageLimit
   * @param login
   * @throws Exception 
   */
  public void init(SSLWiki sourceWiki, SSLWiki targetWiki, int imageLimit, boolean login) throws Exception {
    this.sourceWiki = sourceWiki;
    this.targetWiki = targetWiki;
    if (login)
      sourceWiki.login();
    targetWiki.login();
    // this.sourceWiki.setDebug(debug);
    // this.targetWiki.setDebug(debug);
    this.imageLimit = imageLimit;
  }

  /**
   * push the pages with the given pageTitles
   *
   * @param pageTitles
   * @throws Exception
   */
  public void push(String... pageTitles) throws Exception {
    if (showDebug) {
      List<Page> pages = sourceWiki.getPages(Arrays.asList(pageTitles));
      for (Page page:pages) {
        show4Debug(page);
      }
    }
    for (String pageTitle : pageTitles) {
      pushPage(pageTitle);
    }
  }

  public void show4Debug(Page page) {
    String info=String.format("%s:%s",page.getTitle(), page.getPageid());
    System.out.println(info);
    
  }

  /**
   * get the summary
   * 
   * @return the summary string
   */
  public String getSummary() {
    String summary = String.format("pushed from %s", sourceWiki.getSiteurl());
    return summary;
  }

  /**
   * push the page with the given title
   * 
   * @param pageTitle
   * @throws Exception
   */
  public void pushPage(String pageTitle) throws Exception {
    String pageContent = this.getPageContent(pageTitle);
    if (!check)
      targetWiki.edit(pageTitle, pageContent, getSummary());
    pushImages(pageTitle);
  }
  
  /**
   * get the content of a page optionally showing the page in the browser
   * @param pageTitle
   * @return the pageContent
   * @throws Exception
   */
  public String getPageContent(String pageTitle) throws Exception {
    String pageContent=sourceWiki.getPageContent(pageTitle);
    return pageContent;
  }

  /**
   * push the images for the given pageTitle
   * 
   * @param pageTitle
   * @throws Exception
   */
  public void pushImages(String pageTitle) throws Exception {
    List<Ii> imageInfos = sourceWiki.getImageInfosForPage(pageTitle,
        this.imageLimit);
    for (Ii imageInfo : imageInfos) {
      System.out.println(imageInfo.getCanonicaltitle());
      System.out.println(imageInfo.getUrl());
      String contents = sourceWiki
          .getPageContent(imageInfo.getCanonicaltitle());
      File downloaded = this.download(imageInfo);
      if (showDebug)
        Desktop.getDesktop().open(downloaded);
      if (!check)
        targetWiki.upload(downloaded, imageInfo.getCanonicaltitle(),
            contents == null ? "" : contents, imageInfo.getComment());
    }
  }

  /**
   * download the file for the given imageInfo
   * 
   * @param imageInfo
   * @return the file downloaded
   * @throws IOException
   * @throws MalformedURLException
   */
  public File download(Ii imageInfo) throws MalformedURLException, IOException {
    String url = imageInfo.getUrl();
    InputStream in = new URL(url).openStream();
    String suffix = FilenameUtils.getExtension(url);
    String prefix = FilenameUtils.getBaseName(url);
    File tmpFile = File.createTempFile(prefix, suffix);
    tmpFile.delete();
    Files.copy(in, Paths.get(tmpFile.toURI()));
    return tmpFile;
  }

}
