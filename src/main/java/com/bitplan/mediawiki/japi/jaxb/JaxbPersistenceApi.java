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
package com.bitplan.mediawiki.japi.jaxb;

import javax.xml.bind.JAXBException;

/**
 * gerneric Jaxb persistence interface converts T instances to xml or json
 * @author wf
 *
 */
public interface JaxbPersistenceApi<T> {
	/**
	 * get my factory
	 * @return the factory for T instances
	 */
	public JaxbFactoryApi<T> getFactory();
	
	/**
	 * convert me to json format
	 * @return - a json representation of me
	 * @throws JAXBException
	 */
	public String asJson() throws JAXBException;
	
	/**
	 * convert me to xml format
	 * @return - an xml representation of me
	 * @throws JAXBException
	 */
	public String asXml() throws JAXBException;
}
