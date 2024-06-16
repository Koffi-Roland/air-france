
package com.afklm.bird.ws.w000309.data.schema6538;

import com.afklm.bird.ws.w000309.data.schema5299.CountryWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for CountryResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CountryResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="foundCountries" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}CountryWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountryResponse", propOrder = {
    "foundCountries"
})
public class CountryResponse {

    protected List<CountryWSDTO> foundCountries;

    /**
     * Gets the value of the foundCountries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundCountries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundCountries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CountryWSDTO }
     * 
     * 
     */
    public List<CountryWSDTO> getFoundCountries() {
        if (foundCountries == null) {
            foundCountries = new ArrayList<CountryWSDTO>();
        }
        return this.foundCountries;
    }

}
