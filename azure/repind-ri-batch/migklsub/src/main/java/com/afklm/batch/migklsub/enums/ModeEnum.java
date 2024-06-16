package com.afklm.batch.migklsub.enums;

public enum ModeEnum {
    INIT("init"),
    DAILY("daily");

    final private String name;

    ModeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
