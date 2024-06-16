
package com.afklm.repind.ws.createorupdateindividualdata.prospecttype.prospectdatatype;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentifierTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IdentifierTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="GIN"/>
 *     &lt;enumeration value="EM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IdentifierTypeEnum", namespace = "http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd")
@XmlEnum
public enum IdentifierTypeEnum {

    GIN,
    EM;

    public String value() {
        return name();
    }

    public static IdentifierTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
