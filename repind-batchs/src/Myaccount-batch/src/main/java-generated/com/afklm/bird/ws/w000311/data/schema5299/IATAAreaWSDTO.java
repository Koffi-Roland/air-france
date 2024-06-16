
package com.afklm.bird.ws.w000311.data.schema5299;

import com.afklm.bird.ws.w000311.data.schema5297.CommonsBIRDEntityWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IATAAreaWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IATAAreaWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityWSDTO">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATAAreaCode" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IATAAreaWSDTO", propOrder = {
    "code",
    "name"
})
public class IATAAreaWSDTO
    extends CommonsBIRDEntityWSDTO
{

    protected String code;
    protected String name;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
