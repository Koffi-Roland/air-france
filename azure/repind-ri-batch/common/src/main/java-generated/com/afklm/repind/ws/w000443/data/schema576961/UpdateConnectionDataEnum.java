
package com.afklm.repind.ws.w000443.data.schema576961;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateConnectionDataEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UpdateConnectionDataEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RI_ID"/>
 *     &lt;enumeration value="SOCIAL_ID"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UpdateConnectionDataEnum", namespace = "http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd")
@XmlEnum
public enum UpdateConnectionDataEnum {

    RI_ID,
    SOCIAL_ID;

    public String value() {
        return name();
    }

    public static UpdateConnectionDataEnum fromValue(String v) {
        return valueOf(v);
    }

}
