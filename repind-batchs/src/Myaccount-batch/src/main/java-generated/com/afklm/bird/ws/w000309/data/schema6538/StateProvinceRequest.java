
package com.afklm.bird.ws.w000309.data.schema6538;

import com.afklm.bird.ws.w000309.data.schema5299.StateProvinceCriteriaWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for StateProvinceRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StateProvinceRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateCriteria" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="stateProvinceCriteria" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}StateProvinceCriteriaWSDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StateProvinceRequest", propOrder = {
    "dateCriteria",
    "stateProvinceCriteria"
})
public class StateProvinceRequest {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCriteria;
    protected StateProvinceCriteriaWSDTO stateProvinceCriteria;

    /**
     * Gets the value of the dateCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCriteria() {
        return dateCriteria;
    }

    /**
     * Sets the value of the dateCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCriteria(XMLGregorianCalendar value) {
        this.dateCriteria = value;
    }

    /**
     * Gets the value of the stateProvinceCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link StateProvinceCriteriaWSDTO }
     *     
     */
    public StateProvinceCriteriaWSDTO getStateProvinceCriteria() {
        return stateProvinceCriteria;
    }

    /**
     * Sets the value of the stateProvinceCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateProvinceCriteriaWSDTO }
     *     
     */
    public void setStateProvinceCriteria(StateProvinceCriteriaWSDTO value) {
        this.stateProvinceCriteria = value;
    }

}
