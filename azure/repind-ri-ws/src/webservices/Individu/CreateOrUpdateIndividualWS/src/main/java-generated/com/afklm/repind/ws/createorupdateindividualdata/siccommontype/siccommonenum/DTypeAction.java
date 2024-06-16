
package com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommonenum;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DTypeAction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DTypeAction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CREATION"/>
 *     &lt;enumeration value="MODIFICATION"/>
 *     &lt;enumeration value="AUTENTICATE"/>
 *     &lt;enumeration value="DEBUG"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DTypeAction", namespace = "http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd")
@XmlEnum
public enum DTypeAction {

    CREATION,
    MODIFICATION,
    AUTENTICATE,
    DEBUG;

    public String value() {
        return name();
    }

    public static DTypeAction fromValue(String v) {
        return valueOf(v);
    }

}
