
package com.afklm.repind.ws.w000443.data.schema588217;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NewletterMediaSendingEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NewletterMediaSendingEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="P"/>
 *     &lt;enumeration value="T"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NewletterMediaSendingEnum", namespace = "http://www.af-klm.com/services/passenger/SicDataType-v1/xsd")
@XmlEnum
public enum NewletterMediaSendingEnum {

    C,
    E,
    F,
    P,
    T;

    public String value() {
        return name();
    }

    public static NewletterMediaSendingEnum fromValue(String v) {
        return valueOf(v);
    }

}
