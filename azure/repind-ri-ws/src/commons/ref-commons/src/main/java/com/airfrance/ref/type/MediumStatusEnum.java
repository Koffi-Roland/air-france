package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum MediumStatusEnum {

    VALID("V"),
    HISTORIZED("H"),
    REMOVED("X"),
    INVALID("I"),
    TEMPORARY("T"),
    SUSPENDED("S");

    private String status;

    MediumStatusEnum(String status) {
        this.status = status;
    }

    public static MediumStatusEnum getEnum(String name) throws InvalidParameterException {

        MediumStatusEnum enumType;

        try {
            enumType = getEnumMandatory(name);
        } catch (MissingParameterException e) {
            enumType = VALID; // default value
        }

        return enumType;
    }

    public static MediumStatusEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {

        if (StringUtils.isEmpty(name)) {
            throw new MissingParameterException("Missing medium status");
        }

        for (MediumStatusEnum e : values()) {
            if (e.status.equals(name)) {
                return e;
            }
        }

        throw new InvalidParameterException("Invalid medium status: " + name);
    }

    public static boolean checkIfStatusExists(String statusName) {
        for (MediumStatusEnum e : values()) {
            if (e.status.equals(statusName)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return status;
    }

}
