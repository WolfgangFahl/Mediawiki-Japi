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
