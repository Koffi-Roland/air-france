
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AirFranceProfil complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AirFranceProfil">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="regimental" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMatricule" minOccurs="0"/>
 *         &lt;element name="rank" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeRang" minOccurs="0"/>
 *         &lt;element name="notesAddress" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeAdresseNotes" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMotDePasse" minOccurs="0"/>
 *         &lt;element name="function" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFonction" minOccurs="0"/>
 *         &lt;element name="rReference" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeReferenceR" minOccurs="0"/>
 *         &lt;element name="typology" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeTypologie" minOccurs="0"/>
 *         &lt;element name="originCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCodeOrigine" minOccurs="0"/>
 *         &lt;element name="compagnyCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCodeCompagnie" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCodeStatut" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirFranceProfil", propOrder = {
    "regimental",
    "rank",
    "notesAddress",
    "password",
    "function",
    "rReference",
    "typology",
    "originCode",
    "compagnyCode",
    "statusCode"
})
public class AirFranceProfil {

    protected String regimental;
    protected String rank;
    protected String notesAddress;
    protected String password;
    protected String function;
    protected String rReference;
    protected String typology;
    protected String originCode;
    protected String compagnyCode;
    protected String statusCode;

    /**
     * Gets the value of the regimental property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegimental() {
        return regimental;
    }

    /**
     * Sets the value of the regimental property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegimental(String value) {
        this.regimental = value;
    }

    /**
     * Gets the value of the rank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRank() {
        return rank;
    }

    /**
     * Sets the value of the rank property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRank(String value) {
        this.rank = value;
    }

    /**
     * Gets the value of the notesAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotesAddress() {
        return notesAddress;
    }

    /**
     * Sets the value of the notesAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotesAddress(String value) {
        this.notesAddress = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the function property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunction() {
        return function;
    }

    /**
     * Sets the value of the function property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunction(String value) {
        this.function = value;
    }

    /**
     * Gets the value of the rReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRReference() {
        return rReference;
    }

    /**
     * Sets the value of the rReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRReference(String value) {
        this.rReference = value;
    }

    /**
     * Gets the value of the typology property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypology() {
        return typology;
    }

    /**
     * Sets the value of the typology property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypology(String value) {
        this.typology = value;
    }

    /**
     * Gets the value of the originCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginCode() {
        return originCode;
    }

    /**
     * Sets the value of the originCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginCode(String value) {
        this.originCode = value;
    }

    /**
     * Gets the value of the compagnyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompagnyCode() {
        return compagnyCode;
    }

    /**
     * Sets the value of the compagnyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompagnyCode(String value) {
        this.compagnyCode = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusCode(String value) {
        this.statusCode = value;
    }

}
