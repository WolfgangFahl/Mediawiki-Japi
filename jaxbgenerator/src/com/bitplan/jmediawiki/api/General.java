//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.01 at 07:50:13 PM CET 
//


package com.bitplan.jmediawiki.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fallback" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="thumblimits">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="limit" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *                         &lt;enumeration value="120"/>
 *                         &lt;enumeration value="150"/>
 *                         &lt;enumeration value="180"/>
 *                         &lt;enumeration value="200"/>
 *                         &lt;enumeration value="220"/>
 *                         &lt;enumeration value="250"/>
 *                         &lt;enumeration value="300"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="imagelimits">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="limit" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                           &lt;attribute name="height" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="extensiondistributor">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="snapshots">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="snapshot" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="master"/>
 *                                   &lt;enumeration value="REL1_24"/>
 *                                   &lt;enumeration value="REL1_23"/>
 *                                   &lt;enumeration value="REL1_22"/>
 *                                   &lt;enumeration value="REL1_21"/>
 *                                   &lt;enumeration value="REL1_20"/>
 *                                   &lt;enumeration value="REL1_19"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="list" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="mainpage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="base" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="sitename" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="logo" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="generator" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="phpversion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="phpsapi" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="hhvmversion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dbtype" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dbversion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="imagewhitelistenabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="langconversion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="titleconversion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="linkprefixcharset" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="linkprefix" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="linktrail" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="legaltitlechars" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="git-hash" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="git-branch" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="case" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lang" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fallback8bitEncoding" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="writeapi" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="timeoffset" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="articlepath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="scriptpath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="script" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="server" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="servername" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="wikiid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="time" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="misermode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="maxuploadsize" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="favicon" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fallback",
    "thumblimits",
    "imagelimits",
    "extensiondistributor"
})
public class General {

    @XmlElement(required = true)
    protected String fallback;
    @XmlElement(required = true)
    protected Thumblimits thumblimits;
    @XmlElement(required = true)
    protected Imagelimits imagelimits;
    @XmlElement(required = true)
    protected Extensiondistributor extensiondistributor;
    @XmlAttribute(name = "mainpage")
    protected String mainpage;
    @XmlAttribute(name = "base")
    @XmlSchemaType(name = "anyURI")
    protected String base;
    @XmlAttribute(name = "sitename")
    protected String sitename;
    @XmlAttribute(name = "logo")
    protected String logo;
    @XmlAttribute(name = "generator")
    protected String generator;
    @XmlAttribute(name = "phpversion")
    protected String phpversion;
    @XmlAttribute(name = "phpsapi")
    protected String phpsapi;
    @XmlAttribute(name = "hhvmversion")
    protected String hhvmversion;
    @XmlAttribute(name = "dbtype")
    protected String dbtype;
    @XmlAttribute(name = "dbversion")
    protected String dbversion;
    @XmlAttribute(name = "imagewhitelistenabled")
    protected String imagewhitelistenabled;
    @XmlAttribute(name = "langconversion")
    protected String langconversion;
    @XmlAttribute(name = "titleconversion")
    protected String titleconversion;
    @XmlAttribute(name = "linkprefixcharset")
    protected String linkprefixcharset;
    @XmlAttribute(name = "linkprefix")
    protected String linkprefix;
    @XmlAttribute(name = "linktrail")
    protected String linktrail;
    @XmlAttribute(name = "legaltitlechars")
    protected String legaltitlechars;
    @XmlAttribute(name = "git-hash")
    protected String gitHash;
    @XmlAttribute(name = "git-branch")
    protected String gitBranch;
    @XmlAttribute(name = "case")
    protected String _case;
    @XmlAttribute(name = "lang")
    protected String lang;
    @XmlAttribute(name = "fallback8bitEncoding")
    protected String fallback8BitEncoding;
    @XmlAttribute(name = "writeapi")
    protected String writeapi;
    @XmlAttribute(name = "timezone")
    protected String timezone;
    @XmlAttribute(name = "timeoffset")
    protected Byte timeoffset;
    @XmlAttribute(name = "articlepath")
    protected String articlepath;
    @XmlAttribute(name = "scriptpath")
    protected String scriptpath;
    @XmlAttribute(name = "script")
    protected String script;
    @XmlAttribute(name = "server")
    protected String server;
    @XmlAttribute(name = "servername")
    @XmlSchemaType(name = "anyURI")
    protected String servername;
    @XmlAttribute(name = "wikiid")
    protected String wikiid;
    @XmlAttribute(name = "time")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar time;
    @XmlAttribute(name = "misermode")
    protected String misermode;
    @XmlAttribute(name = "maxuploadsize")
    protected Integer maxuploadsize;
    @XmlAttribute(name = "favicon")
    protected String favicon;

    /**
     * Gets the value of the fallback property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFallback() {
        return fallback;
    }

    /**
     * Sets the value of the fallback property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFallback(String value) {
        this.fallback = value;
    }

    /**
     * Gets the value of the thumblimits property.
     * 
     * @return
     *     possible object is
     *     {@link Thumblimits }
     *     
     */
    public Thumblimits getThumblimits() {
        return thumblimits;
    }

    /**
     * Sets the value of the thumblimits property.
     * 
     * @param value
     *     allowed object is
     *     {@link Thumblimits }
     *     
     */
    public void setThumblimits(Thumblimits value) {
        this.thumblimits = value;
    }

    /**
     * Gets the value of the imagelimits property.
     * 
     * @return
     *     possible object is
     *     {@link Imagelimits }
     *     
     */
    public Imagelimits getImagelimits() {
        return imagelimits;
    }

    /**
     * Sets the value of the imagelimits property.
     * 
     * @param value
     *     allowed object is
     *     {@link Imagelimits }
     *     
     */
    public void setImagelimits(Imagelimits value) {
        this.imagelimits = value;
    }

    /**
     * Gets the value of the extensiondistributor property.
     * 
     * @return
     *     possible object is
     *     {@link Extensiondistributor }
     *     
     */
    public Extensiondistributor getExtensiondistributor() {
        return extensiondistributor;
    }

    /**
     * Sets the value of the extensiondistributor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Extensiondistributor }
     *     
     */
    public void setExtensiondistributor(Extensiondistributor value) {
        this.extensiondistributor = value;
    }

    /**
     * Gets the value of the mainpage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainpage() {
        return mainpage;
    }

    /**
     * Sets the value of the mainpage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainpage(String value) {
        this.mainpage = value;
    }

    /**
     * Gets the value of the base property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBase() {
        return base;
    }

    /**
     * Sets the value of the base property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBase(String value) {
        this.base = value;
    }

    /**
     * Gets the value of the sitename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitename() {
        return sitename;
    }

    /**
     * Sets the value of the sitename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitename(String value) {
        this.sitename = value;
    }

    /**
     * Gets the value of the logo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogo() {
        return logo;
    }

    /**
     * Sets the value of the logo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogo(String value) {
        this.logo = value;
    }

    /**
     * Gets the value of the generator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenerator() {
        return generator;
    }

    /**
     * Sets the value of the generator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenerator(String value) {
        this.generator = value;
    }

    /**
     * Gets the value of the phpversion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhpversion() {
        return phpversion;
    }

    /**
     * Sets the value of the phpversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhpversion(String value) {
        this.phpversion = value;
    }

    /**
     * Gets the value of the phpsapi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhpsapi() {
        return phpsapi;
    }

    /**
     * Sets the value of the phpsapi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhpsapi(String value) {
        this.phpsapi = value;
    }

    /**
     * Gets the value of the hhvmversion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHhvmversion() {
        return hhvmversion;
    }

    /**
     * Sets the value of the hhvmversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHhvmversion(String value) {
        this.hhvmversion = value;
    }

    /**
     * Gets the value of the dbtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbtype() {
        return dbtype;
    }

    /**
     * Sets the value of the dbtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbtype(String value) {
        this.dbtype = value;
    }

    /**
     * Gets the value of the dbversion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbversion() {
        return dbversion;
    }

    /**
     * Sets the value of the dbversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbversion(String value) {
        this.dbversion = value;
    }

    /**
     * Gets the value of the imagewhitelistenabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagewhitelistenabled() {
        return imagewhitelistenabled;
    }

    /**
     * Sets the value of the imagewhitelistenabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagewhitelistenabled(String value) {
        this.imagewhitelistenabled = value;
    }

    /**
     * Gets the value of the langconversion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLangconversion() {
        return langconversion;
    }

    /**
     * Sets the value of the langconversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLangconversion(String value) {
        this.langconversion = value;
    }

    /**
     * Gets the value of the titleconversion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitleconversion() {
        return titleconversion;
    }

    /**
     * Sets the value of the titleconversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitleconversion(String value) {
        this.titleconversion = value;
    }

    /**
     * Gets the value of the linkprefixcharset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkprefixcharset() {
        return linkprefixcharset;
    }

    /**
     * Sets the value of the linkprefixcharset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkprefixcharset(String value) {
        this.linkprefixcharset = value;
    }

    /**
     * Gets the value of the linkprefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkprefix() {
        return linkprefix;
    }

    /**
     * Sets the value of the linkprefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkprefix(String value) {
        this.linkprefix = value;
    }

    /**
     * Gets the value of the linktrail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinktrail() {
        return linktrail;
    }

    /**
     * Sets the value of the linktrail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinktrail(String value) {
        this.linktrail = value;
    }

    /**
     * Gets the value of the legaltitlechars property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegaltitlechars() {
        return legaltitlechars;
    }

    /**
     * Sets the value of the legaltitlechars property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegaltitlechars(String value) {
        this.legaltitlechars = value;
    }

    /**
     * Gets the value of the gitHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGitHash() {
        return gitHash;
    }

    /**
     * Sets the value of the gitHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGitHash(String value) {
        this.gitHash = value;
    }

    /**
     * Gets the value of the gitBranch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGitBranch() {
        return gitBranch;
    }

    /**
     * Sets the value of the gitBranch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGitBranch(String value) {
        this.gitBranch = value;
    }

    /**
     * Gets the value of the case property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCase() {
        return _case;
    }

    /**
     * Sets the value of the case property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCase(String value) {
        this._case = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the fallback8BitEncoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFallback8BitEncoding() {
        return fallback8BitEncoding;
    }

    /**
     * Sets the value of the fallback8BitEncoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFallback8BitEncoding(String value) {
        this.fallback8BitEncoding = value;
    }

    /**
     * Gets the value of the writeapi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWriteapi() {
        return writeapi;
    }

    /**
     * Sets the value of the writeapi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWriteapi(String value) {
        this.writeapi = value;
    }

    /**
     * Gets the value of the timezone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the value of the timezone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimezone(String value) {
        this.timezone = value;
    }

    /**
     * Gets the value of the timeoffset property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getTimeoffset() {
        return timeoffset;
    }

    /**
     * Sets the value of the timeoffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setTimeoffset(Byte value) {
        this.timeoffset = value;
    }

    /**
     * Gets the value of the articlepath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticlepath() {
        return articlepath;
    }

    /**
     * Sets the value of the articlepath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticlepath(String value) {
        this.articlepath = value;
    }

    /**
     * Gets the value of the scriptpath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScriptpath() {
        return scriptpath;
    }

    /**
     * Sets the value of the scriptpath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScriptpath(String value) {
        this.scriptpath = value;
    }

    /**
     * Gets the value of the script property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScript() {
        return script;
    }

    /**
     * Sets the value of the script property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScript(String value) {
        this.script = value;
    }

    /**
     * Gets the value of the server property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets the value of the server property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServer(String value) {
        this.server = value;
    }

    /**
     * Gets the value of the servername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServername() {
        return servername;
    }

    /**
     * Sets the value of the servername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServername(String value) {
        this.servername = value;
    }

    /**
     * Gets the value of the wikiid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWikiid() {
        return wikiid;
    }

    /**
     * Sets the value of the wikiid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWikiid(String value) {
        this.wikiid = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    /**
     * Gets the value of the misermode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMisermode() {
        return misermode;
    }

    /**
     * Sets the value of the misermode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMisermode(String value) {
        this.misermode = value;
    }

    /**
     * Gets the value of the maxuploadsize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxuploadsize() {
        return maxuploadsize;
    }

    /**
     * Sets the value of the maxuploadsize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxuploadsize(Integer value) {
        this.maxuploadsize = value;
    }

    /**
     * Gets the value of the favicon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFavicon() {
        return favicon;
    }

    /**
     * Sets the value of the favicon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFavicon(String value) {
        this.favicon = value;
    }

}