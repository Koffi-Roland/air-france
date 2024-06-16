
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.response;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessErrorCodeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BusinessErrorCodeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ERROR_001"/>
 *     &lt;enumeration value="ERROR_003"/>
 *     &lt;enumeration value="ERROR_120"/>
 *     &lt;enumeration value="ERROR_131"/>
 *     &lt;enumeration value="ERROR_133"/>
 *     &lt;enumeration value="ERROR_135"/>
 *     &lt;enumeration value="ERROR_140"/>
 *     &lt;enumeration value="ERROR_209"/>
 *     &lt;enumeration value="ERROR_277"/>
 *     &lt;enumeration value="ERROR_382"/>
 *     &lt;enumeration value="ERROR_384"/>
 *     &lt;enumeration value="ERROR_385"/>
 *     &lt;enumeration value="ERROR_387"/>
 *     &lt;enumeration value="ERROR_538"/>
 *     &lt;enumeration value="ERROR_551"/>
 *     &lt;enumeration value="ERROR_701"/>
 *     &lt;enumeration value="ERROR_702"/>
 *     &lt;enumeration value="ERROR_703"/>
 *     &lt;enumeration value="ERROR_705"/>
 *     &lt;enumeration value="ERROR_706"/>
 *     &lt;enumeration value="ERROR_707"/>
 *     &lt;enumeration value="ERROR_708"/>
 *     &lt;enumeration value="ERROR_709"/>
 *     &lt;enumeration value="ERROR_710"/>
 *     &lt;enumeration value="ERROR_711"/>
 *     &lt;enumeration value="ERROR_712"/>
 *     &lt;enumeration value="ERROR_713"/>
 *     &lt;enumeration value="ERROR_714"/>
 *     &lt;enumeration value="ERROR_715"/>
 *     &lt;enumeration value="ERROR_716"/>
 *     &lt;enumeration value="ERROR_717"/>
 *     &lt;enumeration value="ERROR_718"/>
 *     &lt;enumeration value="ERROR_730"/>
 *     &lt;enumeration value="ERROR_731"/>
 *     &lt;enumeration value="ERROR_732"/>
 *     &lt;enumeration value="ERROR_902"/>
 *     &lt;enumeration value="ERROR_905"/>
 *     &lt;enumeration value="ERROR_932"/>
 *     &lt;enumeration value="OTHER"/>
 *     &lt;enumeration value="ERROR_550"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BusinessErrorCodeEnum")
@XmlEnum
public enum BusinessErrorCodeEnum {

    ERROR_001,
    ERROR_003,
    ERROR_120,
    ERROR_131,
    ERROR_133,
    ERROR_135,
    ERROR_140,
    ERROR_209,
    ERROR_277,
    ERROR_382,
    ERROR_384,
    ERROR_385,
    ERROR_387,
    ERROR_538,
    ERROR_551,
    ERROR_701,
    ERROR_702,
    ERROR_703,
    ERROR_705,
    ERROR_706,
    ERROR_707,
    ERROR_708,
    ERROR_709,
    ERROR_710,
    ERROR_711,
    ERROR_712,
    ERROR_713,
    ERROR_714,
    ERROR_715,
    ERROR_716,
    ERROR_717,
    ERROR_718,
    ERROR_730,
    ERROR_731,
    ERROR_732,
    ERROR_902,
    ERROR_905,
    ERROR_932,
    OTHER,
    ERROR_550;

    public String value() {
        return name();
    }

    public static BusinessErrorCodeEnum fromValue(String v) {
        return valueOf(v);
    }

}
