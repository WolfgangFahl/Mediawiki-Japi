/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2021 BITPlan GmbH https://github.com/BITPlan
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
