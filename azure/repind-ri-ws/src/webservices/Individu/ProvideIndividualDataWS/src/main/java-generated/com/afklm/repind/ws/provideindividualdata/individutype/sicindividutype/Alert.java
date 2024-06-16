
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Alert complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Alert">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeAlertDataType" minOccurs="0"/>
 *         &lt;element name="alertId" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeAlertID" minOccurs="0"/>
 *         &lt;element name="optIn" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeAlertOptIn"/>
 *         &lt;element name="alertData" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}AlertData" maxOccurs="500" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alert", propOrder = {
    "type",
    "alertId",
    "optIn",
    "alertData"
})
public class Alert {

    protected String type;
    protected String alertId;
    @XmlElement(required = true)
    protected String optIn;
    protected List<AlertData> alertData;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the alertId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertId() {
        return alertId;
    }

    /**
     * Sets the value of the alertId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertId(String value) {
        this.alertId = value;
    }

    /**
     * Gets the value of the optIn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptIn() {
        return optIn;
    }

    /**
     * Sets the value of the optIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptIn(String value) {
        this.optIn = value;
    }

    /**
     * Gets the value of the alertData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alertData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlertData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AlertData }
     * 
     * 
     */
    public List<AlertData> getAlertData() {
        if (alertData == null) {
            alertData = new ArrayList<AlertData>();
        }
        return this.alertData;
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
