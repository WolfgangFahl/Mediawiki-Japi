//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.06.13 um 08:01:41 AM CEST 
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
 *         &lt;element name="query">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="pages">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="page">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="imageinfo">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="ii">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                                                         &lt;attribute name="user" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="userid" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                                         &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                                         &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                                         &lt;attribute name="height" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                                         &lt;attribute name="parsedcomment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="comment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="html" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="canonicaltitle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="url" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="descriptionurl" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="sha1" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="mime" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="mediatype" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="bitdepth" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="_idx" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                                     &lt;attribute name="ns" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                                     &lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="missing" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="imagerepository" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
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
    "query"
})
@XmlRootElement(name = "api")
public class Api {

    @XmlElement(required = true)
    protected Query query;

    /**
     * Ruft den Wert der query-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Query }
     *     
     */
    public Query getQuery() {
        return query;
    }

    /**
     * Legt den Wert der query-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Query }
     *     
     */
    public void setQuery(Query value) {
        this.query = value;
    }

}
