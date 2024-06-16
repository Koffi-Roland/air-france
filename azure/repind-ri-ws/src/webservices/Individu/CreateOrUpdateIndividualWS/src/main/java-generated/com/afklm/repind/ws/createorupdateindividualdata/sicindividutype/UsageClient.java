
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;


/**
 * <p>Java class for UsageClient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UsageClient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="srin" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSrin" minOccurs="0"/>
 *         &lt;element name="applicationCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeApplicationCode" minOccurs="0"/>
 *         &lt;element name="authorizedModification" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeModifAutorisee" minOccurs="0"/>
 *         &lt;element name="lastModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsageClient", propOrder = {
    "srin",
    "applicationCode",
    "authorizedModification",
    "lastModificationDate"
})
public class UsageClient {

    protected String srin;
    protected String applicationCode;
    protected String authorizedModification;
    @XmlSchemaType(name = "dateTime")
    protected Date lastModificationDate;

    /**
     * Gets the value of the srin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrin() {
        return srin;
    }

    /**
     * Sets the value of the srin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrin(String value) {
        this.srin = value;
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
     * Gets the value of the authorizedModification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizedModification() {
        return authorizedModification;
    }

    /**
     * Sets the value of the authorizedModification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizedModification(String value) {
        this.authorizedModification = value;
    }

    /**
     * Gets the value of the lastModificationDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    /**
     * Sets the value of the lastModificationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setLastModificationDate(Date value) {
        this.lastModificationDate = value;
    }

}
