
package com.afklm.repind.ws.w000443.data.schema576961;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MyActIdentifierTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MyActIdentifierTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FP"/>
 *     &lt;enumeration value="RP"/>
 *     &lt;enumeration value="AX"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="MA"/>
 *     &lt;enumeration value="EM"/>
 *     &lt;enumeration value="AC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MyActIdentifierTypeEnum", namespace = "http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd")
@XmlEnum
public enum MyActIdentifierTypeEnum {

    FP,
    RP,
    AX,
    S,
    MA,
    EM,
    AC;

    public String value() {
        return name();
    }

    public static MyActIdentifierTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
