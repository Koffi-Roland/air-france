
package com.afklm.repind.ws.w000443.data.schema588217;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ETypeContentEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ETypeContentEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PINCODERECOVERY"/>
 *     &lt;enumeration value="FBNUMBERRECOVERY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ETypeContentEnum", namespace = "http://www.af-klm.com/services/passenger/SicDataType-v1/xsd")
@XmlEnum
public enum ETypeContentEnum {

    PINCODERECOVERY,
    FBNUMBERRECOVERY;

    public String value() {
        return name();
    }

    public static ETypeContentEnum fromValue(String v) {
        return valueOf(v);
    }

}
