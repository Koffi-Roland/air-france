
package com.afklm.repind.ws.provideindividualdata.v07.data.response;

import com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype.Telecom;
import com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype.TelecomFlags;
import com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype.TelecomNormalization;
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
 * <p>Java class for TelecomResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TelecomResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="telecom" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Telecom" minOccurs="0"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}Signature" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="telecomFlags" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}TelecomFlags" minOccurs="0"/>
 *         &lt;element name="telecomNormalization" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}TelecomNormalization" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelecomResponse", propOrder = {
    "telecom",
    "signature",
    "telecomFlags",
    "telecomNormalization"
})
public class TelecomResponse {

    protected Telecom telecom;
    protected List<Signature> signature;
    protected TelecomFlags telecomFlags;
    protected TelecomNormalization telecomNormalization;

    /**
     * Gets the value of the telecom property.
     * 
     * @return
     *     possible object is
     *     {@link Telecom }
     *     
     */
    public Telecom getTelecom() {
        return telecom;
    }

    /**
     * Sets the value of the telecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Telecom }
     *     
     */
    public void setTelecom(Telecom value) {
        this.telecom = value;
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
     * Gets the value of the telecomFlags property.
     * 
     * @return
     *     possible object is
     *     {@link TelecomFlags }
     *     
     */
    public TelecomFlags getTelecomFlags() {
        return telecomFlags;
    }

    /**
     * Sets the value of the telecomFlags property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelecomFlags }
     *     
     */
    public void setTelecomFlags(TelecomFlags value) {
        this.telecomFlags = value;
    }

    /**
     * Gets the value of the telecomNormalization property.
     * 
     * @return
     *     possible object is
     *     {@link TelecomNormalization }
     *     
     */
    public TelecomNormalization getTelecomNormalization() {
        return telecomNormalization;
    }

    /**
     * Sets the value of the telecomNormalization property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelecomNormalization }
     *     
     */
    public void setTelecomNormalization(TelecomNormalization value) {
        this.telecomNormalization = value;
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
