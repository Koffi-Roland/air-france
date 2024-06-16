
package com.afklm.repind.ws.w000443.data.schema552823;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SoftComputingResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SoftComputingResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="noErreur" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeNumError" minOccurs="0"/>
 *         &lt;element name="libelleErreur" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeLabelError" minOccurs="0"/>
 *         &lt;element name="noErrNormail" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeNErrNormail" minOccurs="0"/>
 *         &lt;element name="adrMailingL1" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeMailingAdress" minOccurs="0"/>
 *         &lt;element name="adrMailingL2" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeMailingAdress" minOccurs="0"/>
 *         &lt;element name="adrMailingL3" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeMailingAdress" minOccurs="0"/>
 *         &lt;element name="adrMailingL4" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeMailingAdress" minOccurs="0"/>
 *         &lt;element name="adrMailingL5" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeMailingAdress" minOccurs="0"/>
 *         &lt;element name="adrMailingL6" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeMailingAdress" minOccurs="0"/>
 *         &lt;element name="adrMailingL7" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeMailingAdress" minOccurs="0"/>
 *         &lt;element name="adrMailingL8" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeMailingAdress" minOccurs="0"/>
 *         &lt;element name="adrMailingL9" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}DTypeMailingAdress" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoftComputingResponse", propOrder = {
    "noErreur",
    "libelleErreur",
    "noErrNormail",
    "adrMailingL1",
    "adrMailingL2",
    "adrMailingL3",
    "adrMailingL4",
    "adrMailingL5",
    "adrMailingL6",
    "adrMailingL7",
    "adrMailingL8",
    "adrMailingL9"
})
public class SoftComputingResponse {

    protected String noErreur;
    protected String libelleErreur;
    protected String noErrNormail;
    protected String adrMailingL1;
    protected String adrMailingL2;
    protected String adrMailingL3;
    protected String adrMailingL4;
    protected String adrMailingL5;
    protected String adrMailingL6;
    protected String adrMailingL7;
    protected String adrMailingL8;
    protected String adrMailingL9;

    /**
     * Gets the value of the noErreur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoErreur() {
        return noErreur;
    }

    /**
     * Sets the value of the noErreur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoErreur(String value) {
        this.noErreur = value;
    }

    /**
     * Gets the value of the libelleErreur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibelleErreur() {
        return libelleErreur;
    }

    /**
     * Sets the value of the libelleErreur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibelleErreur(String value) {
        this.libelleErreur = value;
    }

    /**
     * Gets the value of the noErrNormail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoErrNormail() {
        return noErrNormail;
    }

    /**
     * Sets the value of the noErrNormail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoErrNormail(String value) {
        this.noErrNormail = value;
    }

    /**
     * Gets the value of the adrMailingL1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdrMailingL1() {
        return adrMailingL1;
    }

    /**
     * Sets the value of the adrMailingL1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdrMailingL1(String value) {
        this.adrMailingL1 = value;
    }

    /**
     * Gets the value of the adrMailingL2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdrMailingL2() {
        return adrMailingL2;
    }

    /**
     * Sets the value of the adrMailingL2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdrMailingL2(String value) {
        this.adrMailingL2 = value;
    }

    /**
     * Gets the value of the adrMailingL3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdrMailingL3() {
        return adrMailingL3;
    }

    /**
     * Sets the value of the adrMailingL3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdrMailingL3(String value) {
        this.adrMailingL3 = value;
    }

    /**
     * Gets the value of the adrMailingL4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdrMailingL4() {
        return adrMailingL4;
    }

    /**
     * Sets the value of the adrMailingL4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdrMailingL4(String value) {
        this.adrMailingL4 = value;
    }

    /**
     * Gets the value of the adrMailingL5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdrMailingL5() {
        return adrMailingL5;
    }

    /**
     * Sets the value of the adrMailingL5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdrMailingL5(String value) {
        this.adrMailingL5 = value;
    }

    /**
     * Gets the value of the adrMailingL6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdrMailingL6() {
        return adrMailingL6;
    }

    /**
     * Sets the value of the adrMailingL6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdrMailingL6(String value) {
        this.adrMailingL6 = value;
    }

    /**
     * Gets the value of the adrMailingL7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdrMailingL7() {
        return adrMailingL7;
    }

    /**
     * Sets the value of the adrMailingL7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdrMailingL7(String value) {
        this.adrMailingL7 = value;
    }

    /**
     * Gets the value of the adrMailingL8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdrMailingL8() {
        return adrMailingL8;
    }

    /**
     * Sets the value of the adrMailingL8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdrMailingL8(String value) {
        this.adrMailingL8 = value;
    }

    /**
     * Gets the value of the adrMailingL9 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdrMailingL9() {
        return adrMailingL9;
    }

    /**
     * Sets the value of the adrMailingL9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdrMailingL9(String value) {
        this.adrMailingL9 = value;
    }

}
