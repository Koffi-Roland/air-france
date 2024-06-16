package com.afklm.soa.stubs.w000470.v2.createorreplacepaymentpreferencesschema;

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
 *     &lt;enumeration value="ERR_001"/>
 *     &lt;enumeration value="ERR_002"/>
 *     &lt;enumeration value="ERR_003"/>
 *     &lt;enumeration value="ERR_111"/>
 *     &lt;enumeration value="ERR_120"/>
 *     &lt;enumeration value="ERR_133"/>
 *     &lt;enumeration value="ERR_180"/>
 *     &lt;enumeration value="ERR_905"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BusinessErrorEnum")
@XmlEnum
/*
 * TODO : REMOVE ASAP WHEN WSDL IS UP TO DATE
 */
public enum BusinessErrorEnum {

    ERR_001,
    ERR_002,
    ERR_003,
    ERR_111,
    ERR_120,
    ERR_133,
    ERR_180,
    ERR_905,
    ERR_940;

    public String value() {
        return name();
    }

    public static BusinessErrorEnum fromValue(String v) {
        return valueOf(v);
    }

}
