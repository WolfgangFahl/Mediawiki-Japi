package com.bitplan.jmediawiki.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement(name="query")
public class Query {
   protected Statistics statistics;
   protected General general;
   protected Allpages allpages;
   protected List<Page> pages=new ArrayList<Page>();

	/**
	 * @return the statistics
	 */
	public Statistics getStatistics() {
		return statistics;
	}

	/**
	 * @param statistics the statistics to set
	 */
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	

  /**
   * Gets the value of the general property.
   * 
   * @return
   *     possible object is
   *     {@link General }
   *     
   */
  public General getGeneral() {
      return general;
  }

  /**
   * Sets the value of the general property.
   * 
   * @param value
   *     allowed object is
   *     {@link General }
   *     
   */
  public void setGeneral(General value) {
      this.general = value;
  }
 

  /**
   * Gets the value of the allpages property.
   * 
   * @return
   *     possible object is
   *     {@link Allpages }
   *     
   */
  public Allpages getAllpages() {
      return allpages;
  }

  /**
   * Sets the value of the allpages property.
   * 
   * @param value
   *     allowed object is
   *     {@link Allpages }
   *     
   */
  public void setAllpages(Allpages value) {
      this.allpages = value;
  }
  
  /**
   * Gets the value of the pages property.
   * 
   * @return
   *     
   */
  @XmlElementWrapper(name="pages")
  @XmlElement(name="page", type=Page.class)
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

}
