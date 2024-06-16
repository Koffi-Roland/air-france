
package com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommonenum;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AccountStatusEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="V"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="U"/>
 *     &lt;enumeration value="N"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccountStatusEnum", namespace = "http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd")
@XmlEnum
public enum AccountStatusEnum {

    I,
    V,
    E,
    D,
    C,
    U,
    N;

    public String value() {
        return name();
    }

    public static AccountStatusEnum fromValue(String v) {
        return valueOf(v);
    }

}
