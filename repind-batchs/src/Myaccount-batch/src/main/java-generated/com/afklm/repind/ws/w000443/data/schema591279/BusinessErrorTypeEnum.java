
package com.afklm.repind.ws.w000443.data.schema591279;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessErrorTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BusinessErrorTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ERROR_001"/>
 *     &lt;enumeration value="ERROR_133"/>
 *     &lt;enumeration value="ERROR_212"/>
 *     &lt;enumeration value="ERROR_382"/>
 *     &lt;enumeration value="ERROR_384"/>
 *     &lt;enumeration value="ERROR_385"/>
 *     &lt;enumeration value="ERROR_387"/>
 *     &lt;enumeration value="ERROR_537"/>
 *     &lt;enumeration value="ERROR_538"/>
 *     &lt;enumeration value="ERROR_551"/>
 *     &lt;enumeration value="ERROR_905"/>
 *     &lt;enumeration value="ERROR_932"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BusinessErrorTypeEnum")
@XmlEnum
public enum BusinessErrorTypeEnum {

    ERROR_001,
    ERROR_133,
    ERROR_212,
    ERROR_382,
    ERROR_384,
    ERROR_385,
    ERROR_387,
    ERROR_537,
    ERROR_538,
    ERROR_551,
    ERROR_905,
    ERROR_932;

    public String value() {
        return name();
    }

    public static BusinessErrorTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
