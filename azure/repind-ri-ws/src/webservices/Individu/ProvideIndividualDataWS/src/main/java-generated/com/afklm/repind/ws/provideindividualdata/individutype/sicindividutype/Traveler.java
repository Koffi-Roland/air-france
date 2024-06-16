
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.*;
import java.util.Date;


/**
 * <p>Java class for Traveler complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Traveler">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lastRecognitionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="matchingRecognition" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeOption" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Traveler", propOrder = {
    "lastRecognitionDate",
    "matchingRecognition"
})
public class Traveler {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected Date lastRecognitionDate;
    protected String matchingRecognition;

    /**
     * Gets the value of the lastRecognitionDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getLastRecognitionDate() {
        return lastRecognitionDate;
    }

    /**
     * Sets the value of the lastRecognitionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setLastRecognitionDate(Date value) {
        this.lastRecognitionDate = value;
    }

    /**
     * Gets the value of the matchingRecognition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatchingRecognition() {
        return matchingRecognition;
    }

    /**
     * Sets the value of the matchingRecognition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatchingRecognition(String value) {
        this.matchingRecognition = value;
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
