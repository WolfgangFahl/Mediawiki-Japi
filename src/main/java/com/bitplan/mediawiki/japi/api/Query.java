package com.bitplan.mediawiki.japi.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="query")
/**
 * Query Jaxb Wrapping class
 * @author wf
 *
 */
public class Query {
   protected Statistics statistics;
   protected General general;
   protected List<P> allpages=new ArrayList<P>();
   protected List<Page> pages=new ArrayList<Page>();
   protected List<Rc> recentchanges=new ArrayList<Rc>();
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
   */
  @XmlElementWrapper(name="allpages")
  @XmlElement(name="p", type=P.class)
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
   * Gets the value of the pages property.
   * 
   * @return the list of pages
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

	/**
	 * @return the recentchanges
	 */
  @XmlElementWrapper(name="recentchanges")
  @XmlElement(name="rc", type=Rc.class)
	public List<Rc> getRecentchanges() {
		return recentchanges;
	}

	/**
	 * @param recentchanges the recentchanges to set
	 */
	public void setRecentchanges(List<Rc> recentchanges) {
		this.recentchanges = recentchanges;
	}

}
