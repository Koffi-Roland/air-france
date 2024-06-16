package com.afklm.repind.common.enums;

public enum TerminalTypeEnum {
    FIX("T"),
    MOBILE("M"),
    TELEX("X"),
    FAX("F");

    private final String type;

    TerminalTypeEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
