package com.afklm.repind.utils;

public enum FbLevelLabelEnum {

    EXPLORER;

    public String value() { return name(); }

    public static FbLevelLabelEnum fromValue(String v) { return valueOf(v);}
}
