/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2018 BITPlan GmbH https://github.com/BITPlan
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
package com.bitplan.mediawiki.japi.jaxb;

import javax.xml.bind.JAXBException;

/**
 * generic conversion of <T> instance to and from xml/json
 * @author wf
 *
 * @param <T>
 */
public interface JaxbFactoryApi<T> {
  /**
   * create an xml representation for the given <T> instance
   * @param instance - the instance to convert to xml
   * @return a xml representation of the given <T> instance
   * @throws JAXBException
   */
  public String asXML(T instance) throws JAXBException;
  
	/**
	 * get a <T> instance for the given xml
	 * @param xml - the xml representation of a <T> instance
	 * @return a new <T> instance
	 * @throws JAXBException - if there is an issue with the xml
	 * @throws Exception 
	 */
	public T fromXML(final String xml) throws JAXBException, Exception;
	
	 /**
   * create a Json representation for the given <T> instance 
   * @param instance - the instance to convert to json
   * @return a Json representation of the given <T>
   * @throws JAXBException
   */
  public String asJson(T instance) throws JAXBException;
  
	/**
	 * get a <T> instance for the given json
	 * @param json - the json representation of a <T> instance
	 * @return a new <T> instance
	 * @throws Exception - if there is an issue with the json
	 */
	public T fromJson(final String json) throws Exception;
	
}
