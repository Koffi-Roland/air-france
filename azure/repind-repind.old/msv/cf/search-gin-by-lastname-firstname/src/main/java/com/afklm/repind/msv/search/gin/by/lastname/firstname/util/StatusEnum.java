package com.afklm.repind.msv.search.gin.by.lastname.firstname.util;

public enum StatusEnum {
    VALIDATED("V"),
    PENDING("P");

    private String name;

    StatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
