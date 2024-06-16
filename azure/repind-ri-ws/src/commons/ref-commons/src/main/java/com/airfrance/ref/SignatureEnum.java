package com.airfrance.ref;

public enum SignatureEnum {

    BATCH_SEGMENT_DEF("BatchSegmentDef");

    private String value;

    SignatureEnum(String val) {
        this.value = val;
    }

    public String toString() {
        return value;
    }

}
