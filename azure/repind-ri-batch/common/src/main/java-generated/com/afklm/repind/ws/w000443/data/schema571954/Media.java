
package com.afklm.repind.ws.w000443.data.schema571954;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Media complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Media">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="media1" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMedia" minOccurs="0"/>
 *         &lt;element name="media2" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMedia" minOccurs="0"/>
 *         &lt;element name="media3" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMedia" minOccurs="0"/>
 *         &lt;element name="media4" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMedia" minOccurs="0"/>
 *         &lt;element name="media5" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMedia" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Media", propOrder = {
    "media1",
    "media2",
    "media3",
    "media4",
    "media5"
})
public class Media {

    protected String media1;
    protected String media2;
    protected String media3;
    protected String media4;
    protected String media5;

    /**
     * Gets the value of the media1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia1() {
        return media1;
    }

    /**
     * Sets the value of the media1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia1(String value) {
        this.media1 = value;
    }

    /**
     * Gets the value of the media2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia2() {
        return media2;
    }

    /**
     * Sets the value of the media2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia2(String value) {
        this.media2 = value;
    }

    /**
     * Gets the value of the media3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia3() {
        return media3;
    }

    /**
     * Sets the value of the media3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia3(String value) {
        this.media3 = value;
    }

    /**
     * Gets the value of the media4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia4() {
        return media4;
    }

    /**
     * Sets the value of the media4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia4(String value) {
        this.media4 = value;
    }

    /**
     * Gets the value of the media5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia5() {
        return media5;
    }

    /**
     * Sets the value of the media5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia5(String value) {
        this.media5 = value;
    }

}
