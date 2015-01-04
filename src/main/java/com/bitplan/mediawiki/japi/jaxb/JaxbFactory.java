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

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 * gerneric JaxbFactory
 * 
 * @author wf
 *
 * @param <T>
 */
public class JaxbFactory<T> implements JaxbFactoryApi<T> {
	final Class<T> classOfT;

	/**
	 * construct me for the given T class - workaround for java generics type
	 * erasure
	 * 
	 * @param pClassOfT
	 */
	public JaxbFactory(Class<T> pClassOfT) {
		classOfT = pClassOfT;
	}

	/**
	 * get an instance of T for the given xml string
	 * @param xml - the xml representation of the <T> instance
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T fromXML(String xml) throws JAXBException {
		// unmarshal the xml message to an Mediawiki Api Java object
		JAXBContext context = JAXBContext.newInstance(classOfT);
		Unmarshaller u = context.createUnmarshaller();
		StringReader xmlReader = new StringReader(xml);
		// this step will convert from xml text to Java Object
		Object unmarshalResult = u.unmarshal(xmlReader);
		T result = null;
		if (classOfT.isInstance(unmarshalResult)) {
			result = (T) unmarshalResult;
		}
		return result;
	}

	
	/**
	 * get an instance of T for the given json string
	 * @param json - the json representation of the <T> instance
	 * @return T
	 */
	public T fromJson(String json) throws JAXBException {
		return fromXML(json);
	}
	
	/**
	 * get a marshaller for the given <T> instance 
	 * @param instance - the instance to get a marshaller for
	 * @return a marshaller for <T>
	 * @throws JAXBException
	 */
	public Marshaller getMarshaller(T instance) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(instance.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		return marshaller;
	}
	
	/**
	 * get the string representation of the given marshaller
	 * @param marshaller
	 * @return the string representation for the given marshaller
	 * @throws JAXBException
	 */
	public String getString(Marshaller marshaller) throws JAXBException {
		StringWriter sw = new StringWriter();
		marshaller.marshal(this, sw);
		String result = sw.toString();
		return result;
	}
	
	/**
	 * create a Json representation for the given <T> instance 
	 * @param instance - the instance to convert to json
	 * @return a Json representation of the given <T>
	 * @throws JAXBException
	 */
	public String asJson(T instance) throws JAXBException {
		Marshaller marshaller=getMarshaller(instance);
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
		marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
		String result=getString(marshaller);
		return result;
	}

	/**
	 * create an xml representation for the given <T> instance
	 * @param instance - the instance to convert to xml
	 * @return a xml representation of the given <T> instance
	 * @throws JAXBException
	 */
	public String asXml(T instance) throws JAXBException {
		Marshaller marshaller=getMarshaller(instance);
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/xml");
		String result=getString(marshaller);
		return result;
	}
}
