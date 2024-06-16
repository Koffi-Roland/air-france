
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype.Signature;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Utf complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Utf">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerNumber" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeUtfType"/>
 *         &lt;element name="utfDatas" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}UtfDatas" minOccurs="0"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}Signature" maxOccurs="2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Utf", propOrder = {
    "id",
    "type",
    "utfDatas",
    "signature"
})
public class Utf {

    protected String id;
    @XmlElement(required = true)
    protected String type;
    protected UtfDatas utfDatas;
    protected List<Signature> signature;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the utfDatas property.
     * 
     * @return
     *     possible object is
     *     {@link UtfDatas }
     *     
     */
    public UtfDatas getUtfDatas() {
        return utfDatas;
    }

    /**
     * Sets the value of the utfDatas property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtfDatas }
     *     
     */
    public void setUtfDatas(UtfDatas value) {
        this.utfDatas = value;
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
