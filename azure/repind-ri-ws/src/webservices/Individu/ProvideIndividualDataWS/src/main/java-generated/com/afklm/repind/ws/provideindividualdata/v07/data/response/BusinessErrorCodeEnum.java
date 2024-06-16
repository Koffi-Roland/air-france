
package com.afklm.repind.ws.provideindividualdata.v07.data.response;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessErrorCodeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BusinessErrorCodeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ERROR_001"/>
 *     &lt;enumeration value="ERROR_133"/>
 *     &lt;enumeration value="ERROR_711"/>
 *     &lt;enumeration value="ERROR_712"/>
 *     &lt;enumeration value="ERROR_730"/>
 *     &lt;enumeration value="ERROR_731"/>
 *     &lt;enumeration value="ERROR_732"/>
 *     &lt;enumeration value="ERROR_905"/>
 *     &lt;enumeration value="ERROR_932"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BusinessErrorCodeEnum")
@XmlEnum
public enum BusinessErrorCodeEnum {

    ERROR_001,
    ERROR_133,
    ERROR_711,
    ERROR_712,
    ERROR_730,
    ERROR_731,
    ERROR_732,
    ERROR_905,
    ERROR_932,
    OTHER;

    public String value() {
        return name();
    }

    public static BusinessErrorCodeEnum fromValue(String v) {
        return valueOf(v);
    }

}
