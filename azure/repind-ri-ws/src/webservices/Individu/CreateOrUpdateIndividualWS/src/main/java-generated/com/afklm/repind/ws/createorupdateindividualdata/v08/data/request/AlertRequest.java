
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.request;

import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.Alert;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for AlertRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlertRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alert" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Alert" maxOccurs="50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlertRequest", propOrder = {
    "alert"
})
public class AlertRequest {

    protected List<Alert> alert;

    /**
     * Gets the value of the alert property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alert property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlert().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Alert }
     * 
     * 
     */
    public List<Alert> getAlert() {
        if (alert == null) {
            alert = new ArrayList<Alert>();
        }
        return this.alert;
    }

}
