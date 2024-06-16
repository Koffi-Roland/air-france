
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.request;

import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.Civilian;
import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.IndividualInformationsV3;
import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.IndividualProfilV3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="individualInformations" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}IndividualInformationsV3" minOccurs="0"/>
 *         &lt;element name="individualProfil" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}IndividualProfilV3" minOccurs="0"/>
 *         &lt;element name="civilian" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Civilian" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualRequest", propOrder = {
    "individualInformations",
    "individualProfil",
    "civilian"
})
public class IndividualRequest {

    protected IndividualInformationsV3 individualInformations;
    protected IndividualProfilV3 individualProfil;
    protected Civilian civilian;

    /**
     * Gets the value of the individualInformations property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualInformationsV3 }
     *     
     */
    public IndividualInformationsV3 getIndividualInformations() {
        return individualInformations;
    }

    /**
     * Sets the value of the individualInformations property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualInformationsV3 }
     *     
     */
    public void setIndividualInformations(IndividualInformationsV3 value) {
        this.individualInformations = value;
    }

    /**
     * Gets the value of the individualProfil property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualProfilV3 }
     *     
     */
    public IndividualProfilV3 getIndividualProfil() {
        return individualProfil;
    }

    /**
     * Sets the value of the individualProfil property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualProfilV3 }
     *     
     */
    public void setIndividualProfil(IndividualProfilV3 value) {
        this.individualProfil = value;
    }

    /**
     * Gets the value of the civilian property.
     * 
     * @return
     *     possible object is
     *     {@link Civilian }
     *     
     */
    public Civilian getCivilian() {
        return civilian;
    }

    /**
     * Sets the value of the civilian property.
     * 
     * @param value
     *     allowed object is
     *     {@link Civilian }
     *     
     */
    public void setCivilian(Civilian value) {
        this.civilian = value;
    }

}
