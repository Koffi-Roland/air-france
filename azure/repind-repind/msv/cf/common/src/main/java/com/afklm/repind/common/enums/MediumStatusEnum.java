package com.afklm.repind.common.enums;

public enum MediumStatusEnum {
    VALID("V"),
    HISTORIZED("H"),
    REMOVED("X"),
    INVALID("I"),
    TEMPORARY("T"),
    SUSPENDED("S"),
    PENDING("P");

    private final String status;

    MediumStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
