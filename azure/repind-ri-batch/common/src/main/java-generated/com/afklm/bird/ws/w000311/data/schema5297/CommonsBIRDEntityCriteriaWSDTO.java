
package com.afklm.bird.ws.w000311.data.schema5297;

import com.afklm.bird.ws.w000311.data.schema5299.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommonsBIRDEntityCriteriaWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommonsBIRDEntityCriteriaWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityWSDTO">
 *       &lt;sequence>
 *         &lt;element name="startDateOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_BasicOperator" minOccurs="0"/>
 *         &lt;element name="endDateOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_BasicOperator" minOccurs="0"/>
 *         &lt;element name="modificationDateOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_BasicOperator" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonsBIRDEntityCriteriaWSDTO", propOrder = {
    "startDateOperator",
    "endDateOperator",
    "modificationDateOperator"
})
@XmlSeeAlso({
    StationCriteriaWSDTO.class,
    CountryCriteriaWSDTO.class,
    CityCriteriaWSDTO.class,
    TerminalCriteriaWSDTO.class,
    StateProvinceCriteriaWSDTO.class
})
public class CommonsBIRDEntityCriteriaWSDTO
    extends CommonsBIRDEntityWSDTO
{

    protected String startDateOperator;
    protected String endDateOperator;
    protected String modificationDateOperator;

    /**
     * Gets the value of the startDateOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDateOperator() {
        return startDateOperator;
    }

    /**
     * Sets the value of the startDateOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDateOperator(String value) {
        this.startDateOperator = value;
    }

    /**
     * Gets the value of the endDateOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDateOperator() {
        return endDateOperator;
    }

    /**
     * Sets the value of the endDateOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDateOperator(String value) {
        this.endDateOperator = value;
    }

    /**
     * Gets the value of the modificationDateOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModificationDateOperator() {
        return modificationDateOperator;
    }

    /**
     * Sets the value of the modificationDateOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModificationDateOperator(String value) {
        this.modificationDateOperator = value;
    }

}
