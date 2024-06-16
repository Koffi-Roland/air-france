
package com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommonenum;

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
 *     &lt;enumeration value="ERROR_MARKETING"/>
 *     &lt;enumeration value="ERROR_ADHESION"/>
 *     &lt;enumeration value="ERROR_FLYINGBLUE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BusinessErrorTypeEnum", namespace = "http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd")
@XmlEnum
public enum BusinessErrorTypeEnum {

    ERROR_MARKETING,
    ERROR_ADHESION,
    ERROR_FLYINGBLUE;

    public String value() {
        return name();
    }

    public static BusinessErrorTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
