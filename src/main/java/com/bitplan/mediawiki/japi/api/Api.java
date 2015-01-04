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
package com.bitplan.mediawiki.japi.api;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bitplan.mediawiki.japi.jaxb.JaxbFactory;
import com.bitplan.mediawiki.japi.jaxb.JaxbFactoryApi;
import com.bitplan.mediawiki.japi.jaxb.JaxbPersistenceApi;

/**
 * Mediawiki Api Jaxb wrapper
 * 
 * see <a href='https://www.mediawiki.org/wiki/API:Main_page'>Mediawiki
 * API:Main_page</a>
 * 
 * @author wf
 *
 */
@XmlRootElement(name = "api")
public class Api implements JaxbPersistenceApi<Api> {

	String servedby;
	protected Query query;
	protected Login login;
	protected Error error;
	protected Tokens tokens;

	/**
	 * @return the servedby
	 */
	@XmlAttribute
	public String getServedby() {
		return servedby;
	}

	/**
	 * @param servedby
	 *          the servedby to set
	 */
	public void setServedby(String servedby) {
		this.servedby = servedby;
	}

	/**
	 * Gets the value of the login property.
	 * 
	 * @return possible object is {@link Login }
	 * 
	 */
	public Login getLogin() {
		return login;
	}

	/**
	 * Sets the value of the login property.
	 * 
	 * @param value
	 *          allowed object is {@link Login }
	 * 
	 */
	public void setLogin(Login value) {
		this.login = value;
	}

	/**
	 * @return the query
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * @param query
	 *          the query to set
	 */
	public void setQuery(Query query) {
		this.query = query;
	}

	/**
	 * Gets the value of the error property.
	 * 
	 * @return possible object is {@link Error }
	 * 
	 */
	public Error getError() {
		return error;
	}

	/**
	 * Sets the value of the error property.
	 * 
	 * @param value
	 *          allowed object is {@link Error }
	 * 
	 */
	public void setError(Error value) {
		this.error = value;
	}

	/**
	 * Gets the value of the tokens property.
	 * 
	 * @return possible object is {@link Tokens }
	 * 
	 */
	public Tokens getTokens() {
		return tokens;
	}

	/**
	 * Sets the value of the tokens property.
	 * 
	 * @param value
	 *          allowed object is {@link Tokens }
	 * 
	 */
	public void setTokens(Tokens value) {
		this.tokens = value;
	}

	/**
	 * allows typed / non static access to JaxbFactory features for
	 * 
	 * @author wf
	 *
	 */
	public static class ApiFactory extends JaxbFactory<Api> {
		public ApiFactory() {
			super(Api.class);
		}
	}

	private static ApiFactory apifactory = new ApiFactory();

	/**
	 * create a Api from an XML string
	 * 
	 * @param xml
	 *          - xml representation of Page Schema
	 * @return the Api unmarshalled from the given xml
	 * @throws JAXBException
	 *           if there's something wrong with the xml input
	 */
	public static Api fromXML(final String xml) throws JAXBException {
		return apifactory.fromXML(xml);
	}

	/**
	 * get my factory
	 * 
	 * @return the factory
	 */
	public JaxbFactoryApi<Api> getFactory() {
		return apifactory;
	}

	/**
	 * return me as a Json string
	 */
	public String asJson() throws JAXBException {
		return getFactory().asJson(this);
	}

	/**
	 * return me as an Xml string
	 */
	public String asXml() throws JAXBException {
		return getFactory().asXml(this);
	}

}
