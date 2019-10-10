/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2019 BITPlan GmbH https://github.com/BITPlan
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
package com.bitplan.mediawiki.japi.api;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Delete XML wrapper - hand crafted comparable to Edit
 * @author wf
 *
 */
public class Delete {

  protected String title;
  protected String reason;
  protected Integer logid;
  /**
   * @return the title
   */
  @XmlAttribute
  public String getTitle() {
    return title;
  }
  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }
  /**
   * @return the reason
   */
  @XmlAttribute
  public String getReason() {
    return reason;
  }
  /**
   * @param reason the reason to set
   */
  public void setReason(String reason) {
    this.reason = reason;
  }
  /**
   * @return the logid
   */
  @XmlAttribute
  public Integer getLogid() {
    return logid;
  }
  /**
   * @param logid the logid to set
   */
  public void setLogid(Integer logid) {
    this.logid = logid;
  }
  
  public Delete() {}; // make JAXB happy
}
