package com.bitplan.mediawiki.japi;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.bitplan.mediawiki.japi.api.Ii;

/**
 * transfer pages from one wiki to another
 * 
 * @author wf
 *
 */
public class PushPages {

  public static final int DEFAULT_IMAGE_LIMIT = 100;
  private int imageLimit;
  private Mediawiki sourceWiki;
  private Mediawiki targetWiki;
  public static boolean showDebug=true;

  /**
   * construct me with the given parameters
   * 
   * @param sourceWikiId
   * @param targetWikiId
   * @throws Exception
   */
  public PushPages(String sourceWikiId, String targetWikiId) throws Exception {
    init(sourceWikiId, targetWikiId, DEFAULT_IMAGE_LIMIT);
  }

  /**
   * construct me with the given parameters
   * 
   * @param sourceWikiId
   * @param targetWikiId
   * @param imageLimit
   * @throws Exception
   */
  public PushPages(String sourceWikiId, String targetWikiId, int imageLimit)
      throws Exception {
    init(sourceWikiId, targetWikiId, imageLimit);
  }

  /**
   * construct me with the given parameters
   * 
   * @param sourceWiki
   * @param targetWiki
   */
  public PushPages(Mediawiki sourceWiki, Mediawiki targetWiki) {
    init(sourceWiki, targetWiki, DEFAULT_IMAGE_LIMIT);
  }

  /**
   * construct me with the given parameters
   * 
   * @param sourceWiki
   * @param targetWiki
   * @param imageLimit
   */
  public PushPages(Mediawiki sourceWiki, Mediawiki targetWiki, int imageLimit) {
    init(sourceWiki, targetWiki, imageLimit);
  }

  /**
   * 
   * @param imageLimit
   * @throws Exception
   */
  public void init(String sourceWikiId, String targetWikiId, int imageLimit)
      throws Exception {
    SSLWiki sourceWiki = new SSLWiki(sourceWikiId);
    SSLWiki targetWiki = new SSLWiki(targetWikiId);
    targetWiki.login();
    this.init(sourceWiki, targetWiki, imageLimit);
  }

  /**
   * initialize me with the given parameters
   * 
   * @param sourceWiki
   * @param targetWiki
   * @param imageLimit
   */
  public void init(Mediawiki sourceWiki, Mediawiki targetWiki, int imageLimit) {
    this.sourceWiki = sourceWiki;
    this.targetWiki = targetWiki;
    this.imageLimit = imageLimit;
  }

  /**
   * push the pages with the given pageTitles
   *
   * @param pageTitles
   * @throws Exception
   */
  public void push(String... pageTitles) throws Exception {
    for (String pageTitle : pageTitles) {
      pushPage(pageTitle);
    }
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
    String pageContent = sourceWiki.getPageContent(pageTitle);
    targetWiki.edit(pageTitle, pageContent, getSummary());
    pushImages(pageTitle);
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
      targetWiki.upload(downloaded, imageInfo.getCanonicaltitle(), contents==null?"":contents,
          imageInfo.getComment());
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
