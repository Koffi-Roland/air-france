
package com.afklm.repind.ws.provideindividualdata.siccommontype.siccommonenum;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TypeContratEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TypeContratEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MA"/>
 *     &lt;enumeration value="FP"/>
 *     &lt;enumeration value="RP"/>
 *     &lt;enumeration value="AX"/>
 *     &lt;enumeration value="S"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TypeContratEnum", namespace = "http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd")
@XmlEnum
public enum TypeContratEnum {

    MA,

    /**
     * contrat Flying blue FP ( Frequence Plus)
     * 
     */
    FP,

    /**
     * contrat Abonné
     * 
     */
    RP,

    /**
     * contrat AX AMEX
     * 
     */
    AX,

    /**
     * Saphir
     * 
     */
    S;

    public String value() {
        return name();
    }

    public static TypeContratEnum fromValue(String v) {
        return valueOf(v);
    }

}
