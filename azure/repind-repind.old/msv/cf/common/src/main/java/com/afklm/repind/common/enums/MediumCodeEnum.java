package com.afklm.repind.common.enums;

public enum MediumCodeEnum {

    DIRECT("D"),
    LOCALIZATION("L"),
    PRO("P");

    private final String code;

    MediumCodeEnum(String status) {
        this.code = status;
    }

    public String getCode() {
        return this.code;
    }
}
