package com.afklm.repind.common.enums;

public enum IndividuStatusEnum {

    MERGED("T"),

    VALID("V"),

    FORGOTEN("F");

    private final String status;

    IndividuStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
