package com.bitplan.mediawiki.japi;

import java.util.List;

/**
 * transfer pages from one wiki to another
 * 
 * @author wf
 *
 */
public class PushPages {
  /**
   * transfer a list of page from one wiki to another
   * 
   * @param sourceWikiId
   * @param targetWikiId
   * @param pageNames
   * @throws Exception
   */
  public static void push(String sourceWikiId, String targetWikiId,
      String... pageTitles) throws Exception {
    SSLWiki sourceWiki = new SSLWiki(sourceWikiId);
    SSLWiki targetWiki = new SSLWiki(targetWikiId);
    PushPages.push(sourceWiki, targetWiki, pageTitles);
  }

  /**
   * push the pages with the given pageTitles from the source wiki to the target
   * wiki
   * 
   * @param sourceWiki
   * @param targetWiki
   * @param pageTitles
   * @throws Exception
   */
  public static void push(Mediawiki sourceWiki, Mediawiki targetWiki,
      String... pageTitles) throws Exception {
    for (String pageTitle : pageTitles) {
      String pageContent = sourceWiki.getPageContent(pageTitle);
      String summary = String.format("pushed from %s", sourceWiki.getSiteurl());
      targetWiki.edit(pageTitle, pageContent, summary);
    }
  }
}
