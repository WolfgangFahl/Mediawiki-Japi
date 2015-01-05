//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.01.05 um 04:52:15 PM CET 
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
 *         &lt;element name="paraminfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="modules">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="module" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="helpurls">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="helpurl">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                       &lt;enumeration value="https://www.mediawiki.org/wiki/API:Parsing_wikitext#parse"/>
 *                                                       &lt;enumeration value="https://www.mediawiki.org/wiki/API:Data_formats"/>
 *                                                       &lt;enumeration value="https://www.mediawiki.org/wiki/API:Allpages"/>
 *                                                       &lt;enumeration value="https://www.mediawiki.org/wiki/API:Meta#siteinfo_.2F_si"/>
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="parameters">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="param" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="type" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="t" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                         &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="default" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="multi" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="limit" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                                         &lt;attribute name="lowlimit" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                                         &lt;attribute name="highlimit" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                                         &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                                         &lt;attribute name="highmax" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                                         &lt;attribute name="min" type="{http://www.w3.org/2001/XMLSchema}byte" />
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
 *                                     &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="classname" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="path" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="group" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="prefix" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="readrights" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="generator" type="{http://www.w3.org/2001/XMLSchema}string" />
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
 *                 &lt;attribute name="helpformat" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "paraminfo"
})
@XmlRootElement(name = "api")
public class Api {

    @XmlElement(required = true)
    protected Paraminfo paraminfo;

    /**
     * Ruft den Wert der paraminfo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Paraminfo }
     *     
     */
    public Paraminfo getParaminfo() {
        return paraminfo;
    }

    /**
     * Legt den Wert der paraminfo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Paraminfo }
     *     
     */
    public void setParaminfo(Paraminfo value) {
        this.paraminfo = value;
    }

}
