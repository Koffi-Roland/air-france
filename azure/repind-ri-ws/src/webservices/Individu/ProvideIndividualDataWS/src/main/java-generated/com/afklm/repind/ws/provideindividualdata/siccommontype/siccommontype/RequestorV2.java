
package com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="channel" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeChannel"/>
 *         &lt;element name="matricule" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMatricule" minOccurs="0"/>
 *         &lt;element name="managingCompany" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeManagingCompany" minOccurs="0"/>
 *         &lt;element name="officeId" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeOfficeId" minOccurs="0"/>
 *         &lt;element name="context" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeContext" minOccurs="0"/>
 *         &lt;element name="loggedGin" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerNumber" minOccurs="0"/>
 *         &lt;element name="reconciliationDataCIN" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerNumber" minOccurs="0"/>
 *         &lt;element name="token" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeToken" minOccurs="0"/>
 *         &lt;element name="ipAddress" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeIpAddress" minOccurs="0"/>
 *         &lt;element name="applicationCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeApplicationCode" minOccurs="0"/>
 *         &lt;element name="site" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSite"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSignature"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorV2", propOrder = {
    "channel",
    "matricule",
    "managingCompany",
    "officeId",
    "context",
    "loggedGin",
    "reconciliationDataCIN",
    "token",
    "ipAddress",
    "applicationCode",
    "site",
    "signature"
})
public class RequestorV2 {

    @XmlElement(required = true)
    protected String channel;
    protected String matricule;
    protected String managingCompany;
    protected String officeId;
    protected String context;
    protected String loggedGin;
    protected String reconciliationDataCIN;
    protected String token;
    protected String ipAddress;
    protected String applicationCode;
    @XmlElement(required = true)
    protected String site;
    @XmlElement(required = true)
    protected String signature;

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    /**
     * Gets the value of the matricule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Sets the value of the matricule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatricule(String value) {
        this.matricule = value;
    }

    /**
     * Gets the value of the managingCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagingCompany() {
        return managingCompany;
    }

    /**
     * Sets the value of the managingCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagingCompany(String value) {
        this.managingCompany = value;
    }

    /**
     * Gets the value of the officeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficeId() {
        return officeId;
    }

    /**
     * Sets the value of the officeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficeId(String value) {
        this.officeId = value;
    }

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContext(String value) {
        this.context = value;
    }

    /**
     * Gets the value of the loggedGin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoggedGin() {
        return loggedGin;
    }

    /**
     * Sets the value of the loggedGin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoggedGin(String value) {
        this.loggedGin = value;
    }

    /**
     * Gets the value of the reconciliationDataCIN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReconciliationDataCIN() {
        return reconciliationDataCIN;
    }

    /**
     * Sets the value of the reconciliationDataCIN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReconciliationDataCIN(String value) {
        this.reconciliationDataCIN = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the ipAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Sets the value of the ipAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpAddress(String value) {
        this.ipAddress = value;
    }

    /**
     * Gets the value of the applicationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationCode() {
        return applicationCode;
    }

    /**
     * Sets the value of the applicationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationCode(String value) {
        this.applicationCode = value;
    }

    /**
     * Gets the value of the site property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSite() {
        return site;
    }

    /**
     * Sets the value of the site property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSite(String value) {
        this.site = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignature(String value) {
        this.signature = value;
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
