package com.afklm.repind.common.enums;

public enum SignatureEnum {
    CREATION("C"),
    MODIFICATION("M");

    private String type;

    private SignatureEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
