
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Habilitation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Habilitation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userId" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeUserId" minOccurs="0"/>
 *         &lt;element name="pwd" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePwd" minOccurs="0"/>
 *         &lt;element name="qalPwd" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeQalPwd" minOccurs="0"/>
 *         &lt;element name="keyCrypt" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeKeyCrypt" minOccurs="0"/>
 *         &lt;element name="newPwd" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePwd" minOccurs="0"/>
 *         &lt;element name="role" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeRole" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Habilitation", propOrder = {
    "userId",
    "pwd",
    "qalPwd",
    "keyCrypt",
    "newPwd",
    "role"
})
public class Habilitation {

    protected String userId;
    protected String pwd;
    protected String qalPwd;
    protected String keyCrypt;
    protected String newPwd;
    protected String role;

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the pwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Sets the value of the pwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPwd(String value) {
        this.pwd = value;
    }

    /**
     * Gets the value of the qalPwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQalPwd() {
        return qalPwd;
    }

    /**
     * Sets the value of the qalPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQalPwd(String value) {
        this.qalPwd = value;
    }

    /**
     * Gets the value of the keyCrypt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyCrypt() {
        return keyCrypt;
    }

    /**
     * Sets the value of the keyCrypt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyCrypt(String value) {
        this.keyCrypt = value;
    }

    /**
     * Gets the value of the newPwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPwd() {
        return newPwd;
    }

    /**
     * Sets the value of the newPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPwd(String value) {
        this.newPwd = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
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
