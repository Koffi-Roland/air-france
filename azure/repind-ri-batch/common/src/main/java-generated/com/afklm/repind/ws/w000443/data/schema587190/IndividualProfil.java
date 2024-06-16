
package com.afklm.repind.ws.w000443.data.schema587190;

import com.afklm.repind.ws.w000443.data.schema576961.LanguageCodesEnum;
import com.afklm.repind.ws.w000443.data.schema576961.ProfessionalCodesEnum;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualProfil complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualProfil">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="profilKey" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeProfileKey" minOccurs="0"/>
 *         &lt;element name="fbOptin" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMailingAuthorisation" minOccurs="0"/>
 *         &lt;element name="flagSolvency" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="proAreaCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeDomainProCode" minOccurs="0"/>
 *         &lt;element name="proAreaWording" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLabelDomainPro" minOccurs="0"/>
 *         &lt;element name="civilianCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMaritalCode" minOccurs="0"/>
 *         &lt;element name="languageCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}LanguageCodesEnum" minOccurs="0"/>
 *         &lt;element name="proFunctionCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}ProfessionalCodesEnum" minOccurs="0"/>
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
@XmlType(name = "IndividualProfil", propOrder = {
    "profilKey",
    "fbOptin",
    "flagSolvency",
    "proAreaCode",
    "proAreaWording",
    "civilianCode",
    "languageCode",
    "proFunctionCode",
    "proFunctionWording",
    "childrenNumber",
    "customerSegment",
    "studentCode"
})
public class IndividualProfil {

    protected String profilKey;
    protected String fbOptin;
    protected Boolean flagSolvency;
    protected String proAreaCode;
    protected String proAreaWording;
    protected String civilianCode;
    protected LanguageCodesEnum languageCode;
    protected ProfessionalCodesEnum proFunctionCode;
    protected String proFunctionWording;
    protected String childrenNumber;
    protected String customerSegment;
    protected String studentCode;

    /**
     * Gets the value of the profilKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfilKey() {
        return profilKey;
    }

    /**
     * Sets the value of the profilKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfilKey(String value) {
        this.profilKey = value;
    }

    /**
     * Gets the value of the fbOptin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFbOptin() {
        return fbOptin;
    }

    /**
     * Sets the value of the fbOptin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFbOptin(String value) {
        this.fbOptin = value;
    }

    /**
     * Gets the value of the flagSolvency property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagSolvency() {
        return flagSolvency;
    }

    /**
     * Sets the value of the flagSolvency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagSolvency(Boolean value) {
        this.flagSolvency = value;
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
     *     {@link LanguageCodesEnum }
     *     
     */
    public LanguageCodesEnum getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageCodesEnum }
     *     
     */
    public void setLanguageCode(LanguageCodesEnum value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the proFunctionCode property.
     * 
     * @return
     *     possible object is
     *     {@link ProfessionalCodesEnum }
     *     
     */
    public ProfessionalCodesEnum getProFunctionCode() {
        return proFunctionCode;
    }

    /**
     * Sets the value of the proFunctionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfessionalCodesEnum }
     *     
     */
    public void setProFunctionCode(ProfessionalCodesEnum value) {
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

}
