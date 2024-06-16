
package com.afklm.bird.ws.w000311.data.schema5299;

import com.afklm.bird.ws.w000311.data.schema5297.CommonsBIRDEntityWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeZoneExceptionWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeZoneExceptionWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityWSDTO">
 *       &lt;sequence>
 *         &lt;element name="startTime" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Time" minOccurs="0"/>
 *         &lt;element name="timeZoneCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_TimeZoneCode" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Time" minOccurs="0"/>
 *         &lt;element name="dstAdjust" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_TimeZoneVariation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeZoneExceptionWSDTO", propOrder = {
    "startTime",
    "timeZoneCode",
    "endTime",
    "dstAdjust"
})
public class TimeZoneExceptionWSDTO
    extends CommonsBIRDEntityWSDTO
{

    protected String startTime;
    protected String timeZoneCode;
    protected String endTime;
    protected String dstAdjust;

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the timeZoneCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeZoneCode() {
        return timeZoneCode;
    }

    /**
     * Sets the value of the timeZoneCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeZoneCode(String value) {
        this.timeZoneCode = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the dstAdjust property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDstAdjust() {
        return dstAdjust;
    }

    /**
     * Sets the value of the dstAdjust property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDstAdjust(String value) {
        this.dstAdjust = value;
    }

}
