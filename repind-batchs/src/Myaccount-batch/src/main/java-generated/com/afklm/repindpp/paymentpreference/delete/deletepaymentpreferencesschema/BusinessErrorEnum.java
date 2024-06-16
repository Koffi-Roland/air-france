
package com.afklm.repindpp.paymentpreference.delete.deletepaymentpreferencesschema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessErrorEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BusinessErrorEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ERR_905"/>
 *     &lt;enumeration value="ERR_111"/>
 *     &lt;enumeration value="ERR_001"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BusinessErrorEnum")
@XmlEnum
public enum BusinessErrorEnum {

    ERR_905,
    ERR_111,
    ERR_001;

    public String value() {
        return name();
    }

    public static BusinessErrorEnum fromValue(String v) {
        return valueOf(v);
    }

}
