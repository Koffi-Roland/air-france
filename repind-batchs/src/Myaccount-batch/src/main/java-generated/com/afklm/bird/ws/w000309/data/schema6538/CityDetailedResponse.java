
package com.afklm.bird.ws.w000309.data.schema6538;

import com.afklm.bird.ws.w000309.data.schema5299.CityDetailedWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for CityDetailedResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CityDetailedResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="foundCities" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}CityDetailedWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CityDetailedResponse", propOrder = {
    "foundCities"
})
public class CityDetailedResponse {

    protected List<CityDetailedWSDTO> foundCities;

    /**
     * Gets the value of the foundCities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundCities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundCities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CityDetailedWSDTO }
     * 
     * 
     */
    public List<CityDetailedWSDTO> getFoundCities() {
        if (foundCities == null) {
            foundCities = new ArrayList<CityDetailedWSDTO>();
        }
        return this.foundCities;
    }

}
