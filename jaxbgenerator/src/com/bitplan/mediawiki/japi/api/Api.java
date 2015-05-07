//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.05.07 um 11:22:12 PM CEST 
//


package com.bitplan.mediawiki.japi.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="createaccount">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="token" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="result" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "createaccount"
})
@XmlRootElement(name = "api")
public class Api {

    @XmlElement(required = true)
    protected Createaccount createaccount;

    /**
     * Ruft den Wert der createaccount-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Createaccount }
     *     
     */
    public Createaccount getCreateaccount() {
        return createaccount;
    }

    /**
     * Legt den Wert der createaccount-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Createaccount }
     *     
     */
    public void setCreateaccount(Createaccount value) {
        this.createaccount = value;
    }

}
