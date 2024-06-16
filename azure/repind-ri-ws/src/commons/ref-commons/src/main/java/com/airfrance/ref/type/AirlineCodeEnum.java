package com.airfrance.ref.type;

public enum AirlineCodeEnum {

    AF("AF"),
    BB("BB"),
    KL("KL");

    private String code;
    AirlineCodeEnum(String code) {
        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

