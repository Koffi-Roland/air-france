
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualProfilV3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualProfilV3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="emailOptin" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMailOptin" minOccurs="0"/>
 *         &lt;element name="proAreaCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeDomainProCode" minOccurs="0"/>
 *         &lt;element name="proAreaWording" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLabelDomainPro" minOccurs="0"/>
 *         &lt;element name="civilianCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMaritalCode" minOccurs="0"/>
 *         &lt;element name="languageCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLanguageCode" minOccurs="0"/>
 *         &lt;element name="carrierCode" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeCarrier" minOccurs="0"/>
 *         &lt;element name="proFunctionCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeProFunctionCode" minOccurs="0"/>
 *         &lt;element name="proFunctionWording" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLabelFunctionPro" minOccurs="0"/>
 *         &lt;element name="childrenNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeChildrenNb" minOccurs="0"/>
 *         &lt;element name="customerSegment" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerSegment" minOccurs="0"/>
 *         &lt;element name="studentCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeStudentCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualProfilV3", propOrder = {
    "emailOptin",
    "proAreaCode",
    "proAreaWording",
    "civilianCode",
    "languageCode",
    "carrierCode",
    "proFunctionCode",
    "proFunctionWording",
    "childrenNumber",
    "customerSegment",
    "studentCode"
})
public class IndividualProfilV3 {

    protected String emailOptin;
    protected String proAreaCode;
    protected String proAreaWording;
    protected String civilianCode;
    protected String languageCode;
    protected String carrierCode;
    protected String proFunctionCode;
    protected String proFunctionWording;
    protected String childrenNumber;
    protected String customerSegment;
    protected String studentCode;

    /**
     * Gets the value of the emailOptin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailOptin() {
        return emailOptin;
    }

    /**
     * Sets the value of the emailOptin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailOptin(String value) {
        this.emailOptin = value;
    }

    /**
     * Gets the value of the proAreaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProAreaCode() {
        return proAreaCode;
    }

    /**
     * Sets the value of the proAreaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProAreaCode(String value) {
        this.proAreaCode = value;
    }

    /**
     * Gets the value of the proAreaWording property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProAreaWording() {
        return proAreaWording;
    }

    /**
     * Sets the value of the proAreaWording property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProAreaWording(String value) {
        this.proAreaWording = value;
    }

    /**
     * Gets the value of the civilianCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivilianCode() {
        return civilianCode;
    }

    /**
     * Sets the value of the civilianCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivilianCode(String value) {
        this.civilianCode = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the carrierCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrierCode() {
        return carrierCode;
    }

    /**
     * Sets the value of the carrierCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrierCode(String value) {
        this.carrierCode = value;
    }

    /**
     * Gets the value of the proFunctionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProFunctionCode() {
        return proFunctionCode;
    }

    /**
     * Sets the value of the proFunctionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProFunctionCode(String value) {
        this.proFunctionCode = value;
    }

    /**
     * Gets the value of the proFunctionWording property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProFunctionWording() {
        return proFunctionWording;
    }

    /**
     * Sets the value of the proFunctionWording property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProFunctionWording(String value) {
        this.proFunctionWording = value;
    }

    /**
     * Gets the value of the childrenNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildrenNumber() {
        return childrenNumber;
    }

    /**
     * Sets the value of the childrenNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildrenNumber(String value) {
        this.childrenNumber = value;
    }

    /**
     * Gets the value of the customerSegment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerSegment() {
        return customerSegment;
    }

    /**
     * Sets the value of the customerSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerSegment(String value) {
        this.customerSegment = value;
    }

    /**
     * Gets the value of the studentCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudentCode() {
        return studentCode;
    }

    /**
     * Sets the value of the studentCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudentCode(String value) {
        this.studentCode = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
