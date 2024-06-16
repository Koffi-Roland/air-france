
package com.afklm.repind.ws.w000842.data.schema572954;

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
 *     &lt;enumeration value="ERR_133"/>
 *     &lt;enumeration value="ERR_220"/>
 *     &lt;enumeration value="ERR_550"/>
 *     &lt;enumeration value="ERR_551"/>
 *     &lt;enumeration value="ERR_905"/>
 *     &lt;enumeration value="ERR_932"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BusinessErrorTypeEnum")
@XmlEnum
public enum BusinessErrorTypeEnum {

    ERR_133,
    ERR_220,
    ERR_550,
    ERR_551,
    ERR_905,
    ERR_932;

    public String value() {
        return name();
    }

    public static BusinessErrorTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
