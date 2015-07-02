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

import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Ns;

/**
 * Namespaces interface
 * @author wf
 *
 */
public interface SiteInfo {
  
  /**
   * get the languge of this site
   * @return
   * @throws Exception 
   */
  public String getLang() throws Exception;
  
  /**
   * get the Version of this wiki
   * 
   * @throws Exception
   */
  public String getVersion() throws Exception;
  
  /**
   * get the Namespaces for this wiki
   * @return
   * @throws Exception
   */
  public Map<String,Ns> getNamespaces() throws Exception;
  
  /**
   * get the Namespaces for this wiki
   * @return
   * @throws Exception
   */
  public Map<Integer,Ns> getNamespacesById() throws Exception;
  
  /**
   * get the Namespaces by canonical name for this wiki
   * @return
   * @throws Exception
   */
  public Map<String,Ns> getNamespacesByCanonicalName() throws Exception;
  
  /**
   * get the general siteinfo
   * 
   * @return the general siteinfo
   * @throws Exception
   */
  public General getGeneral() throws Exception;
  
  /**
   * map the given namespace to the target wiki
   * @param ns
   * @param targetWiki
   * @return the namespace name for the target wiki
   * @throws Exception 
   */
  public String mapNamespace(String ns, SiteInfo targetWiki) throws Exception;
}
