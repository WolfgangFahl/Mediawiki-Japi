package com.bitplan.jmediawiki.api;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 * Mediawiki Api Jaxb wrapper
 * 
 * https://www.mediawiki.org/wiki/API:Main_page
 * 
 * @author wf
 *
 */
@XmlRootElement(name = "api")
public class Api {

	String servedby;
	protected Query query;
	protected Login login;
	protected Error error;

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
	 * create a Api from an XML string
	 * 
	 * @param xml
	 *          - xml representation of Page Schema
	 * @return the Api unmarshalled from the given xml
	 * @throws JAXBException
	 *           if there's something wrong with the xml input
	 */
	public static Api fromXML(final String xml) throws JAXBException {
		// unmarshal the xml message to an Mediawiki Api Java object
		JAXBContext context = JAXBContext.newInstance(Api.class);
		Unmarshaller u = context.createUnmarshaller();
		StringReader xmlReader = new StringReader(xml);
		// this step will convert from xml text to Java Object
		Api result = (Api) u.unmarshal(xmlReader);
		return result;
	}

	/**
	 * create a Json representation for this Api object
	 * 
	 * @return a Json representation of the Api object
	 * @throws JAXBException
	 */
	public String asJson() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Api.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
		marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
		StringWriter sw = new StringWriter();
		marshaller.marshal(this, sw);
		String result = sw.toString();
		return result;
	}

}
