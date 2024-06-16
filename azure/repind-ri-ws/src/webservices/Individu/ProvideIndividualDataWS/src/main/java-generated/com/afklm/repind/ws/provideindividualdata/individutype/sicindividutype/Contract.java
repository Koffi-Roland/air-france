
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;


/**
 * <p>Java class for Contract complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Contract">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contractNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNumeroContrat" minOccurs="0"/>
 *         &lt;element name="contractType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeContractType" minOccurs="0"/>
 *         &lt;element name="productType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeTypeProduit" minOccurs="0"/>
 *         &lt;element name="productSubType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSousTypeProduit" minOccurs="0"/>
 *         &lt;element name="companyContractType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCodeCieContrat" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeVersionProduit" minOccurs="0"/>
 *         &lt;element name="contractStatus" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeContractStatus" minOccurs="0"/>
 *         &lt;element name="validityStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="validityEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="tierLevel" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeTierLevel" minOccurs="0"/>
 *         &lt;element name="productFamilly" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFamilleProduit" minOccurs="0"/>
 *         &lt;element name="iata" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeIata" minOccurs="0"/>
 *         &lt;element name="originCompany" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeOrganismeOrigine" minOccurs="0"/>
 *         &lt;element name="adhesionSource" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSourceAdhesion" minOccurs="0"/>
 *         &lt;element name="bonusPermission" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeBonusPermission" minOccurs="0"/>
 *         &lt;element name="milesBalance" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSoldeMiles" minOccurs="0"/>
 *         &lt;element name="qualifyingMiles" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMilesQualifiant" minOccurs="0"/>
 *         &lt;element name="qualifyingHistMiles" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMilesQualifiantHist" minOccurs="0"/>
 *         &lt;element name="qualifyingFlights" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSegmentsQualifiant" minOccurs="0"/>
 *         &lt;element name="qualifyingHistFlights" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSegmentsQualifiantHist" minOccurs="0"/>
 *         &lt;element name="memberType" type="{http://www.af-klm.com/services/passenger/SicOsirisType-v1/xsd}DTypeMemberType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contract", propOrder = {
    "contractNumber",
    "contractType",
    "productType",
    "productSubType",
    "companyContractType",
    "version",
    "contractStatus",
    "validityStartDate",
    "validityEndDate",
    "tierLevel",
    "productFamilly",
    "iata",
    "originCompany",
    "adhesionSource",
    "bonusPermission",
    "milesBalance",
    "qualifyingMiles",
    "qualifyingHistMiles",
    "qualifyingFlights",
    "qualifyingHistFlights",
    "memberType"
})
public class Contract {

    protected String contractNumber;
    protected String contractType;
    protected String productType;
    protected String productSubType;
    protected String companyContractType;
    protected String version;
    protected String contractStatus;
    @XmlSchemaType(name = "dateTime")
    protected Date validityStartDate;
    @XmlSchemaType(name = "dateTime")
    protected Date validityEndDate;
    protected String tierLevel;
    protected String productFamilly;
    protected String iata;
    protected String originCompany;
    protected String adhesionSource;
    protected String bonusPermission;
    protected String milesBalance;
    protected String qualifyingMiles;
    protected String qualifyingHistMiles;
    protected String qualifyingFlights;
    protected String qualifyingHistFlights;
    protected String memberType;

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
     * Gets the value of the companyContractType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyContractType() {
        return companyContractType;
    }

    /**
     * Sets the value of the companyContractType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyContractType(String value) {
        this.companyContractType = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
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
     * Gets the value of the tierLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTierLevel() {
        return tierLevel;
    }

    /**
     * Sets the value of the tierLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTierLevel(String value) {
        this.tierLevel = value;
    }

    /**
     * Gets the value of the productFamilly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductFamilly() {
        return productFamilly;
    }

    /**
     * Sets the value of the productFamilly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductFamilly(String value) {
        this.productFamilly = value;
    }

    /**
     * Gets the value of the iata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIata() {
        return iata;
    }

    /**
     * Sets the value of the iata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIata(String value) {
        this.iata = value;
    }

    /**
     * Gets the value of the originCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginCompany() {
        return originCompany;
    }

    /**
     * Sets the value of the originCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginCompany(String value) {
        this.originCompany = value;
    }

    /**
     * Gets the value of the adhesionSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdhesionSource() {
        return adhesionSource;
    }

    /**
     * Sets the value of the adhesionSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdhesionSource(String value) {
        this.adhesionSource = value;
    }

    /**
     * Gets the value of the bonusPermission property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBonusPermission() {
        return bonusPermission;
    }

    /**
     * Sets the value of the bonusPermission property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBonusPermission(String value) {
        this.bonusPermission = value;
    }

    /**
     * Gets the value of the milesBalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMilesBalance() {
        return milesBalance;
    }

    /**
     * Sets the value of the milesBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMilesBalance(String value) {
        this.milesBalance = value;
    }

    /**
     * Gets the value of the qualifyingMiles property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifyingMiles() {
        return qualifyingMiles;
    }

    /**
     * Sets the value of the qualifyingMiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifyingMiles(String value) {
        this.qualifyingMiles = value;
    }

    /**
     * Gets the value of the qualifyingHistMiles property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifyingHistMiles() {
        return qualifyingHistMiles;
    }

    /**
     * Sets the value of the qualifyingHistMiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifyingHistMiles(String value) {
        this.qualifyingHistMiles = value;
    }

    /**
     * Gets the value of the qualifyingFlights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifyingFlights() {
        return qualifyingFlights;
    }

    /**
     * Sets the value of the qualifyingFlights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifyingFlights(String value) {
        this.qualifyingFlights = value;
    }

    /**
     * Gets the value of the qualifyingHistFlights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifyingHistFlights() {
        return qualifyingHistFlights;
    }

    /**
     * Sets the value of the qualifyingHistFlights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifyingHistFlights(String value) {
        this.qualifyingHistFlights = value;
    }

    /**
     * Gets the value of the memberType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemberType() {
        return memberType;
    }

    /**
     * Sets the value of the memberType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemberType(String value) {
        this.memberType = value;
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
