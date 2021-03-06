/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2021 BITPlan GmbH https://github.com/BITPlan
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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.01 at 10:22:04 PM CET 
//


package com.bitplan.mediawiki.japi.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="result" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="token" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="cookieprefix" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sessionid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
public class Login {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "result")
    protected String result;
    @XmlAttribute(name = "token")
    protected String token;
    @XmlAttribute(name = "cookieprefix")
    protected String cookieprefix;
    @XmlAttribute(name = "sessionid")
    protected String sessionid;
    
    // after successful login
    @XmlAttribute(name = "lguserid")
    protected String lguserid;

    @XmlAttribute(name = "lgusername")
    protected String lgusername;
    
    @XmlAttribute(name = "lgtoken")
    protected String lgtoken;
    
    /**
		 * @return the lguserid
		 */
		public String getLguserid() {
			return lguserid;
		}

		/**
		 * @param lguserid the lguserid to set
		 */
		public void setLguserid(String lguserid) {
			this.lguserid = lguserid;
		}

		/**
		 * @return the lgusername
		 */
		public String getLgusername() {
			return lgusername;
		}

		/**
		 * @param lgusername the lgusername to set
		 */
		public void setLgusername(String lgusername) {
			this.lgusername = lgusername;
		}

		/**
		 * @return the lgtoken
		 */
		public String getLgtoken() {
			return lgtoken;
		}

		/**
		 * @param lgtoken the lgtoken to set
		 */
		public void setLgtoken(String lgtoken) {
			this.lgtoken = lgtoken;
		}

		/**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the cookieprefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCookieprefix() {
        return cookieprefix;
    }

    /**
     * Sets the value of the cookieprefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCookieprefix(String value) {
        this.cookieprefix = value;
    }

    /**
     * Gets the value of the sessionid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionid() {
        return sessionid;
    }

    /**
     * Sets the value of the sessionid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionid(String value) {
        this.sessionid = value;
    }

}
