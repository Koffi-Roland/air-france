package com.airfrance.ref.type;

public enum SegmentationTypeEnum {
    FVF("FVF", "SALES DIRECTION"),
    SGA("SGA", "SALES FORCES"),
    SGC("SGC", "MAIN COMPANY"),
    SGM("SGM", "MULTI MARKETS"),
    SGR("SGR", "GROUP SALES"),
    STO("STO", "TOUR OPERATORS");

    private final String code;
    private final String label;

    SegmentationTypeEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode(){
        return code;
    }

    public String getLabel(){
        return label;
    }
}
