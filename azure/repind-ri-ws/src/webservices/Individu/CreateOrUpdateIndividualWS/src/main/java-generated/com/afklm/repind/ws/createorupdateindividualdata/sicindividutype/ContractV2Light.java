
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ContractV2_light complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractV2_light">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="roleContractNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNumeroContrat" minOccurs="0"/>
 *         &lt;element name="roleContractType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeTypeProduit" minOccurs="0"/>
 *         &lt;element name="roleContractSubType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSousTypeProduit" minOccurs="0"/>
 *         &lt;element name="roleContractData" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}ContractData" maxOccurs="50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractV2_light", propOrder = {
    "roleContractNumber",
    "roleContractType",
    "roleContractSubType",
    "roleContractData"
})
public class ContractV2Light {

    protected String roleContractNumber;
    protected String roleContractType;
    protected String roleContractSubType;
    protected List<ContractData> roleContractData;

    /**
     * Gets the value of the roleContractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleContractNumber() {
        return roleContractNumber;
    }

    /**
     * Sets the value of the roleContractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleContractNumber(String value) {
        this.roleContractNumber = value;
    }

    /**
     * Gets the value of the roleContractType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleContractType() {
        return roleContractType;
    }

    /**
     * Sets the value of the roleContractType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleContractType(String value) {
        this.roleContractType = value;
    }

    /**
     * Gets the value of the roleContractSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleContractSubType() {
        return roleContractSubType;
    }

    /**
     * Sets the value of the roleContractSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleContractSubType(String value) {
        this.roleContractSubType = value;
    }

    /**
     * Gets the value of the roleContractData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleContractData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleContractData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContractData }
     * 
     * 
     */
    public List<ContractData> getRoleContractData() {
        if (roleContractData == null) {
            roleContractData = new ArrayList<ContractData>();
        }
        return this.roleContractData;
    }

}
