
package com.afklm.repind.ws.w000443.data.schema587190;

import com.afklm.repind.ws.w000443.data.schema588217.TypeContratEnum;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ContractType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contractNumber" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeNumeroContrat" minOccurs="0"/>
 *         &lt;element name="contractType" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}TypeContratEnum" minOccurs="0"/>
 *         &lt;element name="productType" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeTypeProduit" minOccurs="0"/>
 *         &lt;element name="productSubType" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeSousTypeProduit" minOccurs="0"/>
 *         &lt;element name="companyContractType" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeCodeCieContrat" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeVersionProduit" minOccurs="0"/>
 *         &lt;element name="contractStatus" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeEtatContrat" minOccurs="0"/>
 *         &lt;element name="validityStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="validityEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="tierLevel" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeNiveauTiers" minOccurs="0"/>
 *         &lt;element name="productFamilly" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeFamilleProduit" minOccurs="0"/>
 *         &lt;element name="iata" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeIata" minOccurs="0"/>
 *         &lt;element name="originCompany" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeOrganismeOrigine" minOccurs="0"/>
 *         &lt;element name="adhesionSource" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeSourceAdhesion" minOccurs="0"/>
 *         &lt;element name="primePermission" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypePermissionPriseDePrime" minOccurs="0"/>
 *         &lt;element name="milesBalance" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeSoldeMiles" minOccurs="0"/>
 *         &lt;element name="qualifyingMiles" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeMilesQualifiant" minOccurs="0"/>
 *         &lt;element name="qualifyingHistMiles" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeMilesQualifiantHist" minOccurs="0"/>
 *         &lt;element name="qualifyingFlights" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeSegmentsQualifiant" minOccurs="0"/>
 *         &lt;element name="qualifyingHistFlights" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeSegmentsQualifiantHist" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractType", propOrder = {
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
    "primePermission",
    "milesBalance",
    "qualifyingMiles",
    "qualifyingHistMiles",
    "qualifyingFlights",
    "qualifyingHistFlights"
})
public class ContractType {

    protected String contractNumber;
    protected TypeContratEnum contractType;
    protected String productType;
    protected String productSubType;
    protected String companyContractType;
    protected String version;
    protected String contractStatus;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validityStartDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validityEndDate;
    protected String tierLevel;
    protected String productFamilly;
    protected String iata;
    protected String originCompany;
    protected String adhesionSource;
    protected String primePermission;
    protected String milesBalance;
    protected String qualifyingMiles;
    protected String qualifyingHistMiles;
    protected String qualifyingFlights;
    protected String qualifyingHistFlights;

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
     *     {@link TypeContratEnum }
     *     
     */
    public TypeContratEnum getContractType() {
        return contractType;
    }

    /**
     * Sets the value of the contractType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeContratEnum }
     *     
     */
    public void setContractType(TypeContratEnum value) {
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
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidityStartDate() {
        return validityStartDate;
    }

    /**
     * Sets the value of the validityStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidityStartDate(XMLGregorianCalendar value) {
        this.validityStartDate = value;
    }

    /**
     * Gets the value of the validityEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidityEndDate() {
        return validityEndDate;
    }

    /**
     * Sets the value of the validityEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidityEndDate(XMLGregorianCalendar value) {
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
     * Gets the value of the primePermission property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimePermission() {
        return primePermission;
    }

    /**
     * Sets the value of the primePermission property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimePermission(String value) {
        this.primePermission = value;
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

}
