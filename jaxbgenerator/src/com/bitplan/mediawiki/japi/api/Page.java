//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.06.13 um 08:01:41 AM CEST 
//


package com.bitplan.mediawiki.japi.api;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="imageinfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ii">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                           &lt;attribute name="user" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="userid" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                           &lt;attribute name="height" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                           &lt;attribute name="parsedcomment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="comment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="html" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="canonicaltitle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="url" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="descriptionurl" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="sha1" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="mime" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="mediatype" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="bitdepth" type="{http://www.w3.org/2001/XMLSchema}integer" />
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
 *       &lt;attribute name="_idx" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="ns" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="missing" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="imagerepository" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "imageinfo"
})
public class Page {

    @XmlElement(required = true)
    protected Imageinfo imageinfo;
    @XmlAttribute(name = "_idx")
    protected BigInteger idx;
    @XmlAttribute(name = "ns")
    protected BigInteger ns;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "missing")
    protected String missing;
    @XmlAttribute(name = "imagerepository")
    protected String imagerepository;

    /**
     * Ruft den Wert der imageinfo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Imageinfo }
     *     
     */
    public Imageinfo getImageinfo() {
        return imageinfo;
    }

    /**
     * Legt den Wert der imageinfo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Imageinfo }
     *     
     */
    public void setImageinfo(Imageinfo value) {
        this.imageinfo = value;
    }

    /**
     * Ruft den Wert der idx-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdx() {
        return idx;
    }

    /**
     * Legt den Wert der idx-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdx(BigInteger value) {
        this.idx = value;
    }

    /**
     * Ruft den Wert der ns-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNs() {
        return ns;
    }

    /**
     * Legt den Wert der ns-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNs(BigInteger value) {
        this.ns = value;
    }

    /**
     * Ruft den Wert der title-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Legt den Wert der title-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Ruft den Wert der missing-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMissing() {
        return missing;
    }

    /**
     * Legt den Wert der missing-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMissing(String value) {
        this.missing = value;
    }

    /**
     * Ruft den Wert der imagerepository-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagerepository() {
        return imagerepository;
    }

    /**
     * Legt den Wert der imagerepository-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagerepository(String value) {
        this.imagerepository = value;
    }

}
