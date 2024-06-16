
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import com.afklm.repind.ws.provideindividualdata.siccommontype.siccommonenum.CivilityEnum;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Alias complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Alias">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lastName" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNom" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePrenom" minOccurs="0"/>
 *         &lt;element name="civility" type="{http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd}CivilityEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alias", propOrder = {
    "lastName",
    "firstName",
    "civility"
})
public class Alias {

    protected String lastName;
    protected String firstName;
    protected CivilityEnum civility;

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the civility property.
     * 
     * @return
     *     possible object is
     *     {@link CivilityEnum }
     *     
     */
    public CivilityEnum getCivility() {
        return civility;
    }

    /**
     * Sets the value of the civility property.
     * 
     * @param value
     *     allowed object is
     *     {@link CivilityEnum }
     *     
     */
    public void setCivility(CivilityEnum value) {
        this.civility = value;
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
