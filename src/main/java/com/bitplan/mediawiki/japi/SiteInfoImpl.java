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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Ns;

/**
 * SiteInfo and namespace handling
 * 
 * @author wf
 *
 */
public class SiteInfoImpl implements SiteInfo {
  /**
   * Logging may be enabled by setting debug to true
   */
  protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
      .getLogger("com.bitplan.mediawiki.japi");

  public static boolean debug = true;
  protected String mediawikiVersion;

  protected General generalSiteInfo;
  protected Map<String, Ns> namespaces;
  protected Map<String, Ns> namespacesByCanonicalName;
  protected Map<Integer, Ns> namespacesById;

  /**
   * initalize me
   * 
   * @param general
   * @param list
   */
  public SiteInfoImpl(General general, List<Ns> list) {
    this.generalSiteInfo = general;
    initNameSpaces(general, list);
  }

  /**
   * initialize the NameSpaces from the given namespaceList
   * 
   * @param general
   * @param namespaceList
   */
  protected void initNameSpaces(General general, List<Ns> namespaceList) {
    namespaces = new LinkedHashMap<String, Ns>();
    namespacesById = new LinkedHashMap<Integer, Ns>();
    namespacesByCanonicalName = new LinkedHashMap<String, Ns>();
    for (Ns namespace : namespaceList) {
      String namespacename = namespace.getValue();
      namespaces.put(namespacename, namespace);
      namespacesById.put(namespace.getId(), namespace);
      String canonical = namespace.getCanonical();
      // this is a BUG in 2015-07-02 - some canonical names are not correct
      // FIXME - when Bug has been fixed in SMW
      String bugs[] = { "Attribut", "Property", "Konzept", "Concept","Kategorie","Category" };
      for (int i = 0; i < bugs.length; i += 2) {
        if (bugs[i].equals(canonical) && bugs[i].equals(namespacename)) {
          canonical = bugs[i + 1];
          namespace.setCanonical(bugs[i + 1]);
        }
      }
      namespacesByCanonicalName.put(canonical, namespace);
    }
  }

  /**
   * @return the siteInfo
   */
  public General getGeneral() {
    return generalSiteInfo;
  }

  /**
   * @param siteInfo
   *          the siteInfo to set
   */
  public void setGeneral(General siteInfo) {
    this.generalSiteInfo = siteInfo;
  }

  /**
   * get the Namespaces for this wiki
   * 
   * @return
   * @throws Exception
   */
  public Map<String, Ns> getNamespaces() throws Exception {
    if (namespaces == null) {
      getGeneral();
    }
    return namespaces;
  }

  /**
   * get the Namespaces by canonical name for this wiki
   * 
   * @return
   * @throws Exception
   */
  public Map<String, Ns> getNamespacesByCanonicalName() throws Exception {
    if (namespacesByCanonicalName == null) {
      getGeneral();
    }
    return namespacesByCanonicalName;
  }

  /**
   * get the Namespaces for this wiki
   * 
   * @return
   * @throws Exception
   */
  public Map<Integer, Ns> getNamespacesById() throws Exception {
    if (namespacesById == null) {
      getGeneral();
    }
    return namespacesById;
  }

  /**
   * map the given namespace to the target wiki
   * 
   * @param ns
   * @param targetWiki
   * @return the namespace name for the target wiki
   * @throws Exception
   */
  public String mapNamespace(String ns, SiteInfo targetWiki) throws Exception {
    Map<String, Ns> sourceMap = this.getNamespaces();
    Map<Integer, Ns> targetMap = targetWiki.getNamespacesById();
    Ns sourceNs = sourceMap.get(ns);
    if (sourceNs == null) {
      if (debug)
        LOGGER.log(Level.WARNING, "can not map unknown namespace " + ns);
      return ns;
    }
    Ns targetNs = targetMap.get(sourceNs.getId());
    if (targetNs == null) {
      if (debug)
        LOGGER.log(
            Level.WARNING,
            "missing namespace " + sourceNs.getValue() + " id:"
                + sourceNs.getId() + " canonical:" + sourceNs.getCanonical());
      return ns;
    }
    return targetNs.getValue();
  }

  @Override
  public String getLang() {
    String lang = this.getGeneral().getLang();
    return lang;
  }

  @Override
  public String getVersion() throws Exception {
    String mediawikiVersion = this.getGeneral().getGenerator();
    return mediawikiVersion;
  }
}
