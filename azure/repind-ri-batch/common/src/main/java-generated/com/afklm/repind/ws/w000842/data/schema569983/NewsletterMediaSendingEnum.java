
package com.afklm.repind.ws.w000842.data.schema569983;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NewsletterMediaSendingEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NewsletterMediaSendingEnum">
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
@XmlType(name = "NewsletterMediaSendingEnum", namespace = "http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd")
@XmlEnum
public enum NewsletterMediaSendingEnum {

    C,
    E,
    F,
    P,
    T;

    public String value() {
        return name();
    }

    public static NewsletterMediaSendingEnum fromValue(String v) {
        return valueOf(v);
    }

}
