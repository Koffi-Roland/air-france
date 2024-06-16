package com.afklm.repind.common.enums;

public enum RoleAndContractTypeEnum {

    AMEX("AX"),
    FLYING_BLUE("FP"),
    STAFF("G"),
    MY_ACCOUNT("MA"),
    SAPHIR("S");

    private String type;

    private RoleAndContractTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
