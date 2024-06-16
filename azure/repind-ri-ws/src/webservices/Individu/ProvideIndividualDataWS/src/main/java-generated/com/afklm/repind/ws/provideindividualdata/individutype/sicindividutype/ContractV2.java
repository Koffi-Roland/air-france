
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.*;
import java.util.Date;


/**
 * <p>Java class for ContractV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contractNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNumeroContrat" minOccurs="0"/>
 *         &lt;element name="contractType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeContractType" minOccurs="0"/>
 *         &lt;element name="productType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeTypeProduit"/>
 *         &lt;element name="productSubType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSousTypeProduit" minOccurs="0"/>
 *         &lt;element name="companyCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCodeCieContrat" minOccurs="0"/>
 *         &lt;element name="contractStatus" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeContractStatus"/>
 *         &lt;element name="validityStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="validityEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="iataCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeIata" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractV2", propOrder = {
    "contractNumber",
    "contractType",
    "productType",
    "productSubType",
    "companyCode",
    "contractStatus",
    "validityStartDate",
    "validityEndDate",
    "iataCode"
})
public class ContractV2 {

    protected String contractNumber;
    protected String contractType;
    @XmlElement(required = true)
    protected String productType;
    protected String productSubType;
    protected String companyCode;
    @XmlElement(required = true)
    protected String contractStatus;
    @XmlSchemaType(name = "dateTime")
    protected Date validityStartDate;
    @XmlSchemaType(name = "dateTime")
    protected Date validityEndDate;
    protected String iataCode;

    /**
     * Gets the value of the contractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * Sets the value of the contractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
    }

    /**
     * Gets the value of the contractType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * Sets the value of the contractType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractType(String value) {
        this.contractType = value;
    }

    /**
     * Gets the value of the productType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Sets the value of the productType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductType(String value) {
        this.productType = value;
    }

    /**
     * Gets the value of the productSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductSubType() {
        return productSubType;
    }

    /**
     * Sets the value of the productSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductSubType(String value) {
        this.productSubType = value;
    }

    /**
     * Gets the value of the companyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * Sets the value of the companyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyCode(String value) {
        this.companyCode = value;
    }

    /**
     * Gets the value of the contractStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractStatus() {
        return contractStatus;
    }

    /**
     * Sets the value of the contractStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractStatus(String value) {
        this.contractStatus = value;
    }

    /**
     * Gets the value of the validityStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getValidityStartDate() {
        return validityStartDate;
    }

    /**
     * Sets the value of the validityStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setValidityStartDate(Date value) {
        this.validityStartDate = value;
    }

    /**
     * Gets the value of the validityEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getValidityEndDate() {
        return validityEndDate;
    }

    /**
     * Sets the value of the validityEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setValidityEndDate(Date value) {
        this.validityEndDate = value;
    }

    /**
     * Gets the value of the iataCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCode() {
        return iataCode;
    }

    /**
     * Sets the value of the iataCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCode(String value) {
        this.iataCode = value;
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
