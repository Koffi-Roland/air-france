
package com.afklm.bird.ws.w000311.data.schema6538;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for TimeConverterRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeConverterRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typeConv" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_TimeConversionType"/>
 *         &lt;element name="points" type="{http://www.af-klm.com/services/passenger/geographyOperationData-v1/xsd}TimeConverterPoint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeConverterRequest", propOrder = {
    "typeConv",
    "points"
})
public class TimeConverterRequest {

    @XmlElement(required = true)
    protected String typeConv;
    protected List<TimeConverterPoint> points;

    /**
     * Gets the value of the typeConv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeConv() {
        return typeConv;
    }

    /**
     * Sets the value of the typeConv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeConv(String value) {
        this.typeConv = value;
    }

    /**
     * Gets the value of the points property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the points property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPoints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeConverterPoint }
     * 
     * 
     */
    public List<TimeConverterPoint> getPoints() {
        if (points == null) {
            points = new ArrayList<TimeConverterPoint>();
        }
        return this.points;
    }

}
