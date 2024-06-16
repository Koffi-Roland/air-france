
package com.afklm.repind.ws.provideindividualdata.v07.data.response;

import com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype.PrefilledNumbers;
import com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype.PrefilledNumbersData;
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
 * <p>Java class for PrefilledNumbersResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrefilledNumbersResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prefilledNumbers" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}PrefilledNumbers" minOccurs="0"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}Signature" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="prefilledNumbersData" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}PrefilledNumbersData" maxOccurs="100" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrefilledNumbersResponse", propOrder = {
    "prefilledNumbers",
    "signature",
    "prefilledNumbersData"
})
public class PrefilledNumbersResponse {

    protected PrefilledNumbers prefilledNumbers;
    protected List<Signature> signature;
    protected List<PrefilledNumbersData> prefilledNumbersData;

    /**
     * Gets the value of the prefilledNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link PrefilledNumbers }
     *     
     */
    public PrefilledNumbers getPrefilledNumbers() {
        return prefilledNumbers;
    }

    /**
     * Sets the value of the prefilledNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrefilledNumbers }
     *     
     */
    public void setPrefilledNumbers(PrefilledNumbers value) {
        this.prefilledNumbers = value;
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
     * Gets the value of the prefilledNumbersData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prefilledNumbersData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrefilledNumbersData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrefilledNumbersData }
     * 
     * 
     */
    public List<PrefilledNumbersData> getPrefilledNumbersData() {
        if (prefilledNumbersData == null) {
            prefilledNumbersData = new ArrayList<PrefilledNumbersData>();
        }
        return this.prefilledNumbersData;
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
