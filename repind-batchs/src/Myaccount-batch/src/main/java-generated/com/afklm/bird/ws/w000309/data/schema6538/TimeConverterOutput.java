
package com.afklm.bird.ws.w000309.data.schema6538;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TimeConverterOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeConverterOutput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_TimeConversionPointCode"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="hour" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Time"/>
 *         &lt;element name="jetLag" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_TimeZoneVariation"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeConverterOutput", propOrder = {
    "errorCode",
    "code",
    "date",
    "hour",
    "jetLag"
})
public class TimeConverterOutput {

    @XmlElement(required = true)
    protected String errorCode;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(required = true)
    protected String hour;
    @XmlElement(required = true)
    protected String jetLag;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the hour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHour() {
        return hour;
    }

    /**
     * Sets the value of the hour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHour(String value) {
        this.hour = value;
    }

    /**
     * Gets the value of the jetLag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJetLag() {
        return jetLag;
    }

    /**
     * Sets the value of the jetLag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJetLag(String value) {
        this.jetLag = value;
    }

}
