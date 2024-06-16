
package com.afklm.repind.ws.provideindividualdata.v07.data.response;

import com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype.Localization;
import com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype.SignatureV2;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for LocalizationResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocalizationResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="localization" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Localization" minOccurs="0"/>
 *         &lt;element name="signatureV2" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}SignatureV2" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="localizationResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}LocalizationResponse" maxOccurs="5" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocalizationResponse", propOrder = {
    "localization",
    "signatureV2",
    "localizationResponse"
})
public class LocalizationResponse {

    protected Localization localization;
    protected List<SignatureV2> signatureV2;
    protected List<LocalizationResponse> localizationResponse;

    /**
     * Gets the value of the localization property.
     * 
     * @return
     *     possible object is
     *     {@link Localization }
     *     
     */
    public Localization getLocalization() {
        return localization;
    }

    /**
     * Sets the value of the localization property.
     * 
     * @param value
     *     allowed object is
     *     {@link Localization }
     *     
     */
    public void setLocalization(Localization value) {
        this.localization = value;
    }

    /**
     * Gets the value of the signatureV2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signatureV2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignatureV2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignatureV2 }
     * 
     * 
     */
    public List<SignatureV2> getSignatureV2() {
        if (signatureV2 == null) {
            signatureV2 = new ArrayList<SignatureV2>();
        }
        return this.signatureV2;
    }

    /**
     * Gets the value of the localizationResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the localizationResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocalizationResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocalizationResponse }
     * 
     * 
     */
    public List<LocalizationResponse> getLocalizationResponse() {
        if (localizationResponse == null) {
            localizationResponse = new ArrayList<LocalizationResponse>();
        }
        return this.localizationResponse;
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
