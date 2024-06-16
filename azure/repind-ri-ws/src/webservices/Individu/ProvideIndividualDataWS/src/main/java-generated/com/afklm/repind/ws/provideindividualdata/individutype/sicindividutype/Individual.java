
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

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
 * <p>Java class for Individual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Individual">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="civilian" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Civilian" minOccurs="0"/>
 *         &lt;element name="individualInformations" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}IndividualInformations"/>
 *         &lt;element name="alias" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Alias" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="clientUses" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}UsageClient" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Signature" maxOccurs="4" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Individual", propOrder = {
    "civilian",
    "individualInformations",
    "alias",
    "clientUses",
    "signature"
})
public class Individual {

    protected Civilian civilian;
    @XmlElement(required = true)
    protected IndividualInformations individualInformations;
    protected List<Alias> alias;
    protected List<UsageClient> clientUses;
    protected List<Signature> signature;

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
     * Gets the value of the individualInformations property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualInformations }
     *     
     */
    public IndividualInformations getIndividualInformations() {
        return individualInformations;
    }

    /**
     * Sets the value of the individualInformations property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualInformations }
     *     
     */
    public void setIndividualInformations(IndividualInformations value) {
        this.individualInformations = value;
    }

    /**
     * Gets the value of the alias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Alias }
     * 
     * 
     */
    public List<Alias> getAlias() {
        if (alias == null) {
            alias = new ArrayList<Alias>();
        }
        return this.alias;
    }

    /**
     * Gets the value of the clientUses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clientUses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClientUses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UsageClient }
     * 
     * 
     */
    public List<UsageClient> getClientUses() {
        if (clientUses == null) {
            clientUses = new ArrayList<UsageClient>();
        }
        return this.clientUses;
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
