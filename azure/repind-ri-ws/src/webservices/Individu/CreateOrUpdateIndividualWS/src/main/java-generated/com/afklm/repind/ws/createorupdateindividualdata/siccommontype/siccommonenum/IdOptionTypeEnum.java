
package com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommonenum;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdOptionTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IdOptionTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FP"/>
 *     &lt;enumeration value="AC"/>
 *     &lt;enumeration value="RP"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="AX"/>
 *     &lt;enumeration value="AI"/>
 *     &lt;enumeration value="GIN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IdOptionTypeEnum", namespace = "http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd")
@XmlEnum
public enum IdOptionTypeEnum {

    FP,
    AC,
    RP,
    S,
    AX,
    AI,
    GIN;

    public String value() {
        return name();
    }

    public static IdOptionTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
