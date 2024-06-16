
package com.afklm.repind.ws.provideindividualdata.siccommontype.siccommonenum;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CivilityEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CivilityEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MR"/>
 *     &lt;enumeration value="MRS"/>
 *     &lt;enumeration value="MISS"/>
 *     &lt;enumeration value="MS"/>
 *     &lt;enumeration value="M_"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CivilityEnum", namespace = "http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd")
@XmlEnum
public enum CivilityEnum {

    MR("MR"),
    MRS("MRS"),
    MISS("MISS"),
    MS("MS"),
    @XmlEnumValue("M_")
    M("M_");
    private final String value;

    CivilityEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CivilityEnum fromValue(String v) {
        for (CivilityEnum c: CivilityEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
