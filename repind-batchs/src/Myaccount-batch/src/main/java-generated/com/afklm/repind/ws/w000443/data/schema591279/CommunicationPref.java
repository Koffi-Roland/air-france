
package com.afklm.repind.ws.w000443.data.schema591279;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for CommunicationPref complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommunicationPref">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="comGroupType" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeComGroupType"/>
 *         &lt;element name="comType" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeComType"/>
 *         &lt;element name="subscribe" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="media" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeMedia" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommunicationPref", propOrder = {
    "comGroupType",
    "comType",
    "subscribe",
    "media"
})
public class CommunicationPref {

    @XmlElement(required = true)
    protected String comGroupType;
    @XmlElement(required = true)
    protected String comType;
    protected boolean subscribe;
    protected List<String> media;

    /**
     * Gets the value of the comGroupType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComGroupType() {
        return comGroupType;
    }

    /**
     * Sets the value of the comGroupType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComGroupType(String value) {
        this.comGroupType = value;
    }

    /**
     * Gets the value of the comType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComType() {
        return comType;
    }

    /**
     * Sets the value of the comType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComType(String value) {
        this.comType = value;
    }

    /**
     * Gets the value of the subscribe property.
     * 
     */
    public boolean isSubscribe() {
        return subscribe;
    }

    /**
     * Sets the value of the subscribe property.
     * 
     */
    public void setSubscribe(boolean value) {
        this.subscribe = value;
    }

    /**
     * Gets the value of the media property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the media property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMedia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMedia() {
        if (media == null) {
            media = new ArrayList<String>();
        }
        return this.media;
    }

}
