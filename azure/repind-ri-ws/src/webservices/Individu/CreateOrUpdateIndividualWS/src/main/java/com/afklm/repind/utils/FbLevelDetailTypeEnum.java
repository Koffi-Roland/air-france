package com.afklm.repind.utils;

public enum FbLevelDetailTypeEnum {

    TIER,
    ATTRIBUTE;

    public String value() { return name(); }

    public static FbLevelDetailTypeEnum fromValue(String v) { return valueOf(v);}
}
