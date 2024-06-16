
package com.afklm.repind.ws.provideindividualdata.v07.data.response;

import com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype.*;
import com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype.Signature;
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
 * <p>Java class for IndividualResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="individualInformations" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}IndividualInformationsV2" minOccurs="0"/>
 *         &lt;element name="individualProfil" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}IndividualProfilV2" minOccurs="0"/>
 *         &lt;element name="normalizedName" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}NormalizedName" minOccurs="0"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}Signature" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="airFranceProfil" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}AirFranceProfilV2" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="civilian" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Civilian" minOccurs="0"/>
 *         &lt;element name="usageClient" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}UsageClient" maxOccurs="255" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualResponse", propOrder = {
    "individualInformations",
    "individualProfil",
    "normalizedName",
    "signature",
    "airFranceProfil",
    "civilian",
    "usageClient"
})
public class IndividualResponse {

    protected IndividualInformationsV2 individualInformations;
    protected IndividualProfilV2 individualProfil;
    protected NormalizedName normalizedName;
    protected List<Signature> signature;
    protected List<AirFranceProfilV2> airFranceProfil;
    protected Civilian civilian;
    protected List<UsageClient> usageClient;

    /**
     * Gets the value of the individualInformations property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualInformationsV2 }
     *     
     */
    public IndividualInformationsV2 getIndividualInformations() {
        return individualInformations;
    }

    /**
     * Sets the value of the individualInformations property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualInformationsV2 }
     *     
     */
    public void setIndividualInformations(IndividualInformationsV2 value) {
        this.individualInformations = value;
    }

    /**
     * Gets the value of the individualProfil property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualProfilV2 }
     *     
     */
    public IndividualProfilV2 getIndividualProfil() {
        return individualProfil;
    }

    /**
     * Sets the value of the individualProfil property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualProfilV2 }
     *     
     */
    public void setIndividualProfil(IndividualProfilV2 value) {
        this.individualProfil = value;
    }

    /**
     * Gets the value of the normalizedName property.
     * 
     * @return
     *     possible object is
     *     {@link NormalizedName }
     *     
     */
    public NormalizedName getNormalizedName() {
        return normalizedName;
    }

    /**
     * Sets the value of the normalizedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link NormalizedName }
     *     
     */
    public void setNormalizedName(NormalizedName value) {
        this.normalizedName = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Signature }
     * 
     * 
     */
    public List<Signature> getSignature() {
        if (signature == null) {
            signature = new ArrayList<Signature>();
        }
        return this.signature;
    }

    /**
     * Gets the value of the airFranceProfil property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the airFranceProfil property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAirFranceProfil().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AirFranceProfilV2 }
     * 
     * 
     */
    public List<AirFranceProfilV2> getAirFranceProfil() {
        if (airFranceProfil == null) {
            airFranceProfil = new ArrayList<AirFranceProfilV2>();
        }
        return this.airFranceProfil;
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

    /**
     * Gets the value of the usageClient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the usageClient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsageClient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UsageClient }
     * 
     * 
     */
    public List<UsageClient> getUsageClient() {
        if (usageClient == null) {
            usageClient = new ArrayList<UsageClient>();
        }
        return this.usageClient;
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
