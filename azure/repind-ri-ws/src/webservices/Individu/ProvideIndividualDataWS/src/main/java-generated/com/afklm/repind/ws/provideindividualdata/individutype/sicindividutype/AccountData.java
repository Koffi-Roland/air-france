
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountIdentifier" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeIdentificationNumber" minOccurs="0"/>
 *         &lt;element name="emailIdentifier" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeEmail" minOccurs="0"/>
 *         &lt;element name="customerType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerType" minOccurs="0"/>
 *         &lt;element name="fbIdentifier" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFbIdentifier" minOccurs="0"/>
 *         &lt;element name="percentageFullProfil" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="personnalizedIdentifier" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePersonnalizedIdentifier" minOccurs="0"/>
 *         &lt;element name="secretQuestion" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSecretQuestion" minOccurs="0"/>
 *         &lt;element name="pos" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePointOfSell" minOccurs="0"/>
 *         &lt;element name="websiteCarrier" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeWebsiteCarrier" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountData", propOrder = {
    "accountIdentifier",
    "emailIdentifier",
    "customerType",
    "fbIdentifier",
    "percentageFullProfil",
    "personnalizedIdentifier",
    "secretQuestion",
    "pos",
    "websiteCarrier",
    "status"
})
public class AccountData {

    protected String accountIdentifier;
    protected String emailIdentifier;
    protected String customerType;
    protected String fbIdentifier;
    protected Integer percentageFullProfil;
    protected String personnalizedIdentifier;
    protected String secretQuestion;
    protected String pos;
    protected String websiteCarrier;
    protected String status;

    /**
     * Gets the value of the accountIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    /**
     * Sets the value of the accountIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountIdentifier(String value) {
        this.accountIdentifier = value;
    }

    /**
     * Gets the value of the emailIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailIdentifier() {
        return emailIdentifier;
    }

    /**
     * Sets the value of the emailIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailIdentifier(String value) {
        this.emailIdentifier = value;
    }

    /**
     * Gets the value of the customerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * Sets the value of the customerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerType(String value) {
        this.customerType = value;
    }

    /**
     * Gets the value of the fbIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFbIdentifier() {
        return fbIdentifier;
    }

    /**
     * Sets the value of the fbIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFbIdentifier(String value) {
        this.fbIdentifier = value;
    }

    /**
     * Gets the value of the percentageFullProfil property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPercentageFullProfil() {
        return percentageFullProfil;
    }

    /**
     * Sets the value of the percentageFullProfil property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPercentageFullProfil(Integer value) {
        this.percentageFullProfil = value;
    }

    /**
     * Gets the value of the personnalizedIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonnalizedIdentifier() {
        return personnalizedIdentifier;
    }

    /**
     * Sets the value of the personnalizedIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonnalizedIdentifier(String value) {
        this.personnalizedIdentifier = value;
    }

    /**
     * Gets the value of the secretQuestion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecretQuestion() {
        return secretQuestion;
    }

    /**
     * Sets the value of the secretQuestion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecretQuestion(String value) {
        this.secretQuestion = value;
    }

    /**
     * Gets the value of the pos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPos() {
        return pos;
    }

    /**
     * Sets the value of the pos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPos(String value) {
        this.pos = value;
    }

    /**
     * Gets the value of the websiteCarrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebsiteCarrier() {
        return websiteCarrier;
    }

    /**
     * Sets the value of the websiteCarrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebsiteCarrier(String value) {
        this.websiteCarrier = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
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
