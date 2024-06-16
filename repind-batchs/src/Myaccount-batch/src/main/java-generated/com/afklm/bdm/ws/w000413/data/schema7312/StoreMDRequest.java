
package com.afklm.bdm.ws.w000413.data.schema7312;

import com.afklm.bdm.ws.w000413.data.schema7309.MarketingData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * update the marketing data of an individual in the BDM.

 * for an individual you can update 5 types of components:

 *   - Personal information 

 *   - TravelCompanions

 *   - TravelDocument

 *   - APIS data

 *   - TravelPreference. 

 * Any modifcation component will follow the rule cancel/replace.

 * A null component will remain unchanged.

 * 

 * 
 * 
 * <p>Java class for StoreMDRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StoreMDRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="marketingData" type="{http://www.af-klm.com/services/marketingdata/common-v1/xsd}MarketingData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StoreMDRequest", propOrder = {
    "marketingData"
})
public class StoreMDRequest {

    @XmlElement(required = true)
    protected MarketingData marketingData;

    /**
     * Gets the value of the marketingData property.
     * 
     * @return
     *     possible object is
     *     {@link MarketingData }
     *     
     */
    public MarketingData getMarketingData() {
        return marketingData;
    }

    /**
     * Sets the value of the marketingData property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketingData }
     *     
     */
    public void setMarketingData(MarketingData value) {
        this.marketingData = value;
    }

}
