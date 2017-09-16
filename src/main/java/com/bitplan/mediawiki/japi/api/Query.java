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
package com.bitplan.mediawiki.japi.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "query")
/**
 * Query Jaxb Wrapping class
 * 
 * @author wf
 *
 */
public class Query {

  protected Tokens tokens;
  protected Statistics statistics;
  protected General general;
  protected List<P> allpages = new ArrayList<P>();
  protected List<Img> allimages = new ArrayList<Img>();
  protected List<Bl> backlinks = new ArrayList<Bl>();
  protected List<Iu> imageusages = new ArrayList<Iu>();
  protected List<Page> pages = new ArrayList<Page>();
  protected List<Rc> recentchanges = new ArrayList<Rc>();
  protected List<Ns> namespaces = new ArrayList<Ns>();
  String subject;
  String serializer;
  String version;
  List<Property> data;

  /**
   * Ruft den Wert der tokens-Eigenschaft ab.
   * 
   * @return possible object is {@link Tokens }
   * 
   */
  public Tokens getTokens() {
    return tokens;
  }

  /**
   * Legt den Wert der tokens-Eigenschaft fest.
   * 
   * @param value
   *          allowed object is {@link Tokens }
   * 
   */
  public void setTokens(Tokens value) {
    this.tokens = value;
  }

  /**
   * @return the statistics
   */
  public Statistics getStatistics() {
    return statistics;
  }

  /**
   * @param statistics
   *          the statistics to set
   */
  public void setStatistics(Statistics statistics) {
    this.statistics = statistics;
  }

  /**
   * Gets the value of the general property.
   * 
   * @return possible object is {@link General }
   * 
   */
  public General getGeneral() {
    return general;
  }

  /**
   * Sets the value of the general property.
   * 
   * @param value
   *          allowed object is {@link General }
   * 
   */
  public void setGeneral(General value) {
    this.general = value;
  }

  /**
   * Gets the value of the allpages property.
   * 
   */
  @XmlElementWrapper(name = "allpages")
  @XmlElement(name = "p", type = P.class)
  public List<P> getAllpages() {
    return allpages;
  }

  /**
   * Sets the value of the allpages property.
   * 
   */
  public void setAllpages(List<P> value) {
    this.allpages = value;
  }

  /**
   * Gets the value of the allimages property.
   * 
   */
  @XmlElementWrapper(name = "allimages")
  @XmlElement(name = "img", type = Img.class)
  public List<Img> getAllImages() {
    return allimages;
  }

  /**
   * Sets the value of the allpages property.
   * 
   */
  public void setAllImages(List<Img> value) {
    this.allimages = value;
  }

  /**
   * Gets the value of the backlinks property.
   * 
   */
  @XmlElementWrapper(name = "backlinks")
  @XmlElement(name = "bl", type = Bl.class)
  public List<Bl> getBacklinks() {
    return backlinks;
  }

  /**
   * Sets the value of the backlinks property.
   * 
   */
  public void setBacklinks(List<Bl> value) {
    this.backlinks = value;
  }

  /**
   * Gets the value of the backlinks property.
   * 
   */
  @XmlElementWrapper(name = "imageusage")
  @XmlElement(name = "iu", type = Iu.class)
  public List<Iu> getImageusage() {
    return this.imageusages;
  }

  /**
   * Sets the value of the imageusages property.
   * 
   */
  public void setImageusage(List<Iu> value) {
    this.imageusages = value;
  }

  /**
   * Gets the value of the pages property.
   * 
   * @return the list of pages
   * 
   */
  @XmlElementWrapper(name = "pages")
  @XmlElement(name = "page", type = Page.class)
  public List<Page> getPages() {
    return pages;
  }

  /**
   * Sets the value of the pages property.
   * 
   * @param value
   * 
   */
  public void setPages(List<Page> value) {
    this.pages = value;
  }

  /**
   * @return the recentchanges
   */
  @XmlElementWrapper(name = "recentchanges")
  @XmlElement(name = "rc", type = Rc.class)
  public List<Rc> getRecentchanges() {
    return recentchanges;
  }

  /**
   * @param recentchanges
   *          the recentchanges to set
   */
  public void setRecentchanges(List<Rc> recentchanges) {
    this.recentchanges = recentchanges;
  }

  /**
   * @return the namespaces
   */
  @XmlElementWrapper(name = "namespaces")
  @XmlElement(name = "ns", type = Ns.class)
  public List<Ns> getNamespaces() {
    return namespaces;
  }

  /**
   * @param namespaces
   *          the namespaces to set
   */
  public void setNamespaces(List<Ns> namespaces) {
    this.namespaces = namespaces;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getSerializer() {
    return serializer;
  }

  public void setSerializer(String serializer) {
    this.serializer = serializer;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public List<Property> getData() {
    return data;
  }

  public void setData(List<Property> data) {
    this.data = data;
  }

}
