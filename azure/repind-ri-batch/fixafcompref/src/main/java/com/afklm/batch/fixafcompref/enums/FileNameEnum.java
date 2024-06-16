package com.afklm.batch.fixafcompref.enums;

public enum FileNameEnum {

    GIN_DOESNT_EXIST("out_of_gin_not_match"),
    MARKET_DOESNT_EXIST("out_of_market_not_found"),
    LANGUAGE_DOESNT_EXIST("out_of_language_not_found"),
    GIN_AND_CIN_DOESNT_EXIST("out_gin_cin_not_match"),
    GIN_AND_EMAIL_DOESNT_EXIST("out_gin_email_not_match"),
    ERROR("error"),
    SUCCESS("success");

    private String value;

    FileNameEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
