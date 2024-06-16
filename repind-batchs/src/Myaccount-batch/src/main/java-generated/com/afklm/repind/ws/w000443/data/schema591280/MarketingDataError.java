
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MarketingDataError.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MarketingDataError">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BAD_INPUT_FORMAT"/>
 *     &lt;enumeration value="MISSING_MANDATORY_INPUT"/>
 *     &lt;enumeration value="RETURN_ERROR"/>
 *     &lt;enumeration value="GIN_NOT_FOUND"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MarketingDataError")
@XmlEnum
public enum MarketingDataError {


    /**
     * Bad input parameter format
     * 
     */
    BAD_INPUT_FORMAT,

    /**
     * Missing mandatory input parameters.
     * 
     */
    MISSING_MANDATORY_INPUT,

    /**
     * a technical error occurred during the execution of service.
     * 
     */
    RETURN_ERROR,

    /**
     * GIN not found in the BDM
     * 
     */
    GIN_NOT_FOUND;

    public String value() {
        return name();
    }

    public static MarketingDataError fromValue(String v) {
        return valueOf(v);
    }

}
