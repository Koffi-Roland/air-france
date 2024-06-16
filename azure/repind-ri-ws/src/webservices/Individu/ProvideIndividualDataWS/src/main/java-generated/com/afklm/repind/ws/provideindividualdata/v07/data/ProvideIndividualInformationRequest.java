
package com.afklm.repind.ws.provideindividualdata.v07.data;

import com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype.RequestorV2;
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
 * <p>Java class for ProvideIndividualInformationRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProvideIndividualInformationRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificationNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeGenericIdentifierV2"/>
 *         &lt;element name="option" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeOption"/>
 *         &lt;element name="scopeToProvide" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeScope" maxOccurs="17" minOccurs="0"/>
 *         &lt;element name="requestor" type="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}RequestorV2"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProvideIndividualInformationRequest", propOrder = {
    "identificationNumber",
    "option",
    "scopeToProvide",
    "requestor"
})
public class ProvideIndividualInformationRequest {

    @XmlElement(required = true)
    protected String identificationNumber;
    @XmlElement(required = true)
    protected String option;
    protected List<String> scopeToProvide;
    @XmlElement(required = true)
    protected RequestorV2 requestor;

    /**
     * Gets the value of the identificationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * Sets the value of the identificationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificationNumber(String value) {
        this.identificationNumber = value;
    }

    /**
     * Gets the value of the option property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOption() {
        return option;
    }

    /**
     * Sets the value of the option property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOption(String value) {
        this.option = value;
    }

    /**
     * Gets the value of the scopeToProvide property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scopeToProvide property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScopeToProvide().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getScopeToProvide() {
        if (scopeToProvide == null) {
            scopeToProvide = new ArrayList<String>();
        }
        return this.scopeToProvide;
    }

    /**
     * Gets the value of the requestor property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorV2 }
     *     
     */
    public RequestorV2 getRequestor() {
        return requestor;
    }

    /**
     * Sets the value of the requestor property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorV2 }
     *     
     */
    public void setRequestor(RequestorV2 value) {
        this.requestor = value;
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
