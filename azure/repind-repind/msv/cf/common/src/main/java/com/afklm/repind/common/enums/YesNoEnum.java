package com.afklm.repind.common.enums;

public enum YesNoEnum {

    YES("O"),
    NO("N");

    private String flag;

    YesNoEnum(String flag) {
        this.flag = flag;
    }

    public static YesNoEnum getValue(String flag) {
        for(YesNoEnum value : values()) {
            if(value.flag.equals(flag)) {
                return value;
            }
        }
        return null;
    }

    public boolean toBoolean() {
        return this == YES;
    }

}
