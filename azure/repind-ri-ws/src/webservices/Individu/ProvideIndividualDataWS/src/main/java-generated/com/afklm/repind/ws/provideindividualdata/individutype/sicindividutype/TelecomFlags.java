
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TelecomFlags complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TelecomFlags">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flagInvalidFixTelecom" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="flagInvalidMobileTelecom" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="flagNoValidNormalizedTelecom" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelecomFlags", propOrder = {
    "flagInvalidFixTelecom",
    "flagInvalidMobileTelecom",
    "flagNoValidNormalizedTelecom"
})
public class TelecomFlags {

    protected Boolean flagInvalidFixTelecom;
    protected Boolean flagInvalidMobileTelecom;
    protected Boolean flagNoValidNormalizedTelecom;

    /**
     * Gets the value of the flagInvalidFixTelecom property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagInvalidFixTelecom() {
        return flagInvalidFixTelecom;
    }

    /**
     * Sets the value of the flagInvalidFixTelecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagInvalidFixTelecom(Boolean value) {
        this.flagInvalidFixTelecom = value;
    }

    /**
     * Gets the value of the flagInvalidMobileTelecom property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagInvalidMobileTelecom() {
        return flagInvalidMobileTelecom;
    }

    /**
     * Sets the value of the flagInvalidMobileTelecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagInvalidMobileTelecom(Boolean value) {
        this.flagInvalidMobileTelecom = value;
    }

    /**
     * Gets the value of the flagNoValidNormalizedTelecom property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagNoValidNormalizedTelecom() {
        return flagNoValidNormalizedTelecom;
    }

    /**
     * Sets the value of the flagNoValidNormalizedTelecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagNoValidNormalizedTelecom(Boolean value) {
        this.flagNoValidNormalizedTelecom = value;
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
